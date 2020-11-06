package com.mobility.resource;

import com.mobility.model.entity.StationType;
import com.mobility.resource.dto.StationDto;
import com.mobility.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/stations")
public class StationResource {

    @Autowired
    StationService stationService;

    @GetMapping(path = "")
    public ResponseEntity<Map<String, Object>> listStations(@RequestParam(required = false) String name,
                                                            @RequestParam(required = false) StationType type,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size) {

        Map<String, Object> response = stationService.listStations(name, type, page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public EntityModel<StationDto> getStationById(@PathVariable("id") Integer id) {
        return stationService.getStationById(id);
    }



}
