package com.moornmo.ltms;

import org.metaworks.multitenancy.persistence.MultitenantRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by uengine on 2017. 5. 30..
 */
@RepositoryRestResource(collectionResourceRel = "progress", path = "progress")
public interface ProgressRepository extends MultitenantRepository<Progress, Long> {


}

