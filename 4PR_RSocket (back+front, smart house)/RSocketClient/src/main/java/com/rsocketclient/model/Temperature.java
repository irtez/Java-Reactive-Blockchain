package com.rsocketclient.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Temperature {
    private Long id;
    private String room_name;
    private Float temp_value;
}