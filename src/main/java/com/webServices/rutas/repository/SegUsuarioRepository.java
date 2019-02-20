package com.webServices.rutas.repository;

import java.util.List;

import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

import com.webServices.rutas.model.SegUsuario;

@ViewIndexed(designDoc = "segUsuario", viewName = "all")
public interface SegUsuarioRepository extends CouchbaseRepository<SegUsuario, String>{
	SegUsuario findByNombreAndClave(String n,String cl);
	Iterable<SegUsuario> findByEstadoIsTrue();
	SegUsuario findByNombre(String name);
}