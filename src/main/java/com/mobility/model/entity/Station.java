package com.mobility.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Document(collection = "stations")
public class Station {

    @Id
    private Integer id;

    @Field("ip_id")
    private String ipId;

    @Field("metro_id")
    private String metroId;

    @Field("destination_id")
    private String destinationId;

    @Field("lat")
    private String latitude;

    @Field("lon")
    private String longitude;

    @Field("linha")
    private List<TrackType> trackTypes;

    private String zone;
    private String name;
}
