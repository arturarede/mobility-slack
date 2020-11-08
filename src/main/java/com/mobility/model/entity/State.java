package com.mobility.model.entity;

public enum State {
    DEPARTURES("partidas"),
    ARRIVALS("chegadas");

    State(String value) {
        this.value = value;
    }

    private final String value;
    public String getValue() {
        return value;
    }
}
