package com.liga_de_jubileo.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ParticipantDto extends AbstractDto {

	private String name;
	
	private int teamValue; 
	
	private int teamPoints;
	
	private int rankingPosition;
	
	private String avatarUrl;

	public ParticipantDto(String name, int teamValue, int teamPoints, int rankingPosition, String avatarUrl) {
		super();
		this.name = name;
		this.teamValue = teamValue;
		this.teamPoints = teamPoints;
		this.rankingPosition = rankingPosition;
		this.avatarUrl = avatarUrl;
	}
	
	
}
