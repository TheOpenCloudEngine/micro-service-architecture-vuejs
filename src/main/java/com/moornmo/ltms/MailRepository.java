package com.moornmo.ltms;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by uengine on 2017. 6. 21..
 */
@RepositoryRestResource(collectionResourceRel = "mail", path = "mail")
public interface MailRepository extends MultitenantRepository<Mail, Long> {
}
