package com.webServices.rutas.repository;

import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

import com.webServices.rutas.model.SegPerfil;

@ViewIndexed(designDoc = "segPerfil", viewName = "all")
public interface SegPerfilRepository  extends CouchbaseRepository<SegPerfil, String>{
	void deleteById (String Id);
}
