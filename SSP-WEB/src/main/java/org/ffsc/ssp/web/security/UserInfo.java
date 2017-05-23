package org.ffsc.ssp.web.security;

import java.io.Serializable;
import java.text.MessageFormat;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.ffsc.ssp.service.domain.Credential;
import org.ffsc.ssp.service.domain.Credential.AccessType;
import org.ffsc.ssp.service.domain.User;

@ManagedBean
@SessionScoped
public class UserInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String HOME_URL_PATTERN = "ssp/pages/{0}/home.jsf";

	private Credential credential;

	private AccessType context;

	private int failLoginCount;

	public void initSession(Credential credential, String context) {

		if (credential == null || context == null) {
			throw new SecurityException(
					"Credential and Context cannot be null to start an authenticated session.");
		}

		this.credential = credential;
		this.context = AccessType.valueOf(context.toUpperCase());
	}

	public User getAuthenticatedUser() {
		return this.credential.getUser();
	}

	public String getContext() {
		return context.name().toLowerCase();
	}

	public boolean isInternal() {
		return !AccessType.CUSTOMER.equals(context);
	}

	public String getHome() {
		return MessageFormat.format(HOME_URL_PATTERN, getContext());
	}

	public int getFailLoginCount() {
		return failLoginCount;
	}

	public void incrementFailLoginCount() {
		this.failLoginCount++;
	}
}