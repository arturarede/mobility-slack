package com.mobility.service;

import com.mobility.model.entity.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class TrainService {

    @Autowired
    @Qualifier("webClientIp")
    private WebClient webClient;

    @Autowired
    StationService stationService;

    public String listTrains(Integer stationId, String state) {
        Station station = stationService.getStationById(stationId);
        String responseJson = webClient.get()
                .uri(state + "/" + station.getIpId() + "/2020-11-07T00:00:00+2020-11-08T23:59:59")
                .exchange()
                .block()
                .bodyToMono(String.class)
                .block();
        System.out.println(responseJson);
        return station.getIpId();
    }

}
