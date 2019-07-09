package com.webServices.rutas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

import com.webServices.rutas.model.Bus;

/**
 * Repositorio Especifico para Buses
 * @author Davids
 */
@ViewIndexed(designDoc = "bus", viewName = "all")
public interface BusRepository extends CouchbaseRepository<Bus, String>{
	Optional<Bus> findByPlacaAndEstadoIsTrue(String id);
	Optional<List<Bus>> findByEstadoIsTrue();
	Optional<List<Bus>> findByIdCooperativaAndEstadoIsTrue(String idC);
}