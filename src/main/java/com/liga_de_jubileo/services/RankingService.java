package com.liga_de_jubileo.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.liga_de_jubileo.converters.RankingConverter;
import com.liga_de_jubileo.dtos.RankingDto;
import com.liga_de_jubileo.entities.Ranking;
import com.liga_de_jubileo.externalDto.RankingExternalDto;
import com.liga_de_jubileo.repository.RankingRepository;
import com.liga_de_jubileo.services.base.AbstractService;

@Service
public class RankingService extends AbstractService<Ranking, RankingDto, RankingExternalDto> {

	private RankingConverter converter; 
	
	private RankingRepository repository;
	
	@Value("${external.api.leagueId}")
	private String leagueId; 

	public RankingService(WebClient.Builder webClientBuilder, RankingRepository repository,
			RankingConverter converter) {
		super(webClientBuilder, repository, converter);
		this.converter = converter; 
		this.repository = repository; 
	} 
	
	@Override
	public List<RankingDto> findAll() {
		Map <String, String> headers = new HashMap<String, String>(); 
		Map <String, String> params = new HashMap<String, String>(); 
		List<RankingDto> list = new ArrayList<>();
		try {
			list = super.fetchData("https://api-fantasy.llt-services.com", "api/v4/leagues/"+ leagueId + "/ranking", HttpMethod.GET, headers, params, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return list; 
	}
	
	
	
	
	
}
