package org.ffsc.ssp.web.beans.customer;

import static org.ffsc.ssp.web.beans.common.SSPMessage.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.ffsc.ssp.service.TicketService;
import org.ffsc.ssp.service.TicketStatusService;
import org.ffsc.ssp.service.domain.Ticket;
import org.ffsc.ssp.service.domain.TicketStatus;
import org.ffsc.ssp.web.beans.layout.SSPStyleClass;
import org.ffsc.ssp.web.util.FacesContextHelper;
import org.ffsc.ssp.web.util.MessageProvider;
import org.primefaces.event.SelectEvent;

import com.sun.faces.context.flash.ELFlash;

@ManagedBean
@ViewScoped
public class TicketsMBean {
	
	private MessageProvider messageProvider;
	
	private TicketStatus statusFilter;
	private List<Ticket> supportRequests = new ArrayList<>();
	private List<TicketStatus> availableStatus = new ArrayList<>();
	
	private TicketStatusService statusService;
	private TicketService ticketService;
	
	@PostConstruct
	private void initialize() {
		availableStatus.addAll(statusService.getAvailableStatus());
		supportRequests.addAll(ticketService.getTicketsForAuthenticatedUser());
	}

	public void preRenderEvents() {
		if(ELFlash.getFlash().get("ticketOpenedMsg") != null) {
			String header  = (String) ELFlash.getFlash().get("ticketOpenedMsg");
			String message = messageProvider.getValue(OPEN_TICKET_DETAILS);
			FacesContextHelper.addGlobalMessage(FacesMessage.SEVERITY_INFO, header, message);
		}
		
		if(ELFlash.getFlash().get("ticketMarkedAsResolvedMsg") != null) {
			String header  = messageProvider.getValue(OPERATION_SUCCESS);
			String message = (String) ELFlash.getFlash().get("ticketMarkedAsResolvedMsg");
			FacesContextHelper.addGlobalMessage(FacesMessage.SEVERITY_INFO, header, message);
		}
		
		if(ELFlash.getFlash().get("ticketCanceledMsg") != null) {
			String header  = messageProvider.getValue(OPERATION_SUCCESS);
			String message = (String) ELFlash.getFlash().get("ticketCanceledMsg");
			FacesContextHelper.addGlobalMessage(FacesMessage.SEVERITY_INFO, header, message);
		}
	}
	
	public String getEmptyTableMessage() {
		return (statusFilter != null) ? messageProvider.getValue(REQUESTS_NOT_AVAILABLE_STATUS) 
				:  messageProvider.getValue(REQUESTS_NOT_AVAILABLE);
	}

	public List<TicketStatus> getAvailableStatus(){
		return availableStatus;
	}
	
	public TicketStatus getStatusFilter() {
		return statusFilter;
	}

	public void setStatusFilter(TicketStatus statusFilter) {
		this.statusFilter = statusFilter;
	}

	public List<Ticket> getSupportRequests() {
		return supportRequests;
	}
	
	public String getRowColorIndicator(int rowIndex) {
		return rowIndex % 2 != 0 ? SSPStyleClass.ODD_ROW.getCssClassName() : null;
	}
	
	public void filterRequestsByStatus() {
		supportRequests.clear();
		if(statusFilter != null){
			supportRequests.addAll(ticketService.getTicketsForAuthenticatedUserByStatus(statusFilter));
		} else {
			supportRequests.addAll(ticketService.getTicketsForAuthenticatedUser());
		}
	}
	
	public void viewRequestTicket(SelectEvent event) {
		Ticket ticket = ((Ticket) event.getObject());
		
		if(ticket != null) {
			Map<String, Object> params = new HashMap<>();
			params.put("ticket", ticket.getId());
			
			FacesContextHelper.redirect("ssp/pages/customer/tickets/viewTicket.jsf", params);
		}
	}
	
	@EJB
	public void setTicketStatusService(TicketStatusService statusService) {
		this.statusService = statusService;
	}
	
	@EJB
	public void setTicketService(TicketService ticketService) {
		this.ticketService = ticketService;
	}
	
	@Inject
	public void setMessageProvider(MessageProvider messageProvider) {
		this.messageProvider = messageProvider;
	}
}