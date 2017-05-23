package org.ffsc.ssp.service.persistence;

import java.util.Collection;

import org.ffsc.ssp.service.domain.Analyst;
import org.ffsc.ssp.service.domain.Credential;
import org.ffsc.ssp.service.domain.Group;

public interface AnalystDAO extends GenericDAO<Analyst, Long> {

	Analyst byCredential(Credential credential);

	Collection<Analyst> listByGroup(Group group);

}