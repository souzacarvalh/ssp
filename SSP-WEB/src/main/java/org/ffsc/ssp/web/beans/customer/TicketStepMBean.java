package org.ffsc.ssp.web.beans.customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.ffsc.ssp.service.TicketService;
import org.ffsc.ssp.service.domain.Ticket;
import org.ffsc.ssp.service.domain.TicketStep;
import org.ffsc.ssp.service.exception.SSPBusinessException;
import org.ffsc.ssp.web.beans.common.AttachmentMBean;
import org.ffsc.ssp.web.beans.common.PopupHelper;
import org.ffsc.ssp.web.beans.common.SSPMessage;
import org.ffsc.ssp.web.security.UserInfo;
import org.ffsc.ssp.web.util.FacesContextHelper;
import org.ffsc.ssp.web.util.MessageProvider;
import org.ffsc.ssp.web.util.TimeUtils;
import org.primefaces.context.RequestContext;

import com.sun.faces.context.flash.ELFlash;

@ManagedBean
@ViewScoped
public class TicketStepMBean {

	private MessageProvider messageProvider;
	
	private TicketService ticketService;

	private TicketStep ticketStep = new TicketStep();

	@ManagedProperty(value = "#{userInfo}")
	private UserInfo userInfo;

	@ManagedProperty(value = "#{attachmentMBean}")
	private AttachmentMBean attachmentMBean;

	
	public TicketStep getStep() {
		return ticketStep;
	}

	public void setStep(TicketStep ticketStep) {
		this.ticketStep = ticketStep;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public AttachmentMBean getAttachmentMBean() {
		return attachmentMBean;
	}

	public void setAttachmentMBean(AttachmentMBean attachmentMBean) {
		this.attachmentMBean = attachmentMBean;
	}

	public String getStepDate() {
		return getStep().getDate() != null ? TimeUtils
				.getDateAsString(getStep().getDate()) : TimeUtils
				.getDateAsString();
	}

	public String getOpenedBy() {

		if (getStep().openedBy() != null) {
			return getStep().openedBy().toString();
		}

		return userInfo.getAuthenticatedUser().toString();
	}

	public void openAddStepDialog() {

		Map<String, Object> options = new HashMap<>();

		options.put("modal", true);
		options.put("draggable", false);
		options.put("resizable", false);
		options.put("width", 670);
		options.put("height", 400);
		options.put("contentHeight", 395);
		options.put("includeViewParams", true);

		// Put the current ticket in edition in session
		FacesContextHelper.getSession().setAttribute("currentTicket",
				ticketStep.getTicket());

		RequestContext.getCurrentInstance().openDialog("addTicketStep", options,
				null);
	}

	public void viewStep(Long id) {
		if (id != null) {
			Map<String, Object> options = new HashMap<>();

			options.put("modal", true);
			options.put("draggable", false);
			options.put("resizable", false);
			options.put("width", 670);
			options.put("height", 400);
			options.put("contentHeight", 395);
			options.put("includeViewParams", true);

			List<String> params = new ArrayList<>();

			params.add(id.toString());

			Map<String, List<String>> paramsMap = new HashMap<>();
			paramsMap.put("ticketStep", params);

			RequestContext.getCurrentInstance().openDialog("viewTicketStep",
					options, paramsMap);
		}
	}

	public void addStepToTicket() {
		try {

			ticketStep.setTicket((Ticket) FacesContextHelper.getSession().getAttribute("currentTicket"));
			ticketStep.setAttachments(attachmentMBean.getUploadsAsAttachment());
			ticketService.addTicketStep(ticketStep);

			PopupHelper.closeCurrentDialog();

		} catch (SSPBusinessException e) {
			FacesContextHelper.showMessageDialog(FacesMessage.SEVERITY_ERROR,
					messageProvider.getValue(SSPMessage.ADD_STEP_TO_TICKET_FAIL.getKey()));
		}
	}

	public void onAddStep() {
		
		FacesContextHelper.getSession().setAttribute("currentTicket", null);

		ELFlash.getFlash().put("newStepAddedMsg"
				, messageProvider.getValue(SSPMessage.ADD_STEP_TO_TICKET_SUCCESS.getKey()
				, ticketStep.getTicket().getId()));

		redirectToViewTicket(ticketStep.getTicket());
	}
	
	public void redirectToViewTicket(Ticket ticket) {
		if (ticket != null) {

			Map<String, Object> params = new HashMap<>();
			params.put("ticket", ticket.getId());

			FacesContextHelper.redirect(String.format("ssp/pages/%s/tickets/viewTicket.jsf"
					, userInfo.getContext()), params);
		}
	}
	
	@EJB
	private void setTicketService(TicketService ticketService) {
		this.ticketService = ticketService;
	}

	@Inject
	public void setMessageProvider(MessageProvider messageProvider) {
		this.messageProvider = messageProvider;
	}
}