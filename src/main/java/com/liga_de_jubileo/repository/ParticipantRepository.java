package com.liga_de_jubileo.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.liga_de_jubileo.entities.Participant;

@Repository
public interface ParticipantRepository extends AbstractRepository<Participant>{

	List<Participant> findByName(String Name); 
}
