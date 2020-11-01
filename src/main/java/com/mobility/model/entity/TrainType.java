package com.mobility.model.entity;

public enum TrainType {
    ALFA("ALFA PENDULAR"),
    SUBURB("SUBURB"),
    IC("IC"),
    REGIONAL("REGIONAL"),
    METRO("METRO");

    private final String value;
    TrainType(String v) {
        value = v;
    }
}
