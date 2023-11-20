/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;
import org.apache.xalan.processor.TransformerFactoryImpl;
import org.zefer.pd4ml.PD4Constants;
import org.zefer.pd4ml.PD4ML;
import org.zefer.pd4ml.PD4PageMark;

import it.eng.auriga.database.store.dmpk_ws.bean.DmpkWsGetinforenderingpdfdocBean;
import it.eng.auriga.database.store.dmpk_ws.store.Getinforenderingpdfdoc;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.util.bean.ModelliDocXmlBean;

public class FattureUtil {
	
	static Logger aLogger = Logger.getLogger(FattureUtil.class.getName());

	public File generaPdfFattura(AurigaLoginBean loginBean, File fatturaFile, boolean isFirmato, String fileName, 
			String idDoc, String idUd, String mimeType)
			throws Exception, FileNotFoundException, IOException {
		// chimata store
		DmpkWsGetinforenderingpdfdocBean dmpkWsGetinforenderingpdfdocBeanIn = new DmpkWsGetinforenderingpdfdocBean();
		dmpkWsGetinforenderingpdfdocBeanIn.setIddocin(new BigDecimal(idDoc));
		DmpkWsGetinforenderingpdfdocBean pdfdocBeanOut = callDmpkRenderingPdf(loginBean,
				idDoc);

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
			
		} else if (pdfdocBeanOut.getNomemodelloout() != null
					&& !"".equals(pdfdocBeanOut.getNomemodelloout())) {// ricevuta
				String xmlFatt = null;
				InputStream is = null;
				if (isFirmato) {
					InfoFileUtility infoFileUtility = new InfoFileUtility();
					is = infoFileUtility.sbusta(fatturaFile, fileName);
				} else {
					is = new FileInputStream(fatturaFile);
				}
				is.reset();

				if (mimeType != null && mimeType.contains("xml")) {
					xmlFatt = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))
							.lines().collect(Collectors.joining("\n"));
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
//			uriFileXmlIn.reset();
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
			InputStream fileXslIn = getClass().getResourceAsStream("/fattura_tabellare_v1.2.xsl");// + getNameXls(versione));
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
//			uriFileXmlIn.reset();
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
	
	private File creaModelloPdf(AurigaLoginBean loginBean, String idDoc, String idUd, String finalita, String contenutoFile, String nomeModello)
			throws Exception {

		try {
			ModelliDocXmlBean modelloDocBean = ModelliUtil.recuperaModello(loginBean, nomeModello);

			String sezioneCacheModello = ModelliUtil.getSezioneCacheModello(loginBean, idUd, idDoc, finalita, contenutoFile, nomeModello);

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
	
	
	
}
