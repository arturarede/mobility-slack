package com.mobility.controller;

import com.mobility.model.entity.Station;
import com.mobility.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stations")
public class StationController {

    @Autowired
    StationService stationService;

    @GetMapping("/")
    public CollectionModel<Station> listStations() {
        List<Station> stations = stationService.listStations();
        return CollectionModel.of(stations);
    }

    @GetMapping("/{name}")
    public EntityModel<Station> getStationByName(@PathVariable("name") String name) {
        Station station = stationService.getStationByName(name);
        return EntityModel.of(station);
    }
}
