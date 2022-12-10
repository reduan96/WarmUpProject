package com.proyecto.app.services;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.proyecto.app.Repositories.ComentarioRepository;
import com.proyecto.app.Repositories.RutinaRepository;
import com.proyecto.app.Repositories.UsuarioRepository;
import com.proyecto.app.model.Comentarios;
import com.proyecto.app.model.Rutinas;
import com.proyecto.app.model.Usuarios;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class warmUpServices {

	@Autowired
	private UsuarioRepository usuariosRepo;

	@Autowired
	private RutinaRepository rutinasRepo;
	
	@Autowired
	private ComentarioRepository comentRepo;

	// Function to check user if it is present on DB via email and password
	public boolean checkUser(String email, String clave) {

		Optional<Usuarios> usuario = usuariosRepo.checkUser(email, clave);

		if (usuario.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	// Function to check user if it is present on DB via Id User
	public boolean checkUser(String idUsuario) {

		Optional<Usuarios> usuario = usuariosRepo.findById(idUsuario);

		if (usuario.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	// Function to check if the email is present on DB
	public boolean checkEmail(String email) {

		Optional<Usuarios> usuario = usuariosRepo.findByEmail(email);

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

		Usuarios usuario = new Usuarios(nombre, apellidos, email, claveCifrada, ts, null);

		usuariosRepo.save(usuario);
	}

	/*
	 * Routines' functions
	 */

	// Function to save the routine's user
	public void registerRoutine(String idUsuario, String nombre, String descripcion, String lunes, String martes,
			String miercoles, String jueves, String viernes, String sabado, String domingo) {

		Timestamp ts = Timestamp.from(Instant.now());
		Rutinas rutina = new Rutinas(idUsuario, nombre, descripcion, lunes, martes, miercoles, jueves, viernes, sabado,
				domingo, ts, null);

		rutinasRepo.save(rutina);
	}
	
	public void editRoutine(String idRutina, String nombre, String descripcion, String lunes, String martes,
			String miercoles, String jueves, String viernes, String sabado, String domingo) {
		
		Optional<Rutinas> rutina = rutinasRepo.findById(idRutina);
		
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
			rutinasRepo.save(rutina.get());
		}
		
	}
	
	public void addCommentRoutine(String idUsuario, String idRutina, 
			String puntuacion, String comentario) {
		
		Comentarios comentarioObj = new Comentarios(idRutina, null, idUsuario, puntuacion, comentario);
		
		comentRepo.save(comentarioObj);
	}

}
