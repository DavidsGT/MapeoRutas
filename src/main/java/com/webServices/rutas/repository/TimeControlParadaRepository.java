package com.webServices.rutas.repository;

import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

import com.webServices.rutas.model.Cooperativa;
import com.webServices.rutas.model.TimeControlParada;

@ViewIndexed(designDoc = "cooperativa", viewName = "all")
public interface TimeControlParadaRepository extends CouchbaseRepository<TimeControlParada, String>{
	TimeControlParada findByLinea(String linea);
	boolean existsByLinea(String linea);
}
