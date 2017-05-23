package org.ffsc.ssp.service.persistence.impl;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.ffsc.ssp.service.domain.Category;
import org.ffsc.ssp.service.domain.Product;
import org.ffsc.ssp.service.domain.Route;
import org.ffsc.ssp.service.persistence.RouteDAO;

@SuppressWarnings("unchecked")
public class RouteDAOImpl implements RouteDAO {

	private EntityManager entityManager;

	@Override
	public Route byId(Long id) {
		return entityManager.find(Route.class, id);
	}

	@Override
	public Collection<Route> listAll() {
		Query query = entityManager.createQuery("SELECT r FROM Route r");
		return query.getResultList();
	}
	
	@Override
	public void delete(Route route) {
		Route routeP = entityManager.getReference(Route.class, route.getId());
		entityManager.remove(routeP);
	}

	@Override
	public Route saveOrUpdate(Route route) {
		Route persisted = entityManager.merge(route);
		return persisted;
	}

	@Override
	public Route byCategory(Category category) {
		try {
			Query query = entityManager.createQuery("SELECT r FROM Route r"
					+ " WHERE r.category = :category");

			query.setParameter("category", category);

			return (Route) query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Collection<Route> listByProduct(Product product) {

		Query query = entityManager.createQuery("SELECT r FROM Route r"
				+ " WHERE r.product = :product");

		query.setParameter("product", product);

		return query.getResultList();
	}

	@Override
	public Route byProductAndCategoryIsNull(Product product) {
		try {

			Query query = entityManager
					.createQuery("SELECT r FROM Route r"
							+ " WHERE r.product = :product"
							+ " AND r.category IS NULL");

			query.setParameter("product", product);

			return (Route) query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		}
	}
	
	@Override
	@PersistenceContext(type = PersistenceContextType.TRANSACTION)
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}