package com.example.springboot.controller;

import com.example.springboot.Application;
import com.example.springboot.service.HelloService;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestDocs
@ContextConfiguration(classes = {Application.class})
public class HelloControllerTest {

	private final String RESOURCE = "resource/";

	@MockBean
	HelloService helloService;

	@LocalServerPort
	private int port;

	private String uri;

	@PostConstruct
	public void init() {
		uri = "http://localhost:" + port;
	}

	@Autowired
	protected RequestSpecification documentationSpec;

	@Test
	public void whenCallingHellosEndpoint_thenReturnAllHellos() {
		// given
		Set<String> helloSet = new HashSet<>();
		helloSet.add("hi");
		when(helloService.getAll()).thenReturn(helloSet);

		// when
		Response response = given(documentationSpec)
				.filter(document(RESOURCE + "whenCallingHellosEndpoint_thenReturnAllHellos",
							responseFields(
									fieldWithPath("[]").type(JsonFieldType.ARRAY).description("test")
							),
							responseHeaders(
									headerWithName("my-header").description("my header")
							)
						))
				.when()
				.port(RestAssured.port)
				.get(uri + "/hello");


		// then
		response.then().statusCode(200);
	}

	@Test
	public void givenHelloId_whenMakingGetRequestToHelloEndpoint_thenReturnHello() { }

	@Test
	public void givenMovie_whenMakingPostRequestToMovieEndpoint_thenCorrect() {

	}


}
