package org.ffsc.ssp.web.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.ffsc.ssp.service.TicketService;
import org.ffsc.ssp.service.domain.Ticket;
import org.ffsc.ssp.service.impl.TicketServiceBean;
import org.ffsc.ssp.service.locator.ServiceLocator;

@FacesConverter(forClass = Ticket.class)
public class TicketConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component,
			String id) {

		if (StringUtils.isNotEmpty(id)) {

			TicketService chamadoService = (TicketService) ServiceLocator
					.getInstance().lookup(TicketServiceBean.class);

			return chamadoService.getTicket(Long.valueOf(id));
		}

		return null;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component,
			Object obj) {

		if (obj instanceof Ticket) {

			Ticket ticket = (Ticket) obj;

			if (ticket != null) {
				return ticket.getId().toString();
			}
		}

		return "";
	}
}