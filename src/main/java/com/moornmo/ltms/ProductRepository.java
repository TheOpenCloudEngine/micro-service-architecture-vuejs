package com.moornmo.ltms;

import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "product", path = "product")
public interface ProductRepository extends MultitenantRepository<Product, Long> {

    ///@Query("select ")
    List<Product> findByProdName(@Param("name") String name);

}

