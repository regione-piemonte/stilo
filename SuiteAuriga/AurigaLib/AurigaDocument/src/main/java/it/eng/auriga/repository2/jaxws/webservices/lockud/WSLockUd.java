/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreLockudBean;
import it.eng.auriga.database.store.dmpk_core.store.Lockud;
import it.eng.auriga.database.store.dmpk_ws.bean.DmpkWsLeggiestremixidentificazioneudBean;
import it.eng.auriga.database.store.dmpk_ws.store.Leggiestremixidentificazioneud;
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


@WebService(targetNamespace = "http://lockud.webservices.repository2.auriga.eng.it",  endpointInterface="it.eng.auriga.repository2.jaxws.webservices.lockud.WSILockUd", name = "WSLockUd")
@MTOM(enabled = true, threshold = 0)

public class WSLockUd extends JAXWSAbstractAurigaService implements WSILockUd{	

    private final String K_SAVEPOINTNAME = "INIZIOWSLOCKUD";
   
    static Logger aLogger = Logger.getLogger(WSLockUd.class.getName());    
    
    public WSLockUd() {
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
    	 aLogger.info("Inizio WSLockUd");
    	
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
  		WSLockUdBean outWS = new WSLockUdBean();
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
	 			        	
	     aLogger.info("Fine WSLockUd");
	    
	     return risposta;
    }
  
    catch (Exception excptn) {
        aLogger.error("WSLockUd: " + excptn.getMessage(), excptn);
        return   generaXMLRisposta( JAXWSAbstractAurigaService.FALLIMENTO, JAXWSAbstractAurigaService.ERR_ERRORE_APPLICATIVO, JAXWSAbstractAurigaService.ERROR_ERRORE_APPLICATIVO, "", "" );
        //throw excptn;
	}
	finally
	{
    	try { DBHelperSavePoint.RollbackToSavepoint(conn, K_SAVEPOINTNAME); } catch (Exception ee) {}
	    aLogger.info("Fine WSLockUd serviceImplementation");
	}
    }

    
    private String eseguiServizio(AurigaLoginBean loginBean, WSLockUdBean bean) throws Exception {
    	aLogger.debug("Eseguo il servizio di AurigaDocument.");
    	
    	String ret = null;
    	
		// creo l'input
		BigDecimal idUdIn       = (bean.getIdUd() != null) ? new BigDecimal(bean.getIdUd()) : null;	    		

		
	    try {	    	
	    	    // **********************************************************
	    	    // Eseguo il DMPK_CORE.LockUD
	    	    // **********************************************************
	    	    aLogger.debug("Eseguo il servizio  DMPK_CORE.LockUD");
	    	
	    	    DmpkCoreLockudBean lLockudBean = new DmpkCoreLockudBean();
	    	    lLockudBean.setCodidconnectiontokenin(loginBean.getToken());
	    	    lLockudBean.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
	    	    lLockudBean.setIdudin(idUdIn);
	    	    lLockudBean.setFlgtipolockin("I");
	    	    
	    	    Lockud lLockud = new Lockud();			
				
				StoreResultBean<DmpkCoreLockudBean> lStoreResultBean = lLockud.execute(loginBean, lLockudBean);
				if (lStoreResultBean.isInError()){
					aLogger.debug(lStoreResultBean.getDefaultMessage());
					aLogger.debug(lStoreResultBean.getErrorContext());
					aLogger.debug(lStoreResultBean.getErrorCode());
					throw new Exception(lStoreResultBean.getDefaultMessage());
				}
	    		
				// Leggo l'XML 
				if(lStoreResultBean.getResultBean().getDocudxmlout()!=null)
					ret = lStoreResultBean.getResultBean().getDocudxmlout();
				else
					throw new Exception("La store procedure DMPK_CORE.LockUD ha ritornato DocUDXMLOut nullo");
	 		}
	 	catch (Exception e){
	 		throw new Exception(e.getMessage());	
	 	}
	 	return ret;    	
    }
    
    private WSLockUdBean callWS(AurigaLoginBean loginBean, String xmlIn) throws Exception {
    	    	
    	aLogger.debug("Eseguo il WS DMPK_WS.LeggiEstremiXIdentificazioneUD");
    	
    	String idUd       = null;
    	String xml         = null;    	
    	
    	try {    		
    		  // Inizializzo l'INPUT    		
    		  DmpkWsLeggiestremixidentificazioneudBean input = new DmpkWsLeggiestremixidentificazioneudBean();
    		  input.setCodidconnectiontokenin(loginBean.getToken());
    		  input.setXmlin(xmlIn);
    		      		
      		
    		  // Eseguo il servizio
    		  Leggiestremixidentificazioneud service = new Leggiestremixidentificazioneud();
    		  StoreResultBean<DmpkWsLeggiestremixidentificazioneudBean> output = service.execute(loginBean, input);

    		  if (output.isInError()){
    			  throw new Exception(output.getDefaultMessage());	
    			}	

    		  // restituisco l'ID UD
    		  if (output.getResultBean().getIdudout() != null){
    			  idUd = output.getResultBean().getIdudout().toString();  
    		  }
    		  if (idUd== null || idUd.equalsIgnoreCase("")){
    			  throw new Exception("La store procedure LockUD ha ritornato id ud nullo");
    		  }
    		  
      	      // popolo il bean di out
    		  WSLockUdBean result = new WSLockUdBean();
    		  result.setIdUd(idUd);
    			  
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
            xml.append(xmlInEsc);
            aLogger.debug(xml.toString());
        }
        catch (Exception e){
 			throw new Exception(e.getMessage()); 			
 		}
        return xml.toString();
    }  
}
