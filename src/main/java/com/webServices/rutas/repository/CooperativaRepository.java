package com.webServices.rutas.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.couchbase.core.query.Query;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.repository.query.Param;

import com.webServices.rutas.model.Cooperativa;

@ViewIndexed(designDoc = "cooperativa", viewName = "all")
public interface CooperativaRepository extends CouchbaseRepository<Cooperativa, String>{
	Optional<Cooperativa> findByNombreAndEstadoIsTrue(String nombre);
	Optional<List<Cooperativa>> findByEstadoIsTrue();
	Optional<Cooperativa> findByIdAndEstadoIsTrue(String id);
	@Query("SELECT CASE WHEN count(c)> 0 THEN true ELSE false END "
			+ "FROM #{#n1ql.bucket} as c "
			+ "WHERE lower(c.nombre) = lower('#{#nombre}') AND c.estado=true AND c.#{#n1ql.filter}")
	boolean existsByNombreAndEstadoIsTrue(@Param("nombre") String nombre);
}