/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.repository2.jaxws.webservices.updunitadoc;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.activation.DataHandler;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.soap.MTOM;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringEscapeUtils;
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
import org.xml.sax.SAXException;

import it.eng.auriga.database.store.dmpk_utility.bean.DmpkUtilityFindud_jBean;
import it.eng.auriga.database.store.dmpk_utility.store.impl.Findud_jImpl;
import it.eng.auriga.database.store.dmpk_ws.bean.DmpkWsGetfileudtoupdxrectrlBean;
import it.eng.auriga.database.store.dmpk_ws.bean.DmpkWsUpdudBean;
import it.eng.auriga.database.store.dmpk_ws.store.Getfileudtoupdxrectrl;
import it.eng.auriga.database.store.dmpk_ws.store.Updud;
import it.eng.auriga.database.store.dmpk_ws.store.impl.GetfileudtoupdxrectrlImpl;
import it.eng.auriga.database.store.dmpk_ws.store.impl.UpdudImpl;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.function.WSFileUtils;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.beans.SpecializzazioneBean;
import it.eng.auriga.module.business.entity.WSTrace;
import it.eng.auriga.repository2.jaxws.webservices.addunitadoc.WSAddUdConfig;
import it.eng.auriga.repository2.jaxws.webservices.common.JAXWSAbstractAurigaService;
import it.eng.auriga.repository2.jaxws.webservices.util.WSAttachBean;
import it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.Output_UD;
import it.eng.auriga.repository2.util.DBHelperSavePoint;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.document.function.GestioneDocumenti;
import it.eng.document.function.StoreException;
import it.eng.document.function.bean.AllegatiBean;
import it.eng.document.function.bean.AttachWSBean;
import it.eng.document.function.bean.CreaModDocumentoOutBean;
import it.eng.document.function.bean.DelUdDocVerIn;
import it.eng.document.function.bean.DelUdDocVerOut;
import it.eng.document.function.bean.DocumentoXmlOutBean;
import it.eng.document.function.bean.Flag;
import it.eng.document.function.bean.RebuildedFile;
import it.eng.document.storage.DocumentStorage;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.jaxb.variabili.Lista.Riga.Colonna;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.storeutil.AnalyzeResult;
import it.eng.util.FattureUtil;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.xml.XmlUtilitySerializer;

/**
 * @author Ottavio passalacqua
 */


@WebService(targetNamespace = "http://updunitadoc.webservices.repository2.auriga.eng.it",  endpointInterface="it.eng.auriga.repository2.jaxws.webservices.updunitadoc.WSIUpdUd", name = "WSUpdUd")
@MTOM(enabled = true, threshold = 0)
@HandlerChain(file = "../common/handler.xml")
public class WSUpdUd extends JAXWSAbstractAurigaService implements WSIUpdUd{	

    private final String K_SAVEPOINTNAME = "INIZIOWSUPDUD";
 
    private final String K_AGGIORN_VERS = "AV";
    private final String K_MODIF_METADATA = "MM";
    private final String K_DELETE_VERS = "DV";
    private final String K_DELETE_DOC = "DD";
    private final String K_SOST_VERS = "SV"; //Sostituzione di versione
    private final String K_MODIF_MD_VERS = "MV"; //Modifica metadati di versione
    
    WSAddUdConfig wsAddUdConfig;
    
    public static final String _SPRING_BEAN_WSADDUDCONFIG = "WSAddUdConfig";
    
    static Logger aLogger = Logger.getLogger(WSUpdUd.class.getName());    
    
    public WSUpdUd() {
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
					      final String parametriconfigout,
					      final WSTrace wsTraceBean) throws Exception {
    	
    	
    	wsAddUdConfig = (WSAddUdConfig) SpringAppContext.getContext().getBean(_SPRING_BEAN_WSADDUDCONFIG);
    	
    	if(!(StringUtils.isNotBlank(wsAddUdConfig.getFlgNuovaGestione()) && wsAddUdConfig.getFlgNuovaGestione().equalsIgnoreCase("true"))) {
    		return vecchiaGestione(user, token, codiceApplicazione, istanzaAppl, conn, xml, schemaDb, idDominio, tipoDominio, parametriconfigout);
	   }else {
		   
	    String risposta        = null;    
	    String outRispostaWS   = null;
	    String warnRegIn       = "";
	    String errMsg = null;
	    String xmlIn = null;
	    Integer errCode = JAXWSAbstractAurigaService.ERR_ERRORE_APPLICATIVO;
	    
	    List<File> fileAggiunti = new ArrayList<File>();
	    
	//    String attivaCalcoloAsyncAttach = getParametroDB("ATTIVA_CALCOLO_ASYNC_ATTACH_WS_PROT");
	//    boolean flgAttivaCalcoloAsyncAttach = StringUtils.isNotBlank(attivaCalcoloAsyncAttach) && "true".equalsIgnoreCase(attivaCalcoloAsyncAttach) ? true : false;
	    boolean flgAttivaCalcoloAsyncAttach = checkApplicazioneCalcoloAsyncAttach(codiceApplicazione, parametriconfigout);
	    
	    try {
	    	 aLogger.info("Inizio WSUpdUd");
	    	
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
	  		         
	         /*************************************************************
	          * Chiamo il WS e il servizio di AurigaDocument
	          ************************************************************/
	         WSUpdUdBean outWS = new WSUpdUdBean();
	         WSUpdUdOutBean outServizio = new WSUpdUdOutBean();
	         Session session = null;
	         
	         List<AttachWSBean> listaAttachWithInfo = new ArrayList<>();
	         String idUdStr = getIdUdFromRequest(xml, idDominio, codiceApplicazione);
	         try {
	     	 		// Creo la transazione
	        	    Transaction lTransaction = null;
			        SubjectBean subject = SubjectUtil.subject.get();
			    	subject.setIdDominio(loginBean.getSchema());
			    	SubjectUtil.subject.set(subject);
			    	session = HibernateUtil.begin();
			    	lTransaction = session.beginTransaction();
			    	
			    	if(flgAttivaCalcoloAsyncAttach) {
			    		fileAggiunti = gestioneAsincronaAttach(xml, idUdStr, false);
			    	}else {
			    		listaAttachWithInfo = getAttachmentWithInfo(loginBean, getMessageDataHandlers(), xml);
			    	}
	     	 		
	        	 	// Chiamo la store
	        	 	outWS =  callStoreUpdUd(loginBean,xml, listaAttachWithInfo, session);
	        	 	
	        	 	outServizio.setXmlRegOut(outWS.getXml());
	        	 			
				 	if( session!=null )
				 		session.flush();
				 	
				 	if( lTransaction!=null)
				 		lTransaction.commit();
				 	
				 	
					// AURIGA-869 gestione fuori tarnsazione di eventuale fattura
					if (!flgAttivaCalcoloAsyncAttach) {
	
						try {
							session = HibernateUtil.begin();
							lTransaction = session.beginTransaction();
	
							aLogger.debug("Controllo se e' una fattura");
							FattureUtil fattureUtil = new FattureUtil();
							String nomeDocType = fattureUtil.isFattura(user, xml, true, session);
							boolean isFattura = (nomeDocType != null && !"".equals(nomeDocType)) ? true : false;
							aLogger.debug("E' una fattura: " + isFattura);
							// fattura
							if (isFattura) {
								// carico idDoc e uri del primario
								DocumentoXmlOutBean documentoXmlOutBean = fattureUtil.loadDoc(loginBean, idUdStr);
								String idDocStr = documentoXmlOutBean.getIdDocPrimario();
								String tipoProvenienza = "I";
								if (documentoXmlOutBean.getFlgTipoProv() != null && !"".equals(documentoXmlOutBean.getFlgTipoProv())) {
									tipoProvenienza = documentoXmlOutBean.getFlgTipoProv().getDbValue();
								}
								String uri = documentoXmlOutBean.getFilePrimario().getUri();
								String firmato = documentoXmlOutBean.getFilePrimario().getFlgFirmato().getDbValue();
	
	
								WSFileUtils lWSFileUtils = new WSFileUtils();
								File fileToExtract = lWSFileUtils.extract(null, uri);
	
								fattureUtil.gestisciSeFattura(xml, loginBean, idDocStr, firmato, fileToExtract, nomeDocType,
										tipoProvenienza,session);
	
								if (session != null) {
									session.flush();
								}
	
								if (lTransaction != null) {
									lTransaction.commit();
								}
							} else {
								throw new Exception("Non e' una fattura");
							}
						} catch (Exception e) {
							aLogger.warn("Fattura non gestita per errore o warning");
//							outServizio.setWarnRegOut("Fattura non gestita: " + e.getMessage());
							if (lTransaction != null)
								lTransaction.rollback();
						}
					}
					// FINE AURIGA-869				 	
			 	

	
	
		 		}
		 	catch (Exception e){	
		 		if (e instanceof StoreException) {
		    		if(((StoreException) e).getError()!=null){
		    			errCode = ((StoreException) e).getError().getErrorCode();
		    		}
		    	}
		 		if(e.getMessage()!=null)
		 			 errMsg = e.getMessage();
		 		 else
		 			errMsg = "Errore imprevisto.";
		 		
		 		if(flgAttivaCalcoloAsyncAttach) {
		        	 cancellaFileAsync(fileAggiunti, getIdUdFromRequest(xml, idDominio, codiceApplicazione));
		         }
		 		}
	         finally {
					if( session!=null )
						HibernateUtil.release(session);
				}
		 	 if (errMsg==null){
		 		xmlIn     = outServizio.getXmlRegOut();	
		 		warnRegIn = outServizio.getWarnRegOut();
		 	 }
		 	 else{
	 	 		xmlIn = errMsg;
		 	 }
		 	                 
		 	/**************************************************************************
			 * Creo XML di risposta del servzio e lo metto in attach alla response
			 **************************************************************************/
		 	 try {
		 		 	  // Creo XML di risposta
		 			  outRispostaWS = generaXMLRispostaWS(xmlIn, warnRegIn);
		 			
		 			  // Creo la lista di attach
			  		  List<InputStream> lListInputStreams = new ArrayList<InputStream>();
			  		  
		 			  // Converto l'XML
			 		  ByteArrayInputStream inputStreamXml = new ByteArrayInputStream(outRispostaWS.getBytes());
			 		 
			 		  // Aggiungo l'XML
			  		  lListInputStreams.add(inputStreamXml); 
			  		  
			  		  // Salvo gli ATTACH alla response
			  		  attachListInputStream(lListInputStreams); 
		 	     }
		 	 catch (Exception e){
		 			return   generaXMLRisposta( JAXWSAbstractAurigaService.FALLIMENTO, JAXWSAbstractAurigaService.ERR_ERRORE_APPLICATIVO,  e.getMessage(), "", "");
		 		 }   
	        		     
		 	 /*************************************************************
			  * Restituisco XML di risposta del WS
			  ************************************************************/	
		 	 if (errMsg==null){
			 	 risposta = generaXMLRisposta( JAXWSAbstractAurigaService.SUCCESSO, JAXWSAbstractAurigaService.SUCCESSO, "Tutto OK", "", warnRegIn);
			 }
			 else{
			 	 risposta = generaXMLRisposta( JAXWSAbstractAurigaService.FALLIMENTO, errCode,  errMsg, "", "");
			 }
	            	
		     aLogger.info("Fine WSUpdUd");
		    
		     return risposta;
	    	}
	    	catch (Exception excptn) {
	    		aLogger.error("WSUpdUd: " + excptn.getMessage(), excptn);
	    		return   generaXMLRisposta( JAXWSAbstractAurigaService.FALLIMENTO, JAXWSAbstractAurigaService.ERR_ERRORE_APPLICATIVO, JAXWSAbstractAurigaService.ERROR_ERRORE_APPLICATIVO, "", "" );
	    	}
			finally
			{
				try { DBHelperSavePoint.RollbackToSavepoint(conn, K_SAVEPOINTNAME); } catch (Exception ee) {}
				aLogger.info("Fine WSUpdUd serviceImplementation");
			}
	    }
    }
    

	private String vecchiaGestione(String user, String token, String codiceApplicazione, String istanzaAppl,
			Connection conn, String xml, String schemaDb, String idDominio, String tipoDominio,
			String parametriconfigout) {
		
	    String risposta        = null;    
	    String outRispostaWS   = null;
	    String warnRegIn       = "";
	    String errMsg = null;
	    String xmlIn = null;
	    String xmlListaDocRectrlIn = null;
	    Integer errCode = JAXWSAbstractAurigaService.ERR_ERRORE_APPLICATIVO;
	    
	    String xmlSenzaAttach = null;
	    List<File> fileAggiunti = null;
	    
	//    String attivaCalcoloAsyncAttach = getParametroDB("ATTIVA_CALCOLO_ASYNC_ATTACH_WS_PROT");
	//    boolean flgAttivaCalcoloAsyncAttach = StringUtils.isNotBlank(attivaCalcoloAsyncAttach) && "true".equalsIgnoreCase(attivaCalcoloAsyncAttach) ? true : false;
	    boolean flgAttivaCalcoloAsyncAttach = checkApplicazioneCalcoloAsyncAttach(codiceApplicazione, parametriconfigout);
	    
	    try {
	    	 aLogger.info("Inizio WSUpdUd");
	    	
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
	  		         
	         /*************************************************************
	          * Chiamo il WS e il servizio di AurigaDocument
	          ************************************************************/
	         WSUpdUdBean outWS = new WSUpdUdBean();
	         WSUpdUdOutBean outServizio = new WSUpdUdOutBean();
	         WSAttachBean wsAttachNuovi = new WSAttachBean();
	         Session session = null;
	         String idUdStr = getIdUdFromRequest(xml, idDominio, codiceApplicazione);
	         try {
	     	 		// Creo la transazione
	        	    Transaction lTransaction = null;
			        SubjectBean subject = SubjectUtil.subject.get();
			    	subject.setIdDominio(loginBean.getSchema());
			    	SubjectUtil.subject.set(subject);
			    	session = HibernateUtil.begin();
			    	lTransaction = session.beginTransaction();
			    	if(flgAttivaCalcoloAsyncAttach) {
			    		fileAggiunti = gestioneAsincronaAttach(xml, idUdStr, false);
			    	}else {
				    	
			    		// Leggo gli attach nuovi
				    	wsAttachNuovi = getAttachment(loginBean);
				    	
			    		// Creo la lista degli attach gia' archiviati
				    	List<AttachWSUpdUdBean> listaAttachArchiviati = creaListaAttachArchiviati(loginBean, xml, session);
				    	
				    	// Creo la lista degli attach nuovi
				    	List<AttachWSUpdUdBean> listaAttachNuovi = creaListaAttachNuovi(loginBean, xml, wsAttachNuovi);
				    	
				    	// Creo la lista degli attach gia' archiviati + nuovi
				    	List<AttachWSUpdUdBean> listaTuttiAttach= creaListaTuttiAttach(listaAttachArchiviati, listaAttachNuovi);
				    	
		     	 		// Creo l'xml con le info di tutti i file ricontrollati 
				    	xmlListaDocRectrlIn = creaXmlListaTuttiAttach(listaTuttiAttach);
			    	}
	     	 		
	        	 	// Chiamo il WS
	        	 	outWS =  callWS(loginBean,xml, xmlListaDocRectrlIn, session);
	        	 	
	        	 	if(flgAttivaCalcoloAsyncAttach) {
	        	 		outServizio.setXmlRegOut(outWS.getXml());
	        	 	}else {
				    	
	        	 	    // Chiamo il servizio di AurigaDocument
	            	 	outServizio =  eseguiServizio(loginBean, conn, outWS, wsAttachNuovi, session);   
	            	 	
	            	 	String errService = outServizio.getWarnRegOut();
	            	 	errCode = outServizio.getErrorCodeStore();
	            	 	
	            	 	// Se i file non sono stati salvati restituisco errore
	    		        if (errService!=null && !errService.equalsIgnoreCase("")){
	    		        	throw new Exception(errService);
	    		        }
	        	 	}

				 	if( session!=null )
				 		session.flush();
				 	
				 	if( lTransaction!=null)
				 		lTransaction.commit();

				 	// AURIGA-869 gestione fuori tarnsazione di eventuale fattura
					if (!flgAttivaCalcoloAsyncAttach) {
	
						try {
							session = HibernateUtil.begin();
							lTransaction = session.beginTransaction();
	
							aLogger.debug("Controllo se e' una fattura");
							FattureUtil fattureUtil = new FattureUtil();
							String nomeDocType = fattureUtil.isFattura(user, xml, true, session);
							boolean isFattura = (nomeDocType != null && !"".equals(nomeDocType)) ? true : false;
							aLogger.debug("E' una fattura: " + isFattura);
							// fattura
							if (isFattura) {
								// carico idDoc e uri del primario
								DocumentoXmlOutBean documentoXmlOutBean = fattureUtil.loadDoc(loginBean, idUdStr);
								String idDocStr = documentoXmlOutBean.getIdDocPrimario();
								String tipoProvenienza = "I";
								if (documentoXmlOutBean.getFlgTipoProv() != null && !"".equals(documentoXmlOutBean.getFlgTipoProv())) {
									tipoProvenienza = documentoXmlOutBean.getFlgTipoProv().getDbValue();
								}
								String uri = documentoXmlOutBean.getFilePrimario().getUri();
								String firmato = documentoXmlOutBean.getFilePrimario().getFlgFirmato().getDbValue();
	
								WSFileUtils lWSFileUtils = new WSFileUtils();
								File fileToExtract = lWSFileUtils.extract(null, uri);
	
								fattureUtil.gestisciSeFattura(xml, loginBean, idDocStr, firmato, fileToExtract, nomeDocType,
										tipoProvenienza,session);
	
								if (session != null) {
									session.flush();
								}
	
								if (lTransaction != null) {
									lTransaction.commit();
								}
							} else {
								throw new Exception("Non e' una fattura");
							}
						} catch (Exception e) {
							aLogger.warn("Fattura non gestita per errore o warning");
//							outServizio.setWarnRegOut("Fattura non gestita: " + e.getMessage());
							if (lTransaction != null)
								lTransaction.rollback();
						}
					}
					// FINE AURIGA-869				 	
				 	
	        	 	callStoreRispostaWSAddUpd(null, outServizio, loginBean);
	
		 		}
		 	catch (Exception e){	
		 		if (e instanceof StoreException) {
		    		if(((StoreException) e).getError()!=null){
		    			errCode = ((StoreException) e).getError().getErrorCode();
		    		}
		    	}
		 		if(e.getMessage()!=null)
		 			 errMsg = e.getMessage();
		 		 else
		 			errMsg = "Errore imprevisto.";
		 		
		 		if(flgAttivaCalcoloAsyncAttach) {
		        	 cancellaFileAsync(fileAggiunti, idUdStr);
		         }
		 		}
	         finally {
					if( session!=null )
						HibernateUtil.release(session);
				}
		 	 if (errMsg==null){
		 		xmlIn     = outServizio.getXmlRegOut();	
		 		warnRegIn = outServizio.getWarnRegOut();
		 	 }
		 	 else{
	 	 		xmlIn = errMsg;
		 	 }
		 	                 
		 	/**************************************************************************
			 * Creo XML di risposta del servzio e lo metto in attach alla response
			 **************************************************************************/
		 	 try {
		 		 	  // Creo XML di risposta
		 			  outRispostaWS = generaXMLRispostaWS(xmlIn, warnRegIn);
		 			
		 			  // Creo la lista di attach
			  		  List<InputStream> lListInputStreams = new ArrayList<InputStream>();
			  		  
		 			  // Converto l'XML
			 		  ByteArrayInputStream inputStreamXml = new ByteArrayInputStream(outRispostaWS.getBytes());
			 		 
			 		  // Aggiungo l'XML
			  		  lListInputStreams.add(inputStreamXml); 
			  		  
			  		  // Salvo gli ATTACH alla response
			  		  attachListInputStream(lListInputStreams); 
		 	     }
		 	 catch (Exception e){
		 			return   generaXMLRisposta( JAXWSAbstractAurigaService.FALLIMENTO, JAXWSAbstractAurigaService.ERR_ERRORE_APPLICATIVO,  e.getMessage(), "", "");
		 		 }   
	        		     
		 	 /*************************************************************
			  * Restituisco XML di risposta del WS
			  ************************************************************/	
		 	 if (errMsg==null){
			 	 risposta = generaXMLRisposta( JAXWSAbstractAurigaService.SUCCESSO, JAXWSAbstractAurigaService.SUCCESSO, "Tutto OK", "", warnRegIn);
			 }
			 else{
			 	 risposta = generaXMLRisposta( JAXWSAbstractAurigaService.FALLIMENTO, errCode,  errMsg, "", "");
			 }
	            	
		     aLogger.info("Fine WSUpdUd");
		    
		     return risposta;
	    	}
	    	catch (Exception excptn) {
	    		aLogger.error("WSUpdUd: " + excptn.getMessage(), excptn);
	    		return   generaXMLRisposta( JAXWSAbstractAurigaService.FALLIMENTO, JAXWSAbstractAurigaService.ERR_ERRORE_APPLICATIVO, JAXWSAbstractAurigaService.ERROR_ERRORE_APPLICATIVO, "", "" );
	    	}
			finally
			{
				try { DBHelperSavePoint.RollbackToSavepoint(conn, K_SAVEPOINTNAME); } catch (Exception ee) {}
				aLogger.info("Fine WSUpdUd serviceImplementation");
			}
	    
	}
	
	private List<AttachWSBean> getAttachmentWithInfo(AurigaLoginBean loginBean, DataHandler[] messageDataHandlers,
			String xml) throws Exception {

		List<AttachWSBean> listaAttachWithInfo = new ArrayList<AttachWSBean>();
		List<AttachWSBean> listaAttachWithInfoOut = new ArrayList<AttachWSBean>();

		try {
			DataHandler[] attachments = getMessageDataHandlers();

			Document sourceDocument = parseXMLString(xml);

			getAssociazioneFileXmlRequest(sourceDocument, listaAttachWithInfo, loginBean, xml, attachments,
					"VersioneElettronica");
			getAssociazioneFileXmlRequest(sourceDocument, listaAttachWithInfo, loginBean, xml, attachments,
					"DatiVersioneElettronica");

			// Controllo se attivare le chiamate multi thread a file operation per il
			// calcolo delle info dei file
			if (!(StringUtils.isNotBlank(wsAddUdConfig.getFlgCallFileOpMultiThread())
					&& wsAddUdConfig.getFlgCallFileOpMultiThread().equalsIgnoreCase("true"))) {
				long t0 = System.currentTimeMillis();
				listaAttachWithInfoOut = calcola_info_file(loginBean, listaAttachWithInfo, false, xml);
				long t1 = System.currentTimeMillis();
				long tempoImpiegato = (t1 - t0) / 1000;

				aLogger.debug(
						"Tempo impiegato per l'esecuzione delle chiamate a file op: " + tempoImpiegato + " secondi");
			} else {
				long t0 = System.currentTimeMillis();
				listaAttachWithInfoOut = calcola_info_file_multiThreaded(loginBean, listaAttachWithInfo, false, xml,
						wsAddUdConfig.getMaxSizePoolThread());
				long t1 = System.currentTimeMillis();
				long tempoImpiegato = (t1 - t0) / 1000;

				aLogger.debug(
						"Tempo impiegato per l'esecuzione delle chiamate a file op: " + tempoImpiegato + " secondi");
			}

		} catch (Exception e) {
			aLogger.error(e.getMessage(), e);
			throw new Exception(e.getMessage(), e);
		}

		return listaAttachWithInfoOut;
	}
	private List<AttachWSBean> getAttachmentWithInfoFattura(AurigaLoginBean loginBean, DataHandler[] messageDataHandlers,
			String xml) throws Exception {

		List<AttachWSBean> listaAttachWithInfo = new ArrayList<AttachWSBean>();
		List<AttachWSBean> listaAttachWithInfoOut = new ArrayList<AttachWSBean>();

		try {
			DataHandler[] attachments = getMessageDataHandlers();

			Document sourceDocument = parseXMLString(xml);

			getAssociazioneFileXmlRequest(sourceDocument, listaAttachWithInfo, loginBean, xml, attachments,
					"VersioneElettronica");
			getAssociazioneFileXmlRequest(sourceDocument, listaAttachWithInfo, loginBean, xml, attachments,
					"DatiVersioneElettronica");

			// Controllo se attivare le chiamate multi thread a file operation per il
			// calcolo delle info dei file
			if (!(StringUtils.isNotBlank(wsAddUdConfig.getFlgCallFileOpMultiThread())
					&& wsAddUdConfig.getFlgCallFileOpMultiThread().equalsIgnoreCase("true"))) {
				long t0 = System.currentTimeMillis();
				listaAttachWithInfoOut = calcola_info_file(loginBean, listaAttachWithInfo, false, xml);
				long t1 = System.currentTimeMillis();
				long tempoImpiegato = (t1 - t0) / 1000;

				aLogger.debug(
						"Tempo impiegato per l'esecuzione delle chiamate a file op: " + tempoImpiegato + " secondi");
			} else {
				long t0 = System.currentTimeMillis();
				listaAttachWithInfoOut = calcola_info_file_multiThreaded(loginBean, listaAttachWithInfo, false, xml,
						wsAddUdConfig.getMaxSizePoolThread());
				long t1 = System.currentTimeMillis();
				long tempoImpiegato = (t1 - t0) / 1000;

				aLogger.debug(
						"Tempo impiegato per l'esecuzione delle chiamate a file op: " + tempoImpiegato + " secondi");
			}

		} catch (Exception e) {
			aLogger.error(e.getMessage(), e);
			throw new Exception(e.getMessage(), e);
		}

		return listaAttachWithInfoOut;
	}
	
	private void getAssociazioneFileXmlRequest(Document sourceDocument, List<AttachWSBean> listaAttachWithInfo,
			AurigaLoginBean loginBean, String xml, DataHandler[] attachments, String tagFileXmlRequest)
			throws Exception {

		// Estrai informazioni sui file dall'XML di origine.
		NodeList fileList = sourceDocument.getElementsByTagName(tagFileXmlRequest);
		if (fileList != null && fileList.getLength() > 0) {

			for (int i = 0; i < fileList.getLength(); i++) {
				Element fileElement = (Element) fileList.item(i);
				String displayFilename = fileElement.getElementsByTagName("NomeFile") != null
						? fileElement.getElementsByTagName("NomeFile").item(0).getTextContent()
						: "";
				String indexFile = fileElement.getElementsByTagName("NroAttachmentAssociato").item(0).getTextContent();
				File tempFile = null;
				long dimensione;

				DataHandler attach = null;
				try {
					attach = attachments[Integer.valueOf(indexFile) - 1];
				} catch (Exception e1) {
					throw new Exception("Non e' stato trovato un file in input alla posizione: " + i);
				}
				try (InputStream inputStream = attach.getInputStream()) {

					WSFileUtils lWSFileUtils = new WSFileUtils();
					tempFile = lWSFileUtils.saveInputStreamToStorageTmp(
							loginBean.getSpecializzazioneBean().getIdDominio(), inputStream);

					dimensione = tempFile.length();

				} catch (ArrayIndexOutOfBoundsException e) {
					throw new Exception("Errore durante la scrittura del file: " + e.getMessage(), e);
				}

				boolean flgFilePrimario = false;
				if (fileElement.getParentNode().getNodeName().contains("AggVersioneElettronica")
						&& fileElement.getParentNode().getParentNode().getNodeName().contains("UDDaAgg")) {
					flgFilePrimario = true;
				}

				AttachWSBean attachWSBean = new AttachWSBean();
				attachWSBean.setDisplayFilename(displayFilename);
				attachWSBean.setNumeroAttach(indexFile);
				attachWSBean.setDimensione(new BigDecimal(dimensione));
				attachWSBean.setFlgFilePrimario(flgFilePrimario ? "1" : "0");
				attachWSBean.setFile(tempFile);

				listaAttachWithInfo.add(attachWSBean);

			}
		}
	}
	
	private WSUpdUdBean callStoreUpdUd(AurigaLoginBean loginBean, String xml, List<AttachWSBean> listaAttachWithInfo,
			Session session) throws Exception {

		aLogger.debug("Eseguo il WS DMPK_WS->UpdUd.");
		
		String listaAttachXml = null;
		
		if (listaAttachWithInfo!=null && !listaAttachWithInfo.isEmpty()) {
			XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
			listaAttachXml = lXmlUtilitySerializer.bindXmlList(listaAttachWithInfo);
		}
		
		// Inizializzo l'INPUT
		DmpkWsUpdudBean input = new DmpkWsUpdudBean();
		input.setCodidconnectiontokenin(loginBean.getToken());
		input.setXmlin(xml);
		input.setAttachmentfilexmin(listaAttachXml);

		// Eseguo il servizio
		StoreResultBean<DmpkWsUpdudBean> output = null;
		if (session != null) {
			final UpdudImpl service = new UpdudImpl();
			service.setBean(input);
			session.doWork(new Work() {

				@Override
				public void execute(Connection paramConnection) throws SQLException {
					paramConnection.setAutoCommit(false);
					service.execute(paramConnection);
				}
			});
			output = new StoreResultBean<DmpkWsUpdudBean>();
			AnalyzeResult.analyze(input, output);
			output.setResultBean(input);

		} else {
			Updud service = new Updud();
			output = service.execute(loginBean, input);
		}

		if (output.isInError()) {
			aLogger.debug(output.getDefaultMessage());
			aLogger.debug(output.getErrorContext());
			aLogger.debug(output.getErrorCode());
			throw new StoreException(output);
		}

		// leggo XmlOut
		xml = output.getResultBean().getXmlout();

		if (xml == null || xml.equalsIgnoreCase(""))
			throw new Exception("La store procedure ha ritornato XmlOut nullo");

		WSUpdUdBean result = new WSUpdUdBean();
		result.setXml(xml);

		return result;

	}


	private String getIdUdFromRequest(String xml, String idDominio, String codApplicazione) throws Exception {
		String idUDValue;
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new InputSource(new StringReader(xml)));

		NodeList idUDList = document.getElementsByTagName("IdUD");

		if (idUDList.getLength() > 0) {
			Element idUDElement = (Element) idUDList.item(0);
			idUDValue = idUDElement.getTextContent();			
		} else {
			aLogger.error("IdUd non presente nella request");
			/*Recupero l'idUd dagli estremi in input*/
			idUDValue = recuperaIdUdFromEstremiReg(document, idDominio, codApplicazione);
			
		}
		
		return idUDValue;
	}


	private String recuperaIdUdFromEstremiReg(Document document, String idDominio, String codApplicazione)
			throws Exception {
		Element estremiRegNumElement = (Element) document.getElementsByTagName("EstremiRegNum").item(0);
		if (estremiRegNumElement != null) {
			
			Session session = HibernateUtil.begin();
			
			try {
				String categoriaReg = getChildValue(estremiRegNumElement, "CategoriaReg");
				String siglaReg = getChildValue(estremiRegNumElement, "SiglaReg");
				String annoReg = getChildValue(estremiRegNumElement, "AnnoReg");
				String numReg = getChildValue(estremiRegNumElement, "NumReg");

				// cerco l'unità doc
				DmpkUtilityFindud_jBean findUdBean = new DmpkUtilityFindud_jBean();
				findUdBean.setIdspaooin(new BigDecimal(idDominio));
				findUdBean.setCodapplownerin(codApplicazione);
				findUdBean.setCodcategoriaregin(categoriaReg);
				findUdBean.setSiglaregin(siglaReg);
				findUdBean.setAnnoregin(new Integer(annoReg));
				findUdBean.setNumregin(new BigDecimal(numReg));

				final Findud_jImpl findUd = new Findud_jImpl();
				findUd.setBean(findUdBean);

				session.doWork(new Work() {
					@Override
					public void execute(Connection paramConnection) throws SQLException {
						paramConnection.setAutoCommit(false);
						findUd.execute(paramConnection);
					}
				});

				StoreResultBean<DmpkUtilityFindud_jBean> findUdResult = new StoreResultBean<DmpkUtilityFindud_jBean>();
				AnalyzeResult.analyze(findUdBean, findUdResult);
				findUdResult.setResultBean(findUdBean);

				BigDecimal idUd = null;
				if (findUdResult.isInError()) {
					throw new StoreException(findUdResult);
				} else {
					idUd = findUdResult.getResultBean().getIdudout();
					if (idUd == null)
						throw new Exception("Unità documentaria inesistente");
				}
				
				session.flush();
				
				return String.valueOf(idUd);
				
			}finally{
				if(session!=null) {
					HibernateUtil.release(session);
				}
			}
		}
		return null;
	}
	
	 private static String getChildValue(Element parent, String childName) {
	        NodeList children = parent.getElementsByTagName(childName);
	        if (children.getLength() > 0) {
	            Node child = children.item(0);
	            return child.getTextContent();
	        }
	        return null;
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
	* 	 -- 7: Nr. che identifica la versione (alfanumerico, opzionale)
	* 	 -- 8: Note della versione
	* 	 -- 9: (valori 1/0) Se 1 indica che la versione deve essere sottoposta a OCR 
	**************************************************************************************************************/
    
    private WSUpdUdOutBean eseguiServizio(AurigaLoginBean loginBean,  Connection conn,  WSUpdUdBean bean, WSAttachBean wsAttachEinfo,Session session) throws Exception {
    	String xmlOut = null;
    	String docAttXmlOut = null;
    	Output_UD listaFileElettroniciNonSalvatiOut = null;
    	    	
    	// popolo il bean di out
    	WSUpdUdOutBean  ret = new WSUpdUdOutBean();
    	
    	aLogger.debug("Eseguo il servizio di AurigaDocument.");
    	
    	try 
    	  {
    		// Leggo input
        	xmlOut                     = bean.getXml();   	
        	docAttXmlOut               = bean.getOperVsDocXML();  
        	        	
        	// Estraggol'IDUD dall'XML
      		StringReader  srXmlOut = new StringReader(xmlOut);  	
      		listaFileElettroniciNonSalvatiOut = (Output_UD) Unmarshaller.unmarshal(Output_UD.class, srXmlOut);
      
      		// Estraggo le info dei file elettronici dall'XML
      		Lista lsDocAttXmlOut =  null;
      		if ( docAttXmlOut != null) {
            	StringReader srDocAttXmlOut = new StringReader(docAttXmlOut);        	
            	lsDocAttXmlOut = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(srDocAttXmlOut);
            }
      		
      	    // Processo i file elettronici
            if ( lsDocAttXmlOut != null && lsDocAttXmlOut.getRiga().size() > 0) {
                        	
            	StoreResultBean storeResultBean;
            	
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
            	String errore = verificaAllegati_local(attachments, lsDocAttXmlOut);
            	
            	// Se ho trovato errori ritorno errore
            	if (!errore.equals("")) {
            		aLogger.error("Rilevati i seguenti errori: " + errore);
            		throw new Exception(errore);	        		
            	}

            	/*************************************************************
            	 * Salvo i file allegati
            	 *************************************************************/
            	storeResultBean = salvaAllegati(loginBean, conn, listRebuildedFile, attachments, lsDocAttXmlOut, listaFileElettroniciNonSalvatiOut, session);
                                
            	ret.setWarnRegOut(storeResultBean.getDefaultMessage());
            	ret.setErrorCodeStore(storeResultBean.getErrorCode());
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
	* Funzione per aggiornare l'unit�documentaria.
	* INPUT : 
	*     -  XMLIn : xml con i metadati da ggiornare
	*     -  XMLListaDocRectrlIn : XML con i file primario e allegati già archiviati e ricontrollati in sede di update UD
	*      
	* OUTPUT :     
	*   a) OperVsDocXMLOut : xml con le informazioni da aggiornare sui file elettronoci  : 
	*       -- 1: Tipo di operazione da fare sul documento; valori possibili:
	*			    --		AV = Step-version, ovvero aggiunta di nuova versione elettronica (anche la prima)
	*				--		MM = Modifica dei metadati del documento
	*				--		DV = Cancellazione dell'ultima versione elettronica valida e visibile all'utente
	*				--		DD = Cancellazione del documento
	*				--		SV = Sostituzione di versione
	*				--		MV = Modifica metadati di versione
	*		-- 2: N.ro dell'attach del messaggio SOAP che corrisponde al file da caricare nella repository (valorizzato solo se il tipo di operazione e' AV o SV)
	*		-- 3: Identificativo del documento
	*		-- 4: Nome del file della versione (valorizzato solo se il tipo di operazione e' AV o SV o MV); e' il nome con cui mostrarlo e dalla cui estensione si ricava il formato)
	*		-- 5: (valori 1/0) Se 1 indica che e' la versione e' pubblicata, se 0 no (valorizzato solo se il tipo di operazione e' AV o SV o MV)
	*		-- 6: (valori 1/0) Se 1 indica che e' la versione deriva da scansione, se 0 no (valorizzato solo se il tipo di operazione e' AV o SV o MV)
	*		-- 7: Nr. che identifica la versione (opzionale) (valorizzato solo se il tipo di operazione e' AV o SV o MV)
	*		-- 8: Note della versione (valorizzato solo se il tipo di operazione e' AV o SV o MV)
	*		-- 9: (valori 1/0) Se 1 indica che la versione deve essere sottoposta a OCR
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
    private WSUpdUdBean callWS(AurigaLoginBean loginBean, String xmlIn, String xmlListaDocRectrlIn,  Session session) throws Exception {
    	  aLogger.debug("Eseguo il WS DMPK_WS->UpdUd.");
    	
    	  String xml = null;
    	  String operVsDocXML         = null;
    	  String regNumDaRichASistEst =  null;
    	
    		
		  // Inizializzo l'INPUT    		
		  DmpkWsUpdudBean input = new DmpkWsUpdudBean();
		  input.setCodidconnectiontokenin(loginBean.getToken());
		  input.setXmlin(xmlIn);
	      input.setListadocrectrlin(xmlListaDocRectrlIn);
		  
		  // Eseguo il servizio
		  StoreResultBean<DmpkWsUpdudBean> output = null;
		  if( session!=null ){
			  final UpdudImpl service = new UpdudImpl();
			  service.setBean(input);
			session.doWork(new Work() {
	
				@Override
				public void execute(Connection paramConnection) throws SQLException {
					paramConnection.setAutoCommit(false);
					service.execute(paramConnection);
				}
			});
			output = new StoreResultBean<DmpkWsUpdudBean>();
			AnalyzeResult.analyze(input, output);
			output.setResultBean(input);
			
		  } else {
			  Updud service = new Updud();
			 output = service.execute(loginBean, input);
		  }
		  
		  if (output.isInError()){
		  	  aLogger.debug(output.getDefaultMessage());
			  aLogger.debug(output.getErrorContext());
			  aLogger.debug(output.getErrorCode());
			  throw new StoreException(output);
		  }	
		  
		  // leggo  XmlOut
		  xml = output.getResultBean().getXmlout();
		  
		  if (xml== null || xml.equalsIgnoreCase(""))
			  throw new Exception("La store procedure ha ritornato XmlOut nullo");
		      		  
		  // leggo operVsDocXML
		  operVsDocXML = output.getResultBean().getOpervsdocxmlout();
		      		  
		  // leggo regNumDaRichASistEst
		  regNumDaRichASistEst = output.getResultBean().getRegnumdarichasistestout();
		  
	      // popolo il bean di out
		  WSUpdUdBean  result = new WSUpdUdBean();
		  
		  result.setOperVsDocXML(operVsDocXML);
		  result.setRegNumDaRichASistEst(regNumDaRichASistEst);
		  result.setXml(xml);
			  
		  return result;
	
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
            	xmlInEsc = StringEscapeUtils.escapeXml(xmlIn);
            }
            xml.append(xmlInEsc);
            if (warnMessage != null && !warnMessage.equals("")) {
            	xml.append("<WarningMessage>" + warnMessage + "</WarningMessage>\n");
            }
            aLogger.debug(xml.toString());
        }
        catch (Exception e){
 			throw new Exception(e.getMessage());
 		}        
        return xml.toString();
    }

    public static String getContentColonnaNro(Riga r, int nro) {
		
		if (r == null)
			return null;
		
		for (int i = 0; i < r.getColonna().size(); i++) {
			Colonna c = r.getColonna().get(i);
			if (c!=null && c.getNro().intValue() == nro) {
				return c.getContent();
			}
		}
		
		return null;
	}
    

    // Controllo se le informazioni degli allagti sono corrette
    private String verificaAllegati_local(DataHandler[] attachments, Lista lsDocAttXmlOut) throws Exception 
    {
    	String errori ="";    	
    	String fileName = "";
    	
    	boolean allegatoPresenteInOutput[] = new boolean[attachments!=null ? attachments.length : 0];
    	    	
    	try {
			           for (int i = 0; i < lsDocAttXmlOut.getRiga().size(); i++) 
					   {
		    				// prendo la riga i-esima
		    				Riga r = lsDocAttXmlOut.getRiga().get(i);
		    				String attachNum = "";
		    				String operazione= "";        		
		    				for(int j = 0; j < r.getColonna().size(); j++) {
		    					int num = 0;            			
		    					Colonna c = r.getColonna().get(j);
		    					String val = c.getContent();
		    					if ( c!=null )
		    						num = c.getNro().intValue();
						
		    					// -- 1: operazione da fare sul documento
		    					if (num == 1) {	            	
		    						operazione= val;
		    					}	
		                        // -- 2: N.ro dell'attach del messaggio SOAP che corrisponde al file da caricare nella repository
		    					else if (num == 2) {	            	
		    						attachNum = val;
		    					}	
		    				}      

		                    if (operazione.equals(K_AGGIORN_VERS) || operazione.equals(K_SOST_VERS)) 
		                       {		  		        	
		    				        // prendo l'indice dell'attachment
		    				        Integer indAtt = null;

		         				    // indice in formato int
		         				    int index; 
		     
		         				    //provo a convertire in int l'indice estratto
		         	     			try {
		         					     indAtt = new java.lang.Integer(attachNum);
		    	     				     index = indAtt.intValue();
		    				        } 
									catch (Throwable ee) { 
		    					                aLogger.error("Errore nella conversione dell'indice dell'allegato");            				
		    					                errori += "Errore nella conversione dell'indice dell'allegato\n";
		    					                continue;
		    				        }

		    				        // prendo l'attach indicato dalla store procedure
		    				        try {            			
		    					           fileName = attachments[index-1].getName();			// -- 4: Nome del file da caricare
		    				        }    		
		    				            // se l'attach e' sbagliato considero il documento come non caricato e passo oltre
		    				        catch (Exception e) 
									{
		    					         aLogger.error("Non esiste un allegato all'indice " + index);
		         					     errori += "Non esiste un allegato all'indice " + index + "\n";
		         					     continue;
		    	     			    }

		    				        if (allegatoPresenteInOutput[index-1]) {
		    					         aLogger.error("Allegato all'indice " + index + " duplicato nell'output della store" );
		    					         errori += "Allegato all'indice " + index + " duplicato nell'output della store\n";
		    					         continue;
		    				        }
		    				        // tengo traccia del fatto che l'allegato e' considerato nell'ouput della store
		    				        allegatoPresenteInOutput[index-1] = true;
		    			          }
		    	        }
		    	
		    			// controllo che tutti gli attach con file fisico siano stati considerati
		    			for (int i = 0; i < allegatoPresenteInOutput.length; i++) {
		    				if (!allegatoPresenteInOutput[i]) {
		    					// allegato i-esimo non considerato: ha un file fisico?
		    					fileName = attachments[i].getName();
		    					// controllo il nome del file estratto
		    					if (fileName != null && !fileName.equals("")) {
		    						// file fisico presente --> errore
		    						aLogger.error("Allegato all'indice " + i + " non considerato" );
		    						errori += "Allegato all'indice " + i + " non considerato\n";
		    					}
		    				}
		    			}		    						
    	}
    	 catch (Throwable ee) { 
 			errori += "Errore nella funzione nella verificaAllegati() - " + ee.getMessage() + "\n";
 			aLogger.error(errori);
 			throw new Exception(errori);	 
 		}	
    	
    	return errori;    	
    }
    
    // Salvo gli allegati
    private StoreResultBean salvaAllegati(AurigaLoginBean loginBean , Connection conn, List<RebuildedFile> listRebuildedFile , DataHandler[] attachments, Lista lsDocAttXmlOut, Output_UD listaFileElettroniciNonSalvatiOut, Session session) throws Exception
    {
    	StoreResultBean storeResultBean = new StoreResultBean<>();
    	String returnMess ="";
    	Integer errorCode = 100;
    	
    	try {
    			AllegatiBean lAllegatiBean = new AllegatiBean();
    		
    			//booleano per sapere se e' gia' fallita una step
    			boolean unaStepGiaFallita = false;

    			//Array per le Versioni elettroniche non caricate
    			List versioniNonCaricate = new ArrayList();
        	
    			String docId = "";
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
    			Long idUdInt          	 = new Long(0);
    			String  idUd             = "";
    	    	
    			// ciclo sulla lista per caricare i bean  lFilePrimarioBean ,lAllegatiBean
    			for (int i = 0; i < lsDocAttXmlOut.getRiga().size(); i++) {
    				// prendo la riga i-esima        		
    				Riga r = lsDocAttXmlOut.getRiga().get(i);
        		
    				attachNum      = "";				
    				nome           = ""; 
    				flgPubblicata  = "";
    				flgDaScansione = "";
    				nroVersione    = "";
    				note           = "";
    				flgRichOCR     = ""; 
    				operazione     = "";
    				
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
    					// -- 7: nr. che identifica la versione (alfanumerico, opzionale)
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
    					if (!operazione.equalsIgnoreCase("") && !operazione.equals(K_AGGIORN_VERS) && !operazione.equals(K_SOST_VERS) && !operazione.equals(K_MODIF_MD_VERS)) {
    						nome           = "";
    						flgPubblicata  = "";
    						flgDaScansione = "";
    						nroVersione    = "";
    						note           = "";
    						flgRichOCR     = "";
    					}
    					
    					// Se l'operazione e' 'MM' oppure 'MV'
    					// non faccio nulla perche' le modifiche sono state fatte dal servizio Dmpk_Ws->Updud
    					if( !operazione.equalsIgnoreCase("") &&  (operazione.equals(K_MODIF_METADATA)   ||  operazione.equals(K_MODIF_MD_VERS)) ){    						
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
    						idDocPrimario = servizio.leggiIdDocPrimarioWSInTransaction(getLocale(), loginBean, idUd, session);
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
        				
        				// Simulo il file elettronico ( primario o allegato)
        				if (operazione.equals(K_AGGIORN_VERS) || operazione.equals(K_SOST_VERS)) {       					
        					if (flgTipoFile.equalsIgnoreCase("P")){
        						Integer indAttP = new java.lang.Integer(attachNum);
    						    RebuildedFile lRebuildedFile1 = new RebuildedFile();
    						    lRebuildedFile1 = listRebuildedFile.get(indAttP-1);
    						    
    						    if(operazione.equals(K_SOST_VERS))
    						    	lRebuildedFile1.setAnnullaLastVer(true);
    						    else
    						    	lRebuildedFile1.setAnnullaLastVer(false);
    						    
    						    lAllegatiBean = popoloAllegatiBean(lRebuildedFile1, nome, docId, note); 
        					}
        					else if (flgTipoFile.equalsIgnoreCase("A")){
        						Integer indAttA = new java.lang.Integer(attachNum);
        						RebuildedFile lRebuildedFile2 = new RebuildedFile();
        						lRebuildedFile2 = listRebuildedFile.get(indAttA-1);
        						
        						if(operazione.equals(K_SOST_VERS))
    						    	lRebuildedFile2.setAnnullaLastVer(true);
    						    else
    						    	lRebuildedFile2.setAnnullaLastVer(false);
        						
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
    						servizioOut = servizio.addUpdDocsWS(loginBean, flgTipoFile, idUd, idDocFileDecimal, lAllegatiBean, session);
            	    
    						// Se il servizio e' andato in errore restituisco il messaggio di errore 	    	    
    						if(StringUtils.isNotBlank(servizioOut.getDefaultMessage())) {
    							returnMess += (unaStepGiaFallita)?", "+ servizioOut.getDefaultMessage() : " " + servizioOut.getDefaultMessage();
    							errorCode = servizioOut.getErrorCode();
    							break;
    						}
    			
    					}catch (Exception ve) {    			
    						String mess = "------> Fallita la stepVersion per il doc " + idDocFile;    			
    						returnMess += (unaStepGiaFallita)?", "+ mess : " " + mess;
    						unaStepGiaFallita = true;    			
    						aLogger.debug(mess + " - " + ve.getMessage(), ve);
    					}        			
    				}
    			
    				/**************************************************
    				 * SV = sostituzione di versione
    				 **************************************************/
    				else if( operazione.equals(K_SOST_VERS)){    
    					
    					try {        		
    						GestioneDocumenti servizio = new GestioneDocumenti();
    						CreaModDocumentoOutBean servizioOut = new CreaModDocumentoOutBean();
    						servizioOut = servizio.addUpdDocsWS(loginBean, flgTipoFile, idUd, idDocFileDecimal, lAllegatiBean, session);
            	    
    						// Se il servizio e' andato in errore restituisco il messaggio di errore 	    	    
    						if(StringUtils.isNotBlank(servizioOut.getDefaultMessage())) {
    							returnMess += (unaStepGiaFallita)?", "+ servizioOut.getDefaultMessage() : " " + servizioOut.getDefaultMessage();
    							errorCode = servizioOut.getErrorCode();
    							break;
    						}
    			
    					}catch (Exception ve) {    			
    						String mess = "------> Fallita la modifyVersionDoc per il doc " + idDocFile;    			
    						returnMess += (unaStepGiaFallita)?", "+ mess : " " + mess;
    						unaStepGiaFallita = true;    			
    						aLogger.debug(mess + " - " + ve.getMessage(), ve);
    					}
    				}
    			    			
    				/********************************************************************************
    				 * DV = delete dell'ultima versione del file elettronico
    				 *******************************************************************************/
    				else if( operazione.equals(K_DELETE_VERS)){
    					// creo l'input
    					DelUdDocVerIn  input = new DelUdDocVerIn();
    					input.setIdUdDocIn(idDocFile);     				
    					input.setFlgTipoTargetIn("V");
    					input.setNroProgrVerIn(null);                // cancella sempre l'ultima versione
    					input.setFlgCancFisicaIn(new Integer(1));    // effettua sempre la cancellazione fisica
    				    		    	    	
    					// eseguo il servizio
    					try {
    						GestioneDocumenti servizio     = new GestioneDocumenti();
    						DelUdDocVerOut  servizioOut  = new DelUdDocVerOut();    
    						servizioOut = servizio.delUdDocVer(loginBean, input);
    		    		 
    						// Se il servizio e' andato in errore restituisco il messaggio di errore 	    	    
    						if(StringUtils.isNotBlank(servizioOut.getDefaultMessage())) {
    							returnMess += (unaStepGiaFallita)?", "+ servizioOut.getDefaultMessage() : " " + servizioOut.getDefaultMessage();
    							errorCode = servizioOut.getErrorCode();
    							break;
    						}
    					}
    					catch (Exception e){
    						throw new Exception(e.getMessage());	
    					}
    				}
    			
    				/********************************************************************************
    				 * DD = delete del documento
    				 *******************************************************************************/    			
    				else if( operazione.equals(K_DELETE_DOC)){
    				
    					// creo l'input
    					DelUdDocVerIn  input = new DelUdDocVerIn();
    					input.setIdUdDocIn(idDocFile);     				
    					input.setFlgTipoTargetIn("D");               // D = delete doc
    					input.setFlgCancFisicaIn(new Integer(1));    // effettua sempre la cancellazione fisica
    				
    					// eseguo il servizio
    					try {
    							GestioneDocumenti servizio     = new GestioneDocumenti();
   		    		 			DelUdDocVerOut  servizioOut  = new DelUdDocVerOut();    
   		    		 			servizioOut = servizio.delUdDocVer(loginBean, input);
   		    		 
   		    		 			// Se il servizio e' andato in errore restituisco il messaggio di errore 	    	    
   		    		 			if(StringUtils.isNotBlank(servizioOut.getDefaultMessage())) {
   		    		 				returnMess += (unaStepGiaFallita)?", "+ servizioOut.getDefaultMessage() : " " + servizioOut.getDefaultMessage();
   		    		 				errorCode = servizioOut.getErrorCode();
   		    		 				break;
   		    		 			}
    					}
    					catch (Exception e){
    						String mess = "------> Fallita la DelUdDocVer per il doc " + idDocFile;    	    						
    						aLogger.debug(mess + " - " + e.getMessage(), e);
   			 				throw new Exception(e.getMessage());	
   			 			}
    				}
    			
    				/********************************************************************************
    				 * MM = modifyMetadata
    				 *******************************************************************************/ 
    				else if( operazione.equals(K_MODIF_METADATA)){
    				   // non faccio nulla 
    				}
    			
    				/********************************************************************************
    				 * MV = Modifica metadati di versione
    				 *******************************************************************************/ 
    				else if( operazione.equals(K_MODIF_MD_VERS)){    				
     				   // non faccio nulla     				
    				}
    			}
    			
    		}
    		catch (Throwable ee) { 
    			returnMess += "Errore nella funzione nella salvaAllegati() - " + ee.getMessage() + "\n";
    			aLogger.error(returnMess);  			
    			throw new Exception(returnMess);	 
    		}
    	
    		storeResultBean.setDefaultMessage(returnMess);
    		storeResultBean.setErrorCode(errorCode);
    		
    		return storeResultBean;    	
    }
	
    public Locale getLocale(){
		return new Locale("it", "IT");
	}
    
    
    /****************************************************************************************************************
   	* Funzione per aggiornare leggere la lista dei file archiviati che devono essere ri-controllati
   	* INPUT : 
   	*     -  XMLIn : xml con i metadati
   	*     
   	* OUTPUT :     
   	* 	  - ListaDocRectrlIn : Lista con gli estremi dei documenti di cui ricontrollare firme e marche
   	*     
   	**************************************************************************************************************/
    private List<DocRectrBean> getFileUdToUpdXRectrl(AurigaLoginBean loginBean, String xmlIn, Session session) throws Exception {
    	
        aLogger.debug("Eseguo la store DmpkWsGetfileudtoupdxrectrl.");
    	
    	String xmlListaDocRectrlIn = null;
    	
    	try {    		
    		
    		  // *************************************************************************************************
    		  // Chiamo la store GetFileUDToUpdXRectrl per sapere quali file elettronici devo ricontrollare
    		  // *************************************************************************************************
    		  // Inizializzo l'INPUT
    		  DmpkWsGetfileudtoupdxrectrlBean input = new DmpkWsGetfileudtoupdxrectrlBean();
    		  input.setCodidconnectiontokenin(loginBean.getToken());
    		  input.setXmlin(xmlIn);
    		  
    		  // Eseguo il servizio
    		  StoreResultBean<DmpkWsGetfileudtoupdxrectrlBean> output = null;
    		  
    		  if( session!=null ){
    			  final GetfileudtoupdxrectrlImpl service = new GetfileudtoupdxrectrlImpl();
    			  service.setBean(input);
  				  session.doWork(new Work() {

  					@Override
  					public void execute(Connection paramConnection) throws SQLException {
  						paramConnection.setAutoCommit(false);
  						service.execute(paramConnection);
  					}
  				});
  				output = new StoreResultBean<DmpkWsGetfileudtoupdxrectrlBean>();
				AnalyzeResult.analyze(input, output);
				output.setResultBean(input);
				
    		  } else {
    			  Getfileudtoupdxrectrl service = new Getfileudtoupdxrectrl();
    			  output = service.execute(loginBean, input);
    		  }

    		  if (output.isInError()){
    			  throw new Exception(output.getDefaultMessage());	
    		  }	
    		  
    		  // Leggo  ListaDocRectrlIn 
    		  xmlListaDocRectrlIn = output.getResultBean().getListadocrectrlin();
    		  
    		  List<DocRectrBean> listaDocRectrBeanOut = new ArrayList<DocRectrBean>();
    		  
    		  if (xmlListaDocRectrlIn!=null){
    			
    			  Lista lsDocRectrlIn =  null;
    			  StringReader srListaDocRectrlIn = new StringReader(xmlListaDocRectrlIn);        	
    			  lsDocRectrlIn = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(srListaDocRectrlIn);
    			  
    			 // Leggo la lista
    	         if ( lsDocRectrlIn != null && lsDocRectrlIn.getRiga().size() > 0) {
    	        	 for (int i = 0; i < lsDocRectrlIn.getRiga().size(); i++) {
    	        		     	        		  
    	        		 // Prendo DISPLAYNAME, URI , ID_DOC, NRO_VERSIONE del file da controllare
    	        		 String displayName = null;
    	        		 String uriDoc = null;
    	        		 String idDoc = null;
    	        		 String nroAlleg = null;
    	        		 String nroVersioneAlleg = null;
    	        		 String nroAttachmentAssociato = null;
    	        		 String dataRifValiditaFirma = null;
    	        		 
    	        		 // prendo la riga i-esima
		    			 Riga r = lsDocRectrlIn.getRiga().get(i);
		    			 for(int j = 0; j < r.getColonna().size(); j++) {
		    				 int num = 0;            			
	    					 Colonna c = r.getColonna().get(j);
	    					 String val = c.getContent();
	    					 if ( c!=null )
	    					 	num = c.getNro().intValue();   // leggo il valore della colonna 
	    					
	    					 // -- 1: DisplayName file                  
	    					 if (num == 1) {	            	
	    						 displayName= val;               
	    					 }
	    					 // -- 2: URI file da controllare           
	    					 else if (num == 2) {	            	
	    						 uriDoc= val;                    
	    					 }		    					
	    					 // -- 25: ID_DOC file da controllare
	    					 else if (num == 25) {	            	
	    						idDoc = val;   
	    					 }	
	    					 
	    					 // -- 26: NRO_ALLEGATO file da controllare 
	    					 else if (num == 26) {	            	
	    						 nroAlleg = val;                   
	    					 }
	    					
	    					 // -- 27: NRO_VERSIONE file da controllare
	    					 else if (num == 27) {	            	
	    						 nroVersioneAlleg = val;           
	    					 }
	    					 
	    					 // -- 28: NroAttachmentAssociato se � un file nuovo passato in attach
	    					 else if (num == 28) {	            	
	    						 nroAttachmentAssociato = val;           
	    					 }
	    					 
	    					 // -- 29: Data e ora a cui controllare la validit� della firma (in formato FMT_STD_TIMESTAMP)
	    					 else if (num == 29) {	            	
	    						 dataRifValiditaFirma = val;           
	    					 }
		    			 }	 
		    			 
		    			 DocRectrBean lDocRectrBean = new DocRectrBean();
		    			 
		    			 lDocRectrBean.setUriFileDoc(uriDoc);
		    			 lDocRectrBean.setDisplayNameDoc(displayName);
		    			 lDocRectrBean.setIdDocumento(StringUtils.isNotBlank(idDoc) ? new BigDecimal(idDoc) : null);
		    			 lDocRectrBean.setNroAllegato(StringUtils.isNotBlank(nroAlleg) ? new Integer(nroAlleg).intValue() : null);
		    			 lDocRectrBean.setNroVersione(StringUtils.isNotBlank(nroVersioneAlleg) ? new Integer(nroVersioneAlleg).intValue() : null);
		    			 lDocRectrBean.setDataRifValiditaFirma(StringUtils.isNotBlank(dataRifValiditaFirma) ? new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(dataRifValiditaFirma) : null);
		    			 listaDocRectrBeanOut.add(lDocRectrBean);		    			
    	        	 }
    	         }
    		  }    		  
    		  return listaDocRectrBeanOut;    		 
    		}
    	
 		catch (Exception e){
 			throw new Exception(e.getMessage()); 			
 		}
    }
    
    public List<AttachWSUpdUdBean> creaListaTuttiAttach(List<AttachWSUpdUdBean> listaAttachArchiviatiIn, List<AttachWSUpdUdBean> listaAttachNuoviIn) throws Exception {
    	List<AttachWSUpdUdBean> listTuttiAttachOut = new ArrayList<AttachWSUpdUdBean>();
    	// Prendo quelli archiviati 
    	
    	if (listaAttachArchiviatiIn!=null && listaAttachArchiviatiIn.size()>0) {
    		
    		for (int i = 0; i < listaAttachArchiviatiIn.size(); i++) {
    			// Leggo n-esimo attach
        		AttachWSUpdUdBean attachArchiviatoBean = new AttachWSUpdUdBean();
    		    attachArchiviatoBean = listaAttachArchiviatiIn.get(i);    		    
    		    listTuttiAttachOut.add(attachArchiviatoBean);
    		}
    	}
    	
    	// Prendo quelli nuovi
    	if (listaAttachNuoviIn!=null && listaAttachNuoviIn.size()>0) {
    		
    		for (int i = 0; i < listaAttachNuoviIn.size(); i++) {
    			// Leggo n-esimo attach
        		AttachWSUpdUdBean attachNuovoBean = new AttachWSUpdUdBean();
        		attachNuovoBean = listaAttachNuoviIn.get(i);
    		    listTuttiAttachOut.add(attachNuovoBean);
    		}
    	}
    	return listTuttiAttachOut;
    }
        
    /********************************************************************************************************************************************
     * Crea l'XML con i file primario e allegati gi� archiviati e ricontrollati in sede di update UD : 
     * 
     * -- Ogni riga corrisponde ad un documento-file ricontrolato e contiene le colonne:
 	* -- 1:  DisplayName del file
 	* -- 10: Flg file firmato (valori 1/0)
 	* -- 23: Flag di firma non valida alla data (valori 1/0/NULL) (la firma della busta crittografica più esterna)
 	* -- 25: (obbligatorio) ID del documento di cui ri-controllare il file. -1 per indicare che � un documento nuovo
 	* -- 26: (obbligatorio) 0 se primario, altrimenti il numero di allegato
 	* --		Se � un NuovoAllegatoUD con NroAttachmentAssociato valorizzato (ovvero passato in attach) settare -1
 	* -- 27: (obbligatorio) Nro di versione del file da ricontrollare. -1 per indicare che � un file nuovo passato in attach.
 	* -- 28: NroAttachmentAssociato in caso di file passato in attach al servizio
 	**********************************************************************************************************************************************/
    public String creaXmlListaTuttiAttach(List<AttachWSUpdUdBean> listaTuttiAttachIn) throws Exception {
        
		String xmlOut = null;
    	
    	// Converto il bean in XML
    	if (listaTuttiAttachIn!=null && listaTuttiAttachIn.size()>0){
        	Lista lista = new Lista();
    		for(AttachWSUpdUdBean attachWSBean : listaTuttiAttachIn) {			
    			Riga riga = new Riga();
    			
    			// Colonna 1: DisplayName del file
    			Colonna col1 = new Colonna();
    			col1.setNro(new BigInteger("1"));			
    			col1.setContent(attachWSBean.getDisplayFilename());			
    			riga.getColonna().add(col1);
    			
    			// Colonna 10: Flg file firmato (valori 1/0)	    			
    			Colonna col10 = new Colonna();
    			col10.setNro(new BigInteger("10"));			
    			col10.setContent(attachWSBean.getFlgFirmato());			
    			riga.getColonna().add(col10);

    			// Colonna 23: Flag di firma non valida alla data (valori 1/0/NULL) (la firma della busta crittografica pi� esterna)
    			Colonna col23 = new Colonna();
    			col23.setNro(new BigInteger("23"));			
    			col23.setContent((attachWSBean.getFlgFirmaCrittograficaNonValida()!=null) ? (attachWSBean.getFlgFirmaCrittograficaNonValida().equals(Flag.SETTED) ? "1" : "0") : null);
    			riga.getColonna().add(col23);
    			
    			// Colonna 25: (obbligatorio) ID del documento di cui ri-controllare il file. -1 per indicare che � un documento nuovo
    			Colonna col25 = new Colonna();
    			col25.setNro(new BigInteger("25"));			
    			col25.setContent((attachWSBean.getIdDocumentoRectr() != null && !attachWSBean.getIdDocumentoRectr().toString().equalsIgnoreCase("")) ? String.valueOf(attachWSBean.getIdDocumentoRectr().longValue()) : null);				
    			riga.getColonna().add(col25);
    			
    			// Colonna 26: (obbligatorio) 0 se primario, altrimenti il numero di allegato. Se e' un NuovoAllegatoUD con NroAttachmentAssociato valorizzato (ovvero passato in attach) settare -1
    			Colonna col26 = new Colonna();
    			col26.setNro(new BigInteger("26"));				
    			col26.setContent((attachWSBean.getNroAllegRectr() != null && !attachWSBean.getNroAllegRectr().toString().equalsIgnoreCase("")) ? String.valueOf(attachWSBean.getNroAllegRectr().longValue()) : null);	    			
    			riga.getColonna().add(col26);
    			
    			// Colonna 27: (obbligatorio) Nro di versione del file da ricontrollare. -1 per indicare che e' un file nuovo passato in attach.
    			Colonna col27 = new Colonna();
    			col27.setNro(new BigInteger("27"));			
    			col27.setContent((attachWSBean.getNroVersioneAllegRectr() != null && !attachWSBean.getNroVersioneAllegRectr().toString().equalsIgnoreCase(""))  ? String.valueOf(attachWSBean.getNroVersioneAllegRectr().longValue()) : null);
    			riga.getColonna().add(col27);
    			
    			// Colonna 28: NroAttachmentAssociato in caso di file passato in attach al servizio
    			Colonna col28 = new Colonna();
    			col28.setNro(new BigInteger("28"));			
    			col28.setContent((attachWSBean.getNroAttachmentAssociatoRectr() != null && !attachWSBean.getNroAttachmentAssociatoRectr().toString().equalsIgnoreCase("")) ? String.valueOf(attachWSBean.getNroAttachmentAssociatoRectr().longValue()) : null);
    			riga.getColonna().add(col28);
    			
    			lista.getRiga().add(riga);
    		}
    		
    		if (lista!=null && !lista.toString().equalsIgnoreCase("")){
	    		StringWriter sw = new StringWriter();
	    		SingletonJAXBContext.getInstance().createMarshaller().marshal(lista, sw);				
	    		xmlOut = (sw.toString());    		
    		}
    	}
    	return xmlOut;
    }
    
    public List<File> getFileFromAttachment(AurigaLoginBean loginbean) throws Exception {

		List<File> listaAttach = new ArrayList<File>();
		
		// Leggo gli attach
		DataHandler[] attachments = getMessageDataHandlers();
		
		listaAttach = getFileFromDataHandler(attachments, loginbean);
		
		return listaAttach;
    }
    
    
    public List<AttachWSUpdUdBean> creaListaAttachArchiviati(AurigaLoginBean loginBeanIn, String xmlIn, Session session) throws Exception {
        	
    	// Lancio la store GetFileUDToUpdXRectrl
    	List<DocRectrBean> listaDocRectr = getFileUdToUpdXRectrl(loginBeanIn, xmlIn, session);
    	
    	List<AttachWSUpdUdBean> listaAttach = new ArrayList<AttachWSUpdUdBean>();
    	
    	if (listaDocRectr!=null && listaDocRectr.size()>0){
    		
    		for (int i = 0; i < listaDocRectr.size(); i++) {
    			// Leggo n-esimo doc
    			DocRectrBean lDocRectrBean = new DocRectrBean();
    			lDocRectrBean = listaDocRectr.get(i);
    			
    			if (lDocRectrBean!=null){    				
    				if (lDocRectrBean.getUriFileDoc()!=null && !lDocRectrBean.getUriFileDoc().equalsIgnoreCase("")){
    					// Scarico il file 
	    		    	File fileOut; 
	    				try {
	    					fileOut = DocumentStorage.extract(lDocRectrBean.getUriFileDoc(), null);
	    				} catch (StorageException e) {
	    					throw new StorageException(e.getMessage(), e);
	    				}
	    				// Se il servizio e' andato in errore restituisco il messaggio di errore
	    				if (fileOut == null) {			
	    					throw new Exception("Errore durante il recupero del file per la verifica della firma");
	    				}
    				
	    				// Copio il puntamento al file + le info del documento    				
	    				AttachWSUpdUdBean lAttachWSBean = new AttachWSUpdUdBean();
	    				lAttachWSBean.setFile(fileOut);
	    				lAttachWSBean.setDisplayFilename(lDocRectrBean.getDisplayNameDoc());
	    				lAttachWSBean.setIdDocumentoRectr(lDocRectrBean.getIdDocumento());
	    				lAttachWSBean.setNroAllegRectr(lDocRectrBean.getNroAllegato());
	    				lAttachWSBean.setNroVersioneAllegRectr(lDocRectrBean.getNroVersione());
	    				lAttachWSBean.setUri(lDocRectrBean.getUriFileDoc());
	    				lAttachWSBean.setDataRifValiditaFirma(lDocRectrBean.getDataRifValiditaFirma());
	    				listaAttach.add(lAttachWSBean);
    				}
    			}
    		}
    	}
    	
    	// Leggo le info degli attach
    	List<AttachWSUpdUdBean> listaAttachOut = new ArrayList<AttachWSUpdUdBean>();
    	
        if (listaAttach!=null && listaAttach.size()>0){			
    		listaAttachOut = getInfoFileForAttachArchiviati(loginBeanIn, listaAttach);
        }
    	
    	return listaAttachOut;
    }
    
    
    public List<AttachWSUpdUdBean> creaListaAttachNuovi(AurigaLoginBean loginBeanIn, String xmlIn, WSAttachBean wsAttachNuoviIn) throws Exception {
    	
    	List<AttachWSUpdUdBean> listaAttach = new ArrayList<AttachWSUpdUdBean>();
    	
    	List<RebuildedFile> listRebuildedFile   = wsAttachNuoviIn.getListRebuildedFile();
    	
    	if (listRebuildedFile!=null && listRebuildedFile.size()>0){
    		
    		for (int i = 0; i<listRebuildedFile.size(); i++) {
        		 RebuildedFile lRebuildedFile = new RebuildedFile();
				 lRebuildedFile = listRebuildedFile.get(i);
				 File fileOut = lRebuildedFile.getFile();
				 
				 // Copio il puntamento al file    				
				 AttachWSUpdUdBean lAttachWSBean = new AttachWSUpdUdBean();
				 lAttachWSBean.setFile(fileOut);
				 listaAttach.add(lAttachWSBean);    	
        	}
    	}
    	
    	// Leggo le info degli attach
    	List<AttachWSUpdUdBean> listaAttachOut = new ArrayList<AttachWSUpdUdBean>();
    	
    	if (listaAttach!=null && listaAttach.size()>0){	
    	 	listaAttachOut = getInfoFileForAttachNuovi(loginBeanIn, listaAttach, xmlIn);
        }
    	
    	return listaAttachOut;
    }
 
    
    public List<AttachWSUpdUdBean> getInfoFileForAttachArchiviati(AurigaLoginBean loginBeanIn, List<AttachWSUpdUdBean> listaAttachIn) throws Exception {
		
    	String xmlIn = null;
    	
		List<AttachWSUpdUdBean> listaAttachWSBeanOut = new ArrayList<AttachWSUpdUdBean>();
	
		try {
			for (int i = 0; i<listaAttachIn.size(); i++) {
				
				AttachWSUpdUdBean attachWSBeanIn = listaAttachIn.get(i);
				
				AttachWSUpdUdBean attachWSBeanOut = buildAttachArchiviatiWSBean(attachWSBeanIn, xmlIn, i, loginBeanIn);
			
				listaAttachWSBeanOut.add(attachWSBeanOut);
			}

		} catch (Exception e) {
			throw new Exception("ERRORE nella funzione getInfoFileForAttachArchiviati = " + e.getMessage());
		}
		
		return listaAttachWSBeanOut;
    }
    
    public static AttachWSUpdUdBean buildAttachArchiviatiWSBean(AttachWSUpdUdBean attachWSBeanIn, String xmlIn, int indiceFileIn, AurigaLoginBean loginBeanIn) throws Exception {
		
    	AttachWSUpdUdBean attachWSBeanOut = new AttachWSUpdUdBean();
		
		try {
				String uriFile = DocumentStorage.storeInputTemp(FileUtils.openInputStream(attachWSBeanIn.getFile()), null);

				InfoFileUtility lFileUtility = new InfoFileUtility();
				
				// Passo la Data e ora a cui controllare la validit� della firma (in formato FMT_STD_TIMESTAMP)
				MimeTypeFirmaBean lMimeTypeFirmaBean = lFileUtility.getInfoFromFileNoOut(attachWSBeanIn.getFile().toURI().toString(), attachWSBeanIn.getDisplayFilename(), false, (attachWSBeanIn.getDataRifValiditaFirma()!=null ? attachWSBeanIn.getDataRifValiditaFirma() : null), false);
			
				
                if (lMimeTypeFirmaBean!=null){
					
                	// -- 7: impronta				
    				attachWSBeanOut.setImpronta(lMimeTypeFirmaBean.getImpronta());
    				
    				// -- 8: algoritmo calcolo impronta
    				attachWSBeanOut.setAlgoritmo(lMimeTypeFirmaBean.getAlgoritmo());
    				
    				// -- 9: encoding di calcolo impronta
    				attachWSBeanOut.setEncodingImpronta(lMimeTypeFirmaBean.getEncoding());
    				
    				// -- 10: Flg file firmato (valori 1/0)
    				attachWSBeanOut.setFlgFirmato(lMimeTypeFirmaBean.isFirmato() ? "1" : "0");
                	
    				// -- 23: Flag di firma non valida alla data (valori 1/0/NULL) (la firma della busta crittografica pi� esterna)
    				attachWSBeanOut.setFlgFirmaCrittograficaNonValida(!lMimeTypeFirmaBean.isFirmaValida() ? Flag.SETTED : Flag.NOT_SETTED);
    			
				}

				// -- 1: DisplayName del file (eventualmente rinominato per rendere coerente l'estensione con il formato riconosciuto)
				attachWSBeanOut.setDisplayFilename(attachWSBeanIn.getDisplayFilename());

				// -- 2: URI del file salvato su archivio definitivo in notazione storageUtil
				attachWSBeanOut.setUri(uriFile);					

                // -- 6: dimensione
				attachWSBeanOut.setDimensione(new BigDecimal(attachWSBeanIn.getFile().length()));
				
				// -- 25: (obbligatorio) ID del documento di cui ri-controllare il file. -1 per indicare che � un documento nuovo
				attachWSBeanOut.setIdDocumentoRectr(attachWSBeanIn.getIdDocumentoRectr());
				
				// -- 26: (obbligatorio) 0 se primario, altrimenti il numero di allegato
				attachWSBeanOut.setNroAllegRectr(attachWSBeanIn.getNroAllegRectr());

				// -- 27: (obbligatorio) Nro di versione del file da ricontrollare. -1 per indicare che � un file nuovo passato in attach.
				attachWSBeanOut.setNroVersioneAllegRectr(attachWSBeanIn.getNroVersioneAllegRectr());

				
				attachWSBeanOut.setFile(attachWSBeanIn.getFile());
			
				return attachWSBeanOut;
			
		} catch (Exception e) {
			aLogger.error("Errore nel calcolo delle info per il file: " + attachWSBeanIn.getDisplayFilename() + " Error: " + e.getMessage(), e);
	        throw new Exception("Errore nel calcolo delle info per il file: " + attachWSBeanIn.getDisplayFilename() + " Error: " + e.getMessage(), e);
		}
	}
    
    public List<AttachWSUpdUdBean> getInfoFileForAttachNuovi(AurigaLoginBean loginBeanIn, List<AttachWSUpdUdBean> listaAttachIn, String xmlIn) throws Exception {
		
		List<AttachWSUpdUdBean> listaAttachWSBeanOut = new ArrayList<AttachWSUpdUdBean>();
	
		try {
			for (int i = 0; i<listaAttachIn.size(); i++) {
				
				AttachWSUpdUdBean attachWSBeanIn = listaAttachIn.get(i);

				AttachWSUpdUdBean attachWSBeanOut = buildAttachNuoviWSBean(attachWSBeanIn, xmlIn, i, loginBeanIn);
				
				listaAttachWSBeanOut.add(attachWSBeanOut);
			}

		} catch (Exception e) {
			throw new Exception("ERRORE nella funzione getInfoFileForAttachNuovi = " + e.getMessage());
		}
		
		return listaAttachWSBeanOut;
    }
    
    
    public static AttachWSUpdUdBean buildAttachNuoviWSBean(AttachWSUpdUdBean attachWSBeanIn, String xmlIn, int indiceFileIn, AurigaLoginBean loginBeanIn) throws Exception {
    	
    	AttachWSUpdUdBean attachWSBeanOut = new AttachWSUpdUdBean();
		
		String nomeFile = null;				
		
		try {
				nomeFile = getNomeFileFromXml(xmlIn, indiceFileIn+1);
				
				String uriFile = null; //DocumentStorage.storeInputTemp(FileUtils.openInputStream(attachWSBeanIn.getFile()), null);

				InfoFileUtility lFileUtility = new InfoFileUtility();
				
				// Controllo la validit� della firma alla data odierna.
				Date today = new Date();
				MimeTypeFirmaBean lMimeTypeFirmaBean = lFileUtility.getInfoFromFileNoOut(attachWSBeanIn.getFile().toURI().toString(), nomeFile, false, today, false);
				
				
				if (lMimeTypeFirmaBean!=null){
					
					// -- 7: impronta				
    				attachWSBeanOut.setImpronta(lMimeTypeFirmaBean.getImpronta());
    				
    				// -- 8: algoritmo calcolo impronta
    				attachWSBeanOut.setAlgoritmo(lMimeTypeFirmaBean.getAlgoritmo());
    				
    				// -- 9: encoding di calcolo impronta
    				attachWSBeanOut.setEncodingImpronta(lMimeTypeFirmaBean.getEncoding());
    				
    				// -- 10: Flg file firmato (valori 1/0)
    				attachWSBeanOut.setFlgFirmato(lMimeTypeFirmaBean.isFirmato() ? "1" : "0");
                	
    				// -- 23: Flag di firma non valida alla data (valori 1/0/NULL) (la firma della busta crittografica pi� esterna)
    				attachWSBeanOut.setFlgFirmaCrittograficaNonValida(!lMimeTypeFirmaBean.isFirmaValida() ? Flag.SETTED : Flag.NOT_SETTED);
    			
				}

				// -- 1: DisplayName del file (eventualmente rinominato per rendere coerente l'estensione con il formato riconosciuto)
				attachWSBeanOut.setDisplayFilename(nomeFile);

				// -- 2: URI del file salvato su archivio definitivo in notazione storageUtil
				attachWSBeanOut.setUri(uriFile);					

				// -- 3: Nro di attachment
				attachWSBeanOut.setNumeroAttach(String.valueOf(indiceFileIn+1));

                // -- 6: dimensione
				attachWSBeanOut.setDimensione(new BigDecimal(attachWSBeanIn.getFile().length()));
				
				// -- 25: (obbligatorio) ID del documento di cui ri-controllare il file. -1 per indicare che � un documento nuovo
				attachWSBeanOut.setIdDocumentoRectr(new BigDecimal(-1));
				
				// -- 26: (obbligatorio) 0 se primario, altrimenti il numero di allegato
				attachWSBeanOut.setNroAllegRectr(new Integer(-1));

				// -- 27: (obbligatorio) Nro di versione del file da ricontrollare. -1 per indicare che � un file nuovo passato in attach.
				attachWSBeanOut.setNroVersioneAllegRectr(new Integer(-1));
				
				// -- 28: NroAttachmentAssociato in caso di file passato in attach al servizio
				attachWSBeanOut.setNroAttachmentAssociatoRectr(indiceFileIn+1);
				
				attachWSBeanOut.setFile(attachWSBeanIn.getFile());
			
				return attachWSBeanOut;
				
		} catch (Exception e) {
			aLogger.error("Errore nel calcolo delle info per il file: " + nomeFile + " Error: " + e.getMessage(), e);
	        throw new Exception("Errore nel calcolo delle info per il file: " + nomeFile + " Error: " + e.getMessage(), e);
		}
	}
  
    public AttachWSUpdUdBean buildAttachNuoviWSBean(List<AttachWSUpdUdBean> attachWSBeanIn) throws Exception {
    	
    	AttachWSUpdUdBean attachWSBeanOut = new AttachWSUpdUdBean();
		
		String nomeFile = null;				
		
		try {
			for (Iterator iterator = attachWSBeanIn.iterator(); iterator.hasNext();) {
				AttachWSUpdUdBean attachWSUpdUdBean = (AttachWSUpdUdBean) iterator.next();
				
			if (attachWSUpdUdBean.getNroAllegRectr() == 0) {
				// -- 3: Nro di attachment
				attachWSBeanOut.setNumeroAttach(attachWSUpdUdBean.getNumeroAttach());
	
				// -- 26: (obbligatorio) 0 se primario, altrimenti il numero di allegato
				attachWSBeanOut.setNroAllegRectr(attachWSUpdUdBean.getNroAllegRectr());

				
				attachWSBeanOut.setFile(attachWSUpdUdBean.getFile());
			}
			}

				return attachWSBeanOut;			
		} catch (Exception e) {
			aLogger.error("Errore nel calcolo delle info per il file: " + nomeFile + " Error: " + e.getMessage(), e);
	        throw new Exception("Errore nel calcolo delle info per il file: " + nomeFile + " Error: " + e.getMessage(), e);
		}
	}

    private void cancellaFileAsync(List<File> fileAggiunti, String nomeCartellaAttachAsync) {

		String rootFolderAttach = getParametroDB("ROOT_PATH_ATTACH_ASYNC_WS_ADD_UPD_UD");
		   
		try {
			File folderAttachAsync= new File(rootFolderAttach + File.separator + nomeCartellaAttachAsync);
			
			if(folderAttachAsync.exists()) {
				for(File fileAggiunto : fileAggiunti) {
					for(File file : folderAttachAsync.listFiles()) {
						if(fileAggiunto.getName().equals(file.getName())) {
							rimuoviDaFileIndice(rootFolderAttach + File.separator + nomeCartellaAttachAsync + File.separator + "indice.xml", fileAggiunto.getName());
							if(!file.delete())
								throw new Exception("Il file " + file.getName() + " non e' stato cancellato");
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			aLogger.error("Errore durante la cancellazione dei file Async: " + e.getMessage(),e);
		}		
	}
	
	private void rimuoviDaFileIndice(String pathFileIndice, String fileToRemove) throws Exception {
		try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(pathFileIndice);

            NodeList fileList = doc.getElementsByTagName("File");

            for (int i = 0; i < fileList.getLength(); i++) {
                Element fileElement = (Element) fileList.item(i);
                Node displayFilenameNode = fileElement.getElementsByTagName("DisplayFilename").item(0);
                
                if (displayFilenameNode != null) {
                    String displayFilename = displayFilenameNode.getTextContent();
                    
                    if (displayFilename.equals(fileToRemove)) {
                        // Rimuovi il tag File se il DisplayFilename corrisponde
                        Node parent = fileElement.getParentNode();
                        parent.removeChild(fileElement);
                    }
                }
            }

            // Salva le modifiche nel file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(pathFileIndice));
            transformer.transform(source, result);

        } catch (Exception e) {
			throw new Exception("Errore durante la cancellazione del file " + fileToRemove + " dal file indice");
        }
		
	}
   
	public static String getNomeFileFromXml(String xmlIn, int numAttachIn) throws SAXException, IOException, ParserConfigurationException{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(xmlIn));
		Document document = builder.parse(is);
		
		String nomeFileOut = "";
		
		// Estraggo tutti i tag <DatiVersioneElettronica>
		NodeList nList1 = document.getElementsByTagName("DatiVersioneElettronica");
        String nomeFile1 = null;
		for (int temp = 0; temp < nList1.getLength(); temp++) {
            Node nNode = nList1.item(temp);
            aLogger.info("Current Element :" + nNode.getNodeName());
            
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
               Element eElement = (Element) nNode;
               
               // Leggo il tag <NroAttachmentAssociato>
               String numeroAttachXml = eElement.getElementsByTagName("NroAttachmentAssociato").item(0).getTextContent();
               aLogger.info("---NroAttachmentAssociato " + numeroAttachXml);
               
               // Leggo il tag <NomeFile>
               String nomeFileXml = eElement.getElementsByTagName("NomeFile").item(0).getTextContent();
               aLogger.info("---NomeFile " + nomeFileXml);
               
               // 
               if( numeroAttachXml!=null && numeroAttachXml.equalsIgnoreCase(""+numAttachIn)){
            	   nomeFile1 = nomeFileXml;
               }
            }
        }
				
		nomeFileOut = nomeFile1;
		
		// Se il non ci sono <DatiVersioneElettronica> il nomeFile e' null, allora cerco il tag <VersioneElettronica>
		String nomeFile2 = "";
		
		if (StringUtils.isBlank(nomeFileOut)) {
			NodeList nList2 = document.getElementsByTagName("VersioneElettronica");
	        
			for (int temp = 0; temp < nList2.getLength(); temp++) {
	            Node nNode = nList2.item(temp);
	            aLogger.info("Current Element :" + nNode.getNodeName());
	            
	            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	               Element eElement = (Element) nNode;
	               
   	               // Leggo il tag <NroAttachmentAssociato>
	               String numeroAttachXml = eElement.getElementsByTagName("NroAttachmentAssociato").item(0).getTextContent();
	               aLogger.info("---NroAttachmentAssociato " + numeroAttachXml);
	               
	               // Leggo il tag <NomeFile>
	               String nomeFileXml = eElement.getElementsByTagName("NomeFile").item(0).getTextContent();
	               aLogger.info("---NomeFile " + nomeFileXml);
	               
	               // Confronto il nr. dell'allegato in inpur con quello dell'xml  
	               if( numeroAttachXml!=null && numeroAttachXml.equalsIgnoreCase(String.valueOf(numAttachIn))){
	            	   // Se corrisponde allora prendo il nome del file
	            	   nomeFile2 = nomeFileXml;
	               }
	            }
	         }
		}
		
		if (StringUtils.isNotBlank(nomeFile2)) {
			nomeFileOut = nomeFile2;
		}
			
		return nomeFileOut;
	}
	
 
	
}
