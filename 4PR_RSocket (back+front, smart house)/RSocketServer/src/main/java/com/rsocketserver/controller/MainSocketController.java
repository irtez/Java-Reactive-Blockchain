package com.rsocketserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.rsocketserver.*;
import com.rsocketserver.model.Light;
import com.rsocketserver.repository.LightRepository;
import com.rsocketserver.model.Temperature;
import com.rsocketserver.repository.TemperatureRepository;



@Controller
public class MainSocketController {

    private final LightRepository lightRepository;
    private final TemperatureRepository temperatureRepository;

    @Autowired
    public MainSocketController(
            LightRepository lightRepository,
            TemperatureRepository temperatureRepository) {
        this.lightRepository = lightRepository;
        this.temperatureRepository = temperatureRepository;
    }


    //Request-Response
    @MessageMapping("getTemperature")
    public Mono<Temperature> getTemperature(Long id) {
        return Mono.justOrEmpty(temperatureRepository.findTemperatureById(id));
    }
    //Request-Stream
    @MessageMapping("getAllTemperatures")
    public Flux<Temperature> getAllTemperatures() {
        return Flux.fromIterable(temperatureRepository.findAll());
    }
    //Stream-Stream
    @MessageMapping("changeTemperatures")
    //сначала по очереди поменять их в бд
    //потом return вывести по отдельности (либо все, либо только измненные)
    public Flux<Temperature> changeTemperatures(Flux<Temperature> temperatures) {
        return temperatures
                .flatMap(temperature -> {
                    // Выполняйте обновление температуры в базе данных
                    temperatureRepository.updateTemp(temperature.getId(), temperature.getTemp_value());
                    return Mono.just(temperatureRepository.findTemperatureById(temperature.getId())); // Верните обновленный объект
                })
                .collectList()
                .flatMapMany(Flux::fromIterable);
    }
    //Fire-and-Forget
    @MessageMapping("updateLight")
    public Mono<Light> updateLight(Boolean isOn) {
        long light_id = 1;
        lightRepository.updateLight(light_id, isOn);
        return Mono.empty();
    }
}
