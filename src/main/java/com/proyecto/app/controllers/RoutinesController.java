package com.proyecto.app.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
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

import com.proyecto.app.Repositories.CommentsRepository;
import com.proyecto.app.Repositories.RoutinesRepository;
import com.proyecto.app.Repositories.UsersRepository;
import com.proyecto.app.classes.CommentForm;
import com.proyecto.app.classes.RoutineForm;
import com.proyecto.app.model.Comments;
import com.proyecto.app.model.Users;
import com.proyecto.app.services.WarmUpServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class RoutinesController {

	@Autowired
	private WarmUpServices wUpService;

	@Autowired
	private RoutinesRepository rutinasRepo;

	@Autowired
	private UsersRepository usuarioRepo;

	@Autowired
	private CommentsRepository comentRepo;

	private static final String REDIRECT = "redirect:";

	/*
	 * Routines controllers >>>
	 */

	// Routines controller
	@GetMapping("/rutinas")
	public String showRoutine(HttpServletRequest request, HttpServletResponse response, Model model,
			RedirectAttributes redirectAttrs) {

		wUpService.resetCookieIdRutina(request, response);
		if (!wUpService.checkIfOnlineUserStillExistsOnDb(request, redirectAttrs)) {

			return REDIRECT + "login";
		}

		model.addAttribute("rutinas", rutinasRepo.findAll());
		return "rutinas";
	}

	// Up Routines controller
	@GetMapping("/subirRutina")
	public String upRoutine(HttpServletRequest request, RedirectAttributes redirectAttrs) {

		if (!wUpService.checkIfOnlineUserStillExistsOnDb(request, redirectAttrs)) {

			return REDIRECT + "login";
		}

		return "subirRutina";
	}

	// Register routine's user and if we have
	// a routine, edit it
	@PostMapping("/altaRutina")
	public String registerRoutine(HttpServletRequest request, RoutineForm routineForm, Model model,
			RedirectAttributes redirectAttrs) {

		if (!wUpService.checkIfOnlineUserStillExistsOnDb(request, redirectAttrs)) {

			return REDIRECT + "login";
		}

		String idRutina = request.getParameter("idRutina");

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

			if (idRutina != null) {

				wUpService.editRoutine(idRutina, nombre, descripcion, lunes, martes, miercoles, jueves, viernes, sabado,
						domingo);
				redirectAttrs.addFlashAttribute("mensaje", "Rutina editada correctamente").addFlashAttribute("clase",
						"success");
				return REDIRECT + "/misRutinas";
			}

			wUpService.registerRoutine(idUsuario, nombre, descripcion, lunes, martes, miercoles, jueves, viernes,
					sabado, domingo);
			redirectAttrs.addFlashAttribute("mensaje", "Rutina añadida correctamente").addFlashAttribute("clase",
					"success");
			return REDIRECT + "/rutinas";
		} else {

			model.addAttribute("userBanned", true);
			return "login";
		}
	}

	// Info Routine, where we can see the routine
	// itself and the users comments
	@GetMapping("/infoRutina")
	public String infoRoutine(HttpServletRequest request, Model model,
			@CookieValue(name = "idRutina", defaultValue = "") String idRutinaCookie, RedirectAttributes redirectAttrs,
			HttpServletResponse response) {

		if (!wUpService.checkIfOnlineUserStillExistsOnDb(request, redirectAttrs)) {

			return REDIRECT + "login";
		}

		if (idRutinaCookie != null && !"".equals(idRutinaCookie)) {

			log.info("VALOR idRutinaCookie: " + idRutinaCookie);
			model.addAttribute("rutina", rutinasRepo.findById(idRutinaCookie));
			Optional<Users> usuario = usuarioRepo.findByEmail(request.getUserPrincipal().getName());
			String idUsuario = usuario.get().getIdUsuario();
			Optional<Comments> comentarioPropio = comentRepo.checkIfCommentExistOnRoutine(idUsuario, idRutinaCookie);
			if (comentarioPropio.isPresent()) {

				model.addAttribute("comentario", comentarioPropio.get());
			}

			List<Comments> comentarios = comentRepo.findCommentsByIdRoutine(idRutinaCookie);

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

			List<Comments> comentariosDist = new ArrayList<>();
			for (Comments comentarioDist : comentarios) {

				if (!comentarioDist.getIdUsuario().equals(idUsuario)) {
					comentariosDist.add(comentarioDist);
				}
			}
			model.addAttribute("comentarios", comentariosDist);
			redirectAttrs.addFlashAttribute("mensaje", "Comentario añadido correctamente").addFlashAttribute("clase",
					"success");
			return "infoRutina";
		} else {

			String idRutina = request.getParameter("idRutina");
			log.info("VALOR idRutina: " + idRutina);
			model.addAttribute("rutina", rutinasRepo.findById(idRutina));
			Optional<Users> usuario = usuarioRepo.findByEmail(request.getUserPrincipal().getName());
			String idUsuario = usuario.get().getIdUsuario();
			Optional<Comments> comentarioPropio = comentRepo.checkIfCommentExistOnRoutine(idUsuario, idRutina);
			if (comentarioPropio.isPresent()) {

				model.addAttribute("comentario", comentarioPropio.get());
			}
			List<Comments> comentarios = comentRepo.findCommentsByIdRoutine(idRutina);

			if (!comentarios.isEmpty()) {

				Integer sumTotalPuntuaciones = 0;
				for (Comments puntComent : comentarios) {

					sumTotalPuntuaciones += Integer.valueOf(puntComent.getPuntuacion());
				}

				Double mediaPuntuaciones = (double) (sumTotalPuntuaciones / comentarios.size());
				model.addAttribute("mediaPuntuacion", mediaPuntuaciones.toString());
			} else {

				model.addAttribute("mediaPuntuacion", "Se el primero !");
			}

			List<Comments> comentariosDist = new ArrayList<>();
			for (Comments comentarioDist : comentarios) {

				if (!comentarioDist.getIdUsuario().equals(idUsuario)) {
					comentariosDist.add(comentarioDist);
				}
			}
			model.addAttribute("comentarios", comentariosDist);
			return "infoRutina";
		}

	}

	// My routines
	@GetMapping("/misRutinas")
	public String myRoutines(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttrs,
			Model model, Principal principal) {

		if (!wUpService.checkIfOnlineUserStillExistsOnDb(request, redirectAttrs)) {

			return REDIRECT + "login";
		}

		String emailUsuario = principal.getName();
		Optional<Users> obj = usuarioRepo.findByEmail(emailUsuario);
		if (obj.isEmpty()) {
			log.info("El usuario no se haya en la base de datos");
			return null;
		}
		String idUsuario = obj.get().getIdUsuario();
		model.addAttribute("rutinas", rutinasRepo.findRoutinesByUserId(idUsuario));
		return "misRutinas";
	}

	// Edit Routines
	@GetMapping("/editarRutinas")
	public String editRoutine(HttpServletRequest request, Model model, RedirectAttributes redirectAttrs) {

		if (!wUpService.checkIfOnlineUserStillExistsOnDb(request, redirectAttrs)) {

			return REDIRECT + "login";
		}
		String idRutina = request.getParameter("idRutina");
		model.addAttribute("rutina", rutinasRepo.findById(idRutina));
		return "editarRutina";
	}

	// Delete Routine
	@GetMapping("/borrarRutina")
	public String deleteRoutine(HttpServletRequest request, RedirectAttributes redirectAttrs) {

		if (!wUpService.checkIfOnlineUserStillExistsOnDb(request, redirectAttrs)) {

			return REDIRECT + "login";
		}

		String idRutina = request.getParameter("idRutina");
		List<Comments> comentarios = comentRepo.findCommentsByIdRoutine(idRutina);
		for (Comments comentario : comentarios) {

			comentRepo.deleteById(comentario.getIdComentario());
		}

		rutinasRepo.deleteById(idRutina);
		redirectAttrs.addFlashAttribute("mensaje", "Rutina borrada correctamente").addFlashAttribute("clase", "danger");
		return REDIRECT + "/misRutinas";
	}

	// Add Routine comment
	@PostMapping("/aniadirComentarioRutina")
	public String addRoutineComment(HttpServletRequest request, HttpServletResponse response, CommentForm commentForm,
			Model model, RedirectAttributes redirectAttrs) {

		if (!wUpService.checkIfOnlineUserStillExistsOnDb(request, redirectAttrs)) {

			return REDIRECT + "login";
		}

		String idUsuario = commentForm.getIdUsuario();
		String idRutina = commentForm.getIdRutina();
		String puntuacion = commentForm.getPuntuacion();
		String comentario = commentForm.getComentario();

		if (idRutina == null) {

			return REDIRECT + "/infoRutina";
		}

		Optional<Comments> comentExistente = comentRepo.checkIfCommentExistOnRoutine(idUsuario, idRutina);

		if (comentExistente.isPresent()) {

			redirectAttrs.addFlashAttribute("mensaje", "Ya has comentado esta rutina").addFlashAttribute("clase",
					"danger");
			Cookie idRutinaCookie = new Cookie("idRutina", idRutina);
			idRutinaCookie.setPath("/");
			response.addCookie(idRutinaCookie);
			return REDIRECT + "/infoRutina";
		}

		log.info("idUsuario logueado: {}", idUsuario);

		boolean flag = wUpService.checkUser(idUsuario);

		if (flag) {

			wUpService.addCommentRoutine(idUsuario, idRutina, puntuacion, comentario);
			redirectAttrs.addFlashAttribute("mensaje", "Comentario añadido correctamente").addFlashAttribute("clase",
					"success");
			Cookie idRutinaCookie = new Cookie("idRutina", idRutina);
			idRutinaCookie.setPath("/");
			response.addCookie(idRutinaCookie);
			return REDIRECT + "/infoRutina";
		} else {

			model.addAttribute("userBanned", true);
			return "login";
		}

	}

	// Delete Routine comment
	@GetMapping("/borrarComentario")
	public String deleteRoutineComment(HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttrs) {

		if (!wUpService.checkIfOnlineUserStillExistsOnDb(request, redirectAttrs)) {

			return REDIRECT + "login";
		}
		Optional<Users> usuario = usuarioRepo.findByEmail(request.getUserPrincipal().getName());
		String idUsuario = usuario.get().getIdUsuario();
		String idRutina = request.getParameter("idRutina");
		Optional<Comments> comentario = comentRepo.checkIfCommentExistOnRoutine(idUsuario, idRutina);
		if (comentario.isPresent()) {
			comentRepo.deleteById(comentario.get().getIdComentario());
		}

		Cookie idRutinaCookie = new Cookie("idRutina", idRutina);
		idRutinaCookie.setPath("/");
		response.addCookie(idRutinaCookie);
		redirectAttrs.addFlashAttribute("mensaje", "Comentario borrado correctamente").addFlashAttribute("clase",
				"danger");
		return REDIRECT + "/infoRutina";
	}

}
