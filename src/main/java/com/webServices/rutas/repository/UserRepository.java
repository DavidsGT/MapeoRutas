package com.webServices.rutas.repository;

import java.util.List;

import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbasePagingAndSortingRepository;

import com.webServices.rutas.model.User;

@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "user")
public interface UserRepository extends CouchbasePagingAndSortingRepository<User, String> {

    List<User> findByUsername(String username);

}
