package com.proyecto.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.proyecto.app.Repositories.RutinaRepository;
import com.proyecto.app.classes.RegisterForm;
import com.proyecto.app.classes.RoutineForm;
import com.proyecto.app.services.warmUpServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class warmUpController {

	@Autowired
	private warmUpServices wUpService;

	@Autowired
	private RutinaRepository rutinasRepo;

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
		return "register";
	}

	// Home controller
	@GetMapping("/")
	public String showHome() {

		return "home";
	}

	// Register user controller
	@PostMapping("/registerUser")
	public String registerUser(@ModelAttribute(name = "registerForm") RegisterForm registerForm, Model model) {

		String nombre = registerForm.getNombre();
		String apellidos = registerForm.getApellidos();
		String email = registerForm.getEmail();
		String clave = registerForm.getClave();
		String repClave = registerForm.getRepClave();

		if (!(clave.equals(repClave))) {

			model.addAttribute("invalidCredentialsRegClave", true);
			return "register";
		}

		boolean flag = wUpService.checkEmail(email);

		if (flag == true) {

			model.addAttribute("invalidCredentialsReg", true);
			return "register";
		} else {

			model.addAttribute("userRegistered", true);
			wUpService.saveUser(nombre, apellidos, email, clave);
			model.addAttribute("registerSuccessful", true);
			return "register";
		}
	}

	/*
	 * Routines controllers >>>
	 */

	// Routines controller
	@GetMapping("/rutinas")
	public String showRoutine(Model model) {

		model.addAttribute("rutinas", rutinasRepo.findAll());
		return "rutinas";
	}

	// Up Routines controller
	@GetMapping("/subirRutina")
	public String upRoutine() {

		return "subirRutina";
	}

	// Register routine's user
	@PostMapping("/altaRutina")
	public String registerRoutine(RoutineForm routineForm, Model model) {

		String idUsuario = routineForm.getIdUsuario();
		String nombre = routineForm.getNombre();
		String descripcion = routineForm.getDescripcion();
		String lunes = routineForm.getLunes();
		String martes = routineForm.getMartes();
		String miercoles = routineForm.getMiercoles();
		String jueves = routineForm.getJueves();
		String viernes = routineForm.getViernes();
		String sabado = routineForm.getSabado();
		String domingo = routineForm.getDomingo();

		log.info("idUsuario logueado: {}", idUsuario);

		boolean flag = wUpService.checkUser(idUsuario);

		if (flag) {

			wUpService.registerRoutine(idUsuario, nombre, descripcion, lunes, martes, miercoles, jueves, viernes,
					sabado, domingo);
			model.addAttribute("routineRegSuccess", true);
			return "rutinas";
		} else {

			model.addAttribute("userBanned", true);
			return "login";
		}
	}

	// Register routine's user
	@GetMapping("/rutinasRecientes")
	public String orderRoutines(Model model) {

		model.addAttribute("rutinas", rutinasRepo.orderRecents());
		return "rutinas";
	}

	/*
	 * Trainers controllers >>>
	 */

	// Trainers controllers
	@GetMapping("/entrenadores")
	public String showTrainers() {

		return "entrenadores";
	}
}
