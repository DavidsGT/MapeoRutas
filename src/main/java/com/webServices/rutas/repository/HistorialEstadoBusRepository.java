package com.webServices.rutas.repository;


import java.util.List;

import org.springframework.data.couchbase.core.query.Query;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.repository.query.Param;

import com.webServices.rutas.model.EstadoBus;
import com.webServices.rutas.model.HistorialEstadoBus;

@ViewIndexed(designDoc = "historialEstadoBus", viewName = "all")
public interface HistorialEstadoBusRepository  extends CouchbaseRepository<HistorialEstadoBus, String>  {
	
	@Query("SELECT ARRAY_MAX(listaEstados).cantidadUsuarios,ARRAY_MAX(listaEstados).creationDate,"
				+ "ARRAY_MAX(listaEstados).velocidad,ARRAY_MAX(listaEstados).posicionActual,"
				+ "ARRAY_MAX(listaEstados).estadoPuerta,ARRAY_MAX(listaEstados).linea, "
				+ "META(h).id AS _ID, META(h).cas AS _CAS "
			+ "FROM #{#n1ql.bucket} as h "
			+ "WHERE MILLIS_TO_STR(h.creadoEn ,'1111-11-11') = '#{#creadoEn}' AND h.#{#n1ql.filter}")
	List<EstadoBus> findLastEstadoBus(@Param("creadoEn") String creadoEn);
	
	@Query("SELECT listaEstados[ARRAY_COUNT(listaEstados)-1].cantidadUsuarios,listaEstados[ARRAY_COUNT(listaEstados)-1].creationDate,"
				+ "listaEstados[ARRAY_COUNT(listaEstados)-1].velocidad,listaEstados[ARRAY_COUNT(listaEstados)-1].posicionActual,"
				+ "listaEstados[ARRAY_COUNT(listaEstados)-1].estadoPuerta,listaEstados[ARRAY_COUNT(listaEstados)-1].linea, "
				+ "META(h).id AS _ID, META(h).cas AS _CAS "
			+ "FROM #{#n1ql.bucket} as h "
			+ "WHERE ARRAY_MAX(listaEstados).linea = #{#linea} AND MILLIS_TO_STR(h.creadoEn ,'1111-11-11') = '#{#creadoEn}' AND h.#{#n1ql.filter}")
	List<EstadoBus> findLastEstadoBusByLinea(@Param("creadoEn") String creadoEn,@Param("linea") String linea);
	
	@Query("SELECT h.*, META(h).id AS _ID, META(h).cas AS _CAS "
			+ "FROM #{#n1ql.bucket} as h "
			+ "WHERE MILLIS_TO_STR(h.creadoEn ,'1111-11-11') = '#{#creadoEn}' AND h.#{#n1ql.filter}")
	List<HistorialEstadoBus> findByCreadoEn(@Param("creadoEn") String creadoEn);
}