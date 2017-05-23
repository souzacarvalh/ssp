package org.ffsc.ssp.web.security;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import org.ffsc.ssp.web.util.FacesContextHelper;

public class SSPAuthenticationListener implements ActionListener{

	@Override
	public void processAction(ActionEvent event) throws AbortProcessingException {
		
		String context = (String) FacesContextHelper.getFromRequest("context");
		
		System.out.println("Initializing SSP Authentication Context: " + context);
	}
}