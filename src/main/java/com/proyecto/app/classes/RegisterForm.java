package com.proyecto.app.classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterForm {

	private String nombre;
	private String apellidos;
	private String email;
	private String clave;
	private String repClave;
	
}
