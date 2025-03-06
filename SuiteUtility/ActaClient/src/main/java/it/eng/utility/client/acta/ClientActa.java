package it.eng.utility.client.acta;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.utility.client.acta.bean.ActaClientConfig;
import it.eng.utility.client.acta.bean.NodoSmistamento;
import it.eng.utility.client.acta.bean.VoceTitolarioBean;
import it.eng.utility.client.acta.object.WsActaObject;
import it.eng.utility.client.acta.object.backoffice.WSActaBackOffice;
import it.eng.utility.client.acta.object.repository.WSActaRepository;
import it.eng.utility.data.Output;


public final class ClientActa {

	private static final Logger logger = LoggerFactory.getLogger(ClientActa.class);
	private static final String CONFIG_FILE = "client-acta.properties";
	private static final String LOG_ERROR_MESSAGE = "Errore di comunicazione con Acta";
	
	private Properties properties;
	private ActaClientConfig clientConfig;
	
	public ClientActa() {		
	}
	
	public ClientActa(ActaClientConfig clientConfig) {
		this.clientConfig = clientConfig;
	}
	
	public void init() throws IOException {
		if (clientConfig == null) {
			clientConfig = new ActaClientConfig();
		}
		properties = retrieveProperties(CONFIG_FILE);
		if (logger.isDebugEnabled())
			logger.debug("CLIENT CONFIGURATION\n" + properties);
		clientConfig.init(properties);
	}
	
	
	public Output<String> ottieniFascicoloDossierInVoceTitolario(String aooCode, String structurCode, String descrizioneVoceTitolario, 
			String codiceFascicoloDossier, String suffissoCodiceFascicoloDossier, String numeroSottofascicolo) {
		logger.info("ottieniFascicoloDossierInVoceTitolario() start");
		final Output<String> output = new Output<>();
		String risposta = null;
		try {
			if( numeroSottofascicolo!=null && !numeroSottofascicolo.equalsIgnoreCase(""))
				risposta  = getFascicoloDossierInVoceTitolario(aooCode, structurCode, descrizioneVoceTitolario, codiceFascicoloDossier, suffissoCodiceFascicoloDossier, numeroSottofascicolo);
            else 
            	risposta  = getFascicoloDossierInVoceTitolario(aooCode, structurCode, descrizioneVoceTitolario, codiceFascicoloDossier, suffissoCodiceFascicoloDossier);
		} catch (Exception e) {
			logger.error(LOG_ERROR_MESSAGE, e);
			UtilActa.aggiornaEsito(output.getOutcome(), e);
			return output;
		}
		output.getOutcome().setOk(true);
		output.setData(risposta);
		logger.info("ottieniFascicoloDossierInVoceTitolario() end");
		return output;
	}

	private String getFascicoloDossierInVoceTitolario(String aooCode, String structurCode, String codiceVoceTitolario, 
			String codiceFascicoloDossier, String suffissoCodiceFascicoloDossier) {
		
		String idFascicoloDossier = null;
		WSActaRepository wsActaRepository = new WSActaRepository();
		wsActaRepository.setEndpointRepository( clientConfig.getEndpointRepository() );
		wsActaRepository.setTimeout(clientConfig.getTimeoutRepository());
		logger.debug("Cerco il repository con name " + clientConfig.getRepoName());
		String repoId = wsActaRepository.getRepository( clientConfig.getRepoName() );
		
		if( repoId!=null){
			logger.debug("Repository id: " + repoId );
			
			WSActaBackOffice wsActaBackOffice = new WSActaBackOffice();
			wsActaBackOffice.setEndpointBackOffice( clientConfig.getEndpointBackOffice() );
			wsActaBackOffice.setTimeout(clientConfig.getTimeoutBackoffice());
			wsActaBackOffice.setAppKey( clientConfig.getAppKey());
			wsActaBackOffice.setFiscalCodeUtente( clientConfig.getFiscalCodeUtente() );
			
			logger.debug("Cerco il principal con aooCode " + aooCode );// + "  structureCode " + structurCode );
			List<String> principalIdList = wsActaBackOffice.getPrincipalsExt(repoId, aooCode/*, structurCode*/);
			
			
			if( principalIdList!=null && principalIdList.size()==1){
				String principalId = principalIdList.get(0);
				logger.debug("Principal id: " + principalId );
				
				WsActaObject wsActaObject = new WsActaObject();
				wsActaObject.setEndpointObject( clientConfig.getEndpointObject() );
				wsActaObject.setTimeout(clientConfig.getTimeoutObject());
				
				logger.debug("Cerco la voce di titolario con codice " + codiceVoceTitolario);
				VoceTitolarioBean voceTitolario = wsActaObject.getVoceTitolario(repoId, principalId, null, codiceVoceTitolario, clientConfig.getDbKeyTitolario());
				
				if( voceTitolario!=null ){
					String idVoceTitolario = voceTitolario.getIdVoce();
					logger.debug("Id voce di titolario: " + idVoceTitolario );
					
					if( idVoceTitolario!=null ){
						String dbKeyVoce = voceTitolario.getDbKeyVoce();
						logger.debug("dbKey voce di titolario: " + dbKeyVoce );
						
						logger.debug("Cerco il fascicolo/dossier con codice " + codiceFascicoloDossier + " e suffisso codice " + suffissoCodiceFascicoloDossier );
						idFascicoloDossier = wsActaObject.getFascicoloDossier(repoId, principalId, idVoceTitolario, dbKeyVoce, codiceFascicoloDossier, suffissoCodiceFascicoloDossier);
						logger.debug("Id fascicolo/Dossier nella voce di titolario: " + idFascicoloDossier );
					} else {
						logger.debug("voce del titolario non trovata");
					}
				} else {
					logger.debug("voce del titolario non trovata");
				}
				
			} else {
				logger.debug("Principal non invididuato");
			}
		} else {
			logger.debug("Principal non invididuato");
		}
		
		return idFascicoloDossier;
	}
	
	private String getFascicoloDossierInVoceTitolario(String aooCode, String structurCode, String codiceVoceTitolario, 
			String codiceFascicoloDossier, String suffissoCodiceFascicoloDossier, String numeroSottofascicolo) {
		
		String idSottoFascicoloDossier = null;
		WSActaRepository wsActaRepository = new WSActaRepository();
		wsActaRepository.setEndpointRepository( clientConfig.getEndpointRepository() );
		wsActaRepository.setTimeout(clientConfig.getTimeoutRepository());
		logger.debug("Cerco il repository con name " + clientConfig.getRepoName());
		String repoId = wsActaRepository.getRepository( clientConfig.getRepoName() );
		
		if( repoId!=null){
			logger.debug("Repository id: " + repoId );
			
			WSActaBackOffice wsActaBackOffice = new WSActaBackOffice();
			wsActaBackOffice.setEndpointBackOffice( clientConfig.getEndpointBackOffice() );
			wsActaBackOffice.setTimeout(clientConfig.getTimeoutBackoffice());
			wsActaBackOffice.setAppKey( clientConfig.getAppKey());
			wsActaBackOffice.setFiscalCodeUtente( clientConfig.getFiscalCodeUtente() );
			
			logger.debug("Cerco il principal con aooCode " + aooCode );// + "  structureCode " + structurCode );
			List<String> principalIdList = wsActaBackOffice.getPrincipalsExt(repoId, aooCode/*, structurCode*/);
			
			
			if( principalIdList!=null && principalIdList.size()==1){
				String principalId = principalIdList.get(0);
				logger.debug("Principal id: " + principalId );
				
				WsActaObject wsActaObject = new WsActaObject();
				wsActaObject.setEndpointObject( clientConfig.getEndpointObject() );
				wsActaObject.setTimeout(clientConfig.getTimeoutObject());
				
				logger.debug("Cerco la voce di titolario con codice " + codiceVoceTitolario);
				VoceTitolarioBean voceTitolario = wsActaObject.getVoceTitolario(repoId, principalId, null, codiceVoceTitolario, clientConfig.getDbKeyTitolario());
				
				if( voceTitolario!=null ){
					String idVoceTitolario = voceTitolario.getIdVoce();
					logger.debug("Id voce di titolario: " + idVoceTitolario );
					
					if( idVoceTitolario!=null ){
						String dbKeyVoce = voceTitolario.getDbKeyVoce();
						logger.debug("dbKey voce di titolario: " + dbKeyVoce );
						
						logger.debug("Cerco il fascicolo/dossier con codice " + codiceFascicoloDossier + " e suffisso codice " + suffissoCodiceFascicoloDossier );
						String idFascicoloDossier = wsActaObject.getFascicoloDossier(repoId, principalId, idVoceTitolario, dbKeyVoce, codiceFascicoloDossier, suffissoCodiceFascicoloDossier);
						logger.debug("Id fascicolo/Dossier nella voce di titolario: " + idFascicoloDossier );
						
						if(idFascicoloDossier!=null){
							logger.debug("Cerco il sottofascicolo con numero " + numeroSottofascicolo );
							idSottoFascicoloDossier = wsActaObject.getSottofascicolo(repoId, principalId, idFascicoloDossier, dbKeyVoce, numeroSottofascicolo);
							logger.debug("Id fascicolo/Dossier nella voce di titolario: " + idFascicoloDossier );
						} else {
							logger.debug("fescicolo non trovato");
						}
						
					} else {
						logger.debug("voce del titolario non trovata");
					}
				} else {
					logger.debug("voce del titolario non trovata");
				}
				
			} else {
				logger.debug("Principal non invididuato");
			}
		} else {
			logger.debug("Principal non invididuato");
		}
		
		return idSottoFascicoloDossier;
	}
	
	public Output<Boolean> verificaIndiceClassificazioneEstesa(String aooCode, String structurCode, String indiceClassificazioneEstesa) {
		logger.info("verificaIndiceClassificazioneEstesa() start");
		final Output<Boolean> output = new Output<>();
		boolean risposta = false;
		try {
            risposta  = esisteIndiceClassificazioneEstesa(aooCode, structurCode, indiceClassificazioneEstesa);
		} catch (Exception e) {
			logger.error(LOG_ERROR_MESSAGE, e);
			UtilActa.aggiornaEsito(output.getOutcome(), e);
			return output;
		}
		output.getOutcome().setOk(true);
		output.setData(risposta);
		logger.info("verificaIndiceClassificazioneEstesa() end");
		return output;
	}
	
	private boolean esisteIndiceClassificazioneEstesa(String aooCode, String structurCode, String indiceClassificazioneEstesa) {
		
		boolean esisteIndiceClassificazioneEstesa = false;
		WSActaRepository wsActaRepository = new WSActaRepository();
		wsActaRepository.setEndpointRepository( clientConfig.getEndpointRepository() );
		wsActaRepository.setTimeout(clientConfig.getTimeoutRepository());
		logger.debug("Cerco il repository con name " + clientConfig.getRepoName());
		String repoId = wsActaRepository.getRepository( clientConfig.getRepoName() );
		
		if( repoId!=null){
			logger.debug("Repository id: " + repoId );
			
			WSActaBackOffice wsActaBackOffice = new WSActaBackOffice();
			wsActaBackOffice.setEndpointBackOffice( clientConfig.getEndpointBackOffice() );
			wsActaBackOffice.setTimeout(clientConfig.getTimeoutBackoffice());
			wsActaBackOffice.setAppKey( clientConfig.getAppKey());
			wsActaBackOffice.setFiscalCodeUtente( clientConfig.getFiscalCodeUtente() );
			
			logger.debug("Cerco il principal con aooCode " + aooCode );// + "  structureCode " + structurCode );
			List<String> principalIdList = wsActaBackOffice.getPrincipalsExt(repoId, aooCode/*, structurCode*/);
			
			
			if( principalIdList!=null && principalIdList.size()==1){
				String principalId = principalIdList.get(0);
				logger.debug("Principal id: " + principalId );
				
				WsActaObject wsActaObject = new WsActaObject();
				wsActaObject.setEndpointObject( clientConfig.getEndpointObject() );
				wsActaObject.setTimeout(clientConfig.getTimeoutObject());
				
				logger.debug("Cerco la classificazione con indice " + indiceClassificazioneEstesa );
				esisteIndiceClassificazioneEstesa = wsActaObject.verificaIndiceClassificazioneEsteso(repoId, principalId, null, indiceClassificazioneEstesa);
				logger.debug("esisteIndiceClassificazioneEstesa: " + esisteIndiceClassificazioneEstesa );
				
			} else {
				logger.debug("Principal non invididuato");
			}
		} else {
			logger.debug("Principal non invididuato");
		}
		
		return esisteIndiceClassificazioneEstesa;
	}
	
	public Output<List<NodoSmistamento>> ottieniDestinatariSmistamento(String aooCode, String structurCode) {
		logger.info("ottieniDestinatariSmistamento() start");
		final Output<List<NodoSmistamento>> output = new Output<>();
		List<NodoSmistamento> risposta = null;
		try {
            risposta  = getDestinatariSmistamento(aooCode, structurCode);
		} catch (Exception e) {
			logger.error(LOG_ERROR_MESSAGE, e);
			UtilActa.aggiornaEsito(output.getOutcome(), e);
			return output;
		}
		output.getOutcome().setOk(true);
		output.setData(risposta);
		logger.info("ottieniDestinatariSmistamento() end");
		return output;
	}

	
	private List<NodoSmistamento> getDestinatariSmistamento(String aooCode, String structurCode) {
		List<NodoSmistamento> destinatariSmistamento = new ArrayList<NodoSmistamento>();
		
		WSActaRepository wsActaRepository = new WSActaRepository();
		wsActaRepository.setEndpointRepository( clientConfig.getEndpointRepository() );
		wsActaRepository.setTimeout(clientConfig.getTimeoutRepository());
		logger.debug("Cerco il repository con name " + clientConfig.getRepoName());
		String repoId = wsActaRepository.getRepository( clientConfig.getRepoName() );
		
		if( repoId!=null){
			logger.debug("Repository id: " + repoId );
			
			WSActaBackOffice wsActaBackOffice = new WSActaBackOffice();
			wsActaBackOffice.setEndpointBackOffice( clientConfig.getEndpointBackOffice() );
			wsActaBackOffice.setTimeout(clientConfig.getTimeoutBackoffice());
			wsActaBackOffice.setAppKey( clientConfig.getAppKey());
			wsActaBackOffice.setFiscalCodeUtente( clientConfig.getFiscalCodeUtente() );
			
			logger.debug("Cerco il principal con aooCode " + aooCode );// + "  structureCode " + structurCode );
			List<String> principalIdList = wsActaBackOffice.getPrincipalsExt(repoId, aooCode/*, structurCode*/);
			
			if( principalIdList!=null && principalIdList.size()==1){
				String principalId = principalIdList.get(0);
				logger.debug("Principal id: " + principalId );
				
				logger.debug("Cerco i nodi destinatari di smistamento per la struttura " + structurCode );
				destinatariSmistamento = wsActaBackOffice.getNodiDestinatarioSmistamento(repoId, principalId, null, structurCode);
				
				
			} else {
				logger.debug("Principal non individuato");
			}
		} else {
			logger.debug("Repository non individuato");
		}
		
		return destinatariSmistamento;
	}
	
	public void setClientConfig(ActaClientConfig clientConfig) {
		this.clientConfig = clientConfig;
	}

	private Properties retrieveProperties(String fileName) throws IOException {
		// try (InputStream input = new FileInputStream("application.properties")) {
		try (InputStream input = getClass().getClassLoader().getResourceAsStream(fileName)) {
			if (input == null) {
				throw new FileNotFoundException("File di configurazione '" + fileName + "' non recuperato.");
			}
			Properties properties = new Properties();
			properties.load(input);
			return properties;
		}
	}
	
}
