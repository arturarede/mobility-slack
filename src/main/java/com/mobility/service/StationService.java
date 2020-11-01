package com.mobility.service;

import com.mobility.service.exceptions.MobilityNotFoundException;
import com.mobility.model.entity.Station;
import com.mobility.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationService {

    @Autowired
    StationRepository stationRepository;

    public List<Station> listStations() {
        return stationRepository.findAll();
    }

    public Station getStationById(Integer id) {
        return stationRepository.findById(id)
                .orElseThrow(() -> new MobilityNotFoundException("The station id " + id + " does not exist"));
    }

    public Station getStationByName(String name) {
        return stationRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new MobilityNotFoundException("The station name " + name + " does not exist"));
    }

}
