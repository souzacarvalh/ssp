package org.ffsc.ssp.service.impl;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.ffsc.ssp.service.RoutingService;
import org.ffsc.ssp.service.domain.Category;
import org.ffsc.ssp.service.domain.Product;
import org.ffsc.ssp.service.domain.Route;
import org.ffsc.ssp.service.domain.Ticket;
import org.ffsc.ssp.service.domain.Treatment;
import org.ffsc.ssp.service.persistence.RouteDAO;

@Stateless
public class RoutingServiceBean implements RoutingService {

	private static final long serialVersionUID = 1L;
	
	private RouteDAO routeDAO;
	
	@Inject
	public RoutingServiceBean(RouteDAO daoImpl) {
		this.routeDAO = daoImpl;
	}
	
	@Override
	public Long saveRoute(Route route){
		return routeDAO.saveOrUpdate(route).getId();
	}

	@Override
	public void deleteRoute(Route route){
		routeDAO.delete(route);
	}

	@Override
	public Route getRoute(Long id) {
		return routeDAO.byId(id);
	}
	
	@Override
	public Route getRoute(Category category) {
		return routeDAO.byCategory(category);
	}

	@Override
	public Route getRoute(Product product){
		return routeDAO.byProductAndCategoryIsNull(product);
	}
	
	@Override
	public Collection<Route> getAvailableRoutes() {
		return routeDAO.listAll();
	}
	
	public Ticket routeTicket(Ticket ticket) {

		Treatment treatment = ticket.getTreatmentInfo();

		treatment.setSupportGroup(null);
		treatment.setSupportAnalyst(null);
		treatment.setShouldRedirect(false);

		/*
		 * Verify whether a specific route for the category is registered ...
		 */
		Route route = routeDAO.byCategory(ticket.getCategory());

		if (route != null) {
			treatment.setSupportGroup(route.getSupportGroup());
			treatment.setSupportAnalyst(route.getSupportAnalyst());
		} else {

			/*
			 * Verify whether a specific route for the product is registered ...
			 */
			route = routeDAO.byProductAndCategoryIsNull(ticket.getProduct());

			if (route != null) {
				treatment.setSupportGroup(route.getSupportGroup());
				treatment.setSupportAnalyst(route.getSupportAnalyst());
			}
		}

		return ticket;
	}
}