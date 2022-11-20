package com.proyecto.app.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "rutinas")
public class Rutinas {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_rutina")
	private Long idRutina;
	
	@Column(name = "id_usuario")
	private Long idUsuario;
	
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "descripcion")
	private String descripcion;
	@Column(name = "lunes")
	private String lunes;
	@Column(name = "martes")
	private String martes;
	@Column(name = "miercoles")
	private String miercoles;
	@Column(name = "jueves")
	private String jueves;
	@Column(name = "viernes")
	private String viernes;
	@Column(name = "sabado")
	private String sabado;
	@Column(name = "domingo")
	private String domingo;
	@Column(name = "fecha_subida")
	private Timestamp fechaSubida;
	@Column(name = "fecha_elim")
	private Timestamp fechaElim;
	
	public Rutinas(Long idUsuario, String nombre, String descripcion, String lunes, String martes, String miercoles,
			String jueves, String viernes, String sabado, String domingo, Timestamp fechaSubida, Timestamp fechaElim) {
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
		this.fechaElim = fechaElim;
	}
	
}
