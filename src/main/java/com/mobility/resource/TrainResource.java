package com.mobility.resource;

import com.mobility.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stations/{station-id}/trains")
public class TrainResource {

    @Autowired
    TrainService trainService;

    @GetMapping(path = "")
    public String listTrains(@PathVariable("station-id") Integer stationId,
                             @RequestParam(required = true) String state) {
        return trainService.listTrains(stationId, state);
    }
}
