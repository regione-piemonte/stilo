/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignature;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;

import it.eng.auriga.ui.module.layout.server.conversionePdf.datasource.bean.RettangoloFirmaPadesBean;
import it.eng.fileOperation.clientws.DigestAlgID;
import it.eng.fileOperation.clientws.DigestEncID;
import it.eng.utility.ui.sign.CertificationLevel;
import it.eng.utility.ui.sign.FileElaborate;
import it.eng.utility.ui.sign.HashAlgorithm;

public class PdfSignatureUtils {

	private PdfSignatureAppearance appearance;
	private byte[] hash;
	private ByteArrayOutputStream fout;

	/**
	 * Restituisce la stringa che rappresenta l'impronta codificata del pdf
	 * 
	 * @param fileToSign
	 * @param algId
	 * @param encId
	 * @param firmatario
	 * @return
	 * @throws Exception
	 */
	public void calcolaHashPdf(FileElaborate fileToSign, DigestAlgID algId, DigestEncID encId, RettangoloFirmaPadesBean rettangoloFirmaPades) throws Exception {

		// logger.info("Calcolo l'hash del file " + fileElaborate.getUnsigned());
		InputStream streamDaFirmare = new FileInputStream(fileToSign.getUnsigned());
		PdfReader pdfReader = new PdfReader(streamDaFirmare);

		String firmatario = rettangoloFirmaPades.getFirmatario();

		fout = new ByteArrayOutputStream();
		HashAlgorithm algorithm;
		if (algId.equals(DigestAlgID.SHA_256))
			algorithm = HashAlgorithm.SHA256;
		else
			algorithm = HashAlgorithm.SHA1;

		// int margine = 10;
		// int rectWidth = 100;
		// int rectHeight = 50;
		// int distanzaFirme = 20;

		PdfStamper stamper = null;
		AcroFields af = pdfReader.getAcroFields();
		ArrayList<String> names = af.getSignatureNames();

		if (names.size() == 0) {
			stamper = PdfStamper.createSignature(pdfReader, fout, algorithm.getPdfVersion());
		} else {
			stamper = PdfStamper.createSignature(pdfReader, fout, algorithm.getPdfVersion(), null, true);
		}
		appearance = stamper.getSignatureAppearance();

		// Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		// font.setSize(55);
		//
		// appearance.setLayer2Font(font);

		Rectangle rettangoloDiFirma = calcolaRettangoloFirma(pdfReader, rettangoloFirmaPades);
		rettangoloDiFirma.disableBorderSide(Rectangle.LEFT);
		rettangoloDiFirma.disableBorderSide(Rectangle.RIGHT);
		rettangoloDiFirma.disableBorderSide(Rectangle.TOP);
		rettangoloDiFirma.disableBorderSide(Rectangle.BOTTOM);

		// Posiziono la firma nell'ultima pagina del documento
		appearance.setVisibleSignature(rettangoloDiFirma, pdfReader.getNumberOfPages(), null);

		appearance.setCertificationLevel(CertificationLevel.NOT_CERTIFIED.getLevel());

		//SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YYYY - HH:mm:ss");
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YYYY");
		if (firmatario != null)
			appearance.setLayer2Text("Firmato digitalmente da " + firmatario + " in data " + formatter.format(new Date()));
		else
			appearance.setLayer2Text("Firmato digitalmente in data " + formatter.format(new Date()));

		PdfSignature dic = new PdfSignature(PdfName.ADOBE_PPKLITE, PdfName.ADBE_PKCS7_DETACHED);
		appearance.setCryptoDictionary(dic);

		HashMap<PdfName, Integer> exc = new HashMap<PdfName, Integer>();
		int contentEstimated = (int) 15000L;
		exc.put(PdfName.CONTENTS, new Integer(contentEstimated * 2 + 2));
		appearance.preClose(exc);

		hash = null;

		if (algId.equals(DigestAlgID.SHA_256))
			hash = DigestUtils.sha256(appearance.getRangeStream());
		else
			hash = DigestUtils.sha(appearance.getRangeStream());

		String encodedHash = null;
		if (encId.equals(DigestEncID.BASE_64)) {
			encodedHash = Base64.encodeBase64String(hash);
		} else if (encId.equals(DigestEncID.HEX)) {
			encodedHash = Hex.encodeHexString(hash);
		}
		fileToSign.setPdfsignature(appearance);
		fileToSign.setHash(hash);
		fileToSign.setEncodedHash(encodedHash);
		fileToSign.setOutStream(fout);

	}

	public static Rectangle calcolaRettangoloFirma(PdfReader pdfReader, RettangoloFirmaPadesBean rettangoloFirmaPades) {

		PosizioneFirma posizioneFirma = rettangoloFirmaPades.getPosizioneFirma();
		int marginePaginaOrizzontale = rettangoloFirmaPades.getMarginePaginaOrizzontale();
		int marginePaginaVerticale = rettangoloFirmaPades.getMarginePaginaVerticale();
		int distanzaFirme = rettangoloFirmaPades.getDistanzaFirme();
		int rectWidth = rettangoloFirmaPades.getRectWidth();
		int rectHeight = rettangoloFirmaPades.getRectHeight();

		AcroFields af = pdfReader.getAcroFields();
		Rectangle rect = pdfReader.getPageSize(1);
		float wPage = rect.getWidth();
		float hPage = rect.getHeight();
		int pageRotation = pdfReader.getPageRotation(1);

		/*
		 * (llx,lly) sono le coordinate dell'angolo in basso a sinistra del rettangolo
		 * 
		 * (urx, ury) sono le coordinate dell'angolo in alto a destra del rettangolo
		 */
		float llx = 0; // lower left x
		float lly = 0; // lower left y
		float urx = 0; // upper right x
		float ury = 0; // upper right y

		/*
		 * Attenzione: quando il documento pdf proviene da una scansione, anche se visualizzato in verticale è i realtà orientato orizzontalmente con un flag
		 * che indica che deve essere ruotato.
		 * 
		 * Per questo motivo le dimensioni wPage e hPage sono invertite nelle due situazioni in base all'orientamento del documento
		 */

		int numeroFirmePresenti = af.getSignatureNames().size();
		int spazioVerticaleOccupatoDaFirmePresenti = (numeroFirmePresenti) * (rectHeight + distanzaFirme);

		if (posizioneFirma.equals(PosizioneFirma.BASSO_DESTRA)) {
			if (pageRotation == 0 || pageRotation == 180) {
				llx = wPage - marginePaginaOrizzontale - rectWidth;
				lly = marginePaginaVerticale - spazioVerticaleOccupatoDaFirmePresenti;
				urx = wPage - marginePaginaOrizzontale;
				ury = lly + rectHeight;
			} else if (pageRotation == 90 || pageRotation == 270) {
				llx = hPage - marginePaginaOrizzontale - rectWidth;
				lly = marginePaginaVerticale - spazioVerticaleOccupatoDaFirmePresenti;
				urx = hPage - marginePaginaOrizzontale;
				ury = lly + rectHeight;
			}
		} else if (posizioneFirma.equals(PosizioneFirma.BASSO_SINISTRA)) {
			if (pageRotation == 0 || pageRotation == 180) {
				llx = marginePaginaOrizzontale;
				lly = marginePaginaVerticale - spazioVerticaleOccupatoDaFirmePresenti;
				urx = marginePaginaOrizzontale + rectWidth;
				ury = lly + rectHeight;
			} else if (pageRotation == 90 || pageRotation == 270) {
				llx = marginePaginaOrizzontale;
				lly = marginePaginaVerticale - spazioVerticaleOccupatoDaFirmePresenti;
				urx = marginePaginaOrizzontale + rectWidth;
				ury = lly + rectHeight;
			}
		} else if (posizioneFirma.equals(PosizioneFirma.ALTO_SINISTRA)) {
			if (pageRotation == 0 || pageRotation == 180) {
				llx = marginePaginaOrizzontale;
				lly = hPage - marginePaginaVerticale - rectHeight - spazioVerticaleOccupatoDaFirmePresenti;
				urx = marginePaginaOrizzontale + rectWidth;
				ury = lly + rectHeight;
			} else if (pageRotation == 90 || pageRotation == 270) {
				llx = marginePaginaOrizzontale;
				lly = wPage - marginePaginaVerticale - rectHeight - spazioVerticaleOccupatoDaFirmePresenti;
				urx = marginePaginaOrizzontale + rectWidth;
				ury = lly + rectHeight;
			}
		} else if (posizioneFirma.equals(PosizioneFirma.ALTO_DESTRA)) {
			if (pageRotation == 0 || pageRotation == 180) {
				llx = wPage - marginePaginaOrizzontale - rectWidth;
				lly = hPage - marginePaginaVerticale - rectHeight - spazioVerticaleOccupatoDaFirmePresenti;
				urx = wPage - marginePaginaOrizzontale;
				ury = lly + rectHeight;
			} else if (pageRotation == 90 || pageRotation == 270) {
				llx = hPage - marginePaginaOrizzontale - rectWidth;
				lly = wPage - marginePaginaVerticale - rectHeight - spazioVerticaleOccupatoDaFirmePresenti;
				urx = hPage - marginePaginaOrizzontale;
				ury = lly + rectHeight;
			}
		}

		Rectangle rettangoloDiFirma = new Rectangle(llx, lly, urx, ury);
		return rettangoloDiFirma;
	}

	public PdfSignatureAppearance getAppearance() {
		return appearance;
	}

	public void setAppearance(PdfSignatureAppearance appearance) {
		this.appearance = appearance;
	}

	public byte[] getHash() {
		return hash;
	}

	public void setHash(byte[] hash) {
		this.hash = hash;
	}

	public ByteArrayOutputStream getFout() {
		return fout;
	}

	public void setFout(ByteArrayOutputStream fout) {
		this.fout = fout;
	}

}
