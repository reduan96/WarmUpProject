package com.proyecto.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.proyecto.app.Repositories.UsuarioRepository;

@EnableMongoRepositories
@SpringBootApplication(scanBasePackages = "com.proyecto")
public class ProyectoWarmUpApplication {

	@Autowired
	UsuarioRepository usuarioRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(ProyectoWarmUpApplication.class, args);
	}

}
