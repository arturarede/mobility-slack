package com.mobility.mapper.hateoas;


import com.mobility.model.entity.Station;
import com.mobility.model.entity.StationType;
import com.mobility.resource.StationResource;
import com.mobility.resource.dto.StationDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class StationModelAssembler extends RepresentationModelAssemblerSupport<Station, StationDto> {

	public StationModelAssembler() {
		super(StationResource.class, StationDto.class);
	}

	@NotNull
	@Override
	public StationDto toModel(@NotNull Station station)
	{
		StationDto stationDto = instantiateModel(station);
		stationDto.add(linkTo(methodOn(StationResource.class).getStationById(station.getId())).withSelfRel());

		stationDto.setStationId(station.getId());
		stationDto.setStationName(station.getName());
		stationDto.setStationType(station.getIpId() != null ? StationType.CP.name() : StationType.METRO.name());
		return stationDto;
	}
}