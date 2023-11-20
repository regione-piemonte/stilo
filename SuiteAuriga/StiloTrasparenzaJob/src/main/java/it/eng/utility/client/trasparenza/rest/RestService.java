/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.net.SocketTimeoutException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.URLConnectionClientHandler;


public class RestService {
	
	private static final Logger logger = Logger.getLogger(RestService.class);
	
	public ClientResponse callGetService(RestRequestBean requestBean, String token) {
		ClientResponse clientResponse = null;
		
		try {
			// istanza Client
			Client client = createClient(requestBean,true);
			
			// composizione url rest
			String urlRest = requestBean.getEndpoint() + requestBean.getParamJson();
			logger.info("url servizio " + requestBean.getNomeServizio() + ": " + urlRest);
			
			long inizio = System.currentTimeMillis();
			
			// chiamata al servizio rest
			WebResource webResource = client.resource(urlRest);
			clientResponse = webResource.header("Authorization", requestBean.getToken())
										.accept(MediaType.APPLICATION_JSON)
										.get(ClientResponse.class);
			
			long fine = System.currentTimeMillis();
			
			logger.info(esecuzioneServizio(inizio, fine, requestBean.getNomeServizio()));
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause() instanceof SocketTimeoutException) {
				logger.error(e.getMessage());
				
				clientResponse = new ClientResponse(408, null, null, null);
				return clientResponse;
			}
			
			logger.error(e.getMessage());
		}
		
		return clientResponse;
	}
	
	public ClientResponse callPostService(RestRequestBean requestBean, String token) {
		ClientResponse clientResponse = null;
		
		try {
			// istanza Client
			Client client = createClient(requestBean,true);
			
			// composizione url rest
			String urlRest = requestBean.getEndpoint();
			logger.info("url servizio " + requestBean.getNomeServizio() + ": " + urlRest);
			
			long inizio = System.currentTimeMillis();
			
			// chiamata al servizio rest
			WebResource webResource = client.resource(urlRest);
			clientResponse = webResource.header("Authorization", requestBean.getToken())
										//.header("Content-Type", MediaType.APPLICATION_JSON)
										.header("Content-Type", "application/vnd.api+json")
										//.accept(MediaType.APPLICATION_JSON)
										//.type(MediaType.APPLICATION_JSON)
										.post(ClientResponse.class, requestBean.getParamJson());
			
			long fine = System.currentTimeMillis();
			
			logger.info(esecuzioneServizio(inizio, fine, requestBean.getNomeServizio()));
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause() instanceof SocketTimeoutException) {
				logger.error(e.getMessage());
				
				clientResponse = new ClientResponse(408, null, null, null);
				return clientResponse;
			}
			
			logger.error(e.getMessage());
		}
		
		return clientResponse;
	}
	
	public ClientResponse callPutService(RestRequestBean requestBean, String token) {
		ClientResponse clientResponse = null;
		
		try {
			// istanza Client
			Client client = createClient(requestBean,true);
			
			// composizione url rest
			String urlRest = requestBean.getEndpoint() + requestBean.getParamJson();
			logger.info("url servizio " + requestBean.getNomeServizio() + ": " + urlRest);
			
			long inizio = System.currentTimeMillis();
			
			// chiamata al servizio rest
			WebResource webResource = client.resource(urlRest);
			clientResponse = webResource.header("Authorization", requestBean.getToken())
										.accept(MediaType.APPLICATION_JSON)
										.put(ClientResponse.class);
			
			long fine = System.currentTimeMillis();
			
			logger.info(esecuzioneServizio(inizio, fine, requestBean.getNomeServizio()));
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause() instanceof SocketTimeoutException) {
				logger.error(e.getMessage());
				
				clientResponse = new ClientResponse(408, null, null, null);
				return clientResponse;
			}
			
			logger.error(e.getMessage());
		}
		
		return clientResponse;
	}
	
	public ClientResponse callDeleteService(RestRequestBean requestBean, String token) {
		ClientResponse clientResponse = null;
		
		try {
			// istanza Client
			Client client = createClient(requestBean,true);
			
			// composizione url rest
			String urlRest = requestBean.getEndpoint() + requestBean.getParamJson();
			logger.info("url servizio " + requestBean.getNomeServizio() + ": " + urlRest);
			
			long inizio = System.currentTimeMillis();
			
			// chiamata al servizio rest
			WebResource webResource = client.resource(urlRest);
			clientResponse = webResource.header("Authorization", requestBean.getToken())
										.accept(MediaType.APPLICATION_JSON)
										.delete(ClientResponse.class);
			
			long fine = System.currentTimeMillis();
			
			logger.info(esecuzioneServizio(inizio, fine, requestBean.getNomeServizio()));
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause() instanceof SocketTimeoutException) {
				logger.error(e.getMessage());
				
				clientResponse = new ClientResponse(408, null, null, null);
				return clientResponse;
			}
			
			logger.error(e.getMessage());
		}
		
		return clientResponse;
	}
	
	
	private Client createClient(RestRequestBean requestBean, boolean timeout) {
		Client client = null;
		
		// se presente connessione a proxy
		ProxyConfig proxyConfig = requestBean.getProxyConfig();
		if (proxyConfig != null && proxyConfig.isProxyEnabled()) {
			URLConnectionClientHandler cch = new URLConnectionClientHandler(new ConnectionFactory(proxyConfig));
			
			client = new Client(cch);
		}
		else {
			DefaultClientConfig cc = new DefaultClientConfig();
			
			client = Client.create(cc);
		}
		
		if (timeout) {
			TimeoutConfig timeoutConfig = requestBean.getTimeoutConfig();
			if (timeoutConfig.getConnectionTimeout() != null) {
				client.setConnectTimeout(timeoutConfig.getConnectionTimeout());
			}
			
			if (timeoutConfig.getReadTimeout() != null) {
				client.setReadTimeout(timeoutConfig.getReadTimeout());
			}
		}
		
		return client;
	}
	
	private String esecuzioneServizio(long inizio, long fine, String nomeServizio) {
		NumberFormat formatter = new DecimalFormat("#0.00000");
		
		String result = "Esecuzione servizio " + nomeServizio + " in " + formatter.format((fine - inizio) / 1000d) + " secondi";
		
		return result;
	}
	
}
