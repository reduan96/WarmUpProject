package com.proyecto.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.proyecto.app.classes.RegisterForm;
import com.proyecto.app.services.WarmUpServices;


@Controller
public class WarmUpController {

	@Autowired
	private WarmUpServices wUpService;
	
	/*
	 * ${#request.userPrincipal.principal.email}
	 * ${#request.userPrincipal.principal.clave} etc This is the way to take de
	 * user's data that is logged in
	 */

	// Init Flow
	@GetMapping("/login")
	public String showLogin() {
		return "login";
	}

	// Register controller
	@GetMapping("/register")
	public String showRegister() {
		return "registro";
	}

	// Home controller
	@GetMapping("/")
	public String showHome() {

		return "inicio";
	}

	// Register user controller
	@PostMapping("/registerUser")
	public String registerUser(@ModelAttribute(name = "registerForm") RegisterForm registerForm, 
			Model model) {

		String nombre = registerForm.getNombre();
		String apellidos = registerForm.getApellidos();
		String email = registerForm.getEmail();
		String clave = registerForm.getClave();
		String repClave = registerForm.getRepClave();

		if (!(clave.equals(repClave))) {

			model.addAttribute("invalidCredentialsRegClave", true);
			return "registro";
		}

		boolean flag = wUpService.checkEmail(email);

		if (flag) {

			model.addAttribute("invalidCredentialsReg", true);
			return "registro";
		} else {

			model.addAttribute("userRegistered", true);
			wUpService.saveUser(nombre, apellidos, email, clave);
			model.addAttribute("registerSuccessful", true);
			return "registro";
		}
	}
	
}
