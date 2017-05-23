package org.ffsc.ssp.service.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.ffsc.ssp.service.AnalystSevice;
import org.ffsc.ssp.service.AuthenticationService;
import org.ffsc.ssp.service.RoutingService;
import org.ffsc.ssp.service.TicketService;
import org.ffsc.ssp.service.TicketStatusService;
import org.ffsc.ssp.service.TreatmentService;
import org.ffsc.ssp.service.domain.Analyst;
import org.ffsc.ssp.service.domain.Credential;
import org.ffsc.ssp.service.domain.Rating;
import org.ffsc.ssp.service.domain.Ticket;
import org.ffsc.ssp.service.domain.TicketStatus;
import org.ffsc.ssp.service.domain.Treatment;
import org.ffsc.ssp.service.persistence.TreatmentDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class TreatmentServiceBean implements TreatmentService {

	private static final long serialVersionUID = 1L;

	private final Logger logger = LoggerFactory
			.getLogger(TreatmentServiceBean.class);

	@EJB
	private AuthenticationService authenticationService;

	@EJB
	private TicketService ticketService;

	@EJB
	private RoutingService routingService;

	@EJB
	private AnalystSevice analystSevice;

	@EJB
	private TicketStatusService statusService;

	private TreatmentDAO treatmentDAO;

	@Inject
	public TreatmentServiceBean(TreatmentDAO daoImpl) {
		this.treatmentDAO = daoImpl;
	}

	@Override
	public void generateTreatment(Ticket ticket) {

		logger.info(MessageFormat.format(
				"Starting treatment for ticket #{0} and routing ",
				ticket.getId()));

		treatmentDAO.saveOrUpdate(doGenerateTreatment(ticket));

		logger.info(MessageFormat.format("Routing for ticket #{0} finished.",
				ticket.getId()));
	}

	@Override
	public void startTreatment(Ticket ticket) {

		Credential authenticated = authenticationService.getAuthenticated();

		logger.info(MessageFormat.format(
				"Analyst Id {0} started the treatment for ticket #{1} ",
				authenticated.getAnalyst(), ticket.getId()));

		TicketStatus inAnalysisStatus = statusService
				.getStatus(TicketStatus.DefaultStatus.IN_ANALYSIS);

		ticket.setStatus(inAnalysisStatus);

		ticketService.updateTicket(ticket);

		Treatment treatmentInfo = ticket.getTreatmentInfo();

		treatmentInfo.setActive(true);

		Analyst analyst = analystSevice.getAnalyst(authenticated);

		treatmentInfo.setSupportGroup(analyst.getGroup());
		treatmentInfo.setSupportAnalyst(analyst);

		treatmentDAO.saveOrUpdate(treatmentInfo);
	}

	@Override
	public void updateTreatment(Treatment treatment) {

		Ticket ticket = treatment.getTicket();

		if (treatment.isShouldRedirect()) {
			ticket = routingService.routeTicket(ticket);			
			treatmentDAO.saveOrUpdate(ticket.getTreatmentInfo());
			ticketService.generateTransferStep(ticket);
		}

		treatmentDAO.saveOrUpdate(treatment);
	}

	@Override
	public void transferTreatment(Ticket ticket) {

		logger.info(MessageFormat.format(
				"Treatment for ticket #{0} transfered.", ticket.getId()));

		Treatment treatment = ticket.getTreatmentInfo();

		if (treatment.getSupportAnalyst() == null) {
			treatment.setActive(false);
		}

		ticketService.generateTransferStep(ticket);

		treatmentDAO.saveOrUpdate(treatment);
	}

	@Override
	public void finishTreatment(Ticket ticket) {

		logger.info(MessageFormat.format("Treatment for ticket #{0} finished.",
				ticket.getId()));

		Treatment treatment = ticket.getTreatmentInfo();

		treatment.setEndDate(new Date(System.currentTimeMillis()));
		treatment.setActive(false);
		treatment.setClosed(true);
		treatment.setRating(new Rating());

		treatmentDAO.saveOrUpdate(treatment);
	}

	@Override
	public Collection<Treatment> getTreatmentsTaskListForUser() {

		Credential authenticated = authenticationService.getAuthenticated();

		Analyst analyst = analystSevice.getAnalyst(authenticated);

		return getTreatments(analyst, null);
	}

	@Override
	public Collection<Treatment> getTreatmentsTaskListForUserByStatus(TicketStatus status) {

		Credential authenticated = authenticationService.getAuthenticated();

		Analyst analyst = analystSevice.getAnalyst(authenticated);

		return getTreatments(analyst, status);
	}

	private List<Treatment> getTreatments(Analyst analyst, TicketStatus status) {

		List<Treatment> treatments = new ArrayList<>();

		treatments.addAll(treatmentDAO
				.listTreatmentsForAnalyst(analyst, status));

		treatments.addAll(treatmentDAO.listTreatmentsForGroup(
				analyst.getGroup(), status));

		if (analyst.getGroup().isReceiveNotRouted()) {
			treatments.addAll(treatmentDAO.listNotAssignedTreatments(status));
		}

		return treatments;
	}

	private Treatment doGenerateTreatment(Ticket ticket) {

		Treatment treatment = new Treatment();

		treatment.setTicket(ticket);
		treatment.setStartDate(new Date(System.currentTimeMillis()));
		treatment.setActive(true);

		ticket.setTreatmentInfo(treatment);

		routingService.routeTicket(ticket);

		return treatment;
	}


	@Override
	public Collection<Treatment> getTreatmentsReadyForRating() {
		return treatmentDAO.listTreatmentsReadyForRating();
	}
}