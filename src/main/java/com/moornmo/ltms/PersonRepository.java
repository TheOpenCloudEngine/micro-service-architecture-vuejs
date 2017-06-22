package com.moornmo.ltms;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by uengine on 2017. 6. 12..
 */
@RepositoryRestResource(collectionResourceRel = "person", path = "person")
public interface PersonRepository extends MultitenantRepository<Person, String> {

}

