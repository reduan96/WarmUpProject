package com.proyecto.app.services;

import java.sql.Timestamp;
import java.time.Instant;
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

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String claveCifrada = passwordEncoder.encode(clave);

		Users usuario = new Users(nombre, apellidos, email, claveCifrada);

		usuarioRepo.save(usuario);
	}

	// Function to save the routine's user
	public void registerRoutine(String idUsuario, String nombre, String descripcion, String lunes, String martes,
			String miercoles, String jueves, String viernes, String sabado, String domingo) {

		Routines rutina = new Routines(idUsuario, nombre, descripcion, lunes, martes, miercoles, jueves, viernes, sabado,
				domingo);

		rutinaRepo.save(rutina);
	}
	
	// Function to edit routine's data
	public void editRoutine(String idRutina, String nombre, String descripcion, String lunes, String martes,
			String miercoles, String jueves, String viernes, String sabado, String domingo) {
		
		Optional<Routines> rutina = rutinaRepo.findById(idRutina);
		
		if(rutina.isEmpty()) {
			
			log.info("Rutina a editar no encontrada");
		}else {
			
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
	public void addCommentRoutine(String idUsuario, String idRutina, 
			String idEntrenador, String puntuacion, String comentario) {
		
		if(idEntrenador == null) {
			
			Comments comentarioObj = new Comments(idRutina, null, idUsuario, puntuacion, comentario);
			comentRepo.save(comentarioObj);
		}else {
			
			Comments comentarioObj = new Comments(null, idEntrenador, idUsuario, puntuacion, comentario);
			comentRepo.save(comentarioObj);
		}
		
	}
	
	// Function to see before everything if the user still exists
	public boolean checkIfOnlineUserStillExistsOnDb(HttpServletRequest request, RedirectAttributes redirectAttrs) {
		
		Optional<Users> usuario = usuarioRepo.findByEmail(request.getUserPrincipal().getName());
		if(usuario.isEmpty()) {
			
			redirectAttrs.addFlashAttribute("mensaje", "Usuario inexistente en BD, contacte con soporte").addFlashAttribute("clase",
					"danger");
			return false;
		}else {
			
			return true;
		}
	}

	// Function to reset idRutina Cookie
	public void resetCookieIdRutina(HttpServletRequest request, HttpServletResponse response) {
		
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			
			if(cookie.getName().equals("idRutina")) {
				
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
		}
		//Cookie cookie = new Cookie("idRutina", null);
		//cookie.setPath("/");
		//cookie.setMaxAge(0);
		//response.addCookie(cookie);
	}
	
	// Function to save Trainer
	public Trainers saveTrainer(String idUsuario, String nombre, String apellidos, String email, String clave) {
		
		Trainers entrenador = new Trainers(idUsuario, nombre, apellidos, 
				email, clave, null);
		entrenRepo.save(entrenador);
		
		return entrenador;
	}
	
	// Function to save Payment
	public void savePayment(String idEntrenador, String tarif) {
		
		Timestamp ts = Timestamp.from(Instant.now());
		Payments pago = new Payments(idEntrenador, tarif, ts);
		pagoRepo.save(pago);
		
	}
	
	public Boolean checkIfTrainerExist(String emailUsuario) {
		
		Optional<Users> usuario = usuarioRepo.findByEmail(emailUsuario);
		if(usuario.isEmpty()) {
			return null;
		}
		
		Optional<Trainers> entrenador = entrenRepo.findTrainersByUserId(usuario.get().getIdUsuario());
		if(entrenador.isEmpty()) {
			return false;
		}else {
			return true;
		}
		
	}
	
}