package com.liga_de_jubileo.config.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liga_de_jubileo.services.base.BaseService;

@Configuration
public class WebClientConfig<T,D,E> {
    @Bean
    BaseService<T, D, E> webClientService(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        return new BaseService<T,D,E>(webClientBuilder, objectMapper);
    }
}