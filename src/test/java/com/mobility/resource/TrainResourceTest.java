package com.mobility.resource;

import com.mobility.configuration.AbstractTestConfiguration;
import com.mobility.helpers.StationBuilder;
import com.mobility.model.entity.Station;
import com.mobility.repository.StationRepository;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.when;

public class TrainResourceTest extends AbstractTestConfiguration {

    private final String RESOURCE = "trains/";
    private Station station;

    @MockBean
    private StationRepository stationRepository;

    @BeforeEach
    private void init() {

        station = StationBuilder
                .aCpStation()
                .build();

        when(stationRepository.findById(station.getId())).thenReturn(Optional.of(station));
    }

    @Test
    public void firstTestHere() {
        stubFor(get(anyUrl())
                .willReturn(aResponse().withHeader("Content-Type", "application/json;charset=iso-8859-1")
                .withStatus(200)
                .withBodyFile("ip_response.json")));

        // when
        Response response = given(documentationSpec)
                .when()
                .port(port)
                .pathParam("station-id", station.getId())
                .get("/stations/{station-id}/trains");

        // then
        response.then().statusCode(200);
    }


}
