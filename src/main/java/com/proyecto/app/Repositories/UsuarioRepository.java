package com.proyecto.app.Repositories;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import com.proyecto.app.model.Usuarios;

public interface UsuarioRepository extends MongoRepository<Usuarios, String> {
	
	@Query("{$and:[{email:'?0'},{clave:'?1'}]}")
	Optional<Usuarios> checkUser(String email, String clave);
	
	//@Query("{email:'?0'}")
	//Optional<Usuarios> checkEmail(String email);
	
	//@Query("{email:'?0'}")
	Optional<Usuarios> findByEmail(String email);
	
	//@Query("{id:?0}")
	Optional<Usuarios> findById(String id);
	
}
