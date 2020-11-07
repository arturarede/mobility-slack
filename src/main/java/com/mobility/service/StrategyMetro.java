package com.mobility.service;

import com.mobility.model.entity.Station;
import com.mobility.model.entity.StationType;
import com.mobility.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StrategyMetro implements Strategy {

    @Autowired
    StationRepository stationRepository;

    @Override
    public Page<Station> listStations(Pageable pageable) {
        return stationRepository.findAllByMetroIdNotNull(pageable);
    }

    @Override
    public Optional<Station> getStationByName(String name) {
        Optional<Station> station = stationRepository.findByNameIgnoreCase(name);
        if (station.isPresent() && station.get().getMetroId() == null) {
            station = Optional.empty();
        }
        return station;
    }

    @Override
    public StationType getStrategyName() {
        return StationType.METRO;
    }
}
