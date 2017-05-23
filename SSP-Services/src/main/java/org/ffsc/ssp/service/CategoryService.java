package org.ffsc.ssp.service;

import java.util.Collection;

import org.ffsc.ssp.service.domain.Category;
import org.ffsc.ssp.service.domain.Product;

public interface CategoryService extends LocalService {

	Category getCategory(Long id);

	Collection<Category> getAvailableCategories();

	Collection<Category> getCategoriesByProduct(Product Product);

}