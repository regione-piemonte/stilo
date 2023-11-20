/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class FileoperationContextProvider implements ApplicationContextAware{

	private static ApplicationContext context;
	
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		FileoperationContextProvider.context = context;
	}

	/**
	 * @return the context
	 */
	public static ApplicationContext getApplicationContext() {
		return context;
	}
	
}