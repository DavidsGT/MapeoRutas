package com.webServices.rutas.repository;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.data.couchbase.core.query.Dimensional;
import org.springframework.data.couchbase.core.query.Query;
import org.springframework.data.couchbase.core.query.View;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.geo.Box;
import org.springframework.data.geo.Circle;
import org.springframework.data.repository.query.Param;

import com.webServices.rutas.model.Parada;

@ViewIndexed(designDoc = "parada", viewName = "all")
public interface ParadaRepository extends CouchbaseRepository<Parada, String>{
	Iterable<Parada> findByEstadoIsTrue();
	@Dimensional(designDocument = "paradas", spatialViewName = "paradas", dimensions = 2)
	  @Retention(RetentionPolicy.RUNTIME)
	  @interface IndexedByLocation {}
	@IndexedByLocation
	Iterable<Parada> findByCoordenadaWithin(Box x);
	
	@Dimensional(designDocument = "paradas", spatialViewName = "paradas")
	Iterable<Parada> findByCoordenadaWithin(Circle p);
	
	@Query("SELECT t.* FROM #{#n1ql.bucket} AS p USE KEYS '7' JOIN #{#n1ql.bucket} AS t ON KEYS ARRAY paradaId FOR paradaId IN p.listasParadas END where t.#{#n1ql.filter} or p.`_class` = \"com.webServices.rutas.model.ruta\";")
	Iterable<Parada> findAllByLinea(@Param("linea") String linea);
}