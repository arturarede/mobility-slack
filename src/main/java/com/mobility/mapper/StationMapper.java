package com.mobility.mapper;

import com.mobility.resource.dto.StationDto;
import com.mobility.model.entity.Station;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StationMapper {

    @Mapping(source = "id", target = "stationId")
    @Mapping(source = "name", target = "stationName")
    @Mapping(target = "stationType", expression = "java(station.getIpId() != null ? \"Comboios de Portugal\" : \"Metro\")")
    StationDto stationToStationDTO(Station station);

    List<StationDto> stationToStationDTO(List<Station> stations);
}
