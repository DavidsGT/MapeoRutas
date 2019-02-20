package com.webServices.rutas.repository;

import java.util.List;

import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.Query;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbasePagingAndSortingRepository;

import com.webServices.rutas.model.SecurityGroup;

@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "securityGroup")
public interface SecurityGroupRepository extends
        CouchbasePagingAndSortingRepository<SecurityGroup, String> {
    @Query("#{#n1ql.selectEntity} where #{#n1ql.filter} and companyId = $1 and removed = false " +
            " AND ARRAY_CONTAINS(users, $2) ")
    List<SecurityGroup> listUserGroups(String companyId, String userId);
}