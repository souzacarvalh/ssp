package org.ffsc.ssp.service;

import java.util.Collection;

import org.ffsc.ssp.service.domain.Group;

public interface GroupService extends LocalService {

	Group getGroup(Long id);

	Collection<Group> getAvailableGroups();

}