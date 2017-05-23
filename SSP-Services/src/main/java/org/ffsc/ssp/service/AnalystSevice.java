package org.ffsc.ssp.service;

import java.util.Collection;

import org.ffsc.ssp.service.domain.Analyst;
import org.ffsc.ssp.service.domain.Credential;
import org.ffsc.ssp.service.domain.Group;

public interface AnalystSevice extends LocalService {

	Analyst getAnalyst(Long id);

	Analyst getAnalyst(Credential credential);

	Collection<Analyst> getAnalystsByGroup(Group group);

}