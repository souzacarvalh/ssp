package org.ffsc.ssp.web.beans.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.ffsc.ssp.service.AnalystSevice;
import org.ffsc.ssp.service.CategoryService;
import org.ffsc.ssp.service.GroupService;
import org.ffsc.ssp.service.TicketService;
import org.ffsc.ssp.service.TreatmentService;
import org.ffsc.ssp.service.domain.Analyst;
import org.ffsc.ssp.service.domain.Category;
import org.ffsc.ssp.service.domain.Group;
import org.ffsc.ssp.service.domain.Ticket;
import org.ffsc.ssp.service.exception.SSPBusinessException;
import org.ffsc.ssp.web.beans.common.Navigation;
import org.ffsc.ssp.web.beans.common.PopupHelper;
import org.ffsc.ssp.web.security.UserInfo;
import org.ffsc.ssp.web.util.FacesContextHelper;
import org.ffsc.ssp.web.util.MessageProvider;
import org.primefaces.context.RequestContext;

import com.sun.faces.context.flash.ELFlash;

@ManagedBean
@ViewScoped
public class TreatmentMBean {
	
	private MessageProvider messageProvider;
	
	private Ticket ticket;
	
	@ManagedProperty(value="#{userInfo}")
	private UserInfo userInfo;
	
	@ManagedProperty(value="#{navigation}")
	private Navigation navigation;
	
	/*
	 * Services
	 */
	
	@EJB
	private TreatmentService treatmentService;
	
	@EJB
	private CategoryService categoryService;
	
	@EJB
	private TicketService ticketService;
	
	@EJB
	private GroupService groupService;
	
	@EJB
	private AnalystSevice analystSevice;
	
	private List<Category> availableCategories = new ArrayList<>();
	private List<Group> availableGroups = new ArrayList<>();
	private List<Analyst> availableAnalysts = new ArrayList<>();
	
	@PostConstruct
	private void initialize() {
		availableGroups = (List<Group>) groupService.getAvailableGroups();
 	}
	
	public void loadAnalysts(){
	
		availableAnalysts.clear();
		
		Group group = ticket.getTreatmentInfo().getSupportGroup();
		
		if(group != null){
			availableAnalysts = (List<Analyst>) analystSevice.getAnalystsByGroup(group);
		}
	}
	
	public void loadCategories(){

		availableCategories.clear();
			
		if(ticket.getProduct() != null){
			availableCategories  = (List<Category>) categoryService.
										getCategoriesByProduct(ticket.getProduct());
		}
	}
	
	public List<Category> getAvailableCategories(){
		return availableCategories;
	}
	
	public List<Group> getAvailableGroups(){
		return availableGroups;
	}
	
	public List<Analyst> getAvailableAnalysts(){
		return availableAnalysts;
	}
	
	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	
	public Navigation getNavigation() {
		return navigation;
	}

	public void setNavigation(Navigation navigation) {
		this.navigation = navigation;
	}
	
	public void startTreatment(){
		try {
			if(ticket != null){
				treatmentService.startTreatment(ticket);
				redirectToViewTicket(ticket);
			}
		} catch (SSPBusinessException e) {
			FacesContextHelper.showMessageDialog(FacesMessage.SEVERITY_ERROR, 
					messageProvider.getValue("ssp_message_start_treatment_fail"));
		}
	}
		
	public void openTransferDialog(){
		
		Map<String, Object> options = new HashMap<>();

		options.put("modal", true);
		options.put("draggable", true);
		options.put("resizable", false);
		options.put("width", 510);
		options.put("height", 200);
		options.put("contentWidth", 480);
		options.put("contentHeight", 195);
		options.put("includeViewParams", true);
		
		List<String> params = new ArrayList<>();
		
		params.add(ticket.getId().toString());
		
		Map<String, List<String>> paramsMap = new HashMap<>();
		paramsMap.put("ticket", params);
		
		RequestContext.getCurrentInstance().openDialog("transferTicket", options, paramsMap);
	}
	
	public void transferTicket(){
		try {
			if(ticket != null){
				treatmentService.transferTreatment(ticket);
			}
			
			PopupHelper.closeCurrentDialog();
			
		} catch (SSPBusinessException e) {
			FacesContextHelper.showMessageDialog(FacesMessage.SEVERITY_ERROR, 
					messageProvider.getValue("ssp_message_transf_ticket_fail"));
		}
	}
	
	public String onTransferTicket(){
		
		ELFlash.getFlash().put("ticketTransferedMsg", 
				messageProvider.getValue("ssp_message_transf_ticket_success",
				ticket.getId()));
		
		return Navigation.SUPPORT_HOME_PAGE_REDIRECT.getPath();
	}
	
	public void openEditTicketDialog(){
		
		Map<String, Object> options = new HashMap<>();

		options.put("modal", true);
		options.put("draggable", false);
		options.put("resizable", false);
		options.put("width", 670);
		options.put("height", 400);
		options.put("contentHeight", 395);
		options.put("includeViewParams", true);
		
		List<String> params = new ArrayList<>();
		
		params.add(ticket.getId().toString());
		
		Map<String, List<String>> paramsMap = new HashMap<>();
		paramsMap.put("ticket", params);
		
		RequestContext.getCurrentInstance().openDialog("editTicketInfo", options, paramsMap);
	}
	
	public void saveTicketChanges(){
		try {
			
			if(ticket != null){
				if(ticket.getTreatmentInfo().isShouldRedirect()){
					FacesContextHelper.getSession().setAttribute("ticketRouted", new Boolean(true));
				}
				
				ticketService.updateTicket(ticket);
			}
			
			PopupHelper.closeCurrentDialog();
			
		} catch (SSPBusinessException e) {
			FacesContextHelper.showMessageDialog(FacesMessage.SEVERITY_ERROR, 
					messageProvider.getValue("ssp_message_edit_ticket_fail"));
		}
	}
	
	public String onSaveTicketChanges(){
				
		Boolean ticketRouted = (Boolean) FacesContextHelper.getSession().getAttribute("ticketRouted");
		
		if(ticketRouted != null && ticketRouted){
			
			ELFlash.getFlash().put("ticketTransferedMsg", 
					messageProvider.getValue("ssp_message_transf_ticket_success",
					ticket.getId()));
			
			return Navigation.SUPPORT_HOME_PAGE_REDIRECT.getPath();
			
		} else {
			
			ELFlash.getFlash().put("ticketEditedMsg", 
					messageProvider.getValue("ssp_message_edit_ticket_success",
					ticket.getId()));
			
			redirectToViewTicket(ticket);
		}
		
		return Navigation.CURRENT_PAGE.getPath();
	}
	
	public void redirectToViewTicket(Ticket ticket) {
		if (ticket != null) {

			Map<String, Object> params = new HashMap<>();
			params.put("ticket", ticket.getId());

			FacesContextHelper.redirect(String.format("ssp/pages/%s/tickets/viewTicket.jsf"
					, userInfo.getContext()), params);
		}
	}
	
	@Inject
	public void setMessageProvider(MessageProvider messageProvider) {
		this.messageProvider = messageProvider;
	}
}