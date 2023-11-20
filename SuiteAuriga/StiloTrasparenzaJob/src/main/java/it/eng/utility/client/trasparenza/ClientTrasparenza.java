/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.gson.Gson;
import com.sun.jersey.api.client.ClientResponse;

import it.eng.utility.client.trasparenza.rest.ProxyConfig;
import it.eng.utility.client.trasparenza.rest.RestRequestBean;
import it.eng.utility.client.trasparenza.rest.RestService;
import it.eng.utility.client.trasparenza.rest.TimeoutConfig;
import it.eng.utility.data.InsertProvvedimentoTrasparenzaRequest;
import it.eng.utility.data.InsertProvvedimentoTrasparenzaResponse;
import it.eng.utility.data.clearo.RequestAttributesClearoBean;
import it.eng.utility.data.clearo.RequestBodyClearoBean;
import it.eng.utility.data.clearo.RequestDataClearoBean;
import it.eng.utility.data.clearo.RequestFieldAnnoClearoBean;
import it.eng.utility.data.clearo.RequestFieldDataProvvedimentoClearoBean;
import it.eng.utility.data.clearo.RequestFieldIndiceFascicoloClearoBean;
import it.eng.utility.data.clearo.RequestFieldNProvvedimentoClearoBean;
import it.eng.utility.data.clearo.RequestFieldOggettoClearoBean;
import it.eng.utility.data.clearo.RequestFieldPaginaAlberaturaClearoBean;
import it.eng.utility.data.clearo.RequestFieldProvvedimentoClearoBean;
import it.eng.utility.data.clearo.RequestFieldSemestreClearoBean;
import it.eng.utility.data.clearo.RequestFieldSpesaPrevistaClearoBean;
import it.eng.utility.data.clearo.RequestFieldTipoProvvedimentoClearoBean;
import it.eng.utility.data.clearo.RequestRelDataClearoBean;
import it.eng.utility.data.clearo.RequestRelationshipsClearoBean;

public class ClientTrasparenza {

	public static final String BEAN_ID = "clientTrasparenza";
	private static final Logger logger = Logger.getLogger(ClientTrasparenza.class);
	private String trasparenzaEndpointRest;
	private String trasparenzaUsername;
	private String trasparenzaPassword;
	private String trasparenzaProxyUrl;
	private String trasparenzaProxyPort;
	private String trasparenzaProxyEnabled;
	private String trasparenzaConnectionTimeout;
	private String trasparenzaReadTimeout;
	private String trasparenzaChiamataTipo;
	private String trasparenzaValoreFormato;
	private String trasparenzaRelTipo;
	private String trasparenzaRelId;
	
	public ClientTrasparenza() {
		
	}
	
	public InsertProvvedimentoTrasparenzaResponse inserisciProvvedimentoTrasparenza(InsertProvvedimentoTrasparenzaRequest input) {
		logger.debug("inizio metodo insertProvvedimentoTrasparenza");
		
		InsertProvvedimentoTrasparenzaResponse response = null;
		try {
			// generazione token autenticazione
			String token = generaTokenAuth();
			//String token = null;
			logger.info("token " + token);
			
			// acquisizione parametri da passare a servizio
			RestRequestBean requestBean = setInsertParamRequest(input, token);
			
			// chiamata servizio rest
			RestService restService = new RestService();
			ClientResponse clientResponse = restService.callPostService(requestBean, token);
			
			// generazione response
			response = generaResponse(clientResponse);;
			
			logger.info("Response servizio " + response.toString());
		} catch (Exception e) {
			logger.error("Errore generico: " + e.getMessage());
			
		}
		
		return response;
	}
	
	private String generaTokenAuth() {
		String result = null;
		
		String auth = trasparenzaUsername + ":" + trasparenzaPassword;
		result = "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());
		
		return result;
	}
	
	private RestRequestBean setInsertParamRequest(InsertProvvedimentoTrasparenzaRequest input, String token) {
		logger.info("parametri input servizio trasparenza: " + input.toString());
		
		// conversione bean parametri in stringa json
		String json = generaJson(input);
		
		logger.info("json generato: " + json);
		
		ProxyConfig proxyConfig = getProxyConfig();
		TimeoutConfig timeoutConfig = getTimeoutConfig();
		
		RestRequestBean request = new RestRequestBean();
		request.setEndpoint(trasparenzaEndpointRest);
		request.setParamJson(json);
		request.setToken(token);
		request.setProxyConfig(proxyConfig);
		request.setTimeoutConfig(timeoutConfig);
		request.setNomeServizio("inserisciProvvedimentoTrasparenza");
		
		return request;
	}
	
	private InsertProvvedimentoTrasparenzaResponse generaResponse(ClientResponse clientResponse) {
		InsertProvvedimentoTrasparenzaResponse response = new InsertProvvedimentoTrasparenzaResponse();
		
		try {
			if (clientResponse != null) {
				int status = clientResponse.getStatus();
				String responseJson = clientResponse.getEntity(String.class);
				
				logger.info("response json servizio: " + responseJson);
				
				// conversione response json in oggetto java
				ObjectMapper mapper = new ObjectMapper();
				Map<String, Object> responseMap = mapper.readValue(responseJson, new org.codehaus.jackson.type.TypeReference<Map<String, Object>>() {});
				
				if (status == 201) {
					response.setEsito(true);
					response.setStatus(status);
					response.setResponseMsg("Provvedimento inserito correttamente in trasparenza");
				}
				else if (status == 408) {
					response.setEsito(false);
					response.setStatus(status);
					response.setResponseMsg("Errore connection timeout");
				}
				else {
					// acquisizione messaggio di errore da response servizio
					String msgErrore = generaMessaggioErrore(responseMap);
					
					response.setEsito(false);
					response.setStatus(status);
					response.setResponseMsg(msgErrore);
				}
			}
		} catch (JsonParseException e) {
			response.setEsito(false);
			response.setStatus(0);
			response.setResponseMsg(e.getMessage());
			
			logger.error(e.getMessage());
		} catch (JsonMappingException e) {
			response.setEsito(false);
			response.setStatus(0);
			response.setResponseMsg(e.getMessage());
			
			logger.error(e.getMessage());
		} catch (IOException e) {
			response.setEsito(false);
			response.setStatus(0);
			response.setResponseMsg(e.getMessage());
			
			logger.error(e.getMessage());
		} catch (Exception e) {
			response.setEsito(false);
			response.setStatus(0);
			response.setResponseMsg(e.getMessage());
			
			logger.error(e.getMessage());
		}
		
		return response;
	}
	
	private String generaJson(InsertProvvedimentoTrasparenzaRequest request) {
		String json = null;
		
		// generazione bean request clearo
		RequestBodyClearoBean bodyClearo = generaBeanBodyJson(request);
		
		// generazione stringa json
		if (bodyClearo != null) {
			// conversione bean parametri in stringa json
			json = new Gson().toJson(bodyClearo);
		}
		
		return json;
	}
	
	private RequestBodyClearoBean generaBeanBodyJson(InsertProvvedimentoTrasparenzaRequest request) {
		List<String> options = new ArrayList<String>();
		
		RequestFieldAnnoClearoBean fieldAnno = new RequestFieldAnnoClearoBean();
		fieldAnno.setValue(request.getAnnoProvvedimento());
		
		RequestFieldSemestreClearoBean fieldSemestre = new RequestFieldSemestreClearoBean();
		fieldSemestre.setValue(request.getSemestreProvvedimento());
		
		RequestFieldNProvvedimentoClearoBean fieldNProvvedimento = new RequestFieldNProvvedimentoClearoBean();
		fieldNProvvedimento.setValue(request.getNumeroProvvedimento());
		fieldNProvvedimento.setFormat(trasparenzaValoreFormato);
		
		RequestFieldDataProvvedimentoClearoBean fieldDataProvvedimento = new RequestFieldDataProvvedimentoClearoBean();
		fieldDataProvvedimento.setValue(request.getDataProvvedimento());
		fieldDataProvvedimento.setFormat(trasparenzaValoreFormato);
		
		RequestFieldIndiceFascicoloClearoBean fieldIndiceFascicolo = new RequestFieldIndiceFascicoloClearoBean();
		fieldIndiceFascicolo.setUri("");
		fieldIndiceFascicolo.setOptions(options);
		
		RequestFieldOggettoClearoBean fieldOggetto = new RequestFieldOggettoClearoBean();
		fieldOggetto.setValue(request.getOggettoProvvedimeno());
		fieldOggetto.setFormat(trasparenzaValoreFormato);
		
		RequestFieldProvvedimentoClearoBean fieldProvvedimento = new RequestFieldProvvedimentoClearoBean();
		fieldProvvedimento.setUri("");
		fieldProvvedimento.setTitle("");
		fieldProvvedimento.setOptions(options);
		
		RequestFieldSpesaPrevistaClearoBean fieldSpesaPrevista = new RequestFieldSpesaPrevistaClearoBean();
		fieldSpesaPrevista.setValue("");
		fieldSpesaPrevista.setFormat(trasparenzaValoreFormato);
		
		RequestFieldTipoProvvedimentoClearoBean fieldTipoProvvedimento = new RequestFieldTipoProvvedimentoClearoBean();
		fieldTipoProvvedimento.setValue(request.getTipoProvvedimento());
		fieldTipoProvvedimento.setFormat(trasparenzaValoreFormato);
		
		RequestAttributesClearoBean attributes = new RequestAttributesClearoBean();
		attributes.setField_anno(fieldAnno);
		attributes.setField_semestre(fieldSemestre);
		attributes.setField_n_provvedimento(fieldNProvvedimento);
		attributes.setField_data_provvedimento(fieldDataProvvedimento);
		attributes.setField_indice_fascicolo(fieldIndiceFascicolo);
		attributes.setField_oggetto(fieldOggetto);
		attributes.setField_provvedimento(fieldProvvedimento);
		attributes.setField_spesa_prevista(fieldSpesaPrevista);
		attributes.setField_tipo_provvedimento(fieldTipoProvvedimento);
		
		RequestRelDataClearoBean relData = new RequestRelDataClearoBean();
		relData.setType(trasparenzaRelTipo);
		relData.setId(trasparenzaRelId);
		
		RequestFieldPaginaAlberaturaClearoBean fieldPaginaAlberatura = new RequestFieldPaginaAlberaturaClearoBean();
		fieldPaginaAlberatura.setData(relData);
		
		RequestRelationshipsClearoBean relationships = new RequestRelationshipsClearoBean();
		relationships.setField_pagina_alberatura(fieldPaginaAlberatura);
		
		RequestDataClearoBean data = new RequestDataClearoBean();
		data.setType(trasparenzaChiamataTipo);
		data.setAttributes(attributes);
		data.setRelationships(relationships);
		
		RequestBodyClearoBean bodyClearo = new RequestBodyClearoBean();
		bodyClearo.setData(data);
		
		return bodyClearo;
	}
	
	private String generaMessaggioErrore(Map<String, Object> responseMap) {
		String msgErrore = "";
		
		if (responseMap.containsKey("errors")) {
			List<Map<String, Object>> error = (List<Map<String, Object>>) responseMap.get("errors");
			
			if (error.size() > 0) {
				if (error.get(0).containsKey("title")) {
					msgErrore += error.get(0).get("title");
				}
				
				if (error.get(0).containsKey("detail")) {
					msgErrore += " " + error.get(0).get("detail");
				}
			}
		}
		
		return msgErrore;
	}
	
	private ProxyConfig getProxyConfig() {
		boolean proxyEnabled = convertiStringToBoolean(trasparenzaProxyEnabled);
		
		ProxyConfig proxyConfig = new ProxyConfig();
		proxyConfig.setProxyUrl(trasparenzaProxyUrl);
		proxyConfig.setProxyPort(trasparenzaProxyPort);
		proxyConfig.setProxyEnabled(proxyEnabled);
		
		return proxyConfig;
	}
	
	private TimeoutConfig getTimeoutConfig() {
		Integer connectionTimeout = null;
		if (trasparenzaConnectionTimeout != null) {
			connectionTimeout = Integer.parseInt(trasparenzaConnectionTimeout);
		}
		
		Integer readTimeout = null;
		if (trasparenzaReadTimeout != null) {
			readTimeout = Integer.parseInt(trasparenzaReadTimeout);
		}
		
		TimeoutConfig timeoutConfig = new TimeoutConfig();
		timeoutConfig.setConnectionTimeout(connectionTimeout);
		timeoutConfig.setReadTimeout(readTimeout);
		
		return timeoutConfig;
	}
	
	private boolean convertiStringToBoolean(String value) {
		boolean result = false;
		
		if (value != null) {
			result = Boolean.parseBoolean(value);
		}
		
		return result;
	}
	
	
	public String getTrasparenzaEndpointRest() {
		return trasparenzaEndpointRest;
	}
	
	public void setTrasparenzaEndpointRest(String trasparenzaEndpointRest) {
		this.trasparenzaEndpointRest = trasparenzaEndpointRest;
	}
	
	public String getTrasparenzaUsername() {
		return trasparenzaUsername;
	}
	
	public void setTrasparenzaUsername(String trasparenzaUsername) {
		this.trasparenzaUsername = trasparenzaUsername;
	}
	
	public String getTrasparenzaPassword() {
		return trasparenzaPassword;
	}
	
	public void setTrasparenzaPassword(String trasparenzaPassword) {
		this.trasparenzaPassword = trasparenzaPassword;
	}
	
	public String getTrasparenzaProxyUrl() {
		return trasparenzaProxyUrl;
	}
	
	public void setTrasparenzaProxyUrl(String trasparenzaProxyUrl) {
		this.trasparenzaProxyUrl = trasparenzaProxyUrl;
	}
	
	public String getTrasparenzaProxyPort() {
		return trasparenzaProxyPort;
	}
	
	public void setTrasparenzaProxyPort(String trasparenzaProxyPort) {
		this.trasparenzaProxyPort = trasparenzaProxyPort;
	}
	
	public String getTrasparenzaProxyEnabled() {
		return trasparenzaProxyEnabled;
	}
	
	public void setTrasparenzaProxyEnabled(String trasparenzaProxyEnabled) {
		this.trasparenzaProxyEnabled = trasparenzaProxyEnabled;
	}
	
	public String getTrasparenzaConnectionTimeout() {
		return trasparenzaConnectionTimeout;
	}
	
	public void setTrasparenzaConnectionTimeout(String trasparenzaConnectionTimeout) {
		this.trasparenzaConnectionTimeout = trasparenzaConnectionTimeout;
	}
	
	public String getTrasparenzaReadTimeout() {
		return trasparenzaReadTimeout;
	}
	
	public void setTrasparenzaReadTimeout(String trasparenzaReadTimeout) {
		this.trasparenzaReadTimeout = trasparenzaReadTimeout;
	}

	public String getTrasparenzaChiamataTipo() {
		return trasparenzaChiamataTipo;
	}

	public void setTrasparenzaChiamataTipo(String trasparenzaChiamataTipo) {
		this.trasparenzaChiamataTipo = trasparenzaChiamataTipo;
	}

	public String getTrasparenzaValoreFormato() {
		return trasparenzaValoreFormato;
	}

	public void setTrasparenzaValoreFormato(String trasparenzaValoreFormato) {
		this.trasparenzaValoreFormato = trasparenzaValoreFormato;
	}

	public String getTrasparenzaRelTipo() {
		return trasparenzaRelTipo;
	}

	public void setTrasparenzaRelTipo(String trasparenzaRelTipo) {
		this.trasparenzaRelTipo = trasparenzaRelTipo;
	}

	public String getTrasparenzaRelId() {
		return trasparenzaRelId;
	}

	public void setTrasparenzaRelId(String trasparenzaRelId) {
		this.trasparenzaRelId = trasparenzaRelId;
	}
	
}
