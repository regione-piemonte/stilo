/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ClientSpringToponomastica {
	
	private static final String[] BEANS_FILE = {"client-toponomastica-shared.xml", "client-toponomastica-servizi.xml"};
	private static final ClientSpringToponomastica INSTANCE = new ClientSpringToponomastica();
	private final ClassPathXmlApplicationContext context;
	private final ClientToponomastica client;
	
	private ClientSpringToponomastica() {
		context = new ClassPathXmlApplicationContext(BEANS_FILE);
		client = (ClientToponomastica) context.getBean(ClientToponomastica.BEAN_ID);
	}
	
	public static ClientToponomastica getClient() {
		return INSTANCE.client;
	}
}
