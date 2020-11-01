package com.mobility.model.entity;

public enum TrackType {
    YELLOW("Amarela"),
    RED("Vermelha"),
    BLUE("Azul"),
    GREEN("Verde");

    private final String value;
    TrackType(String v) {
        value = v;
    }
}
