package com.proyecto.app.entities;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "usuarios", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Usuarios {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
	private Long idUsuario;

	@Column(name = "nombre")
	private String nombre;
	@Column(name = "apellidos")
	private String apellidos;
	@Column(name = "email")
	private String email;
	@Column(name = "clave")
	private String clave;
	@Column(name = "fecha_alta")
	private Timestamp fechaAlta;
	@Column(name = "fecha_baja")
	private Timestamp fechaBaja;
	
	public Usuarios(String nombre, String apellidos, String email, String clave, Timestamp fechaAlta,
			Timestamp fechaBaja) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.clave = clave;
		this.fechaAlta = fechaAlta;
		this.fechaBaja = fechaBaja;
	}
	
}
