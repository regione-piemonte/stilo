/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreLockdocBean;
import it.eng.auriga.database.store.dmpk_core.store.Lockdoc;
import it.eng.auriga.database.store.dmpk_ws.bean.DmpkWsLockcheckoutBean;
import it.eng.auriga.database.store.dmpk_ws.store.Lockcheckout;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.beans.SpecializzazioneBean;
import it.eng.auriga.module.business.entity.WSTrace;
import it.eng.auriga.repository2.util.DBHelperSavePoint;
import java.io.ByteArrayInputStream;
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


@WebService(targetNamespace = "http://lock.webservices.repository2.auriga.eng.it",  endpointInterface="it.eng.auriga.repository2.jaxws.webservices.lock.WSILock", name = "WSLock")
@MTOM(enabled = true, threshold = 0)

public class WSLock extends JAXWSAbstractAurigaService implements WSILock{	

    private final String K_SAVEPOINTNAME = "INIZIOWSLOCK";
   
    static Logger aLogger = Logger.getLogger(WSLock.class.getName());    
    
    public WSLock() {
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
    String outServizio = null;
      
    String errMsg = null;
    String xmlIn = null;
    
    try {
    	 aLogger.info("Inizio WSLock");
    	
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
  		WSLockBean outWS = new WSLockBean();
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
 	  		xmlIn = outServizio;	
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
	 			        	
	     aLogger.info("Fine WSLock");
	    
	     return risposta;
    }
  
    catch (Exception excptn) {
        aLogger.error("WSLock: " + excptn.getMessage(), excptn);
        return   generaXMLRisposta( JAXWSAbstractAurigaService.FALLIMENTO, JAXWSAbstractAurigaService.ERR_ERRORE_APPLICATIVO, JAXWSAbstractAurigaService.ERROR_ERRORE_APPLICATIVO, "", "" );
        //throw excptn;
	}
	finally
	{
    	try { DBHelperSavePoint.RollbackToSavepoint(conn, K_SAVEPOINTNAME); } catch (Exception ee) {}
	    aLogger.info("Fine WSLock serviceImplementation");
	}
    }

    
    private String eseguiServizio(AurigaLoginBean loginBean, WSLockBean bean) throws Exception {
    	aLogger.debug("Eseguo il servizio di AurigaDocument.");
    	
    	String ret = null;
    	
		// creo l'input
		BigDecimal idDocIn       = (bean.getIdDoc() != null) ? new BigDecimal(bean.getIdDoc()) : null;	    		
		
	    try {	    	
	    	    // **********************************************************
	    	    // Eseguo il DMPK_CORE.LockDoc
	    	    // **********************************************************
	    	    aLogger.debug("Eseguo il servizio  DMPK_CORE.LockDoc");
	    	
	    	    DmpkCoreLockdocBean lLockdocBean = new DmpkCoreLockdocBean();
	    	    lLockdocBean.setCodidconnectiontokenin(loginBean.getToken());
	    	    lLockdocBean.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
	    	    lLockdocBean.setIddocin(idDocIn);
	    	    lLockdocBean.setFlgtipolockin("I");		// Imposto il lock I = implicito 
	    	    
	    	    
	    	    Lockdoc lLockdoc = new Lockdoc();			
				
				StoreResultBean<DmpkCoreLockdocBean> lStoreResultBean = lLockdoc.execute(loginBean, lLockdocBean);
				if (lStoreResultBean.isInError()){
					aLogger.debug(lStoreResultBean.getDefaultMessage());
					aLogger.debug(lStoreResultBean.getErrorContext());
					aLogger.debug(lStoreResultBean.getErrorCode());
					throw new Exception(lStoreResultBean.getDefaultMessage());
				}
	    		
				// Leggo l'URI del file elettronico
				if(lStoreResultBean.getResultBean().getUriverout()!=null)
					ret = lStoreResultBean.getResultBean().getUriverout();
				else
					throw new Exception("La store procedure DMPK_CORE.LockDoc ha ritornato Uriverout nullo");
	 		}
	 	catch (Exception e){
	 		throw new Exception(e.getMessage());	
	 	}
	 	return ret;    	
    }
    
    private WSLockBean callWS(AurigaLoginBean loginBean, String xmlIn) throws Exception {
    	    	
    	aLogger.debug("Eseguo il WS DmpkWsLockCheckOut.");
    	
    	String idDoc       = null;
    	String xml         = null;    	

    	try {    		
    		  // Inizializzo l'INPUT    		
    		  DmpkWsLockcheckoutBean input = new DmpkWsLockcheckoutBean();
    		  input.setCodidconnectiontokenin(loginBean.getToken());
    		  input.setXmlin(xmlIn);
    		  input.setFlgtipowsin("L");       							// operazione di Lock = "L"
      		
    		  // Eseguo il servizio
    		  Lockcheckout service = new Lockcheckout();
    		  StoreResultBean<DmpkWsLockcheckoutBean> output = service.execute(loginBean, input);

    		  if (output.isInError()){
    			  throw new Exception(output.getDefaultMessage());	
    			}	

    		  // restituisco l'ID DOC
    		  if (output.getResultBean().getIddocout() != null){
    			  idDoc = output.getResultBean().getIddocout().toString();  
    		  }
    		  if (idDoc== null || idDoc.equalsIgnoreCase("")){
    			  throw new Exception("La store procedure LockCheckout ha ritornato id doc nullo");
    		  }
    		  
    		  // restituisco l'XML
    		  if(output.getResultBean().getXmlout()!=null){
    			  xml = output.getResultBean().getXmlout();  
    		  }
    		      		      		       	      
      	      // popolo il bean di out
    		  WSLockBean result = new WSLockBean();
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
            xml.append("<UriDoc>" + xmlInEsc + "</UriDoc>\n");
            aLogger.debug(xml.toString());
        }
        catch (Exception e){
 			throw new Exception(e.getMessage()); 			
 		}
        return xml.toString();
    }  
}
