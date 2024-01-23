package com.rsocketserver;

import io.rsocket.frame.decoder.PayloadDecoder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.MimeTypeUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import com.rsocketserver.model.Light;
import com.rsocketserver.repository.LightRepository;
import com.rsocketserver.model.Temperature;
import com.rsocketserver.repository.TemperatureRepository;

import java.time.Duration;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MainSocketControllerTest {


    @Autowired
    private LightRepository lightRepository;

    @Autowired
    private TemperatureRepository temperatureRepository;

    private RSocketRequester requester;

    @BeforeEach
    public void setup() {
        requester = RSocketRequester.builder()
                .rsocketStrategies(builder -> builder.decoder(new Jackson2JsonDecoder()))
                .rsocketStrategies(builder -> builder.encoder(new Jackson2JsonEncoder()))
                .rsocketConnector(connector -> connector
                        .payloadDecoder(PayloadDecoder.ZERO_COPY)
                        .reconnect(Retry.fixedDelay(2, Duration.ofSeconds(2))))
                .dataMimeType(MimeTypeUtils.APPLICATION_JSON)
                .tcp("localhost", 5200);
    }

    @AfterEach
    public void cleanup() {
        requester.dispose();
    }

    @Test
    public void testGetTemperature() {

        long tempId = 1;
        Temperature newTemp = temperatureRepository.findTemperatureById(tempId);

        Mono<Temperature> result = requester.route("getTemperature")
                .data(tempId)
                .retrieveMono(Temperature.class);

        assertNotNull(result.block());
    }

    @Test
    public void testGetAllTemperatures() {
        Flux<Temperature> result = requester.route("getAllTemperatures")
                .retrieveFlux(Temperature.class);

        assertNotNull(result.blockFirst());
    }
    @Test
    public void testTurnLight() {
        Boolean isOnNew = false;
        Long lightId = 1L;

        Mono<Void> result = requester.route("updateLight")
                .data(isOnNew)
                .send();

        Optional<Light> newLight = lightRepository.findById(1L);
        assertNotNull(newLight);
    }



}
