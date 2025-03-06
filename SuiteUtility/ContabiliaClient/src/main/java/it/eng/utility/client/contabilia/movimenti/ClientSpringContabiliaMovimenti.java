package it.eng.utility.client.contabilia.movimenti;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ClientSpringContabiliaMovimenti {
	
	private static final String[] BEANS_FILE = {"client-contabilia-shared.xml", "client-contabilia-movimenti.xml"};
	private static final ClientSpringContabiliaMovimenti INSTANCE = new ClientSpringContabiliaMovimenti();
	private final ClassPathXmlApplicationContext context;
	private final ClientContabiliaMovimenti client;
	
	private ClientSpringContabiliaMovimenti() {
		context = new ClassPathXmlApplicationContext(BEANS_FILE);
		client = (ClientContabiliaMovimenti) context.getBean(ClientContabiliaMovimenti.BEAN_ID);
	}
	
	public static ClientContabiliaMovimenti getClient() {
		return INSTANCE.client;
	}
	
}
