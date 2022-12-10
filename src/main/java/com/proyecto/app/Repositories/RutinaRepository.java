package com.proyecto.app.Repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import com.proyecto.app.model.Rutinas;

public interface RutinaRepository extends MongoRepository<Rutinas, String> {
	
	@Query("{nombre:'?0'}")
	Optional<Rutinas> findByName(String nombre);
	
	//@Query("{fechaSubida:'-1'}")
	//Optional<Rutinas> orderRecents();
	
	@Query("{idUsuario:'?0'}")
	List<Rutinas> findRoutinesByUserId(String idUsuario);
	
	//@Query(value="{id:'?0'}", delete = true)
	//void deleteRoutinesById(String id);
	
}