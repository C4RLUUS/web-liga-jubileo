package com.liga_de_jubileo.externalDto._complementaryDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.liga_de_jubileo.externalDto.AbstractExternalDto;

import lombok.Data;

@Data
public class TeamExternalDto extends AbstractExternalDto{

	private boolean managerWarned; 
	
	private ManagerExternalDto manager;
	
	private boolean banned;
	
	private int teamValue; 
	
	private int teamPoints;
	
	private Object teamMoney; 
	
	@JsonProperty("isAdmin")
	private boolean admin; 
	
}
