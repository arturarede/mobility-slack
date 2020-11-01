package com.mobility.controller;

import com.mobility.repository.StationRepository;
import com.mobility.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class HelloController {

	@Autowired
	HelloService helloService;

	@Autowired
	StationRepository stationRepository;

	@GetMapping("/hello")
	public ResponseEntity<?> getHellos() {
		Set<String> result = helloService.getAll();
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("my-header", "value");
		return ResponseEntity.ok()
				.headers(responseHeaders)
				.body(result);
	}

	@PostMapping("/hello")
	@ResponseStatus(HttpStatus.CREATED)
	public String addMovie(@RequestBody String hello) {
		helloService.add(hello);
		return hello;
	}

}
