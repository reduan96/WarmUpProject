package com.proyecto.app.services;
import java.time.LocalDate;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.proyecto.app.Repositories.CommentsRepository;
import com.proyecto.app.Repositories.PaymentsRepository;
import com.proyecto.app.Repositories.RoutinesRepository;
import com.proyecto.app.Repositories.TrainersRepository;
import com.proyecto.app.Repositories.UsersRepository;
import com.proyecto.app.model.Comments;
import com.proyecto.app.model.Payments;
import com.proyecto.app.model.Routines;
import com.proyecto.app.model.Trainers;
import com.proyecto.app.model.Users;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WarmUpServices {

	@Autowired
	private UsersRepository usuarioRepo;

	@Autowired
	private RoutinesRepository rutinaRepo;

	@Autowired
	private CommentsRepository comentRepo;

	@Autowired
	private TrainersRepository entrenRepo;

	@Autowired
	private PaymentsRepository pagoRepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	// Function to check user if it is present on DB via email and password
	public boolean checkUser(String email, String clave) {

		Optional<Users> usuario = usuarioRepo.checkUser(email, clave);

		if (usuario.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	// Function to check user if it is present on DB via Id User
	public boolean checkUser(String idUsuario) {

		Optional<Users> usuario = usuarioRepo.findById(idUsuario);

		if (usuario.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	// Function to check if the email is present on DB
	public boolean checkEmail(String email) {

		Optional<Users> usuario = usuarioRepo.findByEmail(email);

		if (usuario.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	// Function to save the user´s data and create him
	public void saveUser(String nombre, String apellidos, String email, String clave) {

		LocalDate ts = LocalDate.now();
		String claveCifrada = passwordEncoder.encode(clave);
		Users usuario = new Users(nombre, apellidos, email, claveCifrada, ts);

		usuarioRepo.save(usuario);
	}

	// Function to save the routine's user
	public void registerRoutine(String idUsuario, String nombre, String descripcion, String lunes, String martes,
			String miercoles, String jueves, String viernes, String sabado, String domingo) {

		LocalDate ts = LocalDate.now();
		Routines rutina = new Routines(idUsuario, nombre, descripcion, lunes, martes, miercoles, jueves, viernes,
				sabado, domingo, ts);

		rutinaRepo.save(rutina);
	}

	// Function to edit routine's data
	public void editRoutine(String idRutina, String nombre, String descripcion, String lunes, String martes,
			String miercoles, String jueves, String viernes, String sabado, String domingo) {

		Optional<Routines> rutina = rutinaRepo.findById(idRutina);

		if (rutina.isEmpty()) {

			log.info("Rutina a editar no encontrada");
		} else {

			rutina.get().setNombre(nombre);
			rutina.get().setDescripcion(descripcion);
			rutina.get().setLunes(lunes);
			rutina.get().setMartes(martes);
			rutina.get().setMiercoles(miercoles);
			rutina.get().setJueves(jueves);
			rutina.get().setViernes(viernes);
			rutina.get().setSabado(sabado);
			rutina.get().setDomingo(domingo);
			rutinaRepo.save(rutina.get());
		}

	}

	// Function to add routine's comment
	public void addCommentRoutine(String idUsuario, String idRutina, String idEntrenador, String puntuacion,
			String comentario) {

		LocalDate ts = LocalDate.now();
		
		if (idEntrenador == null) {

			Comments comentarioObj = new Comments(idRutina, null, idUsuario, puntuacion, comentario, ts);
			comentRepo.save(comentarioObj);
		} else {

			Comments comentarioObj = new Comments(null, idEntrenador, idUsuario, puntuacion, comentario, ts);
			comentRepo.save(comentarioObj);
		}

	}

	// Function to see before everything if the user still exists
	public boolean checkIfOnlineUserStillExistsOnDb(HttpServletRequest request, RedirectAttributes redirectAttrs) {

		Optional<Users> usuario = usuarioRepo.findByEmail(request.getUserPrincipal().getName());
		if (usuario.isEmpty()) {

			redirectAttrs.addFlashAttribute("mensaje", "Usuario inexistente en BD, contacte con soporte")
					.addFlashAttribute("clase", "danger");
			return false;
		} else {

			return true;
		}
	}

	// Function to reset idRutina Cookie
	public void resetCookie(HttpServletRequest request, HttpServletResponse response, String tipo) {

		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {

			if (tipo.equals("idRutina")) {

				if (cookie.getName().equals("idRutina")) {

					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
			}
			
			if(tipo.equals("idEntrenador")){

				if (cookie.getName().equals("idEntrenador")) {

					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
			}
			
		}
	}

	// Function to save Trainer
	public Trainers saveTrainer(String idUsuario, String nombre, String apellidos, String email, String clave) {
		
		LocalDate ts = LocalDate.now();
		Trainers entrenador = new Trainers(idUsuario, nombre, apellidos, email, clave, null, ts);
		entrenRepo.save(entrenador);

		return entrenador;
	}

	// Function to save Payment
	public void savePayment(String idEntrenador, String tarif) {

		LocalDate ts = LocalDate.now();
		Payments pago = new Payments(idEntrenador, tarif, ts);
		pagoRepo.save(pago);

	}

	// Function to see if trainer exists
	public Boolean checkIfTrainerExist(String emailUsuario) {

		Optional<Users> usuario = usuarioRepo.findByEmail(emailUsuario);
		if (usuario.isEmpty()) {
			return null;
		}

		Optional<Trainers> entrenador = entrenRepo.findTrainersByUserId(usuario.get().getIdUsuario());
		if (entrenador.isEmpty()) {
			return false;
		} else {
			return true;
		}

	}

	// Function to save data user profile
	public void editUserDataProfile(String nombre, String apellidos, String clave, String descripcion,
			Users usuario) {

		if (descripcion == null || descripcion.equals("")) {

			if (clave == null || clave.equals("")) {

				usuario.setNombre(nombre);
				usuario.setApellidos(apellidos);
				usuarioRepo.save(usuario);
			} else {

				String claveCifrada = passwordEncoder.encode(clave);
				usuario.setNombre(nombre);
				usuario.setApellidos(apellidos);
				usuario.setClave(claveCifrada);
				usuarioRepo.save(usuario);
			}
		} else {

			Optional<Trainers> entrenador = entrenRepo.findTrainersByUserId(usuario.getIdUsuario());
			
			if(entrenador.isPresent()) {
				
				if (clave == null || clave.equals("")) {

					usuario.setNombre(nombre);
					usuario.setApellidos(apellidos);
					entrenador.get().setNombre(nombre);
					entrenador.get().setApellidos(apellidos);
					entrenador.get().setDescripcion(descripcion);
					entrenRepo.save(entrenador.get());
					usuarioRepo.save(usuario);
				} else {

					String claveCifrada = passwordEncoder.encode(clave);
					usuario.setNombre(nombre);
					usuario.setApellidos(apellidos);
					usuario.setClave(claveCifrada);
					entrenador.get().setNombre(nombre);
					entrenador.get().setApellidos(apellidos);
					entrenador.get().setClave(claveCifrada);
					entrenador.get().setDescripcion(descripcion);
					entrenRepo.save(entrenador.get());
					usuarioRepo.save(usuario);
				}
			}
		
		}
	}

}