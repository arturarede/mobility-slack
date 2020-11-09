package com.mobility.model.entity;

public enum TrainType {
    ALFA_PENDULAR, SUBURB, IC, REGIONAL, IR, UNKNOWN;

    public static TrainType valueOfDefault(String myValue) {
        String value = myValue.toUpperCase().replaceAll("\\s", "_");
        try {
            return TrainType.valueOf(value);
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}
