package com.proyecto.app.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.proyecto.app.model.Comentarios;

public interface ComentarioRepository extends MongoRepository<Comentarios, String>  {

	@Query("{idRutina:'?0'}")
	List<Comentarios> findCommentsByIdRoutine(String idRutina);
	
	@Query("{$and:[{idUsuario:'?0'},{idRutina:'?1'}]}")
	Optional<Comentarios> checkIfCommentExistOnRoutine(String idUsuario, String idRutina);
	
}
