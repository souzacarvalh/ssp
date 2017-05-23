package org.ffsc.ssp.web.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.ffsc.ssp.service.SLAService;
import org.ffsc.ssp.service.domain.SLAConfiguration;
import org.ffsc.ssp.service.impl.SLAConfigServiceBean;
import org.ffsc.ssp.service.locator.ServiceLocator;

@FacesConverter(forClass = SLAConfiguration.class)
public class SLAConfigConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component,
			String id) {

		if (StringUtils.isNotEmpty(id)) {

			SLAService slaService = (SLAService) ServiceLocator.getInstance()
					.lookup(SLAConfigServiceBean.class);

			return slaService.getSLAConfig(Long.valueOf(id));
		}

		return null;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component,
			Object obj) {

		if (obj instanceof SLAConfiguration) {

			SLAConfiguration slaConfig = (SLAConfiguration) obj;

			if (slaConfig != null) {
				return slaConfig.getId().toString();
			}
		}

		return "";
	}
}