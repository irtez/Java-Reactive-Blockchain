package com.rsocketclient.dto;

import com.rsocketclient.model.Temperature;

import java.util.List;

public class TemperatureWrapper {
    private List<Temperature> new_temps;

    public List<Temperature> getTemps() {
        return new_temps;
    }

    public void setNew_temps(List<Temperature> new_temps) {
        this.new_temps = new_temps;
    }
}
