package com.proyecto.app.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document("Rutinas")
@Data
@NoArgsConstructor
public class Routines {

	@Id
	private String idRutina;
	
	private String idUsuario;
	private String nombre;
	private String descripcion;
	private String lunes;
	private String martes;
	private String miercoles;
	private String jueves;
	private String viernes;
	private String sabado;
	private String domingo;
	private LocalDate fechaSubida;
	
	public Routines(String idUsuario, String nombre, String descripcion, String lunes, String martes, String miercoles,
			String jueves, String viernes, String sabado, String domingo, LocalDate fechaSubida) {
		super();
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.lunes = lunes;
		this.martes = martes;
		this.miercoles = miercoles;
		this.jueves = jueves;
		this.viernes = viernes;
		this.sabado = sabado;
		this.domingo = domingo;
		this.fechaSubida = fechaSubida;
	}
	
}
