package org.ffsc.ssp.service.persistence;

import java.util.Collection;

import org.ffsc.ssp.service.domain.Category;
import org.ffsc.ssp.service.domain.Product;

public interface CategoryDAO extends GenericDAO<Category, Long> {
	
	Collection<Category> listByProduct(Product product);
	
}