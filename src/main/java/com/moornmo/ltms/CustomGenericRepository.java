package com.moornmo.ltms;

import java.io.Serializable;

import javax.persistence.EntityManager;

import com.moornmo.ltms.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CustomGenericRepository<E extends Product, PK extends Serializable> extends
        JpaRepository<E, PK>, JpaSpecificationExecutor<E> {

  //  EntityManager getEntityManager();

}
