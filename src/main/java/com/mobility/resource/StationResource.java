package com.mobility.resource;

import com.mobility.mapper.StationMapper;
import com.mobility.model.entity.Station;
import com.mobility.model.entity.StationType;
import com.mobility.resource.dto.StationDto;
import com.mobility.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/stations")
public class StationResource {

    @Autowired
    private StationService stationService;

    @Autowired
    private StationMapper stationMapper;

    @GetMapping(path = "")
    public ResponseEntity<Map<String, Object>> listStations(@RequestParam(required = false) String name,
                                                            @RequestParam(required = false) StationType type,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size) {

        Map<String, Object> response = new LinkedHashMap<>();
        if(name != null) {
            Optional<Station> station = stationService.getStationByName(name, type);
            EntityModel<StationDto> stationDto = createStationDtoEntityModel(station.get());
            response.put("stations", Collections.singletonList(stationDto));
        } else {
            Pageable paging = PageRequest.of(page, size);
            Page<Station> stations = stationService.listStations(paging, type);
            List<EntityModel<StationDto>> stationsDto = stations.getContent().stream()
                    .map(this::createStationDtoEntityModel)
                    .collect(Collectors.toList());
            response.put("totalItems", stations.getTotalElements());
            response.put("stations", stationsDto);
            response.put("totalPages", stations.getTotalPages());
            response.put("currentPage", stations.getNumber());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public EntityModel<StationDto> getStationById(@PathVariable("id") Integer id) {
        Station station = stationService.getStationById(id);
        EntityModel<StationDto> stationDtoEntityModel = createStationDtoEntityModel(station);
        stationDtoEntityModel.add(linkTo(methodOn(StationResource.class).listStations(null, null,
                0, 10)).withRel("stations"));
        return stationDtoEntityModel;
    }

    private EntityModel<StationDto> createStationDtoEntityModel(Station station) {
        StationDto stationDto = stationMapper.stationToStationDTO(station);
        EntityModel<StationDto> stationDtoEntityModel = EntityModel.of(stationDto);
        stationDtoEntityModel.add(linkTo(StationResource.class).slash(station.getId()).withSelfRel());
        return stationDtoEntityModel;
    }
}
