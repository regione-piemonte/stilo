package it.eng.core.service.client;

import it.eng.core.business.exception.ServiceException;

public class InvocationException extends ServiceException{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5531009332253332317L;
	private static String errorcontext="CLIENT";
	private static String errorcode="ERR_INV_SRV";//errore nell'invocare il servizio
	 
	 
    
	public InvocationException(){
		super(errorcontext, errorcode);
	}
	
	public InvocationException(  Throwable cause) {
	 super(errorcontext, errorcode, cause);
	}
}
