/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.util;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.log4j.Logger;
import org.apache.xalan.processor.TransformerFactoryImpl;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.zefer.pd4ml.PD4Constants;
import org.zefer.pd4ml.PD4ML;
import org.zefer.pd4ml.PD4PageMark;

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreUpddocudBean;
import it.eng.auriga.database.store.dmpk_core.store.impl.UpddocudImpl;
import it.eng.auriga.database.store.dmpk_ws.bean.DmpkWsGetinforenderingpdfdocBean;
import it.eng.auriga.database.store.dmpk_ws.store.Getinforenderingpdfdoc;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.repository2.jaxws.webservices.addunitadoc.visure.AddUdUtils;
import it.eng.document.function.GestioneFatture;
import it.eng.document.function.RecuperoDocumenti;
import it.eng.document.function.StoreException;
import it.eng.document.function.bean.CreaModFatturaInBean;
import it.eng.document.function.bean.DocumentoXmlOutBean;
import it.eng.document.function.bean.FatturaCausale;
import it.eng.document.function.bean.RecuperaDocumentoInBean;
import it.eng.document.function.bean.RecuperaDocumentoOutBean;
import it.eng.document.function.bean.TipoNumerazioneBean;
import it.eng.document.function.bean.fattura.pa.generated.DatiDocumentiCorrelatiType;
import it.eng.document.function.bean.fattura.pa.generated.DatiGeneraliDocumentoType;
import it.eng.document.function.bean.fattura.pa.generated.DatiGeneraliType;
import it.eng.document.function.bean.fattura.pa.generated.DatiPagamentoType;
import it.eng.document.function.bean.fattura.pa.generated.DatiRiepilogoType;
import it.eng.document.function.bean.fattura.pa.generated.DettaglioPagamentoType;
import it.eng.document.function.bean.fattura.pa.generated.FatturaElettronicaBodyType;
import it.eng.document.function.bean.fattura.pa.generated.FatturaElettronicaHeaderType;
import it.eng.document.function.bean.fattura.pa.generated.FatturaElettronicaType;
import it.eng.document.function.bean.fattura.pa.generated.FormatoTrasmissioneType;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.storeutil.AnalyzeResult;
import it.eng.util.bean.ModelliDocXmlBean;
import it.eng.xml.XmlUtilitySerializer;

public class FattureUtil {

	static Logger aLogger = Logger.getLogger(FattureUtil.class.getName());

	public File generaPdfFattura(AurigaLoginBean loginBean, File fatturaFile, boolean isFirmato, String fileName,
			String idDoc, String idUd, String mimeType) throws Exception, FileNotFoundException, IOException {
		// chimata store
		DmpkWsGetinforenderingpdfdocBean dmpkWsGetinforenderingpdfdocBeanIn = new DmpkWsGetinforenderingpdfdocBean();
		dmpkWsGetinforenderingpdfdocBeanIn.setIddocin(new BigDecimal(idDoc));
		DmpkWsGetinforenderingpdfdocBean pdfdocBeanOut = callDmpkRenderingPdf(loginBean, idDoc);

		if (pdfdocBeanOut.getFlgfatturaelettronicaout() != null
				&& pdfdocBeanOut.getFlgfatturaelettronicaout().intValue() == 1) {// fattura
			aLogger.info("fattura: " + fatturaFile.getPath());
			InputStream is = null;
			if (isFirmato) {
				aLogger.info("sbusta fattura");
				InfoFileUtility infoFileUtility = new InfoFileUtility();
				is = infoFileUtility.sbusta(fatturaFile, fileName);
			} else {
				is = new FileInputStream(fatturaFile);
			}

			File pdf = creaPDFFattura(is);

			return pdf;

		} else if (pdfdocBeanOut.getNomemodelloout() != null && !"".equals(pdfdocBeanOut.getNomemodelloout())) {// ricevuta
			String xmlFatt = null;
			InputStream is = null;
			if (isFirmato) {
				InfoFileUtility infoFileUtility = new InfoFileUtility();
				is = infoFileUtility.sbusta(fatturaFile, fileName);
			} else {
				is = new FileInputStream(fatturaFile);
			}
			if (is.markSupported()) {
				is.reset();
			}

			if (mimeType != null && mimeType.contains("xml")) {
				xmlFatt = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8)).lines()
						.collect(Collectors.joining("\n"));
			}
			File pdf = creaModelloPdf(loginBean, idDoc, idUd, null, xmlFatt, pdfdocBeanOut.getNomemodelloout());

			return pdf;
		} else {
			return null;
		}
	}

	private DmpkWsGetinforenderingpdfdocBean callDmpkRenderingPdf(AurigaLoginBean loginBean, String idDoc)
			throws Exception {

		aLogger.debug("Chiamo la store DmpkWsGetinforenderingpdfdoc.");

		BigDecimal flgFatturaEl = null;
		String nomeModello = null;
		try {
			// Inizializzo l'INPUT
			DmpkWsGetinforenderingpdfdocBean input = new DmpkWsGetinforenderingpdfdocBean();
			input.setCodidconnectiontokenin(loginBean.getToken());
			input.setIddocin(new BigDecimal(idDoc));

			// Eseguo il servizio
			Getinforenderingpdfdoc service = new Getinforenderingpdfdoc();
			StoreResultBean<DmpkWsGetinforenderingpdfdocBean> output = service.execute(loginBean, input);

			if (output.isInError()) {
				aLogger.error("La store procedure DmpkWsGetinforenderingpdfdoc ha ritornato errore");
				aLogger.error(output.getResultBean().getErrmsgout());
				aLogger.error(output.getResultBean().getErrcodeout());
				aLogger.error(output.getResultBean().getErrcontextout());
				throw new Exception(output.getDefaultMessage());
			}

			if (idDoc == null || idDoc.equalsIgnoreCase("")) {
				throw new Exception("La store procedure ExtractFileUD ha ritornato id doc nullo");
			}

			// restituisco il nro versione
			if (output.getResultBean().getFlgfatturaelettronicaout() != null) {
				flgFatturaEl = output.getResultBean().getFlgfatturaelettronicaout();
			}

			if (output.getResultBean().getNomemodelloout() != null) {
				nomeModello = output.getResultBean().getNomemodelloout();
			}

			// popolo il bean di out
			DmpkWsGetinforenderingpdfdocBean result = new DmpkWsGetinforenderingpdfdocBean();
			result.setFlgfatturaelettronicaout(flgFatturaEl);
			result.setNomemodelloout(nomeModello);
			return result;
		} catch (Exception e) {
			aLogger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}

	public File creaPDFFattura(InputStream uriFileXmlIn) throws Exception {
		File pdfFileOut = null;
		try {
			InputStream fileXslIn = getClass().getResourceAsStream("/fatturapa.xsl");// + getNameXls(versione));
			TransformerFactory transformer = new TransformerFactoryImpl();
			StreamSource source = new StreamSource(fileXslIn);
			Templates xslTemplate = transformer.newTemplates(source);
			Transformer transform = xslTemplate.newTransformer();
			transform.setOutputProperty(OutputKeys.METHOD, "xml");
			transform.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transform.setOutputProperty(OutputKeys.INDENT, "yes");

			String fileName = "temp";
			File htmlFileOut = File.createTempFile(fileName, ".html");
			pdfFileOut = File.createTempFile(fileName, ".pdf");
			// uriFileXmlIn.reset();
			transform.transform(new StreamSource(uriFileXmlIn), new StreamResult(new FileOutputStream(htmlFileOut)));

			generatePDFFattura(htmlFileOut.getCanonicalPath(), pdfFileOut, PD4Constants.A4, null, null);

		} catch (Exception e) {
			System.out.println("errore " + e.getMessage());
		}
		return pdfFileOut;
	}

	public File creaPDFFattura(InputStream uriFileXmlIn, String versione) throws Exception {
		File pdfFileOut = null;
		try {
			InputStream fileXslIn = getClass().getResourceAsStream("/fattura_tabellare_v1.2.xsl");// +
																									// getNameXls(versione));
			TransformerFactory transformer = new TransformerFactoryImpl();
			StreamSource source = new StreamSource(fileXslIn);
			Templates xslTemplate = transformer.newTemplates(source);
			Transformer transform = xslTemplate.newTransformer();
			transform.setOutputProperty(OutputKeys.METHOD, "xml");
			transform.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transform.setOutputProperty(OutputKeys.INDENT, "yes");

			String fileName = "temp";
			File htmlFileOut = File.createTempFile(fileName, ".html");
			pdfFileOut = File.createTempFile(fileName, ".pdf");
			// uriFileXmlIn.reset();
			transform.transform(new StreamSource(uriFileXmlIn), new StreamResult(new FileOutputStream(htmlFileOut)));

			generatePDFFattura(htmlFileOut.getCanonicalPath(), pdfFileOut, PD4Constants.A4, null, null);

		} catch (Exception e) {
			System.out.println("errore " + e.getMessage());
		}
		return pdfFileOut;
	}

	public File creaPDFFatturaConXsl(String uriFileXmlIn, String nomeFileStyleSheet) throws Exception {
		String fileName = "";
		File pdfFileOut = null;
		try {
			File fileXslIn = new File(nomeFileStyleSheet);
			TransformerFactory transformer = new TransformerFactoryImpl();
			StreamSource source = new StreamSource(fileXslIn);
			Templates xslTemplate = transformer.newTemplates(source);
			Transformer transform = xslTemplate.newTransformer();
			transform.setOutputProperty(OutputKeys.METHOD, "xml");
			transform.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transform.setOutputProperty(OutputKeys.INDENT, "yes");

			fileName = org.apache.commons.io.FilenameUtils.removeExtension(uriFileXmlIn);
			File htmlFileOut = new File(fileName + ".html");
			pdfFileOut = new File(fileName + ".pdf");

			transform.transform(new StreamSource(uriFileXmlIn), new StreamResult(new FileOutputStream(htmlFileOut)));

			generatePDFFattura(htmlFileOut.getCanonicalPath(), pdfFileOut, PD4Constants.A4, null, null);

		} catch (Exception e) {
		}
		return pdfFileOut;
	}

	public File generatePDFFattura(String inputHTMLFileName, File outputPDFFile, Dimension format, String fontsDir,
			String headerBody) throws Exception {

		FileOutputStream fos = new FileOutputStream(outputPDFFile);
		PD4ML pd4ml = new PD4ML();
		pd4ml.setHtmlWidth(1024); // default 640
		pd4ml.addStyle("TABLE,DIV {page-break-inside: auto !important}", true);
		pd4ml.enableTableBreaks(true);

		if (fontsDir != null && fontsDir.length() > 0) {
			pd4ml.useTTF(fontsDir, true);
		}
		if (headerBody != null && headerBody.length() > 0) {
			PD4PageMark header = new PD4PageMark();
			header.setAreaHeight(-1);
			header.setHtmlTemplate(headerBody);
			pd4ml.setPageHeader(header);
		}
		pd4ml.enableDebugInfo();
		pd4ml.render("file:" + inputHTMLFileName, fos);
		return outputPDFFile;
	}

	private File creaModelloPdf(AurigaLoginBean loginBean, String idDoc, String idUd, String finalita,
			String contenutoFile, String nomeModello) throws Exception {

		try {
			ModelliDocXmlBean modelloDocBean = ModelliUtil.recuperaModello(loginBean, nomeModello);

			String sezioneCacheModello = ModelliUtil.getSezioneCacheModelloXFatture(loginBean, idUd, idDoc, finalita,
					nomeModello, contenutoFile);

			List<String> listaValoriModello = new ArrayList<>();
			// listaValoriModello.add("segnaturaRegInTimbro");
			// listaValoriModello.add("improntaDoc");

			File modello = ModelliUtil.generaModelloPdf(modelloDocBean, sezioneCacheModello, listaValoriModello, null,
					true);

			return modello;
		} catch (Exception e) {
			throw new Exception("Errore durante la creazione del modello per la busta pdf: " + e.getMessage(), e);
		}

	}

	public static synchronized CreaModFatturaInBean recuperaMetadatiFatturaAttiva(
			FatturaElettronicaType fatturaElettronicaType) throws Exception {
		FatturaElettronicaHeaderType fatturaElettronicaHeaderType = fatturaElettronicaType
				.getFatturaElettronicaHeader();
		FatturaElettronicaBodyType fatturaElettronicaBodyType = (FatturaElettronicaBodyType) fatturaElettronicaType
				.getFatturaElettronicaBody().get(0);
		GestioneFatture lGestioneFatture = new GestioneFatture();
		CreaModFatturaInBean creaFatturaInBean = new CreaModFatturaInBean();
		DatiGeneraliType datiGeneraliType = fatturaElettronicaBodyType.getDatiGenerali();
		DatiGeneraliDocumentoType datiGeneraliDocumentoType = datiGeneraliType.getDatiGeneraliDocumento();
		// mittente
		creaFatturaInBean.setFattCodFiscMittDoc(
				fatturaElettronicaHeaderType.getCedentePrestatore().getDatiAnagrafici().getCodiceFiscale());
		String denominazioneCP = fatturaElettronicaHeaderType.getCedentePrestatore().getDatiAnagrafici().getAnagrafica()
				.getDenominazione();
		String cognomeCP = fatturaElettronicaHeaderType.getCedentePrestatore().getDatiAnagrafici().getAnagrafica()
				.getCognome();
		String nomeCP = fatturaElettronicaHeaderType.getCedentePrestatore().getDatiAnagrafici().getAnagrafica()
				.getNome();
		if (denominazioneCP != null && !"".equals(denominazioneCP)) {
			creaFatturaInBean.setFattDenomMittDoc(denominazioneCP);
		} else {
			creaFatturaInBean.setFattDenomMittDoc(cognomeCP + " " + nomeCP);
		}

		if (FormatoTrasmissioneType.FPA_12.equals(fatturaElettronicaType.getVersione())) {
			creaFatturaInBean.setCanaleInvioDest("FEPA");
		} else {
			creaFatturaInBean.setCanaleInvioDest("FEPR");
		}

		List<String> causaliLista = datiGeneraliDocumentoType.getCausale();
		List<FatturaCausale> causales = new ArrayList();
		FatturaCausale fatturaCausale;
		for (Iterator<String> iterator = causaliLista.iterator(); iterator.hasNext();) {
			String causale = (String) iterator.next();
			fatturaCausale = new FatturaCausale();
			fatturaCausale.setCausale(causale);
			causales.add(fatturaCausale);
		}
		if (causales.size() > 0) {
			creaFatturaInBean.setCausali(causales);
		}
		creaFatturaInBean.setFattNumeroDoc(datiGeneraliDocumentoType.getNumero());
		creaFatturaInBean.setFattDataDoc(datiGeneraliDocumentoType.getData().toGregorianCalendar().getTime());
		creaFatturaInBean.setDataStesura(datiGeneraliDocumentoType.getData().toGregorianCalendar().getTime());

		creaFatturaInBean.setFattCodDestDoc(fatturaElettronicaHeaderType.getDatiTrasmissione().getCodiceDestinatario());

		// destinatario
		String denominazioneCC = fatturaElettronicaHeaderType.getCessionarioCommittente().getDatiAnagrafici()
				.getAnagrafica().getDenominazione();
		String cognomeCC = fatturaElettronicaHeaderType.getCessionarioCommittente().getDatiAnagrafici().getAnagrafica()
				.getCognome();
		String nomeCC = fatturaElettronicaHeaderType.getCessionarioCommittente().getDatiAnagrafici().getAnagrafica()
				.getNome();
		if (denominazioneCC != null && !"".equals(denominazioneCC)) {
			creaFatturaInBean.setFattDenomDestDoc(denominazioneCC);
		} else {
			creaFatturaInBean.setFattDenomDestDoc(cognomeCC + " " + nomeCC);
		}
		if ((fatturaElettronicaHeaderType.getCessionarioCommittente() != null)
				&& (fatturaElettronicaHeaderType.getCessionarioCommittente().getDatiAnagrafici() != null)) {
			creaFatturaInBean.setFattIdFiscDestDoc(fatturaElettronicaHeaderType.getCessionarioCommittente()
					.getDatiAnagrafici().getIdFiscaleIVA() != null
							? fatturaElettronicaHeaderType.getCessionarioCommittente().getDatiAnagrafici()
									.getIdFiscaleIVA().getIdCodice()
							: (fatturaElettronicaHeaderType.getCessionarioCommittente().getDatiAnagrafici()
									.getCodiceFiscale() != null
									&& !"".equals(fatturaElettronicaHeaderType.getCessionarioCommittente()
											.getDatiAnagrafici().getCodiceFiscale()))
													? fatturaElettronicaHeaderType.getCessionarioCommittente()
															.getDatiAnagrafici().getCodiceFiscale()
													: null);
		}
		if ((datiGeneraliType.getDatiOrdineAcquisto() != null)
				&& (datiGeneraliType.getDatiOrdineAcquisto().size() > 0)) {
			creaFatturaInBean.setFattNroOdaDoc(
					((DatiDocumentiCorrelatiType) datiGeneraliType.getDatiOrdineAcquisto().get(0)).getIdDocumento());
			if (((DatiDocumentiCorrelatiType) datiGeneraliType.getDatiOrdineAcquisto().get(0)).getData() != null) {
				creaFatturaInBean
						.setFattDtOdaDoc(((DatiDocumentiCorrelatiType) datiGeneraliType.getDatiOrdineAcquisto().get(0))
								.getData().toGregorianCalendar().getTime());
			}
			creaFatturaInBean.setFattCupOdaDoc(
					((DatiDocumentiCorrelatiType) datiGeneraliType.getDatiOrdineAcquisto().get(0)).getCodiceCUP());

			creaFatturaInBean.setFattCigOdaDoc(
					((DatiDocumentiCorrelatiType) datiGeneraliType.getDatiOrdineAcquisto().get(0)).getCodiceCIG());
		}
		creaFatturaInBean.setFattDivisaDoc(datiGeneraliDocumentoType.getDivisa());
		if ((fatturaElettronicaBodyType.getDatiPagamento() != null)
				&& (fatturaElettronicaBodyType.getDatiPagamento().size() > 0)) {
			BigDecimal lBigDecimal = new BigDecimal("0");
			for (DettaglioPagamentoType lDettaglioPagamentoType : ((DatiPagamentoType) fatturaElettronicaBodyType
					.getDatiPagamento().get(0)).getDettaglioPagamento()) {
				lBigDecimal = lBigDecimal.add(lDettaglioPagamentoType.getImportoPagamento());
			}
			creaFatturaInBean.setFattImportoDoc(lBigDecimal.toString().replace(".", ","));
			DatiPagamentoType datiPagamentoType = (DatiPagamentoType) fatturaElettronicaBodyType.getDatiPagamento()
					.get(0);
			if (((DettaglioPagamentoType) datiPagamentoType.getDettaglioPagamento().get(0))
					.getDataScadenzaPagamento() != null) {
				creaFatturaInBean
						.setFattScadPagDoc(((DettaglioPagamentoType) datiPagamentoType.getDettaglioPagamento().get(0))
								.getDataScadenzaPagamento().toGregorianCalendar().getTime());
			}
		}

		List<DatiRiepilogoType> riepilogo = fatturaElettronicaBodyType.getDatiBeniServizi().getDatiRiepilogo();
		for (DatiRiepilogoType item : riepilogo) {

			if ((creaFatturaInBean.getFattEsigibilitaIvaDoc() == null
					|| "".equals(creaFatturaInBean.getFattEsigibilitaIvaDoc())) && item.getEsigibilitaIVA() != null) {
				creaFatturaInBean.setFattEsigibilitaIvaDoc(item.getEsigibilitaIVA().value());
			}

		}

		TipoNumerazioneBean lTipoNumerazioneBean = new TipoNumerazioneBean();
		lTipoNumerazioneBean.setSigla("IT");
		creaFatturaInBean.setTipoNumerazioni(Arrays.asList(new TipoNumerazioneBean[] { lTipoNumerazioneBean }));
		return creaFatturaInBean;
	}

	public static synchronized CreaModFatturaInBean recuperaMetadatiFatturaPassiva(
			FatturaElettronicaType fatturaElettronicaType) throws Exception {
		FatturaElettronicaHeaderType fatturaElettronicaHeaderType = fatturaElettronicaType
				.getFatturaElettronicaHeader();
		Boolean isLotto = fatturaElettronicaType.getFatturaElettronicaBody().size() > 1;

		FatturaElettronicaBodyType fatturaElettronicaBodyType = fatturaElettronicaType.getFatturaElettronicaBody()
				.get(0);

		CreaModFatturaInBean creaFatturaInBean = new CreaModFatturaInBean();
		DatiGeneraliType datiGeneraliType = fatturaElettronicaBodyType.getDatiGenerali();
		DatiGeneraliDocumentoType datiGeneraliDocumentoType = datiGeneraliType.getDatiGeneraliDocumento();

		String denominazioneCP = fatturaElettronicaHeaderType.getCedentePrestatore().getDatiAnagrafici().getAnagrafica()
				.getDenominazione();
		String cognomeCP = fatturaElettronicaHeaderType.getCedentePrestatore().getDatiAnagrafici().getAnagrafica()
				.getCognome();
		String nomeCP = fatturaElettronicaHeaderType.getCedentePrestatore().getDatiAnagrafici().getAnagrafica()
				.getNome();

		creaFatturaInBean.setFattDenomMittDoc((denominazioneCP != null && !"".equals(denominazioneCP)) ? denominazioneCP
				: (cognomeCP + " " + nomeCP));
		creaFatturaInBean.setFattCodFiscMittDoc(
				fatturaElettronicaHeaderType.getCedentePrestatore().getDatiAnagrafici().getCodiceFiscale());
		creaFatturaInBean.setFattPIvaMittDoc(fatturaElettronicaHeaderType.getCedentePrestatore().getDatiAnagrafici()
				.getIdFiscaleIVA().getIdCodice());

		creaFatturaInBean.setFattCodDestDoc(fatturaElettronicaHeaderType.getDatiTrasmissione().getCodiceDestinatario());

		String denominazione = fatturaElettronicaHeaderType.getCessionarioCommittente().getDatiAnagrafici()
				.getAnagrafica().getDenominazione();
		String cognome = fatturaElettronicaHeaderType.getCessionarioCommittente().getDatiAnagrafici().getAnagrafica()
				.getCognome();
		String nome = fatturaElettronicaHeaderType.getCessionarioCommittente().getDatiAnagrafici().getAnagrafica()
				.getNome();
		if (denominazione != null && !"".equals(denominazione)) {
			creaFatturaInBean.setFattDenomDestDoc(denominazione);
		} else {
			creaFatturaInBean.setFattDenomDestDoc(cognome + " " + nome);
		}
		if ((fatturaElettronicaHeaderType.getCessionarioCommittente() != null)
				&& (fatturaElettronicaHeaderType.getCessionarioCommittente().getDatiAnagrafici() != null)) {
			creaFatturaInBean.setFattIdFiscDestDoc(fatturaElettronicaHeaderType.getCessionarioCommittente()
					.getDatiAnagrafici().getIdFiscaleIVA() != null
							? fatturaElettronicaHeaderType.getCessionarioCommittente().getDatiAnagrafici()
									.getIdFiscaleIVA().getIdCodice()
							: (fatturaElettronicaHeaderType.getCessionarioCommittente().getDatiAnagrafici()
									.getCodiceFiscale() != null
									&& !"".equals(fatturaElettronicaHeaderType.getCessionarioCommittente()
											.getDatiAnagrafici().getCodiceFiscale()))
													? fatturaElettronicaHeaderType.getCessionarioCommittente()
															.getDatiAnagrafici().getCodiceFiscale()
													: null);
		}

		creaFatturaInBean.setDataArrivo(new Date());

		if (isLotto) {
			creaFatturaInBean.setNomeDocType("Lotto FatturePA");
			creaFatturaInBean.setOggetto(
					"Lotto  Fatture N° " + fatturaElettronicaHeaderType.getDatiTrasmissione().getProgressivoInvio());
		} else {
			creaFatturaInBean.setFattNumeroDoc(datiGeneraliDocumentoType.getNumero());
			creaFatturaInBean.setFattDataDoc(datiGeneraliDocumentoType.getData().toGregorianCalendar().getTime());
			creaFatturaInBean.setDataStesura(datiGeneraliDocumentoType.getData().toGregorianCalendar().getTime());
			creaFatturaInBean.setFattDivisaDoc(datiGeneraliDocumentoType.getDivisa());

			BigDecimal importoFattura = new BigDecimal(0);
			List<DatiRiepilogoType> riepilogo = fatturaElettronicaBodyType.getDatiBeniServizi().getDatiRiepilogo();
			for (DatiRiepilogoType item : riepilogo) {
				importoFattura = importoFattura.add(item.getImponibileImporto());
				importoFattura = importoFattura.add(item.getImposta());

				if ((creaFatturaInBean.getFattEsigibilitaIvaDoc() == null
						|| "".equals(creaFatturaInBean.getFattEsigibilitaIvaDoc()))
						&& item.getEsigibilitaIVA() != null) {
					creaFatturaInBean.setFattEsigibilitaIvaDoc(item.getEsigibilitaIVA().value());
				}

			}

			if (fatturaElettronicaBodyType.getDatiGenerali().getDatiGeneraliDocumento()
					.getImportoTotaleDocumento() != null) {
				creaFatturaInBean.setFattImportoDoc(fatturaElettronicaBodyType.getDatiGenerali()
						.getDatiGeneraliDocumento().getImportoTotaleDocumento().toString().replace(".", ","));
			} else {
				creaFatturaInBean.setFattImportoDoc(importoFattura.toString().replace(".", ","));
			}

			if (fatturaElettronicaBodyType.getDatiPagamento() != null
					&& fatturaElettronicaBodyType.getDatiPagamento().size() > 0) {

				DatiPagamentoType datiPagamentoType = fatturaElettronicaBodyType.getDatiPagamento().get(0);
				if (datiPagamentoType.getDettaglioPagamento().get(0).getDataScadenzaPagamento() != null) {
					creaFatturaInBean.setFattScadPagDoc(datiPagamentoType.getDettaglioPagamento().get(0)
							.getDataScadenzaPagamento().toGregorianCalendar().getTime());
				}

			}
			if (datiGeneraliType.getDatiOrdineAcquisto() != null
					&& datiGeneraliType.getDatiOrdineAcquisto().size() > 0) {
				creaFatturaInBean.setFattNroOdaDoc(datiGeneraliType.getDatiOrdineAcquisto().get(0).getIdDocumento());
				if (datiGeneraliType.getDatiOrdineAcquisto().get(0).getData() != null) {
					creaFatturaInBean.setFattDtOdaDoc(
							datiGeneraliType.getDatiOrdineAcquisto().get(0).getData().toGregorianCalendar().getTime());
				}
				creaFatturaInBean.setFattCupOdaDoc(datiGeneraliType.getDatiOrdineAcquisto().get(0).getCodiceCUP());
				creaFatturaInBean.setFattCigOdaDoc(datiGeneraliType.getDatiOrdineAcquisto().get(0).getCodiceCIG());
			}
		}
		creaFatturaInBean.setFattCodDestDoc(fatturaElettronicaHeaderType.getDatiTrasmissione().getCodiceDestinatario());

		TipoNumerazioneBean lTipoNumerazioneBean = new TipoNumerazioneBean();
		lTipoNumerazioneBean.setSigla("IT");
		creaFatturaInBean.setTipoNumerazioni(Arrays.asList(new TipoNumerazioneBean[] { lTipoNumerazioneBean }));
		return creaFatturaInBean;
	}

	public void salvaMetadatiFattura(AurigaLoginBean loginBean, String idDoc, String firmato, File fattura, // List<AttachWSAddUdBean>
																											// listaAttach,
			String tipoProvenienza, String nomeDocType, Session session)
			throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, Exception {
		aLogger.debug("recupero MetadatiFattura ");
		InputStream istremFatt = null;
		if ("1".equals(firmato)) {
			istremFatt = sbustaFileFirmato(fattura.toURI().toString(), fattura.getName());
		} else {
			istremFatt = new FileInputStream(fattura);

		}
		FatturaElettronicaType fatturaElettronicaType = (FatturaElettronicaType) FattureUtil.unmarshal(istremFatt,
				FatturaElettronicaType.class);
		aLogger.debug("unmarshalled fattura ");

		CreaModFatturaInBean creaModFatturaInBean = null;
		if ("E".equals(tipoProvenienza)) {
			creaModFatturaInBean = FattureUtil.recuperaMetadatiFatturaPassiva(fatturaElettronicaType);
		} else {
			if ("I".equals(tipoProvenienza)) {
				creaModFatturaInBean = FattureUtil.recuperaMetadatiFatturaAttiva(fatturaElettronicaType);
			} else {
				throw new Exception("TipoProvenienza non valorizzato!!!");
			}
		}
		aLogger.debug("recuperati MetadatiFattura ");
		if (nomeDocType != null && !"".equals(nomeDocType) && !"0".equals(nomeDocType)) {
			creaModFatturaInBean.setNomeDocType(nomeDocType);
		}

		updateDocFattura(loginBean, session, new BigDecimal(idDoc), creaModFatturaInBean);

		aLogger.debug("fine modificata fattura ");

	}

	protected void updateDocFattura(AurigaLoginBean pAurigaLoginBean, Session session, BigDecimal idDocPrimario,
			CreaModFatturaInBean creaModFatturaInBean)
			throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, Exception {

		DmpkCoreUpddocudBean dmpkCoreUpddocudBean = new DmpkCoreUpddocudBean();
		dmpkCoreUpddocudBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
		XmlUtilitySerializer lXmlUtilitySerializerXOrd = new XmlUtilitySerializer();
		dmpkCoreUpddocudBean.setAttributiuddocxmlin(lXmlUtilitySerializerXOrd.bindXml(creaModFatturaInBean));
		dmpkCoreUpddocudBean.setFlgtipotargetin("D");
		dmpkCoreUpddocudBean.setIduddocin(idDocPrimario);
		dmpkCoreUpddocudBean.setFlgautocommitin(0); // blocco l'autocommit

		final UpddocudImpl store = new UpddocudImpl();
		store.setBean(dmpkCoreUpddocudBean);
		session.doWork(new Work() {

			@Override
			public void execute(Connection paramConnection) throws SQLException {
				paramConnection.setAutoCommit(false);
				store.execute(paramConnection);
			}
		});
		aLogger.debug("dopo execute");

		StoreResultBean<DmpkCoreUpddocudBean> result = new StoreResultBean<DmpkCoreUpddocudBean>();
		AnalyzeResult.analyze(dmpkCoreUpddocudBean, result);
		result.setResultBean(dmpkCoreUpddocudBean);

		if (result.isInError()) {
			throw new StoreException(result);
		}
		aLogger.debug("dopo execute ok");

	}

	public String isFattura(String utente, String xmlIn, Session session) {
		return isFattura(utente, xmlIn, false, session);
	}

	public String isFattura(String utente, String xmlIn, boolean upd, Session session) {
		String sqlString = "select par_name, NVL(DEFAULT_VALUE,'ND') FROM DMT_DEF_CONFIG_PARAM WHERE par_name IN ('USERNAME_UTENZE_APP_FATTURE', 'ID_DOC_TYPE_FATTPA', 'NOME_DOC_TYPE_FATTPA')";
		SQLQuery query = session.createSQLQuery(sqlString);
		List<Object[]> parametri = (List<Object[]>) query.list();
		String username = "";
		String idDocType = "";
		String nomeDocTypes = "";
		String nomeDocType = null;
		boolean isFattura = false;
		for (int i = 0; i < parametri.size(); i++) {
			String nome = (String) parametri.get(i)[0];
			aLogger.debug("nome: " + nome);
			String valore = (String) parametri.get(i)[1];
			aLogger.debug("valore: " + valore);
			if (nome.equals("USERNAME_UTENZE_APP_FATTURE")) {
				username = valore;
				aLogger.debug("username: " + valore);
			}
			if (nome.equals("ID_DOC_TYPE_FATTPA")) {
				idDocType = valore;
				aLogger.debug("idDocType: " + valore);
			}
			if (nome.equals("NOME_DOC_TYPE_FATTPA")) {
				nomeDocTypes = valore;
				aLogger.debug("nomeDocType: " + valore);
			}
		}
		// aLogger.debug("dopo for (int i = 0; i < parametri.size(); i++)");
		if (username != null) {
			try {
				aLogger.debug("utente " + utente);
				if (username.contains(utente)) {
					aLogger.debug("username");
					isFattura = true;
					if (!upd) {// solo addud
						String codId = AddUdUtils.getTagTipoDocCodId(xmlIn);
						String nome = AddUdUtils.getTagTipoDocDecodificaNome(xmlIn);
						if (nome == null && codId == null) { // se non esiste in xml input
							if (nomeDocTypes != null && !"".equals(nomeDocTypes)) {
								nomeDocType = nomeDocTypes.split(";")[0];
							} else {
								throw new Exception(
										"Caso utenza per fattura: Nessuna indicazione in ingresso di un tipo doccumento");
							}
						} else {
							if (nomeDocTypes.contains(nome)) {
								nomeDocType = "0";
							}
						}
					} else {
						nomeDocType = "0";
					}
				}
			} catch (Exception e) {
				aLogger.warn(e.getMessage(), e);
			}
		}
		if (idDocType != null && !isFattura) {
			try {
				String codId = AddUdUtils.getTagTipoDocCodId(xmlIn);
				aLogger.debug("codId " + codId);

				if (idDocType.contains(codId)) {
					aLogger.debug("idDocType");
					isFattura = true;
					nomeDocType = "0";
				}

			} catch (Exception e) {
				aLogger.warn(e.getMessage(), e);
			}
		}
		try {
			if (nomeDocTypes != null && !isFattura) {

				String nome = AddUdUtils.getTagTipoDocDecodificaNome(xmlIn);
				aLogger.debug("nome " + nome);
				if (nomeDocTypes.contains(nome)) {
					aLogger.debug("nomeDocType");
					isFattura = true;
					nomeDocType = "0";
				}

			}
		} catch (Exception e) {
			aLogger.warn(e.getMessage(), e);
			// return null;
		}
		if (isFattura) {
			// se fattura, nomeDocType contiene:
			// 0 se non deve forzare tipo documento
			// nome tipo documento se deve forzare
			return nomeDocType;
		} else {
			return null;
		}
	}

	public String getTipoProvenienza(String idUd, Session session) {
		String sqlString = "SELECT flg_tipo_prov FROM dmt_unita_doc WHERE id_ud = :idUd";
		aLogger.debug("prima query getTipoProvenienza ok");
		SQLQuery query = session.createSQLQuery(sqlString);
		query.setParameter("idUd", new BigDecimal(idUd));

		Object tipoProvenienza = query.uniqueResult();
		if (tipoProvenienza == null || "".equals(tipoProvenienza)) {
			tipoProvenienza = "I";
		}
		aLogger.debug("dopo query getTipoProvenienza ok");
		return (String) tipoProvenienza;
	}

	// FINE AURIGA-869
	@SuppressWarnings("rawtypes")
	public static Object unmarshal(File xmlMessaggio, Class classe) throws Exception {
		JAXBContext jc = JAXBContext.newInstance(classe);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		// System.out.println("prova");
		InputStream inputStream = new FileInputStream(xmlMessaggio);
		Reader reader = new InputStreamReader(inputStream, "UTF-8");
		Object obj = unmarshaller.unmarshal(xmlMessaggio);
		if (obj instanceof JAXBElement) {
			JAXBElement jaxbEle = (JAXBElement) obj;
			return jaxbEle.getValue();
		} else
			return obj;
	}

	@SuppressWarnings("rawtypes")
	public static Object unmarshal(InputStream xmlMessaggio, Class classe) throws Exception {
		JAXBContext jc = JAXBContext.newInstance(classe);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		Object obj = unmarshaller.unmarshal(xmlMessaggio);
		if (obj instanceof JAXBElement) {
			JAXBElement jaxbEle = (JAXBElement) obj;
			return jaxbEle.getValue();
		} else
			return obj;
	}

	@SuppressWarnings("rawtypes")
	public static Object unmarshal(Reader xmlMessaggio, Class classe) throws Exception {
		JAXBContext jc = JAXBContext.newInstance(classe);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		Object obj = unmarshaller.unmarshal(xmlMessaggio);
		if (obj instanceof JAXBElement) {
			JAXBElement jaxbEle = (JAXBElement) obj;
			return jaxbEle.getValue();
		} else
			return obj;
	}

	@SuppressWarnings("rawtypes")
	public static Object unmarshal(String xmlMessaggio, Class classe) throws Exception {
		JAXBContext jc = JAXBContext.newInstance(classe);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		StringReader reader = new StringReader(xmlMessaggio);
		Object obj = unmarshaller.unmarshal(reader);
		if (obj instanceof JAXBElement) {
			JAXBElement jaxbEle = (JAXBElement) obj;
			return jaxbEle.getValue();
		} else
			return obj;
	}

	@SuppressWarnings("rawtypes")
	public static String marshal(Object bean, Class classe, String pathXSD) throws Exception {
		StringWriter sw = new StringWriter();
		BufferedWriter bw = new BufferedWriter(sw);
		bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"  standalone=\"yes\"?>");
		bw.newLine();
		bw.write("<?xml-stylesheet type=\"text/xsl\" href=\"fatturapa_v1.1.xsl\"?>");
		bw.newLine();
		JAXBContext jc = JAXBContext.newInstance(classe);
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		URL xsdURL = FattureUtil.class.getResource("/xsd/" + pathXSD);
		Schema schema = schemaFactory.newSchema(xsdURL);

		// System.setProperty("file.encoding", "UTF-8");

		Marshaller marshaller = jc.createMarshaller();
		marshaller.setSchema(schema);
		marshaller.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);
		// marshaller.setProperty(Marshaller.JAXB_ENCODING,"UTF-8");
		// marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.marshal(bean, bw);
		bw.close();
		// String bwStr = bw.toString();
		String swStr = sw.toString();
		return swStr;
	}

	@SuppressWarnings("rawtypes")
	public static String marshal(Object bean, Class classe) throws Exception {
		StringWriter sw = new StringWriter();
		BufferedWriter bw = new BufferedWriter(sw);
		bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"  standalone=\"yes\"?>");
		bw.newLine();
		bw.write("<?xml-stylesheet type=\"text/xsl\" href=\"fatturapa_v1.1.xsl\"?>");
		bw.newLine();
		JAXBContext jc = JAXBContext.newInstance(classe);
		Marshaller marshaller = jc.createMarshaller();
		marshaller.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.marshal(bean, bw);
		bw.close();
		String swStr = sw.toString();
		return swStr;
	}

	private InputStream sbustaFileFirmato(String p7m, String fileName) throws IOException, Exception {

		InfoFileUtility lInfoFileUtility = new InfoFileUtility();
		InputStream isSbustato = lInfoFileUtility.sbusta(p7m, fileName);

		return isSbustato;
	}

	// AURIGA 896
	public void gestisciSeFattura(String xml, AurigaLoginBean loginBean, String idDoc, String flgFirmato, File fattura,
			String nomeDocType, String tipoProvenienza, Session session) throws Exception {
		try {
			aLogger.debug("Controllo se è una fattura");
			FattureUtil fattureUtil = new FattureUtil();
			aLogger.debug("tipoProvenienza: " + tipoProvenienza);
			fattureUtil.salvaMetadatiFattura(loginBean, idDoc, flgFirmato, fattura, tipoProvenienza, nomeDocType,
					session);
		} catch (Exception e) {
			aLogger.warn("Errore durante la gestione del caso fattura: " + e.getMessage());
			throw e;
		}
	}

	public DocumentoXmlOutBean loadDoc(AurigaLoginBean pAurigaLoginBean, String pIdUd) throws Exception {
		BigDecimal idUd = (pIdUd != null) ? new BigDecimal(pIdUd) : null;
		RecuperaDocumentoInBean lRecuperaDocumentoInBean = new RecuperaDocumentoInBean();
		lRecuperaDocumentoInBean.setIdUd(idUd);
		RecuperoDocumenti lRecuperoDocumenti = new RecuperoDocumenti();
		RecuperaDocumentoOutBean lRecuperaDocumentoOutBean;
		DocumentoXmlOutBean lDocumentoXmlOutBean = null;
		try {
			lRecuperaDocumentoOutBean = lRecuperoDocumenti.loadDocumento(pAurigaLoginBean, lRecuperaDocumentoInBean);
			if (lRecuperaDocumentoOutBean.isInError()) {
				String errMessage = "StoreName = " + lRecuperaDocumentoOutBean.getStoreName() + ", ErrorContext = "
						+ lRecuperaDocumentoOutBean.getErrorContext() + ", ErroreCode = "
						+ lRecuperaDocumentoOutBean.getErrorCode() + ", defaultMessage = "
						+ lRecuperaDocumentoOutBean.getDefaultMessage();
				throw new Exception(errMessage);
			}
			lDocumentoXmlOutBean = lRecuperaDocumentoOutBean.getDocumento();
		} catch (Exception e) {
			throw new Exception("Impossibile reperire il record del documento primario. ERRORE = " + e.getMessage());
		}

		return lDocumentoXmlOutBean;
	}

}
