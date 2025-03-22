package com.liga_de_jubileo.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		headers.put("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsImtpZCI6IkNBdXdPcWRMN2YyXzlhTVhZX3ZkbEcyVENXbVV4aklXV1MwNVB4WHljcUkiLCJ0eXAiOiJKV1QifQ.eyJhdWQiOiJmZWM5ZTNmZC04Zjg4LTQ1YWItOGNiZC1iNzBiOWQ2NWRkZTAiLCJpc3MiOiJodHRwczovL2xvZ2luLmxhbGlnYS5lcy8zMzUzMTZlYi1mNjA2LTQzNjEtYmI4Ni0zNWE3ZWRjZGNlYzEvdjIuMC8iLCJleHAiOjE3NDI3MjM4MjEsIm5iZiI6MTc0MjYzNzQyMSwic3ViIjoiTm90IHN1cHBvcnRlZCBjdXJyZW50bHkuIFVzZSBvaWQgY2xhaW0uIiwiZXh0ZW5zaW9uX1VzZXJQcm9maWxlSWQiOiJiYWFlYjMyNC00NzVkLTQzZGYtYTI4MC1jMDNlZDNmNWY4N2MiLCJvaWQiOiJiYWFlYjMyNC00NzVkLTQzZGYtYTI4MC1jMDNlZDNmNWY4N2MiLCJleHRlbnNpb25fRW1haWxWZXJpZmllZCI6ZmFsc2UsImF6cCI6ImZlYzllM2ZkLThmODgtNDVhYi04Y2JkLWI3MGI5ZDY1ZGRlMCIsInZlciI6IjEuMCIsImlhdCI6MTc0MjYzNzQyMX0.UQdw_goCBAJwGLUtuKTxhot_qIZLpq4IUG4M-NlBS3xZz0T8WqLiTY9fA40OdfqRD-SdrFTEuxeHP4PpYFYrZ90SEcjQHdYp1xO-cYWm0OoBnPPE8-HENlIELEeG29gbmM73FLU0OAJ-ucE6MLd7ake0SG0AEN-I09V31e3MSnb4zLG90mkeVHWFTpWd1U7OzdpB_O_be-YZqtpeIZA8sTc30K5xCAhCHxFL1vlctuiNaArNQrsv0-_peMk4tf3vvAcd_HoQmiaPhidujOkG0-7C87x3wfyC8CypZ5Y362m9sAZM4Gj57Dxt9aHhwJQx7qjPREKhBOWmUp6uSs1I5g"); 
		List<ParticipantDto> list = super.fetchData("https://api-fantasy.llt-services.com", "api/v3/leagues/013838003/teams", HttpMethod.GET, headers, params, null); 
		return list; 
	}
	
	


}
