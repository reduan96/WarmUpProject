package com.proyecto.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.proyecto.app.Repositories.ComentarioRepository;
import com.proyecto.app.Repositories.RutinaRepository;
import com.proyecto.app.Repositories.UsuarioRepository;
import com.proyecto.app.services.WarmUpServices;

@Controller
public class PaymentController {

	@Autowired
	private WarmUpServices wUpService;

	@Autowired
	private RutinaRepository rutinasRepo;
	
	@Autowired
	private UsuarioRepository usuarioRepo;
	
	@Autowired
	private ComentarioRepository comentRepo;
	
	private static final String REDIRECT = "redirect:";

	/*
	 * Pro controllers >>>
	 */

	// Pro controller
	@GetMapping("/altaPro")
	public String showPro() {

		return "pro";
	}

	// Payment controller
	@GetMapping("/pago")
	public String showProPayment() {

		return "pasarelaPago";
	}

	// Payment finished controller
	@GetMapping("/pagoRealizado")
	public String paymentFinished(RedirectAttributes redirectAttrs) {

		redirectAttrs.addFlashAttribute("mensaje", "Pago realizado correctamente, ya es entrenador!")
				.addFlashAttribute("clase", "success");
		return REDIRECT + "/rutinas";
	}

}