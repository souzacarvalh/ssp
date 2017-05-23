package org.ffsc.ssp.service;

import java.util.Collection;

import org.ffsc.ssp.service.domain.Category;
import org.ffsc.ssp.service.domain.Product;
import org.ffsc.ssp.service.domain.Route;
import org.ffsc.ssp.service.domain.Ticket;

public interface RoutingService extends LocalService {

	Long saveRoute(Route route);

	void deleteRoute(Route route);

	Route getRoute(Long id);

	Route getRoute(Category category);

	Route getRoute(Product product);

	Collection<Route> getAvailableRoutes();

	Ticket routeTicket(Ticket ticket);

}