package com.mobility.resource;

import com.mobility.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stations/{station-id}/trains")
public class TrainResource {

    @Autowired
    TrainService trainService;

    @GetMapping(path = "")
    public String listTrains(@PathVariable("station-id") Integer stationId) {
        return trainService.testConnection(stationId);
    }
}
