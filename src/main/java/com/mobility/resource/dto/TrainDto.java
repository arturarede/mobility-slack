package com.mobility.resource.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.mobility.model.entity.TrainType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.Duration;
import java.time.LocalDateTime;

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

    @JsonFormat(pattern="yyyy-MM-dd HH:mm", timezone="Europe/Lisbon")
    private LocalDateTime departure;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm", timezone="Europe/Lisbon")
    private LocalDateTime arrival;

    @JsonFormat(pattern="mm")
    private Duration delay;


}
