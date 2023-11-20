/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.activation.DataHandler;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.ws.soap.MTOM;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import it.eng.auriga.compiler.ModelliUtil;
import it.eng.auriga.database.store.dmpk_modelli_doc.bean.DmpkModelliDocExtractvermodelloBean;
import it.eng.auriga.database.store.dmpk_modelli_doc.store.Extractvermodello;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.beans.SpecializzazioneBean;
import it.eng.auriga.module.business.entity.WSTrace;
import it.eng.auriga.repository2.jaxws.jaxbBean.fillModelWithValues.FillModelWithValues;
import it.eng.auriga.repository2.jaxws.jaxbBean.fillModelWithValues.TipoModelloType;
import it.eng.auriga.repository2.jaxws.webservices.common.JAXWSAbstractAurigaService;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.jaxb.variabili.SezioneCache.Variabile;

/**
 * @author Federio Cacco
 */

@WebService(targetNamespace = "http://fillmodelwithvalues.webservices.repository2.auriga.eng.it", endpointInterface = "it.eng.auriga.repository2.jaxws.webservices.fillmodelwithvalues.WSIFillModelWithValues", name = "WSFillModelWithValues")
@MTOM(enabled = true, threshold = 0)
@HandlerChain(file = "../common/handler.xml")
public class WSFillModelWithValues extends JAXWSAbstractAurigaService implements WSIFillModelWithValues {

	static Logger aLogger = Logger.getLogger(WSFillModelWithValues.class.getName());

	/**
	 * Crea una nuova istanza di <code>WSFillModelWithValues</code> .
	 *
	 */
	public WSFillModelWithValues() {
		super();
	}

	/**
	 * <code>serviceImplementation</code> biz logik del webservice.
	 * 
	 * @param user
	 *            a <code>String</code>
	 * @param token
	 *            a <code>String</code>
	 * @param codAppl
	 *            a <code>String</code>
	 * @param conn
	 *            a <code>Connection</code>
	 * @param xmlDomDoc
	 *            a <code>Document</code>
	 * @param xml
	 *            a <code>String</code>
	 * @param istanzaAppl
	 *            a <code>String</code>
	 * 
	 * @return a <code>String</code>
	 * 
	 * @exception Exception
	 */
	@WebMethod(exclude = true)
	public final String serviceImplementation(final String user, final String token, final String codiceApplicazione, final String istanzaAppl,
			final Connection conn, final Document xmlDomDoc, final String xml, final String schemaDb, final String idDominio, final String desDominio,
			final String tipoDominio,
		      final WSTrace wsTraceBean) throws Exception {

		String risposta = null;
		String errMsg = null;
		try {
			aLogger.info("Inizio WSFillModelWithValues");

			// creo bean connessione
			AurigaLoginBean loginBean = new AurigaLoginBean();
			loginBean.setToken(token);
			loginBean.setCodApplicazione(codiceApplicazione);
			loginBean.setIdApplicazione(istanzaAppl);
			loginBean.setSchema(schemaDb);

			SpecializzazioneBean lspecializzazioneBean = new SpecializzazioneBean();
			lspecializzazioneBean.setCodIdConnectionToken(token);
			if (idDominio != null && !idDominio.equalsIgnoreCase(""))
				lspecializzazioneBean.setIdDominio(new BigDecimal(idDominio));

			if (tipoDominio != null && !tipoDominio.equalsIgnoreCase(""))
				lspecializzazioneBean.setTipoDominio(new Integer(tipoDominio));

			loginBean.setSpecializzazioneBean(lspecializzazioneBean);

			/*************************************************************
			 * Estraggo modello e inietto valori
			 ************************************************************/

			DataHandler[] wsAttach;
			File fileCompilato = null;

			try {

				// Estraggo i dati del modello
				FillModelWithValues datiModello = getDatiModello(xml);

				// Estraggo il modello
				String uriModello = estraiModello(loginBean, datiModello);

				// Leggo gli attach e le info
				wsAttach = getMessageDataHandlers();

				// Converto l'attach in sezione cache
				String templatesValue = convertiInSezioneCache(datiModello, wsAttach);

				// Inietto i valori
				fileCompilato = iniettaValoriSuModello(loginBean, xml, uriModello, templatesValue);

			} catch (Exception e) {
				errMsg = e.getMessage() != null ? "Errore = " + e.getMessage() : "Errore nella generazione del documento.";  
				aLogger.debug(errMsg, e);
			}

			/**************************************************************************
			 * Creo la response con il docuemnto allegato
			 **************************************************************************/
			if (fileCompilato != null){
				try {
					// Allego il modello compilato nella response
					List<InputStream> lListInputStreams = new ArrayList<InputStream>();
					InputStream documentis = new FileInputStream(fileCompilato);
					lListInputStreams.add(documentis);
					attachListInputStream(lListInputStreams);
				} catch (Exception e) {
					errMsg = e.getMessage() != null ? "Errore = " + e.getMessage() : "Errore imprevisto.";  
					aLogger.debug(errMsg, e);
				}
			}else{
				errMsg = (errMsg != null && !"".equalsIgnoreCase(errMsg)) ? errMsg : "Errore nella generazione del documento";
			}

			/*************************************************************
			 * Restituisco XML di risposta del WS
			 ************************************************************/
			if (errMsg == null) {
				risposta = generaXMLRisposta(JAXWSAbstractAurigaService.SUCCESSO, JAXWSAbstractAurigaService.SUCCESSO, "Tutto OK", "", "");
			} else {
				risposta = generaXMLRisposta(JAXWSAbstractAurigaService.FALLIMENTO, JAXWSAbstractAurigaService.ERR_ERRORE_APPLICATIVO, errMsg, "", "");
			}
			aLogger.info("Fine WSFillModelWithValues");
			return risposta;
			
		} catch (Exception excptn) {
			aLogger.error("WSFillModelWithValues: " + excptn.getMessage(), excptn);
			return generaXMLRisposta(JAXWSAbstractAurigaService.FALLIMENTO, JAXWSAbstractAurigaService.ERR_ERRORE_APPLICATIVO,
					JAXWSAbstractAurigaService.ERROR_ERRORE_APPLICATIVO, "", "");
		} finally {
			aLogger.info("Fine WSFillModelWithValues serviceImplementation");
		}
	}

	private FillModelWithValues getDatiModello(String xmlIn) throws Exception {
		
		try {
			StringReader xmlReader = new StringReader(xmlIn);
			JAXBContext context = JAXBContext.newInstance(FillModelWithValues.class);
			Unmarshaller unMarshaller = context.createUnmarshaller();
			FillModelWithValues datiModello = (FillModelWithValues) unMarshaller.unmarshal(xmlReader);
			return datiModello;
		} catch (Exception e) {
			throw new Exception("Errore nell'estrazione dei dati del modello", e);
		}
	}

	private String estraiModello(AurigaLoginBean loginBean, FillModelWithValues datiModello) throws Exception {

		try {
			// Recupero l'uri del modello della copertina
			Extractvermodello retrieveVersion = new Extractvermodello();
			
			DmpkModelliDocExtractvermodelloBean modelloBean = new DmpkModelliDocExtractvermodelloBean();
			modelloBean.setCodidconnectiontokenin(loginBean.getToken());
			// modelloBean.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			modelloBean.setNomemodelloin(datiModello.getNomeModello());
			StoreResultBean<DmpkModelliDocExtractvermodelloBean> resultModello = retrieveVersion.execute(loginBean, modelloBean);

			String uriModelloCopertinaDaCompilare = resultModello.getResultBean().getUriverout();
			if (!resultModello.isInError()){
				return uriModelloCopertinaDaCompilare;
			}else {
				throw new Exception((resultModello.getDefaultMessage() != null && !"".equalsIgnoreCase(resultModello.getDefaultMessage())) ? resultModello.getDefaultMessage() : "Errore nel recupero del modello");
			}
		}catch (Exception e) {
			throw new Exception("Errore nel recupero del modello " + datiModello.getNomeModello(), e);
		}
	}
	
	private String convertiInSezioneCache(FillModelWithValues datiModello, DataHandler[] attachments) throws Exception {
		TipoModelloType tipoModello = datiModello.getTipoModello();
		if (tipoModello == TipoModelloType.AGID){
			return convertiAGIDInXMLSezioneCache(datiModello, attachments);
		}else if (tipoModello == TipoModelloType.SEZIONE_CACHE){
			return convertiSezioneCacheInXMLSezioneCache(datiModello, attachments);
		}else{
			throw new Exception("Template dei valori da iniettare non valido");
		}
	}
	
	private String convertiSezioneCacheInXMLSezioneCache(FillModelWithValues datiModello, DataHandler[] attachments) throws Exception {
		if (attachments.length > 0) {
			try{
				// Ricavo l'allegato contenente i valori da iniettare
				DataHandler attach = attachments[0];
				InputStream attachis = attach.getInputStream();
				StringWriter writer = new StringWriter();
				IOUtils.copy(attachis, writer);
				return writer.toString();
				
			} catch (Exception e) {
				throw new Exception("Errore nella conversione dei valori da iniettare", e);
			}
			
		}
		throw new Exception("Non è stato trovato nessun file allegato contenete i valori da iniettare nel modello");
	}

	private String convertiAGIDInXMLSezioneCache(FillModelWithValues datiModello, DataHandler[] attachments) throws Exception {
		if (attachments.length > 0) {
			try{
				SezioneCache lSezioneCache = new SezioneCache();
				
				// Ricavo l'allegato contenente i valori da iniettare
				DataHandler attach = attachments[0];
				InputStream attachis = attach.getInputStream();
	
				// Creo il parsing per convertire i valori da iniettare
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(attachis);
				doc.getDocumentElement().normalize();
				
				// Estraggo tutti i nodi contenenti un valore da iniettare
				final XPathExpression xpath = XPathFactory.newInstance().newXPath().compile("//*[count(./*) = 0]");
				final NodeList nList = (NodeList) xpath.evaluate(doc, XPathConstants.NODESET);
			
				// Ciclo sui nodi estratti
				for (int i = 0; i < nList.getLength(); i++) {
					Node nNode = nList.item(i);
					// Verifico che il nodo abia un valore unico
					if ((nNode.getChildNodes().getLength() == 1) && (nNode.getChildNodes().item(0).getNodeValue() != null) && (!"".equalsIgnoreCase(nNode.getChildNodes().item(0).getNodeValue()))){
						// Scorro tutti i nodi parent per ricavare il nome del placeholder
						String placeholder = nNode.getNodeName();
						Node parent  = nNode.getParentNode();
						while (parent != null){
							if (parent.getNodeType() != Node.DOCUMENT_NODE){
								placeholder = parent.getNodeName() + "." + placeholder;
							}
							parent = parent.getParentNode();
						}
						// Creo la variabile della SezioneCache
						Variabile varCodApplicazioni = new Variabile();
						varCodApplicazioni.setNome(placeholder);
						varCodApplicazioni.setValoreSemplice(nNode.getChildNodes().item(0).getNodeValue());
						lSezioneCache.getVariabile().add(varCodApplicazioni);
					}
					
				}
				// Restituisco l'xml della SezioneCache
				StringWriter lStringWriter = new StringWriter();
				Marshaller lMarshaller = SingletonJAXBContext.getInstance().createMarshaller();
				lMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				lMarshaller.marshal(lSezioneCache, lStringWriter);
				return lStringWriter.toString();
				
			} catch (Exception e) {
				throw new Exception("Errore nella conversione dei valori da iniettare", e);
			}
			
		}
		throw new Exception("Non è stato trovato nessun file allegato contenete i valori da iniettare nel modello");
	}

	private File iniettaValoriSuModello(AurigaLoginBean loginBean, String xmlIn, String uriModello, String templateValues) throws Exception {
		try{
			return ModelliUtil.fillTemplateAndConvertToPdf(null, uriModello, "DOCX_FORM", templateValues, Locale.ITALIAN, loginBean);
		}catch (Exception e){
			throw new Exception(e.getMessage() != null && !"".equalsIgnoreCase(e.getMessage()) ? e.getMessage() : "Errore nella generazione del documento");
		}
	}

}