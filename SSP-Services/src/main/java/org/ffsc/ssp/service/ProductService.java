package org.ffsc.ssp.service;

import java.util.Collection;

import org.ffsc.ssp.service.domain.Product;

public interface ProductService extends LocalService {

	Product getProduct(Long id);

	Collection<Product> getAvailableProducts();

}