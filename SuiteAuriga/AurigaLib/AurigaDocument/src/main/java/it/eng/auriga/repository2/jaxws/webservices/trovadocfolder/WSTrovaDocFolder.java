/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_ws.bean.DmpkWsTrovadocfolderBean;
import it.eng.auriga.database.store.dmpk_ws.store.Trovadocfolder;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.function.bean.FindRepositoryObjectBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.beans.SpecializzazioneBean;
import it.eng.auriga.module.business.entity.WSTrace;
import it.eng.auriga.repository2.generic.VersionHandler;
import it.eng.auriga.repository2.jaxws.webservices.common.JAXWSAbstractAurigaService;
import it.eng.auriga.repository2.util.DBHelperSavePoint;
import it.eng.document.function.GestioneFascicoli;
import it.eng.document.function.bean.TrovaDocFolderOut;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.jaxb.variabili.Lista.Riga.Colonna;

/**
 *
 * @author Ottavio Passalacqua
 *
 */

@WebService(targetNamespace = "http://trovadocfolder.webservices.repository2.auriga.eng.it", endpointInterface = "it.eng.auriga.repository2.jaxws.webservices.trovadocfolder.WSITrovaDocFolder", name = "WSTrovaDocFolder")
@MTOM(enabled = true, threshold = 0)

public class WSTrovaDocFolder extends JAXWSAbstractAurigaService implements WSITrovaDocFolder{	

    private final String K_SAVEPOINTNAME = "INIZIOWSTROVADOCFOLDER";
    
    static Logger aLogger = Logger.getLogger(WSTrovaDocFolder.class.getName());    
    
    public WSTrovaDocFolder() {
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
    //String outServizio = null;
    String outRispostaWS = null;
    String errMsg = null;
    String xmlIn = null;
    String warnRegIn         = "";

    try {

        aLogger.info("Inizio WSTrovaDocFolder");
        
    	// set a false dell'autocommit
        if (conn != null) 
        	conn.setAutoCommit(false);
        
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
        WSTrovaDocFolderBean outWS = new WSTrovaDocFolderBean();
        
        WSTrovaDocFolderOutBean outServizio = new WSTrovaDocFolderOutBean();
        
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
	 	  	xmlIn = outServizio.getXmlRegOut();	
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
	 		if(e.getMessage()!=null)
	 			 errMsg = "Errore = " + e.getMessage();
	 		 else
	 			errMsg = "Errore imprevisto.";	
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

        aLogger.info("Fine WSTrovaDocFolder");
   
        return risposta;
    }
    catch (Exception excptn) {
        aLogger.error("WSTrovaDocFolder: " + excptn.getMessage(), excptn);
        return   generaXMLRisposta( JAXWSAbstractAurigaService.FALLIMENTO, JAXWSAbstractAurigaService.ERR_ERRORE_APPLICATIVO, JAXWSAbstractAurigaService.ERROR_ERRORE_APPLICATIVO, "", "" );
        //throw excptn;
	}
	finally
	{
    	try { DBHelperSavePoint.RollbackToSavepoint(conn, K_SAVEPOINTNAME); } catch (Exception ee) {}
	    aLogger.info("Fine WSTrovaDocFolder serviceImplementation");
	}
    }
        
    private WSTrovaDocFolderBean callWS(AurigaLoginBean loginBean, String xmlIn) throws Exception {
   
    	aLogger.debug("Eseguo il WS DmpkWSTrovaDocFolder.");
    	
    	try {    		
    		  // Inizializzo l'INPUT    		
    		  DmpkWsTrovadocfolderBean input = new DmpkWsTrovadocfolderBean();
    		  input.setCodidconnectiontokenin(loginBean.getToken());
    		  input.setXmlin(xmlIn);
    		  
    		  // Eseguo il servizio
    		  Trovadocfolder service = new Trovadocfolder();
    		  StoreResultBean<DmpkWsTrovadocfolderBean> output = service.execute(loginBean, input);

    		  if (output.isInError()){
    			  throw new Exception(output.getDefaultMessage());	
    			}	
    		      	
    		  // popolo il bean di out
    	      WSTrovaDocFolderBean  result = new WSTrovaDocFolderBean();

    	      // AttributiXRicercaFTOut
    		  if (output.getResultBean().getAttributixricercaftout() != null && !output.getResultBean().getAttributixricercaftout().equalsIgnoreCase("")){
    			  result.setAttributiXRicercaFT(output.getResultBean().getAttributixricercaftout());
    		  }
    			  
    		  // FlgUDFolderOut
    		  if (output.getResultBean().getFlgudfolderout()!= null && !output.getResultBean().getFlgudfolderout().equalsIgnoreCase("")){
    			  result.setFlgUDFolder(output.getResultBean().getFlgudfolderout());
    		  }
    		  
    		  // CercaInFolderOut
    		  if (output.getResultBean().getCercainfolderout()!= null){
    			  result.setCercaInFolder(output.getResultBean().getCercainfolderout().longValue());
    		  }
    		      				  
    		  // FlgCercaInSubFolderOut
    		  if (output.getResultBean().getFlgcercainsubfolderout()!= null){
    			  result.setFlgCercaInSubFolder(output.getResultBean().getFlgcercainsubfolderout().toString());
    		  }
    		  
    		  
    		  // momentanemante forzo = 1
    		  // result.setFlgCercaInSubFolder("1");
    		  
    		      		  
    		  // FiltroFullTextOutOut
    		  if (output.getResultBean().getFiltrofulltextout()!= null && !output.getResultBean().getFiltrofulltextout().equalsIgnoreCase("")){
    			  result.setFiltroFullText(output.getResultBean().getFiltrofulltextout());
    		  }
    		  
    		  // FlgTutteLeParoleOut
    		  if (output.getResultBean().getFlgtutteleparoleout()!= null){
    			  result.setFlgTutteLeParole(output.getResultBean().getFlgtutteleparoleout());
    		  }
    		  
    		  // CriteriAvanzatiOut
    		  if (output.getResultBean().getCriteriavanzatiout()!= null && !output.getResultBean().getCriteriavanzatiout().equalsIgnoreCase("")){
    			  result.setCriteriAvanzati(output.getResultBean().getCriteriavanzatiout());
    		  }
    		  
    		  // CriteriPersonalizzatiOut
    		  if (output.getResultBean().getCriteripersonalizzatiout()!= null && !output.getResultBean().getCriteripersonalizzatiout().equalsIgnoreCase("")){
    			  result.setCriteriPersonalizzati(output.getResultBean().getCriteripersonalizzatiout());
    		  }
    		  
    		  // ColOrderByOut
    		  if (output.getResultBean().getColorderbyout()!= null && !output.getResultBean().getColorderbyout().equalsIgnoreCase("")){
    			  result.setColOrderBy(output.getResultBean().getColorderbyout());
    		  }
    		  
    		  // FlgDescOrderByOut
    		  if (output.getResultBean().getFlgdescorderbyout()!= null && !output.getResultBean().getFlgdescorderbyout().equalsIgnoreCase("")){
    			  result.setFlgDescOrderBy(output.getResultBean().getFlgdescorderbyout());
    		  }
    		  
    		  // FlgSenzaPaginazioneOut
    		  if (output.getResultBean().getFlgsenzapaginazioneout()!= null){
    			  result.setFlgSenzaPaginazione(output.getResultBean().getFlgsenzapaginazioneout());
    		  }
    		  
    		  // NroPaginaOut
    		  if (output.getResultBean().getNropaginaout()!= null){
    			  result.setNroPagina(output.getResultBean().getNropaginaout());
    		  }
    		  
    		  // BachSizeOut
    		  if (output.getResultBean().getBachsizeout()!= null){
    			  result.setBachSize(output.getResultBean().getBachsizeout());
    		  }
    		  
    		  // FlgBatchSearchOut
    		  if (output.getResultBean().getFlgbatchsearchout()!= null){
    			  result.setFlgBatchSearch(output.getResultBean().getFlgbatchsearchout());
    		  }
    		  
    		  // ColToReturnOut
    		  if (output.getResultBean().getColtoreturnout()!= null && !output.getResultBean().getColtoreturnout().equalsIgnoreCase("")){
    			  result.setColToReturn(output.getResultBean().getColtoreturnout());
    		  }
    		 // result.setBachSize(1);
    		  
    		  return result;
 			}
 		catch (Exception e){
 			throw new Exception(e.getMessage()); 			
 		}
    }
    
    private WSTrovaDocFolderOutBean eseguiServizio(AurigaLoginBean loginBean, WSTrovaDocFolderBean bean) throws Exception {
    	aLogger.debug("Eseguo il servizio di AurigaDocument.");
    	

    	WSTrovaDocFolderOutBean  ret = new WSTrovaDocFolderOutBean();
    	
    	// creo l'input
	    FindRepositoryObjectBean input = new FindRepositoryObjectBean();
	    input.setFiltroFullText(bean.getFiltroFullText());
	    input.setFormatoEstremiReg("");
	    input.setSearchAllTerms(bean.getFlgTutteLeParole());
	    input.setFlgUdFolder(bean.getFlgUDFolder());
	    input.setIdFolderSearchIn(bean.getCercaInFolder());			
	    input.setFlgSubfoderSearchIn(bean.getFlgCercaInSubFolder());				
	    input.setAdvancedFilters(bean.getCriteriAvanzati());
	    input.setCustomFilters(bean.getCriteriPersonalizzati());
	    input.setColsOrderBy(bean.getColOrderBy());
	    input.setFlgDescOrderBy(bean.getFlgDescOrderBy());
	    input.setFlgSenzaPaginazione(bean.getFlgSenzaPaginazione());
	    //input.setNumPagina(bean.getNroPagina());
	    //input.setNumRighePagina(bean.getBachSize());
	    input.setOnline(bean.getFlgBatchSearch());
	    input.setColsToReturn(bean.getColToReturn());
	    input.setPercorsoRicerca("");
	    input.setFlagTipoRicerca("R");
	    input.setFinalita("");
	    input.setUserIdLavoro(loginBean.getIdUserLavoro());
	    				
		// Converte l'xml in String[] 
		String checkAttributes[] = new String[0];
		checkAttributes = convertXmlInListString(bean.getAttributiXRicercaFT());		
		input.setCheckAttributes(checkAttributes);
		
    	// eseguo il servizio
		try {
				GestioneFascicoli servizio = new GestioneFascicoli();
				TrovaDocFolderOut servizioOut = new TrovaDocFolderOut();
				WSTrovaDocFolder ws =this;
				VersionHandler vh = ws.getVersionHandlerWS();
				servizioOut = servizio.trovaDocFolderWS(loginBean, input,vh);
    	    
				// Se il servizio e' andato in errore restituisco il messaggio di errore 	    	    
				if(servizioOut.isInError()) {
					throw new Exception(servizioOut.getDefaultMessage());	
				}

				String xmlDefaultMessageOut = servizioOut.getDefaultMessage();
				
				String xmlResultSetOut = servizioOut.getResultOut();
	    	    String xmlPercorsiOut = servizioOut.getPercorsoRicercaXMLOut();
	    	    String dettagliCercaInFolderOut = servizioOut.getDettagliCercaInFolderOut();
	    	    
				String numTotRecOut    =  "";
				if(servizioOut.getNroTotRecOut()!=null)
					numTotRecOut    =  servizioOut.getNroTotRecOut().toString();
				
				String numRecInPagOut = "";
				if(servizioOut.getNroRecInPaginaOut()!=null)
					numRecInPagOut = servizioOut.getNroRecInPaginaOut().toString();
				
				//ritocco la lista A MANO aggiungendo i 2 attributi in piu
				if (xmlResultSetOut != null && xmlResultSetOut.indexOf("<Lista")!= -1) {
					String prima = xmlResultSetOut.substring(0, xmlResultSetOut.indexOf("<Lista"));
					String dopo = xmlResultSetOut.substring(xmlResultSetOut.indexOf("<Lista")+6);
					String attrs = "<Lista NroTotaleRecord=\""+numTotRecOut+"\" ";
					if (numRecInPagOut != null && !"".equals(numRecInPagOut.trim()) && !"0".equals(numRecInPagOut.trim())) {
						Integer tot = new Integer(numTotRecOut);
						Integer recPag = new Integer(numRecInPagOut);
						int pagine = tot.intValue()/recPag.intValue();
						if (tot.intValue()%recPag.intValue()!=0) pagine+=1;
						attrs += "NroPagine=\""+pagine+"\"";
					}
					xmlResultSetOut = prima + attrs + dopo;
				}
				// Restituisco l'xml
		     	ret.setXmlRegOut(xmlResultSetOut);
		     	ret.setWarnRegOut(xmlDefaultMessageOut);
		     	
	 		}
	 	catch (Exception e){
	 		throw new Exception(e.getMessage());	
	 	}
	 	aLogger.debug("xmlOut = " + ret);
    	return ret;
    }
    
    private String[] convertXmlInListString(String xmlIn) throws Exception {
    	
    	List<String> lString = new ArrayList<String>();
    	
    	if( xmlIn!=null && !xmlIn.equalsIgnoreCase("") && xmlIn.length()> 0){
    		Lista ls =  null;
    		StringReader sr = new StringReader(xmlIn);  
        	ls = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
        	if ( ls != null && ls.getRiga().size() > 0) {
        		for (int i = 0; i < ls.getRiga().size(); i++) {
        			// prendo la riga i-esima
    				Riga r = ls.getRiga().get(i);
    				for(int j = 0; j < r.getColonna().size(); j++) {
    					Colonna c = r.getColonna().get(j);	
    					if ( c!=null ){
    					    String val = c.getContent();					    
    					    lString.add(val);
    					    
    					}
    				}
        		}
        	}	
    	}    	
    	
    	String out[] = new String[lString.size()];
    	
    	out = lString.toArray(new String[lString.size()]);
    	
    	return out;    	
    }
    
    
    /**
     * Genera il file XML contenente l'URI del folder camcellato
     * Questo file viene passato come allegato in caso di successo.
     *
     * @param String idFolder
     * @return String stringa XML secondo il formato per il ritorno dell'idFolder
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
        }
        catch (Exception e){
 			throw new Exception(e.getMessage());
 			
 		}
        return xml.toString();
    }
}