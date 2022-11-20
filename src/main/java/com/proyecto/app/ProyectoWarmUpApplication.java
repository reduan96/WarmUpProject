package com.proyecto.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.proyecto")
public class ProyectoWarmUpApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoWarmUpApplication.class, args);
	}

}
