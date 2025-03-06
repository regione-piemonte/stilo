package it.eng.utility.client.sib.peg;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public final class ClientSpringSIBPEG {

	private static final String[] BEANS_FILE = { "client-sib-shared.xml", "client-sib-peg.xml" };
	private static final ClientSpringSIBPEG INSTANCE = new ClientSpringSIBPEG();
	private final ClassPathXmlApplicationContext context;
	private final ClientSIBPEG client;

	private ClientSpringSIBPEG() {
		context = new ClassPathXmlApplicationContext(BEANS_FILE);
		client = (ClientSIBPEG) context.getBean(ClientSIBPEG.BEAN_ID);
	}

	public static ClientSIBPEG getClient() {
		return INSTANCE.client;
	}

}// ClientSpringSIBPEG
