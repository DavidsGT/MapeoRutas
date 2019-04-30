package com.webServices.rutas.repository;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.data.couchbase.core.query.Dimensional;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.geo.Box;
import org.springframework.data.geo.Circle;

import com.webServices.rutas.model.EstadoBusTemporal;
import com.webServices.rutas.model.Parada;

@ViewIndexed(designDoc = "estadoBusTemporal", viewName = "all")
public interface EstadoBusTemporalRepository  extends CouchbaseRepository<EstadoBusTemporal, String>{
	@Dimensional(designDocument = "spatialView_estadoBusTemporal", spatialViewName = "spatialView_estadoBusTemporal", dimensions = 2)
	  @Retention(RetentionPolicy.RUNTIME)
	  @interface IndexedByLocation {}
	@IndexedByLocation
	Iterable<EstadoBusTemporal> findByPosicionActualWithin(Circle p);
}