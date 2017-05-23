package org.ffsc.ssp.service.persistence;

import org.ffsc.ssp.service.domain.Credential;

public interface CredentialDAO extends GenericDAO<Credential, Long> {

	Credential byUsername(String username);

}