package com.mobility.service;

import com.mobility.model.entity.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TrainService {

    @Value("${com.mobility.ip.base-url}")
    private String ipBaseUrl;

    @Autowired
    StationService stationService;

    public String listTrains(Integer stationId) {
        Station station = stationService.getStationById(stationId);
        return ipBaseUrl+station.getIpId();
    }

}
