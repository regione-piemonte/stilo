/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.security.DigestAlgorithms;
import com.itextpdf.text.pdf.security.ExternalBlankSignatureContainer;
import com.itextpdf.text.pdf.security.ExternalDigest;
import com.itextpdf.text.pdf.security.ExternalSignatureContainer;
import com.itextpdf.text.pdf.security.MakeSignature;

import it.eng.module.foutility.beans.DatiFirmaPades;
import it.eng.module.foutility.beans.RiquadroFirmaGrafica;

public class FirmaPadesUtil {

	public static final Logger log = LogManager.getLogger(FirmaPadesUtil.class);

	public String hashAlgorithm;
	public String FONT_SIZE = "9";
	public String FONT_NAME = "Courier";
	
	public byte[] preparaFile(File fileDaFirmare, File destFile,
			DatiFirmaPades datiFirmaPades, RiquadroFirmaGrafica riquadroFirma,
			X509Certificate userCert, int paginaFirma) throws Exception{

		PdfReader reader = new PdfReader( fileDaFirmare.getAbsolutePath() );

		FileOutputStream pdfOut = new FileOutputStream(destFile);
		PdfStamper stp = null;
		AcroFields af = reader.getAcroFields();
		ArrayList<String> names = af.getSignatureNames();

		if (names.size() == 0) {
			log.debug("Il file non e' firmato");
			stp = PdfStamper.createSignature(reader, pdfOut, PdfWriter.VERSION_1_6, null, true);
		} else {
			log.debug("Il file  e' firmato");
			stp = PdfStamper.createSignature(reader, pdfOut, PdfWriter.VERSION_1_6, null, true);
		}

		PdfSignatureAppearance sap = stp.getSignatureAppearance();

		Calendar cal = GregorianCalendar.getInstance();
		sap.setSignDate(cal);
		
		if( userCert!=null ){
			sap.setCertificate(userCert);
		} else {
		}
		
		sap.setAcro6Layers(true);
		sap.setCertificationLevel(PdfSignatureAppearance.NOT_CERTIFIED);
		if( datiFirmaPades.getReason()!=null)
			sap.setReason(datiFirmaPades.getReason());
		if( datiFirmaPades.getLocation()!=null)
			sap.setLocation(datiFirmaPades.getLocation());

		if( FONT_NAME==null || "".equals(FONT_NAME))
			FONT_NAME="Courier";
		log.debug("uso il font " + FONT_NAME);
		BaseFont layer2Font = null;
		try{
			layer2Font = BaseFont.createFont( FONT_NAME, BaseFont.WINANSI, BaseFont.EMBEDDED);
		} catch(Exception e){
			log.error("Errore nella definizione del font " + FONT_NAME +" - verra' utilizzato il font di defaault", e);
			layer2Font = BaseFont.createFont( "Courier", BaseFont.WINANSI, BaseFont.EMBEDDED);
		}
		if( FONT_SIZE==null || "".equals(FONT_SIZE))
			FONT_SIZE="9";
		log.debug("uso il font size " + FONT_SIZE);
		int fontSize = 9;
		try{
			fontSize = Integer.parseInt(FONT_SIZE);
		} catch(Exception e){
			log.error("Errore nella definizione del font size " + FONT_SIZE +" - verra' utilizzato il font size di defaault", e);
			fontSize = 9;
		}
		sap.setLayer2Font(new Font(layer2Font, fontSize));

		log.debug("riquadroFirma.getCoordinataXVertice() " + riquadroFirma.getCoordinataXVertice());
		log.debug("riquadroFirma.getCoordinataYVertice() " + riquadroFirma.getCoordinataYVertice());
		log.debug("riquadroFirma.getAltezza() " + riquadroFirma.getAltezza());
		log.debug("riquadroFirma.getLarghezza() " + riquadroFirma.getLarghezza());
		
		if( datiFirmaPades.getTesto()!=null ){
			Rectangle ret = new Rectangle(riquadroFirma.getCoordinataXVertice(), riquadroFirma.getCoordinataYVertice(), 
					riquadroFirma.getCoordinataXVertice()+riquadroFirma.getLarghezza(), 
					riquadroFirma.getCoordinataYVertice()+riquadroFirma.getAltezza());
			sap.setVisibleSignature(ret,paginaFirma, datiFirmaPades.getNomeCampoFirma());
	
			sap.setLayer2Text(datiFirmaPades.getTesto());
			File fileImmagine = riquadroFirma.getFileImmagine();
			log.debug("fileImmagine " +fileImmagine);
			if( fileImmagine!=null ){
				Image image = Image.getInstance(fileImmagine.getAbsolutePath());
				sap.setImage(image);
				sap.setImageScale(-1);
				
			}
		}
		sap.setRenderingMode(PdfSignatureAppearance.RenderingMode.DESCRIPTION);
		
		ExternalDigest digest = getDigest();
		
		ExternalSignatureContainer external = new ExternalBlankSignatureContainer(PdfName.ADOBE_PPKLITE, PdfName.ETSI_CADES_DETACHED);
		MakeSignature.signExternalContainer(sap, external, 8192);
 
	    byte[] hash = DigestAlgorithms.digest(sap.getRangeStream(), digest.getMessageDigest(hashAlgorithm));
		    
        pdfOut.close();
        reader.close();
       
        return hash;
	}

	public String getHashAlgorithm() {
		return hashAlgorithm;
	}

	public void setHashAlgorithm(String hashAlgorithm) {
		this.hashAlgorithm = hashAlgorithm;
	}
	
	public String getFONT_SIZE() {
		return FONT_SIZE;
	}

	public String getFONT_NAME() {
		return FONT_NAME;
	}

	public void setFONT_NAME(String fONT_NAME) {
		FONT_NAME = fONT_NAME;
	}

	public void setFONT_SIZE(String fONT_SIZE) {
		FONT_SIZE = fONT_SIZE;
	}

	public static ExternalDigest getDigest() {
		return new ExternalDigest() {
			public MessageDigest getMessageDigest(String hashAlgorithm)
					throws GeneralSecurityException {
				return DigestAlgorithms.getMessageDigest(hashAlgorithm, null);
			}
		};
	}

	public static void completaFile(File fileDaFirmare, File fileFirmato, String fieldname, byte[] hashSigned) throws IOException, DocumentException, GeneralSecurityException {

		PdfReader reader = new PdfReader(fileDaFirmare.getAbsolutePath());
		FileOutputStream os = new FileOutputStream(fileFirmato);
		ExternalSignatureContainer external = new MyExternalSignatureContainer(hashSigned);


		MakeSignature.signDeferred(reader, fieldname, os, external);

		reader.close();
		os.close();
	}
	
	
	static class MyExternalSignatureContainer implements ExternalSignatureContainer {
        protected byte[] sig;
        public MyExternalSignatureContainer(byte[] sig) {
            this.sig = sig;
        }
        public byte[] sign(InputStream is) {
            return sig;
        }

        @Override
        public void modifySigningDictionary(PdfDictionary signDic) {
        }
    }
}
