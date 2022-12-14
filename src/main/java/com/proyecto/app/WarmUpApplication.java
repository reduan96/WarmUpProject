package com.proyecto.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.client.RestTemplate;

import com.proyecto.app.Repositories.UsersRepository;

@EnableMongoRepositories
@SpringBootApplication(scanBasePackages = "com.proyecto")
public class WarmUpApplication {

	@Autowired
	UsersRepository usuarioRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(WarmUpApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
}
