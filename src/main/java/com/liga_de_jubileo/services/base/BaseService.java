package com.liga_de_jubileo.services.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class BaseService<T, D, E> extends InitService{

	
	private final ObjectMapper objectMapper;

	public BaseService(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
		super(webClientBuilder, objectMapper);
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

	
}
