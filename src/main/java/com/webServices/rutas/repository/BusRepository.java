package com.webServices.rutas.repository;

import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

import com.webServices.rutas.model.Buses;

@ViewIndexed(designDoc = "buses", viewName = "all")
public interface BusRepository extends CouchbaseRepository<Buses, String> {
	Iterable<Buses> findByEstadoIsTrue();
	Iterable<Buses> findByIdCooperativaAndEstadoIsTrue(String idC);
}