package com.mobility.controller;

import com.mobility.controller.dto.StationDto;
import com.mobility.mapper.StationMapper;
import com.mobility.model.entity.Station;
import com.mobility.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/stations")
public class StationController {

    @Autowired
    StationService stationService;

    @Autowired
    StationMapper stationMapper;

    @GetMapping(path = "")
    public ResponseEntity<Map<String, Object>> listStations(@RequestParam(required = false) String name,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size) {
        // @TODO fix this code
        if(name != null) {
            Station station = stationService.getStationByName(name);
            Map<String, Object> response = new HashMap<>();
            response.put("station", station);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        Pageable paging = PageRequest.of(page, size);
        Page<Station> stations = stationService.listStations(paging);
        Map<String, Object> response = new HashMap<>();
        response.put("stations", stations.get());
        response.put("currentPage", stations.getNumber());
        response.put("totalItems", stations.getTotalElements());
        response.put("totalPages", stations.getTotalPages());
        // @TODO return CollectionModel
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public EntityModel<StationDto> getStationById(@PathVariable("id") Integer id) {
        Station station = stationService.getStationById(id);
        StationDto stationDto = stationMapper.stationToStationDTO(station);
        EntityModel<StationDto> stationDtoEntityModel = EntityModel.of(stationDto);
        stationDtoEntityModel.add(linkTo(methodOn(StationController.class).listStations(null, 0, 10)).withRel("stations"));
        return stationDtoEntityModel;
    }
}
