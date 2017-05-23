package org.ffsc.ssp.service.impl;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.ffsc.ssp.service.TicketStatusService;
import org.ffsc.ssp.service.domain.TicketStatus;
import org.ffsc.ssp.service.domain.TicketStatus.DefaultStatus;
import org.ffsc.ssp.service.persistence.TicketStatusDAO;

@Stateless
public class TicketStatusServiceBean implements TicketStatusService {

	private static final long serialVersionUID = 1L;

	private TicketStatusDAO statusDao;

	@Inject
	public TicketStatusServiceBean(TicketStatusDAO daoImpl) {
		this.statusDao = daoImpl;
	}

	@Override
	public TicketStatus getStatus(Long id) {
		return statusDao.byId(id);
	}

	@Override
	public TicketStatus getStatus(DefaultStatus status) {
		return statusDao.byDefautStatusValue(status);
	}

	@Override
	public TicketStatus getInitialStatus() {
		return statusDao.byDefautStatusValue(TicketStatus.DefaultStatus.OPENED);
	}

	@Override
	public Collection<TicketStatus> getAvailableStatus() {
		return statusDao.listAll();
	}
}