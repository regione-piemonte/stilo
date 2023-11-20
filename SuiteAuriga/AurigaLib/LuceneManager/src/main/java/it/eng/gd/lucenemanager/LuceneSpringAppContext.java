/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.persistence.Entity;
import org.springframework.context.ApplicationContext;

@Entity
public class LuceneSpringAppContext {

	private static ApplicationContext context;

	public static ApplicationContext getContext() {
		return context;
	}

	public static void setContext(ApplicationContext context) {
		LuceneSpringAppContext.context = context;
	}
	
}
