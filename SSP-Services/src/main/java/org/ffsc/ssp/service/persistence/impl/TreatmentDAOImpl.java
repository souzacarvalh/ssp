package org.ffsc.ssp.service.persistence.impl;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.ffsc.ssp.service.domain.Analyst;
import org.ffsc.ssp.service.domain.Group;
import org.ffsc.ssp.service.domain.TicketStatus;
import org.ffsc.ssp.service.domain.Treatment;
import org.ffsc.ssp.service.persistence.TreatmentDAO;

@SuppressWarnings("unchecked")
public class TreatmentDAOImpl implements TreatmentDAO {

	private EntityManager entityManager;

	@Override
	public Treatment byId(Long id) {
		return entityManager.find(Treatment.class, id);
	}

	@Override
	public Collection<Treatment> listAll() {

		Query query = entityManager
				.createQuery("SELECT t FROM Treatment t WHERE t.closed = false");

		return query.getResultList();
	}
	
	@Override
	public Treatment saveOrUpdate(Treatment treatment) {
		return entityManager.merge(treatment);
	}

	@Override
	public Collection<Treatment> listNotAssignedTreatments(
			TicketStatus... status) {

		Query query = entityManager
				.createQuery("SELECT t FROM Treatment t WHERE t.closed = false"
						+ " AND t.ticket.status IN :status"
						+ " AND t.supportGroup IS NULL"
						+ " AND t.supportAnalyst IS NULL");

		query.setParameter("status", status);

		return query.getResultList();
	}

	@Override
	public Collection<Treatment> listTreatmentsForGroup(Group group,
			TicketStatus... status) {

		Query query = entityManager
				.createQuery("SELECT t FROM Treatment t WHERE t.closed = false"
						+ " AND t.ticket.status IN :status"
						+ " AND t.supportGroup = :group"
						+ " AND t.supportAnalyst IS NULL");

		query.setParameter("status", status);
		query.setParameter("group", group);

		return query.getResultList();
	}

	@Override
	public Collection<Treatment> listTreatmentsForAnalyst(Analyst analyst,
			TicketStatus... status) {

		Query query = entityManager
				.createQuery("SELECT t FROM Treatment t WHERE t.closed = false"
						+ " AND t.ticket.status IN :status"
						+ " AND t.supportAnalyst = :analyst");

		query.setParameter("status", Arrays.asList(status));
		query.setParameter("analyst", analyst);

		return query.getResultList();
	}

	@Override
	public Collection<Treatment> listTreatmentsReadyForRating() {

		Query q = entityManager.createQuery("SELECT t FROM Treatment t"
				+ " WHERE t.ticket.closeDate IS NOT NULL"
				+ " AND t.rating.submittedOn IS NULL");

		return q.getResultList();
	}

	@Override
	public void delete(Treatment treatment) {
		// TODO Implement the interface method here
	}
	
	@Override
	@PersistenceContext(type = PersistenceContextType.TRANSACTION)
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}