package com.webServices.rutas.repository;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import org.springframework.data.couchbase.core.query.Dimensional;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.geo.Box;
import org.springframework.data.geo.Circle;

import com.webServices.rutas.model.Parada;
public interface ParadaRepository extends CouchbaseRepository<Parada, String>{
	@Dimensional(designDocument = "paradas", spatialViewName = "paradas", dimensions = 2)
	  @Retention(RetentionPolicy.RUNTIME)
	  @interface IndexedByLocation { }
	@IndexedByLocation
	Iterable<Parada> findByCoordenadaWithin(Box x);
	
	@Dimensional(designDocument = "paradas", spatialViewName = "paradas", dimensions = 2)
	List<Parada> findByCoordenadaWithin(Circle x);
}