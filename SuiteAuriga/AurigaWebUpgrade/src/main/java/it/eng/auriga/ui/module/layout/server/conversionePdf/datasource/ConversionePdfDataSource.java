/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.exception.StoreException;
import it.eng.auriga.ui.module.layout.server.conversionePdf.datasource.bean.ConversionePdfBean;
import it.eng.auriga.ui.module.layout.server.conversionePdf.datasource.bean.FileDaConvertireBean;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.pdfUtility.PdfUtil;
import it.eng.utility.pdfUtility.editabili.PdfEditabiliUtil;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.ParametriDBUtil;

@Datasource(id = "ConversionePdfDataSource")
public class ConversionePdfDataSource extends AbstractServiceDataSource<ConversionePdfBean, ConversionePdfBean> {

	private static Logger mLogger = Logger.getLogger(ConversionePdfDataSource.class);

	@Override
	public ConversionePdfBean call(ConversionePdfBean pInBean) throws Exception {
		
		List<FileDaConvertireBean> lFileDaConvertireBeanList = new ArrayList<FileDaConvertireBean>();
				
		boolean conversionePerFirma =  (getExtraparams() != null ? ((StringUtils.isNotBlank(getExtraparams().get("SCOPO"))) ? "FIRMA".equalsIgnoreCase(getExtraparams().get("SCOPO")) : false) : false );
		
		boolean abilitaPdfA = ParametriDBUtil.getParametroDBAsBoolean(getSession(), "FIRMA_ABILITA_PDFA");
		for (FileDaConvertireBean lFileDaConvertireBean : pInBean.getFiles()) {
			String nomeFile = lFileDaConvertireBean.getNomeFile();
			String uri = lFileDaConvertireBean.getUri();
			MimeTypeFirmaBean infoFile = lFileDaConvertireBean.getInfoFile();
			// se è già un pdf
			boolean skipConversione = false;
			boolean conversionePdfPdfa = false;
			if (infoFile != null) {
				// Controllo se devo convertire il pdf in pdfa
				if (infoFile.getMimetype() != null && infoFile.getMimetype().equals("application/pdf") && !infoFile.isFirmato() && abilitaPdfA) {
					// Dovrei mettere skipConversione a true se è già un pdfa
					if (!InfoFileUtility.checkPdfA(StorageImplementation.getStorage().extract(uri))) {
						skipConversione = false;
						conversionePdfPdfa = true;
					} else {
						skipConversione = true;
					}
				} else if ((infoFile.getMimetype() != null && infoFile.getMimetype().equals("application/pdf")) || infoFile.isFirmato() || !infoFile.isConvertibile()) {
					skipConversione = true;
				}
			} else if (nomeFile != null && (nomeFile.toLowerCase().endsWith(".pdf") || nomeFile.toLowerCase().endsWith(".p7m"))) {
				skipConversione = true;
			}
			if (!skipConversione) {
				String uriPdf = "";
				/*
				 * CONTROLLO INTRODOTTO PER GESTIRE I PDF EDITABILI AVENTI XFAFORM
				 * CON INTEGRAZIONE DI CONVERSIONE ESTERNA
				 */
				if (infoFile != null && infoFile.isPdfEditabile()) {
					if (StringUtils.isNotBlank(ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_GEST_PDF_EDITABILI_IN_UPLOAD"))
							&& "true".equalsIgnoreCase(ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_GEST_PDF_EDITABILI_IN_UPLOAD"))
							&& StringUtils.isNotBlank(ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_CONV_XFAFORM"))
							&& "true".equalsIgnoreCase(ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_CONV_XFAFORM"))) {
						InputStream is = StorageImplementation.getStorage().extract(uri);
						File fileTempEditabile = File.createTempFile("editabileXfaForm", ".pdf");
						FileUtils.copyInputStreamToFile(is, fileTempEditabile);
						if (PdfEditabiliUtil.checkEditableFileWithXfaForm(fileTempEditabile.getPath())) {
							InfoFileUtility lInfoFileUtility = new InfoFileUtility();
							uri = StorageImplementation.getStorage().storeStream(lInfoFileUtility.converti(fileTempEditabile.toURI().toString(), getNomeFileSbustato(StringUtils.isNotBlank(nomeFile) ? nomeFile : "File.pdf")));
						}
					}
				} 				
				if (conversionePdfPdfa) {
					try {
						File filePdfa = File.createTempFile("filePdfa", ".pdf");
						InfoFileUtility lFileUtility = new InfoFileUtility();
						lFileUtility.convertiPdfToPdfA(StorageImplementation.getStorage().extract(uri), new FileOutputStream(filePdfa));
						uriPdf = StorageImplementation.getStorage().store(filePdfa);
					} catch (Exception e) {
						mLogger.error("Si è verificato un errore durante la conversione in PDFA del file " + nomeFile, e);
						throw new StoreException("Si è verificato un errore durante la conversione in PDFA del file " + nomeFile);
					}
				} else {
					try {
						uriPdf = convertPdf(uri, nomeFile, conversionePerFirma && abilitaPdfA);
					} catch (Exception e) {
						mLogger.error("Si è verificato un errore durante la conversione in PDF del file " + nomeFile, e);
						throw new StoreException("Si è verificato un errore durante la conversione in PDF del file " + nomeFile);
					}
				}
				String impronta = calculateHash(uriPdf);
				if (infoFile == null || infoFile.getImpronta() == null || !infoFile.getImpronta().equals(impronta)) {
					File fileConvertito = StorageImplementation.getStorage().getRealFile(uriPdf);
					lFileDaConvertireBean.setNomeFile(FilenameUtils.getBaseName(nomeFile) + ".pdf");
					lFileDaConvertireBean.setUri(uriPdf);
					MimeTypeFirmaBean infoFilePdf = new MimeTypeFirmaBean();
					infoFilePdf.setMimetype("application/pdf");
					infoFilePdf.setNumPaginePdf(PdfUtil.recuperaNumeroPagine(fileConvertito));					
					infoFilePdf.setImpronta(impronta);
					infoFilePdf.setConvertibile(true);
					infoFilePdf.setCorrectFileName(lFileDaConvertireBean.getNomeFile());
					lFileDaConvertireBean.setInfoFile(infoFilePdf);
					lFileDaConvertireBean.setNomeFilePrec(nomeFile);
					lFileDaConvertireBean.setUriPrec(uri);
					lFileDaConvertireBean.setInfoFilePrec(infoFile);
				}
			}
			lFileDaConvertireBeanList.add(lFileDaConvertireBean);
		}
		pInBean.setFiles(lFileDaConvertireBeanList);
		return pInBean;
	}

	public String convertPdf(String uri, String nomeFile, boolean pdfA) throws StoreException {
		try {
			InfoFileUtility lInfoFileUtility = new InfoFileUtility();
			String fileUrl = StorageImplementation.getStorage().getRealFile(uri).toURI().toString();
			String uriPdfGenerato = StorageImplementation.getStorage().storeStream(lInfoFileUtility.converti(fileUrl, nomeFile, null, pdfA));
			return uriPdfGenerato;
		} catch (Exception e) {
			mLogger.error("Errore durante la conversione in pdf: " + e.getMessage(), e);
			throw new StoreException("Non è stato possibile convertire il file: " + e.getMessage());
		}
	}

	public String calculateHash(String uri) throws StoreException {
		try {
			InfoFileUtility lInfoFileUtility = new InfoFileUtility();
			String fileUrl = StorageImplementation.getStorage().getRealFile(uri).toURI().toString();
			return lInfoFileUtility.calcolaImpronta(fileUrl, "");
		} catch (Exception e) {
			mLogger.error("Errore durante il calcolo dell'impronta: " + e.getMessage(), e);
			throw new StoreException("Non è stato possibile calcolare l'impronta del file");
		}
	}
	
	public static String getNomeFileSbustato(String nomeFile) { 
		String ext = ".p7m.m7m.std";
		String nomeFileSbustato = nomeFile;
		while (nomeFileSbustato.length() > 3 && ext.contains(nomeFileSbustato.substring(nomeFileSbustato.length() - 4))) {
			nomeFileSbustato = nomeFileSbustato.substring(0, nomeFileSbustato.length() - 4);
		}
		return nomeFileSbustato;
	}

}
