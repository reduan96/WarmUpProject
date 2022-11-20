package com.proyecto.app.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.proyecto.app.entities.Usuarios;

@Repository
public interface UsuariosRepository extends CrudRepository<Usuarios, Long>{

	@Query(value = "select * from usuarios where email = :email and clave = :clave", nativeQuery = true)
	Optional<Usuarios> checkUser(@Param("email") String email, @Param("clave") String clave);
	
	@Query(value = "select * from usuarios where email = :email", nativeQuery = true)
	Optional<Usuarios> checkMail(@Param("email") String email);
	
	Optional<Usuarios> findByEmail(String email);
	
	Optional<Usuarios> findByidUsuario(String idUsuario);
}
