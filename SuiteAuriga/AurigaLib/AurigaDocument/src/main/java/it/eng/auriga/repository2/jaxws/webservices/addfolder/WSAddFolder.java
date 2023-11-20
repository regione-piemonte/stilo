/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_ws.bean.DmpkWsAddfolderBean;
import it.eng.auriga.database.store.dmpk_ws.bean.DmpkWsGetestremifolderBean;
import it.eng.auriga.database.store.dmpk_ws.store.Addfolder;
import it.eng.auriga.database.store.dmpk_ws.store.Getestremifolder;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.beans.SpecializzazioneBean;
import it.eng.auriga.module.business.entity.WSTrace;
import it.eng.auriga.repository2.util.DBHelperSavePoint;
import it.eng.document.function.GestioneFascicoli;
import it.eng.document.function.bean.SalvaFascicoloOut;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import it.eng.auriga.repository2.jaxws.webservices.common.JAXWSAbstractAurigaService;

/**
 * @author Ottavio passalacqua
 */


@WebService(targetNamespace = "http://addfolder.webservices.repository2.auriga.eng.it",  endpointInterface="it.eng.auriga.repository2.jaxws.webservices.addfolder.WSIAddFolder", name = "WSAddFolder")
@MTOM(enabled = true, threshold = 0)

public class WSAddFolder extends JAXWSAbstractAurigaService implements WSIAddFolder{	

    private final String K_SAVEPOINTNAME = "INIZIOWSADDFOLDER";
   
    static Logger aLogger = Logger.getLogger(WSAddFolder.class.getName());    
    
    public WSAddFolder() {
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
    String outWS = null;    
    String errMsg = null;
    String xmlIn = null;
    
    try {
    	 aLogger.info("Inizio WSAddFolder");
    	
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
	 			        	
	     aLogger.info("Fine WSAddFolder");
	    
	     return risposta;
    }
  
    catch (Exception excptn) {
        aLogger.error("WSAddFolder: " + excptn.getMessage(), excptn);
        return   generaXMLRisposta( JAXWSAbstractAurigaService.FALLIMENTO, JAXWSAbstractAurigaService.ERR_ERRORE_APPLICATIVO, JAXWSAbstractAurigaService.ERROR_ERRORE_APPLICATIVO, "", "" );
        //throw excptn;
	}
	finally
	{
    	try { DBHelperSavePoint.RollbackToSavepoint(conn, K_SAVEPOINTNAME); } catch (Exception ee) {}
	    aLogger.info("Fine WSAddFolder serviceImplementation");
	}

    }

    
    private String eseguiServizio(AurigaLoginBean loginBean, String xmlIn) throws Exception {
    	aLogger.debug("Eseguo il servizio di AurigaDocument.");
    	
    	String ret = null;
    			
		// creo l'input
		String input = xmlIn;

		// eseguo il servizio
	    try {
	    	    GestioneFascicoli servizio = new GestioneFascicoli();
	    	    SalvaFascicoloOut servizioOut = new SalvaFascicoloOut();
	    	    servizioOut = servizio.salvaFascicoloWS(loginBean, input);
	    	    
    	    	// Se il servizio e' andato in errore restituisco il messaggio di errore 	    	    
	    	    if(StringUtils.isNotBlank(servizioOut.getDefaultMessage())) {
	    	    	throw new Exception(servizioOut.getDefaultMessage());
	    		}
	    	    
	    	    // Se la store mi restituisce un id, leggo un'altra store che mi restituisce l'xml per la response 
	    		if (servizioOut.getIdFolderOut()!= null){
	    		   ret = getXmlOutResponse(loginBean,servizioOut.getIdFolderOut());
	    		}
	    	    
	    	    // Altrimenti restituisco l'ID FOLDER
	    	    //ret = (servizioOut.getIdFolderOut()!= null ? (StringUtils.isNotBlank(servizioOut.getIdFolderOut().toString()) ? servizioOut.getIdFolderOut().toString() : "") : "");	
	 		}
	 	catch (Exception e){
	 		throw new Exception(e.getMessage());	
	 	}
	 	
	 	return ret;
    	
    }
    
    private String callWS(AurigaLoginBean loginBean, String xmlIn) throws Exception {
    	    	
    	aLogger.debug("Eseguo il WS DmpkWsAddfolder.");
    	
    	String result = null;    	
    	try {    		
    		  // Inizializzo l'INPUT    		
    		  DmpkWsAddfolderBean input = new DmpkWsAddfolderBean();
    		  input.setCodidconnectiontokenin(loginBean.getToken());
    		  input.setXmlin(xmlIn);
    		  
    		  // Eseguo il servizio
    		  Addfolder service = new Addfolder();
    		  StoreResultBean<DmpkWsAddfolderBean> output = service.execute(loginBean, input);

    		  if (output.isInError()){
    			  throw new Exception(output.getDefaultMessage());	
    			}	

    		  // restituisco l'XML
    		  if(output.getResultBean().getXmlout()!=null){
    			  result = output.getResultBean().getXmlout();  
    		  }
    		  
    		  
    		  if (result== null || result.equalsIgnoreCase(""))
    			  throw new Exception("La store procedure ha ritornato XmlOut nullo");
    			  
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
            
            //xml.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n");
            //xml.append("<IdFolder>" + xmlInEsc + "</IdFolder>\n");
            
            xml.append(xmlInEsc);
            
            aLogger.debug(xml.toString());
        }
        catch (Exception e){
 			throw new Exception(e.getMessage());
 		}        
        return xml.toString();
    }
    
    
    private String getXmlOutResponse(AurigaLoginBean loginBean,BigDecimal idFolderIn)  throws Exception {
    	
    	 aLogger.debug("Eseguo la DmpkWsGetestremifolder.");
    	
    	 String result = null;    
    	 
    	 try {

   		  		// Inizializzo l'INPUT    		
   		  		DmpkWsGetestremifolderBean input = new DmpkWsGetestremifolderBean();
   		  		input.setCodidconnectiontokenin(loginBean.getToken());
   		  		input.setIdfolderin(idFolderIn);

   		  	    // Eseguo il servizio
      		    Getestremifolder service = new Getestremifolder();
      		    StoreResultBean<DmpkWsGetestremifolderBean> output = service.execute(loginBean, input);

      		    if (output.isInError()){
      			  throw new Exception(output.getDefaultMessage());	
      			}
      		    
      		    // restituisco l'XML
      		    if(output.getResultBean().getXmlout()!=null){
      			  result = output.getResultBean().getXmlout();  
      		    }
      		  
      		    if (result== null || result.equalsIgnoreCase(""))
      		    	throw new Exception("La store procedure ha ritornato XmlOut nullo");
      			  
      		    return result;
    	 }
    	 catch (Exception e){
  			throw new Exception(e.getMessage());
  		}     
    }
}
