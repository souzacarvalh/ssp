package org.ffsc.ssp.service.persistence;

import java.util.Collection;

import javax.persistence.EntityManager;

import org.ffsc.ssp.service.domain.DomainEntity;

public interface GenericDAO<T extends DomainEntity, R> {
	T byId(R id);

	Collection<T> listAll();

	T saveOrUpdate(T entity);

	void delete(T entity);

	void setEntityManager(EntityManager entityManager);
}