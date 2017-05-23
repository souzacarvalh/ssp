package org.ffsc.ssp.service;

import org.ffsc.ssp.service.domain.Credential;
import org.ffsc.ssp.service.domain.Requester;

public interface RequesterService extends LocalService {

	Requester getRequester(Long id);

	Requester getRequester(Credential credential);

}