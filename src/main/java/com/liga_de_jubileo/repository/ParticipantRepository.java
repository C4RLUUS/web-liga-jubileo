package com.liga_de_jubileo.repository;

import java.util.List;

import com.liga_de_jubileo.entities.Participant;

public interface ParticipantRepository extends AbstractRepository<Participant>{

	List<Participant> findByName(String Name); 
}
