package org.ffsc.ssp.service.persistence.impl;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.ffsc.ssp.service.domain.Priority;
import org.ffsc.ssp.service.persistence.PriorityDAO;

@SuppressWarnings("unchecked")
public class PriorityDAOImpl implements PriorityDAO {

	private EntityManager entityManager;

	@Override
	public Priority byId(Long id) {
		return entityManager.find(Priority.class, id);
	}

	@Override
	public Collection<Priority> listAll() {
		Query q = entityManager.createQuery("SELECT p FROM Priority p");
		return q.getResultList();
	}

	@Override
	public Priority saveOrUpdate(Priority priority) {
		// TODO Implement the interface method here
		return null;
	}

	@Override
	public void delete(Priority entity) {
		// TODO Implement the interface method here
	}
	
	@Override
	@PersistenceContext(type = PersistenceContextType.TRANSACTION)
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}