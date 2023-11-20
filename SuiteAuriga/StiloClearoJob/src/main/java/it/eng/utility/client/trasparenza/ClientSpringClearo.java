/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.springframework.context.support.ClassPathXmlApplicationContext;

public final class ClientSpringClearo {

	private static final String[] BEANS_FILE = { "client-clearo.xml" };
	private static final ClientSpringClearo INSTANCE = new ClientSpringClearo();
	private final ClassPathXmlApplicationContext context;
	private final ClientClearo client;

	private ClientSpringClearo() {
		context = new ClassPathXmlApplicationContext(BEANS_FILE);
		client = context.getBean(ClientClearo.class);
	}

	public static ClientClearo getClient() {
		return INSTANCE.client;
	}

}
