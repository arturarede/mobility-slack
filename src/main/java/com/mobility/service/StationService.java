package com.mobility.service;

import com.mobility.model.entity.Station;
import com.mobility.model.entity.StationType;
import com.mobility.repository.StationRepository;
import com.mobility.service.exceptions.MobilityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StationService {

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private StrategyFactory strategyFactory;

    public Page<Station> listStations(Pageable pageable, StationType type) {
        Page<Station> page;
        if (type != null) {
            page = strategyFactory.findStrategy(type).listStations(pageable);
        } else {
            page = stationRepository.findAll(pageable);
        }
        return page;
    }

    public Optional<Station> getStationByName(String name, StationType type) {
        Optional<Station> station;
        if (type != null) {
            station = strategyFactory.findStrategy(type).getStationByName(name);
        } else {
            station = stationRepository.findByNameIgnoreCase(name);
        }
        return station;
    }

    public Station getStationById(Integer id) {
        return stationRepository.findById(id)
                .orElseThrow(() -> new MobilityNotFoundException("Station with id: " + id + " does not exist"));
    }

}
