package org.ffsc.ssp.web.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.ffsc.ssp.service.ProductService;
import org.ffsc.ssp.service.domain.Product;
import org.ffsc.ssp.service.impl.ProductServiceBean;
import org.ffsc.ssp.service.locator.ServiceLocator;

@FacesConverter(forClass = Product.class)
public class ProductConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component,
			String id) {

		if (StringUtils.isNotEmpty(id)) {

			ProductService productService = (ProductService) ServiceLocator
					.getInstance().lookup(ProductServiceBean.class);

			return productService.getProduct(Long.valueOf(id));
		}

		return null;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component,
			Object obj) {

		if (obj instanceof Product) {

			Product product = (Product) obj;

			if (product != null) {
				return product.getId().toString();
			}
		}

		return "";
	}
}