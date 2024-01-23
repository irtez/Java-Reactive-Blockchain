package com.rsocketclient.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Light {
    private Long id;
    private Boolean isOn;
}
