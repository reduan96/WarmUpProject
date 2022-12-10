package com.proyecto.app.classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutineForm {

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
	
}
