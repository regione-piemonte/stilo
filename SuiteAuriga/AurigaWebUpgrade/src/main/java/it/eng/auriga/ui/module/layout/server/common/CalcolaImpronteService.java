/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.io.RASInputStream;
import com.itextpdf.text.io.RandomAccessSource;
import com.itextpdf.text.io.RandomAccessSourceFactory;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfArray;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;

import it.eng.auriga.ui.module.layout.server.conversionePdf.datasource.bean.ConversionePdfBean;
import it.eng.auriga.ui.module.layout.server.conversionePdf.datasource.bean.FileDaConvertireBean;
import it.eng.auriga.ui.module.layout.server.task.bean.InfoFirmaGraficaBean;
import it.eng.auriga.util.PdfSignatureUtils;
import it.eng.fileOperation.clientws.DigestAlgID;
import it.eng.fileOperation.clientws.DigestEncID;
import it.eng.hsm.pades.FirmaPadesApposizioneFileOpUtil;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.DocumentConfiguration;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.pdfUtility.PdfUtil;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.sign.FileElaborate;
import it.eng.utility.ui.sign.SignerHashUtil;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;

/**
 * Questa classe viene utilizzata per calcolare l'impronta del file quando firmo tramite applet o hybrid
 *
 */

@Datasource(id = "CalcolaImpronteService")
public class CalcolaImpronteService extends AbstractServiceDataSource<ConversionePdfBean, ConversionePdfBean> {
	
	private static Logger mLogger = Logger.getLogger(CalcolaImpronteService.class);

	@Override
	public ConversionePdfBean call(ConversionePdfBean bean) throws Exception {
		throw new UnsupportedOperationException("Operazione non supportata");
	}

	public ConversionePdfBean aggiungiEventualeRettangoloFirmaECalcolaImpronta(ConversionePdfBean bean) throws Exception {
		// Non ho l'informazione sul tipo di firma che verrà fatta, quindi calcolo sia l'impronta per la firma PAdES con eventuale rettagolo di firma sia quella per la firma CAdES congiunta 
		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringAppContext.getContext().getBean("DocumentConfiguration");
		for (FileDaConvertireBean lFileBean : bean.getFiles()) {
			if (lFileBean.getInfoFile() != null && lFileBean.getInfoFile().getMimetype() != null && lFileBean.getInfoFile().getMimetype().equals("application/pdf")) {
				// Calcolo l'impronta pdf del file che mi serve nel caso di firma PAdES
				try {
					FileElaborate fileElaborate = new FileElaborate();
					File lFileDaFirmare = StorageImplementation.getStorage().extractFile(lFileBean.getUri());				
					if (lFileBean.getInfoFile() != null && lFileBean.getInfoFile().isFirmato() && lFileBean.getInfoFile().getTipoFirma().startsWith("CAdES")) {
						P7MDetector lP7MDetector = new P7MDetector();
						File lFileSbustato = StorageImplementation.getStorage().extractFile(StorageImplementation.getStorage().storeStream(lP7MDetector.getContent(lFileDaFirmare), new String[] {}));
						fileElaborate.setUnsigned(lFileSbustato);
					} else {
						fileElaborate.setUnsigned(lFileDaFirmare);
					}
					// Verifico se devo aggiungere l'evidenza grafica
					if (firmaEseguitaConFirmaGraficaFileop(lFileBean.getListaInformazioniFirmaGrafica())) {
						// L'apposizione del rettangolo di firma viene fatta da fileop
						String reason = "";
						String location = "";
						String nomeCampoFirma = "Firma " + UUID.randomUUID().toString();
						Integer nroPaginePdf = lFileBean.getInfoFile() != null && lFileBean.getInfoFile().getNumPaginePdf() != null && lFileBean.getInfoFile().getNumPaginePdf() > 0 ? lFileBean.getInfoFile().getNumPaginePdf() : PdfUtil.recuperaNumeroPagine(lFileDaFirmare);
						String firmatario = bean.getRettangoloFirmaPades().getFirmatario();
						List<InfoFirmaGraficaBean> lListaInformazioniFirmaGrafica = lFileBean.getListaInformazioniFirmaGrafica();
						InfoFirmaGraficaBean lInformazioniFirmaGraficaBean = null;
						for (InfoFirmaGraficaBean lInfoFirmaGraficaBean : lListaInformazioniFirmaGrafica) {
							if (!lInfoFirmaGraficaBean.isFirmaEseguita() && !lInfoFirmaGraficaBean.isFirmaInEsecuzione()) {
								lInformazioniFirmaGraficaBean = lInfoFirmaGraficaBean;
								break;
							}
						}
						lInformazioniFirmaGraficaBean.setFirmaInEsecuzione(true);
						byte[] byteFileConApposizioneGraficaFirma = FirmaPadesApposizioneFileOpUtil.getByteFileDaFirmare(lFileDaFirmare.toURI().toString(), lFileBean.getNomeFile(), null, firmatario, nomeCampoFirma, reason, location, nroPaginePdf, lInformazioniFirmaGraficaBean);
						String uriFileConApposizioneGraficaFirma = StorageImplementation.getStorage().storeStream(new ByteArrayInputStream(byteFileConApposizioneGraficaFirma));
						File fileConApposizioneGraficaFirma = StorageImplementation.getStorage().getRealFile(uriFileConApposizioneGraficaFirma);
						fileElaborate.setUnsigned(fileConApposizioneGraficaFirma);
//						SignerHashUtil.calcolaHashPdf(fileElaborate, lDocumentConfiguration.getAlgoritmo(), lDocumentConfiguration.getEncoding());
						byte[] byteEncodedHash = getHashPerFirmaApposizioneFileop(fileConApposizioneGraficaFirma, nomeCampoFirma);
						String encodedHash = com.itextpdf.text.pdf.codec.Base64.encodeBytes(byteEncodedHash);
						fileElaborate.setEncodedHash(encodedHash);
						// Devo mettere questi parametri nella ServletContext, verranno poi recuperati e cancellati nella UploadMultiHashSignerApplet
						getSession().getServletContext().setAttribute("apposizioneGraficaFirmaTramiteFileop" + lFileBean.getIdFile(), "true");
						getSession().getServletContext().setAttribute("nomeCampoFirma" + lFileBean.getIdFile(), nomeCampoFirma);
					} else if (ParametriDBUtil.getParametroDBAsBoolean(getSession(), "SHOW_GRAPHIC_SIGNATURE_IN_PADES") && bean.getRettangoloFirmaPades() != null) {
						// Ho letto il nome del firmatario dal dispositivo, e ho un rettangolo di firma per l'evidenza grafica
						// Utilizzo la funzione creata per calcolare l'hash del pdf con il rettangolo contentente la firma
						PdfSignatureUtils pdfUtil = new PdfSignatureUtils();
						pdfUtil.calcolaHashPdf(fileElaborate, lDocumentConfiguration.getAlgoritmo(), lDocumentConfiguration.getEncoding(), bean.getRettangoloFirmaPades());
					} else {
						// Calcolo l'hash senza aggiungere rettangolo di firma
						SignerHashUtil.calcolaHashPdf(fileElaborate, lDocumentConfiguration.getAlgoritmo(), lDocumentConfiguration.getEncoding());
					}
					// Setto l'impronta pdf
					lFileBean.getInfoFile().setImprontaPdf(fileElaborate.getEncodedHash());
					// Devo mettere questi parametri nella ServletContext, verranno poi recuperati e cancellati nella UploadMultiHashSignerApplet
					getSession().getServletContext().setAttribute("fileElaborate" + lFileBean.getIdFile(), fileElaborate);
					getSession().getServletContext().setAttribute("ATTIVA_CTRL_USER_VS_CERTIF_FIRMA_DIG_FOR_APPLET" + lFileBean.getIdFile(), ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_CTRL_USER_VS_CERTIF_FIRMA_DIG") + "");
					getSession().getServletContext().setAttribute("SIGNATURE_ID_USER_FOR_APPLET" + lFileBean.getIdFile(), AurigaUserUtil.getLoginInfo(getSession()).getIdUser() + "");
					getSession().getServletContext().setAttribute("SIGNATURE_USER_SCHEMA_FOR_APPLET" + lFileBean.getIdFile(), AurigaUserUtil.getLoginInfo(getSession()).getSchema() + "");
				} catch (Exception e) {
					// Se entro in questa eccezione vuol dire che il file da firmare non era un pdf
					mLogger.warn(e);
				}
			}
			// // Calcolo l'impronta del file sbustato che mi serve nel caso di firma CAdES congiunta
			boolean isFirmaCongiunta = ParametriDBUtil.getParametroDBAsBoolean(getSession(), "FIRMA_CONGIUNTA");
			if (getExtraparams().get("firmaCongiunta") != null && !"".equals(getExtraparams().get("firmaCongiunta"))) {
				isFirmaCongiunta = getExtraparams().get("firmaCongiunta").equalsIgnoreCase("true");
			}
			if (isFirmaCongiunta) {
				if (lFileBean.getInfoFile() != null && lFileBean.getInfoFile().isFirmato() && lFileBean.getInfoFile().getTipoFirma().startsWith("CAdES")) {
					// Calcolo l'impronta del file sbustato che mi serve nel caso di firma congiunta di un file già  firmato
					try {
						File lFileDaFirmare = StorageImplementation.getStorage().extractFile(lFileBean.getUri());
						P7MDetector lP7MDetector = new P7MDetector();
						byte[] hash = null;
						if (lDocumentConfiguration.getAlgoritmo().equals(DigestAlgID.SHA_256)) {
							hash = DigestUtils.sha256(lP7MDetector.getContent(lFileDaFirmare));
						} else {
							hash = DigestUtils.sha(lP7MDetector.getContent(lFileDaFirmare));
						}
						String encodedHash = null;
						if (lDocumentConfiguration.getEncoding().equals(DigestEncID.BASE_64)) {
							encodedHash = Base64.encodeBase64String(hash);
						} else if (lDocumentConfiguration.getEncoding().equals(DigestEncID.HEX)) {
							encodedHash = Hex.encodeHexString(hash);
						}
						lFileBean.getInfoFile().setImprontaSbustato(encodedHash);
					} catch (Exception e) {
						mLogger.warn(e);
					}
				}
			}
		}
		if (getSession() != null && getSession().getId() != null && !"".equalsIgnoreCase(getSession().getId())) {
			bean.setjSessionId(getSession().getId());
		}
		
		return bean;
	}

	private boolean firmaEseguitaConFirmaGraficaFileop(List<InfoFirmaGraficaBean> lListaInformazioniFirmaGrafica) {
		if (lListaInformazioniFirmaGrafica != null) {
			for (InfoFirmaGraficaBean lInfoFirmaGraficaBean : lListaInformazioniFirmaGrafica) {
				if (!lInfoFirmaGraficaBean.isFirmaEseguita() && !lInfoFirmaGraficaBean.isFirmaInEsecuzione()) {
					return true;
				}
			}
		}
		return false;
	}
	
	private static byte[] getHashPerFirmaApposizioneFileop(File fileFirmatoEmpty, String nomeCampoFirma) throws Exception {
		PdfReader reader = new PdfReader(fileFirmatoEmpty.getAbsolutePath());
		AcroFields af = reader.getAcroFields();
		PdfDictionary v = af.getSignatureDictionary(nomeCampoFirma);
		if (v == null) {
			throw new DocumentException("No field");
		}
		if (!af.signatureCoversWholeDocument(nomeCampoFirma)) {
			throw new DocumentException("Not the last signature");
		}
		
		PdfArray b = v.getAsArray(PdfName.BYTERANGE);
		long[] gaps = b.asLongArray();
		if (b.size() != 4 || gaps[0] != 0) {
			throw new DocumentException("Single exclusion space supported");
		}
		RandomAccessSource readerSource = reader.getSafeFile().createSourceView();
		InputStream content = new RASInputStream(new RandomAccessSourceFactory().createRanged(readerSource, gaps));
		
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		int nRead = 0;
		int written = 0;
		byte[] buf = new byte[65664];
		while ((nRead = content.read(buf)) > 0) {
			md.update(buf, 0, nRead);
			written += nRead;
		}
		content.close();
		
		byte[] contentDigest = md.digest();
		
		return contentDigest;
	}
	
	public String calcolaImprontaWithoutFileOp(File file, String algoritmo, String encoding) throws Exception {
		try {
			byte[] hash = null;
			if (StringUtils.isBlank(algoritmo)) {
				throw new Exception("Algoritmo per il calcolo dell'impronta non valorizzato");
			} else if (algoritmo.equalsIgnoreCase("SHA-256")) {
				hash = DigestUtils.sha256(new FileInputStream(file));
			} else if (algoritmo.equalsIgnoreCase("SHA-1")) {
				hash = DigestUtils.sha(new FileInputStream(file));
			} else {
				throw new Exception("L'algoritmo " + algoritmo + " non è uno di quelli previsti");
			}
			String encodedHash = null;
			if (StringUtils.isBlank(encoding)) {
				throw new Exception("Encoding per il calcolo dell'impronta non valorizzato");
			} else if (encoding.equalsIgnoreCase("base64")) {
				encodedHash = Base64.encodeBase64String(hash);
			} else if (encoding.equalsIgnoreCase("hex")) {
				encodedHash = Hex.encodeHexString(hash);
			} else {
				throw new Exception("L'encoding " + encoding + " non è uno di quelli previsti");
			}
			return encodedHash;
		} catch (Exception e) {
			throw new Exception("Si è verificato un errore durante il calcolo dell'impronta: " + e.getMessage(), e);
		}
	}


}