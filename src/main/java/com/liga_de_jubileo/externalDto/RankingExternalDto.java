package com.liga_de_jubileo.externalDto;

import com.liga_de_jubileo.externalDto._complementaryDto.TeamExternalDto;

import lombok.Data;

@Data
public class RankingExternalDto extends AbstractExternalDto{

	private int position;
	
	private int previousPosition; 
	
	private int points; 
	
	private TeamExternalDto team; 
	
}
