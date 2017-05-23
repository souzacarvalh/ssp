package org.ffsc.ssp.web.util;

import org.ffsc.ssp.web.beans.layout.model.PanelMenu;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

public class XMLHelper {

	private XMLHelper() {}
	
	private static XStream xstream = null;

	public static XStream getXStream() {
		
		if(xstream == null){
			xstream = new XStream(new StaxDriver());
			initializeAnnotationProcessor();
		}
		
		return xstream;
	}
	
	private static void initializeAnnotationProcessor() {
		if(xstream != null){
			xstream.processAnnotations(PanelMenu.class);
		}
	}
}