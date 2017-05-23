package org.ffsc.ssp.service.properties;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class PropertiesProviderImpl implements PropertiesProvider {

	private static final String PROPERTIES_FILE_NAME = "environment";

	private ResourceBundle bundle;

	@Override
	public ResourceBundle getPropertiesBundle() {
		if (bundle == null) {
			bundle = ResourceBundle.getBundle(PROPERTIES_FILE_NAME);
		}

		return bundle;
	}

	@Override
	public String getValue(String key) {
		try {
			return getPropertiesBundle().getString(key);
		} catch (MissingResourceException e) {
			return "?? key " + key + " not found ??";
		}
	}
}