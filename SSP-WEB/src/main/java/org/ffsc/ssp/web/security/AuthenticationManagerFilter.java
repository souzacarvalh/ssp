package org.ffsc.ssp.web.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;

public class AuthenticationManagerFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
	
		 HttpServletRequest request   = (HttpServletRequest) req;
		 HttpServletResponse response = (HttpServletResponse) res;
		
		 if (SecurityUtils.getSubject() != null
				 && SecurityUtils.getSubject().isAuthenticated()){
			 
			 String requestedUri = request.getRequestURI();
			 
			 requestedUri = requestedUri.replace("index.jsf", "");
			 
			 response.sendRedirect(requestedUri.concat("home.jsf"));
	     
		 } else {
	    	 chain.doFilter(req, res);
	     }
	}

	@Override
	public void destroy() {}
}