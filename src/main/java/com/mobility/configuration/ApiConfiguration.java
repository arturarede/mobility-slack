package com.mobility.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ApiConfiguration {

    @Value("${com.mobility.metro.base-url}")
    private String metroBaseUrl;

    @Value("${com.mobility.ip.base-url}")
    private String ipBaseUrl;

    @Bean
    @Qualifier("webClientIp")
    public WebClient webClientIp() {
        return WebClient.create(ipBaseUrl);
    }

    @Bean
    @Qualifier("webClientMetro")
    public WebClient webClientMetro() {
        return WebClient.create(metroBaseUrl);
    }
}
