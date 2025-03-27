/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.repository2.jaxws.webservices.extractzipfilesud;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import it.eng.auriga.database.store.dmpk_ws.bean.DmpkWsExtractfilesudBean;
import it.eng.auriga.database.store.dmpk_ws.store.Extractfilesud;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.beans.SpecializzazioneBean;
import it.eng.auriga.module.business.entity.WSTrace;
import it.eng.auriga.repository2.jaxws.webservices.common.JAXWSAbstractAurigaService;
import it.eng.auriga.repository2.util.DBHelperSavePoint;
import it.eng.document.function.StoreException;
import it.eng.document.storage.DocumentStorage;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.jaxb.variabili.Lista.Riga.Colonna;

/**
 * @author Ottavio passalacqua
 */

@WebService(targetNamespace = "http://extractzipfilesud.webservices.repository2.auriga.eng.it", endpointInterface = "it.eng.auriga.repository2.jaxws.webservices.extractzipfilesud.WSIExtractZipFileUD", name = "WSExtractZipFileUD")
@MTOM(enabled = true, threshold = 0)
@HandlerChain(file = "../common/handler.xml")
public class WSExtractZipFileUD extends JAXWSAbstractAurigaService implements WSIExtractZipFileUD {

	private final String K_SAVEPOINTNAME = "INIZIOWSEXTRACTZIPFILESUD";

	static Logger aLogger = Logger.getLogger(WSExtractZipFileUD.class.getName());

	public WSExtractZipFileUD() {
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
	 * @return a <code>String</code>
	 * @exception Exception
	 */
	@WebMethod(exclude = true)
	public final String serviceImplementation(final String user, final String token, final String codiceApplicazione,
			final String istanzaAppl, final Connection conn, final Document xmlDomDoc, final String xml,
			final String schemaDb, final String idDominio, final String desDominio, final String tipoDominio,
			final String parametriconfigout,
			final WSTrace wsTraceBean) throws Exception {

		String risposta = null;
		String outRispostaWS = null;
		WSExtractZipFileUDBean outServizio = new WSExtractZipFileUDBean();
		String errMsg = null;
		String xmlIn = null;
		Integer errCode = JAXWSAbstractAurigaService.ERR_ERRORE_APPLICATIVO;
		
		try {

			aLogger.info("Inizio WSExtractZipFileUD");

			// setto il savepoint
			DBHelperSavePoint.SetSavepoint(conn, K_SAVEPOINTNAME);

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
			 * Chiamo il WS e il servizio di AurigaDocument
			 ************************************************************/
			WSExtractZipFileUDBean outWS = new WSExtractZipFileUDBean();
			try {
				// Chiamo il WS
				outWS = callWS(loginBean, xml);

				outServizio = recuperaAllegati(loginBean, outWS);
				
			} catch (Exception e) {
				if (e instanceof StoreException) {
		    		if(((StoreException) e).getError()!=null){
		    			errCode = ((StoreException) e).getError().getErrorCode();
		    		}
		    	}
				if (e.getMessage() != null)
					errMsg = "Errore = " + e.getMessage();
				else
					errMsg = "Errore imprevisto.";
			}

			if (errMsg == null) {
				xmlIn = outServizio.getXml();
			} else {
				xmlIn = errMsg;
			}

			/**************************************************************************
			 * Creo XML di risposta del servzio e lo metto in attach alla response
			 **************************************************************************/
			ByteArrayInputStream inputStreamXml = null;
			InputStream inputStreamFile = null;
			try {
				// Creo XML di risposta
				outRispostaWS = generaXMLRispostaWS(xmlIn);

				// Creo la lista di attach
				List<InputStream> lListInputStreams = new ArrayList<InputStream>();

				// Converto l'XML
				inputStreamXml = new ByteArrayInputStream(outRispostaWS.getBytes());


				// Zippo e Aggiungo il FILE in ATTACH
				if (outServizio != null && outServizio.getExtractedFileMap() != null
						&& outServizio.getExtractedFileMap().size() > 0) {
					Map<String,File> getExtractedFileMap = outServizio.getExtractedFileMap();
					ByteArrayOutputStream out = creaZip(getExtractedFileMap, "response");
					
					// Salvo gli ATTACH alla response
					lListInputStreams.add(new ByteArrayInputStream( out.toByteArray()));
					
					// Aggiungo l'XML
					lListInputStreams.add(inputStreamXml);
					attachListInputStream(lListInputStreams);
				}
			} catch (Exception e) {
				if (e.getMessage() != null)
					errMsg = "Errore = " + e.getMessage();
				else
					errMsg = "Errore imprevisto.";
			} finally {
				// Chiudo gli stream
				if (inputStreamXml != null) {
					try {
						inputStreamXml.close();
					} catch (Exception e) {
						if (e.getMessage() != null)
							errMsg = "Errore = " + e.getMessage();
						else
							errMsg = "Errore imprevisto.";
					}
				}
				// Chiudo gli stream
				if (inputStreamFile != null) {
					try {
						inputStreamFile.close();
					} catch (Exception e) {
						if (e.getMessage() != null)
							errMsg = "Errore = " + e.getMessage();
						else
							errMsg = "Errore imprevisto.";
					}
				}
			}

			/*************************************************************
			 * Restituisco XML di risposta del WS
			 ************************************************************/
			if (errMsg == null) {
				risposta = generaXMLRisposta(JAXWSAbstractAurigaService.SUCCESSO, JAXWSAbstractAurigaService.SUCCESSO,
						"Tutto OK", "", "");
			} else {
				risposta = generaXMLRisposta(JAXWSAbstractAurigaService.FALLIMENTO,
						JAXWSAbstractAurigaService.ERR_ERRORE_APPLICATIVO, errMsg, "", "");
			}

			aLogger.info("Fine WSExtractZipFileUD");

			return risposta;
		}

		catch (Exception excptn) {
			aLogger.error("WSExtractZipFileUD: " + excptn.getMessage(), excptn);
			return generaXMLRisposta(JAXWSAbstractAurigaService.FALLIMENTO,
					JAXWSAbstractAurigaService.ERR_ERRORE_APPLICATIVO,
					JAXWSAbstractAurigaService.ERROR_ERRORE_APPLICATIVO, "", "");
			// throw excptn;
		} finally {
			try {
				DBHelperSavePoint.RollbackToSavepoint(conn, K_SAVEPOINTNAME);
			} catch (Exception ee) {
			}
			aLogger.info("Fine WSExtractZipFileUD serviceImplementation");
		}

	}

	private WSExtractZipFileUDBean recuperaAllegati(AurigaLoginBean loginBean, WSExtractZipFileUDBean bean)
			throws Exception {
		aLogger.debug("Recupero i files da storage");

		List<File> extractedFileList = new ArrayList<File>();
		Map<String, File> extractedFileMap = new HashMap<String, File>();
		// Ciclo sulla lista degli iddoc
		List<ExtractZipFileUDBean> listExtractBean = new ArrayList<ExtractZipFileUDBean>();
		listExtractBean = bean.getDocumentlist();

		if (listExtractBean != null && listExtractBean.size() > 0) {

			for (ExtractZipFileUDBean lExtractBean : listExtractBean) {

				String nomeFile = lExtractBean.getNomeFile();
				String uri = lExtractBean.getUri();

				File fileOut = null;

				// eseguo il servizio
				try {
					aLogger.debug("DocumentStorage.extract " + uri);
					fileOut = DocumentStorage.extract(uri, loginBean.getSpecializzazioneBean().getIdDominio());
					if (fileOut == null || !fileOut.exists()) {
						throw new Exception("Errore durante l'estrazione");
					}
					extractedFileMap.put(nomeFile,fileOut);
				} catch (Exception e) {
					throw new Exception(e.getMessage());
				}
		
			}

		}

		bean.setExtractedFileList(extractedFileList);
		bean.setExtractedFileMap(extractedFileMap);

		return bean;
	}

	private WSExtractZipFileUDBean callWS(AurigaLoginBean loginBean, String xmlIn) throws Exception {
		aLogger.debug("Eseguo il WS DMPK_WS->ExtractZipFileUD");

		String verDoctoExtractTab = null;
		String xml = null;
		
		// Inizializzo l'INPUT
		DmpkWsExtractfilesudBean input = new DmpkWsExtractfilesudBean();
		input.setCodidconnectiontokenin(loginBean.getToken());
		input.setXmlin(xmlIn);

		// Eseguo il servizio
		Extractfilesud service = new Extractfilesud();
		StoreResultBean<DmpkWsExtractfilesudBean> output = service.execute(loginBean, input);

    	if (output.isInError()){
    		aLogger.debug(output.getDefaultMessage());
    		aLogger.debug(output.getErrorContext());
    		aLogger.debug(output.getErrorCode());
    		throw new StoreException(output);
    	}	

		// restituisco l'XML
		if (output.getResultBean().getXmlout() != null) {
			xml = output.getResultBean().getXmlout();
		}
		if (xml == null || xml.equalsIgnoreCase(""))
			throw new Exception("La store procedure ha ritornato XmlOut nullo");

		// restituisco la lista con i nro versioni
		if (output.getResultBean().getVerdoctoextracttabout() != null) {
			verDoctoExtractTab = output.getResultBean().getVerdoctoextracttabout().toString();
		}
		if (verDoctoExtractTab == null) {
			throw new Exception("La store procedure ExtractFilesUD ha ritornato una lista di documenti nulla");
		}

		// popolo il bean di out
		WSExtractZipFileUDBean result = new WSExtractZipFileUDBean();
		result.setXml(xml);

		// leggo la lista dei documenti
		List<ExtractZipFileUDBean> listExtractBean = new ArrayList<ExtractZipFileUDBean>();
		listExtractBean = getListDoc(verDoctoExtractTab);

		result.setDocumentlist(listExtractBean);

		return result;	
	}

	/**
	 * Genera il file XML contenente l'id del folder aggiunto Questo file viene
	 * passato come allegato in caso di successo.
	 *
	 * @param String
	 *            idFolder
	 * @return String stringa XML secondo il formato per il ritorno dell'idFolder
	 */
	private String generaXMLRispostaWS(String xmlIn) throws Exception {

		StringBuffer xml = new StringBuffer();
		String xmlInEsc = null;

		try {
			// ...se il token non e' null
			if (xmlIn != null) {
				// effettuo l'escape di tutti i caratteri
				xmlInEsc = StringEscapeUtils.escapeXml(xmlIn);
			}

			// xmlInEsc = xmlIn;
			xml.append(xmlInEsc);
			aLogger.debug(xml.toString());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return xml.toString();
	}

	private List<ExtractZipFileUDBean> getListDoc(String xmlIn) throws Exception {
		List<ExtractZipFileUDBean> listExtractBean = new ArrayList<ExtractZipFileUDBean>();
		if ((xmlIn != null) && (!xmlIn.equals(""))) {
			aLogger.debug("xmlIn " + xmlIn);
			// istanzio lo stringReader e vi associo la LISTA_STD
			java.io.StringReader sr = new java.io.StringReader(xmlIn);
			Lista lsSr = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			if (lsSr != null) {
				for (int j = 0; j < lsSr.getRiga().size(); j++) {
					// prendo la riga i-esima
					Riga r = lsSr.getRiga().get(j);
					String idDoc = getContentColonnaNro(r, 1);
					String nroProgrVer = getContentColonnaNro(r, 2);
					String nomeFile = getContentColonnaNro(r, 3);
					String uri = getContentColonnaNro(r, 4);
					aLogger.debug("getListDoc " + j + ": " + uri);
					ExtractZipFileUDBean lExtractBean = new ExtractZipFileUDBean();
					lExtractBean.setIdDoc(idDoc);
					lExtractBean.setNroProgrVer(nroProgrVer);
					lExtractBean.setNomeFile(nomeFile);
					lExtractBean.setUri(uri);
					listExtractBean.add(lExtractBean);
				}
			}
		}
		return listExtractBean;
	}

	public static String getContentColonnaNro(Riga r, int nro) {
		if (r == null)
			return null;

		for (int i = 0; i < r.getColonna().size(); i++) {
			Colonna c = r.getColonna().get(i);
			if (c != null && c.getNro().intValue() == nro) {
				return c.getContent();
			}
		}
		return null;
	}

	private static ByteArrayOutputStream creaZip(Map<String,File> extractedFileMap, String nomeZip) throws FileNotFoundException, IOException {

//		File zipFile = File.createTempFile(nomeZip, ".zip");
		ByteArrayOutputStream fos = new ByteArrayOutputStream();
		ZipOutputStream zos = new ZipOutputStream(fos);
		Set<String> filenames = extractedFileMap.keySet();
		for (Iterator<String> iterator = filenames.iterator(); iterator.hasNext();) {
			String filename = iterator.next();
			File fileAtt = (File) extractedFileMap.get(filename);
			ZipEntry zipEntryAtt = new ZipEntry(filename);
			zos.putNextEntry(zipEntryAtt);
			byte[] bytesAtt = new byte['?'];
			int lengthAtt;
			FileInputStream fisAtt = new FileInputStream(fileAtt);
			while ((lengthAtt = fisAtt.read(bytesAtt)) >= 0) {
				// int length;
				zos.write(bytesAtt, 0, lengthAtt);
			}
			zos.closeEntry();
			fisAtt.close();
		}

		zos.close();

		return fos;
	}
}
