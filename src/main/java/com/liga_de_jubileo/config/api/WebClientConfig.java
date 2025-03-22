package com.liga_de_jubileo.config.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liga_de_jubileo.services.base.InitService;

@Configuration
public class WebClientConfig {
    @Bean
    InitService webClientService(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        return new InitService(WebClient.builder(), new ObjectMapper());
    }
    
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}