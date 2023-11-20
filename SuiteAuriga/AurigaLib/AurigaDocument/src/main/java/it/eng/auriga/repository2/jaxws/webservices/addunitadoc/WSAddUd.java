/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.activation.DataHandler;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.ws.soap.MTOM;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.exolab.castor.xml.Unmarshaller;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreAdddocBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreAddverdocBean;
import it.eng.auriga.database.store.dmpk_core.store.impl.AdddocImpl;
import it.eng.auriga.database.store.dmpk_core.store.impl.AddverdocImpl;
import it.eng.auriga.database.store.dmpk_login.bean.DmpkLoginMarktokenusageBean;
import it.eng.auriga.database.store.dmpk_login.store.impl.MarktokenusageImpl;
import it.eng.auriga.database.store.dmpk_registrazionedoc.bean.DmpkRegistrazionedocGetxmlprotpraticasportelloBean;
import it.eng.auriga.database.store.dmpk_registrazionedoc.bean.DmpkRegistrazionedocSavefileasclobBean;
import it.eng.auriga.database.store.dmpk_registrazionedoc.store.Getxmlprotpraticasportello;
import it.eng.auriga.database.store.dmpk_registrazionedoc.store.impl.SavefileasclobImpl;
import it.eng.auriga.database.store.dmpk_ws.bean.DmpkWsAddudBean;
import it.eng.auriga.database.store.dmpk_ws.store.Addud;
import it.eng.auriga.database.store.dmpk_ws.store.impl.AddudImpl;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.beans.SpecializzazioneBean;
import it.eng.auriga.module.business.entity.WSTrace;
import it.eng.auriga.module.business.login.service.LoginService;
import it.eng.auriga.repository2.jaxws.webservices.addunitadoc.visure.AddUdUtils;
import it.eng.auriga.repository2.jaxws.webservices.addunitadoc.visure.CreaAllegatoBean;
import it.eng.auriga.repository2.jaxws.webservices.addunitadoc.visure.SueFileStoreBean;
import it.eng.auriga.repository2.jaxws.webservices.common.JAXWSAbstractAurigaService;
import it.eng.auriga.repository2.jaxws.webservices.util.WSAttachBean;
import it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.Output_UD;
import it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.VersioneElettronicaNonCaricata;
import it.eng.auriga.repository2.util.DBHelperSavePoint;
import it.eng.auriga.repository2.util.InputStreamDataSource;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.dm.engine.manage.bean.ActivitiProcess;
import it.eng.document.configuration.OperazioniAurigaProtocollazioneAsyncConfigBean;
import it.eng.document.function.GestioneDocumenti;
import it.eng.document.function.bean.AllegatiBean;
import it.eng.document.function.bean.CreaModDocumentoOutBean;
import it.eng.document.function.bean.RebuildedFile;
import it.eng.document.function.bean.RebuildedFileStored;
import it.eng.document.function.bean.VersionaDocumentoOutBean;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.jaxb.variabili.Lista.Riga.Colonna;
import it.eng.spring.utility.SpringAppContext;
import it.eng.storeutil.AnalyzeResult;
import it.eng.util.FileNameUtils;
import it.eng.xml.XmlUtilitySerializer;

/**
 * @author Ottavio passalacqua
 */

@WebService(targetNamespace = "http://addunitadoc.webservices.repository2.auriga.eng.it", endpointInterface="it.eng.auriga.repository2.jaxws.webservices.addunitadoc.WSIAddUd", name = "WSAddUd")
@MTOM(enabled = true, threshold = 0)
@HandlerChain(file = "../common/handler.xml")
public class WSAddUd extends JAXWSAbstractAurigaService implements WSIAddUd{	

    private final String K_SAVEPOINTNAME = "INIZIOWSADDUD";
    private final String K_AGGIORN_VERS = "AV";
    private final String K_MODIF_METADATA = "MM";
    private final String K_DELETE_VERS = "DV";
    private final String K_DELETE_DOC = "DD";
    private final String K_SOST_VERS = "SV"; //Sostituzione di versione
    private final String K_MODIF_MD_VERS = "MV"; //Modifica metadati di versione
    
    WSAddUdConfig wsAddUdConfig;
    
//    private List<File> listTempFileToDelete;
    
    public static final String _SPRING_BEAN_WSADDUDCONFIG = "WSAddUdConfig";
    public static final String _SPRING_BEAN_PROTASYNC_CONFIG = "OperazioniAurigaProtocollazioneAsyncConfigBean";

    static Logger aLogger = Logger.getLogger(WSAddUd.class.getName());    
    
    /**
     * Crea una nuova istanza di <code>WSExtractOne</code> .
     *
     */
    public WSAddUd() {
	super();
    }

    /**
     * <code>serviceImplementation</code> biz logik del webservice.
     *
     * @param user a <code>String</code>
     * @param token a <code>String</code>
     * @param codAppl a <code>String</code>
     * @param conn a <code>Connection</code>
     * @param xmlDomDoc a <code>Document</code>
     * @param xml a <code>String</code>
     * @param istanzaAppl a <code>String</code>
     * @return a <code>String</code>
     * @exception Exception
     */
    @WebMethod(exclude=true)
    public final String serviceImplementation(final String user,
					      final String token,
					      final String codiceApplicazione,
					      final String istanzaAppl,
					      final Connection conn,
					      final Document xmlDomDoc,
					      final String xml,
					      final String schemaDb,
					      final String idDominio,
					      final String desDominio,
					      final String tipoDominio,
					      final WSTrace wsTraceBean) throws Exception {
    	
    	wsAddUdConfig = (WSAddUdConfig) SpringAppContext.getContext().getBean(_SPRING_BEAN_WSADDUDCONFIG);
    	
    	if(!(StringUtils.isNotBlank(wsAddUdConfig.getFlgNuovaGestione()) && wsAddUdConfig.getFlgNuovaGestione().equalsIgnoreCase("true"))) {
    		return vecchiaGestione(user, token, codiceApplicazione, istanzaAppl, conn, xml, schemaDb, idDominio, tipoDominio);
    	}else {
    		
    		String risposta = null;
    		String outRispostaWS = null;
    		String warnRegIn = "";
    		String errMsg = null;
    		String xmlIn = null;
    		
    		String xmlRequest = xml;
    		
    		boolean flgImpresaInUnGiorno = false;
    		String pathFileFtp = "";
    		
    		List<File> listaAttach = new ArrayList<>();
     		
    		try {

//    			listTempFileToDelete = new ArrayList<File>();
    			
    			aLogger.info("Inizio WSAddUd");

    			// setto il savepoint
    			DBHelperSavePoint.SetSavepoint(conn, K_SAVEPOINTNAME);

    			// creo bean connessione
    			AurigaLoginBean loginBean = new AurigaLoginBean();
    			loginBean.setToken(token);
    			loginBean.setCodApplicazione(codiceApplicazione);
    			loginBean.setIdApplicazione(istanzaAppl);
    			loginBean.setSchema(schemaDb);

    			SpecializzazioneBean lspecializzazioneBean = new SpecializzazioneBean();
    			lspecializzazioneBean.setCodIdConnectionToken(token);
    			if (idDominio != null && !idDominio.equalsIgnoreCase(""))
    				lspecializzazioneBean.setIdDominio(new BigDecimal(idDominio));

    			if (tipoDominio != null && !tipoDominio.equalsIgnoreCase(""))
    				lspecializzazioneBean.setTipoDominio(new Integer(tipoDominio));

    			loginBean.setSpecializzazioneBean(lspecializzazioneBean);
    			aLogger.info("Istanzio loginBean con token " + token + " codiceAppl " + codiceApplicazione + " istanzaAppl "
    					+ istanzaAppl + " schemaDb " + schemaDb + " idDominio " + idDominio + " tipoDominio " + tipoDominio
    					+ " user " + user);

    			/*************************************************************
    			 * Chiamo il WS e il servizio di AurigaDocument
    			 ************************************************************/
    			WSAddUdBean outWS = new WSAddUdBean();
    			Session session = null;    			

    			boolean protocolloGiaInviato = false;
		 		try {
    				
    	          	String flagSportelloImpresaInUnGiorno = AddUdUtils.getFlagImpresaPerUnGiorno(loginBean, user);
    	    		 
    		 		if( flagSportelloImpresaInUnGiorno!=null && flagSportelloImpresaInUnGiorno.equalsIgnoreCase("1")) {
    		 			flgImpresaInUnGiorno = true;
    		 			
    		 			//Se ho il nome dell attach nel tag <NomeArchivioFileInviatiSeparati> del xml in input, il file zip lo devo prendere come reference dall ftp e non dagli attach del servizio
    		 			String attachOnFtp = AddUdUtils.checkAttachOnFtp(xmlRequest);		 			
    		 			if(StringUtils.isNotBlank(attachOnFtp)) {
    		 				OperazioniAurigaProtocollazioneAsyncConfigBean protocollazioneAsyncConfigBean = (OperazioniAurigaProtocollazioneAsyncConfigBean) SpringAppContext.getContext()
    		    					.getBean(_SPRING_BEAN_PROTASYNC_CONFIG);
    		 				pathFileFtp = protocollazioneAsyncConfigBean.getPathFtp() + attachOnFtp;
    		 				xmlRequest = AddUdUtils.cancellaTagFileFtp(xmlRequest);
    		 			}
    		 			
    		 		}		 		
    				
    		 		//Se ho il nome dell attach nel tag <NomeArchivioFileInviatiSeparati> del xml in input, il file zip lo devo prendere come reference dall ftp e non dagli attach del servizio
		 			String attachOnFtp = AddUdUtils.checkAttachOnFtp(xmlRequest);		 			
		 			if(StringUtils.isNotBlank(attachOnFtp)) {
		 				listaAttach = getAttachment(flgImpresaInUnGiorno, pathFileFtp);
		 			}
    		 		
		 			
    		 		for(File fileAttach : listaAttach){
			 			if(fileAttach!= null && fileAttach.exists() ){
			 				aLogger.debug("fileAttach.getName " + fileAttach.getName());
				 			if( fileAttach.getName().equalsIgnoreCase("evento-suap.xml") ){
			 					String content = FileUtils.readFileToString(fileAttach);
			 		            aLogger.debug("content " + content);
			 					String xmlOut = verificaProtocollazioneSUE(loginBean, content);
			 		            if( xmlOut!=null){
			 		            	/**
			 		            	 * 
			 		            	 *  PER TESTARE COMMENTARE
			 		            	 * 
			 		            	 * **/
			 		            	protocolloGiaInviato = true;
			 		            	outRispostaWS = xmlOut;
			 		            }
			 				}
			 			}
			 		}
    		 		
    		 		if( !protocolloGiaInviato ){
	    				List<AttachWSBean> listaAttachWithInfo = getAttachmentWithInfo(loginBean, flgImpresaInUnGiorno, listaAttach, pathFileFtp, xmlRequest);
	    				
	    				
	    				// Creo la transazione
	    				SubjectBean subject = SubjectUtil.subject.get();
	    				subject.setIdDominio(loginBean.getSchema());
	    				SubjectUtil.subject.set(subject);
	
	    				// Chiamo il WS
	    				outWS = callStoreAddUd(loginBean, xmlRequest, listaAttachWithInfo, flagSportelloImpresaInUnGiorno);
	    				
    				
    		 		}

    			} catch (Exception e) {
    				if (e.getMessage() != null)
    					errMsg = "Errore = " + e.getMessage();
    				else
    					errMsg = "Errore imprevisto.";
    			} finally {
    				if (session != null)
    					HibernateUtil.release(session);
    					deleteTempFiles(listaAttach);
    			}

    			/**************************************************************************
    			 * Creo XML di risposta del servzio e lo metto in attach alla response
    			 **************************************************************************/
    			try {
    				if( !protocolloGiaInviato ){
	    				if (errMsg == null) {
	    					xmlIn = outWS.getXml();
	    				} else {
	    					xmlIn = errMsg;
	    				}
	
	    				// Creo XML di risposta
	    				outRispostaWS = generaXMLRispostaWS(xmlIn);
    				}
    				
    				// Creo la lista di attach
    				List<InputStream> lListInputStreams = new ArrayList<InputStream>();

    				// Converto l'XML
    				ByteArrayInputStream inputStreamXml = new ByteArrayInputStream(outRispostaWS.getBytes());

    				// Aggiungo l'XML
    				lListInputStreams.add(inputStreamXml);

    				// Salvo gli ATTACH alla response
    				attachListInputStream(lListInputStreams);

    			} catch (Exception e) {
    				return generaXMLRisposta(JAXWSAbstractAurigaService.FALLIMENTO,
    						JAXWSAbstractAurigaService.ERR_ERRORE_APPLICATIVO, e.getMessage(), "", "");
    			}

    			/*************************************************************
    			 * Restituisco XML di risposta del WS
    			 ************************************************************/
    			if (errMsg == null) {
    				risposta = generaXMLRisposta(JAXWSAbstractAurigaService.SUCCESSO, JAXWSAbstractAurigaService.SUCCESSO,
    						"Tutto OK", "", warnRegIn);
    			} else {
    				risposta = generaXMLRisposta(JAXWSAbstractAurigaService.FALLIMENTO,
    						JAXWSAbstractAurigaService.ERR_ERRORE_APPLICATIVO, errMsg, "", "");
    			}
    			aLogger.info("Fine WSAddUd");
    			return risposta;
    		} catch (Exception excptn) {
    			aLogger.error("WSAddUd: " + excptn.getMessage(), excptn);
    			return generaXMLRisposta(JAXWSAbstractAurigaService.FALLIMENTO,
    					JAXWSAbstractAurigaService.ERR_ERRORE_APPLICATIVO,
    					JAXWSAbstractAurigaService.ERROR_ERRORE_APPLICATIVO, "", "");
    		} finally {
    			try {
    				DBHelperSavePoint.RollbackToSavepoint(conn, K_SAVEPOINTNAME);
    			} catch (Exception ee) {
    			}
    			aLogger.info("Fine WSAddUd serviceImplementation");
    		}
    	
    	}
    }

	private String vecchiaGestione(final String user, final String token, final String codiceApplicazione,
			final String istanzaAppl, final Connection conn, final String xml, final String schemaDb,
			final String idDominio, final String tipoDominio) {
		String risposta        = null;    
		String outRispostaWS   = null;
		String warnRegIn         = "";
		String docStoreFallita = "";
		String errMsg = null;
		String xmlIn = null;
		try {

			 aLogger.info("Inizio WSAddUd");
			
			 //setto il savepoint
			 DBHelperSavePoint.SetSavepoint(conn, K_SAVEPOINTNAME);
			
			 // creo bean connessione
		     AurigaLoginBean loginBean = new AurigaLoginBean();         
		     loginBean.setToken(token);
		     loginBean.setCodApplicazione(codiceApplicazione);
		     loginBean.setIdApplicazione(istanzaAppl);
			 loginBean.setSchema(schemaDb);
			
			 SpecializzazioneBean lspecializzazioneBean = new SpecializzazioneBean();
			 lspecializzazioneBean.setCodIdConnectionToken(token);  		
		     if (idDominio!=null && !idDominio.equalsIgnoreCase(""))
		    	  lspecializzazioneBean.setIdDominio(new BigDecimal(idDominio));
			
			 if (tipoDominio!=null && !tipoDominio.equalsIgnoreCase(""))
				  lspecializzazioneBean.setTipoDominio(new Integer(tipoDominio));
			
			loginBean.setSpecializzazioneBean(lspecializzazioneBean);
			aLogger.info("Istanzio loginBean con token " + token + " codiceAppl " + codiceApplicazione +
					" istanzaAppl " + istanzaAppl + " schemaDb " + schemaDb + " idDominio " + 
					idDominio + " tipoDominio " + tipoDominio + " user " + user);
			
			 /*************************************************************
		      * Chiamo il WS e il servizio di AurigaDocument
		      ************************************************************/
		     WSAddUdBean outWS = new WSAddUdBean();
		     
		     WSAddUdOutBean outServizio = new WSAddUdOutBean();
		              
		     WSAttachBean wsAttach = new WSAttachBean();
		     
		     String errori = "";
		     Session session = null;
		     boolean protocolloGiaInviato = false;
		 		
		     try {
		    	 
		    	 Transaction lTransaction = null;
		    	 
		    	// Leggo gli attach e le info 
		      	String flagSportelloImpresaInUnGiorno = AddUdUtils.getFlagImpresaPerUnGiorno(loginBean, user);
				 
		      	String instanceProcessId = null;
		 		if( flagSportelloImpresaInUnGiorno!=null && flagSportelloImpresaInUnGiorno.equalsIgnoreCase("1")) {
		 			//WSAttachBean WSAttachBeanOut = new WSAttachBean();

		 			DataHandler[] attachments = getAttachmentImpresaInUnGiorno(loginBean, xml, wsAddUdConfig.getPathAttach());
		 			
		 			if (attachments != null && attachments.length == 0) {
		 				errori = "Il file ZIP (impresa per un giorno) non e' presente nel repository : " + wsAddUdConfig.getPathAttach();
		 				aLogger.error("Rilevati i seguenti errori: " + errori);
			    		throw new Exception(errori);	        	
		 			}
		 			
		 			List<File> filesArchivio = decomprimi_fileImpresaInUnGiorno(loginBean, attachments);
		 			aLogger.debug("Cerco il file evento-suap.xml e verifico se il protocollo è stato già registrato");
			 		for(File fileArchivio : filesArchivio){
			 			aLogger.debug("fileArchivio.getName " + fileArchivio.getName());
			 			if(fileArchivio!= null && fileArchivio.exists() ){
			 				if( fileArchivio.getName().equalsIgnoreCase("evento-suap.xml") ){
			 					String content = FileUtils.readFileToString(fileArchivio);
			 		            aLogger.debug("content " + content);
			 					String xmlOut = verificaProtocollazioneSUE(loginBean, content);
			 		            if( xmlOut!=null){
			 		            	protocolloGiaInviato = true;
			 		            	outRispostaWS = xmlOut;
			 		            }
			 				}
			 			}
			 		}
			 		aLogger.debug("protocolloGiaInviato " + protocolloGiaInviato);
		 			
			 		if(!protocolloGiaInviato ){
			 			List<RebuildedFile> listRebuildedFile = popolo_info_fileImpresaInUnGiorno(loginBean, filesArchivio);
			 			wsAttach.setAttachments(attachments);
			 			wsAttach.setListRebuildedFile(listRebuildedFile);
				 		
				 		boolean isVisura = false;
				 		
				 		String visuraTipoProcesso = wsAddUdConfig.getVisureTipoProcesso();
				 		aLogger.debug("visuraTipoProcesso : " + visuraTipoProcesso );
				 		
				 		boolean continuaRicerca = true;
			 			for(RebuildedFile rebFile : wsAttach.getListRebuildedFile()){
				 			if( continuaRicerca ){
			    	 			File file = rebFile.getFile();
			    	 			aLogger.info("File : " + file );
			    	 			if( rebFile.getInfo()!=null && rebFile.getInfo().getAllegatoRiferimento()!=null && 
			    	 					rebFile.getInfo().getAllegatoRiferimento().getMimetype()!=null 
			    	 					&& rebFile.getInfo().getAllegatoRiferimento().getMimetype().equalsIgnoreCase("application/xml")){
				    	 			List<String> listaValori = AddUdUtils.getCodiceVisura(file);//getValoreTipoProcedimento(file);
				    	 			aLogger.info("listaValori :" + listaValori );
				    	 			if(listaValori==null){
				    	 				continuaRicerca = true;
				    	 			}
				    	 			else {
				    	 				continuaRicerca = false;
				    	 				if(visuraTipoProcesso!=null && listaValori.contains(visuraTipoProcesso)){
					    	 				isVisura = true;
					    	 			}
				    	 			}
			    	 			}
				 			}
				 		}
				 		
				 		aLogger.info("isVisura " + isVisura);
				 		if( isVisura && wsAddUdConfig.getVisureBPMProcessDefName()!=null ){
				 			aLogger.info("Provo ad avviare il processo activity " + wsAddUdConfig.getVisureBPMProcessDefName() );
				 			instanceProcessId = AddUdUtils.avviaProcesso( wsAddUdConfig.getVisureBPMProcessDefName() );
				 			aLogger.info("Id dell'istanza di processo activity avviata: " + instanceProcessId);
				 		}
			 		}
			 	} else {
			 		wsAttach = getAttachment(loginBean);
			 	}	 		
		 		
		 		if( !protocolloGiaInviato ){
				 	// Controllo se il mimetype degli attach e' valido
				 	aLogger.debug("Controllo se il mimetype degli attach e' valido");
				 	errori = checkMimeTypeAttach(wsAttach);
				 	
				 	// se ho trovato errori ritorno errore
			    	if (!errori.equals("")) {
			    		aLogger.error("Rilevati i seguenti errori: " + errori);
			    		throw new Exception(errori);	        		
			    	}
			            	
			    	// Creo la transazione
			        SubjectBean subject = SubjectUtil.subject.get();
			    	subject.setIdDominio(loginBean.getSchema());
			    	SubjectUtil.subject.set(subject);
			    	session = HibernateUtil.begin();
			    	lTransaction = session.beginTransaction();
			    	
			    	
			    	// Chiamo il WS
			    	outWS =  callWS(loginBean,xml, session);
			    	
				 	// Chiamo il servizio di AurigaDocument
				 	outServizio =  eseguiServizio(loginBean, conn, outWS, wsAttach, flagSportelloImpresaInUnGiorno, session, instanceProcessId, wsAddUdConfig.getVisureBPMProcessDefName());
				 	
				 	String errService = outServizio.getWarnRegOut();
				 	
				 	// Se i file non sono stati salvati restituisco errore
			        if (errService!=null && !errService.equalsIgnoreCase("")){
			        	throw new Exception("Protocollazione con allegati KO! Errore nel salvataggio degli allegati : " + errService);
			        }
			        
				 	if( session!=null )
				 		session.flush();
				 	
				 	if( lTransaction!=null)
				 		lTransaction.commit();
		 		}
				
		 	} catch (Exception e){	
		 		 if(e.getMessage()!=null)
		 			 errMsg = "Errore = " + e.getMessage();
		 		 else
		 			errMsg = "Errore imprevisto."; 
		 	}
		    finally {
				if( session!=null )
					HibernateUtil.release(session);
			}
		 	
		 	/**************************************************************************
			 * Creo XML di risposta del servzio e lo metto in attach alla response
			 **************************************************************************/                       	  	
			try {
				if( !protocolloGiaInviato ){
					if (errMsg == null) {
						xmlIn = outServizio.getXmlRegOut();
						warnRegIn = outServizio.getWarnRegOut();
					} else {
						xmlIn = errMsg;
					}
	
					// Creo XML di risposta
					outRispostaWS = generaXMLRispostaWS(xmlIn, warnRegIn);
				}

				// Creo la lista di attach
				List<InputStream> lListInputStreams = new ArrayList<InputStream>();

				// Converto l'XML
				ByteArrayInputStream inputStreamXml = new ByteArrayInputStream(outRispostaWS.getBytes());

				// Aggiungo l'XML
				lListInputStreams.add(inputStreamXml);

				// Salvo gli ATTACH alla response
				attachListInputStream(lListInputStreams);

			}
			catch (Exception e) {
				return generaXMLRisposta(JAXWSAbstractAurigaService.FALLIMENTO,
						JAXWSAbstractAurigaService.ERR_ERRORE_APPLICATIVO, e.getMessage(), "", "");
			}   
		    	
		     /*************************************************************
			  * Restituisco XML di risposta del WS
			  ************************************************************/		 	
			 if (errMsg==null){
			 	 risposta = generaXMLRisposta( JAXWSAbstractAurigaService.SUCCESSO, JAXWSAbstractAurigaService.SUCCESSO, "Tutto OK", "", warnRegIn);
			 }
			 else{
			 	 risposta = generaXMLRisposta( JAXWSAbstractAurigaService.FALLIMENTO, JAXWSAbstractAurigaService.ERR_ERRORE_APPLICATIVO,  errMsg, "", "");
			 }	 
		     aLogger.info("Fine WSAddUd");
		     return risposta;
		}
		catch (Exception excptn) {
		    aLogger.error("WSAddUd: " + excptn.getMessage(), excptn);
		    return   generaXMLRisposta( JAXWSAbstractAurigaService.FALLIMENTO, JAXWSAbstractAurigaService.ERR_ERRORE_APPLICATIVO, JAXWSAbstractAurigaService.ERROR_ERRORE_APPLICATIVO, "", "" );
		}
		finally
		{
			try { DBHelperSavePoint.RollbackToSavepoint(conn, K_SAVEPOINTNAME); } catch (Exception ee) {}
		    aLogger.info("Fine WSAddUd serviceImplementation");
		}
	}

    
    
   private String verificaProtocollazioneSUE(AurigaLoginBean loginBean, String xmlFile) throws Exception{
	   String xmlOut = null; 
	   aLogger.debug("Verifico la protocollazione SUE");
	   DmpkRegistrazionedocGetxmlprotpraticasportelloBean input = new DmpkRegistrazionedocGetxmlprotpraticasportelloBean();
	   input.setCodidconnectiontokenin(loginBean.getToken());
	   input.setEventosuapxmlin(xmlFile);

		// Eseguo il servizio
	    Getxmlprotpraticasportello service = new Getxmlprotpraticasportello();
		StoreResultBean<DmpkRegistrazionedocGetxmlprotpraticasportelloBean> output = service.execute(loginBean, input);

		if (output.isInError()) {
			aLogger.debug("Errore nella store " + output.getDefaultMessage() );
			throw new Exception(output.getDefaultMessage());
		}

		// leggo XmlOut
		Integer flgProtocolloTrovato = output.getResultBean().getFlgtrovatoprotocolloout();
		aLogger.debug("flgProtocolloTrovato " + flgProtocolloTrovato );
		if( flgProtocolloTrovato!=null && flgProtocolloTrovato==1){
			xmlOut = output.getResultBean().getProtocolloxmlout();
			aLogger.debug("xmlOut " + xmlOut );
		}
		
		return xmlOut;
   }
    
    
    
    
	
    
   /****************************************************************************************************************
	* Prende le info dall'xml e versiona i file elettronici ( primario + allegati ).
	* L'xml restituisce  : 
	*    -- 1: Valore fisso AV: indica il tipo di operazione da fare sul documento corrispondente all'attach
	*    -- 2: N.ro dell'attach del messaggio SOAP che corrisponde al file da caricare nella repository
	* 	 -- 3: Identificativo del documento creato in DB in corrispondenza dell'attachment
	* 	 -- 4: Nome del file da caricare (quello con cui mostrarlo e dalla cui estensione si ricava il formato)
	* 	 -- 5: (valori 1/0) Se 1 indica che la versione e' pubblicata, se 0 no
	* 	 -- 6: (valori 1/0) Se 1 indica che la versione deriva da scansione, se 0 no
	* 	 -- 7: nr. che identifica la versione
	* 	 -- 8: Note della versione
	* 	 -- 9: (valori 1/0) Se 1 indica che la versione deve essere sottoposta a OCR 
	**************************************************************************************************************/
    private WSAddUdOutBean eseguiServizio(AurigaLoginBean loginBean,  Connection conn,  WSAddUdBean bean , WSAttachBean wsAttachEinfo, String flagSportelloImpresaInUnGiorno, Session session, String instanceProcessId, String visureBPMProcessDefName) throws Exception {
    	
    	
    	String xmlOut                  = null;
    	String docAttXmlOut            = null;
    	Output_UD listaFileElettroniciNonSalvatiOut             = null;
    	
    	// popolo il bean di out
    	WSAddUdOutBean  ret = new WSAddUdOutBean();
		  
    	aLogger.debug("Eseguo il servizio di AurigaDocument.");
    	
    	try {
    		// Leggo input
    		xmlOut                  = bean.getXml();
    		docAttXmlOut            = bean.getDocAttachXML();
    		  	
    		// Estraggol'IDUD dall'XML
    		StringReader  srXmlOut = new StringReader(xmlOut);

    		listaFileElettroniciNonSalvatiOut = (Output_UD) Unmarshaller.unmarshal(Output_UD.class, srXmlOut);
  
    		// Estraggo le info degli ALLEGATI dall'XML
    		Lista lsDocAttXmlOut =  null;
    		if ( docAttXmlOut != null) {
    			StringReader srDocAttXmlOut = new StringReader(docAttXmlOut);   
    			lsDocAttXmlOut = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(srDocAttXmlOut);
    		}
  		
    		// Processo gli ALLEGATI
    		if ( lsDocAttXmlOut != null && lsDocAttXmlOut.getRiga().size() > 0) {
        	
        		String errori = "";
        		        		
        		// Prendo gli attach
        		DataHandler[] attachments =  wsAttachEinfo.getAttachments();
        		
        		// Prendo la lista delle info degli attach
        		List<RebuildedFile> listRebuildedFile   = wsAttachEinfo.getListRebuildedFile();
        		
            	/*************************************************************
            	 * Controlli sulla lista_std:
            	 *    - tutti gli allegati esistono e il nome e' corrispondente
            	 *    - tutti gli attach fisici hanno una corrispondenza nella lista
            	 *    - non vi sono attach ripetuti nella lista 
            	 *************************************************************/
            	errori = verificaAllegati(attachments, lsDocAttXmlOut);

            	// se ho trovato errori ritorno errore
            	if (!errori.equals("")) {
            		aLogger.error("Rilevati i seguenti errori: " + errori);
            		throw new Exception(errori);	        		
            	}

            	/*************************************************************
            	 * Salvo i file allegati
            	 *************************************************************/
            	if( flagSportelloImpresaInUnGiorno!=null && flagSportelloImpresaInUnGiorno.equalsIgnoreCase("1")) {
            		errori = salvaAllegatiImpresaUnGiorno(loginBean, conn, listRebuildedFile, attachments, lsDocAttXmlOut, listaFileElettroniciNonSalvatiOut, flagSportelloImpresaInUnGiorno, session, visureBPMProcessDefName, instanceProcessId );
            	} else {
            		errori = salvaAllegati(loginBean, conn, listRebuildedFile, attachments, lsDocAttXmlOut, listaFileElettroniciNonSalvatiOut, session);
            	}
            	
            	// Se il salvataggio e' fallito restituisco l'elenco dei file non salvati
                if (errori!= null && !errori.trim().equalsIgnoreCase("")){
                	if (listaFileElettroniciNonSalvatiOut != null) {
                		StringBuffer lStringBuffer = new StringBuffer();
                		if (listaFileElettroniciNonSalvatiOut.getVersioneElettronicaNonCaricata()!=null && listaFileElettroniciNonSalvatiOut.getVersioneElettronicaNonCaricata().length >0){
                			for (int i = 0; i < listaFileElettroniciNonSalvatiOut.getVersioneElettronicaNonCaricata().length; i++) {
                				String lStrFileInError  = listaFileElettroniciNonSalvatiOut.getVersioneElettronicaNonCaricata(i).getNomeFile();
                				lStringBuffer.append(lStrFileInError + "; ");	
                			}
                			String warnRegOut  = lStringBuffer.toString();
                			ret.setWarnRegOut(warnRegOut);
                			aLogger.debug(warnRegOut);
                		}
                		else{
                    		ret.setWarnRegOut(errori);
                			aLogger.debug(errori);
                    	}
             	   }
                }
           }
    	   // Restituisco l'xml
    	   ret.setXmlRegOut(xmlOut);
    	}
    	catch (Exception e){
    		throw new Exception(e.getMessage());	
    	}
    	
    	return ret;    	
 }
        
    /****************************************************************************************************************
	* Funzione per aggiornare un'unita' documentaria.
	* INPUT : 
	*     -  XMLIn : xml con i metadati da aggiornare
	* OUTPUT :     
	*   a) docAttachXML : xml con le informazioni da aggiornare sui file elettronoci  : 
	*	   -- 1: Valore fisso AV: indica il tipo di operazione da fare sul documento corrispondente all'attach
	*	   -- 2: N.ro dell'attach del messaggio SOAP che corrisponde al file da caricare nella repository
	*	   -- 3: Identificativo del documento creato in DB in corrispondenza dell'attachment
	*	   -- 4: Nome del file da caricare (quello con cui mostrarlo e dalla cui estensione si ricava il formato)
	*	   -- 5: (valori 1/0) Se 1 indica che e' la versione pubblicata, se 0 no
	*	   -- 6: (valori 1/0) Se 1 indica che e' la versione deriva da scansione, se 0 no
	*	   -- 7: Codice che identifica la versione (n.ro versione alfanumerico, opzionale)
	*	   -- 8: Note della versione
	*	   -- 9: (valori 1/0) Se 1 indica che la versione deve essere sottoposta a OCR 
	*
	*   b) RegNumDaRichASistEstOut : Lista con gli estremi delle registrazioni/numerazioni richieste da farsi dare da sistemi esterni (XML conforme a schema LISTA_STD.xsd) o da AURIGA stesso tramite API di RegistraUnitaDoc
	*       -- Per ogni registrazioni/numerazioni vi e' un tag Riga che contiene le seguenti colonne:
	*		-- 1: Codice che indica la categoria di registrazione/numerazione (PG = Protocollo Generale, ecc)
	*		-- 2: Sigla che indica il registro di registrazione/numerazione
	*		-- 3: Anno di registrazione/numerazione se diverso da quello corrente
	*		
	*   c) XmlOut : XML con dati di output specifici del WS, restituito solo in caso di successo, conforme a schema Output_UD.xsd
	*   
	**************************************************************************************************************/    
    private WSAddUdBean callWS(AurigaLoginBean loginBean, String xmlIn, Session session) throws Exception {
    	    	
    	aLogger.debug("Eseguo il WS DmpkWsAddUd.");
    	
    	String xml = null;
    	String docAttachXML = null;
    	String regNumDaRichASistEst =  null;
    	
    	try {    		
    		  // Inizializzo l'INPUT    		
    		  DmpkWsAddudBean input = new DmpkWsAddudBean();
    		  input.setCodidconnectiontokenin(loginBean.getToken());
    		  input.setXmlin(xmlIn);
    		  
    		  // Eseguo il servizio
    		  StoreResultBean<DmpkWsAddudBean> output = null;
    		  if( session!=null ){
    			  final AddudImpl service = new AddudImpl();
    			  service.setBean(input);
  				session.doWork(new Work() {

  					@Override
  					public void execute(Connection paramConnection) throws SQLException {
  						paramConnection.setAutoCommit(false);
  						service.execute(paramConnection);
  					}
  				});
  				output = new StoreResultBean<DmpkWsAddudBean>();
				AnalyzeResult.analyze(input, output);
				output.setResultBean(input);
				
    		  } else {
    			  Addud service = new Addud();
    			 output = service.execute(loginBean, input);
    		  }
    		  
    		  if (output.isInError()){
    			  throw new Exception(output.getDefaultMessage());	
    			}	

    		  // leggo  XmlOut
    		  xml = output.getResultBean().getXmlout();
    		  
    		  if (xml== null || xml.equalsIgnoreCase(""))
    			  throw new Exception("La store procedure ha ritornato XmlOut nullo");
    		      		  
    		  // leggo docAttachXML    		    
    		  docAttachXML = output.getResultBean().getDocattachxmlout();
    		      		  
    		  // leggo regNumDaRichASistEst    		    
    		  regNumDaRichASistEst = output.getResultBean().getRegnumdarichasistestout();
    		  
    	      // popolo il bean di out
    		  WSAddUdBean  result = new WSAddUdBean();
    		  
    		  result.setDocAttachXML(docAttachXML);
    		  result.setRegNumDaRichASistEst(regNumDaRichASistEst);
    		  result.setXml(xml);
    			  
    		  return result;
 			}
 		catch (Exception e){
 			throw new Exception(e.getMessage()); 			
 		}
    }
    


	/**
     * Genera il file XML di risposta del servizio
     * Questo file viene passato come allegato in caso di successo.
     * @param xmlIn       Contiene xml restutuito dal servizio
     * @param warnMessage Puo' essere valorizzato con eventuali warning
     * @return String stringa XML secondo il formato per il ritorno dell'idud
     */
    private String generaXMLRispostaWS(String xmlIn, String warnMessage)  throws Exception {
    
        StringBuffer xml = new StringBuffer();
        String xmlInEsc = null;
        
        try {
        	// ...se il token non e' null
            if (xmlIn != null) {
            	// effettuo l'escape di tutti i caratteri
            	xmlInEsc = eng.util.XMLUtil.xmlEscape(xmlIn);
            }
            
            //xmlInEsc = xmlIn;
            xml.append(xmlInEsc);
            aLogger.debug(xml.toString());
            
            if (warnMessage != null && !warnMessage.equals("")) {
            	xml.append("<WarningMessage>" + warnMessage + "</WarningMessage>\n");
            }
        }
        catch (Exception e){
 			throw new Exception(e.getMessage());
 		}        
        return xml.toString();
    }


    
      
    // Salvo gli allegati
    private String salvaAllegati(AurigaLoginBean loginBean , Connection conn, List<RebuildedFile> listRebuildedFile , DataHandler[] attachments,  Lista lsDocAttXmlOut, Output_UD listaFileElettroniciNonSalvatiOut, Session session) throws Exception {
    	
    	String returnMess ="";
    	try {
    			AllegatiBean lAllegatiBean = new AllegatiBean();
    		    		
    			//booleano per sapere se e' gia' fallita una step
    			boolean unaStepGiaFallita = false;

    			//Array per le Versioni elettroniche non caricate
    			List versioniNonCaricate = new ArrayList();
        	
    			String docId             = "";
    			BigDecimal idDocPrimario = null;
    			String idDocFile         = "";
    			String flgTipoFile       = "";
    			String attachNum         = "";				
				String nome              = ""; 
				String flgPubblicata     = "";
				String flgDaScansione    = "";
				String nroVersione       = "";
				String note              = "";
				String flgRichOCR        = ""; 
    			String operazione        = "";
    			String nroVersioneFile   = "";
    			Long idUdInt             = new Long(0);
    			String  idUd             = "";    			
    			
    			// ciclo sulla lista per caricare i bean  lFilePrimarioBean ,lAllegatiBean
    			for (int i = 0; i < lsDocAttXmlOut.getRiga().size(); i++) {
    				// prendo la riga i-esima        		
    				Riga r = lsDocAttXmlOut.getRiga().get(i);
        		
    				attachNum 		= "";				
    				nome 			= ""; 
    				flgPubblicata 	= "";
    				flgDaScansione 	= "";
    				nroVersione   	= "";
    				note 			= "";
    				flgRichOCR 		= ""; 
    				operazione      = "";
								
    				for(int j = 0; j < r.getColonna().size(); j++) {
    					int num = 0;            			
    					Colonna c = r.getColonna().get(j);
    					String val = c.getContent();
    					if ( c!=null )
    						num = c.getNro().intValue();
											
    					// -- 1: operazione da eseguire								
    					if (num == 1) {
        					operazione = val;
    					}	
    					// -- 2: N.ro dell'attach del messaggio SOAP che corrisponde al file da caricare nella repository
    					else if (num == 2) {	            	
    						attachNum = val;        
    					}		
    					// -- 3: Identificativo del documento creato in DB in corrispondenza dell'attachment
    					else if (num == 3) {
    						docId = val;
    					}				
    					// -- 4: Nome del file da caricare (quello con cui mostrarlo e dalla cui estensione si ricava il formato)
    					else if (num == 4) {					
    						nome = val;
    					}
    					// -- 5: (valori 1/0) Se 1 indica che la versione e' pubblicata, se 0 no
    					else if (num == 5) {					
    						flgPubblicata = val;
    					}
    					// -- 6: (valori 1/0) Se 1 indica che la versione deriva da scansione, se 0 no
    					else if (num == 6) {					
    						flgDaScansione = val;
    					}
    					// -- 7: Codice che identifica la versione (n.ro versione alfanumerico, opzionale)
    					else if (num == 7) {					
    						nroVersione = val;
    					}
    					// -- 8: Note della versione
    					else if (num == 8) {					
    						note = val;
    					}
    					// -- 9: (valori 1/0) Se 1 indica che la versione deve essere sottoposta a OCR 
    					else if (num == 9) {					
    						flgRichOCR = val; 
    					}
    					
    					// resetto se operazione <> K_AGGIORN_VERS,K_SOST_VERS
    					if( !operazione.equalsIgnoreCase("") && !operazione.equals(K_AGGIORN_VERS) && !operazione.equals(K_SOST_VERS) )
    					{
    						attachNum = "";					  
    					}
					
    					// resetto se operazione <> K_AGGIORN_VERS,K_SOST_VERS, K_MODIF_MD_VERS
    					if ( !operazione.equalsIgnoreCase("") && !operazione.equals(K_AGGIORN_VERS) && !operazione.equals(K_SOST_VERS) && !operazione.equals(K_MODIF_MD_VERS)) {
    						nome           = "";
    						flgPubblicata  = "";
    						flgDaScansione = "";
    						note           = "";
    						nroVersione    = "";
    						flgRichOCR     = "";
    					}
    					
    					// Se l'operazione e' 'MM' oppure 'MV'
    					// non faccio nulla perche' le modifiche sono state fatte dal servizio Dmpk_Ws->Updud
    					if( !operazione.equalsIgnoreCase("") && (operazione.equals(K_MODIF_METADATA)   ||  operazione.equals(K_MODIF_MD_VERS) )){    						
    						 break;
    					}
    				}        	          				        	
    				
    				BigDecimal idDocFileDecimal = new BigDecimal(0);
    				flgTipoFile = "";
    				
					// Per ogni operazione indicata nell'xml eseguo il servizio di AurigaDocument
					idUdInt         = listaFileElettroniciNonSalvatiOut.getIdUD();
    				idUd            = idUdInt.toString();
    				idDocFile       = docId;
    				nroVersioneFile = nroVersione;
    				
    				// leggo IdDocPrimario associato alla UD
        			if (operazione.equals(K_AGGIORN_VERS) || operazione.equals(K_SOST_VERS)) {
        				try {        		
    						GestioneDocumenti servizio = new GestioneDocumenti();
    						idDocPrimario =servizio.leggiIdDocPrimarioWSInTransaction(getLocale(), loginBean, idUd, session);
    						aLogger.info("idDocPrimario " + idDocPrimario);
        				}
        				catch (Exception ve) {    			
    						String mess = "------> Fallita la ricerca del iDDocPrimario  per idUd = " + idUd +  ", ERRORE = " + ve.getMessage(); 
    						aLogger.debug(mess + " - " + ve.getMessage(), ve);
    						throw new Exception(mess);	    						
    					} 
        				
        				if (idDocFile!=null && !idDocFile.equalsIgnoreCase("")){
        					idDocFileDecimal = new BigDecimal(idDocFile);
        				}
        				
        				if (idDocPrimario!=null && idDocPrimario.compareTo(idDocFileDecimal) == 0 ){        					 
        					flgTipoFile = "P";
        				}
        				else{
        					flgTipoFile = "A";
        				}
        				aLogger.info("flgTipoFile " + flgTipoFile);
        				
        				// Simulo il file elettronico ( primario o allegato)
        				if (operazione.equals(K_AGGIORN_VERS) || operazione.equals(K_SOST_VERS)) {  
        					if (flgTipoFile.equalsIgnoreCase("P")){
        						Integer indAttP = new java.lang.Integer(attachNum);
    						    RebuildedFile lRebuildedFile1 = new RebuildedFile();
    						    lRebuildedFile1 = listRebuildedFile.get(indAttP-1);
    						    if(operazione.equals(K_SOST_VERS))
    						    {
    						    	lRebuildedFile1.setAnnullaLastVer(true);
    						    }    						    	
    						    else{
    						    	lRebuildedFile1.setAnnullaLastVer(false);
    						    }
    						    lAllegatiBean = popoloAllegatiBean(lRebuildedFile1, nome, docId, note);
        					}
        					else if (flgTipoFile.equalsIgnoreCase("A")){
        						Integer indAttA = new java.lang.Integer(attachNum);
        						RebuildedFile lRebuildedFile2 = new RebuildedFile();
        						lRebuildedFile2 = listRebuildedFile.get(indAttA-1);
        						if(operazione.equals(K_SOST_VERS)){
        							lRebuildedFile2.setAnnullaLastVer(true);
        						}    						    	
    						    else{
    						    	lRebuildedFile2.setAnnullaLastVer(false);
    						    }
        						lAllegatiBean = popoloAllegatiBean(lRebuildedFile2, nome, docId, note);
        					}
        				}
        			}
    				
    				/**************************************************
    				 * AV = step version 
    				 **************************************************/      				    				
    				if (operazione.equals(K_AGGIORN_VERS)) {
    					
    					try {        	        		
        	        		GestioneDocumenti servizio = new GestioneDocumenti();
        	        		CreaModDocumentoOutBean servizioOut = new CreaModDocumentoOutBean();
        	        		
        	        	    servizioOut = servizio.addDocsWS(loginBean, flgTipoFile, idUd,  idDocFileDecimal, lAllegatiBean, session);
        	        		
        	        		// Se il servizio e' andato in errore restituisco il messaggio di errore 	    	    
        	        	    if(StringUtils.isNotBlank(servizioOut.getDefaultMessage())) {
        	        	    	returnMess += (unaStepGiaFallita)?", "+ servizioOut.getDefaultMessage() : " " + servizioOut.getDefaultMessage();
        	        		}
        	        	    
        	        	    //popolo un oggetto VersioneElettronicaNonCaricata per tener conto del fallimento
        	    			VersioneElettronicaNonCaricata venc = new VersioneElettronicaNonCaricata();
        	    			
        	        	    // Se la gestione dei file e' andata in errore restituisco il messaggio di errore 
        	        	    StringBuffer lStringBuffer = new StringBuffer();
        	        	    
        	        	    int key = 0;
        	        	    if (servizioOut.getFileInErrors()!=null && servizioOut.getFileInErrors().size()>0){
        	            			for (String lStrFileInError : servizioOut.getFileInErrors().values()){
        	            				 
        	            				lStringBuffer.append("; " + lStrFileInError);			
        	            				
        	            				//setto l'indice
        	            				String sIndex = servizioOut.getFileInErrors().get(key);
        	            				if (sIndex != null && !sIndex.equalsIgnoreCase(""))
        	            					venc.setNroAttachmentAssociato(new Integer(sIndex));

        	                			//setto il nome file
        	                			venc.setNomeFile(lStrFileInError);

        	                			//aggiungo alla lista dei fallimenti
        	                			versioniNonCaricate.add(venc);
        	                			
        	                			key++;
        	            			}
        	        	    }        	        	    
        	        	    if (lStringBuffer != null && lStringBuffer.length()>0 && !lStringBuffer.toString().equalsIgnoreCase("")) 
        	        	    	returnMess += (unaStepGiaFallita)?", "+ lStringBuffer : " " + lStringBuffer;        	    
        	        	}        				
        	        	catch (Exception ve) {    			
        	    			String mess = "------> Fallita la stepVersion per il doc " + idDocFile;    			
        	    			returnMess += (unaStepGiaFallita)?", "+ mess : " " + mess;
        	    			unaStepGiaFallita = true;    			    	    			
        	    			aLogger.debug(mess + " - " + ve.getMessage(), ve);        	    			
        	    		}
    				}					
        		}
        	
    			/*************************************************************
    			 * Gestisco gli eventuali messaggi di warning
    			 ************************************************************/        

    			// preparo la stringa per xml di risposta
    			if (!returnMess.equals("")) {
    				returnMess="Fallito il caricamento per i file:" + returnMess;
    			}

    			// se vi sono versioni non caricate
    			if (versioniNonCaricate.size() > 0) {
    				//trasferisco la lista in un array per effettuare il set nell'XMLOUT 
    				VersioneElettronicaNonCaricata vencArray[] =  new VersioneElettronicaNonCaricata[versioniNonCaricate.size()];
    				// scorro la lista e trasferisco
    				Iterator iter = versioniNonCaricate.iterator();
    				//contatore per popolare l'array
    				int i = 0;
    				//popolo l'array
    				while (iter.hasNext()) {
    					vencArray[i] = (VersioneElettronicaNonCaricata)iter.next();
    					aLogger.debug("NON inserita versione #" + i);
    					i++;
    				}
    				// effettuo il set sull'output_UD
    				listaFileElettroniciNonSalvatiOut.setVersioneElettronicaNonCaricata(vencArray);        	
    			}    		
    		}
    		catch (Throwable ee) { 
    			returnMess += "Errore nella funzione nella salvaAllegati() - " + ee.getMessage() + "\n";
    			aLogger.error(returnMess, ee);  			
    			throw new Exception(returnMess);	 
    		}
    	
    	return returnMess;    	
    }   
    
    private String salvaAllegatiImpresaUnGiorno(AurigaLoginBean loginBean , Connection conn, List<RebuildedFile> listRebuildedFile , DataHandler[] attachments, Lista lsDocAttXmlOut, Output_UD listaFileElettroniciNonSalvatiOut, String flagSportelloImpresaInUnGiorno, Session session, String visureBPMProcessDefName, String instanceProcessId) throws Exception {
    	aLogger.info("Metodo salvaAllegati impresa in un giorno");
    	
    	String returnMess ="";
    	try {
    		
    		Long idUdInt = listaFileElettroniciNonSalvatiOut.getIdUD();
			String idUd = idUdInt.toString();
			aLogger.info("IdUd " + idUd );

			if( listRebuildedFile!=null ) {
				if( listRebuildedFile.size()==1 ) {			
					
					String displayFilename = listRebuildedFile.get(0).getInfo().getAllegatoRiferimento().getDisplayFilename();
					
					BigDecimal idDocPrimario = null;
					try {        		
						GestioneDocumenti servizio = new GestioneDocumenti();
						idDocPrimario = servizio.leggiIdDocPrimarioWSInTransaction( getLocale(), loginBean, idUd, session);
						aLogger.info("idDocPrimario " + idDocPrimario);
    				}
    				catch (Exception ve) {    			
						String mess = "------> Fallita la ricerca del iDDocPrimario  per idUd = " + idUd +  ", ERRORE = " + ve.getMessage(); 
						aLogger.debug(mess + " - " + ve.getMessage(), ve);
						throw new Exception(mess);	    						
					} 
					
					listRebuildedFile.get(0).setIdDocumento(idDocPrimario);
					VersionaDocumentoOutBean versionaDocResult = versionaDocumentoInTransaction( loginBean, listRebuildedFile.get(0), session, visureBPMProcessDefName, instanceProcessId, 1);
					boolean esitoVersionamento = true;
					if( versionaDocResult.getErrorCode()!=null ){
						esitoVersionamento = false;
						returnMess += " Errore nel versionamento del file " + displayFilename + " : " + versionaDocResult.getDefaultMessage();
					}
					aLogger.info("Esito versionamento " + esitoVersionamento );					
					// Se il versionamento e' stato effettuato
					if (esitoVersionamento){
						if( listRebuildedFile.get(0).getInfo().getAllegatoRiferimento().getDisplayFilename().toUpperCase().endsWith("XML") ) {
	    	        	    aLogger.info("Chiamo la DmpkRegistrazionedocSavefileasclob con idDOc " + idDocPrimario);
	        	    		DmpkRegistrazionedocSavefileasclobBean dmpkRegistrazionedocSavefileasclobBean = new DmpkRegistrazionedocSavefileasclobBean();
	    	        	    dmpkRegistrazionedocSavefileasclobBean.setCodidconnectiontokenin( loginBean.getToken() );
	    	        	    dmpkRegistrazionedocSavefileasclobBean.setIddocin( idDocPrimario );
	    	        	   
	    	        	    String fileContent = FileUtils.readFileToString(listRebuildedFile.get(0).getFile());
	    	        	    dmpkRegistrazionedocSavefileasclobBean.setFilecontentin(fileContent );
	    	        	    dmpkRegistrazionedocSavefileasclobBean.setFlgparsecontentin(1);
	    	        	    
	    	        	    final SavefileasclobImpl lDmpkRegistrazionedocSavefileasclob = new SavefileasclobImpl();
	    	        	    lDmpkRegistrazionedocSavefileasclob.setBean(dmpkRegistrazionedocSavefileasclobBean);
	    	        	    session.doWork(new Work() {

	    						@Override
	    						public void execute(Connection paramConnection) throws SQLException {
	    							paramConnection.setAutoCommit(false);
	    							lDmpkRegistrazionedocSavefileasclob.execute(paramConnection);
	    						}
	    					});
	    	        	    
	    	        	    StoreResultBean<DmpkRegistrazionedocSavefileasclobBean> dmpkRegistrazionedocSavefileasclobResult = new StoreResultBean<DmpkRegistrazionedocSavefileasclobBean>();
	    					AnalyzeResult.analyze(dmpkRegistrazionedocSavefileasclobBean, dmpkRegistrazionedocSavefileasclobResult);
	    					dmpkRegistrazionedocSavefileasclobResult.setResultBean(dmpkRegistrazionedocSavefileasclobBean);
	    	        	    
	    	        	    if( dmpkRegistrazionedocSavefileasclobResult.isInError() ) {
	    	        	    	returnMess += "Errore nella dmpkRegistrazionedocSavefileasclob per il file " + displayFilename  + " : " + dmpkRegistrazionedocSavefileasclobResult.getDefaultMessage();
	    	        	    	 aLogger.info("Errore nella dmpkRegistrazionedocSavefileasclob per il file " + displayFilename  + " : " + dmpkRegistrazionedocSavefileasclobResult.getDefaultMessage());
	    	        	    } else {
	    	        	    	aLogger.info("dmpkRegistrazionedocSavefileasclob invocata con successo");
	    	        	    }	    	        	    
	        	    	}
					}
				}
				else {
		    		int count = 0;
					for( RebuildedFile rebuildedFile : listRebuildedFile) {
		    				count++;	
		    				
		    				String displayFilename = rebuildedFile.getInfo().getAllegatoRiferimento().getDisplayFilename();
		    				
		    				BigDecimal idDocAllegato = null;
		    				try{
		    					idDocAllegato = creaDocAllegato(loginBean, idUd, session  );
		    				} catch(Exception e){
		    					returnMess += " Errore nella creaDocAllegato() per il file  " + displayFilename +  " : " + e.getMessage();
		    				}		    			
		    			
			    			rebuildedFile.setIdDocumento(idDocAllegato);
			    			VersionaDocumentoOutBean versionaDocResult = versionaDocumentoInTransaction( loginBean, rebuildedFile, session, visureBPMProcessDefName, instanceProcessId, count);
			    			boolean esitoVersionamento = true;
			    			if( versionaDocResult.getErrorCode()!=null ){
								esitoVersionamento = false;
								returnMess += " Errore nel versionamento del file " + displayFilename + " : "+  versionaDocResult.getDefaultMessage();
							}
			    			aLogger.info("Esito versionamento " + esitoVersionamento );
			    			// Se il versionamento e' stato effettuato
			    			if (esitoVersionamento){
			    				if( rebuildedFile.getInfo().getAllegatoRiferimento().getDisplayFilename().toUpperCase().endsWith("XML") ) {
			    	        	    aLogger.info("Chiamo la DmpkRegistrazionedocSavefileasclob con idDOc " + idDocAllegato);
			        	    		DmpkRegistrazionedocSavefileasclobBean dmpkRegistrazionedocSavefileasclobBean = new DmpkRegistrazionedocSavefileasclobBean();
			    	        	    dmpkRegistrazionedocSavefileasclobBean.setCodidconnectiontokenin( loginBean.getToken() );
			    	        	    dmpkRegistrazionedocSavefileasclobBean.setIddocin( idDocAllegato );
			    	        	   
			    	        	    String fileContent = FileUtils.readFileToString(rebuildedFile.getFile());
			    	        	    dmpkRegistrazionedocSavefileasclobBean.setFilecontentin(fileContent );
			    	        	    dmpkRegistrazionedocSavefileasclobBean.setFlgparsecontentin(1);
			    	        	    
			    	        	    final SavefileasclobImpl lDmpkRegistrazionedocSavefileasclob = new SavefileasclobImpl();
			    	        	    lDmpkRegistrazionedocSavefileasclob.setBean(dmpkRegistrazionedocSavefileasclobBean);
			    	        	    session.doWork(new Work() {
		
			    						@Override
			    						public void execute(Connection paramConnection) throws SQLException {
			    							paramConnection.setAutoCommit(false);
			    							lDmpkRegistrazionedocSavefileasclob.execute(paramConnection);
			    						}
			    					});
			    	        	    
			    	        	    StoreResultBean<DmpkRegistrazionedocSavefileasclobBean> dmpkRegistrazionedocSavefileasclobResult = new StoreResultBean<DmpkRegistrazionedocSavefileasclobBean>();
			    					AnalyzeResult.analyze(dmpkRegistrazionedocSavefileasclobBean, dmpkRegistrazionedocSavefileasclobResult);
			    					dmpkRegistrazionedocSavefileasclobResult.setResultBean(dmpkRegistrazionedocSavefileasclobBean);
			    								    					
			    	        	    if( dmpkRegistrazionedocSavefileasclobResult.isInError() ) {
			    	        	    	returnMess += "Errore nella dmpkRegistrazionedocSavefileasclob per il file " + displayFilename  + " : " + dmpkRegistrazionedocSavefileasclobResult.getDefaultMessage();
			    	        	    	 aLogger.info("Errore nella dmpkRegistrazionedocSavefileasclob per il file " + displayFilename  + " : " + dmpkRegistrazionedocSavefileasclobResult.getDefaultMessage() );
			    	        	    } else {
			    	        	    	aLogger.info("dmpkRegistrazionedocSavefileasclob invocata con successo");
			    	        	    }			    	        	   
			        	    	}	
			    			}
		    			
		    		}
				}
			}
				
		}
		catch (Throwable ee) { 
			returnMess += "Errore nella funzione nella salvaAllegati() - " + ee.getMessage() + "\n";
			aLogger.error(returnMess, ee);  			
			throw new Exception(returnMess);	 
		} 
    	
    	return returnMess;    	
    }    
    
    public static BigDecimal creaDocAllegato(AurigaLoginBean loginBean, String idUd, Session session) throws Exception {
    	aLogger.debug("Metodo di creazione documento per il file allegato " );
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		
		DmpkCoreAdddocBean lAdddocInput = new DmpkCoreAdddocBean();
		lAdddocInput.setCodidconnectiontokenin(loginBean.getToken());
		lAdddocInput.setIduserlavoroin(null);
		
		CreaAllegatoBean creaModDocumentoInBean = new CreaAllegatoBean();
		creaModDocumentoInBean.setCodNaturaRelVsUd("ALL");
		if( idUd!=null)
			creaModDocumentoInBean.setIdUd( idUd );
				
		String xmlIn = lXmlUtilitySerializer.bindXml(creaModDocumentoInBean);
		aLogger.debug("xmlIn " + xmlIn );
		lAdddocInput.setAttributiuddocxmlin(xmlIn);
		
		final AdddocImpl lAdddoc = new AdddocImpl();
		lAdddoc.setBean(lAdddocInput);
		session.doWork(new Work() {

			@Override
			public void execute(Connection paramConnection) throws SQLException {
				paramConnection.setAutoCommit(false);
				lAdddoc.execute(paramConnection);
			}
		});
		StoreResultBean<DmpkCoreAdddocBean> lAddDocStoreResultBean = new StoreResultBean<DmpkCoreAdddocBean>();
		AnalyzeResult.analyze(lAdddocInput, lAddDocStoreResultBean);
		lAddDocStoreResultBean.setResultBean(lAdddocInput);
		
		if (lAddDocStoreResultBean.isInError() && StringUtils.isNotBlank(lAddDocStoreResultBean.getDefaultMessage())) {
			String message = lAddDocStoreResultBean.getDefaultMessage();
			aLogger.error(message);
			if (lAddDocStoreResultBean.isInError()) {
				throw new Exception(message);
			} 
		}

		BigDecimal idDoc = lAddDocStoreResultBean.getResultBean().getIddocout();
		aLogger.info("idDoc creato: " + idDoc );
		return idDoc;
	}
	
	private static BigDecimal getIdUserLavoro(AurigaLoginBean pAurigaLoginBean) {
		return StringUtils.isNotBlank(pAurigaLoginBean.getIdUserLavoro()) ? new BigDecimal(pAurigaLoginBean.getIdUserLavoro()) : null;
	}
	
	public static VersionaDocumentoOutBean versionaDocumentoInTransaction(AurigaLoginBean pAurigaLoginBean, RebuildedFile lRebuildedFile,
			Session session, String visureBPMProcessDefName, String idProcessIstance, int count) throws Exception {
		VersionaDocumentoOutBean lVersionaDocumentoOutBean = new VersionaDocumentoOutBean();
		try {

			SueFileStoreBean lFileStoreBean = new SueFileStoreBean();
			if( lRebuildedFile instanceof RebuildedFileStored  )
				lFileStoreBean.setUri(((RebuildedFileStored)lRebuildedFile).getUriStorage());
			lFileStoreBean.setDimensione(lRebuildedFile.getFile().length());
			lFileStoreBean.setDisplayFilename(lRebuildedFile.getInfo().getAllegatoRiferimento().getDisplayFilename());
			lFileStoreBean.setImpronta( lRebuildedFile.getInfo().getAllegatoRiferimento().getImpronta() );
			if( count == 1 && idProcessIstance!=null){
				List<ActivitiProcess> processes = AddUdUtils.getProcessi(visureBPMProcessDefName);
				String idProcessDef = null;
				if( processes!=null ){
					for(ActivitiProcess p : processes){
						idProcessDef = p.getId();
						aLogger.debug("key: " + p.getKey() );
						aLogger.debug("id: " + p.getId() );
						aLogger.debug("key: " + p.getKey() );
						aLogger.debug("name: " + p.getName());
						aLogger.debug("resource: " + p.getResource());
					}
				}
				
				aLogger.debug("idProcessDef: " + idProcessDef );
				if( idProcessDef!=null )
					lFileStoreBean.setIdBPMProcessDef(idProcessDef);
				else 
					lFileStoreBean.setIdBPMProcessDef(visureBPMProcessDefName);
				
				lFileStoreBean.setIdBPMProcessInst(idProcessIstance);
			}
			
			try {
				BeanUtilsBean2.getInstance().copyProperties(lFileStoreBean, lRebuildedFile.getInfo().getAllegatoRiferimento());
			} catch (Exception e) {
				aLogger.warn(e);
			}

			String lStringXml = "";
			try {
				XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
				lStringXml = lXmlUtilitySerializer.bindXml(lFileStoreBean);
				aLogger.debug("attributiVerXml " + lStringXml);
			} catch (Exception e) {
				aLogger.warn(e);
			}

			DmpkCoreAddverdocBean lAddverdocBean = new DmpkCoreAddverdocBean();
			lAddverdocBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
			lAddverdocBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
			lAddverdocBean.setIddocin(lRebuildedFile.getIdDocumento());
			lAddverdocBean.setAttributiverxmlin(lStringXml);

			final AddverdocImpl store = new AddverdocImpl();
			store.setBean(lAddverdocBean);
			aLogger.debug("Chiamo la addVerdoc " + lAddverdocBean);

			LoginService lLoginService = new LoginService();
			lLoginService.login(pAurigaLoginBean);
			session.doWork(new Work() {

				@Override
				public void execute(Connection paramConnection) throws SQLException {
					paramConnection.setAutoCommit(false);
					store.execute(paramConnection);
				}
			});

			StoreResultBean<DmpkCoreAddverdocBean> lAddverdocStoreResultBean = new StoreResultBean<DmpkCoreAddverdocBean>();
			AnalyzeResult.analyze(lAddverdocBean, lAddverdocStoreResultBean);
			lAddverdocStoreResultBean.setResultBean(lAddverdocBean);

			final MarktokenusageImpl lMarktokenusage = new MarktokenusageImpl();
			DmpkLoginMarktokenusageBean lMarktokenusageBean = new DmpkLoginMarktokenusageBean();
			if (pAurigaLoginBean.getToken() != null)
				lMarktokenusageBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
			lMarktokenusageBean.setFlgautocommitin(0);
			lMarktokenusage.setBean(lMarktokenusageBean);
			session.doWork(new Work() {

				@Override
				public void execute(Connection paramConnection) throws SQLException {
					paramConnection.setAutoCommit(false);
					lMarktokenusage.execute(paramConnection);
				}
			});

			if (lAddverdocStoreResultBean.isInError()) {
				aLogger.error(lAddverdocStoreResultBean.getDefaultMessage());
				aLogger.debug(lAddverdocStoreResultBean.getErrorContext());
				aLogger.debug(lAddverdocStoreResultBean.getErrorCode());
				BeanUtilsBean2.getInstance().copyProperties(lVersionaDocumentoOutBean, lAddverdocStoreResultBean);
				return lVersionaDocumentoOutBean;
			}

			

		} catch (Throwable e) {

			if (StringUtils.isNotBlank(e.getMessage())) {
				aLogger.error(e.getMessage(), e);
				lVersionaDocumentoOutBean.setDefaultMessage(e.getMessage());
			} else {
				aLogger.error("Errore generico", e);
				lVersionaDocumentoOutBean.setDefaultMessage("Errore generico");
			}
			return lVersionaDocumentoOutBean;
		}

		return lVersionaDocumentoOutBean;
	}
    
	public Locale getLocale(){
		return new Locale("it", "IT");
	}
	
	private void avviaVisure(WSAddUdConfig wsAddUdConfig, List<AttachWSBean> listaAttachWithInfo) throws Exception {
		String instanceProcessId;
		boolean isVisura = false;

		String visuraTipoProcesso = wsAddUdConfig.getVisureTipoProcesso();
		aLogger.debug("visuraTipoProcesso : " + visuraTipoProcesso);

		for (AttachWSBean attachWSBean : listaAttachWithInfo) {
			if (StringUtils.isNotBlank(attachWSBean.getMimetype())&& attachWSBean.getMimetype().equalsIgnoreCase("application/xml")) {
				List<String> listaValori = AddUdUtils.getCodiceVisura(attachWSBean.getFile());// getValoreTipoProcedimento(file);
				aLogger.info("listaValori :" + listaValori);
				if (listaValori != null) {
					if (visuraTipoProcesso != null && listaValori.contains(visuraTipoProcesso)) {
						isVisura = true;
					}
					break;
				}
			}

		}

		aLogger.info("isVisura " + isVisura);
		if (isVisura && wsAddUdConfig.getVisureBPMProcessDefName() != null) {
			aLogger.info("Provo ad avviare il processo activity " + wsAddUdConfig.getVisureBPMProcessDefName());
			instanceProcessId = AddUdUtils.avviaProcesso(wsAddUdConfig.getVisureBPMProcessDefName());
			aLogger.info("Id dell'istanza di processo activity avviata: " + instanceProcessId);
		}
	}

    
	
        
    /****************************************************************************************************************
	* Funzione per aggiornare un'unita' documentaria.
	* INPUT : 
	*     -  XMLIn : xml con i metadati da aggiornare
	* OUTPUT :     
	*   a) docAttachXML : xml con le informazioni da aggiornare sui file elettronoci  : 
	*	   -- 1: Valore fisso AV: indica il tipo di operazione da fare sul documento corrispondente all'attach
	*	   -- 2: N.ro dell'attach del messaggio SOAP che corrisponde al file da caricare nella repository
	*	   -- 3: Identificativo del documento creato in DB in corrispondenza dell'attachment
	*	   -- 4: Nome del file da caricare (quello con cui mostrarlo e dalla cui estensione si ricava il formato)
	*	   -- 5: (valori 1/0) Se 1 indica che e' la versione pubblicata, se 0 no
	*	   -- 6: (valori 1/0) Se 1 indica che e' la versione deriva da scansione, se 0 no
	*	   -- 7: Codice cha versione (n.ro versione alfanumerico, opzionale)
	*	   -- 8: Note della versione
	*	   -- 9: (valori 1/0) Se 1 indica che la versione deve essere sottoposta a OCR 
	*
	*   b) RegNumDaRichASistEstOut : Lista con gli estremi delle registrazioni/numerazioni richieste da farsi dare da sistemi esterni (XML conforme a schema LISTA_STD.xsd) o da AURIGA stesso tramite API di RegistraUnitaDoc
	*       -- Per ogni registrazioni/numerazioni vi e' un tag Riga che contiene le seguenti colonne:
	*		-- 1: Codice che indica la categoria di registrazione/numerazione (PG = Protocollo Generale, ecc)
	*		-- 2: Sigla che indica il registro di registrazione/numerazione
	*		-- 3: Anno di registrazione/numerazione se diverso da quello corrente
	*		
	*   c) XmlOut : XML con dati di output specifici del WS, restituito solo in caso di successo, conforme a schema Output_UD.xsd
	*   
	**************************************************************************************************************/   
 
	private WSAddUdBean callStoreAddUd(AurigaLoginBean loginBean, String xmlIn, List<AttachWSBean> listaAttach, String flagSportelloImpresaInUnGiorno)
			throws Exception {

		aLogger.debug("Eseguo il WS DmpkWsAddUd.");

		String xml = null;
		String regNumDaRichASistEst = null;
		String listaAttachXml = null;

		Session session = null;
		try {

			if (!listaAttach.isEmpty()) {
				XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
				listaAttachXml = lXmlUtilitySerializer.bindXmlList(listaAttach);
			}

			// Inizializzo l'INPUT
			DmpkWsAddudBean input = new DmpkWsAddudBean();
			input.setCodidconnectiontokenin(loginBean.getToken());
			input.setXmlin(xmlIn);
			input.setAttachmentfilexmin(listaAttachXml);

			session = HibernateUtil.begin();
			Transaction lTransaction = session.beginTransaction();

			final AddudImpl service = new AddudImpl();
			service.setBean(input);
			session.doWork(new Work() {

				@Override
				public void execute(Connection paramConnection) throws SQLException {
					paramConnection.setAutoCommit(false);
					service.execute(paramConnection);
				}
			});
			StoreResultBean<DmpkWsAddudBean> output = new StoreResultBean<DmpkWsAddudBean>();
			AnalyzeResult.analyze(input, output);
			output.setResultBean(input);

			// Eseguo il servizio
			// Addud service = new Addud();
			// StoreResultBean<DmpkWsAddudBean> output = service.execute(loginBean, input);

			if (output.isInError()) {
				throw new Exception(output.getDefaultMessage());
			}

			// leggo XmlOut
			xml = output.getResultBean().getXmlout();

			String idUd = null;
			if (xml == null || xml.equalsIgnoreCase("")) {
				throw new Exception("La store procedure ha ritornato XmlOut nullo");
			}else {
				try {
					idUd = getIdUdFromResponse(xml);
					if(StringUtils.isBlank(idUd)) {
						throw new Exception ("idUd nullo");
					}
				} catch(Exception e){
					aLogger.error("Errore nel parsing dell'xml di response della store: " + e.getMessage(),e);
					throw new Exception("Errore nel parsing dell'xml di response della store" + e.getMessage(),e);
				}
			}

			if( flagSportelloImpresaInUnGiorno!=null && flagSportelloImpresaInUnGiorno.equalsIgnoreCase("1")) {
				salvaXmlImpresaInUnGiorno(loginBean, listaAttach, session, idUd);
			}

			if (session != null)
				session.flush();

			if (lTransaction != null)
				lTransaction.commit();

			// leggo regNumDaRichASistEst
			regNumDaRichASistEst = output.getResultBean().getRegnumdarichasistestout();

			// popolo il bean di out
			WSAddUdBean result = new WSAddUdBean();

			result.setRegNumDaRichASistEst(regNumDaRichASistEst);
			result.setXml(xml);

			return result;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			if (session != null)
				HibernateUtil.release(session);
		}
	}

	private void salvaXmlImpresaInUnGiorno(AurigaLoginBean loginBean, List<AttachWSBean> listaAttach, Session session, String idUd)
			throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, Exception {
		for (AttachWSBean attachBean : listaAttach) {

			if (attachBean.getDisplayFilename().toUpperCase().endsWith("XML")) {
				aLogger.info("Chiamo la DmpkRegistrazionedocSavefileasclob con file ");
				DmpkRegistrazionedocSavefileasclobBean dmpkRegistrazionedocSavefileasclobBean = new DmpkRegistrazionedocSavefileasclobBean();
				dmpkRegistrazionedocSavefileasclobBean.setCodidconnectiontokenin(loginBean.getToken());
				dmpkRegistrazionedocSavefileasclobBean.setNomefilein(attachBean.getDisplayFilename());
				dmpkRegistrazionedocSavefileasclobBean.setIdudin(new BigDecimal(idUd));

				String fileContent = FileUtils.readFileToString(attachBean.getFile());
				dmpkRegistrazionedocSavefileasclobBean.setFilecontentin(fileContent);
				dmpkRegistrazionedocSavefileasclobBean.setFlgparsecontentin(0);

				final SavefileasclobImpl lDmpkRegistrazionedocSavefileasclob = new SavefileasclobImpl();
				lDmpkRegistrazionedocSavefileasclob.setBean(dmpkRegistrazionedocSavefileasclobBean);
				session.doWork(new Work() {

					@Override
					public void execute(Connection paramConnection) throws SQLException {
						paramConnection.setAutoCommit(false);
						lDmpkRegistrazionedocSavefileasclob.execute(paramConnection);
					}
				});

				StoreResultBean<DmpkRegistrazionedocSavefileasclobBean> dmpkRegistrazionedocSavefileasclobResult = new StoreResultBean<DmpkRegistrazionedocSavefileasclobBean>();
				AnalyzeResult.analyze(dmpkRegistrazionedocSavefileasclobBean,
						dmpkRegistrazionedocSavefileasclobResult);
				dmpkRegistrazionedocSavefileasclobResult.setResultBean(dmpkRegistrazionedocSavefileasclobBean);

				if (dmpkRegistrazionedocSavefileasclobResult.isInError()) {
					aLogger.info("Errore nella dmpkRegistrazionedocSavefileasclob per il file "
							+ attachBean.getDisplayFilename() + " : "
							+ dmpkRegistrazionedocSavefileasclobResult.getDefaultMessage());
					throw new Exception("Errore durante il salvataggio degli xml contenuti nel file");
				} else {
					aLogger.info("dmpkRegistrazionedocSavefileasclob invocata con successo");
				}
			}
		}
	}

private String getIdUdFromResponse(String xml) throws Exception{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(xml));
		Document document = builder.parse(is);
		NodeList nList = document.getElementsByTagName("Output_UD");
		String idUd = null;
		for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            aLogger.info("Current Element :" + nNode.getNodeName());
            
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
               Element eElement = (Element) nNode;
               idUd = eElement.getElementsByTagName("IdUD").item(0).getTextContent();
               aLogger.info("---idUd " + idUd);
            }
         }
		return idUd;
	}
    


	/**
     * Genera il file XML di risposta del servizio
     * Questo file viene passato come allegato in caso di successo.
     * @param xmlIn       Contiene xml restutuito dal servizio
     * @param warnMessage Puo' essere valorizzato con eventuali warning
     * @return String stringa XML secondo il formato per il ritorno dell'idud
     */
    private String generaXMLRispostaWS(String xmlIn)  throws Exception {
    
        StringBuffer xml = new StringBuffer();
        String xmlInEsc = null;
        
        try {
        	// ...se il token non e' null
            if (xmlIn != null) {
            	// effettuo l'escape di tutti i caratteri
            	xmlInEsc = eng.util.XMLUtil.xmlEscape(xmlIn);
            }
            
            //xmlInEsc = xmlIn;
            xml.append(xmlInEsc);
            aLogger.debug(xml.toString());
            
        }
        catch (Exception e){
 			throw new Exception(e.getMessage());
 		}        
        return xml.toString();
    }
    
    
    public List<File> getAttachment(boolean flgImpresaInUnGiorno, String pathFileFtp) throws Exception {

		List<File> listaAttach = new ArrayList<File>();
		
		// Leggo gli attach
		DataHandler[] attachments = getMessageDataHandlers();
		
		if(flgImpresaInUnGiorno) {
			listaAttach = recuperaFileImpresaInUnGiorno(attachments, pathFileFtp);
		}else {
			listaAttach = getFileFromDataHandler(attachments);
		}
		
		return listaAttach;
    }
    
    public List<AttachWSBean> getAttachmentWithInfo(AurigaLoginBean loginBean, boolean flgImpresaInUnGiorno, 
    		List<File> listaAttach, String pathFileFtp, String xml) throws Exception {

		List<AttachWSBean> listaAttachWithInfo = new ArrayList<AttachWSBean>();
		
		// Leggo le info degli attach
		
		if(listaAttach!=null && listaAttach.size()>0) {
			//Controllo se attivare le chiamate multi thread a file operation per il calcolo delle info dei file
			if(!(StringUtils.isNotBlank(wsAddUdConfig.getFlgCallFileOpMultiThread()) && wsAddUdConfig.getFlgCallFileOpMultiThread().equalsIgnoreCase("true"))) {
				long t0 = System.currentTimeMillis(); 
				listaAttachWithInfo = calcola_info_file(loginBean, listaAttach, flgImpresaInUnGiorno, xml);
				long t1 = System.currentTimeMillis();
				long tempoImpiegato =  (t1 - t0) / 1000;
				
				aLogger.debug("Tempo impiegato per l'esecuzione delle chiamate a file op: " + tempoImpiegato + " secondi");
			}else {
				long t0 = System.currentTimeMillis(); 
				listaAttachWithInfo = calcola_info_file_multiThreaded(loginBean, listaAttach, flgImpresaInUnGiorno, xml, wsAddUdConfig.getMaxSizePoolThread());
				long t1 = System.currentTimeMillis();
				long tempoImpiegato =  (t1 - t0) / 1000;
				
				aLogger.debug("Tempo impiegato per l'esecuzione delle chiamate a file op: " + tempoImpiegato + " secondi");
			}
		}
		
		
		return listaAttachWithInfo;

	}
	

	private List<File> recuperaFileImpresaInUnGiorno(DataHandler[] attachments, String pathFileFtp) throws Exception {
		
		List<File> listaAttach = new ArrayList<File>();
		InputStream isFileZip = null;

		if (StringUtils.isNotBlank(pathFileFtp)) {
			//Recupero il file zip di impresa in un giorno come riferimento e non dagli attach passati al servizio
			File fileZipOnFtp = new File(pathFileFtp);
			isFileZip = FileUtils.openInputStream(fileZipOnFtp);

		} else {
			if (attachments != null) {
				isFileZip = attachments[0].getInputStream();
			}else {
				throw new Exception("File zip non inviato al servizio");
			}
		}
		
		File zipSueTemp = File.createTempFile("tmp", "");
//		listTempFileToDelete.add(zipSueTemp);
		
		FileUtils.copyInputStreamToFile(isFileZip, zipSueTemp);
		
		listaAttach = spacchettaZipSue(zipSueTemp);
		
		zipSueTemp.delete();

		return listaAttach;

	}


	public WSAttachBean getAttachmentFromFtp(AurigaLoginBean loginBean, String pathAttach, String nomeFile) throws Exception {
		WSAttachBean WSAttachBeanOut = new WSAttachBean();

		//Recupero il file zip contenente gli allegati, che è stato caricato sull ftp montata (pathAttach)
		File allegatiZip = new File(pathAttach+nomeFile);
		
		// Setto gli attach
		DataHandler[] attachments = new DataHandler[1];
		attachments[0] = new DataHandler(new InputStreamDataSource(new FileInputStream(allegatiZip), nomeFile));
		aLogger.info("attachments length " + attachments.length);

		// Leggo le info degli attach
		/**Visto che impresa in un giorno spacchetta uno zip posso usare la stessa funzionalità*/
		List<File> filesArchivio = decomprimi_fileImpresaInUnGiorno(loginBean, attachments);
		List<RebuildedFile> listRebuildedFile = popolo_info_fileImpresaInUnGiorno(loginBean, filesArchivio);
		
		WSAttachBeanOut.setAttachments(attachments);
		WSAttachBeanOut.setListRebuildedFile(listRebuildedFile);

		return WSAttachBeanOut;
	}
	
}
