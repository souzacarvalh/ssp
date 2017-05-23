package org.ffsc.ssp.service.persistence;

import java.util.Collection;

import org.ffsc.ssp.service.domain.Category;
import org.ffsc.ssp.service.domain.Product;
import org.ffsc.ssp.service.domain.Route;

public interface RouteDAO extends GenericDAO<Route, Long> {

	Route byCategory(Category category);

	Route byProductAndCategoryIsNull(Product product);

	Collection<Route> listByProduct(Product product);

}