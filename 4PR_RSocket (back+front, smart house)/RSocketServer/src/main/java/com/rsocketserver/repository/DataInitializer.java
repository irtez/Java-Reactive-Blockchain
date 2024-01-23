package com.rsocketserver.repository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import com.rsocketserver.model.Light;
import com.rsocketserver.repository.LightRepository;
import com.rsocketserver.model.Temperature;
import com.rsocketserver.repository.TemperatureRepository;

@Component
public class DataInitializer implements ApplicationRunner {

    @Autowired
    private LightRepository lightRepository;
    @Autowired
    private TemperatureRepository temperatureRepository;

    @Override
    public void run(ApplicationArguments args) {
        float t = 23;
        Light newLight = new Light();
        newLight.setIsOn(false);
        lightRepository.save(newLight);

        Temperature temp1 = new Temperature();
        temp1.setRoom_name("hall");
        temp1.setTemp_value(t);
        temperatureRepository.save(temp1);

        Temperature temp2 = new Temperature();
        temp2.setRoom_name("kitchen");
        temp2.setTemp_value(t-1);
        temperatureRepository.save(temp2);

        Temperature temp3 = new Temperature();
        temp3.setRoom_name("bedroom");
        temp3.setTemp_value(t-2);
        temperatureRepository.save(temp3);

    }
}