package org.ffsc.ssp.service.persistence.impl;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.ffsc.ssp.service.domain.Credential;
import org.ffsc.ssp.service.domain.Requester;
import org.ffsc.ssp.service.persistence.RequesterDAO;

@SuppressWarnings("unchecked")
public class RequesterDAOImpl implements RequesterDAO {

	private EntityManager entityManager;

	@Override
	public Requester byId(Long id) {
		return entityManager.find(Requester.class, id);
	}

	@Override
	public Collection<Requester> listAll() {
		Query q = entityManager.createQuery("SELECT r FROM Requester r");
		return q.getResultList();
	}

	@Override
	public Requester byCredential(Credential credential) {
		try {
			Query q = entityManager.createQuery("SELECT r FROM Requester r"
					+ " WHERE r.credential.username = :username");
			q.setParameter("username", credential.getUsername());
			return (Requester) q.getSingleResult();

		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Requester saveOrUpdate(Requester requester) {
		// TODO Implement the interface method here
		return null;
	}

	@Override
	public void delete(Requester requester) {
		// TODO Implement the interface method here
	}
	
	@Override
	@PersistenceContext(type = PersistenceContextType.TRANSACTION)
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}