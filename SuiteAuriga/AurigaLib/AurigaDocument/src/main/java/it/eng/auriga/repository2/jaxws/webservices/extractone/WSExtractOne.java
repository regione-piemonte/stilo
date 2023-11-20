/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.ws.soap.MTOM;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.jsoup.helper.StringUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_registrazionedoc.bean.DmpkRegistrazionedocGettimbrodigregBean;
import it.eng.auriga.database.store.dmpk_utility.bean.DmpkUtilityGetidudofdocBean;
import it.eng.auriga.database.store.dmpk_utility.store.Getidudofdoc;
import it.eng.auriga.database.store.dmpk_ws.bean.DmpkWsExtractfileudBean;
import it.eng.auriga.database.store.dmpk_ws.store.Extractfileud;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.beans.SpecializzazioneBean;
import it.eng.auriga.module.business.entity.WSTrace;
import it.eng.auriga.repository2.jaxws.webservices.addunitadoc.AttachWSBean;
import it.eng.auriga.repository2.jaxws.webservices.common.JAXWSAbstractAurigaService;
import it.eng.auriga.repository2.jaxws.webservices.common.bean.AttachWSProperties;
import it.eng.auriga.repository2.util.DBHelperSavePoint;
import it.eng.auriga.ui.module.layout.server.common.MergeDocument;
import it.eng.client.DmpkRegistrazionedocGettimbrodigreg;
import it.eng.document.function.RecuperoFile;
import it.eng.fileOperation.clientws.PaginaTimbro;
import it.eng.fileOperation.clientws.PaginaTimbro.Pagine;
import it.eng.fileOperation.clientws.PosizioneRispettoAlTimbro;
import it.eng.fileOperation.clientws.PosizioneTimbroNellaPagina;
import it.eng.fileOperation.clientws.TipoPagina;
import it.eng.fileOperation.clientws.TipoRotazione;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.util.FattureUtil;
import it.eng.util.TimbraturaUtility;
import it.eng.utility.TimbraUtil;
import it.eng.utility.ui.module.layout.shared.bean.OpzioniCopertinaTimbroBean;

/**
 * @author Ottavio passalacqua
 */


@WebService(targetNamespace = "http://extractone.webservices.repository2.auriga.eng.it",  endpointInterface="it.eng.auriga.repository2.jaxws.webservices.extractone.WSIExtractOne", name = "WSExtractOne")
@MTOM(enabled = true, threshold = 0)
@HandlerChain(file = "../common/handler.xml")
public class WSExtractOne extends JAXWSAbstractAurigaService implements WSIExtractOne{	

	private final String K_SAVEPOINTNAME = "INIZIOWSEXTRACTONE";

	static Logger aLogger = Logger.getLogger(WSExtractOne.class.getName());    

	public WSExtractOne() {
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
		WSExtractOneBean outServizio = new WSExtractOneBean();

		String errMsg = null;
		String warnMessage = null;
		String xmlIn = null;

		try {

			aLogger.info("Inizio WSExtractOne");

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
			WSExtractOneBean outWS = new WSExtractOneBean();
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
			ByteArrayInputStream inputStreamXml = null;
			ByteArrayInputStream bais = null;
			
			try {
				// Creo XML di risposta
				outRispostaWS = generaXMLRispostaWS(xmlIn);

				// Creo la lista di attach
				List<AttachWSProperties> listaAttach = new ArrayList<AttachWSProperties>();
				
				// Converto l'XML
				inputStreamXml = new ByteArrayInputStream(outRispostaWS.getBytes());

				// Aggiungo l'XML
				AttachWSProperties xmlAttach = new AttachWSProperties(); 
				xmlAttach.setInputStream(inputStreamXml);
				xmlAttach.setMimeType("application/xml");
				xmlAttach.setNameFile("response.xml");
				listaAttach.add(xmlAttach);
				
				File fileEstratto = null;
				
				if(outServizio.getExtracted()!=null && outServizio.getExtracted().size()!=0) {
					fileEstratto = outServizio.getExtracted().get(0);
				}
					
				/*Se la store mi ha ritornato piu file procedo a fare l'unione e lo rendo un unico file*/
				if(outServizio.getExtracted()!=null && outServizio.getExtracted().size()!=0 && outServizio.getExtracted().size()>1) {
					fileEstratto = unisciFile(outServizio.getExtracted());
				}
				
				// Aggiungo il FILE ATTACH
//				aLogger.info("orima ATTACH: ");
				if(outServizio!=null && outServizio.getExtracted() != null && outServizio.getExtracted().size()!=0){
					 
					bais = new ByteArrayInputStream(FileUtils.readFileToByteArray(fileEstratto));
					
					AttachWSProperties attachEstratto = new AttachWSProperties();
					attachEstratto.setInputStream(bais);
					String mimeType = "application/octet-stream";
					if(!outServizio.isFirmato() && StringUtils.isNotBlank(outServizio.getMimeType())) {
						mimeType = outServizio.getMimeType();
					}
					attachEstratto.setMimeType(mimeType);
					attachEstratto.setNameFile(outServizio.getFileName());
					
					//Vedo se sono state passate in input i parametri per la timbratura
					String tagXmlParametriTimbratura = getTagParametriTimbro(xml);					
					
					//Vedo se sono state passate in input i parametri per lo sbustamento
					boolean flgSbusta = getflgSbusta(xml);
					
					//Vedo se sono state passate in input i parametri per coversione in pdf
					boolean getPdf = getTagParametriPdf(xml);

					if (getPdf) {
						InputStream inputStreamPdf;
						
						/*Verifico se il file è una fattura e quindi genero il pdf per le fatture*/
						FattureUtil fattureUtil = new FattureUtil();
						File filePdf = fattureUtil.generaPdfFattura(loginBean, fileEstratto, outServizio.isFirmato(), 
								outServizio.getFileName(), outServizio.getIdDoc(), outServizio.getIdUd(), outServizio.getMimeType());
						
						/*SE FILEPDF E' VUOTO SIGNIFICA CHE NON E' UNA FATTURA E DEVO CONVERTIRLO NORMALMENTE IN PDF*/
						if(filePdf==null) {
							InfoFileUtility infoFileUtility = new InfoFileUtility(); 
							inputStreamPdf = infoFileUtility.converti(fileEstratto.toURI().toString(), outServizio.getFileName());
						}else {
							inputStreamPdf = new FileInputStream(filePdf);
						}
					
						addAttachToReturn(listaAttach, inputStreamPdf, "application/pdf",
								getNomeFileSbustato(outServizio.getFileName()));
					}else if(flgSbusta) {
						
						try {
							if(outServizio!=null && outServizio.isFirmato()) {
								
								InfoFileUtility infoFileUtility = new InfoFileUtility(); 
								InputStream is = infoFileUtility.sbusta(fileEstratto, fileEstratto.getName());
								
								addAttachToReturn(listaAttach, is, outServizio.getMimeType(), getNomeFileSbustato(outServizio.getFileName()));
							}else {
								// Altrimenti aggiungo il file originale
								listaAttach.add(attachEstratto);					
							}
						} catch (Exception e) {
							aLogger.error("Errore durante il processo di sbustamento: " + e.getMessage(), e);
							warnMessage = "E' avvenuto un errore durante il processo di sbustamento: " + e.getMessage() + "\nRestituisco il file non sbustato";
							
							listaAttach.add(attachEstratto);
						}
					}
					
					/** CONTROLLO SE IL FILE VA TIMBRATO*/
					else if (StringUtils.isNotBlank(tagXmlParametriTimbratura)) {
						
						try {
							String finalita = getContentTag("Finalita", tagXmlParametriTimbratura);
							
							// Leggo il flg per costruire la busta pdf per i file
							Boolean flgBustaPdf = getFlgBustaPdf(tagXmlParametriTimbratura);
												
							/**DEVO CREARE LA BUSTA PDF**/
							if (flgBustaPdf!= null && flgBustaPdf) {

								boolean flgBustaPerPubblicazione = getFlgBustaPubblicazione(tagXmlParametriTimbratura);

								File bustaTimbroPdf = TimbraturaUtility.creaBustaPdf(loginBean, outServizio.getIdUd(),
										outServizio.getIdDoc(), finalita, fileEstratto,
										"File_" + outServizio.getIdDoc(), flgBustaPerPubblicazione);

								addAttachToReturn(listaAttach, new FileInputStream(bustaTimbroPdf), "application/pdf", FilenameUtils.getBaseName(getNomeFileSbustato(outServizio.getFileName())) + "_busta_con_allegati.pdf");
							}
							
							/**DEVO TIMBRARE NORMALMENTE IL FILE*/
							// Leggo le opzioni del timbro
							else if (getOpzioniTimbro(tagXmlParametriTimbratura) != null
									&& getOpzioniTimbro(tagXmlParametriTimbratura).getPosizioneTimbro() != null && !outWS.isTimbrato()) {
								// Leggo le opzioni del timbro
								OpzioniCopertinaTimbroBean opzioniCopertinaTimbroBean = getOpzioniTimbro(
										tagXmlParametriTimbratura);

								WSExtractOneInputStreamsTypesBean lVersioneTimbrataTypesBean = getVersioneTimbrata(
										loginBean, opzioniCopertinaTimbroBean, outServizio, finalita, fileEstratto);
								// Aggiungo il file timbrato
								if (lVersioneTimbrataTypesBean != null
										&& lVersioneTimbrataTypesBean.getInputStream() != null) {
									addAttachToReturn(listaAttach, lVersioneTimbrataTypesBean.getInputStream(), lVersioneTimbrataTypesBean.getInputStreamsTypes(), FilenameUtils.getBaseName(getNomeFileSbustato(outServizio.getFileName())) + "_timbrato.pdf");
								}

							}else{
								warnMessage = "Errore nella lettura dei valori della tipologia di Timbro o il file archiviato e' gia timbrato, restituisco il file archiviato";
								
								listaAttach.add(attachEstratto);
							}
						} catch (Exception e) {
							aLogger.error("Errore durante il processo di timbratura: " + e.getMessage(), e);
							warnMessage = "E' avvenuto un errore durante il processo di timbratura: " + e.getMessage() + "\nRestituisco il file non timbrato";
							
							listaAttach.add(attachEstratto);
							
						}
					}else{
						listaAttach.add(attachEstratto);
					}
				}
				
					
				// Salvo gli ATTACH alla response
				attachListInputStreamTypes(listaAttach);
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
				if(bais != null) {
					try {
						bais.close(); 
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
				risposta = generaXMLRisposta( JAXWSAbstractAurigaService.SUCCESSO, JAXWSAbstractAurigaService.SUCCESSO, "Tutto OK", "", warnMessage);
			}
			else{
				risposta = generaXMLRisposta( JAXWSAbstractAurigaService.FALLIMENTO, JAXWSAbstractAurigaService.ERR_ERRORE_APPLICATIVO,  errMsg, "", "");
			}
			aLogger.info("Fine WSExtractOne");

			return risposta;
		}

		catch (Exception excptn) {
			aLogger.error("WSExtractOne: " + excptn.getMessage(), excptn);
			return   generaXMLRisposta( JAXWSAbstractAurigaService.FALLIMENTO, JAXWSAbstractAurigaService.ERR_ERRORE_APPLICATIVO, JAXWSAbstractAurigaService.ERROR_ERRORE_APPLICATIVO, "", "" );
		}
		finally
		{
			try { DBHelperSavePoint.RollbackToSavepoint(conn, K_SAVEPOINTNAME); } catch (Exception ee) {}
			aLogger.info("Fine WSExtractOne serviceImplementation");
		}
	}

	
	

	@SuppressWarnings("unused")
	private File unisciFile(List<File> listaFileDaUnire) throws Exception {
		File mergedPdf = File.createTempFile("mergedPdf", ".pdf");
		try(BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(mergedPdf))){		
			
			MergeDocument merger = new MergeDocument();
			merger.mergeDocument(listaFileDaUnire, output, false, false);
		}catch(Exception ex) {
			try {
				/*Se c'è stato un errore con Itext provo a mergeare con pdfBox*/
				mergeWithPdfBox(listaFileDaUnire, mergedPdf);
								
			} catch (IOException e) {
				String errorMessage = "Errore durante l'unione dei file: " + ex.getMessage();
				aLogger.error(errorMessage, ex);
				throw new Exception(errorMessage, ex);
			}
		}
		
		return mergedPdf;
	}
	
	private void mergeWithPdfBox(List<File> listaFileDaUnire, File mergedPdf) throws IOException {
		PDFMergerUtility ut = new PDFMergerUtility();
		for(File file : listaFileDaUnire) {
			ut.addSource(file);
		}
		ut.setDestinationFileName(mergedPdf.getPath());
		ut.mergeDocuments();
		
	}

	private void addAttachToReturn(List<AttachWSProperties> listaAttach, InputStream is, String mimeType,
			String fileName) {
		AttachWSProperties attach = new AttachWSProperties();
		attach.setInputStream(is);
		attach.setMimeType(StringUtils.isNotBlank(mimeType) ? mimeType : "application/octet-stream");
		attach.setNameFile(fileName);
		listaAttach.add(attach);
	}
			
	private Boolean getFlgBustaPdf(String tagXmlParametriTimbratura) throws Exception {
		String flgBustaPDf = getContentTag("FlgBustaPdf", tagXmlParametriTimbratura);
		
		if(flgBustaPDf!=null) {
			if("1".equalsIgnoreCase(flgBustaPDf)) {
				return true;
			}else if("0".equalsIgnoreCase(flgBustaPDf)) {
				return false;
			}else {
				throw new Exception("Valore per il tag FlgBustaPdf non valido");
			}
		}else {
			return null;
		}
			
	}

	private boolean getFlgBustaPubblicazione(String tagXmlParametriTimbratura) {
		String valueAttribute = null;
		try {
			Document doc = convertStringToXMLDocument(tagXmlParametriTimbratura);	
			NodeList nodeList = doc.getElementsByTagName("FlgBustaPdf");
			valueAttribute = nodeList.item(0).getAttributes().getNamedItem("VersPubblicabile").getNodeValue();
		} catch (Exception e) {
			aLogger.error("Errore durante il recupero dell'attributo FlgBustaPdf nell'input: " + e.getMessage());
		}
        
        return StringUtils.isNotBlank(valueAttribute) && "true".equalsIgnoreCase(valueAttribute) ? true : false;
        
	}


	private boolean getflgSbusta(String xml){
		String getSbustato ="";
		try {
			Document doc = convertStringToXMLDocument(xml);			
			try {
				if (doc.getElementsByTagName("GetSbustato").item(0)!=null && doc.getElementsByTagName("GetSbustato").item(0).getFirstChild()!=null)
				    getSbustato = doc.getElementsByTagName("GetSbustato").item(0).getFirstChild().getNodeValue();
			} catch (Exception e1) {
				getSbustato = "0";
			}
		} 
		catch (Exception e) {					
			aLogger.error("Errore durante la lettura del parametro di input GetSbustato: " + e.getMessage());
		}
		return StringUtils.isNotBlank(getSbustato) && "1".equalsIgnoreCase(getSbustato) ? true : false;
	}

	private static String getContentTag(String tagName, String xml) {

		String errMsg = "Errore durante la lettura del tag " + tagName + " dall'xml di input. ";
		// Leggo le opzioni del timbro
		try {
			Document doc = convertStringToXMLDocument(xml);

			String contentTag = doc.getElementsByTagName(tagName) != null
					&& doc.getElementsByTagName(tagName).item(0) != null
							? doc.getElementsByTagName(tagName).item(0).getTextContent()
							: null;

			return contentTag;

		} catch (Exception e) {
			if (e.getMessage() != null) {
				errMsg = "Errore = " + e.getMessage();
			} else {
				errMsg = "Errore imprevisto.";
			}
			aLogger.error(errMsg);
			return null;
		}
	
	}
	
	private static String getTagParametriTimbro(String xml){
		
		String xmlTagTimbro = null;
		
		if (StringUtils.isNotBlank(xml) && xml.indexOf("<Timbro>") > 0){
			try {
				xmlTagTimbro = xml.substring(xml.indexOf("<Timbro>"), xml.indexOf("</Timbro>") + "</Timbro>".length());
				} catch (Exception e) {
					aLogger.error("Errore durante il recupero del parametro di input Timbro: " + e.getMessage());
				}
		}
		return StringUtils.isNotBlank(xmlTagTimbro) ? xmlTagTimbro : null;
	}
	
	private static boolean getTagParametriPdf(String xml){		
		String getPdf ="";
		try {
			Document doc = convertStringToXMLDocument(xml);			
			try {
				if (doc.getElementsByTagName("GetVersPdf").item(0)!=null && doc.getElementsByTagName("GetVersPdf").item(0).getFirstChild()!=null)
					getPdf = doc.getElementsByTagName("GetVersPdf").item(0).getFirstChild().getNodeValue();
			} catch (Exception e1) {
				getPdf = "0";
			}
		} 
		catch (Exception e) {					
			aLogger.error("Errore durante la lettura del parametro di input GetSbustato: " + e.getMessage());
		}
		return StringUtils.isNotBlank(getPdf) && "1".equalsIgnoreCase(getPdf) ? true : false;
	}

	private static String getTagParametriVersione(String xml){
		
		String xmlTagVersione = null;
		
		if (StringUtils.isNotBlank(xml) && xml.indexOf("<Versione>") > 0){
			try {
				xmlTagVersione = xml.substring(xml.indexOf("<Versione>"), xml.indexOf("</Versione>") + "</Versione>".length());
				} catch (Exception e) {
					aLogger.error("Errore durante il recupero del parametro di input Pdf: " + e.getMessage());
				}
		}
		return StringUtils.isNotBlank(xmlTagVersione) ? xmlTagVersione : null;
	}

	private WSExtractOneBean callWS(AurigaLoginBean loginBean, String xmlIn) throws Exception {

		aLogger.debug("Eseguo il WS DmpkWsExtractOne.");

		String idDoc       = null;
		String nroProgrVer = null;
		String xml         = null;  
		boolean flgTimbrato = false;
		try {    		
			// Inizializzo l'INPUT    		
			DmpkWsExtractfileudBean input = new DmpkWsExtractfileudBean();
			input.setCodidconnectiontokenin(loginBean.getToken());
			input.setXmlin(xmlIn);

			// Eseguo il servizio
			Extractfileud service = new Extractfileud();
			StoreResultBean<DmpkWsExtractfileudBean> output = service.execute(loginBean, input);

			if (output.isInError()){
				throw new Exception(output.getDefaultMessage());	
			}	

			// restituisco l'XML
			if(output.getResultBean().getXmlout()!=null){
				xml = output.getResultBean().getXmlout();  
			}
			if (xml== null || xml.equalsIgnoreCase(""))
				throw new Exception("La store procedure ha ritornato XmlOut nullo");

			// restituisco l'ID DOC
			if (output.getResultBean().getIddoctoextractout() != null){
				idDoc = output.getResultBean().getIddoctoextractout().toString();  
			}
			if (idDoc== null || idDoc.equalsIgnoreCase("")){
				throw new Exception("La store procedure ExtractFileUD ha ritornato id doc nullo");
			}

			// restituisco il nro versione
			if (output.getResultBean().getNrovertoextractout()!= null){
				nroProgrVer = output.getResultBean().getNrovertoextractout().toString();
			}
			if (nroProgrVer == null) {
				throw new Exception("La store procedure ExtractFileUD ha ritornato un nro versione nullo");
			}
			if (output.getResultBean().getFlgvertimbrataout()!= null){
				flgTimbrato = output.getResultBean().getFlgvertimbrataout().compareTo(new BigDecimal(1))==0 ? true : false;
			}

			// Leggo l'IDUD dall'iddoc		
			String idUd = getIdUdFromIdDoc(loginBean, idDoc);
			
			// popolo il bean di out
			WSExtractOneBean result = new WSExtractOneBean();
			result.setXml(xml);
			result.setIdDoc(idDoc);
			result.setIdUd(idUd);
			result.setNroProgrVer(nroProgrVer);
			result.setTimbrato(flgTimbrato);
			return result;
		}
		catch (Exception e){
			throw new Exception(e.getMessage()); 			
		}
	}

	

	private WSExtractOneBean eseguiServizio(AurigaLoginBean loginBean, WSExtractOneBean bean) throws Exception {
		aLogger.debug("Eseguo il servizio di AurigaDocument.");

		WSExtractOneBean ret = new WSExtractOneBean();
		File fileOut = null;

		// creo l'input
		BigDecimal idDocIn       = (bean.getIdDoc() != null) ? new BigDecimal(bean.getIdDoc()) : null;	    		
		BigDecimal nroProgrVerIn = (bean.getNroProgrVer() != null) ? new BigDecimal(bean.getNroProgrVer()) : null;

		// eseguo il servizio
		try {
			RecuperoFile servizio = new RecuperoFile();
			List<AttachWSBean> servizioOut = new ArrayList<>();
			servizioOut = servizio.extractPropertiesFilesByIdDoc(loginBean, idDocIn, nroProgrVerIn);	    
			
			List<File> listaFileEstratti = new ArrayList<>();
			for(AttachWSBean lAttaccAttachWSBean : servizioOut) {
				listaFileEstratti.add(lAttaccAttachWSBean.getFile());
			}
			
			ret.setIdUd(bean.getIdUd());
			ret.setIdDoc(bean.getIdDoc());
			ret.setNroProgrVer(bean.getNroProgrVer());
			ret.setXml(bean.getXml());
			ret.setExtracted(listaFileEstratti);
			if(servizioOut!=null && servizioOut.get(0)!=null) {
				ret.setFileName(servizioOut.get(0).getDisplayFilename());
				ret.setMimeType(servizioOut.get(0).getMimetype());
				ret.setFirmato(servizioOut.get(0).getFlgFirmato().equals("1") ? true : false);
			}			
			
		}
		catch (Exception e){
			throw new Exception(e.getMessage());	
		}
		return ret;
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

	private static Document convertStringToXMLDocument(String xmlString) throws Exception {
		//Parser that produces DOM object trees from XML content
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		//API to obtain DOM Document instance
		DocumentBuilder builder = null;
		Document doc = null;
		try {
			//Create DocumentBuilder with default configuration
			builder = factory.newDocumentBuilder();

			//Parse the content to Document object
			doc = builder.parse(new InputSource(new StringReader(xmlString)));
			
		} 
		catch (Exception e) 
		{
			throw new Exception(e.getMessage());
		}
		return doc;
	}
	
	static public Node goToNodeElement(String name, Node node) {
		if (node.getNodeType() == Node.ELEMENT_NODE
				&& node.getNodeName().equalsIgnoreCase(name)) {
			return node;
		}

		NodeList list = node.getChildNodes();
		for (int i = 0; i < list.getLength(); ++i) {
			Node child = goToNodeElement(name, list.item(i));
			if (child != null)
				return child;
		}

		return null;
	}
	
	static public Node getChildNode(Node padre, String nomeNodo) {
		NodeList list = padre.getChildNodes();
		for (int i = 0; i < list.getLength(); ++i) {
			// Procediamo con i nodi di primo livello sotto il nodo Dati...
			Node child = list.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE
					&& child.getNodeName().equalsIgnoreCase(nomeNodo)) {
				return child;
			}
		}

		return null;
	}
	
	static public String readPCDATA(Node textNode) {
		NodeList list_child = textNode.getChildNodes();
		for (int ck = 0; ck < list_child.getLength(); ck++) {
			Node child_child = (Node) list_child.item(ck);
			if (child_child.getNodeType() == Node.CDATA_SECTION_NODE
					|| child_child.getNodeType() == Node.TEXT_NODE) {
				return (String) child_child.getNodeValue();
			}
		}
		return "";
	}
	
	private File sbustaFileFirmato(String p7m, String fileName) throws IOException, Exception {
		
		InfoFileUtility lInfoFileUtility = new InfoFileUtility();
		InputStream  isSbustato = lInfoFileUtility.sbusta(p7m, fileName);
		
		File out = File.createTempFile("tmpIsConvert", ".temp");
		FileUtils.copyInputStreamToFile(isSbustato, out);
		
		return out;
	}

	private DmpkRegistrazionedocGettimbrodigregBean getSegnaturaStore(AurigaLoginBean loginBean, String idUd, String idDoc, String finalita) throws Exception {
		
		
		if (StringUtil.isBlank(idUd))
			throw new Exception("Errore durante il recupero della segnatura del timbro. Il riferimento idUd non e' valorizzato.");
		
		if (StringUtil.isBlank(idDoc))
			throw new Exception("Errore durante il recupero della segnatura del timbro. Il riferimento idDoc non e' valorizzato.");
		
		
		DmpkRegistrazionedocGettimbrodigregBean input = new DmpkRegistrazionedocGettimbrodigregBean();
		
		
		input.setIdudio(new BigDecimal(idUd));
		input.setIddocin(new BigDecimal(idDoc));
		// appone la segnatura di registrazione
		input.setFinalitain(StringUtils.isNotBlank(finalita) ? finalita : null);

		DmpkRegistrazionedocGettimbrodigreg store = new DmpkRegistrazionedocGettimbrodigreg();
		StoreResultBean<DmpkRegistrazionedocGettimbrodigregBean> result = store.execute(null, loginBean, input);

		if (result.isInError()) {
			aLogger.error("Errore durante il recupero della segnatura del timbro: " + result.getDefaultMessage());
			throw new Exception("Errore durante il recupero della segnatura del timbro: " + result.getDefaultMessage());
		}	

		return result.getResultBean();
	}
	
	private File timbraFile(File filedaTimbrare, String testoInChiaroBarcode, String contenutoBarcode,OpzioniCopertinaTimbroBean opzioniTimbro) throws Exception {
		try {
			TimbraUtil.impostaTestoOpzioniTimbro(opzioniTimbro, testoInChiaroBarcode, contenutoBarcode);
			InputStream fileTimbratoStream = TimbraUtil.timbra(filedaTimbrare, filedaTimbrare.getName(), opzioniTimbro, false);
			File fileTimbrato = File.createTempFile("tmp", ".pdf");
			FileUtils.copyInputStreamToFile(fileTimbratoStream, fileTimbrato);
			return fileTimbrato;
		} catch (Exception e) {
			aLogger.error("Errore durante la timbratura del file: " + filedaTimbrare.getName() + "\n" + e.getMessage(), e);
			throw new Exception("Errore durante la timbratura del file: " + filedaTimbrare.getName() + "\n" + e.getMessage(), e);
		}
	}
	
	private static OpzioniCopertinaTimbroBean getOpzioniTimbro(String xml) throws Exception {
		Node datiNode =null;
		Node a_node = null;
		
		OpzioniCopertinaTimbroBean out = new OpzioniCopertinaTimbroBean();
		
		// Leggo le opzioni del timbro
		try {
			  Document doc = convertStringToXMLDocument(xml);
			  if (doc.getElementsByTagName("OpzioniTimbro").item(0)!=null && doc.getElementsByTagName("OpzioniTimbro").item(0).getFirstChild()!=null){
					
				  datiNode = goToNodeElement("Timbro", doc.getDocumentElement());
				
				  if (datiNode == null)
					  return null;
				
				  a_node = getChildNode(datiNode, "OpzioniTimbro");
					
				  if (a_node != null) {
					  out = readXMLOpzioniTimbro(a_node);
				  }
			  }
			} 
		catch (Exception e) {
				throw new Exception("Errore durante il recupero delle informazioni di input OpzioniTimbro: " + e.getMessage());
		}				
		return out;
	}
	
	private WSExtractOneInputStreamsTypesBean getVersioneTimbrata(AurigaLoginBean loginBean,OpzioniCopertinaTimbroBean opzioniCopertinaTimbroBean, WSExtractOneBean outServizio, String finalita, File fileEstratto) throws Exception {
		String errMsg = null;
		 
		WSExtractOneInputStreamsTypesBean out = new WSExtractOneInputStreamsTypesBean();
		
		File fileDaTimbrare = File.createTempFile("tmp", ".pdf");
		
		try {
				
				FileUtils.copyFile(fileEstratto, fileDaTimbrare);
				
				// Timbro il file 
				String idDoc = outServizio.getIdDoc();
				String idUd  = outServizio.getIdUd();
				File fileTimbrato = timbraFileRegistrazione(loginBean, idUd, idDoc, fileDaTimbrare, opzioniCopertinaTimbroBean, finalita);
				InputStream inputStream = new FileInputStream(fileTimbrato);
				out.setInputStream(inputStream);
				out.setInputStreamsTypes("application/pdf");				
		} 
		catch (Exception e) {
			throw new Exception("Errore durante il processo di timbratura: " + e.getMessage(), e);
		}
		return out;
	}
	
	private String getIdUdFromIdDoc(AurigaLoginBean loginBean, String idDocIn) throws Exception {
		
		String idUd = "";
		
		if (StringUtils.isBlank(idDocIn)){
			throw new Exception("Il riferimento IdDoc e' nullo. Non e' possibile individuare l'idud."); 	
		}
		
		// Estraggo i riferimenti della registrazione dal tag <EstremiXIdentificazioneUD>		
		aLogger.debug("Eseguo il WS DmpkUtilityGetidudofdoc.");

		try {    		
			// Inizializzo l'INPUT    		
			DmpkUtilityGetidudofdocBean input = new DmpkUtilityGetidudofdocBean();
			input.setIddocin(StringUtils.isNotBlank(idDocIn) ? new BigDecimal(idDocIn) : null);

			// Eseguo il servizio
			Getidudofdoc service = new Getidudofdoc();
			SchemaBean schemaBean = new SchemaBean();
			schemaBean.setSchema(loginBean.getSchema());
			
			StoreResultBean<DmpkUtilityGetidudofdocBean> output = service.execute(schemaBean, input);

			if (output.isInError()){
				throw new Exception(output.getDefaultMessage());	
			}	

			// leggo l'idud
			idUd = (output.getResultBean().getParametro_1() !=null ? String.valueOf(output.getResultBean().getParametro_1()) : null);
			if (idUd == null)
				idUd ="";
			
			if (idUd.equalsIgnoreCase(""))
				throw new Exception("La store procedure DmpkUtilityGetidudofdoc ha ritornato idud nullo");
			
		}
		catch (Exception e){
			throw new Exception(e.getMessage()); 			
		}
		return idUd;
	}
	
	private File timbraFileRegistrazione(AurigaLoginBean loginBean, String idUd, String idDoc, File fileDaTimbrare, OpzioniCopertinaTimbroBean opzioniCopertinaTimbroBean, String finalita) throws Exception {		
		try {
			DmpkRegistrazionedocGettimbrodigregBean result = getSegnaturaStore(loginBean, idUd, idDoc, finalita);
			if (result != null) {
				String contenutoBarcode = result.getContenutobarcodeout();
				String testoInChiaroBarcode = result.getTestoinchiaroout();
				
				File fileTimbrato = timbraFile(fileDaTimbrare, testoInChiaroBarcode, contenutoBarcode, opzioniCopertinaTimbroBean);
				return fileTimbrato;
			} else {
				throw new Exception("Errore durante l'apposizione del timbro al file: ");
			}
			
		} catch (Exception e) {
			throw new Exception("Errore durante l'apposizione del timbro al file: " + e );
		}		
	}
	
	private static OpzioniCopertinaTimbroBean readXMLOpzioniTimbro(Node node) throws Exception {
    	Node a_node = null;
    	Node a_node_pagine = null;
    	String temp;
    	
    	OpzioniCopertinaTimbroBean bean = new OpzioniCopertinaTimbroBean();
    	
    	// Leggo il tag <PosizioneTimbro>
    	a_node = getChildNode(node, "PosizioneTimbro");
        if (a_node != null) {
            temp = readPCDATA(a_node);
            
            if (temp!=null && !temp.equalsIgnoreCase("")){
            	
            	PosizioneTimbroNellaPagina posizioneTimbroNellaPagina = null; 
            	
            	switch (temp.toUpperCase()) {
		            	// "ALTO_DX"
		            	case "ALTO_DX":
		            		posizioneTimbroNellaPagina = PosizioneTimbroNellaPagina.ALTO_DX;
		        			break;
		            	
		        		// ALTO_SN
			            case "ALTO_SN":
			        		posizioneTimbroNellaPagina = PosizioneTimbroNellaPagina.ALTO_SN;
			    			break;
			    			
			    		// BASSO_SN
			            case "BASSO_SN":
			        		posizioneTimbroNellaPagina = PosizioneTimbroNellaPagina.BASSO_SN;
			    			break;
			        	
			    		// BASSO_DX
			            case "BASSO_DX":
			        		posizioneTimbroNellaPagina = PosizioneTimbroNellaPagina.BASSO_DX;
			    			break;
			    			
			            default:
			            	posizioneTimbroNellaPagina = PosizioneTimbroNellaPagina.ALTO_SN;
			    			break;
            	}            	
            	bean.setPosizioneTimbro(posizioneTimbroNellaPagina);
            }
            
            // Leggo il tag <RotazioneTimbro>
        	a_node = getChildNode(node, "RotazioneTimbro");
            if (a_node != null) {
                temp = readPCDATA(a_node);
                
                if (temp!=null && !temp.equalsIgnoreCase("")){
                	
                	TipoRotazione rotazioneTimbro = null;
                	
                	switch (temp.toUpperCase()) {
                		// "VERTICALE"
		            	case "VERTICALE":
		            		rotazioneTimbro = TipoRotazione.VERTICALE;
		        			break;

	                	// "ORIZZONTALE"
		            	case "ORIZZONTALE":
		            		rotazioneTimbro = TipoRotazione.ORIZZONTALE;
		        			break;
		        			
		            	default:
		            		rotazioneTimbro = TipoRotazione.VERTICALE;
		        			break;
                	}                	
                	bean.setRotazioneTimbro(rotazioneTimbro);
                }
            }
            
            // Leggo il tag <PaginaTimbro>
            a_node = getChildNode(node, "PaginaTimbro");
            if (a_node != null) {
                temp = readPCDATA(a_node);
                
                if (temp!=null && !temp.equalsIgnoreCase("")){
                
                	PaginaTimbro paginaTimbro = new PaginaTimbro();
                	
                	TipoPagina tipoPagina = null;
                	Pagine pagine = new Pagine();
                	
                	switch (temp.toUpperCase()) {
	                	// "PRIMA"
		            	case "PRIMA":
		            		tipoPagina = TipoPagina.PRIMA;
		        			break;

		        		// "ULTIMA"
		            	case "ULTIMA":
		            		tipoPagina = TipoPagina.ULTIMA;
		        			break;		        			
		        			
		        		// "TUTTE"
		            	case "TUTTE":
		            		tipoPagina = TipoPagina.TUTTE;
		        			break;
		        			
		            	default:
		            		tipoPagina = TipoPagina.TUTTE;
		        			break;		
                	}
                	paginaTimbro.setTipoPagina(tipoPagina);
                	bean.setPaginaTimbro(paginaTimbro);
                }
            }
            
            // Leggo il tag <IntervalloPagine>
            a_node = getChildNode(node, "IntervalloPagine");
            if (a_node != null) {
            	 temp = readPCDATA(a_node);
                 
                 if (temp!=null && !temp.equalsIgnoreCase("")){
                	 
                	PaginaTimbro paginaTimbro = new PaginaTimbro();
                 	
                 	TipoPagina tipoPagina = null;
                 	Pagine pagine = new Pagine();
                 	
                	// DA
                 	a_node_pagine = getChildNode(a_node, "Da");
                 	if (a_node_pagine != null) {
   					  	temp = readPCDATA(a_node_pagine);
   					  	if (temp!=null && !temp.equalsIgnoreCase("")){
   					  		pagine.setPaginaDa(Integer.valueOf(temp));
   					  		paginaTimbro.setPagine(pagine);
   					  	}
   				  	}		                            	
                 	// A
                 	a_node_pagine = getChildNode(a_node, "A");
                 	if (a_node_pagine != null) {
   					  	temp = readPCDATA(a_node_pagine);
   					  	if (temp!=null && !temp.equalsIgnoreCase("")){
   					  		pagine.setPaginaA(Integer.valueOf(temp));
   					  		paginaTimbro.setPagine(pagine);
   					  	}
   				  	}
                 	
                 	paginaTimbro.setTipoPagina(tipoPagina);
                	bean.setPaginaTimbro(paginaTimbro);
                 }
            }
            
        }
        
        bean.setRigheMultiple(true);
        bean.setPosizioneIntestazione(PosizioneRispettoAlTimbro.INLINEA);
        
        return bean;
   }
	
	private String getNomeFileSbustato(String nomeFile) { 
		String ext = ".p7m.m7m.std";
		String nomeFileSbustato = nomeFile;
		while (nomeFileSbustato.length() > 3 && ext.contains(nomeFileSbustato.substring(nomeFileSbustato.length() - 4))){
				nomeFileSbustato = nomeFileSbustato.substring(0, nomeFileSbustato.length() - 4);
				}
		return nomeFileSbustato;
	}
	
	public static void main(String[] args) throws Exception {
		
		
		WSExtractOne extractOne = new WSExtractOne();
		File estratto = new File("C:\\dgdoc\\data02\\repository\\archivio\\2022\\3\\28\\1\\20220328144615360122690188418193089");
//		File pdf = extractOne.creaPDFFattura(uriFileXmlIn, "1.2.2");
			InputStream is = null;
//			if (false) {
//				InfoFileUtility infoFileUtility = new InfoFileUtility();
//				is = infoFileUtility.sbusta(estratto, "ITFRNFBA74R06H620U_HV90T.xml.p7m");
//			} else {
				is = new FileInputStream(estratto);
//			}
//			String xmlFatt = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))
//					.lines().collect(Collectors.joining("\n"));

//			is.reset();
			String versione = "1.2.2";//getTagParametriVersione(xmlFatt);
			FattureUtil fattureUtil = new FattureUtil();
			File pdf = fattureUtil.creaPDFFattura(is, versione);

		System.out.println("fine " + pdf.getPath());
	}
}