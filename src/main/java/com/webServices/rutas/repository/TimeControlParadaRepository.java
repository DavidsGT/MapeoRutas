package com.webServices.rutas.repository;

import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

import com.webServices.rutas.model.TimeControlParada;

@ViewIndexed(designDoc = "timeControlParada", viewName = "all")
public interface TimeControlParadaRepository extends CouchbaseRepository<TimeControlParada, String>{
	TimeControlParada findByLinea(String linea);
}
