package org.ffsc.ssp.web.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.ffsc.ssp.service.AnalystSevice;
import org.ffsc.ssp.service.domain.Analyst;
import org.ffsc.ssp.service.impl.AnalystServiceBean;
import org.ffsc.ssp.service.locator.ServiceLocator;

@FacesConverter(forClass = Analyst.class)
public class AnalystConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component,
			String id) {

		if (StringUtils.isNotEmpty(id)) {

			AnalystSevice analystSevice = (AnalystSevice) ServiceLocator
					.getInstance().lookup(AnalystServiceBean.class);

			return analystSevice.getAnalyst(Long.valueOf(id));
		}

		return null;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component,
			Object obj) {

		if (obj instanceof Analyst) {

			Analyst analyst = (Analyst) obj;

			if (analyst != null) {
				return analyst.getId().toString();
			}
		}

		return "";
	}
}