package com.proyecto.app.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.proyecto.app.model.Pagos;

public interface PagoRepository extends MongoRepository<Pagos, String> {
	
}