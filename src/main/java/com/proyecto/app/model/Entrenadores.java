package com.proyecto.app.model;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document("Entrenadores")
@Data
@NoArgsConstructor
public class Entrenadores {

	@Id
	private String idEntrenador;
	
	private String idUsuario;
	private String nombre;
	private String apellidos;
	private String email;
	private String clave;
	private String descripcion;
	private ArrayList<Comentarios> comentarios;
	private ArrayList<Pagos> pagosRealizados;
	private Date fechaAlta;
	private Date fechaBaja;
	
	
	public Entrenadores(String idUsuario, String nombre, String apellidos, String email,
			String clave, String descripcion, Date fechaAlta, Date fechaBaja) {
		super();
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.clave = clave;
		this.descripcion = descripcion;
		this.fechaAlta = fechaAlta;
		this.fechaBaja = fechaBaja;
	}
	
}
