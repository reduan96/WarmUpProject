package com.proyecto.app.Repositories;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.app.entities.Rutinas;

@Repository
public interface RutinasRepository extends CrudRepository<Rutinas, Long>{

	@Query(value = "select * from rutinas order by fecha_subida desc", nativeQuery = true)
	Optional<Rutinas> orderRecents();
}
