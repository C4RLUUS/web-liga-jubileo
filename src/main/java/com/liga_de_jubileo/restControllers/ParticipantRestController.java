package com.liga_de_jubileo.restControllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liga_de_jubileo.services.ParticipantService;

@RestController
@RequestMapping("/api/participants")
public class ParticipantRestController {
	
	private ParticipantService service; 

	public ParticipantRestController(ParticipantService service) {
		super();
		this.service = service;
	}


	@GetMapping()
	private ResponseEntity<?> getAll(){
		this.service.findAll(); 
	}
}
