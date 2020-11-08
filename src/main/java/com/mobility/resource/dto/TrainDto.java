package com.mobility.resource.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.mobility.model.entity.TrainType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@EqualsAndHashCode(callSuper=false)
@Relation(collectionRelation = "trains", itemRelation = "train")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TrainDto extends RepresentationModel<TrainDto>  {

    private Integer id;
    private TrainType trainType;
    private Integer fromStationId;
    private Integer toStationId;
    private LocalDateTime departure;
    private LocalDateTime arrival;
    private LocalTime delay;
}
