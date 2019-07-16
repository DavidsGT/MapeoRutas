package com.webServices.rutas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.couchbase.core.query.Query;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

import com.webServices.rutas.model.SegUsuario;

@ViewIndexed(designDoc = "segUsuario", viewName = "all")
public interface SegUsuarioRepository extends CouchbaseRepository<SegUsuario, String>{
	SegUsuario findByUsuarioAndClave(String n,String cl);
	Optional<List<SegUsuario>> findByEstadoIsTrue();
	Optional<SegUsuario> findByUsuarioOrEmail(String name,String email);
	
	@Query("#{#n1ql.selectEntity} WHERE #{#n1ql.filter} AND meta().id = '#{#id}' AND estado=true")
	Optional<SegUsuario> findByIdAndEstadoIsTrue(String id);
}