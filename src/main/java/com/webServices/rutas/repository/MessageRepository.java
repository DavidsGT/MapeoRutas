package com.webServices.rutas.repository;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import com.webServices.rutas.model.Message;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

@ViewIndexed(designDoc = "message", viewName = "all")
public interface MessageRepository extends CouchbaseRepository<Message, String>{
	
}
