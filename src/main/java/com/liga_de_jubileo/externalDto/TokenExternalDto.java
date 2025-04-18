package com.liga_de_jubileo.externalDto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TokenExternalDto {

	@JsonProperty("access_token")
	private String accessToken;
	@JsonProperty("expires_in")
	private long expiresIn;
	@JsonProperty("token_type")
	private String tokenType;
	
	
}
