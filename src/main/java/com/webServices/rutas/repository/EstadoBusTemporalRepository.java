package com.webServices.rutas.repository;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import org.springframework.data.couchbase.core.query.Dimensional;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Box;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoPage;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;

import com.webServices.rutas.model.EstadoBusTemporal;
import com.webServices.rutas.model.Parada;

public interface EstadoBusTemporalRepository  extends CouchbaseRepository<EstadoBusTemporal, String>{
	@Dimensional(designDocument = "spatialView_estadoBusTemporal", spatialViewName = "spatialView_estadoBusTemporal", dimensions = 2)
	List<EstadoBusTemporal> findByPosicionActualWithin(Circle p);
}