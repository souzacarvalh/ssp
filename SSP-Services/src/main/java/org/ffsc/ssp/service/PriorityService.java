package org.ffsc.ssp.service;

import java.util.Collection;

import org.ffsc.ssp.service.domain.Priority;

public interface PriorityService extends LocalService {

	Priority getPriority(Long id);

	Collection<Priority> getAvailablePriorities();

}