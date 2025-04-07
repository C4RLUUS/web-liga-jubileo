package com.liga_de_jubileo.converters;

import org.springframework.stereotype.Component;

import com.liga_de_jubileo.dtos.RankingDto;
import com.liga_de_jubileo.entities.Ranking;
import com.liga_de_jubileo.externalDto.RankingExternalDto;

@Component("RankingConverter")
public class RankingConverter extends AbstractConverter<Ranking, RankingDto, RankingExternalDto> {

	@Override
	public Ranking convertExternalDtoToEntity(RankingExternalDto externalDto, Class<Ranking> entityClass) {
	        
		 Ranking entity = new Ranking();
		 entity.setIdFantasyManager(Long.parseLong(externalDto.getTeam().getManager().getId()));
		 entity.setIdFantasyTeam(Long.parseLong(externalDto.getTeam().getId()));
		 entity.setPosition(externalDto.getPosition());
		 entity.setPreviousPosition(externalDto.getPreviousPosition()); 
		 entity.setPoints(externalDto.getPoints());
		 entity.setBanned(externalDto.getTeam().isBanned());
		 entity.setName(externalDto.getTeam().getManager().getManagerName());
		 entity.setAvatarUrl(externalDto.getTeam().getManager().getAvatar());
		 entity.setTeamValue(externalDto.getTeam().getTeamValue());
		 
		 
		return (Ranking) entity;		 
		 
	    }
	
}
