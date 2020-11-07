package com.mobility.service.strategy;

import com.mobility.model.entity.Station;
import com.mobility.model.entity.StationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface Strategy {

    Page<Station> listStations(Pageable pageable);

    Optional<Station> getStationByName(String name);

    StationType getStrategyName();
}

