package com.proyecto.app.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.proyecto.app.Repositories.UsuariosRepository;
import com.proyecto.app.classes.CustomUserDetails;
import com.proyecto.app.entities.Usuarios;

public class UserService implements UserDetailsService {

	@Autowired
	private UsuariosRepository usuarioRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Usuarios> user = usuarioRepo.findByEmail(email);

		if (user.isEmpty()) {
			throw new UsernameNotFoundException("User not found");
		}
		return new CustomUserDetails(user.get());
	}

}
