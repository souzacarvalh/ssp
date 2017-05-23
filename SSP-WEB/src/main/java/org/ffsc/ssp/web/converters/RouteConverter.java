package org.ffsc.ssp.web.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.ffsc.ssp.service.RoutingService;
import org.ffsc.ssp.service.domain.Route;
import org.ffsc.ssp.service.impl.RoutingServiceBean;
import org.ffsc.ssp.service.locator.ServiceLocator;

@FacesConverter(forClass = Route.class)
public class RouteConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component,
			String id) {

		if (StringUtils.isNotEmpty(id)) {

			RoutingService routingService = (RoutingService) ServiceLocator
					.getInstance().lookup(RoutingServiceBean.class);

			return routingService.getRoute(Long.valueOf(id));
		}

		return null;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component,
			Object obj) {

		if (obj instanceof Route) {

			Route route = (Route) obj;

			if (route != null) {
				return route.getId().toString();
			}
		}

		return "";
	}
}