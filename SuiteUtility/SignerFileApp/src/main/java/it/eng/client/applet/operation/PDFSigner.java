package it.eng.client.applet.operation;

import it.eng.client.applet.bean.PrivateKeyAndCert;
import it.eng.client.applet.i18N.MessageKeys;
import it.eng.client.applet.i18N.Messages;
import it.eng.client.applet.operation.jsignpdf.CertificationLevel;
import it.eng.client.applet.operation.jsignpdf.HashAlgorithm;
import it.eng.client.applet.operation.jsignpdf.RenderMode;
import it.eng.client.applet.operation.jsignpdf.SignData;
import it.eng.client.applet.util.PreferenceKeys;
import it.eng.client.applet.util.PreferenceManager;
import it.eng.client.applet.util.ProxyUtil;
import it.eng.common.LogWriter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.security.AuthProvider;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.naming.OperationNotSupportedException;
import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.tsp.TimeStampTokenInfo;

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

public class PDFSigner implements ISigner {

	public PDFSigner(){

	}
	public PDFSigner(String algId){

	}

	private HashAlgorithm selectAlg(){
		String algoritmoHash = HashAlgorithm.SHA256.getAlgorithmName();
		try {
			algoritmoHash = PreferenceManager.getString( PreferenceKeys.PROPERTY_SIGN_ALGORITHM );
			LogWriter.writeLog("Proprietà "+ PreferenceKeys.PROPERTY_SIGN_ALGORITHM +"=" + algoritmoHash );
		} catch (Exception e) {
			LogWriter.writeLog("Errore nel recupero della proprietà " + PreferenceKeys.PROPERTY_SIGN_ALGORITHM );
		}
		return HashAlgorithm.fromValue( algoritmoHash );
	}

	private RenderMode selectRenderMode(){
		String renderMode=RenderMode.DESCRIPTION_ONLY.getRenderKey();
		try {
			renderMode = PreferenceManager.getString( PreferenceKeys.PROPERTY_SIGN_PDF_RENDER_MODE );
			LogWriter.writeLog("Proprietà " + PreferenceKeys.PROPERTY_SIGN_PDF_RENDER_MODE + "=" + renderMode);
 		} catch (Exception e) {
			LogWriter.writeLog("Errore nel recupero della proprietà " + PreferenceKeys.PROPERTY_SIGN_PDF_RENDER_MODE );
		}
		return RenderMode.fromValue( renderMode );
	}

	public Image getImg(String image){
		Image ret=null;
		String imgPath=PreferenceManager.getString( image);
		LogWriter.writeLog("Proprieta' " + image + "=" + imgPath );
		if( imgPath!=null && !imgPath.equals("")){
			//cerco di caricare l'immagine da un percorso predefinito
			//URL url=ISmartCard.class.getResource(imgPath);
			try {
				ret=Image.getInstance( imgPath );
			} catch (Exception e) {
				LogWriter.writeLog("Errore", e);
			}  
		}
		return ret;
	}
	/* (non-Javadoc)
	 * @see it.eng.client.applet.operation.ISigner#firma(java.io.File, java.io.OutputStream, it.eng.client.applet.bean.PrivateKeyAndCert, java.security.Provider, boolean, boolean, boolean)
	 */
	@Override
	public boolean firma(File inputFile, OutputStream outputStream, PrivateKeyAndCert pvc, AuthProvider provider,
			boolean timemark, boolean detached, boolean congiunta, boolean isSigned) throws Exception{

		LogWriter.writeLog("Inizio metodo firma pdf - timemark = " + timemark + ", detached = " + detached +
				", congiunta = " + congiunta+", isSigned = " + isSigned );

		SignData signData= new SignData();
		
		String certifyMode = CertificationLevel.NOT_CERTIFIED.getKey();
		try {
			certifyMode = PreferenceManager.getString( PreferenceKeys.PROPERTY_SIGN_PDF_CERTIFICATIONLEVEL );
		} catch (Exception e) {
			LogWriter.writeLog("Errore nel recupero della proprietà " + PreferenceKeys.PROPERTY_SIGN_PDF_CERTIFICATIONLEVEL );
		}
		signData.setCertifyMode( CertificationLevel.findCertificationCode( certifyMode ));
		
		signData.setAppend( isSigned ); 
		if( !congiunta )
			signData.setAppend( false );
		
		signData.setHashAlgorithm( selectAlg() );
		signData.setRenderMode( selectRenderMode() );
		
		signData.setVisibleSignature( PreferenceManager.enabled( PreferenceKeys.PROPERTY_SIGN_PDF_VISIBLE_SIGNATURE ) );
		if(signData.isVisibleSignature()){
			InputStream is =FileUtils.openInputStream( inputFile );
			PdfReader pdfReader=new PdfReader(is);

			int paginaDaFirmare=PreferenceManager.getInt( PreferenceKeys.PROPERTY_SIGN_PDF_SIGNPAGE, 1 );
			LogWriter.writeLog("Pagina da firmare " + paginaDaFirmare );
			if( paginaDaFirmare>pdfReader.getNumberOfPages() ){
				LogWriter.writeLog("La pagina da firmare non esiste, firmo la prima pagina");
				paginaDaFirmare = 1;
			}
			signData.setSignPage( paginaDaFirmare );
			
			Rectangle rect=pdfReader.getCropBox(paginaDaFirmare);
			Float urx=rect.getRight();
			Float ury=rect.getTop();
			//dedico alla firma il 5% dello spazio e la posiziono in alto a destra
			int percentSignSize=PreferenceManager.getInt( PreferenceKeys.PROPERTY_SIGN_PDF_L2TEXTSIZE, 5 );
			int visibleSignSizeX=urx.intValue()*percentSignSize/100; 
			int visibleSignSizeY=ury.intValue()*percentSignSize/100;
			Float x1=urx-visibleSignSizeX;
			Float y1=ury-visibleSignSizeY;
			Float x2=urx-5;
			Float y2=ury-5;

			signData.setRectangle(x1,y1,x2,y2);

			signData.setL2Text( PreferenceManager.getString( PreferenceKeys.PROPERTY_SIGN_PDF_L2TEXT ) );
			signData.setSignImage( getImg( PreferenceKeys.PROPERTY_SIGN_PDF_IMAGE_SIGN ) ) ;
			signData.setImageBg( getImg( PreferenceKeys.PROPERTY_SIGN_PDF_IMAGE_BG ) ) ;

			Float scale=100.0f;
			try {
				scale = Float.parseFloat( PreferenceManager.getString( PreferenceKeys.PROPERTY_SIGN_PDF_IMAGE_BG_SCALE) );
				LogWriter.writeLog("proprietà " + PreferenceKeys.PROPERTY_SIGN_PDF_IMAGE_BG_SCALE + "=" + scale);
			} catch (Exception e) {
				LogWriter.writeLog("Errore nel recupero della proprietà " + PreferenceKeys.PROPERTY_SIGN_PDF_IMAGE_BG_SCALE );
			}
			if(scale!=null && scale.intValue()!=0){
				signData.setBgImgScale(scale);
			}
		}
				
		// timestamp
		signData.setTimestamp( timemark );
		if( timemark ) {
			signData.setTsaUrl( PreferenceManager.getString( PreferenceKeys.PROPERTY_SIGN_TSASERVER ) );
			signData.setTsaUser( PreferenceManager.getString( PreferenceKeys.PROPERTY_SIGN_TSAUSER ) );
			signData.setTsaPasswd( PreferenceManager.getString( PreferenceKeys.PROPERTY_SIGN_TSAPASSWORD ) );
			signData.setTsaServerAuthn( PreferenceManager.getBoolean( PreferenceKeys.PROPERTY_SIGN_TSAAUTH ) );
		}
		
		return sign( signData, FileUtils.openInputStream(inputFile), pvc, outputStream );	
	}


	public boolean sign( SignData signData,InputStream selectedFile,PrivateKeyAndCert pvc,OutputStream fout) throws Exception { 

		PrivateKey privateKey = pvc.getPrivateKey();
		Certificate[] chain = pvc.getChain();

		//carico il pdf e predispongo il file di output
		PdfReader reader;
		try {
			reader = new PdfReader( selectedFile );
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
			int response = JOptionPane.showConfirmDialog( null, 
					Messages.getMessage( MessageKeys.MSG_FIRMAMARCA_ERROR_INVALIDPDFVERSION ), "",	JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(response==JOptionPane.OK_OPTION) {
				signData.setAppend(false);
				tmpPdfVersion = hashAlgorithm.getPdfVersion();
			} else {
				LogWriter.writeLog("Firma annullata");
				return false;
			}
			
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

		final PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
		if ( StringUtils.isNotEmpty(signData.getReason() ) ) {
			LogWriter.writeLog("Imposto la proprietà Reason:" + signData.getReason());
			appearance.setReason(signData.getReason());
		}
		if ( StringUtils.isNotEmpty( signData.getLocation() ) ) {
			LogWriter.writeLog("Imposto la proprietà:" + signData.getLocation());
			appearance.setLocation(signData.getLocation());
		}
		if ( StringUtils.isNotEmpty( signData.getContact() ) ) {
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

				final Image signImg = signData.getSignImage();
				LogWriter.writeLog("signImg " + signImg);
				if (signImg != null ) {
					appearance.setSignatureGraphic(signImg);
				}
				final Image bgImg = signData.getImageBg();
				LogWriter.writeLog("bgImg " + bgImg);
				if (bgImg != null ) {
					appearance.setImage(bgImg);
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
				LogWriter.writeLog( "Imposto la modalita' di renderizzazione: " + renderMode.getRender() );
				appearance.setRenderingMode( renderMode.getRender() );
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
		//			if( signData.isCrlEnabled() ){
		//				contentEstimated = (int) (15000L + 2L * CRLInfo.getByteCount(chain));
		//			}
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

			sgn = new PdfPKCS7(privateKey, chain, hashAlgorithm.getAlgorithmName(), null, externalDigest, false);

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
					//signData.getProxyHost(), signData.getProxyPort(), signData.getProxyUser(), signData.getProxyPassword()
					ProxyUtil.initProxyConnection( );

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
			if (signData.isTsaServerAuthn() ) {
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

			//initProxyConnection(signData.getProxyHost(), signData.getProxyPort(), signData.getProxyUser(), signData.getProxyPassword() );
			ProxyUtil.initProxyConnection();
		}
		//il controllo CRL non lo facciamo quì ma mediante ws
		Collection<byte[]> crlBytes = null;
		byte[] encodedSig = null;
		try{

			encodedSig = sgn.getEncodedPKCS7(hash, cal, tsc, ocsp, crlBytes, CryptoStandard.CADES);
			//encodedSig = sgn.getEncodedPKCS7(hash, cal, tsc, ocsp, crlBytes, CryptoStandard.CMS);

			if (contentEstimated + 2 < encodedSig.length) {
				System.err.println("SigSize - contentEstimated=" + contentEstimated + ", sigLen=" + encodedSig.length);
				throw new Exception( Messages.getMessage( MessageKeys.MSG_FIRMAMARCA_ERROR_PDFSIZEEXCEED) );
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

			return true;
		} catch (IOException e) {
			LogWriter.writeLog("Errore", e);
			throw new Exception("Errore");
		}
	}

	@Override
	public void addCounterSignature(File input, OutputStream outputStream,
			PrivateKeyAndCert pvc, X509Certificate aCertToBeSign,
			AuthProvider provider)throws Exception {
		throw new OperationNotSupportedException("not implementet!");

	}
}
