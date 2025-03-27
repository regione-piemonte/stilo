/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.utility.client.trasparenza;

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
import it.eng.utility.data.InsertProvvedimentoTrasparenzaAslVcRequest;
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
import it.eng.utility.data.clearo.aslVc.RequestAttributesDeliberaClearoAslVcBean;
import it.eng.utility.data.clearo.aslVc.RequestAttributesDeterminaClearoAslVcBean;
import it.eng.utility.data.clearo.aslVc.RequestBodyDeliberaClearoAslVcBean;
import it.eng.utility.data.clearo.aslVc.RequestBodyDeterminaClearoAslVcBean;
import it.eng.utility.data.clearo.aslVc.RequestDataDeliberaClearoAslVcBean;
import it.eng.utility.data.clearo.aslVc.RequestDataDeterminaClearoAslVcBean;
import it.eng.utility.data.clearo.aslVc.RequestFieldAnnoClearoAslVcBean;
import it.eng.utility.data.clearo.aslVc.RequestFieldArtContenutoClearoAslVcBean;
import it.eng.utility.data.clearo.aslVc.RequestFieldArtEstremiClearoAslVcBean;
import it.eng.utility.data.clearo.aslVc.RequestFieldArtImportoClearoAslVcBean;
import it.eng.utility.data.clearo.aslVc.RequestFieldArtNoteClearoAslVcBean;
import it.eng.utility.data.clearo.aslVc.RequestFieldArtOggettoClearoAslVcBean;
import it.eng.utility.data.clearo.aslVc.RequestFieldArtTipologiaClearoAslVcBean;
import it.eng.utility.data.clearo.aslVc.RequestFieldDataDeterminaClearoAslVcBean;
import it.eng.utility.data.clearo.aslVc.RequestFieldDecorrenzaAlClearoAslVcBean;
import it.eng.utility.data.clearo.aslVc.RequestFieldDecorrenzaDalClearoAslVcBean;
import it.eng.utility.data.clearo.aslVc.RequestFieldDeliberaDelClearoAslVcBean;
import it.eng.utility.data.clearo.aslVc.RequestFieldDeliberaNumeroClearoAslVcBean;
import it.eng.utility.data.clearo.aslVc.RequestFieldMeseClearoAslVcBean;
import it.eng.utility.data.clearo.aslVc.RequestFieldNumeroDeterminaClearoAslVcBean;
import it.eng.utility.data.clearo.aslVc.RequestFieldOggettoClearoAslVcBean;
import it.eng.utility.data.clearo.aslVc.RequestFieldPaginaAlberaturaClearoAslVcBean;
import it.eng.utility.data.clearo.aslVc.RequestFieldTipologiaClearoAslVcBean;
import it.eng.utility.data.clearo.aslVc.RequestRelDataClearoAslVcBean;
import it.eng.utility.data.clearo.aslVc.RequestRelationshipsClearoAslVcBean;

public class ClientTrasparenzaAslVc {
	
	public static final String BEAN_ID = "clientTrasparenza";
	private static final Logger logger = Logger.getLogger(ClientTrasparenza.class);
	private String trasparenzaAslVcEndpointDDRest;
	private String trasparenzaAslVcEndpointDGRest;
	private String trasparenzaAslVcUsername;
	private String trasparenzaAslVcPassword;
	private String trasparenzaAslVcChiamataDDTipo;
	private String trasparenzaAslVcChiamataDGTipo;
	private String trasparenzaAslVcValoreFormato;
	private String trasparenzaAslVcRelTipo;
	private String trasparenzaAslVcRelDDId;
	private String trasparenzaAslVcRelDGId;
	private String trasparenzaProxyUrl;
	private String trasparenzaProxyPort;
	private String trasparenzaProxyEnabled;
	private String trasparenzaConnectionTimeout;
	private String trasparenzaReadTimeout;
	
	public ClientTrasparenzaAslVc() {
		
	}
	
	public InsertProvvedimentoTrasparenzaResponse inserisciDeterminaTrasparenzaAslVc(InsertProvvedimentoTrasparenzaAslVcRequest input) {
		logger.debug("inizio metodo inserisciDeterminaTrasparenzaAslVc");
		
		InsertProvvedimentoTrasparenzaResponse response = null;
		try {
			// generazione token autenticazione
			String token = generaTokenAuth();
			logger.info("token " + token);
			
			// acquisizione parametri da passare a servizio
			RestRequestBean requestBean = setInsertDeterminaParamRequest(input, token);
			
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
	
	public InsertProvvedimentoTrasparenzaResponse inserisciDeliberaTrasparenzaAslVc(InsertProvvedimentoTrasparenzaAslVcRequest input) {
		logger.debug("inizio metodo inserisciDeliberaTrasparenzaAslVc");
		
		InsertProvvedimentoTrasparenzaResponse response = null;
		try {
			// generazione token autenticazione
			String token = generaTokenAuth();
			logger.info("token " + token);
			
			// acquisizione parametri da passare a servizio
			RestRequestBean requestBean = setInsertDeliberaParamRequest(input, token);
			
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
		
		String auth = trasparenzaAslVcUsername + ":" + trasparenzaAslVcPassword;
		result = "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());
		
		return result;
	}
	
	private RestRequestBean setInsertDeterminaParamRequest(InsertProvvedimentoTrasparenzaAslVcRequest input, String token) {
		logger.info("parametri input servizio trasparenza: " + input.toString());
		
		// conversione bean parametri in stringa json
		String json = generaJsonDetermina(input);
		
		logger.info("json generato: " + json);
		
		ProxyConfig proxyConfig = getProxyConfig();
		TimeoutConfig timeoutConfig = getTimeoutConfig();
		
		RestRequestBean request = new RestRequestBean();
		request.setEndpoint(trasparenzaAslVcEndpointDDRest);
		request.setParamJson(json);
		request.setToken(token);
		request.setProxyConfig(proxyConfig);
		request.setTimeoutConfig(timeoutConfig);
		request.setNomeServizio("inserisciDeterminaTrasparenzaAslVc");
		
		return request;
	}
	
	private RestRequestBean setInsertDeliberaParamRequest(InsertProvvedimentoTrasparenzaAslVcRequest input, String token) {
		logger.info("parametri input servizio trasparenza: " + input.toString());
		
		// conversione bean parametri in stringa json
		String json = generaJsonDelibera(input);
		
		logger.info("json generato: " + json);
		
		ProxyConfig proxyConfig = getProxyConfig();
		TimeoutConfig timeoutConfig = getTimeoutConfig();
		
		RestRequestBean request = new RestRequestBean();
		request.setEndpoint(trasparenzaAslVcEndpointDGRest);
		request.setParamJson(json);
		request.setToken(token);
		request.setProxyConfig(proxyConfig);
		request.setTimeoutConfig(timeoutConfig);
		request.setNomeServizio("inserisciDeliberaTrasparenzaAslVc");
		
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
	
	private String generaJsonDetermina(InsertProvvedimentoTrasparenzaAslVcRequest request) {
		String json = null;
		
		// generazione bean request clearo
		RequestBodyDeterminaClearoAslVcBean bodyClearo = generaBeanBodyDeterminaJson(request);
		
		// generazione stringa json
		if (bodyClearo != null) {
			// conversione bean parametri in stringa json
			json = new Gson().toJson(bodyClearo);
		}
		
		return json;
	}
	
	private String generaJsonDelibera(InsertProvvedimentoTrasparenzaAslVcRequest request) {
		String json = null;
		
		// generazione bean request clearo
		RequestBodyDeliberaClearoAslVcBean bodyClearo = generaBeanBodyDeliberaJson(request);
		
		// generazione stringa json
		if (bodyClearo != null) {
			// conversione bean parametri in stringa json
			json = new Gson().toJson(bodyClearo);
		}
		
		return json;
	}
	
	private RequestBodyDeterminaClearoAslVcBean generaBeanBodyDeterminaJson(InsertProvvedimentoTrasparenzaAslVcRequest request) {
		RequestFieldAnnoClearoAslVcBean fieldAnno = new RequestFieldAnnoClearoAslVcBean();
		fieldAnno.setValue(request.getAnnoProvvedimento());
		
		RequestFieldMeseClearoAslVcBean fieldMese = new RequestFieldMeseClearoAslVcBean();
		fieldMese.setValue(request.getMeseProvvedimento());
		
		RequestFieldNumeroDeterminaClearoAslVcBean fieldNumeroDetermina = new RequestFieldNumeroDeterminaClearoAslVcBean();
		fieldNumeroDetermina.setValue(request.getNumeroProvvedimento());
		
		RequestFieldDataDeterminaClearoAslVcBean fieldDataDetermina = new RequestFieldDataDeterminaClearoAslVcBean();
		fieldDataDetermina.setValue(request.getDataProvvedimento());
		
		RequestFieldOggettoClearoAslVcBean fieldOggetto = new RequestFieldOggettoClearoAslVcBean();
		fieldOggetto.setValue(request.getOggettoProvvedimeno());
		fieldOggetto.setFormat(trasparenzaAslVcValoreFormato);
		
		RequestFieldDecorrenzaDalClearoAslVcBean fieldDecorrenzaDal = new RequestFieldDecorrenzaDalClearoAslVcBean();
		fieldDecorrenzaDal.setValue(request.getDataPubblicazioneDal());
		
		RequestFieldDecorrenzaAlClearoAslVcBean fieldDecorrenzaAl = new RequestFieldDecorrenzaAlClearoAslVcBean();
		fieldDecorrenzaAl.setValue(request.getDataPubblicazioneAl());
		
		RequestFieldArtOggettoClearoAslVcBean fieldArtOggetto = new RequestFieldArtOggettoClearoAslVcBean();
		fieldArtOggetto.setValue("");
		fieldArtOggetto.setFormat(trasparenzaAslVcValoreFormato);
		
		RequestFieldArtTipologiaClearoAslVcBean fieldArtTipologia = new  RequestFieldArtTipologiaClearoAslVcBean();
		fieldArtTipologia.setValue("");
		fieldArtTipologia.setFormat(trasparenzaAslVcValoreFormato);
		
		RequestFieldArtContenutoClearoAslVcBean fieldArtContenuto = new RequestFieldArtContenutoClearoAslVcBean();
		fieldArtContenuto.setValue("");
		fieldArtContenuto.setFormat(trasparenzaAslVcValoreFormato);
		
		RequestFieldArtEstremiClearoAslVcBean fieldArtEstremi = new RequestFieldArtEstremiClearoAslVcBean();
		fieldArtEstremi.setValue("");
		fieldArtEstremi.setFormat(trasparenzaAslVcValoreFormato);
		
		RequestFieldArtImportoClearoAslVcBean fieldArtImporto = new RequestFieldArtImportoClearoAslVcBean();
		fieldArtImporto.setValue("");
		
		RequestFieldArtNoteClearoAslVcBean fieldArtNote = new RequestFieldArtNoteClearoAslVcBean();
		fieldArtNote.setValue("");
		fieldArtNote.setFormat(trasparenzaAslVcValoreFormato);
		
		RequestAttributesDeterminaClearoAslVcBean attributes = new RequestAttributesDeterminaClearoAslVcBean();
		attributes.setField_anno(fieldAnno);
		attributes.setField_mese(fieldMese);
		attributes.setField_numero_determina(fieldNumeroDetermina);
		attributes.setField_data_determina(fieldDataDetermina);
		attributes.setField_oggetto(fieldOggetto);
		attributes.setField_decorrenza_dal(fieldDecorrenzaDal);
		attributes.setField_decorrenza_al(fieldDecorrenzaAl);
		attributes.setField_art23_oggetto(fieldArtOggetto);
		attributes.setField_art23_tipologia(fieldArtTipologia);
		attributes.setField_art23_contenuto(fieldArtContenuto);
		attributes.setField_art23_estremi(fieldArtEstremi);
		attributes.setField_art23_importo(fieldArtImporto);
		attributes.setField_art23_note(fieldArtNote);
		
		RequestRelDataClearoAslVcBean relData = new RequestRelDataClearoAslVcBean();
		relData.setType(trasparenzaAslVcRelTipo);
		relData.setId(trasparenzaAslVcRelDDId);
		
		RequestFieldPaginaAlberaturaClearoAslVcBean fieldPAginaAlberatura = new RequestFieldPaginaAlberaturaClearoAslVcBean();
		fieldPAginaAlberatura.setData(relData);
		
		RequestRelationshipsClearoAslVcBean relationships = new RequestRelationshipsClearoAslVcBean();
		relationships.setField_pagina_alberatura(fieldPAginaAlberatura);
		
		RequestDataDeterminaClearoAslVcBean data = new RequestDataDeterminaClearoAslVcBean();
		data.setType(trasparenzaAslVcChiamataDDTipo);
		data.setAttributes(attributes);
		data.setRelationships(relationships);
		
		RequestBodyDeterminaClearoAslVcBean bodyDetermine = new RequestBodyDeterminaClearoAslVcBean();
		bodyDetermine.setData(data);
		
		return bodyDetermine;
	}
	
	private RequestBodyDeliberaClearoAslVcBean generaBeanBodyDeliberaJson(InsertProvvedimentoTrasparenzaAslVcRequest request) {
		RequestFieldAnnoClearoAslVcBean fieldAnno = new RequestFieldAnnoClearoAslVcBean();
		fieldAnno.setValue(request.getAnnoProvvedimento());
		
		RequestFieldMeseClearoAslVcBean fieldMese = new RequestFieldMeseClearoAslVcBean();
		fieldMese.setValue(request.getMeseProvvedimento());
		
		RequestFieldTipologiaClearoAslVcBean fieldTipologia = new RequestFieldTipologiaClearoAslVcBean();
		fieldTipologia.setValue(request.getTipoProvvedimento());
		
		RequestFieldDeliberaNumeroClearoAslVcBean fieldNumeroDelibera = new RequestFieldDeliberaNumeroClearoAslVcBean();
		fieldNumeroDelibera.setValue(request.getNumeroProvvedimento());
		
		RequestFieldDeliberaDelClearoAslVcBean fieldDeliberaDel = new RequestFieldDeliberaDelClearoAslVcBean();
		fieldDeliberaDel.setValue(request.getDataProvvedimento());
		
		RequestFieldOggettoClearoAslVcBean fieldOggetto = new RequestFieldOggettoClearoAslVcBean();
		fieldOggetto.setValue(request.getOggettoProvvedimeno());
		fieldOggetto.setFormat(trasparenzaAslVcValoreFormato);
		
		RequestFieldDecorrenzaDalClearoAslVcBean fieldDecorrenzaDal = new RequestFieldDecorrenzaDalClearoAslVcBean();
		fieldDecorrenzaDal.setValue(request.getDataPubblicazioneDal());
		
		RequestFieldDecorrenzaAlClearoAslVcBean fieldDecorrenzaAl = new RequestFieldDecorrenzaAlClearoAslVcBean();
		fieldDecorrenzaAl.setValue(request.getDataPubblicazioneAl());
		
		RequestFieldArtOggettoClearoAslVcBean fieldArtOggetto = new RequestFieldArtOggettoClearoAslVcBean();
		fieldArtOggetto.setValue("");
		fieldArtOggetto.setFormat(trasparenzaAslVcValoreFormato);
		
		RequestFieldArtTipologiaClearoAslVcBean fieldArtTipologia = new  RequestFieldArtTipologiaClearoAslVcBean();
		fieldArtTipologia.setValue("");
		fieldArtTipologia.setFormat(trasparenzaAslVcValoreFormato);
		
		RequestFieldArtContenutoClearoAslVcBean fieldArtContenuto = new RequestFieldArtContenutoClearoAslVcBean();
		fieldArtContenuto.setValue("");
		fieldArtContenuto.setFormat(trasparenzaAslVcValoreFormato);
		
		RequestFieldArtEstremiClearoAslVcBean fieldArtEstremi = new RequestFieldArtEstremiClearoAslVcBean();
		fieldArtEstremi.setValue("");
		fieldArtEstremi.setFormat(trasparenzaAslVcValoreFormato);
		
		RequestFieldArtImportoClearoAslVcBean fieldArtImporto = new RequestFieldArtImportoClearoAslVcBean();
		fieldArtImporto.setValue("");
		
		RequestFieldArtNoteClearoAslVcBean fieldArtNote = new RequestFieldArtNoteClearoAslVcBean();
		fieldArtNote.setValue("");
		fieldArtNote.setFormat(trasparenzaAslVcValoreFormato);
		
		RequestAttributesDeliberaClearoAslVcBean attributes = new RequestAttributesDeliberaClearoAslVcBean();
		attributes.setField_anno(fieldAnno);
		attributes.setField_mese(fieldMese);
		attributes.setField_tipologia(fieldTipologia);
		attributes.setField_delibera_numero(fieldNumeroDelibera);
		attributes.setField_oggetto(fieldOggetto);
		attributes.setField_decorrenza_dal(fieldDecorrenzaDal);
		attributes.setField_decorrenza_al(fieldDecorrenzaAl);
		attributes.setField_art23_oggetto(fieldArtOggetto);
		attributes.setField_art23_tipologia(fieldArtTipologia);
		attributes.setField_art23_contenuto(fieldArtContenuto);
		attributes.setField_art23_estremi(fieldArtEstremi);
		attributes.setField_art23_importo(fieldArtImporto);
		attributes.setField_art23_note(fieldArtNote);
		
		RequestRelDataClearoAslVcBean relData = new RequestRelDataClearoAslVcBean();
		relData.setType(getTrasparenzaAslVcRelTipo());
		relData.setId(trasparenzaAslVcRelDGId);
		
		RequestFieldPaginaAlberaturaClearoAslVcBean fieldPAginaAlberatura = new RequestFieldPaginaAlberaturaClearoAslVcBean();
		fieldPAginaAlberatura.setData(relData);
		
		RequestRelationshipsClearoAslVcBean relationships = new RequestRelationshipsClearoAslVcBean();
		relationships.setField_pagina_alberatura(fieldPAginaAlberatura);
		
		RequestDataDeliberaClearoAslVcBean data = new RequestDataDeliberaClearoAslVcBean();
		data.setType(trasparenzaAslVcChiamataDGTipo);
		data.setAttributes(attributes);
		data.setRelationships(relationships);
		
		RequestBodyDeliberaClearoAslVcBean bodyDelibera = new RequestBodyDeliberaClearoAslVcBean();
		bodyDelibera.setData(data);
		
		return bodyDelibera;
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

	public String getTrasparenzaAslVcEndpointDDRest() {
		return trasparenzaAslVcEndpointDDRest;
	}

	public void setTrasparenzaAslVcEndpointDDRest(String trasparenzaAslVcEndpointDDRest) {
		this.trasparenzaAslVcEndpointDDRest = trasparenzaAslVcEndpointDDRest;
	}

	public String getTrasparenzaAslVcEndpointDGRest() {
		return trasparenzaAslVcEndpointDGRest;
	}

	public void setTrasparenzaAslVcEndpointDGRest(String trasparenzaAslVcEndpointDGRest) {
		this.trasparenzaAslVcEndpointDGRest = trasparenzaAslVcEndpointDGRest;
	}

	public String getTrasparenzaAslVcUsername() {
		return trasparenzaAslVcUsername;
	}

	public void setTrasparenzaAslVcUsername(String trasparenzaAslVcUsername) {
		this.trasparenzaAslVcUsername = trasparenzaAslVcUsername;
	}

	public String getTrasparenzaAslVcPassword() {
		return trasparenzaAslVcPassword;
	}

	public void setTrasparenzaAslVcPassword(String trasparenzaAslVcPassword) {
		this.trasparenzaAslVcPassword = trasparenzaAslVcPassword;
	}

	public String getTrasparenzaAslVcChiamataDDTipo() {
		return trasparenzaAslVcChiamataDDTipo;
	}

	public void setTrasparenzaAslVcChiamataDDTipo(String trasparenzaAslVcChiamataDDTipo) {
		this.trasparenzaAslVcChiamataDDTipo = trasparenzaAslVcChiamataDDTipo;
	}

	public String getTrasparenzaAslVcChiamataDGTipo() {
		return trasparenzaAslVcChiamataDGTipo;
	}

	public void setTrasparenzaAslVcChiamataDGTipo(String trasparenzaAslVcChiamataDGTipo) {
		this.trasparenzaAslVcChiamataDGTipo = trasparenzaAslVcChiamataDGTipo;
	}

	public String getTrasparenzaAslVcValoreFormato() {
		return trasparenzaAslVcValoreFormato;
	}

	public void setTrasparenzaAslVcValoreFormato(String trasparenzaAslVcValoreFormato) {
		this.trasparenzaAslVcValoreFormato = trasparenzaAslVcValoreFormato;
	}

	public String getTrasparenzaAslVcRelTipo() {
		return trasparenzaAslVcRelTipo;
	}

	public void setTrasparenzaAslVcRelTipo(String trasparenzaAslVcRelTipo) {
		this.trasparenzaAslVcRelTipo = trasparenzaAslVcRelTipo;
	}

	public String getTrasparenzaAslVcRelDDId() {
		return trasparenzaAslVcRelDDId;
	}

	public void setTrasparenzaAslVcRelDDId(String trasparenzaAslVcRelDDId) {
		this.trasparenzaAslVcRelDDId = trasparenzaAslVcRelDDId;
	}

	public String getTrasparenzaAslVcRelDGId() {
		return trasparenzaAslVcRelDGId;
	}

	public void setTrasparenzaAslVcRelDGId(String trasparenzaAslVcRelDGId) {
		this.trasparenzaAslVcRelDGId = trasparenzaAslVcRelDGId;
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
	
}
