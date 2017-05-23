package org.ffsc.ssp.service.persistence.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.ffsc.ssp.service.domain.TicketStatus;
import org.ffsc.ssp.service.domain.TicketStatus.DefaultStatus;
import org.ffsc.ssp.service.persistence.TicketStatusDAO;

@SuppressWarnings("unchecked")
public class TicketStatusDAOImpl implements TicketStatusDAO {

	private EntityManager entityManager;

	@Override
	public List<TicketStatus> listAll() {

		Query q = entityManager.createQuery("SELECT ts FROM TicketStatus ts");

		return q.getResultList();
	}

	@Override
	public TicketStatus byId(Long id) {
		return entityManager.find(TicketStatus.class, id);
	}
	
	@Override
	public TicketStatus byDefautStatusValue(DefaultStatus status) {
		String name = status.getName();
		return byName(name);
	}

	public TicketStatus byName(String name) {
		try {

			Query q = entityManager
					.createQuery("SELECT ts FROM TicketStatus ts WHERE ts.name = :name");

			q.setParameter("name", name);

			return (TicketStatus) q.getSingleResult();

		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public TicketStatus saveOrUpdate(TicketStatus status) {
		// TODO Implement the interface method here
		return null;
	}

	@Override
	public void delete(TicketStatus status) {
		// TODO Implement the interface method here
	}
	
	@Override
	@PersistenceContext(type = PersistenceContextType.TRANSACTION)
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}