package org.ffsc.ssp.service.impl;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.ffsc.ssp.service.AuthenticationService;
import org.ffsc.ssp.service.RequesterService;
import org.ffsc.ssp.service.TicketService;
import org.ffsc.ssp.service.TicketStatusService;
import org.ffsc.ssp.service.TreatmentService;
import org.ffsc.ssp.service.domain.Credential;
import org.ffsc.ssp.service.domain.Ticket;
import org.ffsc.ssp.service.domain.TicketStatus;
import org.ffsc.ssp.service.domain.TicketStatus.DefaultStatus;
import org.ffsc.ssp.service.domain.TicketStep;
import org.ffsc.ssp.service.domain.Treatment;
import org.ffsc.ssp.service.persistence.TicketDAO;
import org.slf4j.Logger;

@Stateless
public class TicketServiceBean implements TicketService {

	private static final long serialVersionUID = 1L;

	@Inject
	private Logger logger;

	@EJB
	private TreatmentService treatmentService;

	@EJB
	private AuthenticationService authenticationService;

	@EJB
	private TicketStatusService statusService;

	@EJB
	private RequesterService requesterService;

	private TicketDAO ticketDAO;

	@Inject
	public TicketServiceBean(TicketDAO daoImpl) {
		this.ticketDAO = daoImpl;
	}

	@Override
	public Ticket getTicket(Long id) {
		return ticketDAO.byId(id);
	}

	@Override
	public Long openTicket(Ticket ticket) {

		logger.info("A new ticket is being registered ...");

		Credential authenticated = authenticationService.getAuthenticated();

		ticket.setOpenDate(new Date(System.currentTimeMillis()));
		ticket.setStatus(statusService.getInitialStatus());
		ticket.setRequester(requesterService.getRequester(authenticated));

		ticketDAO.saveOrUpdate(ticket);

		treatmentService.generateTreatment(ticket);

		logger.info(MessageFormat.format(
				"Ticket #{0} registered successfully.", ticket.getId()));

		return ticket.getId();
	}

	@Override
	public void updateTicket(Ticket ticket) {

		analyseTicketStatusOnUpdate(ticket);

		if (!ticket.isClosed()) {
			treatmentService.updateTreatment(ticket.getTreatmentInfo());
		}

		ticketDAO.saveOrUpdate(ticket);

		logger.info(MessageFormat.format("Chamado #{0} atualizado",
				ticket.getId()));
	}

	@Override
	public Long addTicketStep(TicketStep step) {

		Credential authenticated = authenticationService.getAuthenticated();

		step.setDate(new Date(System.currentTimeMillis()));
		step.setRequester(requesterService.getRequester(authenticated));

		ticketDAO.addStep(step);

		logger.info(MessageFormat.format("New step #{0} added to ticket #{1}",
				step.getId(), step.getTicket().getId()));

		return step.getId();
	}

	@Override
	public Long generateTransferStep(Ticket ticket) {

		Credential authenticated = authenticationService.getAuthenticated();

		Treatment treatmentInfo = ticket.getTreatmentInfo();

		TicketStep transferStep = new TicketStep();

		transferStep.setTicket(ticket);
		transferStep.setDate(new Date(System.currentTimeMillis()));
		transferStep.setRequester(requesterService.getRequester(authenticated));
		transferStep.setInternal(true);

		String stepDescription = MessageFormat
				.format("User ID {0} transfered the ticket for analysis. New responsibles - Group ID: {1} / "
						+ "Analyst: {2}",
						authenticated.getAnalyst(),
						treatmentInfo.getSupportGroup() != null ? treatmentInfo
								.getSupportGroup() : "Not Assigned",
						treatmentInfo.getSupportAnalyst() != null ? treatmentInfo
								.getSupportAnalyst() : "Not Assigned");

		transferStep.setDescription(stepDescription);

		ticketDAO.addStep(transferStep);

		logger.info(MessageFormat.format(
				"Internal treansfer step #{0} added to ticket #{1}",
				transferStep.getId(), transferStep.getTicket().getId()));

		return transferStep.getId();
	}

	@Override
	public void markTicketAsResolved(Ticket ticket) {

		Credential authenticated = authenticationService.getAuthenticated();

		TicketStatus resolvedStatus = statusService
				.getStatus(TicketStatus.DefaultStatus.RESOLVED);

		ticket.setStatus(resolvedStatus);
		ticket.setCloseDate(new Date(System.currentTimeMillis()));
		ticket.setClosedBy(authenticated.getUsername());

		ticketDAO.saveOrUpdate(ticket);

		treatmentService.finishTreatment(ticket);

		logger.info(MessageFormat.format("Ticket #{0} marked as resolved.",
				ticket.getId()));
	}

	@Override
	public void cancelTicket(Ticket ticket) {

		Credential authenticated = authenticationService.getAuthenticated();

		TicketStatus canceledStatus = statusService
				.getStatus(DefaultStatus.CANCELED);

		ticket.setStatus(canceledStatus);
		ticket.setCloseDate(new Date(System.currentTimeMillis()));
		ticket.setClosedBy(authenticated.getUsername());
		;

		ticketDAO.saveOrUpdate(ticket);

		treatmentService.finishTreatment(ticket);

		logger.info(MessageFormat.format("Ticket #{0} canceled", ticket.getId()));
	}

	@Override
	public TicketStep getTicketStep(Long id) {
		return ticketDAO.getStepById(id);
	}

	@Override
	public Collection<Ticket> getAllTicketsByStatus(TicketStatus... status) {
		return ticketDAO.listByStatus(status);
	}

	@Override
	public Collection<Ticket> getTicketsForAuthenticatedUser() {

		Credential authenticated = authenticationService.getAuthenticated();

		return ticketDAO.listByRequester(authenticated.getRequester());
	}

	@Override
	public Collection<Ticket> getTicketsForAuthenticatedUserByStatus(
			TicketStatus status) {

		Credential authenticated = authenticationService.getAuthenticated();

		return ticketDAO.listByRequesterAndStatus(authenticated.getRequester(),
				status);
	}

	private void analyseTicketStatusOnUpdate(Ticket ticket) {

		TicketStatus.DefaultStatus actualTicketStatus = TicketStatus.DefaultStatus
				.getByLabel(ticket.getStatus().getName());

		if (TicketStatus.DefaultStatus.RESOLVED.equals(actualTicketStatus)) {
			markTicketAsResolved(ticket);
			return;
		}

		if (TicketStatus.DefaultStatus.CANCELED.equals(actualTicketStatus)) {
			ticket.setCancelReason("Status CANCELED informed by the support team");
			cancelTicket(ticket);
		}
	}
}