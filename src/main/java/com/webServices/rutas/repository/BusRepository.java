package com.webServices.rutas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.couchbase.core.query.Query;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.repository.query.Param;

import com.webServices.rutas.model.Bus;

/**
 * Repositorio Especifico para Buses
 * @author Davids
 */
@ViewIndexed(designDoc = "bus", viewName = "all")
public interface BusRepository extends CouchbaseRepository<Bus, String>{
	Optional<Bus> findByPlacaAndEstadoIsTrue(String id);
	Optional<Bus> findByPlaca(String id);
	Optional<List<Bus>> findByEstadoIsTrue();
	Optional<List<Bus>> findByIdCooperativaAndEstadoIsTrue(String idC);
	@Query("SELECT CASE WHEN count(c)> 0 THEN true ELSE false END "
			+ "FROM #{#n1ql.bucket} as c "
			+ "WHERE lower(c.placa) = lower('#{#nombre}') AND c.estado=true AND c.#{#n1ql.filter}")
	boolean existsByPlacaAndEstadoIsTrue(@Param("nombre") String placa);
}