package com.mobility.assembler;


import com.mobility.model.entity.Train;
import com.mobility.resource.StationResource;
import com.mobility.resource.TrainResource;
import com.mobility.resource.dto.TrainDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TrainModelAssembler extends RepresentationModelAssemblerSupport<Train, TrainDto> {

    public TrainModelAssembler() {
        super(TrainResource.class, TrainDto.class);
    }

    @NotNull
    @Override
    public TrainDto toModel(@NotNull Train train)
    {
        TrainDto trainDto = instantiateModel(train);

        trainDto.add(linkTo(methodOn(StationResource.class).getStationById(train.getFrom().getId())).withRel("from"));
        trainDto.add(linkTo(methodOn(StationResource.class).getStationById(train.getTo().getId())).withRel("to"));

        trainDto.setId(train.getId());
        trainDto.setFromStationId(train.getFrom().getId());
        trainDto.setToStationId(train.getTo().getId());
        trainDto.setDeparture(train.getDeparture());
        trainDto.setArrival(train.getArrival());
        trainDto.setTrainType(train.getTrainType());
        trainDto.setDelay(train.getDelay());
        return trainDto;
    }
}