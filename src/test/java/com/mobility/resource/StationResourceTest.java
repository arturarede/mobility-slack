package com.mobility.resource;

import com.mobility.configuration.AbstractTestConfiguration;
import com.mobility.helpers.StationBuilder;
import com.mobility.model.entity.Station;
import com.mobility.repository.StationRepository;
import com.mobility.service.exceptions.MobilityNotFoundException;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

public class StationResourceTest extends AbstractTestConfiguration {

	private final String RESOURCE = "station/";

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
						),
						links(
								linkWithRel("self").description("Reference to self.")
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

	@Test
	public void whenListAllStations_thenReturnPaginatedStations() {
		// given
		int pageNr = 0;
		int size = 10;
		Pageable pageable = PageRequest.of(pageNr, size);

		Station stationCp = StationBuilder
				.aCpStation().but()
				.withId(1)
				.build();

		Station stationMetro = StationBuilder
				.aMetroStation().but()
				.withId(2)
				.build();

		Page<Station> page = new PageImpl<>(List.of(stationCp, stationMetro));
		when(stationRepository.findAll(pageable)).thenReturn(page);

		// when
		Response response = given(documentationSpec)
				.filter(document(RESOURCE + "whenListAllStations_thenReturnPaginatedStations",
						responseFields(
								fieldWithPath("_embedded.stations[].stationId").type(JsonFieldType.NUMBER).description("The Station id."),
								fieldWithPath("_embedded.stations[].stationName").type(JsonFieldType.STRING).description("The Station name."),
								fieldWithPath("_embedded.stations[].stationType").type(JsonFieldType.STRING).description("The Station type, it can be \"CP (Comboios de Portugal)\" or \"Metro\"."),
								subsectionWithPath("_embedded.stations[]._links").type(JsonFieldType.OBJECT).description("Self Link to a station"),
								subsectionWithPath("._links").type(JsonFieldType.OBJECT).description("Link to list stations"),
								subsectionWithPath("page").type(JsonFieldType.OBJECT).description("Paging information")
						)
				))
				.when()
				.port(port)
				.get("/stations/");


		// then
		response.then().statusCode(200);
	}



}
