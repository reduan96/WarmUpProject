package com.proyecto.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TrainersController {

	/*
	 * Trainers controllers >>>
	 */

	// Trainers controller
	@GetMapping("/entrenadores")
	public String showTrainers() {

		return "entrenadores";
	}
	
}
