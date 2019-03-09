package com.webServices.rutas.repository;

import java.util.Date;

import org.springframework.data.couchbase.core.query.Query;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

import com.webServices.rutas.model.Bus;
import com.webServices.rutas.model.EstadoBus;

/**
 * Repositorio Especifico para Buses
 * @author Davids
 */
@ViewIndexed(designDoc = "buses", viewName = "all")
public interface BusRepository extends CouchbaseRepository<Bus, String> {
	Bus findByPlacaAndEstadoIsTrue(String id);
	Iterable<Bus> findByEstadoIsTrue();
	Iterable<Bus> findByIdCooperativaAndEstadoIsTrue(String idC);
}