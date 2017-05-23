package org.ffsc.ssp.web.beans.layout.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("menuitem")
public class MenuItem {
	
	@XStreamAsAttribute
	private String value;
	
	@XStreamAsAttribute
	private String url;
	
	@XStreamAsAttribute
	private String icon;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
}