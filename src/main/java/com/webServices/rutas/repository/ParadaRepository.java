package com.webServices.rutas.repository;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.data.couchbase.core.query.Dimensional;
import org.springframework.data.couchbase.core.query.View;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.geo.Box;
import org.springframework.data.geo.Circle;

import com.webServices.rutas.model.Parada;

public interface ParadaRepository extends CouchbaseRepository<Parada, String>{
	@View(designDocument = "parada", viewName = "all")
	Iterable<Parada> findByEstadoIsTrue();
	@Dimensional(designDocument = "paradas", spatialViewName = "paradas", dimensions = 2)
	  @Retention(RetentionPolicy.RUNTIME)
	  @interface IndexedByLocation {}
	@IndexedByLocation
	Iterable<Parada> findByCoordenadaWithin(Box x);
	
	@Dimensional(designDocument = "paradas", spatialViewName = "paradas")
	Iterable<Parada> findByCoordenadaWithin(Circle p);
}