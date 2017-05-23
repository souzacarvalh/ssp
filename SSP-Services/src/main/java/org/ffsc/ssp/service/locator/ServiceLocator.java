package org.ffsc.ssp.service.locator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ServiceLocator {
	
	private static String BASIC_GLOBAL_EJB_EAR_JNDI_PATH = "java:global/#app-name#/#module-name#/#bean-name#";
	private static ServiceLocator INSTANCE = null;
		
	private InitialContext initialContext;
	
	private Map<String, Object> serviceCache = Collections.synchronizedMap(new HashMap<String, Object>());
		
	private ServiceLocator() {}
	
	public static ServiceLocator getInstance(){
		if(INSTANCE == null){
			INSTANCE = new ServiceLocator();
		}
		return INSTANCE;
	}
	
	@SuppressWarnings("unchecked")
    public <T> T lookup(Class<T> ejbClass) {

    	if(serviceCache.get(ejbClass.getName()) != null){
    		return (T) serviceCache.get(ejbClass.getName());
    	}
    	
    	String jndiName = getJndiNameFor(ejbClass);
    	
        try {
        	
        	if(initialContext == null){
        		initialContext = new InitialContext();
        	}
        	
        	T serviceBean = (T) initialContext.lookup(jndiName);
        	
        	serviceCache.put(ejbClass.getName(), serviceBean);
        	
            return serviceBean;
        
        } catch (NamingException e) {
            throw new IllegalArgumentException(
                String.format("EJB Bean not found: %s in JNDI context: %s", ejbClass, jndiName), e);
        }
    }
    
    private String getJndiNameFor(Class<?> clazz){
		
		String jndiName =  BASIC_GLOBAL_EJB_EAR_JNDI_PATH;
		
		jndiName = jndiName.replace("#app-name#", "SSP-EAR");
    	jndiName = jndiName.replace("#module-name#", "SSP-Services");
    	jndiName = jndiName.replace("#bean-name#", clazz.getSimpleName());
		
		return jndiName;
	}
}