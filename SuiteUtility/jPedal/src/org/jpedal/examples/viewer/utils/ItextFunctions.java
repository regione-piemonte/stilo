/**
 * ===========================================
 * Java Pdf Extraction Decoding Access Library
 * ===========================================
 *
 * Project Info:  http://www.jpedal.org
 * (C) Copyright 1997-2008, IDRsolutions and Contributors.
 *
 * Empty method stubs for building JPedal from source without iText.
 *
 * 	This file is part of JPedal
 *
    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA


 *
 * ---------------
 * ItextFunctions.java
 * ---------------
 */
package org.jpedal.examples.viewer.utils;


import it.eng.fileOperation.clientws.FileOperationResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;

import net.sf.jsignpdf.types.HashAlgorithm;
import net.sf.jsignpdf.types.RenderMode;
import net.sf.jsignpdf.types.ServerAuthentication;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.tsp.TimeStampTokenInfo;
import org.jpedal.PdfDecoder;
import org.jpedal.examples.viewer.config.ConfigConstants;
import org.jpedal.examples.viewer.config.PreferenceManager;
import org.jpedal.examples.viewer.gui.SwingGUI;
import org.jpedal.examples.viewer.gui.popups.AddHeaderFooterToPDFPages;
import org.jpedal.examples.viewer.gui.popups.CropPDFPages;
import org.jpedal.examples.viewer.gui.popups.DeletePDFPages;
import org.jpedal.examples.viewer.gui.popups.EncryptPDFDocument;
import org.jpedal.examples.viewer.gui.popups.ExtractPDFPagesNup;
import org.jpedal.examples.viewer.gui.popups.InsertBlankPDFPage;
import org.jpedal.examples.viewer.gui.popups.RotatePDFPages;
import org.jpedal.examples.viewer.gui.popups.SavePDF;
import org.jpedal.examples.viewer.gui.popups.StampImageToPDFPages;
import org.jpedal.examples.viewer.gui.popups.StampTextToPDFPages;
import org.jpedal.examples.viewer.gui.popups.firma.Factory;
import org.jpedal.examples.viewer.gui.popups.firma.PasswordHandler;
import org.jpedal.examples.viewer.objects.SignData;
import org.jpedal.objects.PdfPageData;
import org.jpedal.utils.LogWriter;
import org.jpedal.utils.MessageConstants;
import org.jpedal.utils.Messages;

import sun.security.pkcs11.SunPKCS11;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfDate;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignature;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfString;
import com.itextpdf.text.pdf.security.CertificateUtil;
import com.itextpdf.text.pdf.security.DigestAlgorithms;
import com.itextpdf.text.pdf.security.ExternalDigest;
import com.itextpdf.text.pdf.security.MakeSignature.CryptoStandard;
import com.itextpdf.text.pdf.security.OcspClientBouncyCastle;
import com.itextpdf.text.pdf.security.PdfPKCS7;
import com.itextpdf.text.pdf.security.TSAClientBouncyCastle;
import com.itextpdf.text.pdf.security.TSAInfoBouncyCastle;



/** central location to place external code using itext library */
public class ItextFunctions {

	public SwingGUI currentGui;
	public String selectedFile;
	public final static boolean IS_DUMMY = true;

	public final static int ROTATECLOCKWISE = 0;
	public final static int ROTATECOUNTERCLOCKWISE = 1;
	public final static int ROTATE180 = 2;

	public final static int ORDER_ACROSS = 3;
	public final static int ORDER_DOWN = 4;
	public final static int ORDER_STACK = 5;

	public final static int REPEAT_NONE = 6;
	public final static int REPEAT_AUTO = 7;
	public final static int REPEAT_SPECIFIED = 8;

	public static final int ALLOW_PRINTING = -1;// PdfWriter.ALLOW_PRINTING;
	public static final int ALLOW_MODIFY_CONTENTS = -1;//PdfWriter.ALLOW_MODIFY_CONTENTS;
	public static final int ALLOW_COPY = -1;//PdfWriter.ALLOW_COPY;
	public static final int ALLOW_MODIFY_ANNOTATIONS = -1;//PdfWriter.ALLOW_MODIFY_ANNOTATIONS;
	public static final int ALLOW_FILL_IN = -1;//PdfWriter.ALLOW_FILL_IN;
	public static final int ALLOW_SCREENREADERS = -1;//PdfWriter.ALLOW_SCREENREADERS;
	public static final int ALLOW_ASSEMBLY = -1;//PdfWriter.ALLOW_ASSEMBLY;
	public static final int ALLOW_DEGRADED_PRINTING = -1;//PdfWriter.ALLOW_DEGRADED_PRINTING;


	public ItextFunctions(SwingGUI currentGUI, String selectedFile,	PdfDecoder decode_pdf) {
		this.selectedFile = selectedFile;
		this.currentGui = currentGUI;
	}

	//<link><a name="saveform" />
	/** uses itext to save out form data with any changes user has made */
	public static void saveFormsData(String file) {
		throw new java.lang.AssertionError("Itext not on classpath");
	}

	public static void extractPagesToNewPDF(SavePDF current_selection) {
		throw new java.lang.AssertionError("Itext not on classpath");
	}

	public static void nup(int pageCount, PdfPageData currentPageData, ExtractPDFPagesNup extractPage){
		throw new java.lang.AssertionError("Itext not on classpath");
	}

	public static void handouts(String file) {
		throw new java.lang.AssertionError("Itext not on classpath");
	}

	public static void add(int pageCount, PdfPageData currentPageData,
			InsertBlankPDFPage addPage) {
		throw new java.lang.AssertionError("Itext not on classpath");
	}

	public FileOperationResponse verificaFirme(SignData signData) throws Exception/**/ { 
		
		PreferenceManager preferenceManager = currentGui.getCommonValues().getPreferenceManager();
		
		FileOperationResponse result = null;
		
		boolean caCheck=true;
		boolean crlCheck=true;
		try {
			caCheck = preferenceManager.getConfiguration().getBoolean( ConfigConstants.FIRMA_PROPERTY_ENABLECACHECK );
			LogWriter.writeLog( "Proprietà "+ ConfigConstants.FIRMA_PROPERTY_ENABLECACHECK  + ": " + caCheck );
			crlCheck = preferenceManager.getConfiguration().getBoolean( ConfigConstants.FIRMA_PROPERTY_ENABLECRLCHECK );
			LogWriter.writeLog( "Proprietà "+ ConfigConstants.FIRMA_PROPERTY_ENABLECRLCHECK  + ": " + crlCheck );
		} catch (Exception e) {
			LogWriter.writeLog("Errore nel recupero delle proprietà " + ConfigConstants.FIRMA_PROPERTY_ENABLECACHECK 
					+ " e " + ConfigConstants.FIRMA_PROPERTY_ENABLECRLCHECK);
		}
		
		Date dataRif = null;
		try {
			String dataRifString = preferenceManager.getConfiguration().getString( ConfigConstants.FIRMA_PROPERTY_DATARIFERIMENTOVERIFICA );
			LogWriter.writeLog( "dataRifString: " + dataRifString );
			String dataRifFormato = preferenceManager.getConfiguration().getString( ConfigConstants.FIRMA_PROPERTY_DATARIFERIMENTOVERIFICA_FORMATO );
			LogWriter.writeLog( "dataRifFormato: " + dataRifFormato );
			if( dataRifFormato!=null && dataRifString!=null ){
				SimpleDateFormat sdf = new SimpleDateFormat( dataRifFormato );
				dataRif = sdf.parse( dataRifString );
			}
			
		} catch (Exception e) {
			LogWriter.writeLog("Errore nel recupero delle proprietà " + ConfigConstants.FIRMA_PROPERTY_DATARIFERIMENTOVERIFICA, e );
		}
				
		try {
			if( currentGui.getCommonValues().getResponseVerificaFirme()==null){
				result = WSClientUtils.verificaFirme( preferenceManager, caCheck, crlCheck, dataRif, new File(selectedFile) );
				currentGui.getCommonValues().setResponseVerificaFirme( result );
			} else {
				LogWriter.writeLog("Considero la response di verifica firme in memoria");
				result = currentGui.getCommonValues().getResponseVerificaFirme();
			}
			
		} catch (Exception e) {
			LogWriter.writeLog("Errore nella chiamata al metodo di verifica firma, la verifica non può essere effettuata ", e );
		}
				
		return result;
	}
	
	public boolean verificaVersionePdf(SignData signData) throws Exception/**/ { 
		
		PdfReader reader;
		try {
			reader = new PdfReader( selectedFile );
		} catch (IOException e) {
			LogWriter.writeLog("Errore", e);
			throw new Exception("Errore nei file di input o output");
		}
		
		final HashAlgorithm hashAlgorithm = signData.getHashAlgorithm();
		LogWriter.writeLog("hashAlgorithm " + hashAlgorithm);

		LogWriter.writeLog("reader.getPdfVersion() " + reader.getPdfVersion());
		LogWriter.writeLog("hashAlgorithm.getPdfVersion() " + hashAlgorithm.getPdfVersion());
		if (reader.getPdfVersion() < hashAlgorithm.getPdfVersion() && signData.isAppendMode()) {
			//this covers also problems with visible signatures (embedded fonts) in PDF 1.2, because the minimal version
			//for hash algorithms is 1.3 (for SHA1)
			//if we are in append mode and version should be updated then return false (not possible)
			LogWriter.writeLog( MessageConstants.MSG_FIRMAMARCA_INVALIDPDFVERSION );
			throw new Exception( Messages.getMessage( MessageConstants.MSG_FIRMAMARCA_INVALIDPDFVERSION ) );
		}
		return true;
	}
	
	public boolean sign(SignData signData) throws Exception/**/ { 
		LogWriter.writeLog("Inizio metodo Firma (ed eventualmente marca) ");

		PreferenceManager preferenceManager = currentGui.getCommonValues().getPreferenceManager();
		
		FileOutputStream fout = null;
		PrivateKey privateKey = null;
		Certificate[] chain = null;

		char[] pin = signData.getPin();
		SunPKCS11 provider =null;
		//verifico il pin e recupero la chiave
		try {
			provider = Factory.getProvider(pin, signData.getSlot(), preferenceManager);
			provider.login(new Subject(), new PasswordHandler(pin));
		} catch (LoginException e) {
			LogWriter.writeLog("Errore", e);
			throw new Exception( Messages.getMessage( MessageConstants.MSG_FIRMAMARCA_INVALIDPIN ) );
		}
			
		KeyStore keyStore = null;
		try {
			keyStore = KeyStore.getInstance("PKCS11",provider);
			keyStore.load(null,null);	
		} catch (KeyStoreException e) {
			LogWriter.writeLog("Errore", e);
			throw new Exception( Messages.getMessage( MessageConstants.MSG_FIRMAMARCA_ERROR_GENERIC ) );
		} catch (NoSuchAlgorithmException e) {
			LogWriter.writeLog("Errore", e);
			throw new Exception( Messages.getMessage( MessageConstants.MSG_FIRMAMARCA_ERROR_GENERIC ) );
		} catch (CertificateException e) {
			LogWriter.writeLog("Errore", e);
			throw new Exception( Messages.getMessage( MessageConstants.MSG_FIRMAMARCA_ERROR_GENERIC ) );
		} catch (IOException e) {
			LogWriter.writeLog("Errore", e);
			throw new Exception( Messages.getMessage( MessageConstants.MSG_FIRMAMARCA_ERROR_GENERIC ) );
		}
		
		List<Certificate> listaCertificates = new  ArrayList<Certificate>();

		//recupero il certificato di firma e effettuo gli eventuali controlli
		Enumeration enumeration;
		try {
			enumeration = keyStore.aliases();
			while(enumeration.hasMoreElements()){
				String alias = enumeration.nextElement().toString();
				LogWriter.writeLog("ALIAS:"+alias);
				privateKey = (PrivateKey)keyStore.getKey(alias, null); 
				LogWriter.writeLog("PRIVATE KEY:"+privateKey);
				X509Certificate certificate=(X509Certificate)keyStore.getCertificate(alias);
				//controllo se  e' un certificato di firma
				boolean[] usage = certificate.getKeyUsage();
				LogWriter.writeLog("KEY USAGE:"+usage[1]);
				String emulationString;
				boolean emulation = false;
				try {
					emulationString = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_EMULATION );
					emulation = Boolean.valueOf( emulationString );
					LogWriter.writeLog("Proprietà " + ConfigConstants.PROPERTY_EMULATION + "=" + emulationString );
				} catch (Exception e) {
					LogWriter.writeLog("Errore", e);
				}
				
				LogWriter.writeLog("usage[1] " + usage[1]);
				if(usage[1] /*|| emulation*/){
					listaCertificates.add( certificate );
				}
				
				try {
					checkCertificate( certificate, preferenceManager);
				} catch (Exception e) {
					LogWriter.writeLog("Errore", e );
					throw e;
				}
				
				ValidationInfos result = null;
				try{
					result = WSClientUtils.checkCertificate( preferenceManager, certificate, signData.isCheckCa(), signData.isCheckCrl() );
				} catch (Exception e){
					LogWriter.writeLog("Errore nella verifica del certificato. La verifica non è stata effettuata", e);
				}
				if( result!=null ){
					if( !result.isValid() ){
						LogWriter.writeLog("Controlli non superati " + result.getErrorsString());
						throw new Exception(  Messages.getMessage( MessageConstants.MSG_FIRMAMARCA_INVALIDCERTIFICATE ) + result.getErrorsString() );
					} else {
						LogWriter.writeLog("Controlli superati ");
					}
				}
			} 
			
			chain = (Certificate[])listaCertificates.toArray(new Certificate[listaCertificates.size()]);

			LogWriter.writeLog( "privateKey " + privateKey );
			if (ArrayUtils.isEmpty(chain)) {
				LogWriter.writeLog("Nessun certificato trovato");
				throw new Exception( Messages.getMessage( MessageConstants.MSG_FIRMAMARCA_NOCERTIFICATE ) );
			} 
		} catch (KeyStoreException e) {
			LogWriter.writeLog("Errore", e);
			throw new Exception( Messages.getMessage( MessageConstants.MSG_FIRMAMARCA_ERROR_GENERIC ) );
		}
		
		//carico il pdf e predispongo il file di output
		PdfReader reader;
		final String outFile;
		try {
			reader = new PdfReader( selectedFile );
			outFile = signData.getOutputFilePath();
			LogWriter.writeLog("Path del file firmato " + outFile, false);
			fout = new FileOutputStream(outFile);
		} catch (IOException e) {
			LogWriter.writeLog("Errore", e);
			throw new Exception("Errore nei file di input o output");
		}

		//recupero l'algoritmo di hsh richiesto
		final HashAlgorithm hashAlgorithm = signData.getHashAlgorithm();
		LogWriter.writeLog("Algoritmo di firma=" + hashAlgorithm);

		char tmpPdfVersion = '\0'; // default version - the same as input
		LogWriter.writeLog("Versione del pdf reader=" + reader.getPdfVersion());
		LogWriter.writeLog("Versione minima richiesta dall'algoritmo=" + hashAlgorithm.getPdfVersion());
		if (reader.getPdfVersion() < hashAlgorithm.getPdfVersion() && signData.isAppendMode()) {
			signData.setAppend(false);
			tmpPdfVersion = hashAlgorithm.getPdfVersion();
		}
		LogWriter.writeLog("Versione pdf che verrà utilizzata in fase di firma=" + tmpPdfVersion);

		PdfStamper stamper;
		try {
			LogWriter.writeLog("Modalita' append? " + signData.isAppendMode() );
			stamper = PdfStamper.createSignature(reader, fout, tmpPdfVersion, null, signData.isAppendMode());
			if ( !signData.isAppendMode() ) {
				// we are not in append mode, let's remove existing signatures
				// (otherwise we're getting to troubles)
				final AcroFields acroFields = stamper.getAcroFields();
				@SuppressWarnings("unchecked")
				final List<String> sigNames = acroFields.getSignatureNames();
				for (String sigName : sigNames) {
					acroFields.removeField(sigName);
				}
			}
		} catch (DocumentException e) {
			LogWriter.writeLog("Errore", e);
			LogWriter.writeLog("Errore " + e.getMessage() );
			throw new Exception("Errore, " + e.getMessage() );
		} catch (IOException e) {
			LogWriter.writeLog("Errore", e);
			LogWriter.writeLog("Errore " + e.getMessage() );
			throw new Exception("Errore, " + e.getMessage() );
		}

		//			if (options.isAdvanced() && options.getPdfEncryption() != PDFEncryption.NONE) {
		//				LogWriter.writeLog("console.setEncryption");
		//				final int tmpRight = options.getRightPrinting().getRight()
		//						| (options.isRightCopy() ? PdfWriter.ALLOW_COPY : 0)
		//						| (options.isRightAssembly() ? PdfWriter.ALLOW_ASSEMBLY : 0)
		//						| (options.isRightFillIn() ? PdfWriter.ALLOW_FILL_IN : 0)
		//						| (options.isRightScreanReaders() ? PdfWriter.ALLOW_SCREENREADERS : 0)
		//						| (options.isRightModifyAnnotations() ? PdfWriter.ALLOW_MODIFY_ANNOTATIONS : 0)
		//						| (options.isRightModifyContents() ? PdfWriter.ALLOW_MODIFY_CONTENTS : 0);
		//				switch (options.getPdfEncryption()) {
		//				case PASSWORD:
		//					stp.setEncryption(true, options.getPdfUserPwdStr(), options.getPdfOwnerPwdStrX(), tmpRight);
		//					break;
		//				case CERTIFICATE:
		//					final X509Certificate encCert = KeyStoreUtils.loadCertificate(options.getPdfEncryptionCertFile());
		//					if (encCert == null) {
		//						LogWriter.writeLog("console.pdfEncError.wrongCertificateFile" +
		//								StringUtils.defaultString(options.getPdfEncryptionCertFile()));
		//						return;
		//					}
		//					if (!KeyStoreUtils.isEncryptionSupported(encCert)) {
		//						LogWriter.writeLog("console.pdfEncError.cantUseCertificate" + encCert.getSubjectDN().getName());
		//						return;
		//					}
		//					stp.setEncryption(new Certificate[] { encCert }, new int[] { tmpRight },
		//							PdfWriter.ENCRYPTION_AES_128);
		//					break;
		//				default:
		//					LogWriter.writeLog("console.unsupportedEncryptionType");
		//					return;
		//				}
		//			}

		final PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
		appearance.setCertificate( chain[0]);
		if (StringUtils.isNotEmpty(signData.getReason())) {
			LogWriter.writeLog("Imposto la proprietà Reason:" + signData.getReason());
			appearance.setReason(signData.getReason());
		}
		if (StringUtils.isNotEmpty(signData.getLocation())) {
			LogWriter.writeLog("Imposto la proprietà:" + signData.getLocation());
			appearance.setLocation(signData.getLocation());
		}
		if (StringUtils.isNotEmpty(signData.getContact())) {
			LogWriter.writeLog("Imposto la proprietà contact:" +  signData.getContact());
			appearance.setContact(signData.getContact());
		}
		LogWriter.writeLog("Imposto il livello di certificazione a " + signData.getCertifyMode().getLevel());
		appearance.setCertificationLevel( signData.getCertifyMode().getLevel() );

		//aggiungo la firma visibile
		try {
			if (signData.isVisibleSignature()) {
				// visible signature is enabled
				LogWriter.writeLog("Viene effettuata la firma visibile");
				//appearance.setAcro6Layers(signData.isAcro6Layers());

				final String tmpImgPath = signData.getImgPath();
				if (tmpImgPath != null && !tmpImgPath.equalsIgnoreCase("")) {
					try{
						Image img = Image.getInstance(tmpImgPath);
						appearance.setSignatureGraphic(img);
					} catch(Exception e){
						
					}
				}
				final String tmpBgImgPath = signData.getBgImgPath();
				if (tmpBgImgPath != null && !tmpBgImgPath.equalsIgnoreCase("")) {
					try{
						final Image img = Image.getInstance(tmpBgImgPath);
						appearance.setImage(img);
					} catch(Exception e){
						
					}
				}
				if( signData.getBgImgScale()!=0 ){
					LogWriter.writeLog("Imposto il fattore di scala dell'immagine di sfondo " + signData.getBgImgScale()/100);
					appearance.setImageScale(signData.getBgImgScale()/100);
				}
				if (signData.getL2Text() != null) {
					LogWriter.writeLog("Imposto il testo della firma");
					appearance.setLayer2Text(signData.getL2Text());
				} 
				if (signData.getL2TextFontSize() > 0) {
					BaseFont font = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
					appearance.setLayer2Font(new Font(font, signData.getL2TextFontSize()));
				}
				if( signData.getL4Text()!=null ) {
					appearance.setLayer4Text(signData.getL4Text());
				}
				RenderMode renderMode = signData.getRenderMode();
				if (renderMode == RenderMode.GRAPHIC_AND_DESCRIPTION && appearance.getSignatureGraphic() == null) {
					LogWriter.writeLog("Render mode of visible signature is set to GRAPHIC_AND_DESCRIPTION, but no image is loaded. Fallback to DESCRIPTION_ONLY.");
					renderMode = RenderMode.DESCRIPTION_ONLY;
				}
				LogWriter.writeLog("Imposto la modalita' di renderizzazione: " + renderMode.getRender() );
				appearance.setRenderingMode( renderMode.getRender());
				Rectangle rett = new Rectangle(signData.getRectangle()[0], signData.getRectangle()[1], signData.getRectangle()[2],
						signData.getRectangle()[3]);
				appearance.setVisibleSignature( rett, signData.getSignPage(), null);
			}
		} catch (BadElementException e) {
			LogWriter.writeLog("Errore", e);
			throw new Exception("Errore");
		} catch (MalformedURLException e) {
			LogWriter.writeLog("Errore", e);
			throw new Exception("Errore");
		} catch (IOException e) {
			LogWriter.writeLog("Errore", e);
			throw new Exception("Errore");
		} catch (DocumentException e) {
			LogWriter.writeLog("Errore", e);
			throw new Exception("Errore");
		}

		final PdfSignature pdfSign = new PdfSignature(PdfName.ADOBE_PPKLITE, PdfName.ADBE_PKCS7_DETACHED);
		if (!StringUtils.isEmpty(signData.getReason())) {
			pdfSign.setReason(appearance.getReason());
		}
		if (!StringUtils.isEmpty(signData.getLocation())) {
			pdfSign.setLocation(appearance.getLocation());
		}
		if (!StringUtils.isEmpty(signData.getContact())) {
			pdfSign.setContact(appearance.getContact());
		}
		pdfSign.setDate(new PdfDate(appearance.getSignDate()));
		appearance.setCryptoDictionary(pdfSign);

		HashMap<PdfName, Integer> exc = new HashMap<PdfName, Integer>();

		int contentEstimated = (int) 15000L;
//		if( signData.isCrlEnabled() ){
//			contentEstimated = (int) (15000L + 2L * CRLInfo.getByteCount(chain));
//		}
		exc.put(PdfName.CONTENTS, new Integer(contentEstimated * 2 + 2));
		try {
			appearance.preClose(exc);
		} catch (IOException e) {
			LogWriter.writeLog("Errore", e);
			throw new Exception("Errore");
		} catch (DocumentException e) {
			LogWriter.writeLog("Errore", e);
			throw new Exception("Errore");
		}

		PdfPKCS7 sgn;
		byte[] hash;
		try {
			ExternalDigest externalDigest = new ExternalDigest() {
				public MessageDigest getMessageDigest(String hashAlgorithm) 	throws GeneralSecurityException {
					return DigestAlgorithms.getMessageDigest(hashAlgorithm, null);
				}
			};

			//sgn = new PdfPKCS7(privateKey, chain, hashAlgorithm.getAlgorithmName(), null, externalDigest, false);
			sgn = new PdfPKCS7(privateKey, chain, hashAlgorithm.getAlgorithmName(), provider.getName(), externalDigest, false);
			//sgn = new PdfPKCS7(privateKey, chain, hashAlgorithm.getAlgorithmName(), provider, externalDigest, false);

			//			final MessageDigest messageDigest = MessageDigest.getInstance(hashAlgorithm.getAlgorithmName());
			//			byte buf[] = new byte[8192];
			//			int n;
			//			InputStream data = appearance.getRangeStream();
			//			while ((n = data.read(buf)) > 0) {
			//				messageDigest.update(buf, 0, n);
			//			}
			//			hash = messageDigest.digest();
			hash = DigestAlgorithms.digest(appearance.getRangeStream(),	externalDigest.getMessageDigest(hashAlgorithm.getAlgorithmName() ));

		} catch (InvalidKeyException e) {
			LogWriter.writeLog("Errore", e);
			throw new Exception("Errore");
		} catch (NoSuchProviderException e) {
			LogWriter.writeLog("Errore", e);
			throw new Exception("Errore");
		} catch (NoSuchAlgorithmException e) {
			LogWriter.writeLog("Errore", e);
			throw new Exception("Errore");
		} catch (IOException e) {
			LogWriter.writeLog("Errore", e);
			throw new Exception("Errore");
		}

		Calendar cal = Calendar.getInstance();

		byte[] ocsp = null;
		try {
			if (signData.isOcspEnabled() && chain.length >= 2) {
				//String url = PdfPKCS7.getOCSPURL((X509Certificate) chain[0]);
				String url = CertificateUtil.getOCSPURL((X509Certificate) chain[0]);

				if (StringUtils.isEmpty(url)) {
					url = signData.getOcspUrl();
				}
				if (!StringUtils.isEmpty(url)) {
					final OcspClientBouncyCastle ocspClient = new OcspClientBouncyCastle();

					initProxyConnection(signData.getProxyHost(), signData.getProxyPort(), signData.getProxyUser(), signData.getProxyPassword() );

					//ocspClient.setProxy(Proxy.NO_PROXY);
					ocsp = ocspClient.getEncoded((X509Certificate) chain[0], (X509Certificate) chain[1], url);
				}
			}
			byte sh[] = sgn.getAuthenticatedAttributeBytes(hash, cal, null, null, CryptoStandard.CADES );
			//byte sh[] = sgn.getAuthenticatedAttributeBytes(hash, cal, ocsp, null, CryptoStandard.CMS );

			sgn.update(sh, 0, sh.length);
		} catch (SignatureException e) {
			LogWriter.writeLog("Errore", e);
			throw new Exception("Errore");
		}

		TSAClientBouncyCastle tsc = null;
		LogWriter.writeLog("Aggiungo la marca?" + signData.isTimestamp());
		if (signData.isTimestamp() && !StringUtils.isEmpty(signData.getTsaUrl())) {
			if ( signData.isTsaServerAuthn() ) {
				tsc = new TSAClientBouncyCastle(signData.getTsaUrl(), signData.getTsaUser(), signData.getTsaPasswd());
			} else {
				tsc = new TSAClientBouncyCastle(signData.getTsaUrl());
			}
			tsc.setTSAInfo(
				new TSAInfoBouncyCastle() {
					public void inspectTimeStampTokenInfo(TimeStampTokenInfo info) {
						LogWriter.writeLog("Marca generata " + info.getGenTime());
						LogWriter.writeLog("Policy della marca " + info.getPolicy());
					}
				}
			);

			initProxyConnection(signData.getProxyHost(), signData.getProxyPort(), signData.getProxyUser(), signData.getProxyPassword() );
		}

		Collection<byte[]> crlBytes = null;
		byte[] encodedSig = null;
		try{
			
			encodedSig = sgn.getEncodedPKCS7(hash, cal, tsc, ocsp, crlBytes, CryptoStandard.CADES);
			//encodedSig = sgn.getEncodedPKCS7(hash, cal, tsc, ocsp, crlBytes, CryptoStandard.CMS);

			if (contentEstimated + 2 < encodedSig.length) {
				System.err.println("SigSize - contentEstimated=" + contentEstimated + ", sigLen=" + encodedSig.length);
				return false;
			}
		} catch(Exception e ){
			LogWriter.writeLog("Errore", e);
			throw e;
		}

		byte[] paddedSig = new byte[contentEstimated];
		System.arraycopy(encodedSig, 0, paddedSig, 0, encodedSig.length);

		PdfDictionary dic2 = new PdfDictionary();
		dic2.put(PdfName.CONTENTS, new PdfString(paddedSig).setHexWriting(true));

		try {
			appearance.close(dic2);
			fout.close();
			fout = null;
			provider.logout();

			return true;
		} catch (IOException e) {
			LogWriter.writeLog("Errore", e);
			throw new Exception("Errore");
		}
	}

	public boolean mark(SignData signData) throws Exception { 

		LogWriter.writeLog("Inizio metodo Marca");

		FileOutputStream fout = null;
		PdfReader reader;
		final String outFile;
		try {
			reader = new PdfReader( selectedFile );
			outFile = signData.getOutputFilePath();
			fout = new FileOutputStream(outFile);
		} catch (IOException e) {
			LogWriter.writeLog("Errore", e);
			throw new Exception("Errore nei file di input o output");
		}

		PdfStamper stamper;
		PdfSignatureAppearance appearance;
		try {
			stamper = PdfStamper.createSignature(reader, fout, '\0', null, false);
			appearance = stamper.getSignatureAppearance();
		} catch (DocumentException e) {
			LogWriter.writeLog("Errore", e);
			throw new Exception("Errore");
		} catch (IOException e) {
			LogWriter.writeLog("Errore", e);
			throw new Exception("Errore");
		}

		AcroFields af = reader.getAcroFields();
		List names = af.getSignatureNames();
		for (int k = 0; k < names.size(); ++k) {
			String name = (String)names.get(k);
			LogWriter.writeLog("name " + name );

			PdfDictionary sigDict = af.getSignatureDictionary(name);
			PdfString contents = sigDict.getAsString(PdfName.CONTENTS);
			sigDict.remove( PdfName.CONTENTS );
			//af.removeField(name);	

			int contentLenght = contents.getOriginalBytes().length;
			LogWriter.writeLog("contentLenght " + contentLenght);

			int contentEstimated = new Integer((contentLenght * 2 + 2));

			byte[] contentSignedMarked = contents.getOriginalBytes();
			byte[] paddedSig = new byte[contentEstimated];
			System.arraycopy(contentSignedMarked, 0, paddedSig, 0, contentSignedMarked.length);
			LogWriter.writeLog("lenght "+ paddedSig.length);

			PdfDictionary dic2 = new PdfDictionary();
			dic2.put(PdfName.CONTENTS, new PdfString(paddedSig).setHexWriting(true));

			try {
				appearance.setCryptoDictionary(sigDict);

				HashMap<PdfName, Integer> exc = new HashMap<PdfName, Integer>();
				exc.put(PdfName.CONTENTS, new Integer(2*contentEstimated+2));
				appearance.preClose(exc);

				appearance.close( dic2);
				fout.close();
				fout = null;

				return true;
			} catch (IOException e) {
				throw new Exception("Errore");
			}


		}

		return false;

	}

	public static void rotate(int pageCount, PdfPageData currentPageData,
			RotatePDFPages current_selection) {
		throw new java.lang.AssertionError("Itext not on classpath");
	}

	public static void setCrop(int pageCount, PdfPageData currentPageData,
			CropPDFPages cropPage) {
		throw new java.lang.AssertionError("Itext not on classpath");
	}

	public static void delete(int pageCount, PdfPageData currentPageData,
			DeletePDFPages deletedPages) {
		throw new java.lang.AssertionError("Itext not on classpath");
	}

	public static void stampImage(int pageCount, PdfPageData currentPageData,
			final StampImageToPDFPages stampImage) {
		throw new java.lang.AssertionError("Itext not on classpath");
	}

	public static void stampText(int pageCount, PdfPageData currentPageData,
			final StampTextToPDFPages stampText) {
		throw new java.lang.AssertionError("Itext not on classpath");
	}

	public static void addHeaderFooter(int pageCount, PdfPageData currentPageData,
			final AddHeaderFooterToPDFPages addHeaderFooter) {
		throw new java.lang.AssertionError("Itext not on classpath");
	}

	public static void encrypt(int pageCount, PdfPageData currentPageData,
			EncryptPDFDocument encryptPage) {
		throw new java.lang.AssertionError("Itext not on classpath");
	}

	public static String getVersion()
	{
		throw new java.lang.AssertionError("Itext not on classpath");
	}

	public Proxy createProxy(Proxy.Type proxyType, String proxyHost, int proxyPort) {
		if( proxyType==null)
			return Proxy.NO_PROXY;
		else {
			return new Proxy(proxyType, new InetSocketAddress(proxyHost, proxyPort));
		}
	}

	private class ProxyAuthenticator extends Authenticator {  

		private String user, password;  

		public ProxyAuthenticator(String user, String password) {  
			this.user = user;  
			this.password = password;  
		}  

		protected PasswordAuthentication getPasswordAuthentication() {  
			return new PasswordAuthentication(user, password.toCharArray());  
		}  
	}  

	private void initProxyConnection(String proxyHost, String proxyPort, String proxyUser, String proxyPassword){
		//if( System.getProperty("http.proxyHost")==null )
			System.setProperty("http.proxyHost", proxyHost );
		//if( System.getProperty("http.proxyPort")==null )
			System.setProperty("http.proxyPort", proxyPort );
		//if( System.getProperty("http.proxyUser")==null )
			System.setProperty("http.proxyUser", proxyUser );
		//if( System.getProperty("http.proxyPassword")==null )
			System.setProperty("http.proxyPassword",  proxyPassword );

		Proxy proxy = createProxy(Proxy.Type.HTTP, proxyHost, Integer.parseInt( proxyPort ));
		Authenticator.setDefault(new ProxyAuthenticator( proxyUser, proxyPassword ));
	}
	
	public static boolean isFileFirmato( String selectedFile ){
		if( selectedFile==null )
			return false;
		PdfReader reader;
		try {
			reader = new PdfReader( selectedFile );
			AcroFields af = reader.getAcroFields();
			List names = af.getSignatureNames();
			if( names!=null && names.size()>0 )
				return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	private boolean checkCertificate(X509Certificate certificate, PreferenceManager preferenceManager ) throws Exception{
		
		
		if( certificate!=null ){
			
			String cognomeNomeCertificato = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_COGNOMENOME_CERTIFICATO );
			if( cognomeNomeCertificato!=null ){
				X500Name x500name = new JcaX509CertificateHolder(certificate).getSubject();
				if(x500name!=null ){
					RDN[] cns = x500name.getRDNs( BCStyle.CN );
					if( cns!=null && cns.length>0){
						RDN cn = cns[0];
						if( cn!=null && cn.getFirst()!=null && cn.getFirst().getValue()!=null 
								&& !IETFUtils.valueToString(cn.getFirst().getValue()).equalsIgnoreCase( cognomeNomeCertificato ) ){
							throw new Exception(Messages.getMessage( MessageConstants.MSG_INVALID_SIGNING_CERT_NAME, IETFUtils.valueToString(cn.getFirst().getValue())));
						}
					}
				}
			}
		
			String codiceFiscaleCertificato = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_CODICEFISCALE_CERTIFICATO );
			if( codiceFiscaleCertificato!=null ){
				X500Name x500name = new JcaX509CertificateHolder(certificate).getSubject();
				if(x500name!=null ){
					RDN[] serialNumbers = x500name.getRDNs( BCStyle.SERIALNUMBER );
					if( serialNumbers!=null && serialNumbers.length>0){
						RDN serialNumberRDN = serialNumbers[0];
						if( serialNumberRDN!=null && serialNumberRDN.getFirst()!=null && serialNumberRDN.getFirst().getValue()!=null){ 
							String serialNumber = IETFUtils.valueToString(serialNumberRDN.getFirst().getValue() );
							if( serialNumber!=null){	
								serialNumber = serialNumber.substring( serialNumber.indexOf(":"), serialNumber.length() );
								if( !serialNumber.equalsIgnoreCase( codiceFiscaleCertificato ) )
									throw new Exception(Messages.getMessage(MessageConstants.MSG_INVALID_SIGNING_CERT_CF, serialNumber ) );
							}
						}
					}
				}
			}
		}
		
		return true;
	}
}
