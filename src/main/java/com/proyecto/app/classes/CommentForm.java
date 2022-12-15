package com.proyecto.app.classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentForm {

	private String idUsuario;
	private String idRutina;
	private String idEntrenador;
	private String idComentario;
	private String puntuacion;
	private String comentario;
}
