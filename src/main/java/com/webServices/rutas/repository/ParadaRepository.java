package com.webServices.rutas.repository;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.data.couchbase.core.query.Dimensional;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.geo.Box;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;

import com.webServices.rutas.model.Parada;
//@ViewIndexed(designDoc = "parada", viewName = "all")
public interface ParadaRepository extends CouchbaseRepository<Parada, String>{
	@Dimensional(designDocument="paradas", spatialViewName="paradas")
	Iterable<Parada> findByCoordenadaWithin(Box x);
	
	@Dimensional(designDocument="paradas", spatialViewName="paradas")
	@Retention(RetentionPolicy.RUNTIME)
	@interface IndexedByCoordenada { }
	@IndexedByCoordenada
	Iterable<Parada> findByCoordenadaNear(Point x,Distance r);
}