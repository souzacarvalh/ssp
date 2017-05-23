package org.ffsc.ssp.service.persistence.impl;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.OrderBy;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.ffsc.ssp.service.domain.Category;
import org.ffsc.ssp.service.domain.Product;
import org.ffsc.ssp.service.domain.RecordStatus;
import org.ffsc.ssp.service.persistence.CategoryDAO;

@SuppressWarnings("unchecked")
public class CategoryDAOImpl implements CategoryDAO {

	private EntityManager entityManager;

	@Override
	public Category byId(Long id) {
		return entityManager.find(Category.class, id);
	}

	@Override
	@OrderBy(value = " name ASC")
	public Collection<Category> listAll() {

		Query q = entityManager
				.createQuery("SELECT c FROM Category c WHERE c.status = :status");

		q.setParameter("status", RecordStatus.valueOf("ACTIVE"));

		return q.getResultList();
	}

	@Override
	@OrderBy(value = " name ASC")
	public Collection<Category> listByProduct(Product product) {

		Query q = entityManager
				.createQuery("SELECT c FROM Category c WHERE c.status = :status"
						+ " AND c.product = :product");

		q.setParameter("product", product);
		q.setParameter("status", RecordStatus.valueOf("ACTIVE"));

		return q.getResultList();
	}

	@Override
	public Category saveOrUpdate(Category category) {
		// TODO Implement the interface method here
		return null;
	}

	@Override
	public void delete(Category category) {
		// TODO Implement the interface method here
	}

	@Override
	@PersistenceContext(type = PersistenceContextType.TRANSACTION)
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}