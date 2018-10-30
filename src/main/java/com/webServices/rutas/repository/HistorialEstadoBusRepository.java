package com.webServices.rutas.repository;

import org.springframework.data.couchbase.repository.CouchbaseRepository;

import com.webServices.rutas.model.HistorialEstadoBus;


public interface HistorialEstadoBusRepository  extends CouchbaseRepository<HistorialEstadoBus, String>  {

}
