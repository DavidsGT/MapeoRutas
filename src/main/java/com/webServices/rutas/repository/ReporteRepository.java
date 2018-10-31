package com.webServices.rutas.repository;

import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

import com.webServices.rutas.model.Reporte;

@ViewIndexed(designDoc = "reporte", viewName = "all")
public interface ReporteRepository extends CouchbaseRepository<Reporte, String>{
}
