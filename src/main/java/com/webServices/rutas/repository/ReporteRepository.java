package com.webServices.rutas.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.couchbase.core.query.Query;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.repository.query.Param;

import com.webServices.rutas.model.Reporte;

/**
 * Repositorio de Denuncias.
 * @author Davids Adrian Gonzalez Tigrero
 *
 */
@ViewIndexed(designDoc = "reporte", viewName = "all")
public interface ReporteRepository extends CouchbaseRepository<Reporte, String>{
	
	/**
	 * Buscar Denuncias con estado Activo.
	 * @return Lista de {@link Reporte}
	 */
	Optional<List<Reporte>> findByEstadoIsTrue();
	
	/**
	 * Buscar denuncias por cooperativas en un rango de fechas.
	 * @param idCooperativa - ID de Cooperativa
	 * @param fechaIni - Fecha inicial
	 * @param fechaFin - Fecha Final
	 * @return Lista de Denuncias
	 */
	Optional<List<Reporte>> findByIdCooperativaAndEstadoIsTrueAndFechaIsBetween(String idCooperativa, Date fechaIni, Date fechaFin);
	
	/**
	 * Buscar Denuncias por rango de fechas
	 * @param fechaIni - fecha Inicial
	 * @param fechaFin - fecha final.
	 * @return lista de Denuncias
	 */
	Optional<List<Reporte>> findByEstadoIsTrueAndFechaIsBetween(Date fechaIni, Date fechaFin);
	
	/**
	 * Buscar Denuncia por ID y estado Activo.
	 * @param id - ID de Cooperativa
	 * @return Denuncia
	 */
	@Query("#{#n1ql.selectEntity} WHERE #{#n1ql.filter} AND meta().id = '#{#id}' AND estado=true")
	Optional<Reporte> findByIdAndEstadoIsTrue(@Param("id") String id);
}
