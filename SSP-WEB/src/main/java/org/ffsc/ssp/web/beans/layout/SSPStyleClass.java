package org.ffsc.ssp.web.beans.layout;

public enum SSPStyleClass {
	
	GRAY_BG_CELL ("gray-bg-cell"),
	GREEN_BG_CELL ("green-bg-cell"),
	ODD_ROW ("odd-row");

	final String cssClassName;
	
	private SSPStyleClass(String cssClassName) {
		this.cssClassName = cssClassName;
	}
	
	public String getCssClassName() {
		return this.cssClassName;
	}
}