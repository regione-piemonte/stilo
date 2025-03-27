/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.repository2.jaxws.webservices.unlockud;

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreUnlockudBean;
import it.eng.auriga.database.store.dmpk_core.store.Unlockud;
import it.eng.auriga.database.store.dmpk_ws.bean.DmpkWsLeggiestremixidentificazioneudBean;
import it.eng.auriga.database.store.dmpk_ws.store.Leggiestremixidentificazioneud;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.beans.SpecializzazioneBean;
import it.eng.auriga.module.business.entity.WSTrace;
import it.eng.auriga.repository2.util.DBHelperSavePoint;
import it.eng.document.function.StoreException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import java.util.List;
import it.eng.auriga.repository2.jaxws.webservices.common.JAXWSAbstractAurigaService;

/**
 * @author Ottavio passalacqua
 */


@WebService(targetNamespace = "http://unlockud.webservices.repository2.auriga.eng.it",  endpointInterface="it.eng.auriga.repository2.jaxws.webservices.unlockud.WSIUnlockUd", name = "WSUnlockUd")
@MTOM(enabled = true, threshold = 0)

public class WSUnlockUd extends JAXWSAbstractAurigaService implements WSIUnlockUd{	

    private final String K_SAVEPOINTNAME = "INIZIOWSUNLOCKUD";
   
    static Logger aLogger = Logger.getLogger(WSUnlockUd.class.getName());    
    
    public WSUnlockUd() {
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


    String risposta = null;
    String outRispostaWS = null;
    String outServizio = null;
      
    String errMsg = null;
    String xmlIn = null;
    Integer errCode = JAXWSAbstractAurigaService.ERR_ERRORE_APPLICATIVO;
    
    try {
    	 aLogger.info("Inizio WSUnlockUd");
    	
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
  		WSUnlockUdBean outWS = new WSUnlockUdBean();
         try {
        	 // Chiamo il WS        	 
        	 outWS =  callWS(loginBean,xml);
        	         	 
 			 // Chiamo il servizio di AurigaDocument
 			 outServizio =  eseguiServizio(loginBean,outWS); 	 		
	 		}
	 	catch (Exception e){	 
	 		if (e instanceof StoreException) {
	    		if(((StoreException) e).getError()!=null){
	    			errCode = ((StoreException) e).getError().getErrorCode();
	    		}
	    	}
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
	 	 		risposta = generaXMLRisposta( JAXWSAbstractAurigaService.FALLIMENTO, errCode,  errMsg, "", "");
	 	 }
	 			        	
	     aLogger.info("Fine WSUnlockUd");
	    
	     return risposta;
    }
  
    catch (Exception excptn) {
        aLogger.error("WSUnlockUd: " + excptn.getMessage(), excptn);
        return   generaXMLRisposta( JAXWSAbstractAurigaService.FALLIMENTO, JAXWSAbstractAurigaService.ERR_ERRORE_APPLICATIVO, JAXWSAbstractAurigaService.ERROR_ERRORE_APPLICATIVO, "", "" );
        //throw excptn;
	}
	finally
	{
    	try { DBHelperSavePoint.RollbackToSavepoint(conn, K_SAVEPOINTNAME); } catch (Exception ee) {}
	    aLogger.info("Fine WSUnlockUd serviceImplementation");
	}
    }

    
    private String eseguiServizio(AurigaLoginBean loginBean, WSUnlockUdBean bean) throws Exception {
    	aLogger.debug("Eseguo il servizio  DMPK_CORE.UnlockUd");
    	
    	String ret = null;
    	
    	// Inizializzo l'INPUT    		
		BigDecimal idUdIn       = (bean.getIdUd() != null) ? new BigDecimal(bean.getIdUd()) : null;	    		
		DmpkCoreUnlockudBean input = new DmpkCoreUnlockudBean();
	    input.setCodidconnectiontokenin(loginBean.getToken());
	    input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
	    input.setIdudin(idUdIn);
	    
	    // Eseguo il servizio
	    Unlockud service = new Unlockud();			
		StoreResultBean<DmpkCoreUnlockudBean> output = service.execute(loginBean, input);
		if (output.isInError()){
			aLogger.debug(output.getDefaultMessage());
			aLogger.debug(output.getErrorContext());
			aLogger.debug(output.getErrorCode());
			throw new StoreException(output);
		}
		
		// Leggo l'XML 
		if(output.getResultBean().getDocudxmlout()!=null)
			ret = output.getResultBean().getDocudxmlout();
		else
			throw new Exception("La store procedure DMPK_CORE.UnlockUd ha ritornato Uriverout nullo");
	 	
	 	return ret;    	
    }
    
    private WSUnlockUdBean callWS(AurigaLoginBean loginBean, String xmlIn) throws Exception {
    	aLogger.debug("Eseguo il WS DMPK_WS->LeggiEstremiXIdentificazioneUD");
    	
    	String idUd       = null;
    	    		
    	// Inizializzo l'INPUT    		
    	DmpkWsLeggiestremixidentificazioneudBean input = new DmpkWsLeggiestremixidentificazioneudBean();
    	input.setCodidconnectiontokenin(loginBean.getToken());
    	input.setXmlin(xmlIn);
	      		      		
    	// Eseguo il servizio
    	Leggiestremixidentificazioneud service = new Leggiestremixidentificazioneud();
    	StoreResultBean<DmpkWsLeggiestremixidentificazioneudBean> output = service.execute(loginBean, input);

	  	if (output.isInError()){
	  		aLogger.debug(output.getDefaultMessage());
		 	aLogger.debug(output.getErrorContext());
			aLogger.debug(output.getErrorCode());
			throw new StoreException(output);
		}	

	  	// restituisco l'ID UD
	  	if (output.getResultBean().getIdudout() != null){
		  idUd = output.getResultBean().getIdudout().toString();  
	  	}
	  	if (idUd== null || idUd.equalsIgnoreCase("")){
		  throw new Exception("La store procedure UnlockUd ha ritornato id ud nullo");
	  	}
	  
	  	// popolo il bean di out
	  	WSUnlockUdBean result = new WSUnlockUdBean();
	  	result.setIdUd(idUd);
		  
	  	return result;
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
            	xmlInEsc = StringEscapeUtils.escapeXml(xmlIn);
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
