package org.ffsc.ssp.service.persistence;

import org.ffsc.ssp.service.domain.TicketStatus;

public interface TicketStatusDAO extends GenericDAO<TicketStatus, Long> {

	TicketStatus byName(String name);

	TicketStatus byDefautStatusValue(TicketStatus.DefaultStatus status);

}