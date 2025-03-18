/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.springframework.context.support.ClassPathXmlApplicationContext;

public final class ClientSpringSicra {

	private static final String[] BEANS_FILE = { "client-sicra.xml" };
	private static final ClientSpringSicra INSTANCE = new ClientSpringSicra();
	private final ClassPathXmlApplicationContext context;
	private final ClientSicra client;

	private ClientSpringSicra() {
		context = new ClassPathXmlApplicationContext(BEANS_FILE);
		client = context.getBean(ClientSicra.class);
	}

	public static ClientSicra getClient() {
		return INSTANCE.client;
	}

}// ClientSpringSicra
