package org.ffsc.ssp.service.persistence.impl;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.ffsc.ssp.service.domain.Group;
import org.ffsc.ssp.service.persistence.GroupDAO;

@SuppressWarnings("unchecked")
public class GroupDAOImpl implements GroupDAO {

	private EntityManager entityManager;

	@Override
	public Group saveOrUpdate(Group group) {
		return entityManager.merge(group);
	}

	@Override
	public Group byId(Long id) {
		return entityManager.find(Group.class, id);
	}

	@Override
	public Collection<Group> listAll() {
		Query q = entityManager.createQuery("SELECT g FROM Group g");
		return q.getResultList();
	}

	@Override
	public void delete(Group entity) {
		// TODO Implement the interface method here
	}
	
	@Override
	@PersistenceContext(type = PersistenceContextType.TRANSACTION)
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}