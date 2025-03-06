package it.eng.core.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


public class SpringCoreContextProvider implements ApplicationContextAware{

	private static ApplicationContext context;
	
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		SpringCoreContextProvider.context = context;
	}

	/**
	 * @return the context
	 */
	public static ApplicationContext getContext() {
		return context;
	}
	
}
