package com.webServices.rutas.repository;

import java.util.List;

import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import com.webServices.rutas.model.Punto;

@ViewIndexed(designDoc = "punto", viewName = "all")
public interface PuntoRepository extends CouchbaseRepository<Punto, String>{
	Punto findById(String id);
	List<Punto> findAll();
	void deleteById (String Id);
}