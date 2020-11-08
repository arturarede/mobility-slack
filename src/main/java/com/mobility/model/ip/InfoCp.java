package com.mobility.model.ip;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@Builder
public class InfoCp {

    public InfoCp() {
        // empty constructor for deserialization
    }

    @JsonProperty("ID")
    private Integer id;

    @JsonProperty("Nome")
    private String name;

    @JsonProperty("Descricao")
    private String description;
}
