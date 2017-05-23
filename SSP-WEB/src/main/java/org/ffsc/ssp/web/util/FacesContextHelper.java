package org.ffsc.ssp.web.util;

import java.io.IOException;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.application.ResourceHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

public class FacesContextHelper {

	private FacesContextHelper() {}
	
	public static HttpSession getSession(){
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true); 
	}
		
	public static HttpServletRequest getRequest(){
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
	
	public static Object getFromRequest(String key){
		return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
	}
		
	public static ResourceHandler getResourceHandler(){
		return FacesContext.getCurrentInstance().getApplication().getResourceHandler();
	}
	
	public static String evaluateEL(String el){
		FacesContext context = FacesContext.getCurrentInstance();
		String value = context.getApplication().evaluateExpressionGet(context, el, String.class);
		return value;
	}
			
	public static void addGlobalMessage(Severity severity, String message, String detail) {
        FacesMessage facesMessage = new FacesMessage(severity, message,  detail);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }
		
	public static void addMessage(String clientId, Severity severity, String message, String detail) {
        FacesMessage facesMessage = new FacesMessage(severity, message,  detail);
        FacesContext.getCurrentInstance().addMessage(clientId, facesMessage);
    }
	
	public static void showMessageDialog(Severity severity, String message){
		String title = new MessageProviderImpl().getValue("ssp_application_title");
		FacesMessage facesMessage = new FacesMessage(severity, title, message);
		RequestContext.getCurrentInstance().showMessageInDialog(facesMessage);
	}
	
	public static void redirect(String page) {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		try {
			ec.redirect(buildURLForPage(page, true, null));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void redirect(String page, Map<String,Object> params) {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		try {
			ec.redirect(buildURLForPage(page, true, params));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String buildURLForPage(String page, boolean redirect, Map<String,Object> params){
		
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

		String scheme		= ((HttpServletRequest)ec.getRequest()).getScheme();
		String localAddress = ((HttpServletRequest)ec.getRequest()).getLocalName();
		String locarPort    = String.valueOf(((HttpServletRequest)ec.getRequest()).getLocalPort());
		String contextPath  = ((HttpServletRequest)ec.getRequest()).getContextPath();
	    
		//If page was not informed try to redirect to the actual page
		if(page == null){
			page = contextPath.concat(FacesContext.getCurrentInstance().getViewRoot().getViewId()).replace(".xhtml", ".jsf");
		}
		
		StringBuffer url = new StringBuffer(String.format("%s://%s:%s/%s", scheme, localAddress, locarPort, page.replaceFirst("^/+", "")));
		
		//JSF Redirect Param
		if(redirect){
			if(!page.contains("faces-redirect")){
				url.append("?faces-redirect=true");
			}
		}
		
		//JSF View Params
		if(params != null){
			if(!page.contains("includeViewParams")){
				if(redirect){
					url.append("&includeViewParams=true");
				} else {
					url.append("?includeViewParams=true");
				}
			}
			
			for(Map.Entry<String, Object> entry: params.entrySet()){
				url.append(String.format("&%s=%s", entry.getKey(), entry.getValue()));
			}
		}
		
		return url.toString();
	}
}