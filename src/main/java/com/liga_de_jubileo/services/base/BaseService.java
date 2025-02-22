package com.liga_de_jubileo.services.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liga_de_jubileo.config.api.ApiErrorResponse;

import reactor.core.publisher.Mono;

public abstract class BaseService<T, D, E> {

	private final WebClient webClient;
	private final ObjectMapper objectMapper;

	public BaseService(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
		this.webClient = webClientBuilder.build();
		this.objectMapper = objectMapper;
	}

	// Funciones para sacar los tipos
	protected Class<T> getTClass() {
		return getGenericType(0); // Obtiene el primer tipo genérico (T)
	}

	protected Class<D> getDClass() {
		return getGenericType(1); // Obtiene el segundo tipo genérico (D)
	}

	protected Class<E> getEClass() {
		return getGenericType(2); // Obtiene el tercer tipo genérico (E)
	}

	private Class getGenericType(int index) {
		Type superclass = getClass().getGenericSuperclass();

		if (superclass instanceof ParameterizedType parameterizedType) {
			Type actualType = parameterizedType.getActualTypeArguments()[index]; // Obtiene el tipo en la posición
																					// `index`
			return (Class) actualType;
		}

		throw new RuntimeException("No se pudo determinar el tipo genérico.");
	}

	// Funciones para hacer peticiones externas

	protected <E> E fetchData(String baseUrl, String endpoint, HttpMethod method, Map<String, String> headers,
			Map<String, String> queryParams, Object body, ParameterizedTypeReference<E> responseType) {

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
