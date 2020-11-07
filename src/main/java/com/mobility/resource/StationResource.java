package com.mobility.resource;

import com.mobility.assembler.StationModelAssembler;
import com.mobility.model.entity.Station;
import com.mobility.model.entity.StationType;
import com.mobility.resource.dto.StationDto;
import com.mobility.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/stations")
public class StationResource {

    @Autowired
    private StationService stationService;

    @Autowired
    private StationModelAssembler stationModelAssembler;

    @GetMapping(path = "")
    public ResponseEntity<?> listStations(@RequestParam(required = false) String name,
                                          @RequestParam(required = false) StationType type,
                                          @PageableDefault Pageable pageable,
                                          PagedResourcesAssembler<Station> assembler) {
        Object response;
        if(name != null) {
            Optional<Station> station = stationService.getStationByName(name, type);
            response = station.map(value -> stationModelAssembler.toModel(value))
                    .orElseGet(() -> new StationDto().add(linkTo(methodOn(StationResource.class)
                                    .listStations(null, null,
                                            null, null)).withSelfRel()));
        } else {
            Page<Station> stations = stationService.listStations(pageable, type);
            response = assembler.toModel(stations, stationModelAssembler);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<StationDto> getStationById(@PathVariable("id") Integer stationId) {
        StationDto stationDto = stationModelAssembler.toModel(stationService.getStationById(stationId));
        return new ResponseEntity<>(stationDto, HttpStatus.OK);
    }
}
