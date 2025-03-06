package it.eng.utility.amco.client;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ClientSpringAmco {
	
	private static final String[] BEANS_FILE = {"client-amco-shared.xml", "client-amco.xml"};
	private static final ClientSpringAmco INSTANCE = new ClientSpringAmco();
	private final ClassPathXmlApplicationContext context;
	private final ClientAmco client;
	
	private ClientSpringAmco() {
		context = new ClassPathXmlApplicationContext(BEANS_FILE);
		client = (ClientAmco) context.getBean(ClientAmco.BEAN_ID);
	}
	
	public static ClientAmco getClient() {
		return INSTANCE.client;
	}
	
}
