package org.ffsc.ssp.service.impl;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.ffsc.ssp.service.ProductService;
import org.ffsc.ssp.service.domain.Product;
import org.ffsc.ssp.service.persistence.ProductDAO;

@Stateless
public class ProductServiceBean implements ProductService {

	private static final long serialVersionUID = 1L;
	
	private ProductDAO productDAO;
	
	@Inject
	public ProductServiceBean(ProductDAO daoImpl) {
		this.productDAO = daoImpl;
	}
	
	public Product getProduct(Long id){
		return productDAO.byId(id);
	}
	
	public Collection<Product> getAvailableProducts(){
		return productDAO.listAll();
	}
}