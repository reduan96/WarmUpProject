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
import com.proyecto.app.Repositories.RoutinesRepository;
import com.proyecto.app.Repositories.UsersRepository;
import com.proyecto.app.model.Comments;
import com.proyecto.app.model.Routines;
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

		Timestamp ts = Timestamp.from(Instant.now());

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String claveCifrada = passwordEncoder.encode(clave);

		Users usuario = new Users(nombre, apellidos, email, claveCifrada, ts, null);

		usuarioRepo.save(usuario);
	}

	// Function to save the routine's user
	public void registerRoutine(String idUsuario, String nombre, String descripcion, String lunes, String martes,
			String miercoles, String jueves, String viernes, String sabado, String domingo) {

		Timestamp ts = Timestamp.from(Instant.now());
		Routines rutina = new Routines(idUsuario, nombre, descripcion, lunes, martes, miercoles, jueves, viernes, sabado,
				domingo, ts, null);

		rutinaRepo.save(rutina);
	}
	
	// Function to edit routine's data
	public void editRoutine(String idRutina, String nombre, String descripcion, String lunes, String martes,
			String miercoles, String jueves, String viernes, String sabado, String domingo) {
		
		Optional<Routines> rutina = rutinaRepo.findById(idRutina);
		
		if(rutina.isEmpty()) {
			
			log.info("Rutina a editar no encontrada");
		}else {
			
			Timestamp ts = Timestamp.from(Instant.now());
			rutina.get().setNombre(nombre);
			rutina.get().setDescripcion(descripcion);
			rutina.get().setLunes(lunes);
			rutina.get().setMartes(martes);
			rutina.get().setMiercoles(miercoles);
			rutina.get().setJueves(jueves);
			rutina.get().setViernes(viernes);
			rutina.get().setSabado(sabado);
			rutina.get().setDomingo(domingo);
			rutina.get().setFechaSubida(ts);
			rutinaRepo.save(rutina.get());
		}
		
	}
	
	// Function to add routine's comment
	public void addCommentRoutine(String idUsuario, String idRutina, 
			String puntuacion, String comentario) {
		
		Comments comentarioObj = new Comments(idRutina, null, idUsuario, puntuacion, comentario);
		
		comentRepo.save(comentarioObj);
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
	
}
