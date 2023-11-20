/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.xerces.parsers.DOMParser;
import org.hibernate.Session;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.remoting.jaxws.JaxWsSoapFaultException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXParseException;

import com.sun.istack.ByteArrayDataSource;
import com.sun.xml.ws.transport.Headers;

import it.eng.auriga.database.store.dmpk_utility.bean.DmpkUtilityCtrlconnectiontokenBean;
import it.eng.auriga.database.store.dmpk_utility.store.Ctrlconnectiontoken;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.function.WSFileUtils;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.dao.DaoWSTrace;
import it.eng.auriga.module.business.entity.WSTrace;
import it.eng.auriga.repository2.generic.VersionHandler;
import it.eng.auriga.repository2.generic.VersionHandlerException;
import it.eng.auriga.repository2.jaxws.jaxbBean.service.request.ServiceRequest;
import it.eng.auriga.repository2.jaxws.jaxbBean.service.response.ServiceResponse;
import it.eng.auriga.repository2.jaxws.webservices.addunitadoc.AttachWSBean;
import it.eng.auriga.repository2.jaxws.webservices.addunitadoc.WSAddUd;
import it.eng.auriga.repository2.jaxws.webservices.addunitadoc.WSAddUdConfig;
import it.eng.auriga.repository2.jaxws.webservices.addunitadoc.multithread.CallInfoFileThread;
import it.eng.auriga.repository2.jaxws.webservices.addunitadoc.visure.AddUdUtils;
import it.eng.auriga.repository2.jaxws.webservices.common.bean.AttachWSProperties;
import it.eng.auriga.repository2.jaxws.webservices.login.WSLogin;
import it.eng.auriga.repository2.jaxws.webservices.util.BridgeSingleton;
import it.eng.auriga.repository2.jaxws.webservices.util.InfoUnitaDoc;
import it.eng.auriga.repository2.jaxws.webservices.util.WSAttachBean;
import it.eng.auriga.repository2.jaxws.webservices.util.XPathHelper;
import it.eng.auriga.repository2.util.Base64;
import it.eng.auriga.repository2.util.DBHelperSavePoint;
import it.eng.auriga.repository2.util.InputStreamDataSource;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.document.function.GestioneDocumenti;
import it.eng.document.function.bean.AllegatiBean;
import it.eng.document.function.bean.FileInfoBean;
import it.eng.document.function.bean.Flag;
import it.eng.document.function.bean.GenericFile;
import it.eng.document.function.bean.RebuildedFile;
import it.eng.document.function.bean.RebuildedFileStored;
import it.eng.document.function.bean.TipoFile;
import it.eng.document.storage.DocumentStorage;
import it.eng.jaxb.variabili.Lista;
import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.jaxb.variabili.Lista.Riga.Colonna;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.InfoGenericFile;
import it.eng.utility.pdfUtility.commenti.PdfCommentiUtil;
import it.eng.utility.pdfUtility.editabili.PdfEditabiliUtil;
import oracle.sql.CLOB;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author unascribed
 * @version 1.0
 */

abstract public class JAXWSAbstractAurigaService extends SpringBeanAutowiringSupport implements ErrorHandler {

	@Resource
	private WebServiceContext context;

	// questo blocco (aperta/chiusa parentesi grafa) e' un blocco statico di inizializzazione
	// in questo modo viene eseguito al class loading.
	private static Logger aLogger = Logger.getLogger(JAXWSAbstractAurigaService.class.getName());

	// ***************************** stringhe di errore predefinite ******************

	// Errore 1
	public static final int ERR_XML_NON_VALIDO = 1;
	public static final String ERROR_XML_NON_VALIDO = "Xml non valido: \n";
	// Errore 51
	public static final String ERROR_CHK_XML_NON_VALIDO = "Errore imprevisto nella verifica della validita' della stringa XML di input: \n";

	// Errore 3
	public static final int ERR_ENTE_NON_VALIDO = 3;
	public static final String ERROR_ENTE_NON_VALIDO = "Codice applicazione  e/o Istanza applicazione non valorizzati o nulli";
	// Errore 53
	public static final String ERROR_CHK_COD_ENTE = "Errore imprevisto nel reperimento della stringa di connessione al db (BRIDGE)";

	// Errore 4
	public static final int ERR_LOGIN_FALLITO = 4;
	// Il codice ritornato non ? il 4, viene aggiunto quello che "ha da dire" la stored di login.
	public static final String ERROR_LOGIN_FALLITO = "Login fallito:";
	// 54 errore inatteso in fase di login
	public static final String ERROR_CHK_LOGIN_FALLITO = "Errore imprevisto durante l'autenticazione dell'utente";

	// Errore 6
	public static final int ERR_CONNESSIONE_DB = 6;
	public static final String ERROR_CONNESSIONE_DB = "Fallito reperimento della connessione al database";
	// Errore 56
	public static final String ERROR_CHK_CONNESSIONE_DB = "Errore imprevisto nel reperimento della connessione al database";

	// Errore 7
	public static final int ERR_STOREFUNCT_MANCANTE = 7;
	// Errore 57
	public static final String ERROR_CHK_STOREFUNCT_MANCANTE = "Errore imprevisto: Manca la storefunction Login";

	// Errore 8
	public static final int ERR_XML_ALTERATO = 8;
	public static final String ERROR_XML_ALTERATO = "Il DIGEST SHA-1 della stringa XML di input non coincide con quello fornito in input";
	// Errore 58
	public static final String ERROR_CHK_XML = "Errore imprevisto nella verifica del DIGEST SHA-1 della stringa XML di input";

	// Errore 9
	public static final int ERR_CREDENZIALI_LOGIN_INVALIDE = 9;
	public static final String ERROR_XML_CREDENZIALI_LOGIN_INVALIDE = "Errore: le credenziali di login non sono valide";
	// Errore 59
	public static final String ERROR_CHK_CREDENZIALI_LOGIN = "Errore imprevisto nel controllo delle credenziali di login";

	// Errore 10
	public static final int ERR_ATTACH_ALLEGATO = 10;
	// Errore 60
	public static final String ERROR_CHK_ATTACH_ALLEGATO = "Errore imprevisto nell'allegare l'attachment alla risposta";

	// Errore 11
	public static final int ERR_INTERNAL_LOGIN_FALLITO = 11;
	// Il codice ritornato non ? il 11, viene aggiunto quello che "ha da dire" la stored di login.
	public static final String ERROR_INTERNAL_LOGIN_FALLITO = "Login interno fallito:";
	// Errore 61 : errore inatteso in fase di login
	public static final String ERROR_CHK_INTERNAL_LOGIN_FALLITO = "Errore durante l'autenticazione dell'utente: controllare il tipo e l'id dominio";

	// costanti per codifica esito
	public static final int SUCCESSO = 1;
	public static final int FALLIMENTO = 0;
	protected static final int DELTA_INATTESO = 50;

	public static final int ERR_ERRORE_APPLICATIVO = 100;
	public static final String ERROR_ERRORE_APPLICATIVO = "Errore generico imprevisto";

	// varie altre costanti
	// protected DBManager dbMan = null;
	public static String COLUMN_SEPARATOR = "|*|";
	public static final String NO_CHK_XML = "_NoVerificaXml_";
	public static final String HEADER_SCHEMA_DB = "schema";  // nome della variabile nel campo header della request

	// per il DIME dovrebbe essere 3 mentre per ikl MIME dovrebbe essere 2
	public static int SEND_TYPE_MIME = 2;
	public static int SEND_TYPE_DIME = 3;

	// String pathTmpUpload=null;

	public static final String _SPRING_BEAN_VERSIONHANDLER = "VersionHandler";
	public static final String _SPRING_BEAN_FAXHANDLER = "FaxHandler";
	public static final String _SPRING_BEAN_BRIDGESINGLETON = "BridgeSingleton";
	public static final String _SPRING_BEAN_WSCONFIGUER = "WebServiceConfigurer";
	public static final String _SPRING_BEAN_REPOSITORYADVANCED = "RepositoryAdvanced";
	public static final String _SPRING_BEAN_PDFCONVERTER = "PdfConverter";
	public static final String _SPRING_BEAN_JAXWS = "JAXWSConfigBean";

	
	public static final int DEFAULT_MAXSIZE_POOL_THREAD = 5;

	private Connection mConnection = null;
	
	/**
	 * Il costruttore si occupa solo di caricare la configurazione
	 */
	public JAXWSAbstractAurigaService() {
	}

	protected AutowireCapableBeanFactory getApplicationContext() {
		ServletContext servletContext = (ServletContext) context.getMessageContext().get("javax.xml.ws.servlet.context");
		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		return webApplicationContext.getAutowireCapableBeanFactory();
	}

	protected DataHandler[] getMessageDataHandlers() throws JaxWsSoapFaultException {
		// reperisco il contesto
		MessageContext msgContext = context.getMessageContext();
		Map<String, DataHandler> mapDataHandler = (Map<String, DataHandler>) msgContext.get(MessageContext.INBOUND_MESSAGE_ATTACHMENTS);

		// restituisco gli attachment
		DataHandler[] handlers = getOrderedAttachments(mapDataHandler);

		return handlers;
	}

	protected DataHandler getMessageDataHandlers_1() throws JaxWsSoapFaultException {
		// reperisco il contesto
		MessageContext msgContext = context.getMessageContext();
		Map<String, DataHandler> mapDataHandler = (Map<String, DataHandler>) msgContext.get(MessageContext.INBOUND_MESSAGE_ATTACHMENTS);

		// restituisco gli attachment
		DataHandler[] handlers = getOrderedAttachments(mapDataHandler);

		// Restituisco solo il primo
		DataHandler attachmentsOut = handlers[0];

		return attachmentsOut;
	}

	protected DataHandler[] getOrderedAttachments(Map<String, DataHandler> mapDataHandler) {
		Collection<DataHandler> dataHandlers = mapDataHandler.values();
		DataHandler[] handlers = new DataHandler[dataHandlers.size()];
		Pattern rxProgressivo = Pattern.compile("^<?#(\\d+)#.*>?$");
		for (Entry<String, DataHandler> dh : mapDataHandler.entrySet()) {
			Matcher mtcProgressivo = rxProgressivo.matcher(dh.getKey());
			if (mtcProgressivo.matches()) {
				int progressivo = Integer.parseInt(mtcProgressivo.group(1));
				// String nomeAttach = mtcProgressivo.group(1);
				handlers[progressivo - 1] = dh.getValue();
			} else {
				handlers = new DataHandler[dataHandlers.size()];
				handlers = dataHandlers.toArray(handlers);
				break;
			}
		}
		return handlers;
	}

	/**
	 * Restituisce una stringa contenente un xml con un solo tag contenente il valore passato <?xml version=\"1.0\" encoding=\"ISO-8859-1\"?> <tag>value</tag>
	 * 
	 * @param value
	 * @param tag
	 * @return
	 */
	public String generaSimpleAttachXml(String value, String tag) {
		StringBuffer xml = new StringBuffer();
		String valueEsc = null;

		// ...se il token non è null
		if (value != null) {
			// effettuo l'escape di tutti i caratteri
			valueEsc = eng.util.XMLUtil.xmlEscape(value);
		}
		aLogger.debug("generaXMLToken: value = " + value);
		aLogger.debug("generaXMLToken: valueEsc = " + valueEsc);
		xml.append("<?xml version=\"1.0\"  encoding=\"ISO-8859-1\"?>\n");
		xml.append("<" + tag + ">" + valueEsc + "</" + tag + ">\n");
		return xml.toString();
	}

	public boolean attachStream(InputStream is, int type) throws Exception {
		DataSource ds = null;
		try {
			MessageContext msgContext = context.getMessageContext();
			ds = new InputStreamDataSource(is);

			DataHandler handler = new DataHandler(ds, "application/xml");
			
			Map<String, DataHandler> mapDataHandlers = new HashMap<String, DataHandler>();
			mapDataHandlers.put(handler.getName(), handler);

			msgContext.put(MessageContext.OUTBOUND_MESSAGE_ATTACHMENTS, mapDataHandlers);

			return true;
		} catch (Exception e) {
			aLogger.error("Errore in attachStream: " + e.getMessage(), e);
			return false;
		}
	}

	public boolean attachListInputStream(List<InputStream> listInputStreamIn) throws Exception {

		Map<String, DataHandler> mapDataHandlers = new HashMap<String, DataHandler>();

		// reperisco il contesto
		MessageContext messageContext = context.getMessageContext();

		// Per ogni InoutStream
		for (InputStream lInputStream : listInputStreamIn) {
			File fileTemp = null;
			try {
				// Apro lo stream di out
				fileTemp = File.createTempFile("fileattachtemp", ".tmp");
				FileOutputStream outFile = new FileOutputStream(fileTemp);

				// Copio il file nella cartella temp
				byte[] bytes = new byte[1024];
				int length;
				while ((length = lInputStream.read(bytes)) >= 0) {
					outFile.write(bytes, 0, length);
				}
				lInputStream.close();
				outFile.flush();
				outFile.close();

				DataHandler handler = new DataHandler(new ByteArrayDataSource(FileUtils.readFileToByteArray(fileTemp), "application/octet-stream"));

				// Create the attachment as a DIME attachment
				if (mapDataHandlers == null || mapDataHandlers.size() == 0) {
					mapDataHandlers = new LinkedHashMap<String, DataHandler>();
				}

				mapDataHandlers.put(fileTemp.getName(), handler);

				// aggiunge l'attachment all response
				messageContext.put(MessageContext.OUTBOUND_MESSAGE_ATTACHMENTS, mapDataHandlers);
			} catch (Exception e) {
				aLogger.error("Errore in attachFile(File file = " + fileTemp + ")" + e.getMessage(), e);
				return false;
			}
		}

		return true;
	}

	//Fulvio, lista tipi oltre a lista campi
	public boolean attachListInputStreamTypes(List<InputStream> listInputStreamIn,List<String> listInputStreamInTypes) throws Exception {

		Map<String, DataHandler> mapDataHandlers = new HashMap<String, DataHandler>();

		// reperisco il contesto
		MessageContext messageContext = context.getMessageContext();

		// Per ogni InoutStream
		int i = 0;
		for (InputStream lInputStream : listInputStreamIn) {
			File fileTemp = null;
			try {
				// Apro lo stream di out
				fileTemp = File.createTempFile("fileattachtemp", ".tmp");
				FileOutputStream outFile = new FileOutputStream(fileTemp);

				// Copio il file nella cartella temp
				byte[] bytes = new byte[1024];
				int length;
				while ((length = lInputStream.read(bytes)) >= 0) {
					outFile.write(bytes, 0, length);
				}
				lInputStream.close();
				outFile.flush();
				outFile.close();

				// Get the file from the filesystem
				String mimetype = "application/octet-stream";
				DataHandler handler = null;
				try {
					mimetype = listInputStreamInTypes.get(i);
					handler = new DataHandler(new ByteArrayDataSource(FileUtils.readFileToByteArray(fileTemp), mimetype));
				} catch (Exception e) {
					handler = new DataHandler(new ByteArrayDataSource(FileUtils.readFileToByteArray(fileTemp), "application/octet-stream"));
				}

				// Create the attachment as a DIME attachment
				if (mapDataHandlers == null || mapDataHandlers.size() == 0) {
					mapDataHandlers = new LinkedHashMap<String, DataHandler>();
				}

				mapDataHandlers.put(fileTemp.getName(), handler);

				// aggiunge l'attachment all response
				messageContext.put(MessageContext.OUTBOUND_MESSAGE_ATTACHMENTS, mapDataHandlers);
			} catch (Exception e) {
				aLogger.error("Errore in attachFile(File file = " + fileTemp + ")" + e.getMessage(), e);
				return false;
			}
			i++;
		}

		return true;
	}
	
	
public boolean attachListInputStreamTypes(List<AttachWSProperties> listattach) throws Exception {
		
		Map<String, DataHandler> mapDataHandlers = new HashMap<String, DataHandler>();
		
		// reperisco il contesto
		MessageContext messageContext = context.getMessageContext();
		
		// Per ogni InoutStream
		for (AttachWSProperties attach : listattach) {
			File fileTemp = null;
			try {
				if (attach.getInputStream() != null) {
					// Apro lo stream di out
					fileTemp = File.createTempFile("fileattachtemp", ".tmp");
					FileOutputStream outFile = new FileOutputStream(fileTemp);

					// Copio il file nella cartella temp
					byte[] bytes = new byte[1024];
					int length;
					InputStream is = attach.getInputStream();
					while ((length = is.read(bytes)) >= 0) {
						outFile.write(bytes, 0, length);
					}
					is.close();
					outFile.flush();
					outFile.close();

					// Get the file from the filesystem
					String mimetype = StringUtils.isNotBlank(attach.getMimeType()) ? attach.getMimeType()
							: "application/octet-stream";
					DataHandler handler = null;
					try {
						handler = new DataHandler(new ByteArrayDataSource(FileUtils.readFileToByteArray(fileTemp), mimetype));
					} catch (Exception e) {
						handler = new DataHandler(new ByteArrayDataSource(FileUtils.readFileToByteArray(fileTemp),"application/octet-stream"));
					}

					// Create the attachment as a DIME attachment
					if (mapDataHandlers == null || mapDataHandlers.size() == 0) {
						mapDataHandlers = new LinkedHashMap<String, DataHandler>();
					}

					mapDataHandlers.put(StringUtils.isNotBlank(attach.getNameFile()) ? attach.getNameFile() : fileTemp.getName(), handler);

					// aggiunge l'attachment all response
					messageContext.put(MessageContext.OUTBOUND_MESSAGE_ATTACHMENTS, mapDataHandlers);
				}
			} catch (Exception e) {
				aLogger.error("Errore in attachFile(File file = " + fileTemp + ")" + e.getMessage(), e);
				return false;
			}
		}
		
		return true;
	}
	
	

	/**
	 * Metodo per aggiungere un InputStream come attachment da passare nella response del WS. L'InputStream viene convertito in File. Utilizza il protocollo
	 * DIME
	 * 
	 * @param is
	 *            InputStream
	 * @return boolean
	 * @throws Exception
	 */

	public boolean attachFileFromStream(InputStream is) throws Exception {
		File fileTemp = null;
		try {
			// Apro lo stream di out
			fileTemp = File.createTempFile("fileattachtemp", ".tmp");
			FileOutputStream outFile = new FileOutputStream(fileTemp);
			// Copio il file nella cartella temp
			byte[] bytes = new byte[1024];
			int length;
			while ((length = is.read(bytes)) >= 0) {
				outFile.write(bytes, 0, length);
			}
			is.close();
			outFile.flush();
			outFile.close();
			// reperisco il contesto
			MessageContext messageContext = context.getMessageContext();

			// Get the file from the filesystem
			DataHandler handler = new DataHandler(new ByteArrayDataSource(FileUtils.readFileToByteArray(fileTemp), "application/xml"));

			// Create the attachment as a DIME attachment
			Map<String, DataHandler> mapDataHandlers = (Map<String, DataHandler>) messageContext.get(MessageContext.INBOUND_MESSAGE_ATTACHMENTS);

			if (mapDataHandlers == null || mapDataHandlers.size() == 0) {
				mapDataHandlers = new LinkedHashMap<String, DataHandler>();
			}

			// Map<String, DataHandler> mapDataHandlers = new HashMap<String, DataHandler>();
			mapDataHandlers.put(fileTemp.getName(), handler);

			// aggiunge l'attachment all response
			messageContext.put(MessageContext.OUTBOUND_MESSAGE_ATTACHMENTS, mapDataHandlers);

			return true;

		} catch (Exception e) {
			aLogger.error("Errore in attachFile(File file = " + fileTemp + ")" + e.getMessage(), e);
			return false;
		}
	}

	/**
	 * Metodo per aggiungere un file all'insieme degli attachment che verranno passati nella response del WS. Utilizza il protocollo DIME
	 * 
	 * @param mioFile
	 *            File
	 * @return boolean
	 * @throws Exception
	 */
	public boolean attachFile(File mioFile) throws Exception {
		try {
			// reperisco il contesto
			MessageContext msgContext = context.getMessageContext();

			// Get the file from the filesystem
			FileDataSource fileDS = new FileDataSource(mioFile);
			DataHandler handler = new DataHandler(fileDS);

			// Create the attachment as a DIME attachment
			Map<String, DataHandler> mapDataHandlers = (Map<String, DataHandler>) msgContext.get(MessageContext.INBOUND_MESSAGE_ATTACHMENTS);
			mapDataHandlers.put(handler.getName(), handler);

			// aggiunge l'attachment all response
			msgContext.put(MessageContext.OUTBOUND_MESSAGE_ATTACHMENTS, mapDataHandlers);
			return true;
		} catch (Exception e) {
			aLogger.error("Errore in attachFile(File file = " + mioFile + ")" + e.getMessage(), e);
			return false;
		}
	}

	public boolean attachStream(InputStream is) throws Exception {
		return attachStream(is, SEND_TYPE_DIME);
	}

	public boolean attachStreamMime(InputStream is) throws Exception {
		return attachStream(is, SEND_TYPE_MIME);
	}

	/**
	 * Metodo per aggiungere un attachment da passare nella response del WS, a partire da un InputStream. Utilizza il protocollo DIME
	 * 
	 * @param mioFile
	 *            File
	 * @return boolean
	 * @throws Exception
	 */

	@WebMethod
	@WebResult(partName = "serviceResponse", name = "serviceResponse")
	public ServiceResponse serviceOperation(@WebParam(partName = "service", name = "service") ServiceRequest serviceRequest) {
		WSTrace wsTraceBean = null;
		boolean attivaWSTrace = false;
		
		JAXWSConfigBean lJAXWSConfigBean = null;
		try {
			lJAXWSConfigBean = (JAXWSConfigBean) SpringAppContext.getContext()
					.getBean(_SPRING_BEAN_JAXWS);
		} catch (BeansException e) {
			aLogger.warn("Non è stato trovato il bean JAXWSConfigBean nel file jaxwsservices.xml");
		} 
		
		attivaWSTrace = lJAXWSConfigBean!=null && StringUtils.isNotBlank(lJAXWSConfigBean.getFlgAttivaWSTrace())
				&& lJAXWSConfigBean.getFlgAttivaWSTrace().equalsIgnoreCase("true") ? true : false;
		
		if(attivaWSTrace) {
			wsTraceBean = initTraceWS(serviceRequest);
		}
		
		long t0 = System.currentTimeMillis();
		ServiceResponse serviceResponse = new ServiceResponse();
		serviceResponse.setServiceReturn(service(serviceRequest.getCodApplicazione(), serviceRequest.getIstanzaApplicazione(), serviceRequest.getUserName(),
				serviceRequest.getPassword(), serviceRequest.getXml(), serviceRequest.getHash(), wsTraceBean)); 
		long t1 = System.currentTimeMillis();
		String tempoRisposta = String.valueOf((t1 - t0) / 1000);	
		
		if(attivaWSTrace) {
			if(wsTraceBean!=null) {
				try {
					String xmlResponse = decodeStringBase64(serviceResponse.getServiceReturn()!=null ? serviceResponse.getServiceReturn() : "");  
					wsTraceBean.setXmlResponse(xmlResponse);
					wsTraceBean.setFlgInErrore(xmlResponse.contains("<WSResult>1</WSResult>") ? false : true);
					wsTraceBean.setTempoRispostaWs(tempoRisposta);
					
					DaoWSTrace wsTraceDao = new DaoWSTrace();
				
					wsTraceDao.save(wsTraceBean);
				} catch (Exception e) {
					aLogger.warn("Non è stato possibile salvare il record di tracciamento WS in DB: " + e.getMessage());
				}
			}			
		}
		
		return serviceResponse;
	}
	
	private String decodeStringBase64(String string) {
		Base64 decoder = new Base64();
		byte[] decodebyte = decoder.decode(string);
		return new String(decodebyte);
	}

	private WSTrace initTraceWS(ServiceRequest serviceRequest) {
		WSTrace wsTraceBean = null;
		try {
			wsTraceBean = new WSTrace();
			wsTraceBean.setUsername(serviceRequest.getUserName());
			wsTraceBean.setApplicazione(serviceRequest.getCodApplicazione());
			wsTraceBean.setInstanza(serviceRequest.getIstanzaApplicazione());
			wsTraceBean.setXmlRequest(serviceRequest.getXml());
			wsTraceBean.setHash(serviceRequest.getHash());
			wsTraceBean.setTsInvocazione(new Date());	
			
			String wsdl = context.getMessageContext().get("javax.xml.ws.wsdl.service").toString();
			String [] tokens = wsdl.split("}");		
			wsTraceBean.setService(tokens[1]);
		} catch (Exception e) {
			aLogger.warn("Errore nella creazione del bean di tracciamento WS");
			return null;
		}
		
		return wsTraceBean;
	}

	/**
	 * Servizio generico. Permette di effettuare una serie di controlli di base prima di eseguire il servizio vero e proprio
	 * @param wsTraceBean 
	 */
	public String service(String codApplicazione, String istanzaApplicazione, String userName, String password, String xml, String hash, WSTrace wsTraceBean) {

		String schemaDb = null;

		// token di sicurezza restituito dalla login
		String token = null;

		// desUser e idDominio che verranno restituiti come attributi nell'xml di output della ws di login
		String desUserAttr = null;
		String idDominioAttr = null;
		String desDominio = null;
		String flgTpDominioAut = null;

		// encoder base64.
		it.eng.auriga.repository2.util.Base64 encoder = null;

		// connessione al db valida per tutte le operazioni del WS
		Connection con = null;
		final ConnectionWrapper lConnectionWrapper = new ConnectionWrapper();

		// oggetto storefunction per chiamate a stored procedure
		// eng.storefunction.StoreProcedure store = null;

		// Qui ci va lo XML di risposta
		String resultXML = "";

		// oggetto document per contenere il modello xml
		Document xmlDomDoc = null;

		// flag x sapere se stiamo facendo una login
		boolean isLoginWS = false;

		// verifico che stiamo facendo una login
		if ((userName != null) && !userName.equals("") && (this instanceof WSLogin)) {
			isLoginWS = true;
		}

		Session session = null;

		try {
			// Qualsiasi cosa succeda qua dentro devo rilasciare la
			// connessione al pool. La finally finale (ben avanti) si
			// occupa di questo

			// ***************************************************************
			// ******************* 1. Verifica dell'hash... ******************
			// ***************************************************************

			// la verifica non serve se stiamo facendo una login
			// visto che non c'è un xml di input
			// if (!isDebugMode() && !isLoginWS) {
			if (!isLoginWS) {
				try {
					// creo il digester sha-1 base64 per la stringa xml di input
					java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA");
					md.update(xml.getBytes(StandardCharsets.UTF_8));

					// calcolo lo sha-1 dell'xml di input
					byte[] digest = md.digest();
					
					// istanzio il decoder base64
					encoder = new it.eng.auriga.repository2.util.Base64();
					
					// lo codifico base64
					String digest_str = encoder.encode(digest);
					
					aLogger.debug("*************************************************");
					aLogger.debug("XML INPUT : \n");
					aLogger.debug(xml);
					aLogger.debug("");
					aLogger.debug("");
					aLogger.debug("LENGTH = " + xml.getBytes().length);
					aLogger.debug("");
					aLogger.debug("");
					aLogger.debug("HASH IN = " + hash);
					aLogger.debug("");
					aLogger.debug("*************************************************");
					aLogger.debug("");
					aLogger.debug("HASH RICALCOLATO = " + digest_str);
					aLogger.debug("");
					aLogger.debug("*************************************************");
					
					// confronto il digest con la stringa hash di input
					if (!digest_str.equals(hash)) {
						// fallito il confronto. mando il messaggio di errore.
						aLogger.debug("*************************************************");
						aLogger.debug("CONFRONTO CON HASH DI INPUT FALLITO !!!");
						aLogger.debug("*************************************************");
						return generaXMLRisposta(FALLIMENTO, ERR_XML_ALTERATO, ERROR_XML_ALTERATO, "", "");
					}
					else{
						aLogger.debug("*************************************************");
						aLogger.debug("CONFRONTO CON HASH DI INPUT OK !!!");
						aLogger.debug("*************************************************");
					}
						
				} catch (Exception ex) {
					// lancio un messaggio di errore imprevisto NON APPLICATIVO
					aLogger.error("Errore imprevisto nella verifica dell'hash: " + ex.getMessage(), ex);
					return generaXMLRisposta(FALLIMENTO, ERR_XML_ALTERATO + DELTA_INATTESO, ERROR_CHK_XML, "", "");
				}
			}

			// ***************************************************************
			// ricavo l'id ente mediante la chiamata al bridge
			// ***************************************************************
			try {
				// verifica che codApplicazione,istanzaApplicazione siano valorizzati
				if (codApplicazione == null || codApplicazione.equals("")) { 
					return generaXMLRisposta(FALLIMENTO, ERR_ENTE_NON_VALIDO, ERROR_ENTE_NON_VALIDO, "", "");
				}

				// Utilizziamo il BridgeSingleton per ricavare a quale ente collegarsi in
				// base a codApplicazione e istanzaApplicazione
				String schemaDbFromHeader = retrieveSchemaFromHeader();
				if(schemaDbFromHeader != null) {
					schemaDb = schemaDbFromHeader;
				} else {
					BridgeSingleton bs = (BridgeSingleton) getApplicationContext().getBean(_SPRING_BEAN_BRIDGESINGLETON);
					schemaDb = bs.getDBPoolAlias(codApplicazione, istanzaApplicazione);
				}
			} catch (Exception ex) {
				// lancio un messaggio di errore imprevisto NON APPLICATIVO
				aLogger.error("Errore imprevisto: " + ex.getMessage(), ex);
				return generaXMLRisposta(FALLIMENTO, ERR_ENTE_NON_VALIDO + DELTA_INATTESO, ERROR_CHK_COD_ENTE, "", "");
			}

			SubjectBean subject = new SubjectBean();
			subject.setIdDominio(schemaDb);
			SubjectUtil.subject.set(subject);
			
/* 01/12/2022 ottavio - Eliminata la connessione al db, usata per verificare se e' attivo 
			// ***************************************************************
			// ricavo la connessione al db mediante l'id ente
			// ***************************************************************
			try {
				try {
					session = HibernateUtil.begin();
					session.doWork(new Work() {

						@Override
						public void execute(Connection paramConnection) throws SQLException {
							mConnection = paramConnection;
						}
					});
				} catch (Exception e) {
					throw e;
				}
				// ricavo la connessione dal pool
				con = mConnection;

				// fallita connessione al db
				if (con == null) {
					return generaXMLRisposta(FALLIMENTO, ERR_CONNESSIONE_DB, ERROR_CONNESSIONE_DB, "", "");
				}
			} catch (Exception ex) {
				// lancio un messaggio di errore imprevisto NON APPLICATIVO
				aLogger.error("Errore nel ricavare la connessione: " + ex.getMessage(), ex);
				return generaXMLRisposta(FALLIMENTO, ERR_CONNESSIONE_DB + DELTA_INATTESO, ERROR_CHK_CONNESSIONE_DB, "", "");
			}
*/
			
			// Nuova gestione della login: viene effettuata una volta tramite
			// un apposito web service. Questo restituisce, nell'xml, il token
			// da utilizzare successivamente.
			// Se la USERNAME e' vuota si assume che la login sia gia' stata effettuata
			// e la PASSWORD conterra' il token. Se invece USERNAME non e' vuota allora
			// sto effettuando la login.

			// controllo se la username e' null o vuota
			if (userName == null || userName.trim().equals("")) {
				// prima di settare il token controllo che la password non sia vuota
				if ((password == null || password.trim().equals("")) /* && (!(this instanceof WSRegistrazioneNellaCommunity)) */) {
					// password vuota, torno un errore
					return generaXMLRisposta(FALLIMENTO, ERR_CREDENZIALI_LOGIN_INVALIDE, ERROR_XML_CREDENZIALI_LOGIN_INVALIDE, "", "");
				} else {
					// login vuota e password non vuota, uso la password come token
					token = password;

					try {
						String res[] = null;
						// Estraggo le informazioni del dominio tramite il token
						// IdUserOut
						// IdDominioAutOut
						// FlgTpDominioAutOut
						// CodApplEsternaOut
						// CodIstanzaApplEstOut

						res = getInfoDominioFromToken(con, schemaDb, token);

						String IdUser = res[0]; // IdUserOut
						idDominioAttr = res[1]; // IdDominioAutOut
						flgTpDominioAut = res[2]; // FlgTpDominioAutOut
						String codApplicazioneAut = res[3]; // CodApplEsternaOut
						String istanzaApplicazioneAut = res[4]; // CodIstanzaApplEstOut

						aLogger.debug("risultato dmpk_utility.CtrlConnectionToken TOKEN : " + token + "; IdUser: " + IdUser + "; idDominioAttr: "
								+ idDominioAttr + "; flgTpDominioAut: " + flgTpDominioAut + "; codApplicazioneAut: " + codApplicazioneAut
								+ "; istanzaApplicazioneAut: " + istanzaApplicazioneAut);

					} catch (Exception ex) {
						aLogger.error("Errore nel ricavare lle informazioni del domnio: " + ex.getMessage(), ex);

						return generaXMLRisposta(FALLIMENTO, ERR_LOGIN_FALLITO + DELTA_INATTESO, ERROR_CHK_LOGIN_FALLITO, "", "");
					}

				}
			}
			// user name non vuota, utilizzo la externalLogin
			else {
				try {
					String res[] = null;
					aLogger.debug("Applicazione = " + codApplicazione + " Istanza = " + istanzaApplicazione);

					/*
					 * Comportamento: - Se codApplicazione != "AURIGA" effettuo una login esterna passando applicazione ed istanza ottenuta in ingresso - Se
					 * codApplicazione == "AURIGA" effettuo una login interna. tipoDominio e idDominio sono ricavati a partire dal valore in istanzaApplicazione
					 * secondo la seguente convenzione: + istanzaApplicazione == "" o null, tipoDominio e idDominio sono "" + istanzaApplicazione == "X" (X
					 * numerico) tipoDominio = "X" e idDominio = "" + istanzaApplicazione == "X:Y" (X e Y numerici) tipoDominio = "X" e idDominio = "Y"
					 */
					// gestione della procedura di login e
					// ottenimento del token di connessione

					// prendo istanza del version handler
					aLogger.debug("prendo istanza del version handler");
					VersionHandler vh = getVersionHandler();

					// discriminante tra login interna ed esterna
					if ((VersionHandler._CNOME_APPLICAZIONE).equals(codApplicazione)) {
						String tipoDominio = "";
						String idDominio = "";
						int columnPos = -1;

						// vediamo se dobbiamo fare il parsing di istanzaApplicazione
						if (istanzaApplicazione != null && !"".equals(istanzaApplicazione)) {
							columnPos = istanzaApplicazione.lastIndexOf(":");
							if (columnPos == -1) {
								// ho solo il tipoDominio, vediamo se e' un numerico
								try {
									if (!"".equals(istanzaApplicazione))
										Integer.parseInt(istanzaApplicazione);

									tipoDominio = istanzaApplicazione;
									aLogger.debug("tipoDominio = " + tipoDominio);
								} catch (Exception e) {
									aLogger.error("Errore in fase di login: tipoDominio non numerico");
									return generaXMLRisposta(FALLIMENTO, ERR_INTERNAL_LOGIN_FALLITO + DELTA_INATTESO, ERROR_CHK_INTERNAL_LOGIN_FALLITO, "", "");
								}
							} else {
								tipoDominio = istanzaApplicazione.substring(0, columnPos);
								idDominio = istanzaApplicazione.substring(columnPos + 1);
								aLogger.debug("tipoDominio = " + tipoDominio + " idDominio = " + idDominio);
								try {
									if (!"".equals(tipoDominio))
										Integer.parseInt(tipoDominio);

									if (!"".equals(idDominio))
										Integer.parseInt(idDominio);
								} catch (Exception e) {
									aLogger.error("Errore in fase di login: tipoDominio o idDominio non numerico");
									return generaXMLRisposta(FALLIMENTO, ERR_INTERNAL_LOGIN_FALLITO + DELTA_INATTESO, ERROR_CHK_INTERNAL_LOGIN_FALLITO, "", "");
								}
							}
						}
						
						long t0 = System.currentTimeMillis();
						// login interna
						res = vh.internalLogin(con, userName, password, tipoDominio, idDominio, schemaDb);
						long t1 = System.currentTimeMillis();
						String tempoRispostaLogin = String.valueOf((t1 - t0) / 1000);
						if(wsTraceBean!=null) {
							wsTraceBean.setTempoRispostaLogIn(tempoRispostaLogin);
						}
						aLogger.debug("risultato internal login -> token: " + res[0] + "; desUserAttr: " + res[1] + "; idDominioAttr: " + res[2]
								+ "; desDominio: " + res[3] + "; flgTpDominioAut: " + res[4]);

						// Leggo out restituito dalla WSLogin
						token = res[0]; // CodIdConnectionTokenOut
						desUserAttr = res[1]; // DesUserOut
						idDominioAttr = res[2]; // IdDominioOut
						desDominio = res[3]; // DesDominioOut
						flgTpDominioAut = res[4]; // FlgTpDominioAutOut
					} else {
						// TODO: check applicazione-istanza
						aLogger.debug("effettuo la external login (applicazione esterna: " + codApplicazione + "; istanza: " + istanzaApplicazione + ")");
						if (istanzaApplicazione == null) {
							istanzaApplicazione = "";
						}
						
						long t0 = System.currentTimeMillis();
						// effettuo la external login
						res = vh.externalLogin(con, userName, password, codApplicazione, istanzaApplicazione, schemaDb);
						long t1 = System.currentTimeMillis();
						String tempoRispostaLogin = String.valueOf((t1 - t0) / 1000);
						if(wsTraceBean!=null) {
							wsTraceBean.setTempoRispostaLogIn(tempoRispostaLogin);
						}
						aLogger.debug("risultato external login -> token: " + res[0] + "; desUserAttr: " + res[1] + "; idDominioAttr: " + res[2]
								+ "; desDominio: " + res[3]);

						// Leggo out restituito dalla WSLogin
						token = res[0]; // CodIdConnectionTokenOut
						desUserAttr = res[1]; // DesUserOut
						idDominioAttr = res[2]; // IdDominioOut
						desDominio = res[3]; // DesDominioOut
					}

				} catch (VersionHandlerException vhe) {
					// Qualcosa e` andato storto!!!!
					StringBuffer errMsg = new StringBuffer(ERROR_LOGIN_FALLITO);
					errMsg.append("[");
					errMsg.append(vhe.getMessage());
					errMsg.append("] ");
					return generaXMLRisposta(FALLIMENTO, ERR_LOGIN_FALLITO, errMsg.toString(), "", "");
				} catch (Exception e) {
					aLogger.error("Errore imprevisto in fase di login: " + e.getMessage(), e);
					return generaXMLRisposta(FALLIMENTO, ERR_LOGIN_FALLITO + DELTA_INATTESO, ERROR_CHK_LOGIN_FALLITO, "", "");
				}

			}

			// ***************************************************************
			// verifico la validita' e la wellformness della stringa XML di input
			// ***************************************************************

			// l'xml di input non e' presente in caso di login
			// per cui in quel caso non effettuo il controllo
			if (!isLoginWS) {
				try {
					xmlDomDoc = getDOMDocumentFromXml(xml, getXMLRootNode(), getXsdUri());
				} catch (org.xml.sax.SAXException saxEx) {
					aLogger.error("Errore nel controllare xml in ingresso: " + saxEx.getMessage(), saxEx);
					return generaXMLRisposta(FALLIMENTO, ERR_XML_NON_VALIDO, ERROR_XML_NON_VALIDO + saxEx.getMessage(), "", "");
				} catch (Exception e) {
					aLogger.error("Errore imprevisto nel controllare xml in ingresso: " + e.getMessage(), e);
					return generaXMLRisposta(FALLIMENTO, ERR_XML_NON_VALIDO + DELTA_INATTESO, ERROR_CHK_XML_NON_VALIDO + e.getMessage(), "", "");
				}
			} else {
				// nel caso di login l'xml di input non viene utilizzato quindi lo uso per passare gli attributi desUser e idDominio
				xml = "DesUser=\"" + desUserAttr + "\" IdDominio=\"" + idDominioAttr + "\"";
			}

			// ***************************************************************
			// chiamo l'implementazione concreta
			// ***************************************************************
			try {
				resultXML = serviceImplementation(userName, token, codApplicazione, istanzaApplicazione, con, xmlDomDoc, xml, schemaDb, idDominioAttr, // IdDominioOut
						desDominio, // DesDominioOut
						flgTpDominioAut, // FlgTpDominioAutOut
						wsTraceBean
						);
				return resultXML;

			} catch (SQLException ex) {
				aLogger.error("Errore SQL: " + ex.getMessage(), ex);
				return generaXMLRisposta(FALLIMENTO, ERR_ERRORE_APPLICATIVO,
						"Engineering. SQL error code= " + ex.getErrorCode() + ". Exception=" + ex.getMessage(), "", "");
			} catch (Exception ex) {
				aLogger.error("Errore imprevisto: " + ex.getMessage(), ex);
				return generaXMLRisposta(FALLIMENTO, ERR_ERRORE_APPLICATIVO, "" + ex.getMessage(), "", "");
			}
		} finally {
			try {
				if (con !=null) 
					con.close();
				
				if (session != null)
					HibernateUtil.release(session);
				
			} catch (Exception e) {
			}
		}

	}

	/*
	 * Chiamo il servizio DmpkUtilityCtrlconnectiontoken INPUT : token OUTPUT : IdUserOut IdDominioAutOut FlgTpDominioAutOut CodApplEsternaOut
	 * CodIstanzaApplEstOut
	 */
	public String[] getInfoDominioFromToken(Connection conn, String schemaDb, String tokenIn) throws Exception {
	
		String[] resOut = new String[5];

		if (tokenIn != null && !tokenIn.equalsIgnoreCase("")) {

			aLogger.debug("Inizio getInfoDominioFromToken");
			aLogger.debug("tokenIn : " + tokenIn);

			if (conn!=null) 
				conn.setAutoCommit(false);

			// setto il savepoint
			DBHelperSavePoint.SetSavepoint(conn, "GETINFODOMINIOFROMTOKEN");
			
			/************************************************************************************
			 * Chiamo il servizio di AurigaDocument DmpkUtilityCtrlconnectiontoken
			 ************************************************************************************/

			AurigaLoginBean loginBean = new AurigaLoginBean();
			loginBean.setSchema(schemaDb);
			loginBean.setToken(tokenIn);

			// Inizializzo l'INPUT
			DmpkUtilityCtrlconnectiontokenBean input = new DmpkUtilityCtrlconnectiontokenBean();
			input.setCodidconnectiontokenin(tokenIn);

			// Eseguo il servizio
			Ctrlconnectiontoken service = new Ctrlconnectiontoken();
			StoreResultBean<DmpkUtilityCtrlconnectiontokenBean> output = service.execute(loginBean, input);

			if (output.isInError()) {
				throw new Exception(output.getDefaultMessage());
			}

			// restituisco IdUserOut
			if (output.getResultBean().getIduserout() != null) {
				resOut[0] = output.getResultBean().getIduserout().toString();
			}

			// restituisco IdDominioAutOut
			if (output.getResultBean().getIddominioautout() != null) {
				resOut[1] = output.getResultBean().getIddominioautout().toString();
			}

			// restituisco FlgTpDominioAutOut
			if (output.getResultBean().getFlgtpdominioautout() != null) {
				resOut[2] = output.getResultBean().getFlgtpdominioautout().toString();
			}

			// restituisco CodApplEsternaOut
			if (output.getResultBean().getCodapplesternaout() != null) {
				resOut[3] = output.getResultBean().getCodapplesternaout().toString();
			}

			// restituisco CodIstanzaApplEstOut
			if (output.getResultBean().getCodistanzaapplestout() != null) {
				resOut[4] = output.getResultBean().getCodistanzaapplestout().toString();
			}
		} else {
			aLogger.error("Errore. Il token non e' valorizzato. Impossibile ricavare le informazionid del dominio.");
			throw new Exception("Errore. Il token non e' valorizzato. Impossibile ricavare le informazionid del dominio.");
		}
		return resOut;
	}


	/**
	 * Implementazione predefinita di genera risposta. Restituisce l'xl restituito dalla store.
	 *
	 * @param xmlIn
	 *            Xml restituito dalla store
	 * @return String stringa XML della store
	 */

	public String generaXMLRisposta(String xmlIn) {

		Base64 encoder = new Base64();
		StringBuffer xml = new StringBuffer();

		aLogger.debug("xmlIn = " + xmlIn);

		xml.append(xmlIn);
		
		String risposta = new String(encoder.encode(xml.toString().getBytes()));

		return risposta;
	}
		
		
	/**
	 * Implementazione predefinita di genera risposta. Crea un XML di RisultatoRicerca.
	 *
	 * @param esito
	 *            int 0=KO, 1=OK
	 * @param errCode
	 *            int Se KO, allora e' valorizzato con il retcode specifico
	 * @param errMessage
	 *            Se KO, allora e' valorizzato con il messaggio corrispondente
	 * @param errContext
	 *            Se KO, allora puo' essere valorizzato con il context corrispondente
	 * @param warnMessage
	 *            Puo' essere valorizzato con eventuali warning
	 * @return String stringa XML secondo il formato definito in BaseOutput_WS.xsd
	 */
	public String generaXMLRisposta(int esito, int errCode, String errMessage, String errContext, String warnMessage) {
		Base64 encoder = new Base64();
		StringBuffer xml = new StringBuffer();

		String errMessageEsc = null;

		// controllo che il messaggio non sia nullo
		if (errMessage != null) {
			// effettuo l'escape tranne che dello spazio " "
			errMessageEsc = eng.util.XMLUtil.xmlEscapeEntities(errMessage);
		}
		aLogger.debug("generaXMLRisposta: errMessage = " + errMessage);
		aLogger.debug("generaXMLRisposta: errMessageEsc = " + errMessageEsc);

		xml.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n");
		xml.append("<BaseOutput_WS>\n");
		xml.append("<WSResult>" + esito + "</WSResult>\n");

		// aggiungo il codice di errore se esito !=1
		if (esito != 1) {
			xml.append("<WSError>\n");
			if ((errContext != null) && (!"".equals(errContext))) {
				xml.append("<ErrorContext>" + errContext + "</ErrorContext>\n");
			}
			xml.append("<ErrorNumber>" + errCode + "</ErrorNumber>\n");
			xml.append("<ErrorMessage>" + errMessageEsc + "</ErrorMessage>\n");
			xml.append("</WSError>\n");
		}

		if (warnMessage != null && !warnMessage.equals("")) {
			xml.append("<WarningMessage>" + warnMessage + "</WarningMessage>\n");
		}

		xml.append("</BaseOutput_WS>");

		String risposta = new String(encoder.encode(xml.toString().getBytes()));

		return risposta;
	}

	/**
	 * <code>serviceImplementation</code> Metodo astratto da implementare con ogni estensione (biznez logik!). Se una implementazione ha bisogno di agganciare
	 * allegati lo deve fare da sola.
	 *
	 * @param user
	 *            a <code>String</code> utente che richiede il webservice
	 * @param token
	 *            a <code>String</code> token di sicurezza
	 * @param conn
	 *            a <code>Connection</code> Connessione al document repository
	 * @param xmlDomDoc
	 *            a <code>Document</code> Document XML di input
	 * @param xml
	 *            a <code>String</code> XML di input (con informazioni applicative)
	 * @param schemaDb
	 *            schema del db
	 * @param wsTraceBean 
	 * @return a <code>String</code> XML di risposta
	 * @exception Exception
	 *                Qualcosa pu? sempre andare male...
	 */

	public abstract String serviceImplementation(String user, String token, String codAppl, String istanzaAlppl, Connection conn, Document xmlDomDoc,
			String xml, String schemaDb, String idDominio, String desDominio, String flgTpDominioAut, WSTrace wsTraceBean) throws Exception;

	/**
	 * Metodo che restituisce la stringa URI del file XSD per la verifica della stringa XML
	 * 
	 * @return String
	 */
	protected String getXsdUri() {
		AurigaWebServiceConfigurer awsc = (AurigaWebServiceConfigurer) getApplicationContext().getBean(_SPRING_BEAN_WSCONFIGUER);
		return awsc.getXsdUri() + awsc.getXsdNames().getProperty(this.getClass().getName());
	}

	/**
	 * Metodo che restituisce la stringa che identifica il root node della stringa XML
	 * 
	 * @return String
	 */
	protected String getXMLRootNode() {
		AurigaWebServiceConfigurer awsc = (AurigaWebServiceConfigurer) getApplicationContext().getBean(_SPRING_BEAN_WSCONFIGUER);
		return awsc.getXsdRootTags().getProperty(this.getClass().getName());
	}

	/**
	 * restituisce il version handler che e' stato configurato
	 * 
	 * @return VersionHandler
	 */
	protected VersionHandler getVersionHandler() {
		// reperisco le informazioni per referenziare il DocumentRepositorySingleton
		try {
			return (VersionHandler) getApplicationContext().getBean(_SPRING_BEAN_VERSIONHANDLER);
		} catch (Exception e) {
			aLogger.error(e.getMessage(), e);
			return null;
		}

	}
	
	public VersionHandler getVersionHandlerWS() {
		// reperisco le informazioni per referenziare il DocumentRepositorySingleton
		try {
			return (VersionHandler) getApplicationContext().getBean(_SPRING_BEAN_VERSIONHANDLER);
		} catch (Exception e) {
			aLogger.error(e.getMessage(), e);
			return null;
		}

	}

	/**
	 *
	 * @param clob
	 *            CLOB
	 * @param fileName
	 *            String
	 * @throws SQLException
	 * @throws IOException
	 */
	public void clob2file(CLOB clob, String fileName) throws SQLException, IOException {
		Writer writer = null;
		Reader reader = null;
		try {
			writer = new BufferedWriter(new FileWriter(fileName));
			reader = new BufferedReader(clob.getCharacterStream());
			int length;
			char[] buf = new char[clob.getChunkSize()];
			while ((length = reader.read(buf, 0, clob.getChunkSize())) != -1) {
				writer.write(buf, 0, length);
			}
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (Exception ex1) {
				//
			}

			try {
				if (reader != null) {
					reader.close();
				}
			} catch (Exception ex2) {
				//
			}
		}
	}

	/**
	 *
	 * @param xml
	 *            String
	 * @param strSegn
	 *            String
	 * @param fileXSD
	 *            String
	 * @return Document
	 * @throws SAXException
	 */
	protected Document getDOMDocumentFromXml(String xml, String strSegn, String fileXSD) throws SAXException, IOException {
		// se strSegn==NO_CHK_XML e fileXSD==NO_CHK_XML, allora restituisco null
		if ((NO_CHK_XML.equals(strSegn)) && (NO_CHK_XML.equals(fileXSD)))
			return null;

		// Create a Xerces DOM Parser
		DOMParser parser = new DOMParser();
		StringBuffer xmlBuf = new StringBuffer();

		try {
			parser.setFeature("http://xml.org/sax/features/validation", true);
			parser.setFeature("http://apache.org/xml/features/validation/schema", true);
			parser.setFeature("http://apache.org/xml/features/validation/schema-full-checking", true);
		} catch (SAXNotRecognizedException e) {
			aLogger.error(e);
		} catch (SAXNotSupportedException e) {
			aLogger.error(e);
		}

		// Register Error Handler
		parser.setErrorHandler(this);

		// aggiunto un test sulla presenza della stringa <?xml
		if (xml.indexOf("<?xml") == -1)
			throw new SAXException("Xml malformato, non trovo lo header <?xml ...> all'inizio.");

		// Sostituzione dell'header....
		// Sostituiamo la direttiva <!DOC TYPE e iniziamo dalla segnatura....
		int segn_index_1 = 0;
		String prima = "";
		int segn_index_2 = 0;
		String dopo = "";

		boolean attributiPres = false;
		try {
			segn_index_1 = xml.indexOf("<" + strSegn);
			if (segn_index_1 == -1) {
				// il tag strSegn non è presente
				throw new SAXException("Il tag " + strSegn + " non e' presente...");
			}
			prima = xml.substring(0, (segn_index_1));
			segn_index_2 = xml.indexOf(">", segn_index_1);

			// Elaborazione del tag root, si vuole il seguente comportamento:
			// 1 - prendo tutto quello che sta nel tag root
			// 2 - rimuovo eventuali attributi "xmlns:xsi" e "xsi:noNamespaceSchemaLocation"
			// e setto quelli configurati per questo ws
			// 3 - il risultato viene dato in pasto al parser
			String tmp = xml.substring(segn_index_1, segn_index_2);
			if (tmp.indexOf("xmlns") != -1 && tmp.indexOf("noNamespaceSchemaLocation") != -1) {
				attributiPres = true;
			}

			dopo = xml.substring(segn_index_1 + strSegn.length() + 1);
			if (attributiPres == false) {
				dopo = removeAttribute("xmlns:xsi", dopo);
				dopo = removeAttribute("xsi:noNamespaceSchemaLocation", dopo);
			}

		} catch (IndexOutOfBoundsException excptn) {
			throw new SAXException("Xml malformato, verificare la presenza dello header <?xml ...>");
		}

		if (segn_index_1 >= 0) {
			xmlBuf.append(prima);
			xmlBuf.append("<");
			xmlBuf.append(strSegn);
			if (attributiPres == false) {
				xmlBuf.append(" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"");
				xmlBuf.append(fileXSD);
				xmlBuf.append("\"");
			}
			xmlBuf.append(dopo);
		}

		// carico la stringa xml
		java.io.StringReader sr = new java.io.StringReader(xmlBuf.toString());
		// faccio il parse dell'xml
		parser.parse(new org.xml.sax.InputSource(sr));
		// istanzio l'oggetto DOM Document
		Document document = parser.getDocument();

		return document;
	}

	/**
	 * **************************************************************************
	 *
	 * @param xmlDomDoc
	 * @return InfoUnitaDoc
	 */
	public InfoUnitaDoc getInfoUnitaDoc(Document xmlDomDoc) throws org.xml.sax.SAXException {
		InfoUnitaDoc ris = null;
		NodeList nlEstremi = null;

		try {
			XPathHelper xpathHelper = new XPathHelper();
			// Cerco in eventuale doc. elettronico principale
			nlEstremi = xpathHelper.processXPath("//EstremiXIdentificazioneUD", xmlDomDoc.getDocumentElement());
			if (nlEstremi.getLength() > 0) {
				ris = new InfoUnitaDoc();
				Node nd = nlEstremi.item(0);

				String strIdUD = xpathHelper.processXPathStringValue("IdUD", nd);
				String strCategoriaReg = xpathHelper.processXPathStringValue("//EstremiRegNum/CategoriaReg", nd);
				String strSiglaReg = xpathHelper.processXPathStringValue("//EstremiRegNum/SiglaReg", nd);
				String strAnnoReg = xpathHelper.processXPathStringValue("//EstremiRegNum/AnnoReg", nd);
				String strNumReg = xpathHelper.processXPathStringValue("//EstremiRegNum/NumReg", nd);

				if (!strIdUD.equals("")) {
					ris.setIdUD(xpathHelper.processXPathStringValue("IdUD", nd));
				}
				ris.setCategoriaReg(xpathHelper.processXPathStringValue("//EstremiRegNum/CategoriaReg", nd));
				ris.setSiglaReg(xpathHelper.processXPathStringValue("//EstremiRegNum/SiglaReg", nd));
				if (!strAnnoReg.equals("")) {
					ris.setAnnoReg(xpathHelper.processXPathStringValue("//EstremiRegNum/AnnoReg", nd));
				}
				if (!strNumReg.equals("")) {

					ris.setNumReg(xpathHelper.processXPathStringValue("//EstremiRegNum/NumReg", nd));
				}

				return ris;
			}
		} catch (Exception ex) {
			aLogger.error(ex.getMessage(), ex);
		}

		return ris;
	}

	public void warning(SAXParseException e) throws SAXException {
		aLogger.error("Warning:  " + e);

		throw new SAXException("Warning:  " + e);
	}

	public void error(SAXParseException e) throws SAXException {
		aLogger.error("Error:  " + e);

		throw new SAXException("Error:  " + e);
	}

	public void fatalError(SAXParseException e) throws SAXException {
		aLogger.error("Fatal Error:  " + e);
		throw new SAXException("Fatal Error:  " + e);
	}

	/**
	 * Get the <code>XpathHelper</code> value.
	 *
	 * @return a <code>XPathHelper</code> ritorna un riferimento all'helper per XPath.
	 */
	// rimuove l'attributo attributo="xyz" nel primo presente tag in str
	private static String removeAttribute(String attribute, String str) {

		String tmp = null;
		String prima = null;
		int index1 = -1;
		attribute = " " + attribute;
		try {
			int i = str.indexOf(attribute);
			index1 = str.indexOf('>');
			if (i > index1)
				return str;
			prima = str.substring(0, i);
			tmp = str.substring(i);
			i = tmp.indexOf('"');
			tmp = tmp.substring(i + 1);
			i = tmp.indexOf('"');
			tmp = tmp.substring(i + 1);
			return prima + tmp;
		} catch (Exception e) {
			return str;
		}
	}

	/**
	 *
	 * @return String
	 */
	public static String getStrDate() {
		Calendar c = Calendar.getInstance();

		int m = c.get(Calendar.MONTH);
		int d = c.get(Calendar.DATE);
		int h = c.get(Calendar.HOUR_OF_DAY);
		int mi = c.get(Calendar.MINUTE);
		int ms = c.get(Calendar.MILLISECOND);
		String mm = Integer.toString(m);
		String dd = Integer.toString(d);
		String hh = Integer.toString(h);
		String mmi = Integer.toString(mi);
		String mms = Integer.toString(ms);

		String repDt = c.get(Calendar.YEAR) + (m < 10 ? "0" + mm : mm) + (d < 10 ? "0" + dd : dd);
		String repOra = (h < 10 ? "0" + hh : hh) + (mi < 10 ? "0" + mmi : mmi) + (ms < 10 ? "0" + mms : mms);

		return repDt + repOra;
	}

	// Metodi di utilit? per generare una stringa pseudocasuale di 20
	// cifre esadecimali

	// final e static perch?: 1) sono cos? e non server l'override
	// 2) ci guadagni un zinzino.
	public final static String randomHexString() {
		StringBuffer nmfl = new StringBuffer();
		byte casuali[] = new byte[10];
		new Random().nextBytes(casuali);
		for (int jj = 0; jj < casuali.length; jj++) {
			nmfl.append(upper(casuali[jj]));
			nmfl.append(lower(casuali[jj]));
		}
		return nmfl.toString();
	}

	private static final String[] hexdigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };

	private final static String upper(byte ottetto) {
		return hexdigits[((ottetto + 128) & 0xF0) >> 4];
	}

	private final static String lower(byte ottetto) {
		return hexdigits[((ottetto + 128) & 0x0F)];
	}

	private boolean isDebugMode() {
		AurigaWebServiceConfigurer awsc = (AurigaWebServiceConfigurer) getApplicationContext().getBean(_SPRING_BEAN_WSCONFIGUER);
		return awsc.getIsDebugMode();
	}

	public List<RebuildedFile> popolo_info_file(AurigaLoginBean pAurigaLoginBean, DataHandler[] handlerIn) throws Exception {
		List<RebuildedFile> listRebuildedFileOut = new ArrayList<RebuildedFile>();

		if (handlerIn != null && handlerIn.length>0) {
			
			WSAddUdConfig wsAddUdConfig = (WSAddUdConfig) SpringAppContext.getContext().getBean(WSAddUd._SPRING_BEAN_WSADDUDCONFIG);
	    	boolean isVerificaCommentiAttiva = false;
	    	boolean isVerificaEditabiliAttiva = false;
	    	if( wsAddUdConfig!= null && wsAddUdConfig.getAbilitaPdfComenti()!=null && 
	    			wsAddUdConfig.getAbilitaPdfComenti().equalsIgnoreCase("true")){
	    		aLogger.debug("La verifica commenti e' attiva");
	    		isVerificaCommentiAttiva = true;
	    	} else {
	    		aLogger.debug("La verifica commenti non e' attiva");
	    	}
	    	if( wsAddUdConfig!= null && wsAddUdConfig.getAbilitaPdfEditabili()!=null && 
	    			wsAddUdConfig.getAbilitaPdfEditabili().equalsIgnoreCase("true")){
	    		aLogger.debug("La verifica editabili e' attiva");
	    		isVerificaEditabiliAttiva = true;
	    	} else {
	    		aLogger.debug("La verifica editabili non e' attiva");
	    	}
			
			for (int i = 0; i < handlerIn.length; i++) {
				// Salvo attach in un file temp
				WSFileUtils lWSFileUtils = new WSFileUtils();
				File fileAttachTemp = lWSFileUtils.saveInputStreamToStorageTmp(pAurigaLoginBean.getSpecializzazioneBean().getIdDominio(),
						handlerIn[i].getInputStream());

				// Popolo le info del file 1
				RebuildedFile lRebuildedFile1 = new RebuildedFile();
				GenericFile lGenericFile1 = new GenericFile();
				InfoGenericFile lInfoGenericFile1 = new InfoGenericFile();
				FileInfoBean lFileInfoBean1 = new FileInfoBean();

				lRebuildedFile1.setFile(fileAttachTemp);
				lGenericFile1 = lInfoGenericFile1.get(lRebuildedFile1, "");
				
				
				
				aLogger.debug("Il file ha commenti? " + lGenericFile1.getPdfConCommenti());
				if( lGenericFile1.getPdfConCommenti()!=null && lGenericFile1.getPdfConCommenti().equals(Flag.SETTED)
						&& isVerificaCommentiAttiva && 
						 (lGenericFile1.getFirmato()==null ||	lGenericFile1.getFirmato().equals(Flag.NOT_SETTED))){
					
					List<Integer> listaPagineConCommenti = PdfCommentiUtil.returnPagesWithComment(fileAttachTemp);
					aLogger.debug("listaPagineConCommenti " + listaPagineConCommenti);
					
					File fileConvertito = PdfCommentiUtil.staticizzaFileConCommenti(fileAttachTemp, listaPagineConCommenti);
					aLogger.debug("File convertito " + fileConvertito);
					
					if( fileConvertito!=null) {
						lRebuildedFile1.setFile(fileConvertito);
					
						lGenericFile1 = lInfoGenericFile1.get(lRebuildedFile1, "");
					}
				}
				aLogger.debug("Il file e' editabile? " + lGenericFile1.getPdfEditabile());
				if( lGenericFile1.getPdfEditabile()!=null && lGenericFile1.getPdfEditabile().equals(Flag.SETTED)
						&& isVerificaEditabiliAttiva && 
						(lGenericFile1.getFirmato()==null ||	lGenericFile1.getFirmato().equals(Flag.NOT_SETTED))){
					
					if(!PdfEditabiliUtil.checkEditableFileWithXfaForm(fileAttachTemp.getAbsolutePath())) {
						
						aLogger.debug("Il file non ha xForm " );
						File fileStaticizzato = PdfEditabiliUtil.staticizzaPdfEditabile(fileAttachTemp );
						aLogger.debug("Il file non ha xForm - fileStaticizzato " + fileStaticizzato);
						
						if( fileStaticizzato!=null ){
							lRebuildedFile1.setFile(fileStaticizzato);
							
							lGenericFile1 = lInfoGenericFile1.get(lRebuildedFile1, "");
						}
						
					} else {
						aLogger.debug("Il file  ha xForm  " );
						String pathFileStaticizzato = PdfEditabiliUtil.staticizzaPdfConXfaForm( fileAttachTemp.getAbsolutePath() );
						File fileStaticizzato = new File(pathFileStaticizzato);
						aLogger.debug("Il file ha xForm - fileStaticizzato " + fileStaticizzato);
						
						if(fileStaticizzato!=null) {
							lRebuildedFile1.setFile(fileStaticizzato);
							
							lGenericFile1 = lInfoGenericFile1.get(lRebuildedFile1, "");
						}
					}
					
				}
				
				
				
				if (i == 0) {
					lFileInfoBean1.setTipo(TipoFile.PRIMARIO);
				} else {
					lFileInfoBean1.setTipo(TipoFile.ALLEGATO);
				}
				lFileInfoBean1.setAllegatoRiferimento(lGenericFile1);
				lRebuildedFile1.setInfo(lFileInfoBean1);
				listRebuildedFileOut.add(lRebuildedFile1);
			}
		}

		return listRebuildedFileOut;
	}
	
	public List<AttachWSBean> calcola_info_file_multiThreaded(AurigaLoginBean pAurigaLoginBean, List<File> listaAttach, boolean flgImpresaInUnGiorno, String xml, String maxSizePoolThreadConfig)
			throws Exception {

		List<AttachWSBean> listaAttachWithInfo = new ArrayList<AttachWSBean>();
		List<Callable<AttachWSBean>> threadList = new ArrayList<>();
		ExecutorService executorService = null;

		try {
			for (int i = 0; i < listaAttach.size(); i++/* File fileAttach : listaAttach */) {
				File fileAttach = listaAttach.get(i);

				CallInfoFileThread callInfoFileThread = new CallInfoFileThread(flgImpresaInUnGiorno, xml, i, fileAttach, pAurigaLoginBean);

				threadList.add(callInfoFileThread);
			}			
			
			/**
			 *  INIZIO CHIAMATE MULTITHREAD A FILEOP PER CALCOLARE LE INFO DEI FILE
			 */
			
			int maxSizePoolThread;
			if(StringUtils.isNotBlank(maxSizePoolThreadConfig)) {
				maxSizePoolThread = Integer.valueOf(maxSizePoolThreadConfig);
			}else {
				maxSizePoolThread = DEFAULT_MAXSIZE_POOL_THREAD;
			}
			
			executorService = Executors.newFixedThreadPool(maxSizePoolThread);

			List<Future<AttachWSBean>> results = executorService.invokeAll(threadList);
			for (Future<AttachWSBean> f : results) {
				listaAttachWithInfo.add(f.get());
			}
			
			/**
			 * TUTTE LE CHIAMATE SONO STATE ESEGUITE
			 * */
		

		} catch (Exception e) {
			throw new Exception("ERRORE nella funzione InfoGenericFile = " + e.getMessage());
		}finally {
			executorService.shutdown();
		}

		return listaAttachWithInfo;
	}
	
	public List<AttachWSBean> calcola_info_file(AurigaLoginBean pAurigaLoginBean, List<File> listaAttach, boolean flgImpresaInUnGiorno, String xml)
			throws Exception {
		List<AttachWSBean> listaAttachWithInfo = new ArrayList<AttachWSBean>();
		
			try {
				for (int i = 0; i<listaAttach.size(); i++/*File fileAttach : listaAttach*/) {
					File fileAttach = listaAttach.get(i);
					
					AttachWSBean attachWSBean = AddUdUtils.buildAttachWSBean(fileAttach, xml, i, flgImpresaInUnGiorno, pAurigaLoginBean);
					
					boolean isValid = AddUdUtils.checkRequiredAttribute(fileAttach.getName(), attachWSBean);
					
					if(!isValid) {
						AddUdUtils.retryCallFileOp(fileAttach, xml, i, flgImpresaInUnGiorno, pAurigaLoginBean);
					}

					listaAttachWithInfo.add(attachWSBean);
				}

			} catch (Exception e) {
				throw new Exception("ERRORE nella funzione InfoGenericFile = " + e.getMessage());
			}

		

		return listaAttachWithInfo;
	}
	
	public List<File> decomprimi_fileImpresaInUnGiorno(AurigaLoginBean pAurigaLoginBean, DataHandler[] handlerIn)
			throws Exception {
		List<File> filesArchivio = new ArrayList<File>();
		if (handlerIn != null) {
			InputStream isFileZip = handlerIn[0].getInputStream();

			File zipTemp = File.createTempFile("zipTemp", ".zip");
			FileUtils.copyInputStreamToFile(isFileZip, zipTemp);
			filesArchivio = spacchettaZipSue(zipTemp);
			zipTemp.delete();
		}
		return filesArchivio;
	}

	public List<RebuildedFile> popolo_info_fileImpresaInUnGiorno(AurigaLoginBean pAurigaLoginBean, List<File> filesArchivio) throws Exception {
		List<RebuildedFile> listRebuildedFileOut = new ArrayList<RebuildedFile>();

		if (filesArchivio != null) {
			aLogger.info("Numero di file interno all'archivio zip  " + filesArchivio.size());
			if (filesArchivio.size() > 1) {
				for (File fileArchivio : filesArchivio) {
					RebuildedFileStored lRebuildedFile = new RebuildedFileStored();
					InfoGenericFile lInfoGenericFile = new InfoGenericFile();
					GenericFile lGenericFile = new GenericFile();
					FileInfoBean lFileInfoBean = new FileInfoBean();
					lFileInfoBean.setTipo(TipoFile.ALLEGATO);
					lRebuildedFile.setFile(fileArchivio);

					String uriVer = DocumentStorage.store(fileArchivio, pAurigaLoginBean.getSpecializzazioneBean().getIdDominio());
					aLogger.info("File salvato sullo storage in " + uriVer);
					lRebuildedFile.setUriStorage(uriVer);

					File fileStorage = DocumentStorage.extract(uriVer, pAurigaLoginBean.getSpecializzazioneBean().getIdDominio());
					lRebuildedFile.setFile(fileStorage);
					
					lGenericFile = lInfoGenericFile.get(lRebuildedFile, "");
					lGenericFile.setDisplayFilename(fileArchivio.getName());
					aLogger.info("Il file sara' archiviato come allegato " + fileArchivio.getName());
					lFileInfoBean.setAllegatoRiferimento(lGenericFile);
					lRebuildedFile.setInfo(lFileInfoBean);
					listRebuildedFileOut.add(lRebuildedFile);
				}

			} else if (filesArchivio.size() == 1) {
				File fileArchivio = filesArchivio.get(0);
				RebuildedFileStored lRebuildedFile = new RebuildedFileStored();
				InfoGenericFile lInfoGenericFile = new InfoGenericFile();
				GenericFile lGenericFile = new GenericFile();
				FileInfoBean lFileInfoBean = new FileInfoBean();
				lFileInfoBean.setTipo(TipoFile.PRIMARIO);
				lRebuildedFile.setFile(fileArchivio);

				String uriVer = DocumentStorage.store(fileArchivio, pAurigaLoginBean.getSpecializzazioneBean().getIdDominio());
				aLogger.info("File salvato sullo storage in " + uriVer);
				lRebuildedFile.setUriStorage(uriVer);
				
				File fileStorage = DocumentStorage.extract(uriVer, pAurigaLoginBean.getSpecializzazioneBean().getIdDominio());
				lRebuildedFile.setFile(fileStorage);

				lGenericFile = lInfoGenericFile.get(lRebuildedFile, "");
				lGenericFile.setDisplayFilename(fileArchivio.getName());
				aLogger.info("Il file sara' archiviato come file primario " + fileArchivio.getName());
				lFileInfoBean.setAllegatoRiferimento(lGenericFile);
				lRebuildedFile.setInfo(lFileInfoBean);
				listRebuildedFileOut.add(lRebuildedFile);
			}
		}

		return listRebuildedFileOut;
	}

	public RebuildedFile popolo_info_file_singolo(AurigaLoginBean pAurigaLoginBean, DataHandler handlerIn, String idUdIn, String idDocFileIn) throws Exception {
		String flgTipoFile = "";
		BigDecimal idDocFileDecimal = new BigDecimal(0);
		RebuildedFile rebuildedFileOut = new RebuildedFile();

		if (handlerIn != null) {
			// Salvo attach in un file temp
			WSFileUtils lWSFileUtils = new WSFileUtils();
			File fileAttachTemp = lWSFileUtils.saveInputStreamToStorageTmp(pAurigaLoginBean.getSpecializzazioneBean().getIdDominio(),
					handlerIn.getInputStream());

			// Popolo le info del file 1
			GenericFile lGenericFile1 = new GenericFile();
			InfoGenericFile lInfoGenericFile1 = new InfoGenericFile();
			FileInfoBean lFileInfoBean1 = new FileInfoBean();

			rebuildedFileOut.setFile(fileAttachTemp);
			lGenericFile1 = lInfoGenericFile1.get(rebuildedFileOut, "");
			
			aLogger.debug("Verifico i commenti " + lGenericFile1.getPdfConCommenti());
			if( lGenericFile1.getPdfConCommenti()!=null && lGenericFile1.getPdfConCommenti().equals(Flag.SETTED)
					/*&& parametroAttivo*/){
				
				aLogger.debug("Chiamo listaPagineConCommenti" );
				List<Integer> listaPagineConCommenti = PdfCommentiUtil.returnPagesWithComment(fileAttachTemp);
				aLogger.debug("Chiamato listaPagineConCommenti " + listaPagineConCommenti);
				
				aLogger.debug("Chiamo convertPagesPdfToPdfImages " );
				File fileConvertito = PdfCommentiUtil.staticizzaFileConCommenti(fileAttachTemp, listaPagineConCommenti);
				aLogger.debug("Chiamato convertPagesPdfToPdfImages " + fileConvertito);
				
				rebuildedFileOut.setFile(fileConvertito);
				
				lGenericFile1 = lInfoGenericFile1.get(rebuildedFileOut, "");
				
			}
			aLogger.debug("Verifico gli editabili " + lGenericFile1.getPdfEditabile());
			if( lGenericFile1.getPdfEditabile()!=null && lGenericFile1.getPdfEditabile().equals(Flag.SETTED)
					/*&& parametroAttivo*/){
				
				if(!PdfEditabiliUtil.checkEditableFileWithXfaForm(fileAttachTemp.getAbsolutePath())) {
					
					aLogger.debug("Il file non ha xForm - chiamo staticizzaPdf " );
					File fileStaticizzato = PdfEditabiliUtil.staticizzaPdfEditabile( fileAttachTemp );
					aLogger.debug("Il file non ha xForm - chiamato staticizzaPdf " + fileStaticizzato);
					
					if( fileStaticizzato!=null ){
						rebuildedFileOut.setFile(fileStaticizzato);
						
						lGenericFile1 = lInfoGenericFile1.get(rebuildedFileOut, "");
					}
					
				} else {
					aLogger.debug("Il file  ha xForm - chiamo staticizzaPdfConXfaForm " );
					String pathFileStaticizzato = PdfEditabiliUtil.staticizzaPdfConXfaForm( fileAttachTemp.getAbsolutePath() );
					File fileStaticizzato = new File(pathFileStaticizzato);
					aLogger.debug("Il file ha xForm - chiamato staticizzaPdf " + fileStaticizzato);
					
					if(fileStaticizzato!=null) {
						rebuildedFileOut.setFile(fileStaticizzato);
						
						lGenericFile1 = lInfoGenericFile1.get(rebuildedFileOut, "");
					}
				}
				
			}
			

			// Leggo le info del file elettronico per capire se e' un PRIMARIO o ALLEGATO
			try {
				GestioneDocumenti servizio = new GestioneDocumenti();

				BigDecimal idDocPrimario = servizio.leggiIdDocPrimarioWS(pAurigaLoginBean, idUdIn);

				if (idDocFileIn != null && !idDocFileIn.equalsIgnoreCase("")) {
					idDocFileDecimal = new BigDecimal(idDocFileIn);
				}

				if (idDocPrimario != null && idDocPrimario.compareTo(idDocFileDecimal) == 0) {
					lFileInfoBean1.setTipo(TipoFile.PRIMARIO);
				} else {
					lFileInfoBean1.setTipo(TipoFile.ALLEGATO);
				}

			} catch (Exception ve) {
				String mess = "------> Fallita la ricerca del iDDocPrimario  per idUd = " + idUdIn + ", ERRORE = " + ve.getMessage();
				aLogger.debug(mess + " - " + ve.getMessage(), ve);
				throw new Exception(mess);
			}

			lFileInfoBean1.setAllegatoRiferimento(lGenericFile1);
			rebuildedFileOut.setInfo(lFileInfoBean1);
		}

		return rebuildedFileOut;
	}

	// Controllo se le informazioni degli allagti sono corrette
	public String verificaAllegati(DataHandler[] attachments, Lista lsDocAttXmlOut) throws Exception {
		String errori = "";

		String fileName = "";

		boolean allegatoPresenteInOutput[] = new boolean[attachments.length];

		try {
			for (int i = 0; i < lsDocAttXmlOut.getRiga().size(); i++) {
				// prendo la riga i-esima
				Riga r = lsDocAttXmlOut.getRiga().get(i);
				String attachNum = "";
				String nome = "";
				for (int j = 0; j < r.getColonna().size(); j++) {
					int num = 0;
					Colonna c = r.getColonna().get(j);
					String val = c.getContent();
					if (c != null)
						num = c.getNro().intValue();

					// -- 2: N.ro dell'attach del messaggio SOAP che corrisponde al file da caricare nella repository
					if (num == 2) {
						attachNum = val;
					}
					// -- 4: Nome del file da caricare (quello con cui mostrarlo e dalla cui estensione si ricava il formato)
					else if (num == 4) {
						nome = val;
					}
				}
				// prendo l'indice dell'attachment
				Integer indAtt = null;

				// indice in formato int
				int index;

				// provo a convertire in int l'indice estratto
				try {
					indAtt = new java.lang.Integer(attachNum);
					index = indAtt.intValue();
				} catch (Throwable ee) {
					aLogger.error("Errore nella conversione dell'indice dell'allegato");
					errori += "Errore nella conversione dell'indice dell'allegato\n";
					continue;
				}

				// prendo l'attach indicato dalla store procedure
				try {
					fileName = attachments[index - 1].getName(); // -- 4: Nome del file da caricare
				}

				// se l'attach e' sbagliato considero il documento come non caricato e passo oltre
				catch (Exception e) {
					aLogger.error("Non esiste un allegato all'indice " + index);
					errori += "Non esiste un allegato all'indice " + index + "\n";
					continue;
				}

				if (allegatoPresenteInOutput[index - 1]) {
					aLogger.error("Allegato all'indice " + index + " duplicato nell'output della store");
					errori += "Allegato all'indice " + index + " duplicato nell'output della store\n";
					continue;
				}
				// tengo traccia del fatto che l'allegato e' considerato nell'ouput della store
				allegatoPresenteInOutput[index - 1] = true;
			}

			// controllo che tutti gli attach con file fisico siano stati considerati
			for (int i = 0; i < allegatoPresenteInOutput.length; i++) {
				if (!allegatoPresenteInOutput[i]) {
					// allegato i-esimo non considerato: ha un file fisico?
					fileName = attachments[i].getName();
					// controllo il nome del file estratto
					if (fileName != null && !fileName.equals("")) {
						// file fisico presente --> errore
						aLogger.error("Allegato all'indice " + i + " non considerato");
						errori += "Allegato all'indice " + i + " non considerato\n";
					}
				}
			}

		} catch (Throwable ee) {
			errori += "Errore nella funzione nella verificaAllegati() - " + ee.getMessage() + "\n";
			aLogger.error(errori);
			throw new Exception(errori);
		}

		return errori;

	}

	public AllegatiBean popoloAllegatiBean(RebuildedFile lRebuildedFile, String nome, String docId, String note) throws Exception {

		AllegatiBean lAllegatiBeanOut = new AllegatiBean();
		List<File> lFileAllegati = new ArrayList<File>();
		List<String> lDescrizione = new ArrayList<String>();
		List<Integer> lDocType = new ArrayList<Integer>();
		List<String> lDisplayFilename = new ArrayList<String>();
		List<BigDecimal> lIdDocumento = new ArrayList<BigDecimal>();
		List<Boolean> lIsNull = new ArrayList<Boolean>();
		List<Boolean> lIsNewOrChanged = new ArrayList<Boolean>();
		List<FileInfoBean> lInfo = new ArrayList<FileInfoBean>();
		List<Boolean> lFlgParteDispositivo = new ArrayList<Boolean>();
		List<String> lIdTask = new ArrayList<String>();
		List<Boolean> lFlgNoPubbl = new ArrayList<Boolean>();
		List<Boolean> lFlgPubblicaSeparato = new ArrayList<Boolean>();
		List<String> lUriFile = new ArrayList<String>();
		List<Boolean> lFlgDaFirmare = new ArrayList<Boolean>();
		List<Boolean> lFlgSostituisciVerPrec = new ArrayList<Boolean>();

		// -- 4: Nome del file da caricare
		lDisplayFilename.add(nome);

		// -- 3: Identificativo del documento creato in DB in corrispondenza dell'attachment
		if (docId != null) {
			lIdDocumento.add(new BigDecimal(docId));
		}

		// Inserisco le info del file
		FileInfoBean lFileInfoBean = new FileInfoBean();
		lFileInfoBean.setTipo(lRebuildedFile.getInfo().getTipo());

		GenericFile lAllegatoRiferimento = new GenericFile();

		lAllegatoRiferimento = lRebuildedFile.getInfo().getAllegatoRiferimento();
		lAllegatoRiferimento.setDisplayFilename(nome);
		lFileInfoBean.setAllegatoRiferimento(lAllegatoRiferimento);

		lFileInfoBean.setPosizione(lRebuildedFile.getPosizione());
		lInfo.add(lFileInfoBean);
		lDescrizione.add(note);
		lIsNull.add(false);
		lIsNewOrChanged.add(true);
		lFileAllegati.add(lRebuildedFile.getFile());

		// Flag per eliminare la versione precedente
		lFlgSostituisciVerPrec.add(lRebuildedFile.getAnnullaLastVer());

		// Aggiorno il bean
		lAllegatiBeanOut.setDescrizione(lDescrizione);
		lAllegatiBeanOut.setDisplayFilename(lDisplayFilename);
		lAllegatiBeanOut.setDocType(lDocType);
		lAllegatiBeanOut.setFileAllegati(lFileAllegati);
		lAllegatiBeanOut.setFlgDaFirmare(lFlgDaFirmare);
		lAllegatiBeanOut.setFlgNoPubbl(lFlgNoPubbl);
		lAllegatiBeanOut.setFlgPubblicaSeparato(lFlgPubblicaSeparato);
		lAllegatiBeanOut.setFlgParteDispositivo(lFlgParteDispositivo);
		lAllegatiBeanOut.setIdDocumento(lIdDocumento);
		lAllegatiBeanOut.setIdTask(lIdTask);
		lAllegatiBeanOut.setInfo(lInfo);
		lAllegatiBeanOut.setIsNewOrChanged(lIsNewOrChanged);
		lAllegatiBeanOut.setIsNull(lIsNull);
		lAllegatiBeanOut.setUriFile(lUriFile);

		lAllegatiBeanOut.setFlgSostituisciVerPrec(lFlgSostituisciVerPrec);

		return lAllegatiBeanOut;
	}

	public WSAttachBean getAttachment(AurigaLoginBean loginBean) throws Exception {
		WSAttachBean WSAttachBeanOut = new WSAttachBean();

		// Leggo gli attach
		DataHandler[] attachments = getMessageDataHandlers();

		// Leggo le info degli attach
		List<RebuildedFile> listRebuildedFile = popolo_info_file(loginBean, attachments);

		WSAttachBeanOut.setAttachments(attachments);
		WSAttachBeanOut.setListRebuildedFile(listRebuildedFile);

		return WSAttachBeanOut;

	}
	
	protected List<File> getFileFromDataHandler(DataHandler[] attachments) throws IOException {
		List<File> listaAttach = new ArrayList<File>();
		
		for(DataHandler dataHandlerAttach : attachments) {
			File tempFile = File.createTempFile("tmp","");
			FileUtils.copyInputStreamToFile(dataHandlerAttach.getInputStream(), tempFile);
			
			listaAttach.add(tempFile);
		}
		
		return listaAttach;
	}

	public DataHandler[] getAttachmentImpresaInUnGiorno(AurigaLoginBean loginBean, String xml, String pathFtp) throws Exception {
		//WSAttachBean WSAttachBeanOut = new WSAttachBean();

		DataHandler[] attachments;
		
		String attachOnFtp = AddUdUtils.checkAttachOnFtp(xml);
		
		if(StringUtils.isNotBlank(attachOnFtp)) {
			//Recupero il file zip contenente gli allegati, che è stato caricato sull ftp montata (pathAttach)
			File allegatiZip = new File(pathFtp+attachOnFtp);
			
			attachments = new DataHandler[1];
			attachments[0] = new DataHandler(new InputStreamDataSource(new FileInputStream(allegatiZip), attachOnFtp));
			aLogger.info("attachments length " + attachments.length);
		}else {
			// Leggo gli attach
			attachments = getMessageDataHandlers();
			aLogger.info("attachments length " + attachments.length);
		}
		
		return attachments;
	}

		// Leggo le info degli attach
//		List<RebuildedFile> listRebuildedFile = popolo_info_fileImpresaInUnGiorno(loginBean, attachments);
//
//		WSAttachBeanOut.setAttachments(attachments);
//		WSAttachBeanOut.setListRebuildedFile(listRebuildedFile);
//
//		return WSAttachBeanOut;
//	}

	public WSAttachBean getAttachment_file_singolo(AurigaLoginBean loginBean, String idUdIn, String idDocFileIn) throws Exception {
		WSAttachBean WSAttachBeanOut = new WSAttachBean();

		// Leggo gli attach
		DataHandler[] attachments = getMessageDataHandlers();

		if (attachments.length > 0) {
			// Leggo il primo
			DataHandler dataHandler1 = attachments[0];

			DataHandler[] attachmentsNew = new DataHandler[1];

			// Restituisco il primo
			attachmentsNew[0] = dataHandler1;

			// Leggo le info degli attach
			RebuildedFile rebuildedFile = popolo_info_file_singolo(loginBean, dataHandler1, idUdIn, idDocFileIn);

			List<RebuildedFile> listRebuildedFile = new ArrayList<RebuildedFile>();
			listRebuildedFile.add(rebuildedFile);

			WSAttachBeanOut.setAttachments(attachmentsNew);
			WSAttachBeanOut.setListRebuildedFile(listRebuildedFile);
		}

		return WSAttachBeanOut;
	}

	public String checkMimeTypeAttach(WSAttachBean WSAttachBeanIn) throws Exception {
		String errori = "";
		List<RebuildedFile> listRebuildedFile = new ArrayList<RebuildedFile>();
		try {
			// Leggo la lista degli attach
			listRebuildedFile = WSAttachBeanIn.getListRebuildedFile();

			// Per ogni attach verfico se il mimetype esiste

			if (listRebuildedFile != null && listRebuildedFile.size() > 0) {
				for (int i = 0; i < listRebuildedFile.size(); i++) {

					// Leggo n-esimo attach
					RebuildedFile rebuildedFileBean = new RebuildedFile();
					rebuildedFileBean = listRebuildedFile.get(i);

					// Leggo le info
					FileInfoBean info = new FileInfoBean();
					info = rebuildedFileBean.getInfo();

					// Leggo il mimetype e il nome del file
					GenericFile allegatoRiferimento = new GenericFile();
					allegatoRiferimento = info.getAllegatoRiferimento();
					String mimetype = allegatoRiferimento.getMimetype();

					if (mimetype == null || mimetype.equalsIgnoreCase("")) {
						errori = "E' presente un file elettronico il cui mimetype e' sconosciuto\n";
					}
				}
			}
		} catch (Throwable ee) {
			errori += "Errore nella funzione nella checkMimeTypeAttach() - " + ee.getMessage() + "\n";
			aLogger.error(errori);
			throw new Exception(errori);
		}
		return errori;
	}
	
	private String getNomeFile(String xml, int numAttach) throws SAXException, IOException, ParserConfigurationException{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(xml));
		Document document = builder.parse(is);
		
		NodeList nList = document.getElementsByTagName("VersioneElettronica");
        String nomeFile = null;
		for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            aLogger.info("Current Element :" + nNode.getNodeName());
            
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
               Element eElement = (Element) nNode;
               String numeroAttachXml = eElement.getElementsByTagName("NroAttachmentAssociato").item(0).getTextContent();
               aLogger.info("---NroAttachmentAssociato " + numeroAttachXml);
               String nomeFileXml = eElement.getElementsByTagName("NomeFile").item(0).getTextContent();
               aLogger.info("---NomeFile " + nomeFileXml);
               if( numeroAttachXml!=null && numeroAttachXml.equalsIgnoreCase(""+numAttach)){
            	   nomeFile = nomeFileXml;
               }
            }
         }
		return nomeFile;
	}
	
//	protected void deleteTempFiles(List<File> listaFile) {
//		for(File file : listaFile) {
//			file.delete();
//		}		
//	}
	
	protected void deleteTempFiles(List<File> listaTempFile) {
		for (File file : listaTempFile) {
			if (file.isDirectory()) {
				File[] files = file.listFiles();
				if (files != null) {
					for (File f : files) {
						if (f.isDirectory()) {
							List<File> listRecursiveFile = new ArrayList<>();
							listaTempFile.add(f);
							deleteTempFiles(listRecursiveFile);
						} else {
							f.delete();
						}
					}
				}
			}
			file.delete();
		}
	}
	
public List<File> spacchettaZipSue(File file) throws Exception {
		
		List<File> listaFileUnzippati = new ArrayList<File>();

		try {
			FileInputStream inputStream = new FileInputStream(file.getAbsolutePath());
			ZipInputStream zipStream = new ZipInputStream(inputStream);
			ZipEntry zEntry = null;
			
			String pathtempFolderFilesZip = file.getParent() + File.separator + UUID.randomUUID().toString().replace("-", "");
			Path path = Paths.get(pathtempFolderFilesZip);
			Files.createDirectories(path);
//			listTempFileToDelete.add(new File(pathtempFolderFilesZip));
			
			while ((zEntry = zipStream.getNextEntry()) != null) {
			
				File fileEstratto = new File(pathtempFolderFilesZip + File.separator + zEntry.getName());
				

				FileOutputStream fout = new FileOutputStream(fileEstratto);
				BufferedOutputStream bufout = new BufferedOutputStream(fout);
				byte[] buffer = new byte[1024];
				int read = 0;
				while ((read = zipStream.read(buffer)) != -1) {
					bufout.write(buffer, 0, read);
				}

				zipStream.closeEntry();
				bufout.close();
				fout.close();

				listaFileUnzippati.add(fileEstratto);

			}
			zipStream.close();
		} catch (Exception e) {
			aLogger.error("Errore durante lo spacchettamento dello zio: " + e.getMessage(), e);
			throw new Exception("E' avvenuto un errore durante lo spacchettamento dello zip");
	    }
	    
	    return listaFileUnzippati;

	}
	
	/**
	 * 
	 * @return il valore dello schemaDB passato nell'header della request, null se non esiste. 
	 */
	private String retrieveSchemaFromHeader() {
		if(context != null && context.getMessageContext() != null) {
			Headers headers = (Headers) context.getMessageContext().get(MessageContext.HTTP_REQUEST_HEADERS);
			if(headers.containsKey(HEADER_SCHEMA_DB)) {
				return headers.getFirst(HEADER_SCHEMA_DB);
			}
		} 
		return null;
	}
}