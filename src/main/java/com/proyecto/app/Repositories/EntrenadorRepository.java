package com.proyecto.app.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.proyecto.app.model.Entrenadores;

public interface EntrenadorRepository extends MongoRepository<Entrenadores, String> {
	
}