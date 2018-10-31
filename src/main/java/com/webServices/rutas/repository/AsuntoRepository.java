package com.webServices.rutas.repository;

import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

import com.webServices.rutas.model.Asunto;

@ViewIndexed(designDoc = "asuntos", viewName = "all")
public interface AsuntoRepository extends CouchbaseRepository<Asunto, String>{
}
