package it.eng.core.service.bean;

import java.lang.reflect.Method;

/**
 * Bean che ingloba i parametri per la chiamata al servizio esposto
 * Contiene i dati necessari per una invocazione IrisCall, ovvero la classe (servizio) e il metodo (operazion)
 * TODO aggiungere i par di ingresso.
 * 
 * A partire dalle stringhe relative al nome servizio e al nome operazione occorre determinare il servicebean
 *  ovvero la classe e il metodo effettivo da invocare. 
 * @author michele
 *
 */
public class ServiceBean {
	
	public ServiceBean() {
		// TODO Auto-generated constructor stub
	}
	
	private Class<?> service;
	private Method operation;
	
	public Class<?> getService() {
		return service;
	}
	public void setService(Class<?> service) {
		this.service = service;
	}
	public Method getOperation() {
		return operation;
	}
	public void setOperation(Method operation) {
		this.operation = operation;
	}
}
