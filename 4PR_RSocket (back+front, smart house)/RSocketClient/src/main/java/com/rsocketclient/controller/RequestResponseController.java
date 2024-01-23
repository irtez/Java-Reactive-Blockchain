package com.rsocketclient.controller;


import com.rsocketclient.model.Temperature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api/temperature")
public class RequestResponseController {

    private final RSocketRequester rSocketRequester;

    @Autowired
    public RequestResponseController(RSocketRequester rSocketRequester) {
        this.rSocketRequester = rSocketRequester;
    }


    @GetMapping("/{id}")
    public Mono<Temperature> getTemperature(@PathVariable Long id) {
        return rSocketRequester
                .route("getTemperature")
                .data(id)
                .retrieveMono(Temperature.class);
    }

}
