package com.liga_de_jubileo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "ranking")
@NoArgsConstructor
@Data
public class Ranking extends AbstractEntity{

	@Column(name = "id_fantasyTeam", nullable = false)
	private Long idFantasyTeam;
	
	@Column(name = "id_fantasyManager", nullable = false)
	private Long idFantasyManager;
	
	@Column(name = "name", nullable = false)
	private  String name; 
	
	@Column(name="avatarUrl", nullable = false)
	private String avatarUrl; 
	
	@Column(name = "banned", nullable=false, columnDefinition = "SMALLINT DEFAULT 0")
	private Boolean banned; 
	
	@Column(name = "points", nullable=false)
	private int points; 
	
	@Column(name="position", nullable= false)
	private int position;
	
	@Column(name ="previous_position",nullable = false)
	private int previousPosition; 
	
	@Column(name = "team_value", nullable = false)
	private int teamValue; 
	
	
}
