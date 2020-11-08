package com.mobility.resource;

import com.mobility.mapper.hateoas.TrainModelAssembler;
import com.mobility.model.entity.State;
import com.mobility.model.entity.Train;
import com.mobility.resource.dto.TrainDto;
import com.mobility.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;

@RestController
@RequestMapping("/stations/{station-id}/trains")
public class TrainResource {

    @Autowired
    TrainService trainService;

    @Autowired
    private TrainModelAssembler trainModelAssembler;

    @GetMapping(path = "")
    public ResponseEntity<CollectionModel<TrainDto>> listTrains(@PathVariable("station-id") Integer stationId,
                                                                @RequestParam(required = false) State state,
                                                                @RequestParam(required = false, defaultValue = "false") Boolean withDelay,
                                                                @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm") LocalDateTime date) {

        Collection<Train> trains = trainService.listTrains(stationId, state, withDelay, date);
        CollectionModel<TrainDto> trainsCollectionModel = trainModelAssembler.toCollectionModel(trains, stationId);
        return new ResponseEntity<>(trainsCollectionModel, HttpStatus.OK);
    }
}
