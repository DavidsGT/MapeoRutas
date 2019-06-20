package com.webServices.rutas.repository;


import java.util.List;

import org.springframework.data.couchbase.core.query.Query;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.repository.query.Param;

import com.webServices.rutas.model.EstadoBus;
import com.webServices.rutas.model.EstadoBusTemporal;
import com.webServices.rutas.model.HistorialEstadoBus;

@ViewIndexed(designDoc = "historialEstadoBus", viewName = "all")
public interface HistorialEstadoBusRepository  extends CouchbaseRepository<HistorialEstadoBus, String>  {
	
	@Query("SELECT h.listaEstados[ARRAY_COUNT(h.listaEstados)-2].posicionActual as posicionAnterior, (ARRAY_COUNT(h.listaEstados)-1) as idx, h.placa, h.listaEstados[ARRAY_COUNT(h.listaEstados)-1].cantidadUsuarios,h.listaEstados[ARRAY_COUNT(h.listaEstados)-1].creationDate,"
			+ "h.listaEstados[ARRAY_COUNT(h.listaEstados)-1].velocidad,h.listaEstados[ARRAY_COUNT(h.listaEstados)-1].posicionActual,"
			+ "h.listaEstados[ARRAY_COUNT(h.listaEstados)-1].estadoPuerta,h.listaEstados[ARRAY_COUNT(h.listaEstados)-1].linea, "
			+ "META(h).id AS _ID, META(h).cas AS _CAS "
			+ "FROM #{#n1ql.bucket} as h "
			+ "WHERE MILLIS_TO_STR(h.creadoEn ,'1111-11-11') = '#{#creadoEn}' AND h.#{#n1ql.filter}")
	List<EstadoBusTemporal> findLastEstadoBus(@Param("creadoEn") String creadoEn);
	
	@Query("SELECT h.listaEstados[ARRAY_COUNT(h.listaEstados)-2].posicionActual as posicionAnterior, (ARRAY_COUNT(h.listaEstados)-1) as idx, h.placa, listaEstados[ARRAY_COUNT(listaEstados)-1].cantidadUsuarios,listaEstados[ARRAY_COUNT(listaEstados)-1].creationDate,"
				+ "listaEstados[ARRAY_COUNT(listaEstados)-1].velocidad,listaEstados[ARRAY_COUNT(listaEstados)-1].posicionActual,"
				+ "listaEstados[ARRAY_COUNT(listaEstados)-1].estadoPuerta,listaEstados[ARRAY_COUNT(listaEstados)-1].linea, "
				+ "META(h).id AS _ID, META(h).cas AS _CAS "
			+ "FROM #{#n1ql.bucket} as h "
			+ "WHERE ARRAY_MAX(listaEstados).linea = #{#linea} AND MILLIS_TO_STR(h.creadoEn ,'1111-11-11') = '#{#creadoEn}' AND h.#{#n1ql.filter}")
	List<EstadoBusTemporal> findLastEstadoBusByLinea(@Param("creadoEn") String creadoEn,@Param("linea") String linea);
	
	@Query("SELECT h.*, META(h).id AS _ID, META(h).cas AS _CAS "
			+ "FROM #{#n1ql.bucket} as h "
			+ "WHERE MILLIS_TO_STR(h.creadoEn ,'1111-11-11') = '#{#creadoEn}' AND h.#{#n1ql.filter}")
	List<HistorialEstadoBus> findByCreadoEn(@Param("creadoEn") String creadoEn);
	
	@Query("SELECT META(h).id AS id , META(h).id AS _ID, META(h).cas AS _CAS "
			+ "FROM #{#n1ql.bucket} as h "
			+ "WHERE MILLIS_TO_STR(h.creadoEn ,'1111-11-11') = '#{#creadoEn}' AND h.#{#n1ql.filter}")
	List<String> findByCreadoEnGetId(@Param("creadoEn") String creadoEn);
	
	//6378.1 - radio aproximado de la tierra
	//17200.45
	@Query("SELECT m.#{#lista}[ARRAY_POSITION(m.#{#lista},er)-1].posicionActual as posicionAnterior, ARRAY_POSITION(m.#{#lista},er)  as idx, m.placa, er.cantidadUsuarios,er.creationDate,"
					+ "er.velocidad,er.posicionActual, er.estadoPuerta,er.linea, "
					+ "META(m).id AS _ID, META(m).cas AS _CAS "+" FROM #{#n1ql.bucket} AS m UNNEST m.#{#lista} AS er WHERE " + 
    "(RADIANS(er.posicionActual.y) >=  #{#SW_loc_rad_lat}  and RADIANS(er.posicionActual.y) <= #{#NE_loc_rad_lat}) and " +
    "(RADIANS(er.posicionActual.x) >= #{#SW_loc_rad_lon} #{#meridian180condition} RADIANS(er.posicionActual.x) <= #{#NE_loc_rad_lon} ) AND " +
    " acos(sin( RADIANS( #{#loc_deg_lat} )) * sin (RADIANS(er.posicionActual.y)) + cos( RADIANS( #{#loc_deg_lat} )) " 
    + " * cos(RADIANS(er.posicionActual.y)) * cos (RADIANS(er.posicionActual.x) - RADIANS( #{#loc_deg_lon} ))) <= #{#distance}/6378  AND m.#{#n1ql.filter} AND meta(m).id = '#{#idHistorial}'")
	List<EstadoBusTemporal> findByListaEstadosInPosicionWithIn( @Param("lista") String lista,
																@Param("meridian180condition") String meridian180condition,
																@Param("loc_deg_lat") double loc_deg_lat, 
																@Param("loc_deg_lon") double loc_deg_lon,
																@Param("SW_loc_rad_lat") double SW_loc_rad_lat,
																@Param("SW_loc_rad_lon") double SW_loc_rad_lon,
																@Param("NE_loc_rad_lat") double NE_loc_rad_lat,
																@Param("NE_loc_rad_lon") double NE_loc_rad_lon,
																@Param("distance") double distance,
																@Param("idHistorial") String idHistorial);
}