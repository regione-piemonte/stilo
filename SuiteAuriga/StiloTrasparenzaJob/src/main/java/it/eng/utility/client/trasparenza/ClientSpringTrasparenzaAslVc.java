/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.utility.client.trasparenza;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ClientSpringTrasparenzaAslVc {

	private static final String[] BEANS_FILE = {"client-trasparenza.xml"};
	private static final ClientSpringTrasparenzaAslVc INSTANCE = new ClientSpringTrasparenzaAslVc();
	private final ClassPathXmlApplicationContext context;
	private final ClientTrasparenzaAslVc client;
	
	private ClientSpringTrasparenzaAslVc() {
		context = new ClassPathXmlApplicationContext(BEANS_FILE);
		client = context.getBean(ClientTrasparenzaAslVc.class);
	}
	
	public static ClientTrasparenzaAslVc getClient() {
		return INSTANCE.client;
	}
	
}
