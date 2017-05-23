package org.ffsc.ssp.service.persistence.impl;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.OrderBy;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.ffsc.ssp.service.domain.Product;
import org.ffsc.ssp.service.persistence.ProductDAO;

@SuppressWarnings("unchecked")
public class ProductDAOImpl implements ProductDAO {

	private EntityManager entityManager;

	@Override
	public Product byId(Long id) {
		return entityManager.find(Product.class, id);
	}

	@Override
	@OrderBy(value = " name ASC")
	public Collection<Product> listAll() {
		Query q = entityManager.createQuery("SELECT p FROM Product p");
		return q.getResultList();
	}

	@Override
	public Product saveOrUpdate(Product product) {
		// TODO Implement the interface method here
		return null;
	}

	@Override
	public void delete(Product product) {
		// TODO Implement the interface method here
	}
	
	@Override
	@PersistenceContext(type = PersistenceContextType.TRANSACTION)
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}