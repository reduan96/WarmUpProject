package com.proyecto.app.controllers;

import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.proyecto.app.Repositories.TrainersRepository;
import com.proyecto.app.Repositories.UsersRepository;
import com.proyecto.app.classes.EditProfileForm;
import com.proyecto.app.model.Trainers;
import com.proyecto.app.model.Users;
import com.proyecto.app.services.WarmUpServices;

@Controller
public class ConfigurationController {

	@Autowired
	private WarmUpServices wUpService;
	
	@Autowired
	private TrainersRepository entrenRepo;
	
	@Autowired
	private UsersRepository usuarioRepo;
	
	private static final String REDIRECT = "redirect:";
	
	/**
	 * My Profile Controllers
	 */
	
	// Configuration controller
	@GetMapping("/config")
	public String showMyProfile(HttpServletRequest request, 
			RedirectAttributes redirectAttrs, Model model, Principal principal,
			@CookieValue(name = "datUsuarioMod", defaultValue = "") String datUsuarioMod,
			HttpServletResponse response) {
		
		if(datUsuarioMod.equals("OK")) {
			
			wUpService.resetCookie(request, response, "datUsuarioMod");
		}else {
			
			if (!wUpService.checkIfOnlineUserStillExistsOnDb(request, redirectAttrs)) {

				return REDIRECT + "login";
			}
		}
		
		Optional<Users> usuario = usuarioRepo.findByEmail(principal.getName());
		if(usuario.isPresent()) {
			
			model.addAttribute("usuario", usuario.get());
			Optional<Trainers> entrenador = entrenRepo.findTrainersByUserId(usuario.get().getIdUsuario());
			if(entrenador.isPresent()) {
				
				model.addAttribute("entrenador", entrenador.get());
			}else {
				
				Trainers entrenadorVacio = new Trainers();
				entrenadorVacio.setDescripcion("");
				model.addAttribute("entrenador", entrenadorVacio);
				model.addAttribute("oculto", "d-none");
			}
		}
		
		return "configuracion";
	}
	
	// Save/Edit Profile's data
	@PostMapping("/guardarDatosPerfil")
	public String SaveDataProfile(HttpServletRequest request, EditProfileForm editProfileForm, 
			Model model, RedirectAttributes redirectAttrs, Principal principal, HttpServletResponse response) {
		
		if (!wUpService.checkIfOnlineUserStillExistsOnDb(request, redirectAttrs)) {

			return REDIRECT + "login";
		}
		
		String nombre = editProfileForm.getNombre();
		String apellidos = editProfileForm.getApellidos();
		String email = editProfileForm.getEmail();
		String claveNueva = editProfileForm.getClaveNueva();
		String descripcion = editProfileForm.getDescripcion();
		
		Cookie datUsuarioMod = new Cookie("datUsuarioMod", "OK");
		datUsuarioMod.setPath("/");
		response.addCookie(datUsuarioMod);
		
		
		Optional<Users> usuario = usuarioRepo.findByEmail(principal.getName());
		if(usuario.isPresent()) {
			
			Optional<Trainers> entrenador = entrenRepo.findTrainersByUserId(usuario.get().getIdUsuario());
			if(entrenador.isEmpty()) {
				
				if(nombre == null || nombre.equals("") || apellidos == null || apellidos.equals("") 
						|| email == null || email.equals("")) {
					
					redirectAttrs
					.addFlashAttribute("mensaje", "Campo/s nulo/s")
					.addFlashAttribute("clase", "danger");
					return REDIRECT + "/config";
				}
				
				if(claveNueva != null && !claveNueva.equals("")) {
					
					wUpService.editUserDataProfile(nombre, apellidos, email, claveNueva, null, usuario.get());
					redirectAttrs
					.addFlashAttribute("mensaje", "Datos actualizados")
					.addFlashAttribute("clase", "success");
					return REDIRECT + "/config";
				}
				
				if(claveNueva == null || claveNueva.equals("")) {
					
					wUpService.editUserDataProfile(nombre, apellidos, email, null, null, usuario.get());
					redirectAttrs
					.addFlashAttribute("mensaje", "Datos actualizados")
					.addFlashAttribute("clase", "success");
					return REDIRECT + "/config";
				}
				
			}else {
				
				if(nombre == null || nombre.equals("") || apellidos == null || apellidos.equals("") 
						|| email == null || email.equals("") || descripcion == null || descripcion.equals("")) {
					
					redirectAttrs
					.addFlashAttribute("mensaje", "Campo/s nulo/s")
					.addFlashAttribute("clase", "danger");
					return REDIRECT + "/config";
				}
				
				if(claveNueva != null && !claveNueva.equals("")) {
					
					wUpService.editUserDataProfile(nombre, apellidos, email, claveNueva, descripcion, usuario.get());
					redirectAttrs
					.addFlashAttribute("mensaje", "Datos actualizados")
					.addFlashAttribute("clase", "success");
					return REDIRECT + "/config";
				}
				
				if(claveNueva == null || claveNueva.equals("")) {
					
					wUpService.editUserDataProfile(nombre, apellidos, email, null, descripcion, usuario.get());
					redirectAttrs
					.addFlashAttribute("mensaje", "Datos actualizados")
					.addFlashAttribute("clase", "success");
					return REDIRECT + "/config";
				}
			}
		}
		
		return null;
	}
	
}