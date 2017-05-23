package org.ffsc.ssp.web.beans.support;

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

import org.ffsc.ssp.service.TicketStatusService;
import org.ffsc.ssp.service.TreatmentService;
import org.ffsc.ssp.service.domain.TicketStatus;
import org.ffsc.ssp.service.domain.Treatment;
import org.ffsc.ssp.service.exception.SSPBusinessException;
import org.ffsc.ssp.web.util.FacesContextHelper;
import org.ffsc.ssp.web.util.MessageProvider;
import org.primefaces.event.SelectEvent;

import com.sun.faces.context.flash.ELFlash;

@ManagedBean(name = "taskListMBean")
@ViewScoped
public class TreatmentsMBean {

	private static final String ODD_ROW_STYLE = "odd-row";
	private static final String SLA_GREEN_INDICATOR_ICON  = "images/icons/sla-green.png";
	private static final String SLA_YELLOW_INDICATOR_ICON = "images/icons/sla-yellow.png";
	private static final String SLA_RED_INDICATOR_ICON    = "images/icons/sla-red.png";

	private MessageProvider messageProvider;

	private TicketStatus statusFilter;
	
	private List<TicketStatus> availableStatus;
	private List<Treatment> treatmentTaskList = new ArrayList<>();

	@EJB
	private TicketStatusService statusService;

	@EJB
	private TreatmentService treatmentService;

	@PostConstruct
	private void initialize() {
		try {

			availableStatus = ((List<TicketStatus>) statusService.getAvailableStatus());
			treatmentTaskList.addAll(treatmentService.getTreatmentsTaskListForUser());

		} catch (SSPBusinessException e) {
			e.printStackTrace();
		}
	}

	public void preRenderEvents() {
		
		if (ELFlash.getFlash().get("ticketMarkedAsResolvedMsg") != null) {
			String header  = messageProvider.getValue("ssp_message_operation_done");
			String message = (String) ELFlash.getFlash().get("ticketMarkedAsResolvedMsg");
			FacesContextHelper.addGlobalMessage(FacesMessage.SEVERITY_INFO, header, message);
		}
		
		if(ELFlash.getFlash().get("ticketCanceledMsg") != null){
			String header  = messageProvider.getValue("ssp_message_operation_done");
			String message = (String) ELFlash.getFlash().get("ticketCanceledMsg");
			FacesContextHelper.addGlobalMessage(FacesMessage.SEVERITY_INFO, header, message);
		}
		
		if(ELFlash.getFlash().get("ticketTransferedMsg") != null){
			String header  = messageProvider.getValue("ssp_message_operation_done");
			String message = (String) ELFlash.getFlash().get("ticketTransferedMsg");
			FacesContextHelper.addGlobalMessage(FacesMessage.SEVERITY_INFO, header, message);
		}
	}

	public String getEmptyTableMessage() {
		return (statusFilter != null) ? messageProvider
				.getValue("ssp_service_empty_table_status_filtered")
				: messageProvider.getValue("ssp_service_empty_table");
	}

	public List<TicketStatus> getAvailableStatus() {
		return availableStatus;
	}

	public TicketStatus getStatusFilter() {
		return statusFilter;
	}

	public void setStatusFilter(TicketStatus statusFilter) {
		this.statusFilter = statusFilter;
	}

	public List<Treatment> getTreatmentTaskList() {
		return treatmentTaskList;
	}
	
	public String getRowColorIndicator(int rowIndex) {
		if (rowIndex % 2 != 0) {
			return ODD_ROW_STYLE;
		}
		return null;
	}

	public void filterTaskListByStatus() {
		
		treatmentTaskList.clear();

		try {

			if (statusFilter != null) {
				treatmentTaskList.addAll(treatmentService
						.getTreatmentsTaskListForUserByStatus(statusFilter));
			} else {
				treatmentTaskList.addAll(treatmentService.getTreatmentsTaskListForUser());
			}

		} catch (SSPBusinessException e) {
			FacesContextHelper.showMessageDialog(FacesMessage.SEVERITY_ERROR,
					messageProvider.getValue("ssp_message_filter_tickets_fail"));
		}
	}
	
	public String getSLAIndicator(Treatment treatment){
		if(treatment != null){
			if(treatment.isExceededLimitTime()){
				return SLA_RED_INDICATOR_ICON;
			}
			if(treatment.isExceededWarningTime()){
				return SLA_YELLOW_INDICATOR_ICON;
			}
		}
		
		return SLA_GREEN_INDICATOR_ICON;
	}

	public void viewTicket(SelectEvent event) {

		Treatment treatment = ((Treatment) event.getObject());

		if (treatment != null) {
			Map<String, Object> params = new HashMap<>();
			params.put("ticket", treatment.getTicket().getId());

			FacesContextHelper.redirect(
					"ssp/pages/support/tickets/viewTicket.jsf", params);
		}
	}
	
	@Inject
	public void setMessageProvider(MessageProvider messageProvider) {
		this.messageProvider = messageProvider;
	}
}