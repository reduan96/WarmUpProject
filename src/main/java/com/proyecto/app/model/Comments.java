package com.proyecto.app.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("Comentarios")
@Data
@NoArgsConstructor
public class Comments {

	//It can have or idRutina or idEntrenador
	//never both of them
	
	@Id
	private String idComentario;
	
	private String idRutina;
	private String idEntrenador;
	private String idUsuario;
	private String puntuacion;
	private String comentario;
	private LocalDate fechaSubida;
	
	public Comments(String idRutina, String idEntrenador, String idUsuario, String puntuacion, String comentario,
			LocalDate fechaSubida) {
		super();
		this.idRutina = idRutina;
		this.idEntrenador = idEntrenador;
		this.idUsuario = idUsuario;
		this.puntuacion = puntuacion;
		this.comentario = comentario;
		this.fechaSubida = fechaSubida;
	}
	
}
