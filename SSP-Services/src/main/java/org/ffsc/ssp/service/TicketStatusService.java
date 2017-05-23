package org.ffsc.ssp.service;

import java.util.Collection;

import org.ffsc.ssp.service.domain.TicketStatus;

public interface TicketStatusService extends LocalService {
	TicketStatus getStatus(Long id);
	TicketStatus getStatus(TicketStatus.DefaultStatus status);
	TicketStatus getInitialStatus();
	Collection<TicketStatus> getAvailableStatus();
}