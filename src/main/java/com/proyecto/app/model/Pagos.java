package com.proyecto.app.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document("Pagos")
@Data
@NoArgsConstructor
public class Pagos {

	@Id
	private String idPago;
	
	private String idEntrenador;
	private String tarifa;
	private Date fechaPago;
	
}
