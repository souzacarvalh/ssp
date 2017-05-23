package org.ffsc.ssp.service;

import java.util.Collection;

import org.ffsc.ssp.service.domain.Ticket;
import org.ffsc.ssp.service.domain.TicketStatus;
import org.ffsc.ssp.service.domain.Treatment;

public interface TreatmentService extends LocalService {

	void generateTreatment(Ticket ticket);

	void startTreatment(Ticket ticket);

	void finishTreatment(Ticket ticket);

	void updateTreatment(Treatment treatment);

	void transferTreatment(Ticket ticket);

	Collection<Treatment> getTreatmentsTaskListForUser();

	Collection<Treatment> getTreatmentsTaskListForUserByStatus(TicketStatus status);

	Collection<Treatment> getTreatmentsReadyForRating();

}