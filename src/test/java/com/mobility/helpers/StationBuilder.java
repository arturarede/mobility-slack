package com.mobility.helpers;

import com.mobility.model.entity.Station;

public class StationBuilder {

    private static final Integer DEFAULT_ID = 3;
    private static final String DEFAULT_NAME = "TestStation";
    private static final String DEFAULT_IP_ID = "100";
    private static final String DEFAULT_METRO_ID = "101";

    private Integer id = DEFAULT_ID;
    private String name = DEFAULT_NAME;
    private String ipId = DEFAULT_IP_ID;
    private String metroId = DEFAULT_METRO_ID;

    private StationBuilder() { }

    public static StationBuilder aStation() {
        return new StationBuilder();
    }

    public static StationBuilder aMetroStation() {
        return StationBuilder
                .aStation()
                .withMetroId(DEFAULT_METRO_ID);
    }

    public static StationBuilder aCpStation() {
        return StationBuilder
                .aStation()
                .withMetroId(DEFAULT_IP_ID);
    }

    public StationBuilder withId(Integer id) {
        this.id = id;
        return this;
    }

    public StationBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public StationBuilder withIpId(String ipId) {
        this.ipId = ipId;
        return this;
    }

    public StationBuilder withMetroId(String metroId) {
        this.metroId = metroId;
        return this;
    }

    public StationBuilder but() {
        return StationBuilder
                .aStation()
                .withId(id)
                .withName(name)
                .withMetroId(metroId)
                .withIpId(ipId);
    }

    public Station build() {
        Station station = new Station();
        station.setId(id);
        station.setName(name);
        station.setIpId(ipId);
        station.setMetroId(metroId);
        return station;
    }
}
