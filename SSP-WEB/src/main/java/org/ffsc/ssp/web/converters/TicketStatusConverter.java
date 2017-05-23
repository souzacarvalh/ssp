package org.ffsc.ssp.web.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.ffsc.ssp.service.TicketStatusService;
import org.ffsc.ssp.service.domain.TicketStatus;
import org.ffsc.ssp.service.impl.TicketStatusServiceBean;
import org.ffsc.ssp.service.locator.ServiceLocator;

@FacesConverter(forClass = TicketStatus.class)
public class TicketStatusConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component,
			String id) {

		if (StringUtils.isNotEmpty(id)) {

			TicketStatusService statusService = (TicketStatusService) ServiceLocator
					.getInstance().lookup(TicketStatusServiceBean.class);

			return statusService.getStatus(Long.valueOf(id));
		}

		return null;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component,
			Object obj) {

		if (obj instanceof TicketStatus) {

			TicketStatus ticketStatus = (TicketStatus) obj;

			if (ticketStatus != null) {
				return ticketStatus.getId().toString();
			}
		}

		return "";
	}
}