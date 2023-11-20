/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreAdddocBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreUpddocudBean;
import it.eng.auriga.database.store.dmpk_fatture.bean.DmpkFattureGetidudtokenaddfatturaBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.beans.SpecializzazioneBean;
import it.eng.auriga.module.business.dao.beans.NotificaSdIBean;
import it.eng.auriga.ui.module.layout.shared.bean.SchemaSelector;
import it.eng.auriga.ui.module.layout.shared.util.SharedConstants;
import it.eng.client.AurigaService;
import it.eng.client.DmpkCoreAdddoc;
import it.eng.client.DmpkCoreUpddocud;
import it.eng.client.DmpkFattureGetidudtokenaddfattura;
import it.eng.client.GestioneDocumenti;
import it.eng.config.AurigaBusinessClientConfig;
import it.eng.core.business.TFilterFetch;
import it.eng.core.service.client.FactoryBusiness.BusinessType;
import it.eng.core.service.client.config.Configuration;
import it.eng.document.function.bean.CreaModDocumentoInBean;
import it.eng.document.function.bean.DocumentoCollegato;
import it.eng.document.function.bean.FileInfoBean;
import it.eng.document.function.bean.GenericFile;
import it.eng.document.function.bean.RebuildedFile;
import it.eng.document.function.bean.TipoFile;
import it.eng.document.function.bean.VersionaDocumentoInBean;
import it.eng.document.function.bean.VersionaDocumentoOutBean;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.DocumentConfiguration;
import it.eng.utility.FileUtil;
import it.eng.utility.jobmanager.quartz.AbstractJob;
import it.eng.utility.jobmanager.quartz.Job;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.storageutil.StorageService;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.xml.XmlUtilitySerializer;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

@Job(type="NotificheSdIJob")
public class NotificheSdIJob extends AbstractJob<String> {

	private Logger mLogger = Logger.getLogger(NotificheSdIJob.class);
	
	private static Locale locale = new Locale("it", "IT");
	
	@Override
	public List<String> load() {						
		
		List<String> ret = new ArrayList<String>();	
		SchemaSelector result = (SchemaSelector) SpringAppContext.getContext().getBean("SchemaConfigurator");
		if(result != null && result.getSchemi() != null) {
		for (int i = 0; i < result.getSchemi().size(); i++)
			ret.add(result.getSchemi().get(i).getName());
		}
		return ret;
	}

	@Override
	public void execute(String schema) {
		
		mLogger.info("START JOB NOTIFICHE SDI (SCHEMA: " + schema + ")");
		
		AurigaLoginBean loginBean = new AurigaLoginBean();
		// Inserisco la lingua di default
		loginBean.setLinguaApplicazione(SharedConstants.DEFAUL_LANGUAGE);
		loginBean.setSchema(schema);
		
		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration)SpringAppContext.getContext().getBean("DocumentConfiguration");
		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();						
		
		try {
			
			TFilterFetch<NotificaSdIBean> filter = new TFilterFetch<NotificaSdIBean>();
			NotificaSdIBean filterBean = new NotificaSdIBean();
			filter.setFilter(filterBean);
			
			List<NotificaSdIBean> listaNotifiche = AurigaService.getDaoTNotificheSdI().search(locale, loginBean, filter).getData();
			
			for(NotificaSdIBean bean : listaNotifiche) {
				
				if(bean.getTsCarAuriga() == null) {
					
					File tempFile = null;
				
					try {						
						
						mLogger.debug(">>> ELABORAZIONE NOTIFICA CON ID_MSG_NOTIFICA = " + bean.getIdMsgNotifica());
						
						mLogger.debug("Tipo notifica: " + bean.getTipoNotifica());
						mLogger.debug("Nome del file trasmesso: " + bean.getNomeFileTrasmesso());
												
						int index = bean.getNomeFileTrasmesso().indexOf("_") + 1;
						if(index == 0) {
							throw new Exception("Impossibile recuperare il progressivo di trasm. della notifica");	
						}
						
						String progrTrasm = bean.getNomeFileTrasmesso().substring(index, index + 5);
						mLogger.debug("Progr. trasm. notifica: " + progrTrasm);
						
						mLogger.debug(">>> Recupero l'identificativo della fattura, il dominio e il token");
						
						DmpkFattureGetidudtokenaddfatturaBean lGetidudtokenaddfatturaInput = new DmpkFattureGetidudtokenaddfatturaBean();
						lGetidudtokenaddfatturaInput.setProgrtrasmsdiin(progrTrasm);
						
						DmpkFattureGetidudtokenaddfattura lGetidudtokenaddfattura = new DmpkFattureGetidudtokenaddfattura();
						StoreResultBean<DmpkFattureGetidudtokenaddfatturaBean> lGetidudtokenaddfatturaOutput = lGetidudtokenaddfattura.execute(locale, loginBean, lGetidudtokenaddfatturaInput);
						
						if(StringUtils.isNotBlank(lGetidudtokenaddfatturaOutput.getDefaultMessage())) {
							String message = "DMPK_FATTURE.GetIdUdTokenAddFattura: " + lGetidudtokenaddfatturaOutput.getDefaultMessage();
							if(lGetidudtokenaddfatturaOutput.isInError()) {
								throw new Exception(message);	
							} else {
								mLogger.warn(message);								
							}
						}
						
						BigDecimal idUdFattura = lGetidudtokenaddfatturaOutput.getResultBean().getIdudfatturaout();
						mLogger.debug("idUd fattura: " + idUdFattura);
						
						SpecializzazioneBean spec = new SpecializzazioneBean();
						spec.setIdDominio(lGetidudtokenaddfatturaOutput.getResultBean().getIddominioout());
						loginBean.setSpecializzazioneBean(spec);
						mLogger.debug("idDominio: " + spec.getIdDominio());	
												
						loginBean.setToken(lGetidudtokenaddfatturaOutput.getResultBean().getCodidconnectiontokenout());
						mLogger.debug("token: " + loginBean.getToken());	
						
						tempFile = File.createTempFile("notificaSdI", ".xml");
						FileUtils.writeStringToFile(tempFile, bean.getMessaggio());			
						StorageService storageService = StorageImplementation.getStorage();	
						String uri = storageService.store(tempFile);						
						
						mLogger.debug("uri: " + uri);
											
						Boolean esitoNotificaEsito = ("NotificaEsito".equals(bean.getTipoNotifica())) ? getEsitoNotificaEsito(tempFile) : null;											    
						
						if(bean.getIdDoc() != null) {
							
							mLogger.debug(">>> Il documento relativo alla notifica esiste già");												
									
						} else {
						
							mLogger.debug(">>> Creo il documento relativo alla notifica e lo collego alla fattura");					
							
							DmpkCoreAdddocBean lAdddocInput = new DmpkCoreAdddocBean();
							lAdddocInput.setCodidconnectiontokenin(loginBean.getToken());
							lAdddocInput.setIduserlavoroin(null);					
							
							CreaModDocumentoInBean attributiDocNotifica = new CreaModDocumentoInBean();
							attributiDocNotifica.setOggetto(getMappaturaOggettoFromtTipoNotifica(esitoNotificaEsito).get(bean.getTipoNotifica()));
							attributiDocNotifica.setNomeDocType(getMappaturaNomeDocTypeFromtTipoNotifica().get(bean.getTipoNotifica()));
							attributiDocNotifica.setDataArrivo(bean.getTsIns());
							List<DocumentoCollegato> documentiCollegati = new ArrayList<DocumentoCollegato>();
							
							DocumentoCollegato docCollegato = new DocumentoCollegato();
							docCollegato.setIdUd(String.valueOf(idUdFattura));
							docCollegato.setcValue("CAU");
							docCollegato.setScValue(getMappaturaScValueFromtTipoNotifica().get(bean.getTipoNotifica()));
							docCollegato.setPrcValue(null);
							documentiCollegati.add(docCollegato);
							attributiDocNotifica.setDocCollegato(documentiCollegati);					
							
							lAdddocInput.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(attributiDocNotifica));						
							
							DmpkCoreAdddoc lAdddoc = new DmpkCoreAdddoc();
							StoreResultBean<DmpkCoreAdddocBean> lAdddocOutput = lAdddoc.execute(locale, loginBean, lAdddocInput);																				
							
							if(StringUtils.isNotBlank(lAdddocOutput.getDefaultMessage())) {
								String message = "DMPK_CORE.AddDoc: " + lAdddocOutput.getDefaultMessage();
								if(lAdddocOutput.isInError()) {
									throw new Exception(message);	
								} else {
									mLogger.warn(message);								
								}						
							}
							
							bean.setIdDoc(lAdddocOutput.getResultBean().getIddocout());
							
							AurigaService.getDaoTNotificheSdI().update(locale, loginBean, bean);
						
						}
						
						mLogger.debug("idDoc notifica: " + bean.getIdDoc());						
												
						if(bean.isFlgCarVerAuriga()) {
							
							mLogger.debug(">>> Il documento relativo alla notifica risulta già versionato");	
							
						} else {
						
							mLogger.debug(">>> Versiono il documento relativo alla notifica con il file presente nel CLOB");														
														
							RebuildedFile lRebuildedFile = new RebuildedFile();
							lRebuildedFile.setIdDocumento(bean.getIdDoc());
							lRebuildedFile.setFile(storageService.extractFile(uri));
							
							MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
							InfoFileUtility lFileUtility = new InfoFileUtility();
							lMimeTypeFirmaBean = lFileUtility.getInfoFromFile(lRebuildedFile.getFile().toURI().toString(), lRebuildedFile.getFile().getName(), false, null);
							lMimeTypeFirmaBean.setFirmato(true);
							lMimeTypeFirmaBean.setFirmaValida(true);
							lMimeTypeFirmaBean.setConvertibile(false);
							lMimeTypeFirmaBean.setDaScansione(false);						
							
							FileInfoBean lFileInfoBean = new FileInfoBean();
							lFileInfoBean.setTipo(TipoFile.PRIMARIO);					
							GenericFile lGenericFile = new GenericFile();				
							lGenericFile.setMimetype(lMimeTypeFirmaBean.getMimetype());
							lGenericFile.setDisplayFilename(bean.getNomeFileNotifica());
							lGenericFile.setImpronta(lMimeTypeFirmaBean.getImpronta());
							lGenericFile.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
							lGenericFile.setEncoding(lDocumentConfiguration.getEncoding().value());				
							lFileInfoBean.setAllegatoRiferimento(lGenericFile);				
							
							lRebuildedFile.setInfo(lFileInfoBean);
							
							VersionaDocumentoInBean lVersionaDocumentoInBean = new VersionaDocumentoInBean();
							BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lVersionaDocumentoInBean, lRebuildedFile); 
							
							GestioneDocumenti lGestioneDocumenti = new GestioneDocumenti();
							VersionaDocumentoOutBean lVersionaDocumentoOutput = lGestioneDocumenti.versionadocumento(locale, loginBean, lVersionaDocumentoInBean);
							
							if (lVersionaDocumentoOutput.getDefaultMessage() != null){
								mLogger.error("VersionaDocumento: " + lVersionaDocumentoOutput.getDefaultMessage());
								throw new StoreException(lVersionaDocumentoOutput);
							}
							
							bean.setFlgCarVerAuriga(true);
							
							AurigaService.getDaoTNotificheSdI().update(locale, loginBean, bean);
							
						} 
						
						mLogger.debug(">>> Aggiorno lo stato della fattura");
						
						DmpkCoreUpddocudBean lUpddocudInput = new DmpkCoreUpddocudBean();
						lUpddocudInput.setCodidconnectiontokenin(loginBean.getToken());
						lUpddocudInput.setIduserlavoroin(null);					
						lUpddocudInput.setIduddocin(idUdFattura);
						lUpddocudInput.setFlgtipotargetin("U");					
						
						CreaModDocumentoInBean attributiUdFattura = new CreaModDocumentoInBean();
						attributiUdFattura.setCodStatoDett(getMappaturaCodStatoDettFromtTipoNotifica(esitoNotificaEsito).get(bean.getTipoNotifica()));				
						lUpddocudInput.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(attributiUdFattura));
						
						DmpkCoreUpddocud lUpddocud = new DmpkCoreUpddocud();
						StoreResultBean<DmpkCoreUpddocudBean> lUpddocudOutput = lUpddocud.execute(locale, loginBean, lUpddocudInput);
						
						if(StringUtils.isNotBlank(lUpddocudOutput.getDefaultMessage())) {						
							String message = "DMPK_CORE.UpdDocUd: " + lUpddocudOutput.getDefaultMessage();
							if(lUpddocudOutput.isInError()) {
								throw new Exception(message);	
							} else {
								mLogger.warn(message);								
							}							
						}
						
						bean.setTsCarAuriga(new Date());
						bean.setTsErrCarAuriga(null);
						bean.setErrMsgCarAuriga(null);
						
						AurigaService.getDaoTNotificheSdI().update(locale, loginBean, bean);
												
					} catch (Throwable e) {
						
						mLogger.error(e.getMessage(), e);
						
						bean.setTsErrCarAuriga(new Date());
						bean.setErrMsgCarAuriga(e.getMessage());
						
						AurigaService.getDaoTNotificheSdI().update(locale, loginBean, bean);
						
					} finally {
						
						if(tempFile != null && tempFile.exists()) {
							FileUtil.deleteFile(tempFile);
						}
						
					}
				
				}
				
			}
			
		} catch (Throwable e) {
			
			mLogger.fatal(e.getMessage(), e);	
			
		}		
			
		mLogger.info("END JOB NOTIFICHE SDI");
					
	}
	
	private static Boolean getEsitoNotificaEsito(File fileXml) throws Exception {
		Boolean esito = null;
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
	    Document doc = docBuilder.parse (fileXml);
	    XPathFactory xPathfactory = XPathFactory.newInstance();
	    XPath xpath = xPathfactory.newXPath();
	    XPathExpression expr = xpath.compile("/NotificaEsito/EsitoCommittente/Esito");
	    Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);
	    if(node != null) {					    
		    if("EC01".equals(node.getTextContent())) {
				esito = true;
			} else if("EC02".equals(node.getTextContent())) {
				esito = false;
			}
	    }
	    return esito;
	}
	
	private HashMap<String, String> getMappaturaOggettoFromtTipoNotifica(Boolean esitoNotificaEsito) {		
		HashMap<String, String> lHashMap = new HashMap<String, String>();
		lHashMap.put("RicevutaConsegna", "Ricevuta di consegna SdI");
		lHashMap.put("NotificaMancataConsegna", "Notifica di mancata consegna SdI");
		lHashMap.put("NotificaScarto", "Notifica di scarto SdI");
		if(esitoNotificaEsito != null) {
			lHashMap.put("NotificaEsito", "Notifica esito SdI (" + (esitoNotificaEsito ? "positivo" : "negativo") + ")");
		} else {
			lHashMap.put("NotificaEsito", "Notifica esito SdI");
		}
		lHashMap.put("NotificaFileNonRecapitabile", "Notifica di file non recapitabile SdI");
		lHashMap.put("AttestazioneTrasmissioneFattura", "Notifica di file non recapitabile SdI");		
		lHashMap.put("NotificaDecorrenzaTermini", "Notifica di decorrenza termini SdI");		
		return lHashMap;
	}
	
	private HashMap<String, String> getMappaturaNomeDocTypeFromtTipoNotifica() {		
		HashMap<String, String> lHashMap = new HashMap<String, String>();
		lHashMap.put("RicevutaConsegna", "ricevuta di consegna SdI");
		lHashMap.put("NotificaMancataConsegna", "notifica di mancata consegna SdI");
		lHashMap.put("NotificaScarto", "notifica di scarto SdI");
		lHashMap.put("NotificaEsito", "notifica esito SdI");
		lHashMap.put("NotificaFileNonRecapitabile", "notifica di file non recapitabile SdI");
		lHashMap.put("AttestazioneTrasmissioneFattura", "notifica di file non recapitabile SdI");		
		lHashMap.put("NotificaDecorrenzaTermini", "notifica di decorrenza termini SdI");		
		return lHashMap;
	}
	
	private HashMap<String, String> getMappaturaScValueFromtTipoNotifica() {
		HashMap<String, String> lHashMap = new HashMap<String, String>();
		lHashMap.put("RicevutaConsegna", "SDI_RC");
		lHashMap.put("NotificaMancataConsegna", "SDI_NMC");
		lHashMap.put("NotificaScarto", "SDI_NST");
		lHashMap.put("NotificaEsito", "SDI_NE");
		lHashMap.put("NotificaFileNonRecapitabile", "SDI_NFNR");
		lHashMap.put("AttestazioneTrasmissioneFattura", "SDI_NFNR");		
		lHashMap.put("NotificaDecorrenzaTermini", "SDI_NDT");		
		return lHashMap;
	}
	
	private HashMap<String, String> getMappaturaCodStatoDettFromtTipoNotifica(Boolean esitoNotificaEsito) {	
		HashMap<String, String> lHashMap = new HashMap<String, String>();
		lHashMap.put("RicevutaConsegna", "CONS");
		lHashMap.put("NotificaMancataConsegna", "ERR_CONS");
		lHashMap.put("NotificaScarto", "RIF_SdI");
		if(esitoNotificaEsito != null) {
			lHashMap.put("NotificaEsito", esitoNotificaEsito ? "ES_POS" : "ES_NEG");
		}
		lHashMap.put("NotificaFileNonRecapitabile", "ERR_CONS");
		lHashMap.put("AttestazioneTrasmissioneFattura", "ERR_CONS");		
		lHashMap.put("NotificaDecorrenzaTermini", "DEC_TERM");		
		return lHashMap;
	}	
	
	public static void main(String[] args) throws Exception {
		locale = new Locale ("it");
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("config.xml");				
		SpringAppContext.setContext(context);
		Configuration.getInstance().setUrl("http://localhost:8080/AurigaBusiness");
		Configuration.getInstance().setBusinesstype(BusinessType.REST);
		Configuration.getInstance().initClient();
		AurigaBusinessClientConfig.getInstance().setUrl("http://localhost:8080/AurigaBusiness");	
//		StorageImplementation.init();
		NotificheSdIJob job = new NotificheSdIJob();
		job.execute("OWNER_1");
	}
	
	@Override
	protected void end(String obj) {
		
		
	}

}
