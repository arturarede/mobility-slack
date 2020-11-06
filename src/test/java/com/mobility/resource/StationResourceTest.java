package com.mobility.resource;

import com.mobility.configuration.AbstractTestConfiguration;
import com.mobility.helpers.StationBuilder;
import com.mobility.model.entity.Station;
import com.mobility.repository.StationRepository;
import com.mobility.service.exceptions.MobilityNotFoundException;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

public class StationResourceTest extends AbstractTestConfiguration {

	private final String RESOURCE = "resource/";

	@MockBean
	private StationRepository stationRepository;

	@Test
	public void whenCallingStationById_thenReturnStation() {
		// given
		Station station = StationBuilder
				.aMetroStation()
				.build();

		when(stationRepository.findById(station.getId())).thenReturn(Optional.of(station));

		// when
		Response response = given(documentationSpec)
				.filter(document(RESOURCE + "whenCallingStationByIdEndpoint_thenReturnStation",
						responseFields(
								fieldWithPath("stationId").type(JsonFieldType.NUMBER).description("The Station id."),
								fieldWithPath("stationName").type(JsonFieldType.STRING).description("The Station name."),
								fieldWithPath("stationType").type(JsonFieldType.STRING).description("The Station type, it can be \"CP (Comboios de Portugal)\" or \"Metro\"."),
								subsectionWithPath("_links").type(JsonFieldType.OBJECT).description("HATEOAS Links " +
										"in HAL Format. Delivering every possible next action a client may perform.")
						)
				))
				.when()
				.port(port)
				.pathParam("id", station.getId())
				.get("/stations/{id}");


		// then
		response.then().statusCode(200);
	}

	@Test
	public void givenStationIdDoesNotExist_whenCallingStationByIdEndpoint_thenReturnNotFoundException() {
		// given
		Station station = StationBuilder
				.aStation()
				.build();

		when(stationRepository.findById(station.getId())).thenThrow(MobilityNotFoundException.class);

		// when
		Response response = given(documentationSpec)
				.filter(document(RESOURCE + "givenStationIdDoesNotExist_whenCallingStationByIdEndpoint_" +
						"thenReturnNotFoundException"))
				.when()
				.port(port)
				.pathParam("id", station.getId())
				.get("/stations/{id}");


		// then
		response.then()
				.statusCode(404);
	}

	@Test
	public void givenStationIdNotNumber_whenCallingStationByIdEndpoint_thenReturnBadRequestException() {
		// given
		String id = "I'm a String";
		// when
		Response response = given(documentationSpec)
				.filter(document(RESOURCE + "givenStationIdNotNumber_whenCallingStationByIdEndpoint_" +
						"thenReturnBadRequestException"))
				.when()
				.port(port)
				.pathParam("id", id)
				.get("/stations/{id}");


		// then
		response.then()
				.statusCode(400);
	}

}
