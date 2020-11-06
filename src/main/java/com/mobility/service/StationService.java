package com.mobility.service;

import com.mobility.mapper.StationMapper;
import com.mobility.model.entity.Station;
import com.mobility.model.entity.StationType;
import com.mobility.repository.StationRepository;
import com.mobility.resource.StationResource;
import com.mobility.resource.dto.StationDto;
import com.mobility.service.exceptions.MobilityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class StationService {

    @Autowired
    StationMapper stationMapper;

    @Autowired
    StationRepository stationRepository;

    public Map<String, Object> listStations(String name, StationType type, int page, int size) {
        Map<String, Object> response = new LinkedHashMap<>();
        if(name != null) {
            Station station = this.getStationByName(name);
            EntityModel<StationDto> stationDto = createStationDtoEntityModel(station);
            response.put("stations", stationDto);
        } else {
            Pageable paging = PageRequest.of(page, size);
            Page<Station> stations = stationRepository.findAll(paging);
            List<EntityModel<StationDto>> stationsDto = stations.getContent().stream()
                    .map(this::createStationDtoEntityModel)
                    .collect(Collectors.toList());
            response.put("totalItems", stations.getTotalElements());
            response.put("stations", stationsDto);
            response.put("totalPages", stations.getTotalPages());
            response.put("currentPage", stations.getNumber());
        }
        return response;
    }

    public EntityModel<StationDto> getStationById(Integer id) {
        Station station = stationRepository.findById(id)
                .orElseThrow(() -> new MobilityNotFoundException("Station with id: " + id + " does not exist"));
        EntityModel<StationDto> stationDtoEntityModel = createStationDtoEntityModel(station);
        stationDtoEntityModel.add(linkTo(methodOn(StationResource.class).listStations(null, null,
                0, 10)).withRel("stations"));
        return stationDtoEntityModel;
    }

    private Station getStationByName(String name) {
        return stationRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new MobilityNotFoundException("Station with name " + name + " does not exist"));
    }

    private EntityModel<StationDto> createStationDtoEntityModel(Station station) {
        StationDto stationDto = stationMapper.stationToStationDTO(station);
        EntityModel<StationDto> stationDtoEntityModel = EntityModel.of(stationDto);
        stationDtoEntityModel.add(linkTo(StationResource.class).slash(station.getId()).withSelfRel());
        return stationDtoEntityModel;
    }

}
