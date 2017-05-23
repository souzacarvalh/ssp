package org.ffsc.ssp.service.persistence;

import java.util.Collection;

import org.ffsc.ssp.service.domain.Requester;
import org.ffsc.ssp.service.domain.Ticket;
import org.ffsc.ssp.service.domain.TicketStatus;
import org.ffsc.ssp.service.domain.TicketStep;

public interface TicketDAO extends GenericDAO<Ticket, Long> {

	TicketStep getStepById(Long id);
	
	Long addStep(TicketStep ticketStep);

	Collection<Ticket> listByRequester(Requester requester);

	Collection<Ticket> listByStatus(TicketStatus... status);

	Collection<Ticket> listByRequesterAndStatus(Requester requester, TicketStatus status);

}