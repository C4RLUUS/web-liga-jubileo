package com.liga_de_jubileo.config.api;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liga_de_jubileo.config.ExternalApiProperties;
import com.liga_de_jubileo.services.base.InitService;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;

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
    
 // WebClient específico para la llamada de AUTENTICACIÓN (SIN filtro)
    @Bean
    @Qualifier("authWebClient")
    public WebClient authWebClient(WebClient.Builder builder, ExternalApiProperties apiProperties) {

         // Configuración de timeouts para la llamada de autenticación
         HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000) // 10 segundos conexión
                .responseTimeout(Duration.ofSeconds(15)) // 15 segundos respuesta global
                 .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(15, TimeUnit.SECONDS))
                            .addHandlerLast(new WriteTimeoutHandler(15, TimeUnit.SECONDS)));

        // Nota: NO se añade el filtro AuthTokenExchangeFilterFunction aquí
        return builder
        		.baseUrl(apiProperties.getApiUrl())
                .clientConnector(new ReactorClientHttpConnector(httpClient)) // TODO: PREGUNTAR QUE ES ESTO
                .build();
    }
}