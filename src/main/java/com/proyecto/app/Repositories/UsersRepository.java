package com.proyecto.app.Repositories;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import com.proyecto.app.model.Users;

public interface UsersRepository extends MongoRepository<Users, String> {
	
	@Query("{$and:[{email:'?0'},{clave:'?1'}]}")
	Optional<Users> checkUser(String email, String clave);
	
	//@Query("{email:'?0'}")
	Optional<Users> findByEmail(String email);
	
	//@Query("{id:?0}")
	Optional<Users> findById(String id);
	
}
