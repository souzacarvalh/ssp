package org.ffsc.ssp.service;

import java.util.Collection;

import org.ffsc.ssp.service.domain.Ticket;
import org.ffsc.ssp.service.domain.TicketStatus;
import org.ffsc.ssp.service.domain.TicketStep;

public interface TicketService extends LocalService {
	Ticket getTicket(Long id);
	Long openTicket(Ticket ticket);
	Long addTicketStep(TicketStep ticketStep);
	void cancelTicket(Ticket ticket);
	Long generateTransferStep(Ticket ticket);
	void markTicketAsResolved(Ticket ticket);
	void updateTicket(Ticket ticket);
	TicketStep getTicketStep(Long id);
	Collection<Ticket> getAllTicketsByStatus(TicketStatus... status);
	Collection<Ticket> getTicketsForAuthenticatedUser();
	Collection<Ticket> getTicketsForAuthenticatedUserByStatus(TicketStatus status);
}