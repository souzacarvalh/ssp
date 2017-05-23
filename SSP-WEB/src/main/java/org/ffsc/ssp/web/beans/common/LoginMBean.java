package org.ffsc.ssp.web.beans.common;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.ffsc.ssp.service.AuthenticationService;
import org.ffsc.ssp.service.domain.Credential;
import org.ffsc.ssp.web.security.UserInfo;
import org.ffsc.ssp.web.util.FacesContextHelper;
import org.ffsc.ssp.web.util.MessageProvider;

@ManagedBean
@ViewScoped
public class LoginMBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
	private boolean keepMeConnected;

	@ManagedProperty(value="#{userInfo}")
	private UserInfo userInfo;
	
	private MessageProvider messageProvider;
	
	private AuthenticationService authService;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isKeepMeConnected() {
		return keepMeConnected;
	}

	public void setKeepMeConnected(boolean keepMeConnected) {
		this.keepMeConnected = keepMeConnected;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
		
	public void checkUserIsAuthenticated(){
		if(SecurityUtils.getSubject().isAuthenticated()){
			String context = (String) FacesContextHelper.getFromRequest("context");
			redirectAfterLogin(null, context);
		}
	}

	public void doLogin() {
		try {
			
			// 1 - Do the Apache Shiro Authentication
			SecurityUtils.getSubject().login(new UsernamePasswordToken(username, password, false));
	
			// 2 - Retrieve SSP Credentials
			Credential credential = authService.getAuthenticated();
			
			// 3 - Start SSP Internal Context {Admin, Support, Customer}
			if (credential != null) {
				
				String context = (String) FacesContextHelper.getFromRequest("context");
				userInfo.initSession(credential, context);
				
				if(keepMeConnected){
					FacesContextHelper.getSession().setMaxInactiveInterval(-1);
				}
				
				SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(FacesContextHelper.getRequest());	
				redirectAfterLogin(savedRequest, context);
			}
			
		} catch(AuthenticationException e) {
			userInfo.incrementFailLoginCount();
			
			FacesContextHelper.addGlobalMessage(FacesMessage.SEVERITY_ERROR, 
					messageProvider.getValue(SSPMessage.AUTHENTICATION_FAILED), null);
		}
	}
	
	public void doLogoff(){
		SecurityUtils.getSubject().logout();
		FacesContextHelper.getSession().invalidate();
		FacesContextHelper.redirect(userInfo.getHome());
		userInfo = null;
	}
	
	private void redirectAfterLogin(SavedRequest savedRequest, String context){
		
		boolean shouldRedirectToHome = true;
		
		if(savedRequest != null){
			shouldRedirectToHome = savedRequest.getRequestUrl().endsWith(context.concat("/"));
		}
		
		FacesContextHelper.redirect(shouldRedirectToHome ? userInfo.getHome() : savedRequest.getRequestUrl());
	}
	
	@EJB
	public void setAuthenticationService(AuthenticationService authService) {
		this.authService = authService;
	}
	
	@Inject
	public void setMessageProvider(MessageProvider messageProvider) {
		this.messageProvider = messageProvider;
	}
}