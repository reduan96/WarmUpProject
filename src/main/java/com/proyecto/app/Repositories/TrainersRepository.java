package com.proyecto.app.Repositories;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import com.proyecto.app.model.Trainers;

public interface TrainersRepository extends MongoRepository<Trainers, String> {
	
	@Query("{idUsuario:'?0'}")
	Optional<Trainers> findTrainersByUserId(String idUsuario);
	
}