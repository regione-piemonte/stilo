package it.eng.utility.client.contabilia.documenti;

import org.springframework.context.support.ClassPathXmlApplicationContext;


public class ClientSpringContabiliaDocumenti {
	
	private static final String[] BEANS_FILE = {"client-contabilia-shared.xml", "client-contabilia-documenti.xml"};
	private static final ClientSpringContabiliaDocumenti INSTANCE = new ClientSpringContabiliaDocumenti();
	private final ClassPathXmlApplicationContext context;
	private final ClientContabiliaDocumenti client;
	
	private ClientSpringContabiliaDocumenti() {
		context = new ClassPathXmlApplicationContext(BEANS_FILE);
		client = (ClientContabiliaDocumenti) context.getBean(ClientContabiliaDocumenti.BEAN_ID);
	}
	
	public static ClientContabiliaDocumenti getClient() {
		return INSTANCE.client;
	}
	
}
