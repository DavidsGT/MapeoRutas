package com.webServices.rutas.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.couchbase.core.query.Query;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.repository.query.Param;

import com.webServices.rutas.model.EstadoBusTemporal;
import com.webServices.rutas.model.HistorialEstadoBus;

@ViewIndexed(designDoc = "historialEstadoBus", viewName = "all")
public interface HistorialEstadoBusRepository  extends CouchbaseRepository<HistorialEstadoBus, String>  {
	
	@Query("SELECT OBJECT_CONCAT(u,{'placa':h.placa},{'_ID':META(h).id},{'_CAS':META(h).cas}).* "
			+ "FROM #{#n1ql.bucket} as h "
			+ "LET u = CASE WHEN ARRAY_COUNT(h.listaEstados3) <> 0"
			+ "   THEN OBJECT_CONCAT(h.listaEstados3[ARRAY_COUNT(h.listaEstados3)-1],{'posicionAnterior':h.listaEstados3[ARRAY_COUNT(h.listaEstados3)-2].posicionActual},{'idx':ARRAY_COUNT(h.listaEstados3)-1})"
			+ "   ELSE CASE WHEN ARRAY_COUNT(h.listaEstados2) <> 0"
			+ "        THEN OBJECT_CONCAT(h.listaEstados2[ARRAY_COUNT(h.listaEstados2)-1],{'posicionAnterior':h.listaEstados2[ARRAY_COUNT(h.listaEstados2)-2].posicionActual},{'idx':ARRAY_COUNT(h.listaEstados2)-1})"
			+ "        ELSE OBJECT_CONCAT(h.listaEstados1[ARRAY_COUNT(h.listaEstados1)-1],{'posicionAnterior':h.listaEstados1[ARRAY_COUNT(h.listaEstados1)-2].posicionActual},{'idx':ARRAY_COUNT(h.listaEstados1)-1})"
			+ "        END"
			+ "   END "
			+ "WHERE MILLIS_TO_STR(h.creadoEn ,'1111-11-11') = '#{#creadoEn}' AND h.#{#n1ql.filter}")
	Optional<List<EstadoBusTemporal>> findLastEstadoBus(@Param("creadoEn") String creadoEn);
	//TODO resolver ultimo estado evaluar con el nuevo modelo
	@Query("SELECT OBJECT_CONCAT(u,{'placa':h.placa},{'_ID':META(h).id},{'_CAS':META(h).cas}).* "
			+ "FROM #{#n1ql.bucket} as h "
			+ "LET u = CASE WHEN ARRAY_COUNT(h.listaEstados3) <> 0"
			+ "   THEN OBJECT_CONCAT(h.listaEstados3[ARRAY_COUNT(h.listaEstados3)-1],{'posicionAnterior':h.listaEstados3[ARRAY_COUNT(h.listaEstados3)-2].posicionActual},{'idx':ARRAY_COUNT(h.listaEstados3)-1})"
			+ "   ELSE CASE WHEN ARRAY_COUNT(h.listaEstados2) <> 0"
			+ "        THEN OBJECT_CONCAT(h.listaEstados2[ARRAY_COUNT(h.listaEstados2)-1],{'posicionAnterior':h.listaEstados2[ARRAY_COUNT(h.listaEstados2)-2].posicionActual},{'idx':ARRAY_COUNT(h.listaEstados2)-1})"
			+ "        ELSE OBJECT_CONCAT(h.listaEstados1[ARRAY_COUNT(h.listaEstados1)-1],{'posicionAnterior':h.listaEstados1[ARRAY_COUNT(h.listaEstados1)-2].posicionActual},{'idx':ARRAY_COUNT(h.listaEstados1)-1})"
			+ "        END"
			+ "   END "
			+ "WHERE h.linea= #{#linea} AND MILLIS_TO_STR(h.creadoEn ,'1111-11-11') = '#{#creadoEn}' AND h.#{#n1ql.filter}"
			+ "& timeout=900000ms")
	Optional<List<EstadoBusTemporal>> findLastEstadoBusByLinea(@Param("creadoEn") String creadoEn,@Param("linea") String linea);
	
	@Query("SELECT OBJECT_CONCAT(u,{'placa':h.placa},{'_ID':META(h).id},{'_CAS':META(h).cas}).* "
			+ "FROM #{#n1ql.bucket} as h "
			+ "LET u = CASE WHEN ARRAY_COUNT(h.listaEstados3) <> 0"
			+ "   THEN OBJECT_CONCAT(h.listaEstados3[ARRAY_COUNT(h.listaEstados3)-1],{'posicionAnterior':h.listaEstados3[ARRAY_COUNT(h.listaEstados3)-2].posicionActual},{'idx':ARRAY_COUNT(h.listaEstados3)-1})"
			+ "   ELSE CASE WHEN ARRAY_COUNT(h.listaEstados2) <> 0"
			+ "        THEN OBJECT_CONCAT(h.listaEstados2[ARRAY_COUNT(h.listaEstados2)-1],{'posicionAnterior':h.listaEstados2[ARRAY_COUNT(h.listaEstados2)-2].posicionActual},{'idx':ARRAY_COUNT(h.listaEstados2)-1})"
			+ "        ELSE OBJECT_CONCAT(h.listaEstados1[ARRAY_COUNT(h.listaEstados1)-1],{'posicionAnterior':h.listaEstados1[ARRAY_COUNT(h.listaEstados1)-2].posicionActual},{'idx':ARRAY_COUNT(h.listaEstados1)-1})"
			+ "        END"
			+ "   END "
			+ "WHERE h.placa= '#{#placa}' AND MILLIS_TO_STR(h.creadoEn ,'1111-11-11') = '#{#creadoEn}' AND h.#{#n1ql.filter}")
	Optional<EstadoBusTemporal> findLastEstadoBusByPlaca(@Param("creadoEn") String creadoEn,@Param("placa") String placa);
	
	@Query("SELECT META(h).id AS _ID, META(h).cas AS _CAS "
			+ "FROM #{#n1ql.bucket} as h "
			+ "WHERE MILLIS_TO_STR(h.creadoEn ,'1111-11-11') = '#{#creadoEn}' AND h.#{#n1ql.filter}")
	List<HistorialEstadoBus> findByCreadoEn(@Param("creadoEn") String creadoEn);
	
	//6378.1 - radio aproximado de la tierra
	//17200.45
	@Query("SELECT m.#{#lista}[ARRAY_POSITION(m.#{#lista},er)-1].posicionActual as posicionAnterior, ARRAY_POSITION(m.#{#lista},er)  as idx, m.placa, er.cantidadUsuarios,er.creationDate,"
					+ "er.velocidad,er.posicionActual, er.estadoPuerta,er.linea, "
					+ "META(m).id AS _ID, META(m).cas AS _CAS "+" FROM #{#n1ql.bucket} AS m UNNEST m.#{#lista} AS er WHERE " + 
    "(RADIANS(er.posicionActual.y) >=  #{#SW_loc_rad_lat}  and RADIANS(er.posicionActual.y) <= #{#NE_loc_rad_lat}) and " +
    "(RADIANS(er.posicionActual.x) >= #{#SW_loc_rad_lon} #{#meridian180condition} RADIANS(er.posicionActual.x) <= #{#NE_loc_rad_lon} ) AND " +
    " acos(sin( RADIANS( #{#loc_deg_lat} )) * sin (RADIANS(er.posicionActual.y)) + cos( RADIANS( #{#loc_deg_lat} )) " 
    + " * cos(RADIANS(er.posicionActual.y)) * cos (RADIANS(er.posicionActual.x) - RADIANS( #{#loc_deg_lon} ))) <= #{#distance}/6378  AND m.#{#n1ql.filter} AND meta(m).id = '#{#idHistorial}'"
    + "& timeout=900000ms")
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
	@Query("SELECT h.*, META(h).id AS _ID, META(h).cas AS _CAS "
			+ "FROM #{#n1ql.bucket} as h "
			+ "WHERE meta(h).id = '#{#id}' AND h.#{#n1ql.filter} LIMIT 1")
	HistorialEstadoBus findByIdCustom(@Param("id") String id);
}