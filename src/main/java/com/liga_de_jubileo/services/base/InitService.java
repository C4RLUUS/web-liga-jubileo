package com.liga_de_jubileo.services.base;

import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liga_de_jubileo.config.api.ApiErrorResponse;

import reactor.core.publisher.Mono;

public class InitService {

	private final WebClient webClient;
	private final ObjectMapper objectMapper;

	public InitService(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
		this.webClient = webClientBuilder.build();
		this.objectMapper = objectMapper;
	}
	
	// Funciones para hacer peticiones externas

		protected <E> List<E> fetchData(String baseUrl, String endpoint, HttpMethod method, Map<String, String> headers,
				Map<String, String> queryParams, Object body, ParameterizedTypeReference<List<E>> responseType) {

			WebClient.RequestBodySpec requestSpec = webClient.mutate().baseUrl(baseUrl).build().method(method)
					.uri(uriBuilder -> {
						uriBuilder.path(endpoint);
						queryParams.forEach(uriBuilder::queryParam);
						return uriBuilder.build();
					}).headers(httpHeaders -> headers.forEach(httpHeaders::set));

			WebClient.ResponseSpec responseSpec = (method == HttpMethod.POST || method == HttpMethod.PUT)
					? requestSpec.bodyValue(body).retrieve()
					: requestSpec.retrieve();

			try {
				return responseSpec.onStatus(HttpStatusCode::isError, response -> response.bodyToMono(String.class) // Recibir
																													// error
																													// en
																													// formato
																													// JSON
						.flatMap(errorBody -> {
							try {
								ApiErrorResponse apiError = objectMapper.readValue(errorBody, ApiErrorResponse.class);
								return Mono.error(new WebClientResponseException(apiError.getStatus(), apiError.getError(),
										null, errorBody.getBytes(), null));
							} catch (Exception ex) {
								return Mono
										.error(new RuntimeException("Error al parsear JSON de error: " + ex.getMessage()));
							}
						})).bodyToMono(responseType).block(); // 🔹 Bloqueamos el Mono para obtener el resultado síncrono
			} catch (WebClientResponseException ex) {
				throw ex; 
				//throw new RuntimeException("Error en la petición: " + ex.getMessage(), ex);
			}
		}
}
