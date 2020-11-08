package com.mobility.service;

import com.mobility.configuration.exceptions.MobilitySystemException;
import com.mobility.model.entity.Station;
import com.mobility.model.entity.Train;
import com.mobility.model.entity.TrainType;
import com.mobility.model.ip.TimetableCp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Service
public class TrainService {

    @Autowired
    @Qualifier("webClientIp")
    private WebClient webClient;

    @Autowired
    StationService stationService;

    public Collection<Train> listTrains(Integer stationId, String state, Boolean delay, LocalDateTime date) {
        Station station = stationService.getStationById(stationId);

        if (state == null) {

        }

        if (delay == null) {

        }

        if (date == null) {

        }

        Train train = new Train();
        train.setId(12321);
        train.setFrom(station);
        train.setTo(station);
        train.setDeparture(LocalDateTime.now());
        train.setArrival(LocalDateTime.now());
        train.setTrainType(TrainType.ALFA);
        train.setDelay(Duration.ofMinutes(10));

        //@TODO if date not provided assume date.now() until end of day
        //@TODO state = null just assume chegadas IN THEORY THIS MEANS ALL partidas e chegadas
        System.out.println(state);
        System.out.println(delay);
        System.out.println(date);

        TimetableCp response = webClient.get()
                .uri(state + "/" + station.getIpId() + "/2020-11-08T00:00:00+2020-11-08T23:59:59")
                .retrieve()
                .bodyToMono(TimetableCp.class)
                .onErrorResume(e -> Mono.error(new MobilitySystemException("The server could " +
                        "not fulfill the request")))
                .block();

        System.out.println(response);

        return Collections.singletonList(train);
    }

}
