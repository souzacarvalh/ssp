package org.ffsc.ssp.web.beans.common;

public enum Navigation {

	ADMIN_HOME_PAGE_REDIRECT("ssp/pages/administration/home.jsf?faces-redirect=true"),
	SUPPORT_HOME_PAGE_REDIRECT("ssp/pages/support/home.jsf?faces-redirect=true"),
	SUPPORT_VIEW_TICKET("ssp/pages/support/tickets/viewTicket.jsf"),
	CUSTOMER_HOME_PAGE_REDIRECT("ssp/pages/customer/home.jsf?faces-redirect=true"),
	CUSTOMER_VIEW_TICKET("ssp/pages/customer/tickets/viewTicket.jsf"),
	CURRENT_PAGE(""),
	RATING_PAGE_REDIRECT("rating.xhtml.jsf?faces-redirect=true");
	
	final String path;
	
	private Navigation(String path) {
		this.path = path;
	}
	
	public String getPath() {
		return this.path;
	}
}