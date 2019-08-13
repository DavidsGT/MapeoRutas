package com.webServices.rutas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.couchbase.core.query.Dimensional;
import org.springframework.data.couchbase.core.query.Query;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.geo.Circle;
import org.springframework.data.repository.query.Param;

import com.webServices.rutas.model.Parada;

@ViewIndexed(designDoc = "parada", viewName = "all")
public interface ParadaRepository extends CouchbaseRepository<Parada, String>{
	
	@Query("#{#n1ql.selectEntity} WHERE #{#n1ql.filter} AND meta().id = '#{#id}' AND estado=true")
	Optional<Parada> findByIdAndEstadoIsTrue(@Param("id") String id);
	
	Optional<List<Parada>> findByEstadoIsTrue();
	
	@Dimensional(designDocument = "spatialView_parada", spatialViewName = "spatialView_parada")
	Optional<List<Parada>> findByCoordenadaWithin(Circle p);
	
	@Query("SELECT t.*, META(t).id AS _ID, META(t).cas AS _CAS FROM #{#n1ql.bucket} AS p USE KEYS '#{#linea}' JOIN #{#n1ql.bucket} AS t ON KEYS ARRAY paradaId FOR paradaId IN p.listasParadas END where t.#{#n1ql.filter} or p.`_class` = \"com.webServices.rutas.model.Ruta\";")
	Iterable<Parada> findAllByLinea(@Param("linea") String linea);

	@Query("SELECT CASE WHEN count(c)> 0 THEN true ELSE false END "
			+ "FROM #{#n1ql.bucket} as c "
			+ "WHERE lower(c.nombre) = lower('#{#nombre}') AND c.estado=true AND c.#{#n1ql.filter}")
	boolean existsByNombreAndEstadoIsTrue(@Param("nombre") String nombre);

	Optional<Iterable<Parada>> findByNombreContainsAndEstadoIsTrue(String nombre);
}