/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_ws.bean.DmpkWsGetdeterminaBean;
import it.eng.auriga.database.store.dmpk_ws.store.Getdetermina;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.beans.SpecializzazioneBean;
import it.eng.auriga.module.business.entity.WSTrace;
import it.eng.auriga.repository2.util.DBHelperSavePoint;
import it.eng.document.function.RecuperoFile;
import it.eng.document.function.bean.FileExtractedIn;
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

@WebService(targetNamespace = "http://getdetermina.webservices.repository2.auriga.eng.it",  endpointInterface="it.eng.auriga.repository2.jaxws.webservices.determina.WSIGetDetermina", name = "WSGetDetermina")
@MTOM(enabled = true, threshold = 0)
@HandlerChain(file = "../common/handler.xml")
public class WSGetDetermina extends JAXWSAbstractAurigaService implements WSIGetDetermina{	

    private final String K_SAVEPOINTNAME = "INIZIOWSGetDetermina";
   
    static Logger aLogger = Logger.getLogger(WSGetDetermina.class.getName());    
    
    public WSGetDetermina() {
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
    WSGetDeterminaOutBean outServizio = new WSGetDeterminaOutBean();
    String errMsg = null;
    String xmlIn = null;
    
    try {
    	
    	 aLogger.info("Inizio WSGetDetermina");
    	
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
  		WSGetDeterminaOutBean outWS = new WSGetDeterminaOutBean();
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

	  		  // Aggiungo i FILE ATTACH	  		  	
	 		  if(outServizio!=null && outServizio.getListaFileExtracted() != null && outServizio.getListaFileExtracted().size()> 0 ){	 			  
	 			List<File> extractedFileList = new ArrayList<File>(); 	 	
	 			extractedFileList = outServizio.getListaFileExtracted();
	 			
	 		    // Ciclo sulla lista dei FILE ATTACH 	 			
	 			for (File lFile : extractedFileList){    
	 				InputStream inputStreamFile = new FileInputStream(lFile);	 				
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
        	
	 	/*************************************************************
		 * Restituisco XML di risposta del WS
		 ************************************************************/	
	 	 if (errMsg==null){
	 	 		risposta = generaXMLRisposta( JAXWSAbstractAurigaService.SUCCESSO, JAXWSAbstractAurigaService.SUCCESSO, "Tutto OK", "", "");
	 	 }
	 	 else{
	 	 		risposta = generaXMLRisposta( JAXWSAbstractAurigaService.FALLIMENTO, JAXWSAbstractAurigaService.ERR_ERRORE_APPLICATIVO,  errMsg, "", "");
	 	 }
	 			        	
	     aLogger.info("Fine WSGetDetermina");
	    
	     return risposta;
    }
  
    catch (Exception excptn) {
        aLogger.error("WSGetDetermina: " + excptn.getMessage(), excptn);
        return   generaXMLRisposta( JAXWSAbstractAurigaService.FALLIMENTO, JAXWSAbstractAurigaService.ERR_ERRORE_APPLICATIVO, JAXWSAbstractAurigaService.ERROR_ERRORE_APPLICATIVO, "", "" );
        //throw excptn;
	}
	finally
	{
    	try { DBHelperSavePoint.RollbackToSavepoint(conn, K_SAVEPOINTNAME); } catch (Exception ee) {}
	    aLogger.info("Fine WSGetDetermina serviceImplementation");
	}

    }
    
    private WSGetDeterminaOutBean eseguiServizio(AurigaLoginBean loginBean, WSGetDeterminaOutBean bean) throws Exception {
    	aLogger.debug("Eseguo il servizio di AurigaDocument.");
    	
    	List<File> listaFileExtracted = new ArrayList<File>(); 
    	     	
    	List<FileBean> listaFileBean = new ArrayList<FileBean>();      	  
    	listaFileBean = bean.getListaFile();
    	
    	if(listaFileBean != null && listaFileBean.size()> 0){
    			for (FileBean lFileBean : listaFileBean){    				
					if (lFileBean.getUriFile()!=null && !lFileBean.getUriFile().equalsIgnoreCase("")){
					
						// Estraggo il file
						File fileOut = null;
					    try {				    	   
					    		RecuperoFile servizio = new RecuperoFile();
					    		FileExtractedOut servizioOut = new FileExtractedOut();
					    		FileExtractedIn pFileExtractedIn = new FileExtractedIn();
					    		pFileExtractedIn.setUri(lFileBean.getUriFile());
					    	    servizioOut = servizio.extractFile(loginBean, pFileExtractedIn);	    
					    	    
				    	    	// Se il servizio e' andato in errore restituisco il messaggio di errore 	    	    
					    	    if(StringUtils.isNotBlank(servizioOut.getErrorInExtract())) {
					    	    	throw new Exception(servizioOut.getErrorInExtract());
					    		}
					    	    
					    	    // Altrimenti restituisco il FILE
					    	    if (servizioOut.getExtracted()!=null){
					    	    	fileOut =  servizioOut.getExtracted();
					    	    	listaFileExtracted.add(fileOut);
					    	    }
					 		}
					 	catch (Exception e){
					 		throw new Exception(e.getMessage());	
					 	}	
					}
								 						
    			}    				
    	}    	 
    	
    	bean.setListaFileExtracted(listaFileExtracted);

	 	return bean;
    }
    
    private WSGetDeterminaOutBean callWS(AurigaLoginBean loginBean, String xmlIn) throws Exception {
    	    	
    	aLogger.debug("Eseguo il WS DmpkWsGetDetermina.");
    	    	    	  	
    	try {    		
    		  // Inizializzo l'INPUT    		
    		  DmpkWsGetdeterminaBean input = new DmpkWsGetdeterminaBean();
    		  input.setCodidconnectiontokenin(loginBean.getToken());
    		  input.setXmlrequestin(xmlIn);
    		  
    		  // Eseguo il servizio
    		  Getdetermina service = new Getdetermina();
    		  StoreResultBean<DmpkWsGetdeterminaBean> output = service.execute(loginBean, input);

    		  if (output.isInError()){
    			  throw new Exception(output.getDefaultMessage());	
    			}	

    		  // restituisco l'XML
    		  String xmlResponseOut = null;  
    		  if(output.getResultBean().getXmlresponseout()!=null){
    			  xmlResponseOut = output.getResultBean().getXmlresponseout();  
    		  }
    		  if (xmlResponseOut== null || xmlResponseOut.equalsIgnoreCase(""))
    			  throw new Exception("La store procedure ha ritornato XmlOut nullo");
    		      		    		      		  
    		  // restituisco la lista con gli uri dei file 
    		  String xmlListaFileOut = null;
    	      if (output.getResultBean().getListafileout() != null){
    	    	  xmlListaFileOut = output.getResultBean().getListafileout().toString();
    	      }
      	      if (xmlListaFileOut == null) {
      	    	    throw new Exception("La store procedure Getdetermina ha ritornato una lista di file nulla");
      	      }
      	      
      	      // popolo il bean di out
    		  WSGetDeterminaOutBean result = new WSGetDeterminaOutBean();
    		  result.setXml(xmlResponseOut);
    		  
    		  // leggo la lista dei documenti 
    		  List<FileBean> listFileBean = new ArrayList<FileBean>();    		  
    		  listFileBean =  getListaUriFile(xmlListaFileOut);
    		  
    		  result.setListaFile(listFileBean);
    			  
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
            
            //xmlInEsc = xmlIn;
            xml.append(xmlInEsc);
            aLogger.debug(xml.toString());
        }
        catch (Exception e){
 			throw new Exception(e.getMessage());
 		}        
        return xml.toString();
    }
    
    private List<FileBean> getListaUriFile(String xmlIn) throws Exception {
    	List<FileBean> out  = new ArrayList<FileBean>();
    	if ((xmlIn != null) && (!xmlIn.equals(""))) {
    		// istanzio lo stringReader e vi associo la LISTA_STD
			java.io.StringReader sr = new java.io.StringReader(xmlIn);
			Lista lsSr = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			if (lsSr != null) {
				for (int j = 0; j < lsSr.getRiga().size(); j++) {
					// prendo la riga j-esima
					Riga r = lsSr.getRiga().get(j);
					
					for (int c = 1; c <= r.getColonna().size(); c++) {
						// prendo la colonna c-esima 
						//Colonna nroCol = r.getColonna().get(c);
						String uriFile = getContentColonnaNro(r, c);					
						FileBean lFileBean = new FileBean();
						lFileBean.setUriFile(uriFile);	        		  
						out.add(lFileBean);
					}	
				}				
			}			
    	}
    	return out;
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