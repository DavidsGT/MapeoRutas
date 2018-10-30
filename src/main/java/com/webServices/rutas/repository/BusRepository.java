package com.webServices.rutas.repository;

import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

import com.webServices.rutas.model.Bus;

/**
 * Repositorio Especifico para Buses
 * @author Davids
 */
@ViewIndexed(designDoc = "buses", viewName = "all")
public interface BusRepository extends CouchbaseRepository<Bus, String> {
	Bus findByIdAndEstadoIsTrue(String id);
	Iterable<Bus> findByEstadoIsTrue();
	Iterable<Bus> findByIdCooperativaAndEstadoIsTrue(String idC);
}