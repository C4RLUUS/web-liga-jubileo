package com.liga_de_jubileo.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.liga_de_jubileo.converters.ParticipantConverter;
import com.liga_de_jubileo.dtos.ParticipantDto;
import com.liga_de_jubileo.entities.Participant;
import com.liga_de_jubileo.externalDto.ParticipantsExternalDto;
import com.liga_de_jubileo.repository.ParticipantRepository;
import com.liga_de_jubileo.services.base.AbstractService;

@Service
public class ParticipantService extends AbstractService<Participant, ParticipantDto, ParticipantsExternalDto>{
	
	private ParticipantRepository repository;
	
	private ParticipantConverter converter;
	
	@Value("${external.api.leagueId}")
	private String leagueId; 

	public ParticipantService(WebClient.Builder webClientBuilder, ParticipantRepository repository, 
							ParticipantConverter converter) {
		super(webClientBuilder, repository, converter);
		this.repository = repository; 
		this.converter = this.converter; 
	}
	
	@Override
	public List<ParticipantDto> findAll() {
		Map <String, String> headers = new HashMap<String, String>(); 
		Map <String, String> params = new HashMap<String, String>(); 
		List<ParticipantDto> list = new ArrayList<>();
		try {
			list = super.fetchData("https://api-fantasy.llt-services.com", "api/v3/leagues/"+ leagueId +"/teams", HttpMethod.GET, headers, params, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return list; 
	}
	
	


}
