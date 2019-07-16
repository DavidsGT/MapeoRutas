package com.webServices.rutas.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.couchbase.core.query.Query;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.repository.query.Param;

import com.webServices.rutas.model.Ruta;
@ViewIndexed(designDoc = "ruta", viewName = "all")
public interface RutaRepository extends CouchbaseRepository<Ruta, String>{
	Optional<List<Ruta>> findByEstadoIsTrue();
	Optional<Ruta> findByLineaAndEstadoIsTrue(String linea);
	@Query("#{#n1ql.selectEntity} where #{#n1ql.filter} AND ARRAY_CONTAINS(listasParadas, '#{#idParada}')")
	Optional<Ruta> findByListaParadasContains(@Param("idParada") String idParada);
	@Query("SELECT CASE WHEN count(c)> 0 THEN true ELSE false END "
			+ "FROM #{#n1ql.bucket} as c "
			+ "WHERE lower(c.linea) = lower('#{#linea}') AND c.estado=true AND c.#{#n1ql.filter}")
	boolean existsByLineaAndEstadoIsTrue(@Param("linea") String linea);
}