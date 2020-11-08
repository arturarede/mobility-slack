package com.mobility.mapper;

import com.mobility.model.entity.Station;
import com.mobility.model.entity.Train;
import com.mobility.model.ip.TrainCp;
import com.mobility.repository.StationRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;

@Mapper(componentModel = "spring")
public abstract class IpMapper {

    @Autowired
    StationRepository stationRepository;

    @Mapping(source = "id", target = "id")
    @Mapping(target = "trainType", expression = "java(com.mobility.model.entity.TrainType.valueOfWithoutSpecial(trainCp.getInfoTrain().getName()))")
    @Mapping(source = "arrival", target = "arrival")
    @Mapping(source = "departure", target = "departure")
    @Mapping(target = "from", expression = "java(idToStation(trainCp.getFromStation().getId()))")
    @Mapping(target = "to", expression = "java(idToStation(trainCp.getToStation().getId()))")
    @Mapping(target = "delay", expression = "java(toDuration(trainCp.getPuctuality().getName()))")
    public abstract Train trainCpToTrain(TrainCp trainCp);

    public Duration toDuration(String duration) {
        String cleanDuration = duration.replaceAll("[^0-9]", "");
        if(cleanDuration.isEmpty()){
            return Duration.ofMinutes(0);
        } else {
            return Duration.ofMinutes(Long.parseLong(cleanDuration));
        }
    }

    public Station idToStation(Integer id) {
        return stationRepository.findByIpId(Integer.toString(id))
                .orElse(new Station());

    }
}
