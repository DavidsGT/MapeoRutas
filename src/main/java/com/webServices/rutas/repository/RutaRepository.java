package com.webServices.rutas.repository;

import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

import com.webServices.rutas.model.Ruta;
@ViewIndexed(designDoc = "rutaModel", viewName = "all")
public interface RutaRepository extends CouchbaseRepository<Ruta, String>{
	void deleteById (String Id);
}