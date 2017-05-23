package org.ffsc.ssp.web.beans.admin;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.ffsc.ssp.service.AnalystSevice;
import org.ffsc.ssp.service.CategoryService;
import org.ffsc.ssp.service.GroupService;
import org.ffsc.ssp.service.ProductService;
import org.ffsc.ssp.service.RoutingService;
import org.ffsc.ssp.service.domain.Analyst;
import org.ffsc.ssp.service.domain.Category;
import org.ffsc.ssp.service.domain.Group;
import org.ffsc.ssp.service.domain.Product;
import org.ffsc.ssp.service.domain.Route;
import org.ffsc.ssp.service.exception.SSPBusinessException;
import org.ffsc.ssp.web.util.FacesContextHelper;
import org.ffsc.ssp.web.util.MessageProvider;

import com.sun.faces.context.flash.ELFlash;

@ManagedBean(name = "routesMBean")
@ViewScoped
public class ManageRoutesMBean {
	
	private MessageProvider messageProvider;

	private Route route = new Route();
		
	private List<Route> availableRoutes = new ArrayList<>();
	private List<Product> availableProducts = new ArrayList<>();
	private List<Category> availableCategories = new ArrayList<>();
	private List<Group> availableGroups = new ArrayList<>();
	private List<Analyst> availableAnalysts = new ArrayList<>();
	
	@EJB
	private RoutingService routingService;
	
	@EJB
	private ProductService productService;
	
	@EJB
	private CategoryService categoryService;
	
	@EJB
	private GroupService groupService;
	
	@EJB
	private AnalystSevice analystSevice;

	public void preRenderEvents(){
		try {
		
			setAvailableRoutes((List<Route>) routingService.getAvailableRoutes());
			setAvailableProducts((List<Product>) productService.getAvailableProducts());
			setAvailableGroups((List<Group>) groupService.getAvailableGroups());
		
		} catch (SSPBusinessException e) {
			e.printStackTrace();
		}

		if(ELFlash.getFlash().get("routeAddedMsg") != null){
			String header  = messageProvider.getValue("ssp_message_operation_done");
			String message = (String) ELFlash.getFlash().get("routeAddedMsg");
			
			FacesContextHelper.addGlobalMessage(FacesMessage.SEVERITY_INFO, header, message);
		}
	}
	
	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}
	
	public List<Product> getAvailableProducts(){
		return availableProducts;
	}
	
	public void setAvailableProducts(List<Product> products){
		this.availableProducts = products;
	}
	
	public List<Route> getAvailableRoutes() {
		return availableRoutes;
	}
	
	public void setAvailableRoutes(List<Route> routes) {
		this.availableRoutes = routes;
	}
	
	public List<Category> getAvailableCategories() {
		return availableCategories;
	}
	
	public void setAvailableCategories(List<Category> categories) {
		this.availableCategories = categories;
	}
	
	public List<Group> getAvailableGroups(){
		return this.availableGroups;
	}
	
	public void setAvailableGroups(List<Group> groups){
		this.availableGroups = groups;
	}
	
	public List<Analyst> getAvailableAnalysts(){
		return availableAnalysts;
	}
	
	public void setAvailableAnalysts(List<Analyst> analysts){
		this.availableAnalysts = analysts;
	}

	public void loadCategories(){

		availableCategories.clear();
		
		if(route.getProduct() != null){
			availableCategories  = (List<Category>) categoryService
											.getCategoriesByProduct(route.getProduct());
		}
	}
	
	public void loadAnalysts(){
		
		availableAnalysts.clear();
		
		Group group = route.getSupportGroup();
		
		if(group != null){
			availableAnalysts = (List<Analyst>) analystSevice.getAnalystsByGroup(group);
		}
	}
	
	public String getEmptyTableMessage() {
		return messageProvider.getValue("ssp_admin_routes_empty_table");
	}
	
	public String saveRoute(){
		try {
			
			if(route != null){
				
				routingService.saveRoute(route);

				ELFlash.getFlash().put("routeAddedMsg", 
						messageProvider.getValue("ssp_message_admin_route_save_success", route.getId()));
				
				route = new Route();
				
				setAvailableRoutes((List<Route>) routingService.getAvailableRoutes());
			}
			
		} catch (SSPBusinessException e) {
			FacesContextHelper.showMessageDialog(FacesMessage.SEVERITY_ERROR, 
					messageProvider.getValue("ssp_message_admin_route_save_fail"));
		}
		
		return "routes.jsf?faces-redirect=true";
	}
	
	public void deleteRoute(Route route){
		try {
			
			if(route != null){
				
				Long id = route.getId();
				
				routingService.deleteRoute(route);
				availableRoutes = (List<Route>) routingService.getAvailableRoutes();

				String header  = messageProvider.getValue("ssp_message_operation_done");
				String message = messageProvider.getValue("ssp_message_admin_route_delete_success", id);
				
				FacesContextHelper.addGlobalMessage(FacesMessage.SEVERITY_INFO, header, message);
			}
			
		} catch (SSPBusinessException e) {
			FacesContextHelper.showMessageDialog(FacesMessage.SEVERITY_ERROR, 
					messageProvider.getValue("ssp_message_admin_route_delete_fail"));
		}
	}
	
	public void reset(){
		try {
			
			setAvailableRoutes((List<Route>) routingService.getAvailableRoutes());
			setAvailableProducts((List<Product>) productService.getAvailableProducts());
			setAvailableGroups((List<Group>) groupService.getAvailableGroups());
			
			setAvailableCategories(new ArrayList<Category>());
			setAvailableAnalysts(new ArrayList<Analyst>());
			
			route = new Route();
			
		} catch (SSPBusinessException e) {
			e.printStackTrace();
		}
	}
	
	@Inject
	public void setMessageProvider(MessageProvider messageProvider) {
		this.messageProvider = messageProvider;
	}
}