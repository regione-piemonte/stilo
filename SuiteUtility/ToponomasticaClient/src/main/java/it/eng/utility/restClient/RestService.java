package it.eng.utility.restClient;

import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import it.eng.utility.client.toponomastica.ClientToponomastica;

public class RestService {
	
	private static final Logger logger = Logger.getLogger(ClientToponomastica.class);
	
	/*
	 * Metodo per eseguire chiamate rest
	 */
	public ClientResponse callService(RestRequestBean requestBean) {
		Client client = null;
		ClientResponse clientResponse = null;
		
		try {
			DefaultClientConfig cc = new DefaultClientConfig();
			
			client = Client.create(cc);
			
			// composizione url rest
			String urlRest = requestBean.getEndpoint() + 
							 requestBean.getMetodo() + 
							 requestBean.getRequestParam();
			
			logger.info("Esecuzione call rest: " + urlRest);
			
			// chiamata al servizio rest
			WebResource webResource = client.resource(urlRest);
			clientResponse = webResource.header("Authorization", requestBean.getToken())
										.accept(MediaType.APPLICATION_JSON)
										.get(ClientResponse.class);
			
			logger.info("Call " + urlRest + " eseguita con stato " + clientResponse.getStatus());
		} catch (Exception e) {
			logger.error("Errore nella call rest " + requestBean.getMetodo() + e.getMessage());
		}
			
		return clientResponse;
	}
	
}
