package com.proyecto.app.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.proyecto.app.Repositories.UsersRepository;
import com.proyecto.app.classes.CustomUserDetails;
import com.proyecto.app.model.Users;

public class UserService implements UserDetailsService {

	@Autowired
	private UsersRepository usuarioRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Users> user = usuarioRepo.findByEmail(email);

		if (user.isEmpty()) {
			throw new UsernameNotFoundException("User not found");
		}
		return new CustomUserDetails(user.get());
	}

}
