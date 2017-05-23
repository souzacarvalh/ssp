package org.ffsc.ssp.service.persistence.impl;

import java.util.Collection;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.ffsc.ssp.service.domain.Requester;
import org.ffsc.ssp.service.domain.Ticket;
import org.ffsc.ssp.service.domain.TicketStatus;
import org.ffsc.ssp.service.domain.TicketStep;
import org.ffsc.ssp.service.persistence.TicketDAO;

@SuppressWarnings("unchecked")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class TicketDAOImpl implements TicketDAO {

	private EntityManager entityManager;

	@Override
	public Ticket byId(Long id) {
		return entityManager.find(Ticket.class, id);
	}

	@Override
	public Ticket saveOrUpdate(Ticket ticket) {
		return entityManager.merge(ticket);
	}

	@Override
	public Long addStep(TicketStep step) {
		Ticket ticket = byId(step.getTicket().getId());
		ticket.addStepToTicketThread(step);
		entityManager.persist(ticket);
		return step.getId();
	}

	@Override
	public TicketStep getStepById(Long id) {
		try {
			return entityManager.find(TicketStep.class, id);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	@Override
	public Collection<Ticket> listAll() {
		Query q = entityManager.createQuery("SELECT t FROM Ticket t");
		return q.getResultList();
	}

	@Override
	public Collection<Ticket> listByRequester(Requester requester) {
		Query q = entityManager
				.createQuery("SELECT t FROM Ticket t WHERE t.requester = :requester");

		q.setParameter("requester", requester);

		return q.getResultList();
	}

	@Override
	public Collection<Ticket> listByStatus(TicketStatus... status) {

		Query q = entityManager
				.createQuery("SELECT t FROM Ticket t WHERE t.status = :status");

		return q.getResultList();
	}

	@Override
	public List<Ticket> listByRequesterAndStatus(Requester requester,
			TicketStatus status) {

		Query q = entityManager
				.createQuery("SELECT t FROM Ticket t WHERE t.requester = :requester"
						+ " AND t.status = :status");

		q.setParameter("requester", requester);
		q.setParameter("status", status);

		return q.getResultList();
	}

	@Override
	public void delete(Ticket ticket) {
		// TODO Implement the interface method here
	}
	
	@Override
	@PersistenceContext(type = PersistenceContextType.TRANSACTION)
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}