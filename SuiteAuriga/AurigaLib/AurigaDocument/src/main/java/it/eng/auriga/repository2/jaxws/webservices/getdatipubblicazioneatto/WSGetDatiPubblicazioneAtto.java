/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import it.eng.auriga.database.store.dmpk_ws.bean.DmpkWsGetdatipubblicazioneattoBean;
import it.eng.auriga.database.store.dmpk_ws.store.Getdatipubblicazioneatto;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.beans.SpecializzazioneBean;
import it.eng.auriga.module.business.entity.WSTrace;
import it.eng.auriga.repository2.util.DBHelperSavePoint;
import it.eng.document.function.RecuperoFile;
import it.eng.document.function.bean.FileExtractedOut;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.jaxb.variabili.Lista.Riga.Colonna;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import java.util.List;
import it.eng.auriga.repository2.jaxws.webservices.common.JAXWSAbstractAurigaService;

/**
 * @author Ottavio passalacqua
 */

@WebService(targetNamespace = "http://getdatipubblicazioneatto.webservices.repository2.auriga.eng.it",  endpointInterface="it.eng.auriga.repository2.jaxws.webservices.getdatipubblicazioneatto.WSIGetDatiPubblicazioneAtto", name = "WSGetDatiPubblicazioneAtto")
@MTOM(enabled = true, threshold = 0)
@HandlerChain(file = "../common/handler.xml")
public class WSGetDatiPubblicazioneAtto extends JAXWSAbstractAurigaService implements WSIGetDatiPubblicazioneAtto{	

    private final String K_SAVEPOINTNAME = "INIZIO_WSGetDatiPubbAtto";
                                            
    static Logger aLogger = Logger.getLogger(WSGetDatiPubblicazioneAtto.class.getName());    
    
    public WSGetDatiPubblicazioneAtto() {
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
    	
    String risposta = null;
    String outRispostaWS = null;
    WSGetDatiPubblicazioneAttoBean outServizio = new WSGetDatiPubblicazioneAttoBean();
    String errMsg = null;
    String xmlIn = null;
    
    try {
    	
    	 aLogger.info("Inizio WSGetDatiPubblicazioneAtto");
    	
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
  		WSGetDatiPubblicazioneAttoBean outWS = new WSGetDatiPubblicazioneAttoBean();
         try {
        	 // Chiamo il WS        	 
        	 outWS =  callWS(loginBean,xml);
        	         	 
 			 // Chiamo il servizio di AurigaDocument
 			 outServizio =  eseguiServizio(loginBean,outWS); 	 		
	 		}
	 	catch (Exception e){	 
	 		if(e.getMessage()!=null)
	 			 errMsg = "Errore = " + e.getMessage();
	 		 else
	 			errMsg = "Errore imprevisto.";	 			
	 		}
          
	 	if (errMsg==null){
 	  		xmlIn = outServizio.getXml();	
 	 	 }
 	 	 else{
 	 		xmlIn = errMsg;
 	 	 }
    	                 	 	 	
	 	/**************************************************************************
		 * Creo XML di risposta del servzio e lo restituisco come output del ws
		 * I file li metto in attach alla response
		 **************************************************************************/
	 	ByteArrayInputStream inputStreamXml = null;
	 	InputStream inputStreamFile = null;
	 	try {
	 		  // Creo XML di risposta
	 		  outRispostaWS = generaXMLRispostaWS(xmlIn);
	 		   
	 		  // Creo la lista di attach
	  		  List<InputStream> lListInputStreams = new ArrayList<InputStream>();
	  		  
	  		  // Aggiungo i FILE ATTACH	  		  	
	 		  if(outServizio!=null && outServizio.getExtractedFileList() != null && outServizio.getExtractedFileList().size()> 0 ){	 			  
	 			List<File> extractedFileList = new ArrayList<File>(); 	 	
	 			extractedFileList = outServizio.getExtractedFileList();
	 			
	 		    // Ciclo sulla lista dei FILE ATTACH	 			
	 			for (File lFile : extractedFileList){
	 				 inputStreamFile = null;
	 				 inputStreamFile = new FileInputStream(lFile);	 				
	 			     // Aggiungo il FILE
			  		 lListInputStreams.add(inputStreamFile);
	 			}	 	 			
	 			// Salvo gli ATTACH alla response
		  		attachListInputStream(lListInputStreams);	 			 	 			 	 			 										  		 
	 		  }
	  	    }
	 		catch (Exception e){
	 			if(e.getMessage()!=null)
		 			 errMsg = "Errore = " + e.getMessage();
		 		 else
		 			errMsg = "Errore imprevisto.";	
	 		} 
	 		finally {
	 			// Chiudo gli stream	 		
	 			if(inputStreamXml != null) {
					try {
						inputStreamXml.close(); 
					} 
					catch (Exception e) {
						if(e.getMessage()!=null)
							errMsg = "Errore = " + e.getMessage();
						else
							errMsg = "Errore imprevisto.";	
					}
				}	 			
	 		    // Chiudo gli stream	 		
	 			if(inputStreamFile != null) {
					try {
						inputStreamFile.close(); 
					} 
					catch (Exception e) {
						if(e.getMessage()!=null)
							errMsg = "Errore = " + e.getMessage();
						else
							errMsg = "Errore imprevisto.";	
					}
				}	 			
	 		}
        	
	 	/*************************************************************
		 * Restituisco XML di risposta del WS
		 ************************************************************/	
	 	 if (errMsg==null){
	 	 		// Restituisco l'xml restituito dalla store
	 		 	risposta = generaXMLRisposta(outRispostaWS);	 	 	
	 	 }
	 	 else{
	 	 		risposta = generaXMLRisposta( JAXWSAbstractAurigaService.FALLIMENTO, JAXWSAbstractAurigaService.ERR_ERRORE_APPLICATIVO,  errMsg, "", "");
	 	 }
	 			        	
	     aLogger.info("Fine WSGetDatiPubblicazioneAtto");
	     
	     return risposta;
    }
  
    catch (Exception excptn) {
        aLogger.error("WSGetDatiPubblicazioneAtto: " + excptn.getMessage(), excptn);
        return   generaXMLRisposta( JAXWSAbstractAurigaService.FALLIMENTO, JAXWSAbstractAurigaService.ERR_ERRORE_APPLICATIVO, JAXWSAbstractAurigaService.ERROR_ERRORE_APPLICATIVO, "", "" );
        //throw excptn;
	}
	finally
	{
    	try { DBHelperSavePoint.RollbackToSavepoint(conn, K_SAVEPOINTNAME); } catch (Exception ee) {}
	    aLogger.info("Fine WSGetDatiPubblicazioneAtto serviceImplementation");
	}

    }
    
    private WSGetDatiPubblicazioneAttoBean eseguiServizio(AurigaLoginBean loginBean, WSGetDatiPubblicazioneAttoBean bean) throws Exception {
    	aLogger.debug("Eseguo il servizio di AurigaDocument.");
    	
    	List<File> extractedFileList = new ArrayList<File>(); 
    	     	
    	// Ciclo sulla lista degli iddoc    	
    	List<RelatePubblBean> listRelatePubblBean = new ArrayList<RelatePubblBean>();      	  
    	listRelatePubblBean = bean.getDocumentlist();
    	
    	if(listRelatePubblBean != null && listRelatePubblBean.size()> 0){
    		    		    			
    			for (RelatePubblBean lRelatePubblBean : listRelatePubblBean){    				
    									
					// creo l'input
					String uriFileIn       = (lRelatePubblBean.getUriFile() != null) ? (lRelatePubblBean.getUriFile()) : null;	    		
    				
					File fileOut = null;
					
					// eseguo il servizio
				    try {				    	   
				    		RecuperoFile servizio = new RecuperoFile();
				    		FileExtractedOut servizioOut = new FileExtractedOut();
				    	    servizioOut = servizio.extractFileByUri(loginBean, uriFileIn);	    
				    	    
			    	    	// Se il servizio e' andato in errore restituisco il messaggio di errore 	    	    
				    	    if(StringUtils.isNotBlank(servizioOut.getErrorInExtract())) {
				    	    	throw new Exception(servizioOut.getErrorInExtract());
				    		}
				    	    
				    	    // Altrimenti restituisco il FILE
				    	    fileOut =  servizioOut.getExtracted();
				    	    				    	  				    	   
				    	    extractedFileList.add(fileOut);				    	  
				 		}
				 	catch (Exception e){
				 		throw new Exception(e.getMessage());	
				 	}				 						
    			}    				
    	}    	 
    	
    	bean.setExtractedFileList(extractedFileList);

	 	return bean;
    }
    
    private WSGetDatiPubblicazioneAttoBean callWS(AurigaLoginBean loginBean, String xmlIn) throws Exception {
    	    	
    	aLogger.debug("Eseguo il WS DmpkWSGetDatiPubblicazioneAtto.");
    	    	
    	String uriRelatePubbl = null;
    	String xml            = null;    	
    	try {    		
    		  // Inizializzo l'INPUT    		
    		  DmpkWsGetdatipubblicazioneattoBean input = new DmpkWsGetdatipubblicazioneattoBean();
    		  input.setCodidconnectiontokenin(loginBean.getToken());
    		  input.setXmlin(xmlIn);
    		  
    		  // Eseguo il servizio
    		  Getdatipubblicazioneatto service = new Getdatipubblicazioneatto();
    		  StoreResultBean<DmpkWsGetdatipubblicazioneattoBean> output = service.execute(loginBean, input);

    		  if (output.isInError()){
    			  throw new Exception(output.getDefaultMessage());	
    			}	

    		  // restituisco l'XML
    		  if(output.getResultBean().getXmlout()!=null){
    			  xml = output.getResultBean().getXmlout();  
    		  }
    		
    		  // restituisco la lista con i nro versioni
    	      if (output.getResultBean().getUrirelatepubblout() != null){
    	    	  uriRelatePubbl = output.getResultBean().getUrirelatepubblout().toString();
    	      }
      	      
      	      // popolo il bean di out
    		  WSGetDatiPubblicazioneAttoBean result = new WSGetDatiPubblicazioneAttoBean();
    		  result.setXml(xml);
    		  
    		  // leggo la lista dei documenti 
    		  List<RelatePubblBean> listRelatePubblBean = new ArrayList<RelatePubblBean>();    		  
    		  listRelatePubblBean =  getListDoc(uriRelatePubbl);
    		  
    		  result.setDocumentlist(listRelatePubblBean);
    			  
    		  return result;
 			}
 		catch (Exception e){
 			throw new Exception(e.getMessage()); 			
 		}
    }
    
    
	/**
     * Genera il file XML contenente l'id del folder aggiunto
     * Questo file viene passato come allegato in caso di successo.
     *
     * @param String idFolder
     * @return String stringa XML secondo il formato per il ritorno dell'idFolder
     */
    private String generaXMLRispostaWS(String xmlIn)  throws Exception {
    
        StringBuffer xml = new StringBuffer();
        String xmlInEsc = "";
        
        try {
        	// ...se il token non e' null
            if (xmlIn != null) {
            	// effettuo l'escape di tutti i caratteri
            	xmlInEsc = eng.util.XMLUtil.xmlEscape(xmlIn);
            }
            xml.append(xmlInEsc);
            aLogger.debug(xml.toString());
        }
        catch (Exception e){
 			throw new Exception(e.getMessage());
 		}        
        return xml.toString();
    }
    
    private List<RelatePubblBean> getListDoc(String xmlIn) throws Exception {
    	List<RelatePubblBean> listRelatePubblBean = new ArrayList<RelatePubblBean>();
    	if ((xmlIn != null) && (!xmlIn.equals(""))) {
    		// istanzio lo stringReader e vi associo la LISTA_STD
			java.io.StringReader sr = new java.io.StringReader(xmlIn);
			Lista lsSr = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			if (lsSr != null) {
				for (int j = 0; j < lsSr.getRiga().size(); j++) {
					// prendo la riga i-esima
					Riga r = lsSr.getRiga().get(j);
					String uriFile       = getContentColonnaNro(r, 1);
					RelatePubblBean lRelatePubblBean = new RelatePubblBean();
	        		lRelatePubblBean.setUriFile(uriFile);
	        		listRelatePubblBean.add(lRelatePubblBean);
				}				
			}			
    	}
    	return listRelatePubblBean;
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
}