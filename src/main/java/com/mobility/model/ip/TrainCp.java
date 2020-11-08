package com.mobility.model.ip;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrainCp {

    @JsonProperty("ID")
    private Integer id;

    @JsonProperty("Nome")
    private String name;

    @JsonProperty("HoraChegada")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Europe/Lisbon")
    private LocalDateTime arrival;

    @JsonProperty("HoraPartida")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Europe/Lisbon")
    private LocalDateTime departure;

    @JsonProperty("Comboio")
    private InfoCp infoTrain;

    @JsonProperty("EstacaoOrigem")
    private InfoCp fromStation;

    @JsonProperty("EstacaoDestino")
    private InfoCp toStation;

    @JsonProperty("Operador")
    private InfoCp operator;

    @JsonProperty("EstadoComboio")
    private InfoCp puctuality;

}

