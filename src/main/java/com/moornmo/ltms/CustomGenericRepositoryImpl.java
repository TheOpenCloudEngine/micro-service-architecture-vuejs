package com.moornmo.ltms;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.List;


@Transactional(readOnly = true)
public class CustomGenericRepositoryImpl<E extends Product, PK extends Serializable> extends
        SimpleJpaRepository<E, PK> implements CustomGenericRepository<E, PK> {

    private final EntityManager entityManager;
    private final JpaEntityInformation<E, ?> entityInformation;

    public CustomGenericRepositoryImpl(final JpaEntityInformation<E, ?> entityInformation,
                                       final EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
        this.entityInformation = entityInformation;
    }

    @Override
    @Transactional
    public void delete(final E entity) {
        Assert.notNull(entity, "Entity object must not be null!");
       // entity.setChangeDate(Calendar.getInstance().getTime());
        entity.setDeleted(true);
    }

    @Override
    public List<E> findAll() {
        return super.findAll(this.isRemoved());
    }

    @Override
    public E findOne(final PK pk) {
        return this.findOne(this.isRemovedByID(pk));
    }

    private Specification<E> isRemoved() {
        return new Specification<E>() {

            @Override
            public Predicate toPredicate(final Root<E> root, final CriteriaQuery<?> query, final CriteriaBuilder cb) {
                return cb.isFalse(root.<Boolean> get("deleted"));
            }

        };
    }

    private Specification<E> isRemovedByID(final PK pk) {
        return new Specification<E>() {

            @Override
            public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                final Predicate id = cb.equal(root.get("id"), pk);
                final Predicate hidden = cb.isFalse(root.<Boolean> get("deleted"));
                return cb.and(id, hidden);
            }

        };
    }

    @Override
    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    protected JpaEntityInformation<E, ?> getEntityInformation() {
        return this.entityInformation;
    }

}