package com.webServices.rutas.repository;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import com.webServices.rutas.model.Denuncia;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

@ViewIndexed(designDoc = "denuncia", viewName = "all")
public interface DenunciaRepository extends CouchbaseRepository<Denuncia, String>{
	Iterable<Denuncia> findByEstadoIsTrue();
}