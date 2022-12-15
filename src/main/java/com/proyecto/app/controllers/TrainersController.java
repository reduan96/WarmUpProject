package com.proyecto.app.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.proyecto.app.Repositories.CommentsRepository;
import com.proyecto.app.Repositories.TrainersRepository;
import com.proyecto.app.Repositories.UsersRepository;
import com.proyecto.app.model.Comments;
import com.proyecto.app.model.Trainers;
import com.proyecto.app.model.Users;
import com.proyecto.app.services.WarmUpServices;

@Controller
public class TrainersController {

	@Autowired
	private WarmUpServices wUpService;

	@Autowired
	private TrainersRepository entrenRepo;

	@Autowired
	private UsersRepository usuarioRepo;

	@Autowired
	private CommentsRepository comentRepo;

	private static final String REDIRECT = "redirect:";

	/*
	 * Trainers controllers >>>
	 */

	// Trainers controller
	@GetMapping("/entrenadores")
	public String showTrainers(Model model, HttpServletRequest request, RedirectAttributes redirectAttrs) {

		if (!wUpService.checkIfOnlineUserStillExistsOnDb(request, redirectAttrs)) {

			return REDIRECT + "login";
		}

		model.addAttribute("entrenadores", entrenRepo.findAll());
		return "entrenadores";
	}

	// Info about Trainer controller
	@GetMapping("/infoEntrenador")
	public String infoTrainer(HttpServletRequest request, HttpServletResponse response,
			@CookieValue(name = "idEntrenador", defaultValue = "") String idEntrenadorCookie, Model model,
			RedirectAttributes redirectAttrs) {

		if (!wUpService.checkIfOnlineUserStillExistsOnDb(request, redirectAttrs)) {

			return REDIRECT + "login";
		}

		if (idEntrenadorCookie != null && !"".equals(idEntrenadorCookie)) {

			model.addAttribute("entrenador", entrenRepo.findById(idEntrenadorCookie));
			Optional<Trainers> entrenador = entrenRepo.findById(idEntrenadorCookie);
			Optional<Users> usuario = usuarioRepo.findByEmail(request.getUserPrincipal().getName());
			String idUsuario = usuario.get().getIdUsuario();
			// if its me i cant comment
			if (entrenador.isPresent()) {

				if (entrenador.get().getIdUsuario().equals(idUsuario)) {

					model.addAttribute("ocultar", "d-none");
				}
			}
			// Show own comment
			Optional<Comments> comentarioPropio = comentRepo.checkIfCommentExistOnTrainer(idUsuario,
					idEntrenadorCookie);
			if (comentarioPropio.isPresent()) {

				model.addAttribute("comentario", comentarioPropio.get());
			}

			List<Comments> comentarios = comentRepo.findCommentsByIdTrainer(idEntrenadorCookie);
			// Show rate
			if (!comentarios.isEmpty()) {

				Integer sumTotalPuntuaciones = 0;
				for (Comments puntComent : comentarios) {

					sumTotalPuntuaciones += Integer.valueOf(puntComent.getPuntuacion());
				}

				Double mediaPuntuaciones = (double) (sumTotalPuntuaciones / comentarios.size());
				model.addAttribute("mediaPuntuacion", mediaPuntuaciones.toString());
			} else {

				model.addAttribute("mediaPuntuacion", "Aun sin puntuación");
			}
			// Show rest of comments
			List<Comments> comentariosDist = new ArrayList<>();
			for (Comments comentarioDist : comentarios) {

				if (!comentarioDist.getIdUsuario().equals(idUsuario)) {
					comentariosDist.add(comentarioDist);
				}
			}
			model.addAttribute("comentarios", comentariosDist);
			redirectAttrs.addFlashAttribute("mensaje", "Comentario añadido correctamente").addFlashAttribute("clase",
					"success");
			return "infoEntrenador";
		} else {

			String idEntrenador = request.getParameter("idEntrenador");
			model.addAttribute("entrenador", entrenRepo.findById(idEntrenador));
			Optional<Trainers> entrenador = entrenRepo.findById(idEntrenador);
			Optional<Users> usuario = usuarioRepo.findByEmail(request.getUserPrincipal().getName());
			String idUsuario = usuario.get().getIdUsuario();
			// if its me i cant comment
			if (entrenador.isPresent()) {

				if (entrenador.get().getIdUsuario().equals(idUsuario)) {

					model.addAttribute("ocultar", "d-none");
				}
			}
			// Show own comment
			Optional<Comments> comentarioPropio = comentRepo.checkIfCommentExistOnTrainer(idUsuario,
					idEntrenador);
			if (comentarioPropio.isPresent()) {

				model.addAttribute("comentario", comentarioPropio.get());
			}

			List<Comments> comentarios = comentRepo.findCommentsByIdTrainer(idEntrenador);
			// Show rate
			if (!comentarios.isEmpty()) {

				Integer sumTotalPuntuaciones = 0;
				for (Comments puntComent : comentarios) {

					sumTotalPuntuaciones += Integer.valueOf(puntComent.getPuntuacion());
				}

				Double mediaPuntuaciones = (double) (sumTotalPuntuaciones / comentarios.size());
				model.addAttribute("mediaPuntuacion", mediaPuntuaciones.toString());
			} else {

				model.addAttribute("mediaPuntuacion", "Aun sin puntuación");
			}
			// Show rest of comments
			List<Comments> comentariosDist = new ArrayList<>();
			for (Comments comentarioDist : comentarios) {

				if (!comentarioDist.getIdUsuario().equals(idUsuario)) {
					comentariosDist.add(comentarioDist);
				}
			}
			model.addAttribute("comentarios", comentariosDist);
			redirectAttrs.addFlashAttribute("mensaje", "Comentario añadido correctamente").addFlashAttribute("clase",
					"success");
			return "infoEntrenador";
		}
	}

	
}
