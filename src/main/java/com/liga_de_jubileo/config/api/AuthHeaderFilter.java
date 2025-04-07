package com.liga_de_jubileo.config.api;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;

import com.liga_de_jubileo.config.ExternalApiProperties;
import com.liga_de_jubileo.token.TokenManager;

import reactor.core.publisher.Mono;

@Component
public class AuthHeaderFilter implements ExchangeFilterFunction {
	 	private static final Logger log = LoggerFactory.getLogger(AuthHeaderFilter.class);
	    private final TokenManager tokenManager;
	    private final String authPath; // Para evitar aplicar el filtro a la llamada de login

	     public AuthHeaderFilter(TokenManager tokenManager, ExternalApiProperties properties) {
	         this.tokenManager = tokenManager;
	         // Extraemos solo la ruta de la URL de autenticación para compararla
	         this.authPath = getPathFromAuthUrl(properties.getTokenUrl());
	         log.debug("Auth path to exclude from filtering: {}", this.authPath);
	     }

	    @Override
	    public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {
	        // Comprueba si la URL de la petición actual es la de autenticación
	        if (request.url().getPath().equals(this.authPath)) {
	            log.debug("Petición a la URL de autenticación [{}] - Omitiendo filtro de token.", request.url());
	            // Si es la llamada de login, no hacemos nada y continuamos
	            return next.exchange(request);
	        }

	        log.debug("Aplicando filtro de token para la petición a: {}", request.url());

	        // Obtiene el token (se refrescará si es necesario gracias a la lógica en TokenManager)
	        return tokenManager.getToken()
	                .flatMap(token -> {
	                    // Clona la petición original y añade la cabecera Authorization
	                    ClientRequest authorizedRequest = ClientRequest.from(request)
	                            .headers(headers -> headers.setBearerAuth(token))
	                            .build();
	                    // Continúa con la petición modificada
	                    return next.exchange(authorizedRequest);
	                })
	                .doOnError(error -> log.error("Error al obtener/aplicar token para {}: {}", request.url(), error.getMessage()));
	                // Considera cómo manejar errores aquí. ¿Reintentar? ¿Propagar?
	                // Por ahora, simplemente loguea y deja que el error se propague.
	    }

	    // Helper duplicado (o puedes ponerlo en una clase de utilidad)
	    private String getPathFromAuthUrl(String fullUrl) {
	         try {
	             URI uri = URI.create(fullUrl);
	             // Solo nos interesa la ruta para la comparación
	             return uri.getPath();
	         } catch (IllegalArgumentException e) {
	             log.warn("No se pudo parsear la URL de autenticación: '{}'. Usando la URL tal cual como ruta.", fullUrl);
	             return fullUrl;
	         }
	    }
}
