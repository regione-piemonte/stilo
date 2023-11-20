/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ClientSpringTrasparenza {

	private static final String[] BEANS_FILE = {"client-trasparenza.xml"};
	private static final ClientSpringTrasparenza INSTANCE = new ClientSpringTrasparenza();
	private final ClassPathXmlApplicationContext context;
	private final ClientTrasparenza client;
	
	private ClientSpringTrasparenza() {
		context = new ClassPathXmlApplicationContext(BEANS_FILE);
		client = context.getBean(ClientTrasparenza.class);
	}
	
	public static ClientTrasparenza getClient() {
		return INSTANCE.client;
	}
	
}
