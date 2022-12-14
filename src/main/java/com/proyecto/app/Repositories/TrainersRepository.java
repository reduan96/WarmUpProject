package com.proyecto.app.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.proyecto.app.model.Trainers;

public interface TrainersRepository extends MongoRepository<Trainers, String> {
	
}