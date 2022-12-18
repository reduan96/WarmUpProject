package com.proyecto.app.classes;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EditProfileForm {

	private String nombre;
	private String apellidos;
	private String claveNueva;
	private String descripcion;
	
}
