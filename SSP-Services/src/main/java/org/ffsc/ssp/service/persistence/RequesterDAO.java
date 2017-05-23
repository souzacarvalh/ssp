package org.ffsc.ssp.service.persistence;

import org.ffsc.ssp.service.domain.Credential;
import org.ffsc.ssp.service.domain.Requester;

public interface RequesterDAO extends GenericDAO<Requester, Long> {
	
	Requester byCredential(Credential credential);
	
}