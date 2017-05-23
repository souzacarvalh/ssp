package org.ffsc.ssp.web.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.ffsc.ssp.service.GroupService;
import org.ffsc.ssp.service.domain.Group;
import org.ffsc.ssp.service.impl.GroupServiceBean;
import org.ffsc.ssp.service.locator.ServiceLocator;

@FacesConverter(forClass = Group.class)
public class GroupConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component,
			String id) {

		if (StringUtils.isNotEmpty(id)) {

			GroupService groupService = (GroupService) ServiceLocator
					.getInstance().lookup(GroupServiceBean.class);

			return groupService.getGroup(Long.valueOf(id));
		}

		return null;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component,
			Object obj) {

		if (obj instanceof Group) {

			Group group = (Group) obj;

			if (group != null) {
				return group.getId().toString();
			}
		}

		return "";
	}
}