package com.mobility.model.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class Train {

    private Integer id;
    private TrainType trainType;
    private Station from;
    private Station to;
    private LocalDateTime departure;
    private LocalDateTime arrival;
    private LocalTime delay;
}