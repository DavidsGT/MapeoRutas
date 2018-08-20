package com.webServices.rutas.repository;

import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

import com.webServices.rutas.model.SegAcceso;

@ViewIndexed(designDoc = "segAcceso", viewName = "all")
public interface SegAccesoRepository extends CouchbaseRepository<SegAcceso, String>{
	void deleteById (String Id);
	Iterable<SegAcceso> findByIdSegPerfil(String idSegPerfil);
}
