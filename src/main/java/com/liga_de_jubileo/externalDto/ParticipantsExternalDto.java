package com.liga_de_jubileo.externalDto;

import com.liga_de_jubileo.externalDto._complementaryDto.ManagerExternalDto;

import lombok.Data;

@Data
public class ParticipantsExternalDto extends AbstractExternalDto{
	
	public ManagerExternalDto manager; 

	public boolean banned; 
	
	public int teamValue; 
	
	public int teamPoints;
	
	public int position; 
	
	public int fixturePoints; 
	
	public String previousPosition; 
}

