package com.liga_de_jubileo.restControllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test") // Define la URL base
public class TestRestController {

	@GetMapping // Responde a GET /api/test
	public String testEndpoint() {
		return "¡El endpoint funciona correctamente! 🚀";
	}
}
