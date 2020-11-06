package com.mobility.mapper;

import com.mobility.controller.dto.StationDto;
import com.mobility.model.entity.Station;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StationMapper {

    @Mapping(source = "id", target = "stationId")
    @Mapping(source = "name", target = "stationName")
    @Mapping(target = "stationType", ignore = true)
    StationDto stationToStationDTO(Station station);

    List<StationDto> stationToStationDTO(List<Station> stations);
}
