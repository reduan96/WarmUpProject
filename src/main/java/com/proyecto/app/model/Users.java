package com.proyecto.app.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document("Usuarios")
@Data
@NoArgsConstructor
public class Users {

	@Id
	private String idUsuario;
	
	private String nombre;
	private String apellidos;
	private String email;
	private String clave;
	private LocalDate fechaAlta;
	
	public Users(String nombre, String apellidos, String email, String clave, LocalDate fechaAlta) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.clave = clave;
		this.fechaAlta = fechaAlta;
	}
	
}
