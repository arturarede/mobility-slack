package com.example.springboot.service;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class HelloService {

    private Set<String> helloSet = new HashSet<>();

    public Set<String> getAll() {
        return helloSet;
    }

    public void add(String hello) {
        helloSet.add(hello);
    }
}
