package com.proyecto.app.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.proyecto.app.model.Payments;

public interface PaymentsRepository extends MongoRepository<Payments, String> {
	
}