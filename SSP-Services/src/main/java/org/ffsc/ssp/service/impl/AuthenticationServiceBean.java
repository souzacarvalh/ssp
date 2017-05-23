package org.ffsc.ssp.service.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.shiro.SecurityUtils;
import org.ffsc.ssp.service.AuthenticationService;
import org.ffsc.ssp.service.domain.Credential;
import org.ffsc.ssp.service.persistence.CredentialDAO;
import org.slf4j.Logger;

@Stateless
public class AuthenticationServiceBean implements AuthenticationService {

	private static final long serialVersionUID = 1L;
	
	private Logger logger;

	private CredentialDAO credentialDAO;

	@Inject
	public AuthenticationServiceBean(CredentialDAO daoImpl) {
		this.credentialDAO = daoImpl;
	}

	@Override
	public Credential getAuthenticated() {
		String username = SecurityUtils.getSubject().getPrincipal().toString();
		logger.info(String.format("User authenticated in the system: %s", username));
		return credentialDAO.byUsername(username);
	}
	
	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}