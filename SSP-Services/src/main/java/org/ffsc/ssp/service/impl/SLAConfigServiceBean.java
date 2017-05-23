package org.ffsc.ssp.service.impl;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.ffsc.ssp.service.SLAService;
import org.ffsc.ssp.service.domain.SLAConfiguration;
import org.ffsc.ssp.service.persistence.SLAConfigDAO;

@Stateless
public class SLAConfigServiceBean implements SLAService {

	private static final long serialVersionUID = 1L;

	private SLAConfigDAO slaConfigDAO;

	@Inject
	public SLAConfigServiceBean(SLAConfigDAO daoImpl) {
		this.slaConfigDAO = daoImpl;
	}

	@Override
	public Long saveSLAConfig(SLAConfiguration slaConfig) {
		return slaConfigDAO.saveOrUpdate(slaConfig).getId();
	}

	@Override
	public void deleteSLAConfig(SLAConfiguration slaConfig) {
		slaConfigDAO.delete(slaConfig);
	}

	@Override
	public SLAConfiguration getSLAConfig(Long id) {
		return slaConfigDAO.byId(id);
	}

	@Override
	public Collection<SLAConfiguration> getSLAConfigs() {
		return slaConfigDAO.listAll();
	}
}