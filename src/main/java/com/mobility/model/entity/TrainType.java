package com.mobility.model.entity;

public enum TrainType {
    ALFA_PENDULAR, SUBURB, IC, REGIONAL, IR, UNKNOWN;

    public static TrainType valueOfDefault(String myValue) {
        try {
            String value = myValue.toUpperCase().replaceAll("\\s", "_");
            return TrainType.valueOf(value);
        } catch (Exception e) {
            return UNKNOWN;
        }
    }
}
