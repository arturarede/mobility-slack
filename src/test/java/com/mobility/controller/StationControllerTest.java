package com.mobility.controller;

import com.mobility.configuration.AbstractTestConfiguration;
import com.mobility.helpers.StationBuilder;
import com.mobility.model.entity.Station;
import com.mobility.repository.StationRepository;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

public class StationControllerTest extends AbstractTestConfiguration {

	private final String RESOURCE = "resource/";

	@MockBean
	private StationRepository stationRepository;

	@Test
	public void whenCallingStationByIdEndpoint_thenReturnStation() {
		// given
		Station station = StationBuilder
				.aStation()
				.build();

		when(stationRepository.findById(station.getId())).thenReturn(Optional.of(station));

		// when
		Response response = given(documentationSpec)
				.filter(document(RESOURCE + "whenCallingHellosEndpoint_thenReturnAllHellos",
						responseFields(
								fieldWithPath("stationId").type(JsonFieldType.NUMBER).description("test"),
								fieldWithPath("stationName").type(JsonFieldType.STRING).description("test")
						)
				))
				.when()
				.port(port)
				.get("/stations/{id}", station.getId());


		// then
		response.then().statusCode(200);
	}

	@Test
	public void givenHelloId_whenMakingGetRequestToHelloEndpoint_thenReturnHello() {}

	@Test
	public void givenMovie_whenMakingPostRequestToMovieEndpoint_thenCorrect() { }

}
