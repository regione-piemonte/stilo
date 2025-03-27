/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.repository2.jaxws.webservices.notificadocfolder;

import it.eng.auriga.database.store.dmpk_collaboration.bean.DmpkCollaborationNotificaBean;
import it.eng.auriga.database.store.dmpk_collaboration.store.Notifica;
import it.eng.auriga.database.store.dmpk_ws.bean.DmpkWsNotificadocfolderBean;
import it.eng.auriga.database.store.dmpk_ws.store.Notificadocfolder;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.beans.SpecializzazioneBean;
import it.eng.auriga.module.business.entity.WSTrace;
import it.eng.auriga.repository2.jaxws.webservices.common.JAXWSAbstractAurigaService;
import it.eng.auriga.repository2.jaxws.webservices.util.InvioMailBean;
import it.eng.auriga.repository2.util.DBHelperSavePoint;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import it.eng.aurigasendmail.SimpleSendMailService;
import it.eng.aurigasendmail.bean.AurigaDummyMailToSendBean;
import it.eng.aurigasendmail.bean.AurigaResultSendMail;
import it.eng.document.function.StoreException;

import java.lang.reflect.InvocationTargetException;



/**
 *
 * @author Ottavio Passalacqua
 *
 */

@WebService(targetNamespace = "http://notificadocfolder.webservices.repository2.auriga.eng.it", endpointInterface = "it.eng.auriga.repository2.jaxws.webservices.notificadocfolder.WSINotificaDocFolder", name = "WSNotificaDocFolder")
@MTOM(enabled = true, threshold = 0)

public class WSNotificaDocFolder extends JAXWSAbstractAurigaService implements WSINotificaDocFolder{	

    private final String K_SAVEPOINTNAME = "INIZIOWSNOTIFICADOCFOLDER";
    
    static Logger aLogger = Logger.getLogger(WSNotificaDocFolder.class.getName());    
    
   
    
   // private EmailContainer emailSender = null;
    
    public WSNotificaDocFolder() {
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
    WSNotificaDocFolderBean outServizio = new WSNotificaDocFolderBean();
    String outRispostaWS = null;
    String errMsg = null;
    String xmlIn = null;
    Integer errCode = JAXWSAbstractAurigaService.ERR_ERRORE_APPLICATIVO;
    
    try {

        aLogger.info("Inizio WSNotificaDocFolder");
        
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
        WSNotificaDocFolderBean outWS = new WSNotificaDocFolderBean();
        
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
	 	  	xmlIn = outServizio.getURIXml();	
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

        aLogger.info("Fine WSNotificaDocFolder");
   
        return risposta;
    }
    catch (Exception excptn) {
        aLogger.error("WSNotificaDocFolder: " + excptn.getMessage(), excptn);
        return   generaXMLRisposta( JAXWSAbstractAurigaService.FALLIMENTO, JAXWSAbstractAurigaService.ERR_ERRORE_APPLICATIVO, JAXWSAbstractAurigaService.ERROR_ERRORE_APPLICATIVO, "", "" );
        //throw excptn;
	}
	finally
	{
    	try { DBHelperSavePoint.RollbackToSavepoint(conn, K_SAVEPOINTNAME); } catch (Exception ee) {}
	    aLogger.info("Fine WSNotificaDocFolder serviceImplementation");
	}
    }
        
    private WSNotificaDocFolderBean callWS(AurigaLoginBean loginBean, String xmlIn) throws Exception {
    	aLogger.debug("Eseguo il WS DMPK_WS->NotificaDocFolder.");
  		
    	// Inizializzo l'INPUT    		
    	DmpkWsNotificadocfolderBean input = new DmpkWsNotificadocfolderBean();
    	input.setCodidconnectiontokenin(loginBean.getToken());
    	input.setXmlin(xmlIn);
	  
    	// Eseguo il servizio
    	Notificadocfolder service = new Notificadocfolder();
    	StoreResultBean<DmpkWsNotificadocfolderBean> output = service.execute(loginBean, input);

    	if (output.isInError()){
    		aLogger.debug(output.getDefaultMessage());
    		aLogger.debug(output.getErrorContext());
    		aLogger.debug(output.getErrorCode());
    		throw new StoreException(output);
    	}	
	  
    	if (output.getResultBean().getIdobjtonotifout()== null){
		  throw new Exception("La store procedure ha ritornato idObj nullo");
    	}
	      			  
    	if (output.getResultBean().getFlgtypeobjtonotifout()== null){
		  throw new Exception("La store procedure ha ritornato flgTypeObj nullo");
    	}
	  
    	if (!output.getResultBean().getFlgtypeobjtonotifout().equalsIgnoreCase("U") && !output.getResultBean().getFlgtypeobjtonotifout().equalsIgnoreCase("F")){
		  throw new Exception("E' possibile effettuare la notifica solo di cartelle o unita' documentarie");
    	}
	    			
    	// popolo il bean di out
    	WSNotificaDocFolderBean  result = new WSNotificaDocFolderBean();

    	// FlgTypeObjToNotifOut
    	if (output.getResultBean().getFlgtypeobjtonotifout() != null && !output.getResultBean().getFlgtypeobjtonotifout().equalsIgnoreCase("")){
		  result.setFlgTypeObjToNotifOut(output.getResultBean().getFlgtypeobjtonotifout());
    	}
		  
    	// IdObjToNotifOut
    	if (output.getResultBean().getIdobjtonotifout()!= null){
		  result.setIdObjToNotifOut(output.getResultBean().getIdobjtonotifout());
    	}    		  
	  
    	// Recipientsxmlout (lista destinatari)
    	if (output.getResultBean().getRecipientsxmlout() != null && !output.getResultBean().getRecipientsxmlout().equalsIgnoreCase("")){
		  result.setRecipientsXml(output.getResultBean().getRecipientsxmlout());
    	}
	  
    	// SenderTypeOut
    	if (output.getResultBean().getSendertypeout() != null && !output.getResultBean().getSendertypeout().equalsIgnoreCase("")){
		  result.setSenderType(output.getResultBean().getSendertypeout());
    	}
	  
    	// SenderIdOut
    	if (output.getResultBean().getSenderidout()!= null){
		  result.setIdSender(output.getResultBean().getSenderidout());
    	}
	  
    	// CodMotivoNotifOut
    	if (output.getResultBean().getCodmotivonotifout() != null && !output.getResultBean().getCodmotivonotifout().equalsIgnoreCase("")){
		  result.setCodMotivoNotifica(output.getResultBean().getCodmotivonotifout());
    	}
	  
    	// MotivoNotifOut
    	if (output.getResultBean().getMotivonotifout()!= null && !output.getResultBean().getMotivonotifout().equalsIgnoreCase("")){
		  result.setMotivoNotifica(output.getResultBean().getMotivonotifout());
    	}
	  
    	// MessaggioNotifOut
    	if (output.getResultBean().getMessaggionotifout()!= null && !output.getResultBean().getMessaggionotifout().equalsIgnoreCase("")){
		  result.setMessaggioNotifica(output.getResultBean().getMessaggionotifout());
    	}
	  
    	// LivelloPrioritaOut
    	if (output.getResultBean().getLivelloprioritaout()!= null ){
		  result.setLivelloPriorita(output.getResultBean().getLivelloprioritaout());
    	}
	  
    	// RichConfermaPresaVisOut
    	if (output.getResultBean().getRichconfermapresavisout()!= null ){
		  result.setRichConfermaPresaVis(output.getResultBean().getRichconfermapresavisout());
    	}
	  
    	// FlgEmailNotifPresaVisOut
    	if (output.getResultBean().getFlgemailnotifpresavisout()!= null){
		  result.setFlgEmailNotifPresaVis(output.getResultBean().getFlgemailnotifpresavisout());
    	}
	  
    	// IndEmailNotifPresaVisOut
    	if (output.getResultBean().getIndemailnotifpresavisout()!= null && !output.getResultBean().getIndemailnotifpresavisout().equalsIgnoreCase("")){
		  result.setIndEmailNotifPresaVis(output.getResultBean().getIndemailnotifpresavisout());
    	}
	  
    	// NotNoPresaVisEntroGGOut
    	if (output.getResultBean().getNotnopresavisentroggout()!= null){
		  result.setNotNoPresaVisEntroGG(output.getResultBean().getNotnopresavisentroggout());
    	}
	  
    	// FlgEmailNoPresaVisOut
    	if (output.getResultBean().getFlgemailnopresavisout()!= null){
		  result.setFlgEmailNoPresaVis(output.getResultBean().getFlgemailnopresavisout());
    	}
	  
    	// IndEmailNoPresaVisOut
    	if (output.getResultBean().getIndemailnopresavisout()!= null && !output.getResultBean().getIndemailnopresavisout().equalsIgnoreCase("")){
		  result.setIndEmailNoPresaVis(output.getResultBean().getIndemailnopresavisout());
    	}
	  
    	// TsDecorrenzaNotifOut
    	if (output.getResultBean().getTsdecorrenzanotifout()!= null && !output.getResultBean().getTsdecorrenzanotifout().equalsIgnoreCase("")){
		  result.setTsDecorrenzaNotifica(output.getResultBean().getTsdecorrenzanotifout());
    	}
	      		  
    	// FlgNotificaEmailNotifOut
    	if (output.getResultBean().getFlgnotificaemailnotifout()!= null){
		  result.setFlgNotificaEmailNotif(output.getResultBean().getFlgnotificaemailnotifout());
    	}
	  
    	// IndXNotifEmailNotifOut
    	if (output.getResultBean().getIndxnotifemailnotifout()!= null && !output.getResultBean().getIndxnotifemailnotifout().equalsIgnoreCase("")){
		  result.setIndXNotifEmailNotif(output.getResultBean().getIndxnotifemailnotifout());
    	}
	  
    	// OggEmailOut
    	if (output.getResultBean().getOggemailout()!= null && !output.getResultBean().getOggemailout().equalsIgnoreCase("")){
		  result.setOggettoEmail(output.getResultBean().getOggemailout());
    	}
	  
    	// BodyEmailOut
    	if (output.getResultBean().getBodyemailout()!= null && !output.getResultBean().getBodyemailout().equalsIgnoreCase("")){
		  result.setBodyEmail(output.getResultBean().getBodyemailout());
    	}
	  
    	// FlgNotificaSMSNotifOut
    	if (output.getResultBean().getFlgnotificasmsnotifout()!= null ){
		  result.setFlgNotificaSMSNotif(output.getResultBean().getFlgnotificasmsnotifout());
    	}
	  
    	// NriCellXNotifSMSNotifOut
    	if (output.getResultBean().getNricellxnotifsmsnotifout()!= null && !output.getResultBean().getNricellxnotifsmsnotifout().equalsIgnoreCase("")){
		  result.setNriCellXNotifSMSNotif(output.getResultBean().getNricellxnotifsmsnotifout());
    	}

    	// TestoSMSOut
    	if (output.getResultBean().getTestosmsout()!= null && !output.getResultBean().getTestosmsout().equalsIgnoreCase("")){
		  result.setTestoSMS(output.getResultBean().getTestosmsout());
    	}
	      		  
	  return result;
    }
    
    private WSNotificaDocFolderBean eseguiServizio(AurigaLoginBean loginBean, WSNotificaDocFolderBean bean) throws Exception {
    	aLogger.debug("Eseguo il servizio  DMPK_COLLABORATION.Notifica");
    		    	
	 	// Inizializzo l'INPUT
	    DmpkCollaborationNotificaBean input = new DmpkCollaborationNotificaBean();
	    input.setCodidconnectiontokenin(loginBean.getToken());
	    input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
	    input.setFlgtypeobjtonotifin(bean.getFlgTypeObjToNotifOut());	    	    
	    input.setIdobjtonotifin(bean.getIdObjToNotifOut());	    	    
	    input.setRecipientsxmlin(bean.getRecipientsXml());	    	    
	    input.setSendertypein(bean.getSenderType());
	    input.setSenderidin(bean.getIdSender());
	    input.setCodmotivonotifin(bean.getCodMotivoNotifica());
	    input.setMotivonotifin(bean.getMotivoNotifica());
	    input.setMessaggionotifin(bean.getMessaggioNotifica());
	    input.setLivelloprioritain(bean.getLivelloPriorita());
	    input.setRichconfermapresavisin(bean.getRichConfermaPresaVis());
	    input.setFlgemailnotifpresavisin(bean.getFlgEmailNotifPresaVis());
	    input.setIndemailnotifpresavisin(bean.getIndEmailNotifPresaVis());
	    input.setNotnopresavisentroggin(bean.getNotNoPresaVisEntroGG());
	    input.setFlgemailnopresavisin(bean.getFlgEmailNoPresaVis());	    	    
	    input.setIndemailnopresavisin(bean.getIndEmailNoPresaVis());
	    input.setTsdecorrenzanotifin(bean.getTsDecorrenzaNotifica());
	    input.setFlgnotificaemailnotifin(bean.getFlgNotificaEmailNotif());
	    input.setIndxnotifemailnotifio(bean.getIndXNotifEmailNotif());
	    input.setFlgnotificasmsnotifin(bean.getFlgNotificaSMSNotif());
	    input.setNricellxnotifsmsnotifio(bean.getNriCellXNotifSMSNotif());
	    
	    // Eseguo il servizio
	    Notifica service = new Notifica();
	    StoreResultBean<DmpkCollaborationNotificaBean> output = service.execute(loginBean, input);
		if (output.isInError()){
			aLogger.debug(output.getDefaultMessage());
			aLogger.debug(output.getErrorContext());
			aLogger.debug(output.getErrorCode());
			throw new StoreException(output);
		}
		
		//popolo il bean da tornare
        
		 // URIXmlOut
        bean.setURIXml(output.getResultBean().getUrixmlout());
       
        //se oggettoEmail, bodyEmail o testoSMS erano gia' valorizzati in input non li cambio
		// IndXNotifEmailNotifIO
        bean.setIndXNotifEmailNotif(output.getResultBean().getIndxnotifemailnotifio());
        
        // NriCellXNotifSMSNotifIO
        bean.setNriCellXNotifSMSNotif(output.getResultBean().getNricellxnotifsmsnotifio());
        
        // OggEmailOut
        if(bean.getOggettoEmail() == null || "".equals(bean.getOggettoEmail())) {
        	bean.setOggettoEmail(output.getResultBean().getOggemailout());
        }
        
        // BodyEmailOut
        if(bean.getBodyEmail() == null || "".equals(bean.getBodyEmail())) {
        	bean.setBodyEmail(output.getResultBean().getBodyemailout());
        }            
        
        // TestoSMSOut
        if(bean.getTestoSMS() == null || "".equals(bean.getTestoSMS())) {
        	bean.setTestoSMS(output.getResultBean().getTestosmsout());
        }	    
        	            
        // Invio la mail di notifica
        if(bean.getFlgNotificaEmailNotif() == 1 ) {
        	if (bean.getIndXNotifEmailNotif() != null) {	            		
        		 // Invio la mail di alert
				try {	    
					InvioMailBean lInvioMailBean = new InvioMailBean();
					lInvioMailBean.setMittente(bean.getMittenteEmail());
					lInvioMailBean.setDestinatari(bean.getIndXNotifEmailNotif());
					lInvioMailBean.setBodyHtml(bean.getBodyEmail());						
					lInvioMailBean.setOggetto(bean.getOggettoEmail());	
					List<String> errorMessages = new ArrayList<String>();
					errorMessages = inviaEmail(lInvioMailBean);
					if (errorMessages.size()>0 ){
						String listErrorMessages="";
						for (int i = 0; i < errorMessages.size(); i++) {
							if (errorMessages.get(i)!=null)
								listErrorMessages = listErrorMessages + errorMessages.get(i) +"\n";
						}	
						if (!listErrorMessages.equalsIgnoreCase(""))
    						throw new Exception(listErrorMessages);
					}						
				}
				catch (Exception elog) {
					throw new Exception(elog.getMessage());
				}
        	}
        }
	
	 	return bean;  	 	
    }
    
    /**
     * Genera il file XML contenente l'URI del folder camcellato
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
            aLogger.debug("generaXMLToken: token = " + xmlIn);
            aLogger.debug("generaXMLToken: tokenEsc = " + xmlInEsc);
            xml.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n");
            xml.append("<UriXml>" + xmlInEsc + "</UriXml>\n");
            aLogger.debug(xml.toString());
        }
        catch (Exception e){
 			throw new Exception(e.getMessage());
 			
 		}
        return xml.toString();
    }
    
 
    // Invia una mail
	public List<String>  inviaEmail(InvioMailBean pInvioMailBean) throws Exception{		

		// Inizializza i parametri di input
		AurigaDummyMailToSendBean lDummyMailToSendBean = new AurigaDummyMailToSendBean();
				
		// from
		lDummyMailToSendBean.setFrom(pInvioMailBean.getMittente());
		
		// to
		List<String> destinatari = new ArrayList<String>();
		String[] lStringDestinatari = pInvioMailBean.getDestinatari().split(";");
		destinatari = Arrays.asList(lStringDestinatari);
		lDummyMailToSendBean.setAddressTo(destinatari);

		// oggetto
		lDummyMailToSendBean.setSubject(pInvioMailBean.getOggetto());
		
		// testo
		lDummyMailToSendBean.setBody(pInvioMailBean.getBodyHtml());
		
		lDummyMailToSendBean.setHtml(true);
		
		// Invia la mail 
		List<String> errorMessages = new ArrayList<String>();
		try {
			AurigaResultSendMail lResultSendMail = new SimpleSendMailService().sendFromConfigured(lDummyMailToSendBean, "helpdesk"); 
			
			if (!lResultSendMail.isInviata()){
				errorMessages	= 	lResultSendMail.getErrori();
			}
		} catch (IllegalAccessException e) {
			aLogger.warn(e);
			//throw new StoreException(e.getMessage());
		} catch (InvocationTargetException e) {
			aLogger.warn(e);
			//throw new StoreException(e.getMessage());
		}		
		return errorMessages;
	}
}
