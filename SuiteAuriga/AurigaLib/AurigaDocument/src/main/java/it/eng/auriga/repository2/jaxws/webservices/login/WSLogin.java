/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.module.business.entity.WSTrace;
import it.eng.auriga.repository2.jaxws.webservices.common.JAXWSAbstractAurigaService;
import it.eng.auriga.repository2.util.DBHelperSavePoint;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;

/**
 * @author Ottavio Passalacqua
 */

@WebService(targetNamespace = "http://login.webservices.repository2.auriga.eng.it", endpointInterface="it.eng.auriga.repository2.jaxws.webservices.login.WSILogin", name = "WSLogin")
public class WSLogin extends JAXWSAbstractAurigaService implements WSILogin{

    private final String K_SAVEPOINTNAME = "INIZIOWSLOGIN";
      
    static Logger aLogger = Logger.getLogger(WSLogin.class.getName());    
    
    public WSLogin() {
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
					      final String attributi,
					      final String schemaDb,
					      final String idDominio,
					      final String desDominio,
					      final String tipoDominio,
					      final WSTrace wsTraceBean) throws Exception {


    String risposta = null;

    String outRispostaWS = null;
    String outWS = null;
    String errMsg = null;
    String xmlIn = null;
    
    try {
    	aLogger.debug("Inizio WSLogin");
	    
    	 //setto il savepoint
 		 DBHelperSavePoint.SetSavepoint(conn, K_SAVEPOINTNAME);
 		 
    	 /*************************************************************
         * Chiamo il WS
         ************************************************************/ 
 		try {
 				outWS =  callWS(token);
	 		}
	 	catch (Exception e){	 
	 		if(e.getMessage()!=null)
	 			 errMsg = "Errore = " + e.getMessage();
	 		 else
	 			errMsg = "Errore imprevisto.";
	 		}
         
	 	if (errMsg==null){
	 	  		xmlIn = outWS;	
	 	  		if (conn != null) 
	 	  			conn.setAutoCommit(false);
	 	}
	 	else{
	 	 		xmlIn = errMsg;
	 	}
	 	
		/**************************************************************************
		 * Creo XML di risposta del servizio e lo metto in attach alla response
		 **************************************************************************/
	 	try {		    	 
	    	  // Creo XML di risposta
	 		  outRispostaWS = generaXMLRispostaWS(attributi, xmlIn);
	 		  
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
         	
	 	if (conn!=null)
	 		conn.commit();
	     
	    aLogger.info("Fine WSLogin");
	     
	    return risposta;       	
    }
    
    catch (Exception excptn) {
        aLogger.error("WSLogin: " + excptn.getMessage(), excptn);
        return   generaXMLRisposta( JAXWSAbstractAurigaService.FALLIMENTO, JAXWSAbstractAurigaService.ERR_ERRORE_APPLICATIVO, JAXWSAbstractAurigaService.ERROR_ERRORE_APPLICATIVO, "", "" );
        //throw excptn;
	}
	finally
	{
    	try { DBHelperSavePoint.RollbackToSavepoint(conn, K_SAVEPOINTNAME); } catch (Exception ee) {}
	    aLogger.info("Fine WSLogin serviceImplementation");
	}
  } 
    
    
  private String callWS(String token) throws Exception {
    	
    	String result = null;   
    	
    	try {   
    		
    		// controllo che il token non sia nullo o vuoto
    	    // se e' valorizzato tutto OK, altrimenti torno errore
    	    if (token != null && !token.equals("")) {
    	    	aLogger.debug("");
    	    	aLogger.debug("*******************************************");
    	    	aLogger.debug("WSLogin: token valorizzzato: " + token);
    	    	aLogger.debug("*******************************************");
    	    	aLogger.debug("");   
    	    	result = token;
    	    }
    	    else{
    	    	aLogger.debug("");
    	    	aLogger.debug("*******************************************");
    	    	aLogger.debug("WSLogin: token vuoto o null...");
    	    	aLogger.debug("*******************************************");
    	    	aLogger.debug("");    	    	
    	    	throw new Exception("Nessun Token di connessione");    	    	
    	    }    	    	
   		    return result;   		
    	}
    	catch (Exception e){
 			throw new Exception(e.getMessage()); 			
 		}
    }
    
    /**
     * Genera il file XML contenente DesUser, IdDominio e TokenConnessione
     * Esempio :
     * <?xml version="1.0"  encoding="ISO-8859-1"?><TokenConnessione DesUser="Valentina Martinucci" IdDominio="2">41F4F57CB0B50CEDE0538B011BAC966B231120160948550016870</TokenConnessione>
     * 
     * Questo file viene passato come allegato in caso di successo.
     * @return String stringa XML
     */
    private String generaXMLRispostaWS(String attributiIn, String xmlIn)  throws Exception {
    
        StringBuffer xml = new StringBuffer();
        String xmlInEsc = null;
        
        try {        
            if (xmlIn != null) {
            	// effettuo l'escape di tutti i caratteri
            	xmlInEsc = eng.util.XMLUtil.xmlEscape(xmlIn);
            }
            aLogger.debug("generaXMLToken: token = " + xmlIn);
            aLogger.debug("generaXMLToken: tokenEsc = " + xmlInEsc);
            xml.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n");                  
            xml.append("<TokenConnessione"+" "+attributiIn+">" + xmlInEsc + "</TokenConnessione>\n");	
            aLogger.debug(xml.toString());
        }
        catch (Exception e){
 			throw new Exception(e.getMessage());
 		}        
        return xml.toString();                              
    }
    
}