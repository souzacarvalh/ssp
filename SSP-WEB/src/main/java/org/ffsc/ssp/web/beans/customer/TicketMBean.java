package org.ffsc.ssp.web.beans.customer;

import static org.ffsc.ssp.web.beans.common.SSPMessage.CANCEL_TICKET_FAILED;
import static org.ffsc.ssp.web.beans.common.SSPMessage.CANCEL_TICKET_SUCCESS;
import static org.ffsc.ssp.web.beans.common.SSPMessage.MARK_TICKET_RESOLVED_FAILED;
import static org.ffsc.ssp.web.beans.common.SSPMessage.MARK_TICKET_RESOLVED_SUCCESS;
import static org.ffsc.ssp.web.beans.common.SSPMessage.OPEN_TICKET_FAILED;
import static org.ffsc.ssp.web.beans.common.SSPMessage.OPEN_TICKET_SUCCESS;
import static org.ffsc.ssp.web.beans.common.SSPMessage.OPERATION_SUCCESS;

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

import org.ffsc.ssp.service.CategoryService;
import org.ffsc.ssp.service.PriorityService;
import org.ffsc.ssp.service.ProductService;
import org.ffsc.ssp.service.TicketService;
import org.ffsc.ssp.service.TicketStatusService;
import org.ffsc.ssp.service.domain.Category;
import org.ffsc.ssp.service.domain.Priority;
import org.ffsc.ssp.service.domain.Product;
import org.ffsc.ssp.service.domain.Ticket;
import org.ffsc.ssp.service.domain.TicketStatus;
import org.ffsc.ssp.service.domain.TicketStep;
import org.ffsc.ssp.service.exception.SSPBusinessException;
import org.ffsc.ssp.web.beans.common.AttachmentMBean;
import org.ffsc.ssp.web.beans.common.Navigation;
import org.ffsc.ssp.web.beans.common.PopupHelper;
import org.ffsc.ssp.web.beans.layout.SSPStyleClass;
import org.ffsc.ssp.web.security.UserInfo;
import org.ffsc.ssp.web.util.FacesContextHelper;
import org.ffsc.ssp.web.util.MessageProvider;
import org.ffsc.ssp.web.util.TimeUtils;
import org.primefaces.component.accordionpanel.AccordionPanel;
import org.primefaces.context.RequestContext;

import com.sun.faces.context.flash.ELFlash;

@ManagedBean
@ViewScoped
public class TicketMBean {
	
	private static final String STEPS_TAB_INDEX = "2";
		
	private MessageProvider messageProvider;
		
	@ManagedProperty(value="#{attachmentMBean}")
	private AttachmentMBean attachmentMBean;
	
	@ManagedProperty(value="#{userInfo}")
	private UserInfo userInfo;
	
	private AccordionPanel accordion;
	
	private Ticket ticket;
	private TicketStatus initialStatus;
	private List<TicketStep> sortedSteps = new ArrayList<>();
	
	private List<TicketStatus> availableStatus = new ArrayList<>();
	private List<Product> availableProducts = new ArrayList<>();
	private List<Category> availableCategories = new ArrayList<>();
	private List<Priority> availablePriorities = new ArrayList<>();
	
	/*
	 * Service Providers
	 */
	
	private TicketService ticketService;
	private TicketStatusService statusService;
	private ProductService productService;
	private CategoryService categoryService;
	private PriorityService priorityService;

	@PostConstruct
	private void initialize() {
		try {		
			
			availableStatus.addAll(statusService.getAvailableStatus());
			availableProducts.addAll(productService.getAvailableProducts());
			availablePriorities.addAll(priorityService.getAvailablePriorities());
			initialStatus  = statusService.getInitialStatus();
			
		} catch(SSPBusinessException e) {
			e.printStackTrace();
		}
 	}
	
	public void preRenderEvents(){

		if(ELFlash.getFlash().get("newStepAddedMsg") != null){
			
			String header  = messageProvider.getValue(OPERATION_SUCCESS);
			String message = (String) ELFlash.getFlash().get("newStepAddedMsg");
			
			accordion.setActiveIndex(STEPS_TAB_INDEX);
			
			FacesContextHelper.addGlobalMessage(FacesMessage.SEVERITY_INFO, header, message);
		}		

		if(ELFlash.getFlash().get("ticketEditedMsg") != null){
			String header  = messageProvider.getValue(OPERATION_SUCCESS);
			String message = (String) ELFlash.getFlash().get("ticketEditedMsg");			
			FacesContextHelper.addGlobalMessage(FacesMessage.SEVERITY_INFO, header, message);
		}
			
		loadTicketThread();
	}
	
	public AccordionPanel getAccordion() {
		return accordion;
	}

	public void setAccordion(AccordionPanel accordion) {
		this.accordion = accordion;
	}
		
	public List<TicketStep> getSortedSteps() {
		return sortedSteps;
	}

	public void setSortedSteps(List<TicketStep> steps) {
		this.sortedSteps = steps;
	}

	public List<TicketStatus> getAvailableStatus() {
		return availableStatus;
	}
	
	public List<Product> getAvailableProducts() {
		return availableProducts;
	}
	
	public List<Category> getAvailableCategories(){
		return availableCategories;
	}
	
	public List<Priority> getAvailablePriorities(){
		return availablePriorities;
	}
	
	public String getRequestDate() {
		return ticket.getOpenDate() != null ? 
					TimeUtils.getDateAsString(ticket.getOpenDate()) : TimeUtils.getDateAsString();
	}
	
	public String getCloseDate() {
		return ticket.getCloseDate() != null ? 
					TimeUtils.getDateAsString(ticket.getCloseDate()) : " - ";
	}
	
	public Ticket getTicket() {
		if(ticket == null){
			ticket = new Ticket();
			ticket.setStatus(initialStatus);
		}
		
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
	
	public TicketStatus getInitialStatus() {
		return initialStatus;
	}
	
	public void setAttachmentMBean(AttachmentMBean attachmentMBean){
		this.attachmentMBean = attachmentMBean;
	}
	
	public AttachmentMBean getAttachmentMBean(){
		return this.attachmentMBean;
	}
	
	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public String getStyleStatusColumn(){
		if(ticket.isResolved()){
			return SSPStyleClass.GREEN_BG_CELL.getCssClassName();
		}
		
		if(ticket.isCanceled()){
			return SSPStyleClass.GRAY_BG_CELL.getCssClassName();
		}
		return null;
	}
	
	/*
	 * Action Methods ...
	 */
	
	private void loadTicketThread(){
		sortedSteps.clear();
		
		if(userInfo.isInternal()){
			sortedSteps.addAll(ticket.getTicketThread());
		} else {
			sortedSteps.addAll(ticket.getPublicThread());
		}
	}
	
	public void loadCategories(){
		availableCategories.clear();
		
		if(ticket.getProduct() != null){
			availableCategories.addAll(categoryService.getCategoriesByProduct(ticket.getProduct()));
		}
	}
	
	public String onSaveTicket() {
		try {
			ticket.setAttachments(attachmentMBean.getUploadsAsAttachment());

			Long id = ticketService.openTicket(ticket);

			ELFlash.getFlash().put("ticketOpenedMsg", messageProvider.getValue(OPEN_TICKET_SUCCESS, id));
					
			return Navigation.CUSTOMER_HOME_PAGE_REDIRECT.getPath();
			
		} catch (SSPBusinessException e) {
			FacesContextHelper.showMessageDialog(FacesMessage.SEVERITY_ERROR, 
					messageProvider.getValue(OPEN_TICKET_FAILED));
		}
		
		return null;
	}
	
	public String markTicketAsResolved(){
		try {
			
			ticketService.markTicketAsResolved(ticket);
			
			ELFlash.getFlash().put("ticketMarkedAsResolvedMsg", 
					messageProvider.getValue(MARK_TICKET_RESOLVED_SUCCESS,ticket.getId()));
						
			return Navigation.CUSTOMER_HOME_PAGE_REDIRECT.getPath();
			
		} catch (SSPBusinessException e) {
			FacesContextHelper.showMessageDialog(FacesMessage.SEVERITY_ERROR
					, messageProvider.getValue(MARK_TICKET_RESOLVED_FAILED));
		}
		
		return Navigation.CURRENT_PAGE.getPath();
	}
	
	public void cancelTicket(){
		try {

			ticketService.cancelTicket(ticket);
			closeDialog();

		} catch (SSPBusinessException e) {
			FacesContextHelper.showMessageDialog(FacesMessage.SEVERITY_ERROR, 
			messageProvider.getValue(CANCEL_TICKET_FAILED));
		}
	}
		
	public void openCancelTicketDialog(){
		
		Map<String, Object> options = new HashMap<>();

		options.put("modal", true);
		options.put("draggable", false);
		options.put("resizable", false);
		options.put("width", 544);
		options.put("height", 250);
		options.put("contentWidth", 515);
		options.put("contentHeight", 240);
		
		List<String> params = new ArrayList<>();
		
		params.add(ticket.getId().toString());
		
		Map<String, List<String>> paramsMap = new HashMap<>();
		paramsMap.put("ticket", params);
		
		RequestContext.getCurrentInstance().openDialog("cancelTicket", options, paramsMap);
	}
	
	public String onCancel(){
		
		ELFlash.getFlash().put("ticketCanceledMsg", 
				messageProvider.getValue(CANCEL_TICKET_SUCCESS,ticket.getId()));
		
		return Navigation.CUSTOMER_HOME_PAGE_REDIRECT.getPath();
	}
	
	public void openTicketDetailsDialog(){
		
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
		
		RequestContext.getCurrentInstance().openDialog("viewTicketDescription", options, paramsMap);
	}
	
	public void closeDialog() {
		PopupHelper.closeCurrentDialog();
	}
	
	@EJB
	public void setTicketService(TicketService ticketService) {
		this.ticketService = ticketService;
	}
	
	@EJB
	public void setStatusService(TicketStatusService statusService) {
		this.statusService = statusService;
	}
	
	@EJB
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	
	@EJB
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@EJB
	public void setPriorityService(PriorityService priorityService) {
		this.priorityService = priorityService;
	}
	
	@Inject
	public void setMessageProvider(MessageProvider messageProvider) {
		this.messageProvider = messageProvider;
	}
}