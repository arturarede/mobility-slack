package com.mobility.helpers;

import com.mobility.model.entity.Station;

public class StationBuilder {

    private static final Integer DEFAULT_ID = 3;
    private static final String DEFAULT_NAME = "TestStation";

    private Integer id = DEFAULT_ID;
    private String name = DEFAULT_NAME;

    private StationBuilder() { }

    public static StationBuilder aStation() {
        return new StationBuilder();
    }

    public StationBuilder withId(Integer id) {
        this.id = id;
        return this;
    }

    public StationBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public StationBuilder but() {
        return StationBuilder
                .aStation()
                .withId(id)
                .withName(name);
    }

    public Station build() {
        Station station = new Station();
        station.setId(id);
        station.setName(name);
        return station;
    }
}
