package it.eng.core.audit;

import java.io.Serializable;
/**
 * interfaccia per il sistema di audit, l'implementazione consente di eseguire delle operazioni prima 
 * di una chiamata ad un serviizo dopo e in caso di errore.
 *  TODO considerare l'utilizzo dell'AOP.
 * @author Russo
 *
 */
public interface IAudit {
	public String traceStart(String serviceName,String operationName, Serializable...  input) throws Exception;
	
	public void traceEnd(String idLogIniz,String serviceName,String operationName,Serializable output) throws Exception;
	
	public void traceError(String idLogIniz,String serviceName,String operationName, Throwable e) throws Exception;
		
}
