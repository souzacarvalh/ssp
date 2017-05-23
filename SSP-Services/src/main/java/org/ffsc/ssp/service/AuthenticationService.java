package org.ffsc.ssp.service;

import org.ffsc.ssp.service.domain.Credential;

public interface AuthenticationService extends LocalService {
	Credential getAuthenticated();
}