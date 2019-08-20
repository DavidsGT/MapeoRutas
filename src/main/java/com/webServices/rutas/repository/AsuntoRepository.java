package com.webServices.rutas.repository;

import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

import com.webServices.rutas.model.Asunto;

/**
 * Repositorio especifico para Asuntos.
 * @author Davids Adrian Gonzalez Tigrero
 *
 */
@ViewIndexed(designDoc = "asuntos", viewName = "all")
public interface AsuntoRepository extends CouchbaseRepository<Asunto, String>{
}