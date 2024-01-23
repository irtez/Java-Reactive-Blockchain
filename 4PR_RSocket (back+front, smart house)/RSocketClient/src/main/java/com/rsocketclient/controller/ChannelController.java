package com.rsocketclient.controller;


import com.rsocketclient.model.Temperature;
import com.rsocketclient.dto.TemperatureWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;


@RestController
@RequestMapping("/api/temperature")
public class ChannelController {

    private final RSocketRequester rSocketRequester;

    @Autowired
    public ChannelController(RSocketRequester rSocketRequester) {
        this.rSocketRequester = rSocketRequester;
    }


    @PostMapping
    public Flux<Temperature> changeTempsMultiple(@RequestBody TemperatureWrapper tempListWrapper){
        List<Temperature> tempList = tempListWrapper.getTemps();
        Flux<Temperature> temps = Flux.fromIterable(tempList);
        return rSocketRequester
                .route("changeTemperatures")
                .data(temps)
                .retrieveFlux(Temperature.class);
    }

}
