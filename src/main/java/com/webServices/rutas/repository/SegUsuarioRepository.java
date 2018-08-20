package com.webServices.rutas.repository;

import java.util.List;

import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

import com.webServices.rutas.model.SegUsuario;

public interface SegUsuarioRepository extends CouchbaseRepository<SegUsuario, String>{
	SegUsuario findByNombreAndClave(String n,String cl);
}