package com.proyecto.app.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document("Entrenadores")
@Data
@NoArgsConstructor
public class Trainers {

	@Id
	private String idEntrenador;
	
	private String idUsuario;
	private String nombre;
	private String apellidos;
	private String email;
	private String clave;
	private String descripcion;
	private LocalDate fechaAlta;
	
	public Trainers(String idUsuario, String nombre, String apellidos, String email, String clave, String descripcion,
			LocalDate fechaAlta) {
		super();
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.clave = clave;
		this.descripcion = descripcion;
		this.fechaAlta = fechaAlta;
	}
	
}
