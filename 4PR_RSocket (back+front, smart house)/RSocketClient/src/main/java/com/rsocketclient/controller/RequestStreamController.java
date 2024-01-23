package com.rsocketclient.controller;

import com.rsocketclient.model.Temperature;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/temperature")
public class RequestStreamController {

    private final RSocketRequester rSocketRequester;

    @Autowired
    public RequestStreamController(RSocketRequester rSocketRequester) {
        this.rSocketRequester = rSocketRequester;
    }


    @GetMapping
    public Publisher<Temperature> getTemps() {
        return rSocketRequester
                .route("getAllTemperatures")
                .data(new Temperature())
                .retrieveFlux(Temperature.class);
    }
}
