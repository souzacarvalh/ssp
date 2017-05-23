package org.ffsc.ssp.web.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.ffsc.ssp.service.PriorityService;
import org.ffsc.ssp.service.domain.Priority;
import org.ffsc.ssp.service.impl.PriorityServiceBean;
import org.ffsc.ssp.service.locator.ServiceLocator;

@FacesConverter(forClass = Priority.class)
public class PriorityConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component,
			String id) {

		if (StringUtils.isNotEmpty(id)) {

			PriorityService priorityService = (PriorityService) ServiceLocator
					.getInstance().lookup(PriorityServiceBean.class);

			return priorityService.getPriority(Long.valueOf(id));
		}

		return null;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component,
			Object obj) {

		if (obj instanceof Priority) {

			Priority priority = (Priority) obj;

			if (priority != null) {
				return priority.getId().toString();
			}
		}

		return "";
	}
}