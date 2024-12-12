package com.liga_de_jubileo.services;

import org.springframework.stereotype.Service;

import com.liga_de_jubileo.converters.ParticipantConverter;
import com.liga_de_jubileo.dtos.ParticipantDto;
import com.liga_de_jubileo.entities.Participant;
import com.liga_de_jubileo.repository.ParticipantRepository;

@Service
public class ParticipantService extends AbstractService<Participant, ParticipantDto>{
	
	private ParticipantRepository repository;
	
	private ParticipantConverter converter;

	public ParticipantService(ParticipantRepository repository, 
							ParticipantConverter converter) {
		super(repository, converter, Participant.class, ParticipantDto.class);
		this.repository = repository; 
		this.converter = this.converter; 
	}
	


}
