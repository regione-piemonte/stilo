/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreCheckoutdocBean;
import it.eng.auriga.database.store.dmpk_core.store.Checkoutdoc;
import it.eng.auriga.database.store.dmpk_ws.bean.DmpkWsLockcheckoutBean;
import it.eng.auriga.database.store.dmpk_ws.store.Lockcheckout;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.beans.SpecializzazioneBean;
import it.eng.auriga.module.business.entity.WSTrace;
import it.eng.auriga.repository2.util.DBHelperSavePoint;
import it.eng.document.function.RecuperoFile;
import it.eng.document.function.StoreException;
import it.eng.document.function.bean.FileExtractedOut;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
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


@WebService(targetNamespace = "http://checkout.webservices.repository2.auriga.eng.it",  endpointInterface="it.eng.auriga.repository2.jaxws.webservices.checkout.WSICheckOut", name = "WSCheckOut")
@MTOM(enabled = true, threshold = 0)

public class WSCheckOut extends JAXWSAbstractAurigaService implements WSICheckOut{	

    private final String K_SAVEPOINTNAME = "INIZIOWSCHECKOUT";
   
    static Logger aLogger = Logger.getLogger(WSCheckOut.class.getName());    
    
    public WSCheckOut() {
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
    WSCheckOutBean outServizio = new WSCheckOutBean();
      
    String errMsg = null;
    String xmlIn = null;
    
    try {
    	 aLogger.info("Inizio WSCheckOut");
    	
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
  		WSCheckOutBean outWS = new WSCheckOutBean();
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
		 * Creo XML di risposta del servzio e lo metto in attach alla response
		 **************************************************************************/
	 	try {
	    	  // Creo XML di risposta
	 		  outRispostaWS = generaXMLRispostaWS(xmlIn);
	 		   
	 		  // Creo la lista di attach
	  		  List<InputStream> lListInputStreams = new ArrayList<InputStream>();
	  		  
	 		  // Converto l'XML
	 		  ByteArrayInputStream inputStreamXml = new ByteArrayInputStream(outRispostaWS.getBytes());
	  		  
	 		  // Aggiungo l'XML
	  		  lListInputStreams.add(inputStreamXml);                                
	  		  
	  		  // Converto il FILE ATTACH
	 		  if(outServizio!=null && outServizio.getExtracted() != null){
	 			 InputStream inputStreamFile = new FileInputStream(outServizio.getExtracted());
	 			 
	 			 // Aggiungo il FILE
		  		 lListInputStreams.add(inputStreamFile);										  		  
	 		  }	 		  	  		  
	  		  
	  		  // Salvo gli ATTACH alla response
	  		  attachListInputStream(lListInputStreams);
	 	     }
	 		catch (Exception e){
	 			if(e.getMessage()!=null)
		 			 errMsg = "Errore = " + e.getMessage();
		 		 else
		 			errMsg = "Errore imprevisto.";	
	 		}   
        	
	 	/*************************************************************
		 * Restituisco XML di risposta del WS
		 ************************************************************/	
	 	 if (errMsg==null){
	 	 		risposta = generaXMLRisposta( JAXWSAbstractAurigaService.SUCCESSO, JAXWSAbstractAurigaService.SUCCESSO, "Tutto OK", "", "");
	 	 }
	 	 else{
	 	 		risposta = generaXMLRisposta( JAXWSAbstractAurigaService.FALLIMENTO, JAXWSAbstractAurigaService.ERR_ERRORE_APPLICATIVO,  errMsg, "", "");
	 	 }
	 			        	
	     aLogger.info("Fine WSCheckOut");
	    
	     return risposta;
    }
  
    catch (Exception excptn) {
        aLogger.error("WSCheckOut: " + excptn.getMessage(), excptn);
        return   generaXMLRisposta( JAXWSAbstractAurigaService.FALLIMENTO, JAXWSAbstractAurigaService.ERR_ERRORE_APPLICATIVO, JAXWSAbstractAurigaService.ERROR_ERRORE_APPLICATIVO, "", "" );
        //throw excptn;
	}
	finally
	{
    	try { DBHelperSavePoint.RollbackToSavepoint(conn, K_SAVEPOINTNAME); } catch (Exception ee) {}
	    aLogger.info("Fine WSCheckOut serviceImplementation");
	}
    }

    
    private WSCheckOutBean eseguiServizio(AurigaLoginBean loginBean, WSCheckOutBean bean) throws Exception {
    	aLogger.debug("Eseguo il servizio di AurigaDocument.");
    	
    	WSCheckOutBean ret = new WSCheckOutBean();
    	File fileOut = null;
    	
		// creo l'input
		BigDecimal idDocIn       = (bean.getIdDoc() != null) ? new BigDecimal(bean.getIdDoc()) : null;	    		
		BigDecimal nroProgrVerIn =  null;

	    try {	    	
	    	    // **********************************************************
	    	    // Eseguo il DMPK_CORE.CheckOutDoc
	    	    // **********************************************************
	    	    aLogger.debug("Eseguo il WS DmpkCheckoutdoc");
	    	
	    	    DmpkCoreCheckoutdocBean lCheckoutdocBean = new DmpkCoreCheckoutdocBean();
	    	    lCheckoutdocBean.setCodidconnectiontokenin(loginBean.getToken());
	    	    lCheckoutdocBean.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
	    	    lCheckoutdocBean.setIddocin(idDocIn);
	    	    
	    	    Checkoutdoc lCheckoutdoc = new Checkoutdoc();			
				
				StoreResultBean<DmpkCoreCheckoutdocBean> lStoreResultBean = lCheckoutdoc.execute(loginBean, lCheckoutdocBean);
				if (lStoreResultBean.isInError()){
					aLogger.debug(lStoreResultBean.getDefaultMessage());
					aLogger.debug(lStoreResultBean.getErrorContext());
					aLogger.debug(lStoreResultBean.getErrorCode());
					throw new Exception(lStoreResultBean.getDefaultMessage());
				}
	    		
				// Leggo il nr. progressivo ( NroProgrVerOut )
				if(lStoreResultBean.getResultBean().getNroprogrverout()!=null)
					nroProgrVerIn = lStoreResultBean.getResultBean().getNroprogrverout();
				else
					throw new Exception("La store procedure DmpkCheckoutdoc ha ritornato Nroprogrverout nullo");
				
				// **********************************************************
	    	    // Estraggo il file elettronico 
				// **********************************************************
	    		RecuperoFile servizio = new RecuperoFile();
	    		FileExtractedOut servizioOut = new FileExtractedOut();
	    	    servizioOut = servizio.extractFileByIdDoc(loginBean, idDocIn, nroProgrVerIn);	    
	    	    
    	    	// Se il servizio e' andato in errore restituisco il messaggio di errore 	    	    
	    	    if(StringUtils.isNotBlank(servizioOut.getErrorInExtract())) {
	    	    	throw new Exception(servizioOut.getErrorInExtract());
	    		}
	    	    // Altrimenti restituisco il FILE
	    	    fileOut =  servizioOut.getExtracted();
	    	    
	    	    ret.setIdDoc(bean.getIdDoc());
	    	    
	    	    if(nroProgrVerIn!=null)
	    	    	ret.setNroProgrVer(nroProgrVerIn.toString());
	    	    
	    	    ret.setXml(bean.getXml());
	    	    ret.setExtracted(fileOut);	
	 		}
	 	catch (Exception e){
	 		throw new Exception(e.getMessage());	
	 	}
	 	return ret;    	
    }
    
    private WSCheckOutBean callWS(AurigaLoginBean loginBean, String xmlIn) throws Exception {
    	    	
    	aLogger.debug("Eseguo il WS DmpkWsLockCheckOut.");
    	
    	String idDoc       = null;
    	String xml         = null;    	
    	try {    		
    		  // Inizializzo l'INPUT    		
    		  DmpkWsLockcheckoutBean input = new DmpkWsLockcheckoutBean();
    		  input.setCodidconnectiontokenin(loginBean.getToken());
    		  input.setXmlin(xmlIn);
    		  input.setFlgtipowsin("C");       		// operazione di c.o. = "C"
      		
    		  // Eseguo il servizio
    		  Lockcheckout service = new Lockcheckout();
    		  StoreResultBean<DmpkWsLockcheckoutBean> output = service.execute(loginBean, input);

    		  if (output.isInError()){
    			  throw new Exception(output.getDefaultMessage());	
    			}	

    		  // restituisco l'XML
    		  if(output.getResultBean().getXmlout()!=null){
    			  xml = output.getResultBean().getXmlout();  
    		  }
    		  if (xml== null || xml.equalsIgnoreCase(""))
    			  throw new Exception("La store procedure LockCheckout ha ritornato XmlOut nullo");
    		  
    		  // restituisco l'ID DOC
    		  if (output.getResultBean().getIddocout() != null){
    			  idDoc = output.getResultBean().getIddocout().toString();  
    		  }
    		  if (idDoc== null || idDoc.equalsIgnoreCase("")){
    			  throw new Exception("La store procedure LockCheckout ha ritornato id doc nullo");
    		  }
    		      		      		       	      
      	      // popolo il bean di out
    		  WSCheckOutBean result = new WSCheckOutBean();
    		  result.setXml(xml);
    		  result.setIdDoc(idDoc);    		  
    			  
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
        String xmlInEsc = null;
        
        try {
        	// ...se il token non e' null
            if (xmlIn != null) {
            	// effettuo l'escape di tutti i caratteri
            	xmlInEsc = eng.util.XMLUtil.xmlEscape(xmlIn);
            }
            aLogger.debug("generaXMLToken: token = " + xmlIn);
            aLogger.debug("generaXMLToken: tokenEsc = " + xmlInEsc);
            xml.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n");
            xml.append("<Output_FileCheckedOut>" + xmlInEsc + "</Output_FileCheckedOut>\n");
            aLogger.debug(xml.toString());
        }
        catch (Exception e){
 			throw new Exception(e.getMessage());
 		}        
        return xml.toString();
    }
}
