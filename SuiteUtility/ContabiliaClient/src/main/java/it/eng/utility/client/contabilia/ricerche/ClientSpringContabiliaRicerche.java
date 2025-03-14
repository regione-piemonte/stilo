package it.eng.utility.client.contabilia.ricerche;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ClientSpringContabiliaRicerche {
	
	private static final String[] BEANS_FILE = {"client-contabilia-shared.xml", "client-contabilia-ricerche.xml"};
	private static final ClientSpringContabiliaRicerche INSTANCE = new ClientSpringContabiliaRicerche();
	private final ClassPathXmlApplicationContext context;
	private final ClientContabiliaRicerche client;
	
	private ClientSpringContabiliaRicerche() {
		context = new ClassPathXmlApplicationContext(BEANS_FILE);
		client = (ClientContabiliaRicerche) context.getBean(ClientContabiliaRicerche.BEAN_ID);
	}
	
	public static ClientContabiliaRicerche getClient() {
		return INSTANCE.client;
	}
	
}
