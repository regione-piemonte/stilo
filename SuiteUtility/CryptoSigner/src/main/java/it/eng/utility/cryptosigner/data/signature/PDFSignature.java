package it.eng.utility.cryptosigner.data.signature;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.pdmodel.PDDocument;

//import com.itextpdf.text.pdf.security.DigestAlgorithms;
//import com.itextpdf.text.pdf.security.MakeSignature;
//import com.itextpdf.text.pdf.security.PdfPKCS7;

import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSProcessable;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.util.Store;

import it.eng.utility.cryptosigner.bean.SignerBean;
import it.eng.utility.cryptosigner.controller.bean.ValidationInfos;
import it.eng.utility.cryptosigner.utils.MessageConstants;
import it.eng.utility.cryptosigner.utils.MessageHelper;

/**
 * Implementa una firma digitale di tipo PDF (utilizzata nel formato PAdES)
 * 
 * @author Stefano Zennaro
 */
public class PDFSignature implements ISignature {

	//private PdfPKCS7 pkcs7;
	private PDSignature signature;
	private List<ISignature> counterSignatures;
	private SignerBean signerBean;
	private byte[] signatureContent;
	private boolean signatureValid;
	private boolean signatureWarning;

	static Logger log = LogManager.getLogger(PDFSignature.class);

	//public PDFSignature(PdfPKCS7 pkcs) {
	public PDFSignature(PDSignature signature, File signedFile, int indexFirma) {
		//this.pkcs7 = pkcs;
		this.signature = signature;
		this.signerBean = new SignerBean();
		//X509Certificate certificate = pkcs7.getSigningCertificate();
		
//		if (pkcs7.getSignDate() != null)
//			signerBean.setSigningTime(pkcs7.getSignDate().getTime());
		try{
			if (signature.getSignDate() != null){
				signerBean.setSigningTime(signature.getSignDate().getTime());
			}
		} catch(Exception e){
			log.error("Errore in PDFSignature ", e);
		}
		X509Certificate certificate = getCertificate(signature, signedFile, indexFirma);//pkcs7.getSigningCertificate();
		try{
			if( certificate!=null ){
				signerBean.setCertificate(certificate);
				signerBean.setIusser(certificate.getIssuerX500Principal());
				signerBean.setSubject(certificate.getSubjectX500Principal());
			}
		} catch(Exception e){
			log.error("Errore in PDFSignature ", e);
		}
		counterSignatures = new ArrayList<ISignature>();
	}

	/*
	 * TODO: da implementare..
	 */
	public List<ISignature> getCounterSignatures() {
		return counterSignatures;
	}

	public byte[] getSignatureBytes() {
		try {
//			return pkcs7.getAuthenticatedAttributeBytes(DigestAlgorithms.getDigest(pkcs7.getDigestAlgorithm()).getBytes(), pkcs7.getTimeStampDate(), null, null,
//					// MakeSignature.CryptoStandard.CADES
//					MakeSignature.CryptoStandard.CMS);
			return signatureContent;
		} catch (Throwable e) {
			log.error("Eccezione getSignatureBytes", e);
			return null;
		}
	}

	public SignerBean getSignerBean() {
		return signerBean;
	}

	public ValidationInfos verify() {
		ValidationInfos validationInfos = new ValidationInfos();
		boolean result = false;
		if( signerBean!=null)
			log.debug("Certificate: " + signerBean.getCertificate() );
		//try {
			result = signatureValid;//pkcs7.verify();
			log.debug("Esito verifica " + result);
//		} catch (SignatureException e) {
//			log.error("Eccezione verify", e);
//		} catch (GeneralSecurityException e) {
//			log.error("Eccezione verify", e);
//		}
		if (!result) {
			validationInfos.addErrorWithCode(MessageConstants.SIGN_VERIFICATION_ERROR, MessageHelper.getMessage(MessageConstants.SIGN_VERIFICATION_ERROR));
			validationInfos.addError(MessageHelper.getMessage(MessageConstants.SIGN_VERIFICATION_ERROR));
		}
		log.debug("signatureWarning ...." + signatureWarning);
		if(signatureWarning){
			log.debug("Il subfilter non dovrebbe essere adbe.pkcs7.sha1");
			validationInfos.addWarning(MessageHelper.getMessage(MessageConstants.SIGN_SHA1SUBFILTER_WARNING));
			validationInfos.addWarningWithCode(MessageConstants.SIGN_SHA1SUBFILTER_WARNING, MessageHelper.getMessage(MessageConstants.SIGN_SHA1SUBFILTER_WARNING));
		}
		return validationInfos;
	}
	
	private X509Certificate getCertificate(PDSignature signature, File signedFile, int indexFirma){
		
		try {
			byte[] bytes = IOUtils.toByteArray(new FileInputStream(signedFile));
			//signatureContent = signature.getContents(new FileInputStream(signedFile));
			//byte[] signedContent = signature.getSignedContent(new FileInputStream(signedFile));
	        signatureContent = signature.getContents(bytes);
	        byte[] signedContent = signature.getSignedContent(bytes);
	        // Now we construct a PKCS #7 or CMS.
	        
	        String subFilter = signature.getSubFilter();
            CMSSignedData cmsSignedData = null;
	        if ("adbe.pkcs7.detached".equals(subFilter) || "ETSI.CAdES.detached".equals(subFilter))
            {
                //cms = new CMSSignedData(new CMSProcessableByteArray(signedContentAsBytes), signatureAsBytes);
                CMSProcessable cmsProcessableInputStream = new CMSProcessableByteArray(signedContent);
    	        cmsSignedData = new CMSSignedData(cmsProcessableInputStream, signatureContent);
            }
            else if ("adbe.pkcs7.sha1".equals(subFilter))
            {
            	cmsSignedData = new CMSSignedData(new ByteArrayInputStream(signatureContent));
            	signatureWarning = true;
            }
	        
	        
	        SignerInformationStore sis = cmsSignedData.getSignerInfos();
	        Collection signers = sis.getSigners();
	        Store certStore = cmsSignedData.getCertificates();
	        Iterator signersIterator = signers.iterator();
	        
	        while (signersIterator.hasNext()) {
	        	SignerInformation signer = (SignerInformation) signersIterator.next();
	            Collection certCollection = certStore.getMatches(signer.getSID());
	            
	            Iterator certIt = certCollection.iterator();
	            X509CertificateHolder x509CertificateHolder = (X509CertificateHolder) certIt.next();
	    	
				if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
					log.info("-----aggiungo il provider ");
					Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
				}
				X509Certificate certificate1 = new JcaX509CertificateConverter().setProvider(BouncyCastleProvider.PROVIDER_NAME).getCertificate( x509CertificateHolder);
				
				//System.out.println("digest "+signer.getDigestAlgOID());
				//MessageDigest digest=MessageDigest.getInstance(signer.getDigestAlgOID());
				//MessageDigest digest=MessageDigest.getInstance("SHA256");
				//String encAlgo="";
				/*if(digest.getAlgorithm().equals("1.3.14.3.2.26")) {
				    encAlgo="SHA1withRSA";                
				}
				else if(digest.getAlgorithm().equals("2.16.840.1.101.3.4.2.1"))    {
				    encAlgo="SHA256withRSA";  
				}
				else if(digest.getAlgorithm().equals("2.16.840.1.101.3.4.2.2"))    {
				    encAlgo="SHA384withRSA";  
				}
				else if(digest.getAlgorithm().equals("2.16.840.1.101.3.4.2.3"))    {
				    encAlgo="SHA512withRSA";  
				}
				Signature rsaSign=Signature.getInstance(encAlgo);       
		        rsaSign.initVerify(certificate1.getPublicKey());
		        rsaSign.update(signer.getEncodedSignedAttributes());
		        boolean cmsSignatureValid=rsaSign.verify(signer.getSignature());
				System.out.println("*****cmsSignatureValid: "+cmsSignatureValid);*/
				
				//https://stackoverflow.com/questions/64234897/adobe-displays-pkcs7-parsing-error-when-i-make-a-pades-lt-signature-using-my-cod
				//Get Attribute
				/*Attribute attribute1 =signer.getSignedAttributes().get(PKCSObjectIdentifiers.pkcs_9_at_messageDigest);
				Attribute attribute2=null;
				if(signer.getUnsignedAttributes()!=null)    {
				    attribute2 =signer.getUnsignedAttributes().get(PKCSObjectIdentifiers.id_aa_signatureTimeStampToken);
				}
				byte[] digest1 = Base64.getEncoder().encode(
				                Hex.decode(attribute1.getAttributeValues()[0].toString().substring(1)));
				
				ByteArrayInputStream pdfBytes=new ByteArrayInputStream(bytes);
				byte[] contentToSigned=getByteRangeData(pdfBytes, signature.getByteRange());
				String mdPdf = Base64.getEncoder().encodeToString(digest.digest(contentToSigned));
				
				if(mdPdf.equals(new String(digest1))){
					System.out.println("**VALIDO******");
				} else {
					System.out.println("----NON VALIDO------");
				}*/
				
//				log.info("certIt.next() " + certIt.next());
//				X509CertificateHolder cert = (X509CertificateHolder) certIt.next();
				//if (signer.verify(new JcaSimpleSignerInfoVerifierBuilder().build(x509CertificateObject))) {
				
				log.debug("certificate1.getPublicKey()::: " + certificate1.getPublicKey());
				boolean test = signer.verify(new JcaSimpleSignerInfoVerifierBuilder().build(certificate1.getPublicKey()));
				if ( test) {
									
					log.debug("PDF signature verification is correct.");
				    signatureValid = true;
				    // IMPORTANT: Note that you should usually validate the signing certificate in this phase, e.g. trust, validity, revocation, etc. See http://www.nakov.com/blog/2009/12/01/x509-certificate-validation-in-java-build-and-verify-chain-and-verify-clr-with-bouncy-castle/.
				} else {
					signatureValid = false;
				    log.debug("PDF signature verification failed.");
				}
				
	            //return x509CertificateObject;
	            return certificate1;
				
	        }
	        
		} catch (FileNotFoundException e) {
			signatureValid = false;
			log.error("Eccezione getCertificate " + e.getMessage());
		} catch (IOException e) {
			log.error("Eccezione getCertificate " + e.getMessage());
			signatureValid = false;
			return getCertificateError(signedFile, indexFirma);
		} catch (CMSException e) {
			signatureValid = false;
			log.error("Eccezione getCertificate " + e.getMessage());
		} catch (CertificateParsingException e) {
			signatureValid = false;
			log.error("Eccezione getCertificate " + e.getMessage());
		} catch (OperatorCreationException e) {
			signatureValid = false;
			log.error("Eccezione getCertificate " + e.getMessage());
		} catch (CertificateException e) {
			signatureValid = false;
			log.error("Eccezione getCertificate " + e.getMessage());
		} catch (Exception e) {
			signatureValid = false;
			log.error("Eccezione getCertificate " + e.getMessage());
			return getCertificateError(signedFile, indexFirma);
		}
        return null;
	}
	
	private X509Certificate getCertificateError( File file, int indexFirma){

		X509Certificate x509CertificateObject = null;
		int localIndex = 0;
		PDDocument pdDoc = null;
		FileInputStream fis = null;
		try {
			fis = FileUtils.openInputStream(file);
			pdDoc = PDDocument.load(fis);

			COSDictionary trailer = pdDoc.getDocument().getTrailer();
			COSDictionary root = (COSDictionary)trailer.getDictionaryObject( COSName.ROOT );
			COSDictionary acroForm = (COSDictionary)root.getDictionaryObject( COSName.ACRO_FORM );
			COSArray fields = (COSArray)acroForm.getDictionaryObject( COSName.FIELDS );
			if( fields!=null ){
				for( int i=0; i<fields.size(); i++ ){
					COSDictionary field = (COSDictionary)fields.getObject( i );
					String type = field.getNameAsString( "FT" );
					if( "Sig".equals( type ) ){
						COSDictionary cert = (COSDictionary)field.getDictionaryObject( COSName.V );
						if( cert != null ){
							COSName subFilter = (COSName)cert.getDictionaryObject( COSName.getPDFName( "SubFilter" ) );
							if( subFilter!=null ){
								COSString certString = (COSString) cert.getDictionaryObject( COSName.CONTENTS);

								byte[] certData = certString.getBytes();
								CertificateFactory factory;

								factory = CertificateFactory.getInstance("X.509");
								ByteArrayInputStream certStream = new ByteArrayInputStream(certData);
								Collection<? extends Certificate> certs = factory.generateCertificates(certStream);
								Iterator certIt = certs.iterator();
								boolean stopRicerca = false;
								while (certIt.hasNext() && !stopRicerca) {
									if( localIndex==indexFirma){
										x509CertificateObject = (X509Certificate) certIt.next();	                				
										stopRicerca = true;
									} else {
										certIt.next();
									}
									localIndex++;              				

								}
							}

						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Eccezione getCertificateError", e);
		} finally {
			if( pdDoc!=null ){
				try {
					pdDoc.close();
				} catch (IOException e) {
					log.error("Eccezione getCertificateError", e);
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					log.error("Eccezione getCertificateError", e);
				}
			}
		}

		return x509CertificateObject;
	}

}
