package org.ffsc.ssp.web.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.ffsc.ssp.service.CategoryService;
import org.ffsc.ssp.service.domain.Category;
import org.ffsc.ssp.service.impl.CategoryServiceBean;
import org.ffsc.ssp.service.locator.ServiceLocator;

@FacesConverter(forClass = Category.class)
public class CategoryConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component,
			String id) {

		if (StringUtils.isNotEmpty(id)) {

			CategoryService categService = (CategoryService) ServiceLocator
					.getInstance().lookup(CategoryServiceBean.class);

			return categService.getCategory(Long.valueOf(id));
		}

		return null;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component,
			Object obj) {

		if (obj instanceof Category) {

			Category category = (Category) obj;

			if (category != null) {
				return category.getId().toString();
			}
		}

		return "";
	}
}