package org.ffsc.ssp.web.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.ffsc.ssp.service.TicketService;
import org.ffsc.ssp.service.domain.TicketStep;
import org.ffsc.ssp.service.impl.TicketServiceBean;
import org.ffsc.ssp.service.locator.ServiceLocator;

@FacesConverter(forClass = TicketStep.class)
public class TicketStepConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component,
			String id) {

		if (StringUtils.isNotEmpty(id)) {

			TicketService ticketService = (TicketService) ServiceLocator
					.getInstance().lookup(TicketServiceBean.class);

			return ticketService.getTicketStep(Long.valueOf(id));
		}

		return null;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component,
			Object obj) {

		if (obj instanceof TicketStep) {

			TicketStep ticketStep = (TicketStep) obj;

			if (ticketStep != null) {
				return ticketStep.getId().toString();
			}
		}

		return "";
	}
}