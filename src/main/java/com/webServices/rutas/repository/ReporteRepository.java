package com.webServices.rutas.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.couchbase.core.query.Query;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.repository.query.Param;

import com.webServices.rutas.model.Reporte;

@ViewIndexed(designDoc = "reporte", viewName = "all")
public interface ReporteRepository extends CouchbaseRepository<Reporte, String>{
	Optional<List<Reporte>> findByEstadoIsTrue();

	Optional<List<Reporte>> findByIdCooperativaAndEstadoIsTrueAndFechaIsBetween(String idCooperativa, Date fechaIni, Date fechaFin);
	
	Optional<List<Reporte>> findByEstadoIsTrueAndFechaIsBetween(Date fechaIni, Date fechaFin);
	
	@Query("#{#n1ql.selectEntity} WHERE #{#n1ql.filter} AND meta().id = '#{#id}' AND estado=true")
	Optional<Reporte> findByIdAndEstadoIsTrue(@Param("id") String id);
}
