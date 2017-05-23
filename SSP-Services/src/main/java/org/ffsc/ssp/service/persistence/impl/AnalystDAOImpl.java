package org.ffsc.ssp.service.persistence.impl;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.ffsc.ssp.service.domain.Analyst;
import org.ffsc.ssp.service.domain.Credential;
import org.ffsc.ssp.service.domain.Group;
import org.ffsc.ssp.service.persistence.AnalystDAO;

@SuppressWarnings("unchecked")
public class AnalystDAOImpl implements AnalystDAO {

	private EntityManager entityManager;

	@Override
	public Analyst byCredential(Credential credential) {
		try {

			Query q = entityManager.createQuery("SELECT a FROM Analyst a"
					+ " WHERE a.credential.username = :username");

			q.setParameter("username", credential.getUsername());

			return (Analyst) q.getSingleResult();

		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Collection<Analyst> listByGroup(Group group) {

		Query q = entityManager.createQuery("SELECT a FROM Analyst a"
				+ " WHERE a.group = :group");

		q.setParameter("group", group);

		return q.getResultList();
	}

	@Override
	public Analyst saveOrUpdate(Analyst analyst) {
		// TODO Implement the interface method here
		return null;
	}

	@Override
	public void delete(Analyst analyst) {
		// TODO Implement the interface method here

	}

	@Override
	public Analyst byId(Long id) {
		// TODO Implement the interface method here
		return null;
	}

	@Override
	public Collection<Analyst> listAll() {
		// TODO Implement the interface method here
		return null;
	}
	
	@Override
	@PersistenceContext(type = PersistenceContextType.TRANSACTION)
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}