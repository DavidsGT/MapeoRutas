package com.webServices.rutas.repository;

import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

import com.webServices.rutas.model.Cooperativa;

@ViewIndexed(designDoc = "cooperativa", viewName = "all")
public interface CooperativaRepository extends CouchbaseRepository<Cooperativa, String>{
	Cooperativa findByNombre(String nombre);
	Iterable<Cooperativa> findByEstadoIsTrue();
}