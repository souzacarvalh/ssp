package org.ffsc.ssp.service.persistence.impl;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.ffsc.ssp.service.domain.Credential;
import org.ffsc.ssp.service.persistence.CredentialDAO;

public class CredentialDAOImpl implements CredentialDAO {

	private EntityManager entityManager;

	@Override
	public Credential byId(Long id) {
		return entityManager.find(Credential.class, id);
	}

	@Override
	public Credential byUsername(String login) {
		try {

			Query q = entityManager
					.createQuery("SELECT c FROM Credential c WHERE c.username = :username");

			q.setParameter("username", login);

			return (Credential) q.getSingleResult();

		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Collection<Credential> listAll() {
		// TODO Implement the interface method here
		return null;
	}

	@Override
	public Credential saveOrUpdate(Credential credential) {
		// TODO Implement the interface method here
		return null;
	}

	@Override
	public void delete(Credential credential) {
		// TODO Implement the interface method here

	}
	
	@Override
	@PersistenceContext(type = PersistenceContextType.TRANSACTION)
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}