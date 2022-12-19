package com.proyecto.app.Repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import com.proyecto.app.model.Routines;

public interface RoutinesRepository extends MongoRepository<Routines, String> {
	
	@Query("{nombre:'?0'}")
	Optional<Routines> findByName(String nombre);
	
	@Query("{idUsuario:'?0'}")
	List<Routines> findRoutinesByUserId(String idUsuario);
	
}