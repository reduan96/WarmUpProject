package com.proyecto.app.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.proyecto.app.model.Comments;

public interface CommentsRepository extends MongoRepository<Comments, String>  {

	@Query("{idRutina:'?0'}")
	List<Comments> findCommentsByIdRoutine(String idRutina);
	
	@Query("{idEntrenador:'?0'}")
	List<Comments> findCommentsByIdTrainer(String idEntrenador);
	
	@Query("{$and:[{idUsuario:'?0'},{idRutina:'?1'}]}")
	Optional<Comments> checkIfCommentExistOnRoutine(String idUsuario, String idRutina);
	
	@Query("{$and:[{idUsuario:'?0'},{idEntrenador:'?1'}]}")
	Optional<Comments> checkIfCommentExistOnTrainer(String idUsuario, String idEntrenador);
	
}
