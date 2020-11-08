package com.mobility.model.entity;

import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
public class Train {

    private Integer id;
    private TrainType trainType;
    private Station from;
    private Station to;
    private LocalDateTime departure;
    private LocalDateTime arrival;
    private Duration delay;
}