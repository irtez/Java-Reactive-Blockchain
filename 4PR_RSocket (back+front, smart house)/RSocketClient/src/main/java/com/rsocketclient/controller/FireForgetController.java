package com.rsocketclient.controller;


import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/light")
public class FireForgetController {

    private final RSocketRequester rSocketRequester;

    @Autowired
    public FireForgetController(RSocketRequester rSocketRequester) {
        this.rSocketRequester = rSocketRequester;
    }


    @PostMapping
    public Publisher<Void> updateLight(@RequestBody Boolean isOn){
        return rSocketRequester
                .route("updateLight")
                .data(isOn)
                .send();
    }

}
