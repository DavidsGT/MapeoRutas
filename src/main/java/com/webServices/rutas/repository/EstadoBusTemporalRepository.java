package com.webServices.rutas.repository;

import java.util.List;

import org.springframework.data.couchbase.core.query.Dimensional;
import org.springframework.data.couchbase.core.query.Query;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.geo.Circle;
import org.springframework.data.repository.query.Param;

import com.webServices.rutas.model.BetweenParada;
import com.webServices.rutas.model.EstadoBusTemporal;
@ViewIndexed(designDoc = "estadoBusTemporal", viewName = "all")
public interface EstadoBusTemporalRepository  extends CouchbaseRepository<EstadoBusTemporal, String>{
	@Dimensional(designDocument = "spatialView_estadoBusTemporal", spatialViewName = "spatialView_estadoBusTemporal", dimensions = 2)
	List<EstadoBusTemporal> findByPosicionActualWithin(Circle p);
}