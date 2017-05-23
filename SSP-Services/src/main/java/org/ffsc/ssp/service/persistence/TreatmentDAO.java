package org.ffsc.ssp.service.persistence;

import java.util.Collection;

import org.ffsc.ssp.service.domain.Analyst;
import org.ffsc.ssp.service.domain.Group;
import org.ffsc.ssp.service.domain.TicketStatus;
import org.ffsc.ssp.service.domain.Treatment;

public interface TreatmentDAO extends GenericDAO<Treatment, Long> {

	Collection<Treatment> listNotAssignedTreatments(
			TicketStatus... statusList);

	Collection<Treatment> listTreatmentsForGroup(Group group, TicketStatus... statusList);

	Collection<Treatment> listTreatmentsForAnalyst(Analyst analyst,
			TicketStatus... status);

	Collection<Treatment> listTreatmentsReadyForRating();

}