package com.liga_de_jubileo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;


@Configuration
@ConfigurationProperties(prefix = "external.api.auth")
@Data
public class ExternalApiProperties {

	    private String tokenUrl; // URL para obtener/refrescar token
	    private String apiUrl;   // URL base para las llamadas de datos de la API
	    private final String grantType = "";
	    private final String client_id = ""; 
	    private final String scope = ""; 
	    private final String redirect_url = ""; 
	    private final String response_type = ""; 
	    private String username; // o clientId
	    private String password; // o clientSecret
	    private Long tokenExpireBuffer; 
	
}
