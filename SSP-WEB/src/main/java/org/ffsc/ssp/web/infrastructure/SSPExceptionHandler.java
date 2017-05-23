package org.ffsc.ssp.web.infrastructure;

import java.util.Iterator;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SSPExceptionHandler extends ExceptionHandlerWrapper{

	private final Logger logger = LoggerFactory.getLogger(SSPExceptionHandler.class);
	
	ExceptionHandler wrapped;
	
	public SSPExceptionHandler(ExceptionHandler handler) {
		this.wrapped = handler;
	}
	
	@Override
	public ExceptionHandler getWrapped(){
		return wrapped;
	}
	
	@Override
	public void handle() throws FacesException {

		final Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator();
        
		while (i.hasNext()) {
			
			logger.error("Exception not catched in wed layer ...");
            
			ExceptionQueuedEvent event = i.next();
            
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
 
            Throwable t = context.getException();
            
            logger.error("Exception: ", t);
            logger.error("Redirecting the user to /pages/common/error");
 
            final FacesContext fc = FacesContext.getCurrentInstance();
            final Map<String, Object> requestMap = fc.getExternalContext().getRequestMap();
            final NavigationHandler nav = fc.getApplication().getNavigationHandler();
 
            try {
  
                requestMap.put("exceptionMessage", t.getMessage());
               
                nav.handleNavigation(fc, null, "/pages/common/error");
                
                fc.renderResponse();
 
            } finally {
                i.remove();
            }
        }

        getWrapped().handle();
	}
}