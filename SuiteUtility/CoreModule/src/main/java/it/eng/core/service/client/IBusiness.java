package it.eng.core.service.client;

import it.eng.core.service.bean.IAuthenticationBean;
import it.eng.core.service.client.config.Configuration;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Locale;

/**
 * Interfaccia che implementa il metodo di chiamata alla business
 * @author michele
 */
public interface IBusiness {
	
	//chiama un servizio di business
	public Serializable call(Locale locale,Class<?> outputclass,Type outputType, String module, String method, Serializable... input) throws Exception;
	
	public Serializable call(String url, Locale locale, Class<?> outputclass,Type outputType, String module, String method, Serializable... input) throws Exception;

	//chiamata che fa uso della autenticazione iris
	public Serializable call(IAuthenticationBean authBean, String url, Locale locale, Class<?> outputclass,Type outputType, String module, String method, Serializable... input) throws Exception;
	
	
	//inizializza la business prima di usarla
	public void init(Configuration config);
}