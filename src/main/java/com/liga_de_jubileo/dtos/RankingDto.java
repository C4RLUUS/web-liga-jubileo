package com.liga_de_jubileo.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RankingDto {

	private String name; 
	
	private String avatarUrl; 
	
	private int points; 
	
	private int position; 
	
	private int previousPosition; 
	
	private boolean banned; 
	
	private int teamValue; 
	
}
