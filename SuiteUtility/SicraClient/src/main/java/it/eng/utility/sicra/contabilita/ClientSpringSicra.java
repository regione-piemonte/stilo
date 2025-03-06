package it.eng.utility.sicra.contabilita;

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
