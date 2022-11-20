package com.proyecto.app.services;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.proyecto.app.Repositories.RutinaRepository;
import com.proyecto.app.Repositories.UsuarioRepository;
import com.proyecto.app.model.Rutinas;
import com.proyecto.app.model.Usuarios;

@Service
public class warmUpServices {

	@Autowired
	private UsuarioRepository usuariosRepo;

	@Autowired
	private RutinaRepository rutinasRepo;

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

		Optional<Usuarios> usuario = usuariosRepo.checkEmail(email);

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

}
