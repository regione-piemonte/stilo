/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import it.eng.job.SpringHelper;


public class ClientSpringContabiliaDocumenti {
	
	private static final String[] BEANS_FILE = {"client-contabilia-shared.xml", "client-contabilia-documenti.xml"};
	private static final ClientSpringContabiliaDocumenti INSTANCE = new ClientSpringContabiliaDocumenti();
	//private final ClassPathXmlApplicationContext context;
	private final ClientContabiliaDocumenti client;
	private ApplicationContext context;
	
	private ClientSpringContabiliaDocumenti() {
		//context = new ClassPathXmlApplicationContext(BEANS_FILE);
		context = SpringHelper.getMainApplicationContext();
		client = (ClientContabiliaDocumenti) context.getBean(ClientContabiliaDocumenti.BEAN_ID);
	}
	
	public static ClientContabiliaDocumenti getClient() {
		return INSTANCE.client;
	}
	
}
