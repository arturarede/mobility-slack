package com.mobility.model.entity;

public enum TrainType {
    ALFA_PENDULAR, SUBURB, IC, REGIONAL, IR;

    public static TrainType valueOfWithoutSpecial(String myValue) {
        String value = myValue.toUpperCase().replaceAll("\\s", "_");
        return TrainType.valueOf(value);
    }
}
