package org.ffsc.ssp.service.persistence.impl;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.ffsc.ssp.service.domain.SLAConfiguration;
import org.ffsc.ssp.service.exception.SSPBusinessException;
import org.ffsc.ssp.service.persistence.SLAConfigDAO;

@SuppressWarnings("unchecked")
public class SLAConfigDAOImpl implements SLAConfigDAO {

	private EntityManager entityManager;

	@Override
	public void delete(SLAConfiguration slaConfig) throws SSPBusinessException {
		SLAConfiguration slaConfigP = entityManager.getReference(SLAConfiguration.class,
				slaConfig.getId());
		entityManager.remove(slaConfigP);
	}

	@Override
	public SLAConfiguration saveOrUpdate(SLAConfiguration slaConfig) {
		return entityManager.merge(slaConfig);
	}

	@Override
	public SLAConfiguration byId(Long id) {
		return entityManager.find(SLAConfiguration.class, id);
	}

	@Override
	public Collection<SLAConfiguration> listAll() {
		Query query = entityManager.createQuery("SELECT sc FROM SLAConfiguration sc");
		return query.getResultList();
	}
	
	@Override
	@PersistenceContext(type = PersistenceContextType.TRANSACTION)
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}