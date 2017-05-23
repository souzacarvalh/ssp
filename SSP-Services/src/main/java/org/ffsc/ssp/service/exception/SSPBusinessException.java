package org.ffsc.ssp.service.exception;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class SSPBusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

}