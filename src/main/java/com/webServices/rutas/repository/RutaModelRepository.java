package com.webServices.rutas.repository;

import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

import com.webServices.rutas.model.RutaModel;
@ViewIndexed(designDoc = "rutaModel", viewName = "all")
public interface RutaModelRepository extends CouchbaseRepository<RutaModel, String>{
	RutaModel findByNumRuta(String linea);
	void deleteById (String Id);
}