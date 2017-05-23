package org.ffsc.ssp.web.util;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import org.ffsc.ssp.web.beans.common.SSPMessage;

public class MessageProviderImpl implements MessageProvider {

	private static final String BUNDLE_NAME = "msgs";
	private static final String KEY_NOT_FOUND = "?? key %s not found ??";
	
	private ResourceBundle bundle;

	public ResourceBundle getBundle() {
		if (bundle == null) {
			FacesContext context = FacesContext.getCurrentInstance();
			bundle = context.getApplication().getResourceBundle(context, BUNDLE_NAME);
		}
		
		return bundle; 
	}
	
	public String getValue(String key, Object... params) {
		try {
			
			String value = getBundle().getString(key);
			
			if(params != null) {
				MessageFormat mf = new MessageFormat(value);
				value = mf.format(params, new StringBuffer(), null).toString();
			}
			
			return value;
			
		} catch (MissingResourceException e) {
			return String.format(KEY_NOT_FOUND, key);
		}
	}
	
	public String getValue(SSPMessage message, Object... params) {
		return getValue(message.getKey(), params);
	}
}