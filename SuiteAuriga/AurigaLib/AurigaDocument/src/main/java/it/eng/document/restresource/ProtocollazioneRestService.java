/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.xml.sax.SAXException;

import com.google.gson.Gson;
import com.sun.jersey.spi.resource.Singleton;

import io.swagger.annotations.Api;
import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_bmanager.bean.DmpkBmanagerInsbatchBean;
import it.eng.auriga.database.store.dmpk_bmanager.store.impl.InsbatchImpl;
import it.eng.auriga.database.store.dmpk_registrazionedoc.bean.DmpkRegistrazionedocSavefileasclobBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.dao.DaoBmtJobParameters;
import it.eng.auriga.module.business.dao.DaoTRichiestaProtocollazione;
import it.eng.auriga.module.business.dao.beans.JobParameterBean;
import it.eng.auriga.module.business.dao.beans.TRichiestaProtocollazioneBean;
import it.eng.client.DmpkBmanagerInsbatch;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.document.configuration.OperazioniAurigaProtocollazioneAsyncConfigBean;
import it.eng.document.function.bean.JobParameterXmlStoreBean;
import it.eng.document.function.bean.UtenzaApplicazioneEsternaBean;
import it.eng.document.function.bean.restrepresentation.ValidazioneProtocolloBean;
import it.eng.document.function.bean.restrepresentation.input.ProtocollazioneAsyncRequest;
import it.eng.document.function.bean.restrepresentation.output.ProtocollazioneAsyncResponse;
import it.eng.document.storage.DocumentStorage;
import it.eng.spring.utility.SpringAppContext;
import it.eng.storeutil.AnalyzeResult;

/**
 * @author Antonio Peluso
 * 
 * Servizio rest protocollazione asincrona, attualmente utilizzato da
 * 	SUE, e per i servizi di variazione domicilio digitale
 * 
 */

@Singleton
@Api
@Path("/protocollazione")
public class ProtocollazioneRestService {
	
	@Context
	ServletContext context;

	@Context
	HttpServletRequest requestContext;
	
	private static final Logger log = Logger.getLogger(ProtocollazioneRestService.class);
	OperazioniAurigaProtocollazioneAsyncConfigBean protocollazioneAsyncConfigBean = new OperazioniAurigaProtocollazioneAsyncConfigBean();
	
	private SchemaBean schemaBean;
	
	private static final String STATO_SCARTATA = "scartata";
	private static final String STATO_INSERITA = "inserita";	
	
	private static final String OPERAZIONE_INSERT = "INSERT";	
	private static final String OPERAZIONE_UPDATE = "UPDATE";
	
	private static final String PATH_XSD_ADDUD = "/includes/xsd/NewUD.xsd";	
	private static final String PATH_XSD_UPDUD = "/includes/xsd/UDDaAgg.xsd";		
	
	private static final String TIPO_JOB_ADD = "OP_AURIGA_PROTOCOLLAZIONI_ASINCRONE";	
	private static final String TIPO_JOB_UPDATE = "OP_AURIGA_AGGIORNAMENTO_ASINCRONE";	
	
	private String idRichiesta = "";

	@POST
	@Path("/eseguiProtocollazione")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ProtocollazioneAsyncResponse eseguiProtocollazione(ProtocollazioneAsyncRequest request)
			throws Exception {
		log.info("**START** -  service Protocollazione Asincrona - idReciver: " + requestContext.getHeader("X-Correlation-ID"));
		
		ProtocollazioneAsyncResponse response = new ProtocollazioneAsyncResponse();

		TRichiestaProtocollazioneBean savedRequestBean;

		configParamBean();
		
		UtenzaApplicazioneEsternaBean utenzaApplicazione = new UtenzaApplicazioneEsternaBean();

		if (request != null && StringUtils.isNotBlank(request.getCodApplicazione())) {				
			
			utenzaApplicazione.setCodApplicazione(request.getCodApplicazione());
			utenzaApplicazione.setIstanzaApplicazione(request.getIstanzaApplicazione());
			utenzaApplicazione.setUserName(request.getUserName());
			utenzaApplicazione.setPassword(request.getPassword());
			
			/**
			 * PROTOCOLLAZIONE SUE ATTUALMENTE DI DEFAULT SE NON VIENE PASSATO IL CODAPPLICAZIONE
			 * */		
			
		}else {
			
			if("true".equalsIgnoreCase(protocollazioneAsyncConfigBean.getFlgAttivaDefaultProtSUE())) {
			
				utenzaApplicazione.setCodApplicazione(protocollazioneAsyncConfigBean.getCodApplicazioneSUE());
				utenzaApplicazione.setIstanzaApplicazione(protocollazioneAsyncConfigBean.getIstApplicazioneSUE());
				utenzaApplicazione.setUserName(protocollazioneAsyncConfigBean.getUsernameSUE());
				utenzaApplicazione.setPassword(protocollazioneAsyncConfigBean.getPasswordSUE());	
			}
			else {
				log.error("Richiesta nulla o codice applicazione non presente!");
				
				response.setCode("400");
				response.setMessage("Richiesta nulla o codice applicazione non presente");
				response.setResult(false);
				
				return response;
			}
		}
		
		if((requestContext.getHeader("X-Correlation-ID")!=null && !"".equalsIgnoreCase(requestContext.getHeader("X-Correlation-ID")))) {
			request.setIdReceiver(requestContext.getHeader("X-Correlation-ID"));
			//Verifico se l'idReciver è stato inserito giò
			if(checkIdReciver(request.getIdReceiver())) {
				response.setResult(true);
				response.setCode("200");
				response.setMessage("Richiesta inserita correttamente");
				
				return response;
			}
		}
		
		/**     DI DEFAULT SE NON HO IL TIPO DI OPERAZIONE IN INPUT PROCEDO CON L'AGGIUNTA DEL PROTOCOLLO      **/
		if(StringUtils.isBlank(request.getOperazione())) {
			request.setOperazione(OPERAZIONE_INSERT);
		}
				
		savedRequestBean = insertRichiestaProtocollazione(request, utenzaApplicazione.getCodApplicazione());
		
		response = protocollazioneAsincrona(request, savedRequestBean, utenzaApplicazione);
		
		log.info("**END** -  service Protocollazione Asincrona - idReciver: " + requestContext.getHeader("X-Correlation-ID"));

		return response;
	}

	private boolean checkIdReciver(String idReceiver) throws Exception {
		JobParameterBean jobParameterBean = new JobParameterBean();
		jobParameterBean = new JobParameterBean();
		jobParameterBean.setParameterType("VARCHAR2");
		jobParameterBean.setParameterDir("IN");
		jobParameterBean.setParameterSubtype("ID_RECEIVER");
		jobParameterBean.setParameterValue(idReceiver);
		
		TFilterFetch<JobParameterBean> filter = new TFilterFetch<JobParameterBean>();
		filter.setFilter(jobParameterBean);

		DaoBmtJobParameters daoBmtJobParameters = new DaoBmtJobParameters();
		TPagingList<JobParameterBean> params = daoBmtJobParameters.search(filter);
		
		if(params.getData()!=null && !params.getData().isEmpty()) {
			return true;
		}		
		
		return  false;
	}

	private ProtocollazioneAsyncResponse protocollazioneAsincrona(ProtocollazioneAsyncRequest request,
			TRichiestaProtocollazioneBean savedRequestBean, UtenzaApplicazioneEsternaBean utenzaApplicazione) throws Exception {

		ProtocollazioneAsyncResponse response = new ProtocollazioneAsyncResponse();
		response.setMessage("Messaggio vuoto");

		if (request != null) {
			
			//controllo se l'idReciver è passato nell header, se si, lo setto all input
//			if((requestContext.getHeader("X-Correlation-ID")!=null && !"".equalsIgnoreCase(requestContext.getHeader("X-Correlation-ID")))) {
//				request.setIdReceiver(requestContext.getHeader("X-Correlation-ID"));
//			}
			
			// check validazione request per SUE (che vuole in input almeno un file obbligatorio)
			if (utenzaApplicazione.getCodApplicazione().equalsIgnoreCase(protocollazioneAsyncConfigBean.getCodApplicazioneSUE())) {
				response = checkRequestSUE(request);
			}
			// check validazione request (puo non esserci file)
			else /*if (utenzaApplicazione.getCodApplicazione().equalsIgnoreCase(protocollazioneAsyncConfigBean.getCodApplicazioneServDigitale())) */{
				response = checkRequest(request);
			}

			if (response.isResult()) {

				// check validazione file (se ho file e hash in ingresso controllo se sono
				// coerenti con quello che c'è sull ftp senno procedo)
				if (StringUtils.isNotBlank(request.getFileName()) && StringUtils.isNotBlank(request.getHashFile())) {
					response = checkFile(request.getFileName(), request.getHashFile());
				} else {
					response.setResult(true);
				}

				if (response.isResult()) {
					// check validazione xml request conforme al xsd e se contiene file primario
					// nella request lo deve avere
					// anche nell xml di protocollazione
					response = checkXmlRequest(request.getMetadatiProtocollo(), request.getFileName(), request.getOperazione());

					if (response.isResult()) {
						// Controllo per i servizi di domicilio digitale se c'è nell xml di
						// protocollazione, il tag inerente all ' operazione di domicilio
						if (utenzaApplicazione.getCodApplicazione().equalsIgnoreCase(protocollazioneAsyncConfigBean.getCodApplicazioneServDigitale())) {
							response = checkDomicilioInXml(request.getMetadatiProtocollo());
						} else {
							response.setResult(true);
						}

						if (response.isResult()) {
							// inserimento su DB parametri job protocollazione
							response = insertBMTJob(request.getMetadatiProtocollo(), request.getFileName(),
									request.getHashFile(), request.getIdReceiver(), utenzaApplicazione, savedRequestBean, request.getOperazione());

							if (response.isResult()) {
								return response;
							}
						}
					}
				}
			}
		}

		// Non ho superato controlli, tracciatura errori su DB
		updateRichiestaProtocollazione(savedRequestBean, STATO_SCARTATA, response.getMessage());

		return response;
	}

	
//	private ProtocollazioneSueAsyncResponse protocollazioneConVariazioneDomicilioDigitale(ProtocollazioneSueAsyncRequest request, TRichiestaProtocollazioneBean savedRequestBean, String codApplicazione, String istanzaApplicazione) throws Exception {
//		ProtocollazioneSueAsyncResponse response = new ProtocollazioneSueAsyncResponse();
//
//		if (request != null) {
//			// check validazione request
//			response = checkRequest(request);
//
//			if (response.isResult()) {
//				
//				/**
//				 * 
//				 * ATTUALMENTE PER LA VARIAZIONE DI DOMICILIO NON SONO PREVISTI FILE, DA IMPLEMENTARE SUCCESSIVAMENTE
//				 *  IL CHECK SUI FILE QUANDO VERRANNO PASSATI IN INPUT
//				 * 
//				 * */
//				// check validazione file
////				response = checkFile(request.getFileName(), request.getHashFile());
//
////				if (response.isResult()) {
//
//					// check validazione xml request conforme al xsd
//					response = checkXmlRequestConVariazioneDomicilio(request.getMetadatiProtocollo());
//
//					if (response.isResult()) {
//
//						// inserimento su DB parametri job protocollazione
//						response = insertBMTJob(request.getMetadatiProtocollo(), request.getFileName(),
//								request.getHashFile(), request.getIdReceiver(), codApplicazione, istanzaApplicazione);
//
//						if (response.isResult()) {
//							return response;
//						}
//					}
////				}
//			}
//		}
//
//		// Non ho superato controlli, tracciatura errori su DB
//		updateRichiestaProtocollazione(savedRequestBean, response.getMessage());
//
//		return response;
//	}
//
//
//	/**
//	 * @param request
//	 * @param savedRequestBean
//	 * @param istanzaApplicazione 
//	 * @param codApplicazione 
//	 * @return
//	 * @throws Exception
//	 */
//	public ProtocollazioneSueAsyncResponse protocollazioneSUE(ProtocollazioneSueAsyncRequest request, TRichiestaProtocollazioneBean savedRequestBean, String codApplicazione, String istanzaApplicazione) throws Exception {
//		ProtocollazioneSueAsyncResponse response = new ProtocollazioneSueAsyncResponse();
//		
//		if (request != null) {
//			// check validazione request
//			response = checkRequest(request);
//
//			if (response.isResult()) {
//				// check validazione file
//				response = checkFile(request.getFileName(), request.getHashFile());
//
//				if (response.isResult()) {
//
//					// check validazione xml request conforme al xsd
//					response = checkXmlRequestSUE(request.getMetadatiProtocollo());
//
//					if (response.isResult()) {
//
//						// inserimento su DB parametri job protocollazione
//						response = insertBMTJob(request.getMetadatiProtocollo(), request.getFileName(),
//								request.getHashFile(), request.getIdReceiver(), codApplicazione, istanzaApplicazione);
//
//						if (response.isResult()) {
//							return response;
//						}
//					}
//				}
//			}
//		}
//
//		// Non ho superato controlli, tracciatura errori su DB
//		updateRichiestaProtocollazione(savedRequestBean, response.getMessage());
//
//		return response;
//	}	
	
	
	private boolean checkFilePrimarioNelXml(String xml) {
		Document responseXML = Jsoup.parse(xml, "", Parser.xmlParser());
		if(StringUtils.isNotBlank(responseXML.select("VersioneElettronica").text())) {
			return true;
		}else {
			return false;
		}
	}
	
	private ProtocollazioneAsyncResponse checkDomicilioInXml(String xml) {
		ProtocollazioneAsyncResponse result = new ProtocollazioneAsyncResponse();

		Document responseXML = Jsoup.parse(xml, "", Parser.xmlParser());
		String operazioneDomicilio = responseXML.select("Decodifica_Nome").text();
		if (StringUtils.isNotBlank(operazioneDomicilio)) {
			if (operazioneDomicilio.equalsIgnoreCase("dichiarazione domicilio digitale")
					|| operazioneDomicilio.equalsIgnoreCase("variazione domicilio digitale")
					|| operazioneDomicilio.equalsIgnoreCase("cancellazione domicilio digitale")) {

				result.setResult(true);
				return result;
			}
		}
		log.error("Indicazione del tipo di operazione è dichiarazione, variazione o cancellazione è assente o non corretta");

		result.setMessage("Indicazione del tipo di operazione è dichiarazione, variazione o cancellazione è assente o non corretta");
		result.setCode("400");
		result.setResult(false);

		return result;
	}

	private ProtocollazioneAsyncResponse checkRequestSUE(ProtocollazioneAsyncRequest request) {
		ProtocollazioneAsyncResponse result = new ProtocollazioneAsyncResponse();
		
		String filename = request.getFileName();
		String hashFile = request.getHashFile();
		String idReceiver = request.getIdReceiver();
		String metadatiProtocollo = request.getMetadatiProtocollo();
		
		log.debug("Parametri in input - filename: " + filename + " - hashfile: " + hashFile + " - metadatiProtocollo: " + metadatiProtocollo);
		
		// verifica filename valorizzato
		if (filename != null && !filename.equalsIgnoreCase("")) {
			// verifica hashfile valorizzato
			if (hashFile != null && !hashFile.equalsIgnoreCase("")) {
				// verifica idReceiver valorizzato
				if (idReceiver != null && !idReceiver.equalsIgnoreCase("")) {
					// verifica metadatiProtocollo valorizzato
					if (metadatiProtocollo != null && !metadatiProtocollo.equalsIgnoreCase("")) {
						result.setResult(true);
					}
					else {
						// errore metadatiProtocollo null
						result.setResult(false);
						result.setMessage("XML di richiesta di protocollazione assente");
						result.setCode("400");
					}
				}
				else {
					// errore IDreceiver null
					result.setResult(false);
					result.setMessage("IDreceiver assente");
					result.setCode("400");
				}
			}
			else {
				// errore hashfile null
				result.setResult(false);
				result.setMessage("Impronta file assente");
				result.setCode("400");
			}
		}
		else {
			// errore filename null
			result.setResult(false);
			result.setMessage("Nome file assente");
			result.setCode("400");
		}
		
		return result;
	}
	
	private ProtocollazioneAsyncResponse checkRequest(ProtocollazioneAsyncRequest request) {
		ProtocollazioneAsyncResponse result = new ProtocollazioneAsyncResponse();

		String filename = request.getFileName();
		String hashFile = request.getHashFile();
		String idReceiver = request.getIdReceiver();
		String metadatiProtocollo = request.getMetadatiProtocollo();

		log.debug("Parametri in input - filename: " + filename + " - hashfile: " + hashFile + " - metadatiProtocollo: "
				+ metadatiProtocollo);
		
		if (idReceiver != null && !idReceiver.equalsIgnoreCase("")) {
			// verifica metadatiProtocollo valorizzato
			if (metadatiProtocollo != null && !metadatiProtocollo.equalsIgnoreCase("")) {
				result.setResult(true);
			}
			else {
				// errore metadatiProtocollo null
				result.setResult(false);
				result.setMessage("XML di richiesta di protocollazione assente");
				result.setCode("400");
			}
		}
		else {
			// errore IDreceiver null
			result.setResult(false);
			result.setMessage("IDreceiver assente");
			result.setCode("400");
		}

		return result;
	}
	
	private ProtocollazioneAsyncResponse checkFile(String filename, String hashFile) {
		ProtocollazioneAsyncResponse result = new ProtocollazioneAsyncResponse();
		
		log.debug("Parametri file: " + filename + " - hash: " + hashFile);
		
		try {
			/*/auriga_ws_asincroni/impresainungiorno/   ftp milano*/
			File fileProtocollo = new File(protocollazioneAsyncConfigBean.getPathFtp() + filename);
//			File fileProtocollo = new File("C:\\Users\\Peluso Antonio\\Desktop\\appoggio\\" + filename);
			
			// verifica presenza file su ftp
			if (fileProtocollo.exists()) {
				// calcolo hash SHA-256 del file
				byte[] hash = DigestUtils.sha256(new BufferedInputStream(new FileInputStream(fileProtocollo)));
				
				// encode hash
				String encodedHash = Base64.encodeBase64String(hash);
				
				log.debug("Hash in input: " + hashFile + " - Hash calcolato: " + encodedHash);
				
				// verifica hash del file con quella passata dal comune
				
				if (hashFile.equals(encodedHash)) {
					result.setResult(true);
					result.setCode("200");
				}
				else {
					// errore hash non corrispondenti
					result.setResult(false);
					result.setMessage("File corrotto: impronta dichiarata e calcolata sul file " + filename + " non coincidono");
					result.setCode("452");
				}
			}
			else {
				// errore file non presente
				result.setResult(false);
				result.setMessage("File " + filename + " non presente su ftp");
				result.setCode("428");
			}	
		} catch (Exception e) {
			result.setResult(false);
			result.setMessage("Fatal error imprevisto: la richiesta non può essere presa in carico");
			result.setCode("503");
			
			log.error("Errore per la richiesta " + idRichiesta + " : errore durante il calcolo hash del file: " + filename +": " + e.getMessage(), e);
		}
		
		return result;
	}
	
//	private Document getMetadatiProtocolloXml(String metadatiProtocollo) {
//		Document docXml = null;
//		
//		log.debug("Conversione metadatiProtocollo in xml");
//		
//		try {
//			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//			factory.setNamespaceAware(true);
//			DocumentBuilder builder = factory.newDocumentBuilder();
//			docXml = builder.parse(new InputSource(new StringReader(metadatiProtocollo)));
//		} catch (Exception e) {
//			log.error(e.getMessage(), e);
//		}
//		
//		return docXml;
//	}
	
	private ValidazioneProtocolloBean checkXml(Document docXml) {
		ValidazioneProtocolloBean result = new ValidazioneProtocolloBean();
		
		return result;
	}
	
	private ProtocollazioneAsyncResponse insertBMTJob(String docXml, String filename, String hashFile, String idReceiver, 
			UtenzaApplicazioneEsternaBean utenzaApplicazione, TRichiestaProtocollazioneBean savedRequestBean, String operazione) throws Exception {
		log.debug("metodo per inserimento parametri in DB per avviare job");
		
		ProtocollazioneAsyncResponse result = new ProtocollazioneAsyncResponse();
		
		Session session = null;
		try {
			
			log.debug("Schema per connessione con Hibernate: " + schemaBean.getSchema());
			
			session = HibernateUtil.begin();
			Transaction lTransaction = session.beginTransaction();
			
			log.debug("Avvio sessione Hibernate");
			
			String tipoJob;
			if(operazione.equalsIgnoreCase(OPERAZIONE_INSERT)) {
				tipoJob = TIPO_JOB_ADD;
			}else {
				tipoJob = TIPO_JOB_UPDATE;
			}
			
			// dati da inserire in BMT_JOBS
			DmpkBmanagerInsbatchBean insertBean = new DmpkBmanagerInsbatchBean();
			insertBean.setIddominioin(new BigDecimal(2));
			insertBean.setUseridin("#AUTO");
			insertBean.setTipojobin(tipoJob);
			insertBean.setCodapplicazionein(utenzaApplicazione.getCodApplicazione());			
			
			/*
			 *      GESTIONE CON JOB_PARAMETER NELA STORE E NON MESSI COL DAO SUCCESSIVAMENTE
			 * 
			List<JobParameterXmlStoreBean> listaJobParameter = new ArrayList<JobParameterXmlStoreBean>();
			
			listaJobParameter = initJobParameters(filename, hashFile, idReceiver, docXml);
			
			XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
			String xmlJobParameters = lXmlUtilitySerializer.bindXmlList(listaJobParameter);
			
			insertBean.setParametriin(xmlJobParameters);
			*/
			
			Locale locale = new Locale("it", "IT");
			final InsbatchImpl service = new InsbatchImpl();
			service.setBean(insertBean);
			session.doWork(new Work() {

				@Override
				public void execute(Connection paramConnection) throws SQLException {
					paramConnection.setAutoCommit(false);
					service.execute(paramConnection);
				}
			});

			StoreResultBean<DmpkBmanagerInsbatchBean> output = new StoreResultBean<DmpkBmanagerInsbatchBean>();
			AnalyzeResult.analyze(insertBean,
					output);
			output.setResultBean(insertBean);
			
			if (output.isInError()) {
				// errore insert
				result.setResult(false);
				result.setCode("503");
				result.setMessage("Fatal error imprevisto: la richiesta non può essere presa in carico");
				
				log.error("Errore per la richiesta " + idRichiesta + " : Errore insert in BMT_JOBS " + output.getStoreName() + " " + output.getErrorCode() + " " + output.getErrorContext() + " " + output.getDefaultMessage());
			}
			else {
				log.debug("Insert in BMT_JOBS completato");

				session.flush();

				// estrazione idJob da sequence
				DmpkBmanagerInsbatchBean resultBean = output.getResultBean();
				BigDecimal jobId = new BigDecimal(resultBean.getIdjobout());

				List<JobParameterBean> jobParamList = new ArrayList<JobParameterBean>();

				// parametro REQUEST_XML da inserire in BMT_JOB_PATRAMETERS
				if (StringUtils.isNotBlank(docXml)) {
					JobParameterBean jobParameterBean = new JobParameterBean();
					jobParameterBean.setIdJob(jobId);
					jobParameterBean.setParameterId(BigDecimal.ZERO);
					jobParameterBean.setParameterType("VARCHAR2");
					jobParameterBean.setParameterDir("IN");
					jobParameterBean.setParameterSubtype("REQUEST_XML");
					jobParameterBean.setParameterValue(docXml);
					jobParamList.add(jobParameterBean);
				}

				log.debug("Insert in BMT_JOB_PATRAMETERS - REQUEST_XML: " + docXml);

				// parametro NOME_FILE_PRIMARIO da inserire in BMT_JOB_PATRAMETERS
				if (StringUtils.isNotBlank(filename)) {
					JobParameterBean jobParameterBean = new JobParameterBean();
					jobParameterBean = new JobParameterBean();
					jobParameterBean.setIdJob(jobId);
					jobParameterBean.setParameterId(BigDecimal.ONE);
					jobParameterBean.setParameterType("VARCHAR2");
					jobParameterBean.setParameterDir("IN");
					jobParameterBean.setParameterSubtype("NOME_FILE_PRIMARIO");
					jobParameterBean.setParameterValue(filename);
					jobParamList.add(jobParameterBean);
				}

				log.debug("Insert in BMT_JOB_PATRAMETERS - NOME_FILE_PRIMARIO: " + filename);

				// parametro HASH_FILE da inserire in BMT_JOB_PATRAMETERS
				if (StringUtils.isNotBlank(hashFile)) {
					JobParameterBean jobParameterBean = new JobParameterBean();
					jobParameterBean = new JobParameterBean();
					jobParameterBean.setIdJob(jobId);
					jobParameterBean.setParameterId(BigDecimal.valueOf(2L));
					jobParameterBean.setParameterType("VARCHAR2");
					jobParameterBean.setParameterDir("IN");
					jobParameterBean.setParameterSubtype("HASH_FILE");
					jobParameterBean.setParameterValue(hashFile);
					jobParamList.add(jobParameterBean);
				}

				log.debug("Insert in BMT_JOB_PATRAMETERS - HASH_FILE: " + hashFile);

				// parametro ID_RECEIVER da inserire in BMT_JOB_PATRAMETERS
				if (StringUtils.isNotBlank(idReceiver)) {
					JobParameterBean jobParameterBean = new JobParameterBean();
					jobParameterBean = new JobParameterBean();
					jobParameterBean.setIdJob(jobId);
					jobParameterBean.setParameterId(BigDecimal.valueOf(3L));
					jobParameterBean.setParameterType("VARCHAR2");
					jobParameterBean.setParameterDir("IN");
					jobParameterBean.setParameterSubtype("ID_RECEIVER");
					jobParameterBean.setParameterValue(idReceiver);
					jobParamList.add(jobParameterBean);
				}
				
				log.debug("Insert in BMT_JOB_PATRAMETERS - ID_RECEIVER: " + idReceiver);
				
				// parametro COD_APPLICAZIONE da inserire in BMT_JOB_PATRAMETERS
				if (StringUtils.isNotBlank(utenzaApplicazione.getCodApplicazione())) {
					JobParameterBean jobParameterBean = new JobParameterBean();
					jobParameterBean = new JobParameterBean();
					jobParameterBean.setIdJob(jobId);
					jobParameterBean.setParameterId(BigDecimal.valueOf(4L));
					jobParameterBean.setParameterType("VARCHAR2");
					jobParameterBean.setParameterDir("IN");
					jobParameterBean.setParameterSubtype("COD_APPLICAZIONE");
					jobParameterBean.setParameterValue(utenzaApplicazione.getCodApplicazione());
					jobParamList.add(jobParameterBean);
				}
				
				log.debug("Insert in BMT_JOB_PATRAMETERS - COD_APPLICAZIONE: " + utenzaApplicazione.getCodApplicazione());
				
				// parametro ISTANZA_APPLICAZIONE da inserire in BMT_JOB_PATRAMETERS
				if (StringUtils.isNotBlank(utenzaApplicazione.getIstanzaApplicazione())) {
					JobParameterBean jobParameterBean = new JobParameterBean();
					jobParameterBean = new JobParameterBean();
					jobParameterBean.setIdJob(jobId);
					jobParameterBean.setParameterId(BigDecimal.valueOf(5L));
					jobParameterBean.setParameterType("VARCHAR2");
					jobParameterBean.setParameterDir("IN");
					jobParameterBean.setParameterSubtype("ISTANZA_APPLICAZIONE");
					jobParameterBean.setParameterValue(utenzaApplicazione.getIstanzaApplicazione());
					jobParamList.add(jobParameterBean);
				}
				
				log.debug("Insert in BMT_JOB_PATRAMETERS - ISTANZA_APPLICAZIONE: " + utenzaApplicazione.getIstanzaApplicazione());
				
				// parametro PASSWORD da inserire in BMT_JOB_PATRAMETERS
				if (StringUtils.isNotBlank(utenzaApplicazione.getPassword())) {
					JobParameterBean jobParameterBean = new JobParameterBean();
					jobParameterBean = new JobParameterBean();
					jobParameterBean.setIdJob(jobId);
					jobParameterBean.setParameterId(BigDecimal.valueOf(6L));
					jobParameterBean.setParameterType("VARCHAR2");
					jobParameterBean.setParameterDir("IN");
					jobParameterBean.setParameterSubtype("PASSWORD");
					jobParameterBean.setParameterValue(utenzaApplicazione.getPassword());
					jobParamList.add(jobParameterBean);
				}
				
				log.debug("Insert in BMT_JOB_PATRAMETERS - PASSWORD: " + utenzaApplicazione.getPassword());
				
				// parametro USERNAME da inserire in BMT_JOB_PATRAMETERS
				if (StringUtils.isNotBlank(utenzaApplicazione.getUserName())) {
					JobParameterBean jobParameterBean = new JobParameterBean();
					jobParameterBean = new JobParameterBean();
					jobParameterBean.setIdJob(jobId);
					jobParameterBean.setParameterId(BigDecimal.valueOf(7L));
					jobParameterBean.setParameterType("VARCHAR2");
					jobParameterBean.setParameterDir("IN");
					jobParameterBean.setParameterSubtype("USERNAME");
					jobParameterBean.setParameterValue(utenzaApplicazione.getUserName());
					jobParamList.add(jobParameterBean);
				}

				log.debug("Insert in BMT_JOB_PATRAMETERS - USERNAME: " + utenzaApplicazione.getUserName());
				
				// parametro USERNAME da inserire in BMT_JOB_PATRAMETERS
				if (operazione.equalsIgnoreCase(OPERAZIONE_UPDATE)) {
					File fileZip = new File(protocollazioneAsyncConfigBean.getPathFtp() + filename);
					
					String uriZip = DocumentStorage.store(fileZip, null);					
					
					JobParameterBean jobParameterBean = new JobParameterBean();
					jobParameterBean = new JobParameterBean();
					jobParameterBean.setIdJob(jobId);
					jobParameterBean.setParameterId(BigDecimal.valueOf(8L));
					jobParameterBean.setParameterType("VARCHAR2");
					jobParameterBean.setParameterDir("IN");
					jobParameterBean.setParameterSubtype("URI_FILE_ZIP");
					jobParameterBean.setParameterValue(uriZip);
					jobParamList.add(jobParameterBean);
					
					log.debug("Insert in BMT_JOB_PATRAMETERS - URI_FILE_ZIP: " + uriZip);
				}				

				// insert in BMT_JOB_PATRAMETERS
				DaoBmtJobParameters daoBmtJobParameters = new DaoBmtJobParameters();
				for (JobParameterBean jobParam : jobParamList) {
					daoBmtJobParameters.saveInSession(jobParam, session);
				}
				
				//AGGIORNO LA RICHIESTA ASSOXIANDOLI L ID DEL JOB CHE LO PRENDE IN CARICO
				savedRequestBean.setIdJob(jobId);
				
				updateRichiestaProtocollazione(savedRequestBean, STATO_INSERITA, "");
				

				lTransaction.commit();

				result.setResult(true);
				result.setCode("200");
				result.setMessage("Richiesta inserita correttamente");
			}
		} catch (Exception e) {
			log.error("Errore per la richiesta " + idRichiesta + " : " + e.getMessage(),e);
			
			result.setResult(false);
			result.setCode("503");
			result.setMessage("Fatal error imprevisto: la richiesta non può essere presa in carico");
		} finally {
			HibernateUtil.release(session);
		}
		
		return result;
	}
	
	private List<JobParameterXmlStoreBean> initJobParameters(String filename, String hashFile, String idReceiver, String docXml) {
		List<JobParameterXmlStoreBean> listaJobParameter = new ArrayList<JobParameterXmlStoreBean>();
		
		if(StringUtils.isNotBlank(filename)) {
			JobParameterXmlStoreBean joParameter = new JobParameterXmlStoreBean();
			joParameter.setNomeParametro("NOME_FILE_PRIMARIO");
			joParameter.setTipoParametro("VARCHAR2");
			joParameter.setValoreParametro(filename);
			joParameter.setVerso("IN");
			
			listaJobParameter.add(joParameter);
		}
		if(StringUtils.isNotBlank(hashFile)) {
			JobParameterXmlStoreBean joParameter = new JobParameterXmlStoreBean();
			joParameter.setNomeParametro("HASH_FILE");
			joParameter.setTipoParametro("VARCHAR2");
			joParameter.setValoreParametro(hashFile);
			joParameter.setVerso("IN");
			
			listaJobParameter.add(joParameter);
		}
		if(StringUtils.isNotBlank(idReceiver)) {
			JobParameterXmlStoreBean joParameter = new JobParameterXmlStoreBean();
			joParameter.setNomeParametro("ID_RECEIVER");
			joParameter.setTipoParametro("VARCHAR2");
			joParameter.setValoreParametro(idReceiver);
			joParameter.setVerso("IN");
			
			listaJobParameter.add(joParameter);
		}
		if(StringUtils.isNotBlank(docXml)) {
			JobParameterXmlStoreBean joParameter = new JobParameterXmlStoreBean();
			joParameter.setNomeParametro("REQUEST_XML");
			joParameter.setTipoParametro("VARCHAR2");
			joParameter.setValoreParametro(docXml);
			joParameter.setVerso("IN");
			
			listaJobParameter.add(joParameter);
		}
				
		return listaJobParameter;
	}

	private ProtocollazioneAsyncResponse checkXmlRequest(String xml, String filePrimario, String operazione) {

		ProtocollazioneAsyncResponse result = new ProtocollazioneAsyncResponse();

		try {
			File xmlFileTemp = File.createTempFile("temp", ".xml");

			FileWriter fr = null;
			try {
				fr = new FileWriter(xmlFileTemp);
				fr.write(xml);
			} finally {
				if (fr != null) {
					fr.close();
				}
			}

			// File schemaFile = new File("/location/to/localfile.xsd");
			// URL schemaFile = new
			// URL("http://161.27.146.14:8080//AurigaBusiness/includes/xsd/NewUD.xsd");
			
			String pathXsd;
			
			if(operazione.equalsIgnoreCase(OPERAZIONE_UPDATE)) {
				pathXsd = PATH_XSD_UPDUD;
			}else {
				pathXsd = PATH_XSD_ADDUD;
			}
			
			URL schemaFile = context.getResource(pathXsd);

			Source xmlFile = new StreamSource(xmlFileTemp);

			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

			Schema schema = schemaFactory.newSchema(schemaFile);
			Validator validator = schema.newValidator();
			validator.validate(xmlFile);
			log.debug("XmlRequest è valido");
			
			//Controllo che se c'è il filePrimario nella request del servizio deve esserci anche il tag nell xml di protocollazione
			if(StringUtils.isNotBlank(filePrimario) && OPERAZIONE_INSERT.equalsIgnoreCase(operazione)) {
				if(checkFilePrimarioNelXml(xml)) {
					result.setResult(true);
				}else {
					log.error("Per il file principale inviato manca il corrispondente tag nella request di protocollazione");
					
					result.setMessage("Per il file principale inviato manca il corrispondente tag nella request di protocollazione");
					result.setCode("400");
					result.setResult(false);
				}
			}else {
				result.setResult(true);
			}

		} catch (SAXException e) {
			String errorMessage = "XML di richiesta di protocollazione non conforme al tracciato xsd";
			log.error(errorMessage + e.getMessage(), e);

			result.setMessage(errorMessage);
			result.setResult(false);
			result.setCode("400");

		} catch (Exception ex) {
			log.error("Errore per la richiesta " + idRichiesta + " : Errore durante la convalida del xml di request: " + ex.getMessage(), ex);
			result.setMessage("Fatal error imprevisto: la richiesta non può essere presa in carico");
			result.setResult(false);
			result.setCode("503");
		}

		return result;
	}
	
	private TRichiestaProtocollazioneBean insertRichiestaProtocollazione(ProtocollazioneAsyncRequest request, String codApplicazione) throws Exception {
		Session session = null;
		TRichiestaProtocollazioneBean savedBean = null;
		
		try {
			// bean connessione
			schemaBean = new SchemaBean();
			schemaBean.setSchema(protocollazioneAsyncConfigBean.getDefaultSchema());
			
			SubjectBean subject = new SubjectBean();
			subject.setIdDominio(schemaBean.getSchema());
			SubjectUtil.subject.set(subject);
				
			log.debug("Schema per connessione con Hibernate: " + schemaBean.getSchema());
			
			session = HibernateUtil.begin();
			Transaction lTransaction = session.beginTransaction();
			
			log.debug("Avvio sessione Hibernate");
			
			Date dateIns = new Date();
			
			Gson gson = new Gson();
	        String jsonRequest = StringEscapeUtils.unescapeJava(gson.toJson(request)); 
			
			TRichiestaProtocollazioneBean tRichiestaProtocollazioneBean = new TRichiestaProtocollazioneBean();
			tRichiestaProtocollazioneBean.setIdJob(null);
			tRichiestaProtocollazioneBean.setTsIns(dateIns);
			tRichiestaProtocollazioneBean.setStato(STATO_INSERITA);
			tRichiestaProtocollazioneBean.setJsonRequest(jsonRequest);
			tRichiestaProtocollazioneBean.setXmlFragment(request.getMetadatiProtocollo());
			tRichiestaProtocollazioneBean.setCodApplicazione(codApplicazione);
			tRichiestaProtocollazioneBean.setOperazione(request.getOperazione());
			tRichiestaProtocollazioneBean.setProtocollazioneAvvenuta(false);
			tRichiestaProtocollazioneBean.setProtocollazioneRichiamata(false);
			tRichiestaProtocollazioneBean.setInvioEsitoProtocollazioneAvvenuto(false);
			tRichiestaProtocollazioneBean.setTryInvioEsito(new BigDecimal(0));
			tRichiestaProtocollazioneBean.setTryProt(new BigDecimal(0));
			
			DaoTRichiestaProtocollazione daoTRichiestaProtocollazione = new DaoTRichiestaProtocollazione();
			savedBean = daoTRichiestaProtocollazione.save(tRichiestaProtocollazioneBean);
			
			idRichiesta = savedBean.getIdRichiesta();
			
			log.debug("Inserimento richiesta di protocollazione con id: " + idRichiesta);
			
			lTransaction.commit();
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		} finally {
			HibernateUtil.release(session);
		}
		
		return savedBean;
	}
	
	private void updateRichiestaProtocollazione(TRichiestaProtocollazioneBean tRichiestaProtocollazioneBean, String stato, String errorMessage) throws Exception{
		Session session = null;
		
		try {
			// bean connessione
			SchemaBean schemaBean = new SchemaBean();
			schemaBean.setSchema(protocollazioneAsyncConfigBean.getDefaultSchema());
			
			SubjectBean subject = new SubjectBean();
			subject.setIdDominio(schemaBean.getSchema());
			SubjectUtil.subject.set(subject);
			
			log.debug("Schema per connessione con Hibernate: " + schemaBean.getSchema());
			
			session = HibernateUtil.begin();
			Transaction lTransaction = session.beginTransaction();
			
			log.debug("Avvio sessione Hibernate");
			
			Date dateIns = new Date();
			
			tRichiestaProtocollazioneBean.setTsIns(dateIns);
			tRichiestaProtocollazioneBean.setStato(stato);
			tRichiestaProtocollazioneBean.setTsMessaggioErrore(errorMessage);
			
			DaoTRichiestaProtocollazione daoTRichiestaProtocollazione = new DaoTRichiestaProtocollazione();
			daoTRichiestaProtocollazione.update(tRichiestaProtocollazioneBean);
			
			log.debug("Aggiornata richiesta di protocollazione con esito: " + stato + " " + errorMessage);
			
			lTransaction.commit();
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		} finally {
			HibernateUtil.release(session);
		}
	}
	
	private void configParamBean() {
		
		log.debug("preparazione configurazione operazione protocollazione asincrona");
		
		try {
			// recupero il token per effettuare la chiamata alla store e il relativo schema dal bean di configurazione
			protocollazioneAsyncConfigBean = (OperazioniAurigaProtocollazioneAsyncConfigBean) SpringAppContext.getContext()
					.getBean("OperazioniAurigaProtocollazioneAsyncConfigBean");
			
			// bean connessione
			SchemaBean schemaBean = new SchemaBean();
			schemaBean.setSchema(protocollazioneAsyncConfigBean.getDefaultSchema());
			
			SubjectBean subject = new SubjectBean();
			subject.setIdDominio(schemaBean.getSchema());
			SubjectUtil.subject.set(subject);
			
		} catch (Exception e) {
			log.error("Eccezione nel recupero del bean OperazioniAurigaProtocollazioneAsyncConfigBean");
		}
		
		if (protocollazioneAsyncConfigBean == null) {
			log.error("Bean OperazioniAurigaProtocollazioneAsyncConfigBean non configurato");
		}
		if (StringUtils.isBlank(protocollazioneAsyncConfigBean.getDefaultSchema())) {
			log.error("Schema non valorizzato");
		}
		if (protocollazioneAsyncConfigBean.getFileManagerConfig() == null
				|| protocollazioneAsyncConfigBean.getFileManagerConfig().getTipoServizio() == null) {
			log.error("Configurazione file manager non valorizzate");
		}
		
		log.debug("fine configurazione operazione protocollazione asincrona");
	}
	
}
