package com.liga_de_jubileo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.liga_de_jubileo.entity.Participant;

public interface ParticipantRepository extends JpaRepository<Participant, Long>{

	List<Participant> findByName(String Name); 
}
