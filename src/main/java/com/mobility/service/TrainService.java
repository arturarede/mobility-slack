package com.mobility.service;

import com.mobility.configuration.exceptions.MobilitySystemException;
import com.mobility.mapper.IpMapper;
import com.mobility.model.entity.State;
import com.mobility.model.entity.Station;
import com.mobility.model.entity.Train;
import com.mobility.model.ip.TimetableCp;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

    Logger logger = LoggerFactory.getLogger(TrainService.class);

    public Collection<Train> listTrains(Integer stationId, State state, Boolean withDelay, LocalDateTime date) {
        Station station = stationService.getStationById(stationId);
        String requestState = state == null ? State.ARRIVALS.getValue() : state.getValue();
        String requestDate = getRequestDate(date);

        String requestUrl = requestState + "/" + station.getIpId() + "/" + requestDate;
        logger.debug("The request path is: " + requestUrl);

        TimetableCp response = webClient.get()
                .uri(requestUrl)
                .retrieve()
                .bodyToMono(TimetableCp.class)
                .onErrorResume(e -> Mono.error(new MobilitySystemException("The server could " +
                        "not fulfill the request")))
                .block();

        Collection<Train> trains = getTrains(response);
        if(Boolean.TRUE.equals(withDelay)) {
            trains = trains.stream()
                    .filter(train -> train.getDelay().compareTo(Duration.ofMinutes(0)) > 0)
                    .collect(Collectors.toList());
        }
        return trains;
    }

    @NotNull
    private String getRequestDate(LocalDateTime date) {
        LocalDateTime departure;
        LocalDateTime arrival;
        if (date == null) {
            departure = LocalDateTime.now();
            arrival = LocalDate.now().atTime(LocalTime.MAX);
        } else {
            departure = date;
            arrival = LocalDate.from(date).atTime(LocalTime.MAX);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return departure.format(formatter) + "+" + arrival.format(formatter);
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
