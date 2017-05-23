package org.ffsc.ssp.service.impl;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.ffsc.ssp.service.PriorityService;
import org.ffsc.ssp.service.domain.Priority;
import org.ffsc.ssp.service.persistence.PriorityDAO;

@Stateless
public class PriorityServiceBean implements PriorityService {

	private static final long serialVersionUID = 1L;

	private PriorityDAO priorityDAO;

	@Inject
	public PriorityServiceBean(PriorityDAO daoImpl) {
		this.priorityDAO = daoImpl;
	}

	@Override
	public Priority getPriority(Long id) {
		return priorityDAO.byId(id);
	}

	@Override
	public Collection<Priority> getAvailablePriorities() {
		return priorityDAO.listAll();
	}
}