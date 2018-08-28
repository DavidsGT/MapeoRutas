package com.webServices.rutas.repository;

import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

import com.webServices.rutas.model.HistorialEstadoBus;
@ViewIndexed(designDoc = "historialEstadoBus", viewName = "all")
public interface HistorialEstadoBusRepository extends CouchbaseRepository<HistorialEstadoBus, String>{
}
