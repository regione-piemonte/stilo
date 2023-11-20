/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.client.urlconnection.HTTPSProperties;
import com.sun.jersey.client.urlconnection.URLConnectionClientHandler;

import it.eng.spring.utility.SpringAppContext;
//import it.eng.sue.client.config.ProxyConfig;
//import it.eng.sue.client.util.ConnectionFactory;
import it.eng.utility.cogito.config.AurigaCogitoClientConfig;
import it.eng.utility.cogito.exception.AurigaCogitoException;

public class CogitoServices {

	private static String endpoint;	
	private static String errorMessage;
	
	private static Logger logger = Logger.getLogger(CogitoServices.class);

	public String integrazioneClassificazioneCogito(String testoDaClassificare) throws AurigaCogitoException {
		return callService("?language=it&&idonly=false", testoDaClassificare);
	}
		
	private TrustManager trustManager = new X509TrustManager() {

		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateExpiredException, CertificateNotYetValidException {
			chain[0].checkValidity();
			chain[0].getIssuerUniqueID();
			chain[0].getSubjectDN();
		}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType) {

		}
	};
	
	private String callService(String nameService, String testoDaClassificare) throws AurigaCogitoException {
		AurigaCogitoClientConfig lProxyCogitoClientConfig = (AurigaCogitoClientConfig) SpringAppContext.getContext().getBean("CogitoClientConfig");
		
		logger.debug("Configurazione Client");

		// Setto l'endpoind del WS
		if (lProxyCogitoClientConfig != null && lProxyCogitoClientConfig.getUrl() != null) {
			endpoint = lProxyCogitoClientConfig.getUrl();
			logger.debug("Settaggio endpoint: " + lProxyCogitoClientConfig.getUrl());
		} else {
			logger.error("Non e' stato specificato l'endpoint del WS");
			throw new AurigaCogitoException("Endpoint del WS non specificato");
		}
		
		StringBuffer result = new StringBuffer();
		logger.debug("Endpoint: " + endpoint + nameService);		
		
		try {
			SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, new TrustManager[] { trustManager }, null);
			DefaultClientConfig defaultClientConfig = new com.sun.jersey.api.client.config.DefaultClientConfig();
			defaultClientConfig.getProperties().put(HTTPSProperties.PROPERTY_HTTPS_PROPERTIES, new HTTPSProperties(null, sslContext));
			Client client = Client.create(defaultClientConfig);
			
			//Setto il proxy
			/*
			ProxyConfig proxyConfig=lProxyCogitoClientConfig.getProxyConfig();
			
			if (proxyConfig != null && proxyConfig.isUseProxy()) {
				logger.debug("Configurazione proxy con host: " + proxyConfig.getProxyHost() + " e port: " + proxyConfig.getProxyPort());
				
				URLConnectionClientHandler ch  = new URLConnectionClientHandler(new ConnectionFactory(proxyConfig));
				client = new Client(ch);
			} else {
				logger.debug("Configurazione Client con proxy mancante");
				
				DefaultClientConfig cc = new DefaultClientConfig();
				client = Client.create(cc);
			}
			
			*/
			
			// Basic Authentication al WS con username e password
			if (lProxyCogitoClientConfig != null && lProxyCogitoClientConfig.getUser() != null
					&& lProxyCogitoClientConfig.getPassword() != null) {
				logger.debug("Autenticazione base al WS con User: " + lProxyCogitoClientConfig.getUser() + " e Password: "
						+ lProxyCogitoClientConfig.getPassword());
				client.addFilter(new HTTPBasicAuthFilter(lProxyCogitoClientConfig.getUser(), lProxyCogitoClientConfig.getPassword()));
			} else {
				logger.debug("Non sono state specificate le credenziali per l'autenticazione al WS");
			}
			
			WebResource webResource = client.resource(endpoint + nameService);
			
			ClientResponse response = webResource
					.accept(MediaType.APPLICATION_XML).type(MediaType.TEXT_PLAIN)
					.post(ClientResponse.class, testoDaClassificare);

			logger.debug("response.getStatus() " + response.getStatus() + " " + response.getResponseStatus());

			if (Response.Status.OK.getStatusCode() != response.getStatus()) {
				String responseString = response.getEntity(String.class);
				logger.error("Errore nella risposta del servizio: " + nameService + "   - Descrizione errore: "
						+ responseString);
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			} else {
				BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntityInputStream())));
				String line = "";
				while ((line = br.readLine()) != null) {
					result.append(line);
				}

				return result.toString();
			}

		} catch (Exception e) {
			if (e.getCause() != null && e.getCause() instanceof SocketTimeoutException) {
				logger.error("Errore di timeout, superati i limiti del client \n" + e.getMessage(), e);
				if (errorMessage != null && !errorMessage.equalsIgnoreCase("")) {
					throw new AurigaCogitoException(errorMessage);
				} else {
					throw new AurigaCogitoException(e.getMessage(), e);
				}
			}
			logger.error(e.getMessage(), e);
			throw new AurigaCogitoException("Errore durante l'invocazione del servizio - ERROR:\n" + e.getMessage(), e);
		}				
	}

}
