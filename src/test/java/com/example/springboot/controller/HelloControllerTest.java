package com.example.springboot.controller;

import com.example.springboot.Application;
import com.example.springboot.service.HelloService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

import static io.restassured.RestAssured.get;
import static org.mockito.Mockito.when;

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

	@Test
	public void getHello() {
		// given

		Set<String> helloSet = new HashSet<>();
		helloSet.add("hi");
		when(helloService.getAll()).thenReturn(helloSet);

		get(uri + "/hello").then()
				.assertThat()
				.statusCode(HttpStatus.OK.value());
	}
}
