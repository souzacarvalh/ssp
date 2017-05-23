package org.ffsc.ssp.web.beans.layout.model;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("panelmenu")
public class PanelMenu {

	@XStreamAlias("submenus")
	private List<SubMenu> subMenus = new ArrayList<>();

	public List<SubMenu> getSubMenus() {
		return subMenus;
	}
	
	public void setSubMenus(List<SubMenu> subMenus) {
		this.subMenus = subMenus;
	}
}