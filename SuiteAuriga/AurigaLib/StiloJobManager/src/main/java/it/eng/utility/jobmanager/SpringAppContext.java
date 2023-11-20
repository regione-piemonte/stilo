/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.springframework.context.ApplicationContext;

public class SpringAppContext {

	private static ApplicationContext context;

	public static ApplicationContext getContext() {
		return context;
	}

	/**
	 * Permette l'accesso sincronizzato all'ApplicationContext, per evitare hce
	 * più thread tentino di accedere in maniera concorrente allo stesso
	 * contesto, tentando di istanziare il medesimo bean. Spring non è thread
	 * safe, quindi tecnicamente c'è la possibilità che lo stesso bean venga
	 * utilizzato da diversi thread, anche se marcato come prototype
	 * 
	 * @return
	 */
	public static synchronized ApplicationContext getContextSynchronized() {
		return context;
	}

	public static void setContext(ApplicationContext context) {
		SpringAppContext.context = context;
	}

}
