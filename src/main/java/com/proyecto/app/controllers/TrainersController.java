package com.proyecto.app.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.proyecto.app.Repositories.TrainersRepository;
import com.proyecto.app.services.WarmUpServices;

@Controller
public class TrainersController {

	@Autowired
	private WarmUpServices wUpService;

	@Autowired
	private TrainersRepository entrenRepo;
	
	private static final String REDIRECT = "redirect:";
	
	/*
	 * Trainers controllers >>>
	 */

	// Trainers controller
	@GetMapping("/entrenadores")
	public String showTrainers(Model model, HttpServletRequest request, 
			RedirectAttributes redirectAttrs) {

		if (!wUpService.checkIfOnlineUserStillExistsOnDb(request, redirectAttrs)) {

			return REDIRECT + "login";
		}
		
		model.addAttribute("entrenadores", entrenRepo.findAll());
		return "entrenadores";
	}
	
	// Info about Trainer controller
	@GetMapping("/infoEntrenador")
	public String infoTrainer(HttpServletRequest request, HttpServletResponse response,
			@CookieValue(name = "idEntrenador", defaultValue = "") String idEntrenadorCookie ,Model model, RedirectAttributes redirectAttrs) {
		
		if (!wUpService.checkIfOnlineUserStillExistsOnDb(request, redirectAttrs)) {

			return REDIRECT + "login";
		}
		
		if(idEntrenadorCookie != null && !"".equals(idEntrenadorCookie)) {
			
			model.addAttribute("entrenador", entrenRepo.findById(idEntrenadorCookie));
			
		}else {
			
			
		}
		
		return null;
	}

}
