package org.ffsc.ssp.web.util;

import java.util.ResourceBundle;

import org.ffsc.ssp.web.beans.common.SSPMessage;

public interface MessageProvider {
	ResourceBundle getBundle();	
	String getValue(String key, Object... params);
	String getValue(SSPMessage message, Object... params);
}