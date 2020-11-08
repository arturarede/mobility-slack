package com.mobility.model.ip;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Collection;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Builder
public class TimetableCp {

    public TimetableCp() {
        // empty constructor for deserialization
    }

    @JsonProperty("HorarioDetalhe")
    private Collection<TrainCp> trains;

}
