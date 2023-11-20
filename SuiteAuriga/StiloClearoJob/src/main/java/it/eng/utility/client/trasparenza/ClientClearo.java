/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Hashtable;

import javax.xml.rpc.ServiceException;

import org.apache.axis.transport.http.HTTPConstants;
import org.apache.log4j.Logger;

import it.eng.utility.clearo.trasparenza.wsdl.DdlWrapperServiceSoapServiceLocator;
import it.eng.utility.clearo.trasparenza.wsdl.Plugin_ddlWrapper_ddlWrapperServiceSoapBindingStub;
import it.eng.utility.client.trasparenza.bean.Provvedimento;
import it.eng.utility.client.trasparenza.bean.ProvvedimentoOutput;
import it.csi.energee3.ddlhook.wrapper.model.DDLRecordDataSoap;

public class ClientClearo {
	
	private static final Logger logger = Logger.getLogger(ClientClearo.class);
	
	private DdlWrapperServiceSoapServiceLocator service;
	private String usernameToken;
	private BearerAuthSupplier bearerTokenAuth;
	private String tabellaId;
	
	public ClientClearo() {
	}
	
	public ProvvedimentoOutput aggiungiRecordTrasparenza(Provvedimento input) {
		logger.debug("inizio metodo aggiungiRecordTrasparenza");
		
		ProvvedimentoOutput output = new ProvvedimentoOutput();
		boolean result = false;
		String errorMsg = null;
		
		try {
			// id tabella in input
			Long recordsetId = new Long(tabellaId);
			
			// hashmap con campi e valore da passare al servizio in input
			HashMap<String, String> fieldsMap = new HashMap<>();
			fieldsMap.put("anno", input.getAnno());
			fieldsMap.put("semestre", input.getSemestre());
			fieldsMap.put("numprov", input.getNumProv());
			//fieldsMap.put("url_provvedimento", input.getUrlProvvedimento());
			//fieldsMap.put("alias_provvedimento", input.getAliasProvvedimento());
			fieldsMap.put("data_prov", input.getDataProv());
			fieldsMap.put("flag_concessione", input.getFlagConcessione());
			fieldsMap.put("oggetto", input.getOggetto());
			//fieldsMap.put("spesa", input.getSpesa());
			//fieldsMap.put("url_indice_fascicolo", input.getUrlIndiceFascicolo());
			//fieldsMap.put("alias_indice_fascicolo", input.getAliasIndiceFascicolo());
			
			logger.info("Parametri input WS - recordsetId: " + recordsetId + " - fieldsMap: " + fieldsMap.toString());
			
			long inizio = System.currentTimeMillis();
			
			// chiamata ws del servizio addRecord
			DDLRecordDataSoap response = getStub().addRecord(recordsetId, fieldsMap);
			
			long fine = System.currentTimeMillis();
			
			logger.info(esecuzioneServizio(inizio, fine, "addRecord"));
			
			if (response != null && response.getFields() != null) {
				result = true;
				
				logger.info("Chiamata WS addRrcord eseguita con successo: " + response.getPrimaryKey() + " - " + response.getFields());
			}
		} catch (RemoteException e) {
			errorMsg = e.getMessage();
			
			logger.error(e.getMessage());
		} catch (ServiceException e) {
			errorMsg = e.getMessage();
			
			logger.error(e.getMessage());
		} catch (Exception e) {
			errorMsg = e.getMessage();
			
			logger.error(e.getMessage());
		}
		
		output.setResult(result);
		output.setErrorMsg(errorMsg);
		
		logger.debug("fine metodo aggiungiRecordTrasparenza con esito " + result);
		
		return output;
	}
	
	public void setService(DdlWrapperServiceSoapServiceLocator service) {
		this.service = service;
	}

	public void setUsernameToken(String usernameToken) {
		this.usernameToken = usernameToken;
	}

	public void setBearerTokenAuth(BearerAuthSupplier bearerTokenAuth) {
		this.bearerTokenAuth = bearerTokenAuth;
	}

	public void setTabellaId(String tabellaId) {
		this.tabellaId = tabellaId;
	}

	private Plugin_ddlWrapper_ddlWrapperServiceSoapBindingStub getStub() throws ServiceException {
		// bearer token
		String bearerToken = bearerTokenAuth.getTokenAuth();
		logger.info("Acquisione token Authorization: " + bearerToken);
		
		// username token
		String basicUsernameToken = "Basic " + usernameToken;
		logger.info("Acquisione usernameToken X-Authorization: " + basicUsernameToken);
		
		// definizione doppia autenticazione in header
		Hashtable<String, String> headers = new Hashtable<String, String>();
		headers.put("Authorization", bearerToken);
		headers.put("X-Authorization", basicUsernameToken);
		
		final Plugin_ddlWrapper_ddlWrapperServiceSoapBindingStub stub = (Plugin_ddlWrapper_ddlWrapperServiceSoapBindingStub) service.getPlugin_ddlWrapper_ddlWrapperService();
		stub._setProperty(HTTPConstants.REQUEST_HEADERS, headers);
		
		logger.info("Token di autorizzaione settati in header");
		
		return stub;
	}
	
	private String esecuzioneServizio(long inizio, long fine, String nomeServizio) {
		NumberFormat formatter = new DecimalFormat("#0.00000");
		
		String result = "Esecuzione servizio WS " + nomeServizio + " in " + formatter.format((fine - inizio) / 1000d) + " secondi";
		
		return result;
	}
	
}
