package org.ffsc.ssp.service.impl;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.ffsc.ssp.service.CategoryService;
import org.ffsc.ssp.service.domain.Category;
import org.ffsc.ssp.service.domain.Product;
import org.ffsc.ssp.service.persistence.CategoryDAO;

@Stateless
public class CategoryServiceBean implements CategoryService {

	private static final long serialVersionUID = 1L;
	
	private CategoryDAO categoryDAO;

	@Inject
	public CategoryServiceBean(CategoryDAO daoImpl) {
		this.categoryDAO = daoImpl;
	}
	
	@Override
	public Category getCategory(Long id){
		return categoryDAO.byId(id);
	}
	
	@Override
	public Collection<Category> getAvailableCategories(){
		return categoryDAO.listAll();
	}
	
	@Override
	public Collection<Category> getCategoriesByProduct(Product product){
		return categoryDAO.listByProduct(product);
	}
}