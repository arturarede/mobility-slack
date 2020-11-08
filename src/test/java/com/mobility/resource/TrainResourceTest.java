package com.mobility.resource;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.mobility.configuration.AbstractTestConfiguration;
import com.mobility.helpers.StationBuilder;
import com.mobility.model.entity.Station;
import com.mobility.repository.StationRepository;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.Optional;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;

public class TrainResourceTest extends AbstractTestConfiguration {

    private final String RESOURCE = "trains/";

    @MockBean
    private StationRepository stationRepository;

    @BeforeEach
    private void init() {
        WireMock.reset();

        stubFor(get(anyUrl())
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json;charset=iso-8859-1")
                        .withStatus(200)
                        .withBodyFile("ip_response.json")));
    }

    @Test
    public void firstTestHere() {
        // given
        Station station = StationBuilder
                .aCpStation()
                .build();

        when(stationRepository.findById(station.getId())).thenReturn(Optional.of(station));

        // when
        Response response = given(documentationSpec)
                .filter(document(RESOURCE + "firstTestHere",
                        requestParameters(
                                parameterWithName("state").optional().description("If it is from (DEPARTURES/ARRIVALS)"),
                                parameterWithName("date").optional().description("Date from when to start the search (dd-MM-yyyy HH:mm)"),
                                parameterWithName("withDelay").optional().description("Only look for the trains with delay (Boolean)")
                        ),
                        responseFields(
                                fieldWithPath("_embedded.trains[].id").type(JsonFieldType.NUMBER).description("The Train id."),
                                fieldWithPath("_embedded.trains[].trainType").type(JsonFieldType.STRING).description("The Train type."),
                                fieldWithPath("_embedded.trains[].departure").type(JsonFieldType.STRING).description(""),
                                fieldWithPath("_embedded.trains[].arrival").type(JsonFieldType.STRING).description(""),
                                fieldWithPath("_embedded.trains[].delay").type(JsonFieldType.STRING).description(""),
                                subsectionWithPath("_embedded.trains[]._links").type(JsonFieldType.OBJECT).description(""),
                                subsectionWithPath("_links").type(JsonFieldType.OBJECT).description("HATEOAS Links " +
                                        "in HAL Format. Delivering every possible next action a client may perform.")
                        )
                ))
                .when()
                .port(port)
                .pathParam("station-id", station.getId())
                .get("/stations/{station-id}/trains");

        // then
        response.then().statusCode(200);
    }


}
