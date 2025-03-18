/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.springframework.context.support.ClassPathXmlApplicationContext;

public final class ClientSpringSIBDetermine {

	private static final String[] BEANS_FILE = { "client-sib-shared.xml", "client-sib-determine.xml" };
	private static final ClientSpringSIBDetermine INSTANCE = new ClientSpringSIBDetermine();
	private final ClassPathXmlApplicationContext context;
	private final ClientSIBDetermine client;

	private ClientSpringSIBDetermine() {
		context = new ClassPathXmlApplicationContext(BEANS_FILE);
		client = (ClientSIBDetermine) context.getBean(ClientSIBDetermine.BEAN_ID);
	}

	public static ClientSIBDetermine getClient() {
		return INSTANCE.client;
	}

}// ClientSpringSIBDetermine
