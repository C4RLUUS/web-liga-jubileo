package com.liga_de_jubileo.restControllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liga_de_jubileo.services.RankingService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Ranking Endpoints")
@RestController
@RequestMapping("/api/ranking")
public class RankingRestController extends AbstractRestController{

	private RankingService service;

	public RankingRestController(RankingService service) {
		super();
		this.service = service;
	} 
	
	@GetMapping
	private ResponseEntity<?> getAll(){
		return handleRequest(() -> this.service.findAll(), null); 
		//return ResponseEntity.ok("Hola mundo ");
	}
}
