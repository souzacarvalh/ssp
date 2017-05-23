package org.ffsc.ssp.service.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.ffsc.ssp.service.RequesterService;
import org.ffsc.ssp.service.domain.Credential;
import org.ffsc.ssp.service.domain.Requester;
import org.ffsc.ssp.service.persistence.RequesterDAO;

@Stateless
public class RequesterServiceBean implements RequesterService {

	private static final long serialVersionUID = 1L;

	private RequesterDAO requesterDAO;

	@Inject
	public RequesterServiceBean(RequesterDAO daoImpl) {
		this.requesterDAO = daoImpl;
	}

	@Override
	public Requester getRequester(Long id) {
		return requesterDAO.byId(id);
	}

	@Override
	public Requester getRequester(Credential credential) {
		return requesterDAO.byCredential(credential);
	}
}