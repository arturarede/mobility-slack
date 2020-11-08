package com.mobility.resource;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.mobility.configuration.AbstractTestConfiguration;
import com.mobility.helpers.StationBuilder;
import com.mobility.model.entity.Station;
import com.mobility.repository.StationRepository;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.when;

public class TrainResourceTest extends AbstractTestConfiguration {

    private final String RESOURCE = "trains/";
    private static WireMockServer wireMockServer;

    @MockBean
    private StationRepository stationRepository;

    @BeforeAll
    private static void setup() {
        wireMockServer = new WireMockServer(wireMockConfig().port(8089));
        wireMockServer.start();
    }

    @BeforeEach
    private void init() {
        wireMockServer.resetAll();
    }

    @AfterAll
    private static void tearDown() {
        wireMockServer.stop();
    }

    @Test
    public void firstTestHere() {
        wireMockServer.stubFor(get(urlMatching("infraestruturasdeportugal"))
                .willReturn(aResponse()
                        .withStatus(200)));

        Station station = StationBuilder
                .aCpStation()
                .build();

        when(stationRepository.findById(station.getId())).thenReturn(Optional.of(station));

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
