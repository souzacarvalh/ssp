package org.ffsc.ssp.web.infrastructure;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

public class SSPExceptionHandlerFactory extends ExceptionHandlerFactory {

	private ExceptionHandlerFactory parent;
	 
	// JSF Provided Dependency
	public SSPExceptionHandlerFactory(ExceptionHandlerFactory parent) {
		this.parent = parent;
	}
	
	@Override
	public ExceptionHandler getExceptionHandler() {
		
		ExceptionHandler handler = new SSPExceptionHandler(parent.getExceptionHandler());
		
		return handler;
	}
}