package org.ffsc.ssp.service.properties;

import java.util.ResourceBundle;

public interface PropertiesProvider {

	ResourceBundle getPropertiesBundle();

	String getValue(String key);

}