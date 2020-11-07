package com.mobility.service;

import com.mobility.model.entity.Station;
import com.mobility.repository.StationRepository;
import com.mobility.service.exceptions.MobilityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class StationService {

    @Autowired
    StationRepository stationRepository;

    public Page<Station> listStations(Pageable page) {
        return stationRepository.findAll(page);
    }

    public Station getStationById(Integer id) {
        return stationRepository.findById(id)
                .orElseThrow(() -> new MobilityNotFoundException("Station with id: " + id + " does not exist"));

    }

    public Station getStationByName(String name) {
        return stationRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new MobilityNotFoundException("Station with name " + name + " does not exist"));
    }

}
