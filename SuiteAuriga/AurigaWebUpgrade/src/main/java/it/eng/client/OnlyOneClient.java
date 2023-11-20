/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import it.eng.aurigamailbusiness.bean.ResultBean;
import it.eng.spring.utility.SpringAppContext;

public class OnlyOneClient {
	
	private static final Logger logger = Logger.getLogger(OnlyOneClient.class);

	private static final String PROTOCOLLO = "PG";
	private static final String OPENAGENT = "openagent";
	private static final String AUTHORIZATION = "Authorization";
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";
	
	private String username;
	private String password;
	private String token;
	private Integer timeout;
	private String endpoint;
	
	private WebResource webResource;
	
	public OnlyOneClient() throws Exception {
		
		try {
						
			OnlyOneClientConfigBean lOnlyOneClientConfigBean = (OnlyOneClientConfigBean) SpringAppContext.getContext().getBean("OnlyOneClientConfigBean");
			if(lOnlyOneClientConfigBean!=null && lOnlyOneClientConfigBean.getUsername()!=null && 
					lOnlyOneClientConfigBean.getPassword()!=null && lOnlyOneClientConfigBean.getAuthorizationToken()!=null
					 && lOnlyOneClientConfigBean.getEndpoint()!=null  && lOnlyOneClientConfigBean.getTimeout()!=null) {
				username = lOnlyOneClientConfigBean.getUsername();
				password = lOnlyOneClientConfigBean.getPassword();
				token = lOnlyOneClientConfigBean.getAuthorizationToken();
				timeout = lOnlyOneClientConfigBean.getTimeout();
				endpoint = lOnlyOneClientConfigBean.getEndpoint();
			}else {
				throw new Exception("Parametri di configurazione OnlyOne mancanti o incompleti");
			}
			
			Client client = Client.create(new DefaultClientConfig());
			client.setReadTimeout(timeout);

			webResource = client.resource(endpoint);
			
			
		}  catch (Exception e) {
			String errorMesage = "Errore durante la configurazione dei servizi Onlyone: Errore = " + e.getMessage();
			logger.error(errorMesage);
			throw new Exception(errorMesage);
		}			
	
	}
	
	public ResultBean<String> interrogaDatiPratica(String numProtocollo) throws Exception {
		String metaDatiXml = null;		
		ResultBean<String> result = new ResultBean<>();
		
		try {
			
			ClientResponse response = webResource
//					.path(path)
					.queryParam(OPENAGENT, "")
					.queryParam(USERNAME, username)
					.queryParam(PASSWORD, password)
					.queryParam(PROTOCOLLO, numProtocollo)
					.header(AUTHORIZATION, "Bearer " + token)
			        .get(ClientResponse.class);		
			
			metaDatiXml= response.getEntity(String.class);
				
		} catch(Exception e) {
			String errorMessage = "Errore durante la chiamata al servizio interrogaDatiPratica: " + e.getMessage();
			logger.error(errorMessage, e);
			result.setInError(true);
			result.setDefaultMessage(errorMessage);
			return result;
		}
		
		if (StringUtils.isNotBlank(metaDatiXml)) {			
			if(checkResponse(metaDatiXml)) {
				result.setInError(false);
				result.setResultBean(metaDatiXml);
				return result;
			}else {
				String errorMessage = "Il servizio interrogaDatiPratica ha restituito un errore: " + metaDatiXml;
				logger.error(errorMessage);
				result.setInError(true);
				result.setDefaultMessage(errorMessage);
				return result;
			}			
		}
		else {
			String errorMessage = "Il servizio interrogaDatiPratica non ha restituito risultato";
			logger.error(errorMessage);
			result.setInError(true);
			result.setDefaultMessage(errorMessage);
			return result;
		}
		
		
	}
	
	private boolean checkResponse(String response){		
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(response));
			Document document = builder.parse(is);

			NodeList nList = document.getElementsByTagName("ERRORE");
			if(nList.getLength()>0) {
				return false;
			}
		} catch (Exception e) {
			logger.error("Errore durante la lettura della response del servizio: " + e.getMessage(),e);
			return false;
		}
		
		return true;
	}
	
}
