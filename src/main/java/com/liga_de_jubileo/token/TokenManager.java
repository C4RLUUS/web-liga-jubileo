package com.liga_de_jubileo.token;

import java.time.Duration;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.liga_de_jubileo.config.ExternalApiProperties;
import com.liga_de_jubileo.externalDto.TokenExternalDto;

import reactor.core.publisher.Mono;

@Component
public class TokenManager {

	private static final Logger log = LoggerFactory.getLogger(TokenManager.class);
	
	private final WebClient authWebClient; // WebClient específico para la autenticación
    private final ExternalApiProperties apiProperties;

    // Estado del token (volatile para visibilidad entre hilos)
    private volatile String currentToken = null;
    private volatile Instant expiryTime = Instant.MIN; // Inicializar a un tiempo pasado

    // Usaremos un Mono para gestionar la lógica de refresco de forma reactiva y segura
    // Replay(1) asegura que múltiples suscriptores obtengan el mismo token (o error)
    // y que la llamada de refresco solo se ejecute una vez concurrentemente.
    private final Mono<String> tokenMono;

    
    public TokenManager(@Qualifier("authWebClient") WebClient webClientAuth, ExternalApiProperties apiProperties) {
        this.apiProperties = apiProperties;
        // Creamos un WebClient específico para autenticación, podría tener una base URL diferente
        this.authWebClient = webClientAuth; 

        // Lógica principal para obtener/refrescar el token
        this.tokenMono = Mono.defer(this::getFreshTokenIfNeeded) // Solo se ejecuta al suscribirse
                             .cache( // Cachea el resultado (el token) hasta que expire
                                 token -> Duration.ofSeconds(getExpiresInSeconds() - apiProperties.getTokenExpireBuffer()), // TTL = duración - buffer
                                 error -> Duration.ZERO, // No cachear errores
                                 () -> Duration.ZERO // No cachear si está vacío (aunque no debería pasar)
                             );
    }
    
    public Mono<String> getToken() {
        return this.tokenMono;
    }
    
    private boolean isTokenValid() {
        // Considera el buffer para refrescar antes de la expiración real
        return currentToken != null && Instant.now().isBefore(expiryTime.minusSeconds(apiProperties.getTokenExpireBuffer()));
    }
    
    private long getExpiresInSeconds() {
        // Calcula cuánto tiempo falta para expirar desde ahora, mínimo 1 segundo para evitar TTL negativo/cero inmediato.
        if (currentToken == null || expiryTime.isBefore(Instant.now())) {
            return 1; // Si no hay token o ya expiró, el TTL efectivo es mínimo.
        }
        return Math.max(1, Duration.between(Instant.now(), expiryTime).getSeconds());
   }

    
    /**
     * Lógica interna para decidir si refrescar o devolver el actual.
     * Se llama dentro de Mono.defer.
     */
    private Mono<String> getFreshTokenIfNeeded() {
        if (isTokenValid()) {
            log.debug("Usando token cacheado. Válido hasta: {}", expiryTime);
            return Mono.just(currentToken);
        } else {
            log.info("Token no válido o expirado. Solicitando nuevo token...");
            return fetchNewToken();
        }
    }
    
    private Mono<String> fetchNewToken() {
    	
    	MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", apiProperties.getGrantType());
        formData.add("client_id", apiProperties.getClient_id());
        formData.add("scope", apiProperties.getScope());
        formData.add("redirect_url", apiProperties.getRedirect_url());
        formData.add("username", apiProperties.getUsername());
        formData.add("password", apiProperties.getPassword());
        formData.add("response_type", apiProperties.getResponse_type());
    	
        // Ajusta el cuerpo según los requisitos de la API (form-urlencoded, JSON, etc.)
        // Este es un ejemplo común para OAuth2 con grant_type=password o client_credentials
       
        
         // Extrae solo la parte de la ruta de la URL de autenticación completa
         String authPath = getPathFromAuthUrl(apiProperties.getTokenUrl());


        return this.authWebClient.post()
                .uri(authPath) // Usa solo la ruta relativa a la baseUrl del authWebClient
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                // Manejo básico de errores HTTP
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                          clientResponse -> clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    log.error("Error al obtener token: {} - {}", clientResponse.statusCode(), errorBody);
                                    return Mono.error(new RuntimeException("Fallo al autenticar: " + clientResponse.statusCode()));
                                }))
                .bodyToMono(TokenExternalDto.class) // Mapea la respuesta a un DTO
                .doOnNext(authResponse -> {
                    this.currentToken = authResponse.getAccessToken();
                    // Calcula el tiempo de expiración absoluto
                    this.expiryTime = Instant.now().plusSeconds(authResponse.getExpiresIn());
                    log.info("Nuevo token obtenido. Válido por {} segundos. Expiración: {}", authResponse.getExpiresIn(), this.expiryTime);
                })
                .map(TokenExternalDto::getAccessToken) // Emite solo el token
                 .doOnError(e -> {
                    // En caso de error, reseteamos el token para forzar un nuevo intento la próxima vez
                    this.currentToken = null;
                    this.expiryTime = Instant.MIN;
                    log.error("Error en el flujo de obtención de token: {}", e.getMessage());
                });
    }
    
    private String getPathFromAuthUrl(String fullUrl) {
        try {
            java.net.URI uri = java.net.URI.create(fullUrl);
            return uri.getPath() + (uri.getQuery() != null ? "?" + uri.getQuery() : ""); // Incluir query params si existen
        } catch (IllegalArgumentException e) {
            log.warn("No se pudo parsear la URL de autenticación: '{}'. Usando la URL tal cual como ruta.", fullUrl);
            return fullUrl; // Devuelve la URL original si no se puede parsear
        }
   }
    
    private String getBaseUrlFromAuthUrl(String fullUrl) {
        try {
            java.net.URI uri = java.net.URI.create(fullUrl);
            if (uri.getScheme() != null && uri.getHost() != null) {
                return uri.getScheme() + "://" + uri.getAuthority();
            }
        } catch (IllegalArgumentException e) {
             log.warn("No se pudo parsear la URL de autenticación como absoluta: '{}'. Asumiendo relativa a 'external.api.base-url'", fullUrl);
        }
        // Si no es absoluta o falla el parseo, asumimos que es relativa a la API principal
        return apiProperties.getApiUrl();
    }

    
}
