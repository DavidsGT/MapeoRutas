package com.webServices.rutas.repository;

import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

import com.webServices.rutas.model.SegMenu;

@ViewIndexed(designDoc = "segMenu", viewName = "all")
public interface SegMenuRepository extends CouchbaseRepository<SegMenu, String>{
	SegMenu findById(String id);
	void deleteById (String Id);
}
