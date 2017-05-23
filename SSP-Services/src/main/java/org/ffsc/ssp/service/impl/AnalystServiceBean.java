package org.ffsc.ssp.service.impl;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.ffsc.ssp.service.AnalystSevice;
import org.ffsc.ssp.service.domain.Analyst;
import org.ffsc.ssp.service.domain.Credential;
import org.ffsc.ssp.service.domain.Group;
import org.ffsc.ssp.service.persistence.AnalystDAO;

@Stateless
public class AnalystServiceBean implements AnalystSevice {

	private static final long serialVersionUID = 1L;

	private AnalystDAO analystDAO;

	@Inject
	public AnalystServiceBean(AnalystDAO daoImpl) {
		this.analystDAO = daoImpl;
	}

	@Override
	public Analyst getAnalyst(Long id) {
		return analystDAO.byId(id);
	}

	@Override
	public Analyst getAnalyst(Credential credential) {
		return analystDAO.byCredential(credential);
	}

	@Override
	public Collection<Analyst> getAnalystsByGroup(Group group) {
		return analystDAO.listByGroup(group);
	}
}