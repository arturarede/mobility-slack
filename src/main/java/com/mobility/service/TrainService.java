package com.mobility.service;

import com.mobility.configuration.exceptions.MobilitySystemException;
import com.mobility.mapper.IpMapper;
import com.mobility.model.entity.Station;
import com.mobility.model.entity.Train;
import com.mobility.model.ip.TimetableCp;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class TrainService {

    @Autowired
    @Qualifier("webClientIp")
    private WebClient webClient;

    @Autowired
    private IpMapper ipMapper;

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

        return getTrains(response);
    }

    @NotNull
    private Collection<Train> getTrains(TimetableCp response) {
        Collection<Train> trains;
        if (response == null || response.getTrains() == null || response.getTrains().isEmpty()) {
            trains = Collections.emptyList();
        } else {
            trains = response.getTrains().stream()
                    .map(t -> ipMapper.trainCpToTrain(t))
                    .collect(Collectors.toList());
        }
        return trains;
    }

}
