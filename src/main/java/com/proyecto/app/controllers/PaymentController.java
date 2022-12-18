package com.proyecto.app.controllers;

import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.proyecto.app.Repositories.UsersRepository;
import com.proyecto.app.model.Trainers;
import com.proyecto.app.model.Users;
import com.proyecto.app.services.WarmUpServices;

@Controller
public class PaymentController {

	@Autowired
	private WarmUpServices wUpService;
	
	@Autowired
	private UsersRepository usuarioRepo;

	private static final String REDIRECT = "redirect:";

	/*
	 * Pro controllers >>>
	 */

	// Pro controller
	@GetMapping("/altaPro")
	public String showPro(HttpServletRequest request, RedirectAttributes redirectAttrs,
			Principal principal) {

		if (!wUpService.checkIfOnlineUserStillExistsOnDb(request, redirectAttrs)) {

			SecurityContextHolder.getContext().setAuthentication(null);
			return REDIRECT + "/login?logout";
		}
		
		String emailUsuario = principal.getName();
		
		if(Boolean.TRUE.equals(wUpService.checkIfTrainerExist(emailUsuario))) {
			
			redirectAttrs
			.addFlashAttribute("mensaje", "Usted ya esta dado de alta como entrenador")
			.addFlashAttribute("clase", "danger");
			return REDIRECT + "/entrenadores";
		}
		
		return "pro";
	}

	// Payment controller
	@GetMapping("/pago")
	public String showProPayment(HttpServletResponse response,
			HttpServletRequest request, RedirectAttributes redirectAttrs,
			@CookieValue(name = "tarif", defaultValue = "") String tarifCookieRecogida) {

		if (!wUpService.checkIfOnlineUserStillExistsOnDb(request, redirectAttrs)) {

			SecurityContextHolder.getContext().setAuthentication(null);
			return REDIRECT + "/login?logout";
		}
		
		String tarif = request.getParameter("tarif");
		
		if(tarif == null) {
			
			tarif = tarifCookieRecogida;
		}
		
		if(!tarif.equals("10") && !tarif.equals("50")) {
			
			return REDIRECT + "/error";
		}
		
		Cookie tarifCookie = new Cookie("tarif", tarif);
		tarifCookie.setPath("/");
		response.addCookie(tarifCookie);
		
		return "pasarelaPago";
	}

	// Payment finished controller
	@PostMapping("/pagoRealizado")
	public String paymentFinished(@CookieValue(name = "tarif", defaultValue = "") String tarifCookie,
			HttpServletRequest request, RedirectAttributes redirectAttrs, Principal principal) {

		if (!wUpService.checkIfOnlineUserStillExistsOnDb(request, redirectAttrs)) {

			SecurityContextHolder.getContext().setAuthentication(null);
			return REDIRECT + "/login?logout";
		}
		
		// Here we create the payment data and the trainer
		// to show him/her data at trainers
		String emailUsuario = principal.getName();
		Optional<Users> usuario = usuarioRepo.findByEmail(emailUsuario);
		
		if(usuario.isEmpty()) {
			return REDIRECT + "/error";
		}
		
		if(request.getParameter("cardNumber").length() != 16 || request.getParameter("cardCvv").length() != 3) {
			
			redirectAttrs
			.addFlashAttribute("mensaje", "El numero de tarjeta son 16 digitos y el cvv 3 digitos")
			.addFlashAttribute("clase", "danger");
			return REDIRECT + "/pago";
		}
		
		Users datosUsuario = usuario.get();
		Trainers entrenador = wUpService.saveTrainer(datosUsuario.getIdUsuario(), datosUsuario.getNombre(), datosUsuario.getApellidos(), 
				datosUsuario.getEmail(), datosUsuario.getClave());
			
		wUpService.savePayment(entrenador.getIdEntrenador(), tarifCookie);
		
		redirectAttrs
		.addFlashAttribute("mensaje", "Pago realizado, ya es entrenador!,"
				+ "Dirijase a mi perfil e introduzca una descripcion de los servicios que ofrece")
		.addFlashAttribute("clase", "success");
		return REDIRECT + "/entrenadores";
	}

}
