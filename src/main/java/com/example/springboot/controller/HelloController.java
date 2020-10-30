package com.example.springboot.controller;

import com.example.springboot.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class HelloController {

	@Autowired
	HelloService helloService;

	@GetMapping("/hello")
	public ResponseEntity<?> getHellos() {
		Set<String> result = helloService.getAll();
		return ResponseEntity.ok().body(result);
	}

	@PostMapping("/hello")
	@ResponseStatus(HttpStatus.CREATED)
	public String addMovie(@RequestBody String hello) {
		helloService.add(hello);
		return hello;
	}

}
