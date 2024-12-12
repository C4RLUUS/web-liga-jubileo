package com.liga_de_jubileo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "participant")
@NoArgsConstructor
public class Participant extends AbstractEntity{
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "team_value", nullable = false)
	private int teamValue; 
	
	@Column(name = "team_points", nullable = false)
	private int teamPoints;
	
	@Column(name = "ranking_position", nullable = false)
	private int rankingPosition;
	
	@Column(name = "avatar_url", nullable = false)
	private String avatarUrl;

	public Participant(String name, int teamValue, int teamPoints, int rankingPosition, String avatarUrl) {
		super();
		this.name = name;
		this.teamValue = teamValue;
		this.teamPoints = teamPoints;
		this.rankingPosition = rankingPosition;
		this.avatarUrl = avatarUrl;
	} 
	
	
}
