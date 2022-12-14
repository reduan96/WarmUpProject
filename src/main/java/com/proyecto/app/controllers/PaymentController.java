package com.proyecto.app.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.proyecto.app.services.WarmUpServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class PaymentController {

	@Autowired
	private WarmUpServices wUpService;

	private static final String REDIRECT = "redirect:";

	/*
	 * Pro controllers >>>
	 */

	// Pro controller
	@GetMapping("/altaPro")
	public String showPro(HttpServletRequest request, RedirectAttributes redirectAttrs) {

		if (!wUpService.checkIfOnlineUserStillExistsOnDb(request, redirectAttrs)) {

			return REDIRECT + "login";
		}
		return "pro";
	}

	// Payment controller
	@GetMapping("/pago")
	public String showProPayment(HttpServletRequest request, RedirectAttributes redirectAttrs) {

		if (!wUpService.checkIfOnlineUserStillExistsOnDb(request, redirectAttrs)) {

			return REDIRECT + "login";
		}
		
		String tarif = request.getParameter("tarif");
		log.info(tarif);
		
		if(!tarif.equals("10") && !tarif.equals("50")) {
			
			return REDIRECT + "/error";
		}
		
		// Here we create the payment data and the trainer to show him/her
		// data at trainers
		
		return "pasarelaPago";
	}

	// Payment finished controller
	@GetMapping("/pagoRealizado")
	public String paymentFinished(HttpServletRequest request, RedirectAttributes redirectAttrs) {

		if (!wUpService.checkIfOnlineUserStillExistsOnDb(request, redirectAttrs)) {

			return REDIRECT + "login";
		}
		redirectAttrs.addFlashAttribute("mensaje", "Pago realizado correctamente, ya es entrenador!")
				.addFlashAttribute("clase", "success");
		return REDIRECT + "/rutinas";
	}

}
