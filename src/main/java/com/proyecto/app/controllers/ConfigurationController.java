package com.proyecto.app.controllers;

import java.security.Principal;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	public String showMyProfile(HttpServletRequest request, RedirectAttributes redirectAttrs, Model model,
			Principal principal, HttpServletResponse response) {

		Optional<Users> usuario = usuarioRepo.findByEmail(principal.getName());

		if (usuario.isEmpty()) {

			redirectAttrs.addFlashAttribute("mensaje", "Usuario inexistente en BD, contacte con soporte")
					.addFlashAttribute("clase", "danger");
			return REDIRECT + "login";
		}
		
		model.addAttribute("usuario", usuario.get());
		Optional<Trainers> entrenador = entrenRepo.findTrainersByUserId(usuario.get().getIdUsuario());
		if (entrenador.isPresent()) {

			model.addAttribute("entrenador", entrenador.get());
			model.addAttribute("requerido", "true");
		} else {

			Trainers entrenadorVacio = new Trainers();
			entrenadorVacio.setDescripcion("");
			model.addAttribute("entrenador", entrenadorVacio);
			model.addAttribute("oculto", "d-none");
			model.addAttribute("requerido", "false");
		}

		return "configuracion";
	}

	// Save/Edit Profile's data
	@PostMapping("/guardarDatosPerfil")
	public String SaveDataProfile(HttpServletRequest request, EditProfileForm editProfileForm, Model model,
			RedirectAttributes redirectAttrs, Principal principal, HttpServletResponse response) {

		if (!wUpService.checkIfOnlineUserStillExistsOnDb(request, redirectAttrs)) {

			return REDIRECT + "login";
		}

		String nombre = editProfileForm.getNombre();
		String apellidos = editProfileForm.getApellidos();
		String claveNueva = editProfileForm.getClaveNueva();
		String descripcion = editProfileForm.getDescripcion();

		Optional<Users> usuario = usuarioRepo.findByEmail(principal.getName());
		if (usuario.isPresent()) {

			Optional<Trainers> entrenador = entrenRepo.findTrainersByUserId(usuario.get().getIdUsuario());
			if (entrenador.isEmpty()) {

				if (nombre == null || nombre.equals("") || apellidos == null || apellidos.equals("")) {

					redirectAttrs.addFlashAttribute("mensaje", "Campo/s nulo/s").addFlashAttribute("clase", "danger");
					return REDIRECT + "/config";
				}

				if (claveNueva != null && !claveNueva.equals("")) {

					wUpService.editUserDataProfile(nombre, apellidos, claveNueva, null, usuario.get());
					redirectAttrs.addFlashAttribute("mensaje", "Datos de su perfil actualizados")
							.addFlashAttribute("clase", "success");
					return REDIRECT + "/config";
				}

				if (claveNueva == null || claveNueva.equals("")) {

					wUpService.editUserDataProfile(nombre, apellidos, null, null, usuario.get());
					redirectAttrs.addFlashAttribute("mensaje", "Datos de su perfil actualizados")
							.addFlashAttribute("clase", "success");
					return REDIRECT + "/config";
				}

			} else {

				if (nombre == null || nombre.equals("") || apellidos == null || apellidos.equals("") 
						|| descripcion == null || descripcion.equals("")) {

					redirectAttrs.addFlashAttribute("mensaje", "Campo/s nulo/s").addFlashAttribute("clase", "danger");
					return REDIRECT + "/config";
				}

				if (claveNueva != null && !claveNueva.equals("")) {

					wUpService.editUserDataProfile(nombre, apellidos, claveNueva, descripcion, usuario.get());
					redirectAttrs.addFlashAttribute("mensaje", "Datos de su perfil actualizados")
							.addFlashAttribute("clase", "success");
					return REDIRECT + "/config";
				}

				if (claveNueva == null || claveNueva.equals("")) {

					wUpService.editUserDataProfile(nombre, apellidos, null, descripcion, usuario.get());
					redirectAttrs.addFlashAttribute("mensaje", "Datos de su perfil actualizados")
							.addFlashAttribute("clase", "success");
					return REDIRECT + "/config";
				}
			}
		}

		return null;
	}

}
