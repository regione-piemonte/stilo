package it.eng.client.applet.operation;


import it.eng.client.applet.bean.PrivateKeyAndCert;
import it.eng.client.applet.operation.jsignpdf.HashAlgorithm;
import it.eng.client.applet.operation.xml.Base64Utils;
import it.eng.client.applet.operation.xml.SignatureCertificateResponse;
import it.eng.client.applet.operation.xml.SignatureResponse;
import it.eng.client.applet.operation.xml.SignatureValidity;
import it.eng.client.applet.operation.xml.URIOfflineResolver;
import it.eng.client.applet.util.PreferenceKeys;
import it.eng.client.applet.util.PreferenceManager;
import it.eng.common.LogWriter;
import it.eng.common.bean.FileBean;
import it.eng.common.type.SignerType;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.AuthProvider;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.xml.security.c14n.Canonicalizer;
import org.apache.xml.security.keys.KeyInfo;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.utils.Constants;
import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import xades4j.XAdES4jException;
import xades4j.algorithms.EnvelopedSignatureTransform;
import xades4j.algorithms.GenericAlgorithm;
import xades4j.production.DataObjectReference;
import xades4j.production.SignatureAppendingStrategies;
import xades4j.production.SignedDataObjects;
import xades4j.production.XadesBesSigningProfile;
import xades4j.production.XadesSignatureResult;
import xades4j.production.XadesSigner.SignatureAppendingStrategy;
import xades4j.production.XadesSigningProfile;
import xades4j.properties.DataObjectDesc;
import xades4j.properties.QualifyingProperty;
import xades4j.properties.SignatureTimeStampProperty;
import xades4j.properties.data.PropertyDataObject;
import xades4j.properties.data.SignatureTimeStampData;
import xades4j.providers.BasicSignatureOptionsProvider;
import xades4j.providers.CertificateValidationProvider;
import xades4j.providers.KeyingDataProvider;
import xades4j.providers.SigningCertChainException;
import xades4j.providers.SigningKeyException;
import xades4j.providers.ValidationData;
import xades4j.providers.impl.PKIXCertificateValidationProvider;
import xades4j.utils.CollectionUtils;
import xades4j.utils.DOMHelper;
import xades4j.verification.QualifPropsDataCollectorImpl;
import xades4j.verification.QualifyingPropertiesIncorporationException;
import xades4j.verification.QualifyingPropertyVerificationContext;
import xades4j.verification.RawDataObjectDesc;
import xades4j.verification.SignatureSpecificVerificationOptions;
import xades4j.verification.SignatureUtils;
import xades4j.verification.UnexpectedJCAException;
import xades4j.verification.XadesVerificationProfile;
import xades4j.verification.XadesVerifier;
import xades4j.verification.SignatureUtils.KeyInfoRes;
import xades4j.verification.SignatureUtils.ReferencesRes;

import com.sun.org.apache.xpath.internal.XPathAPI;

public class XadesSigner implements ISigner {

	public enum XmlSignatureType {
		ENVELOPED, ENVELOPING
	}

	protected String signatureMethodURI = XMLSignature.ALGO_ID_SIGNATURE_RSA_SHA256;
	protected String digestURI = "http://www.w3.org/2001/04/xmlenc#sha256";
	private SignatureAppendingStrategy signatureAppendingStrategies = SignatureAppendingStrategies.AsLastChild;  
	
	private Document doc = null;

	public XadesSigner(){

	}
	public XadesSigner(String algId){

	}

	private void selectAlg(){
		String algoritmoHash = HashAlgorithm.SHA256.getAlgorithmName();
		try {
			algoritmoHash = PreferenceManager.getString( PreferenceKeys.PROPERTY_SIGN_ALGORITHM );
			LogWriter.writeLog("Proprietà "+ PreferenceKeys.PROPERTY_SIGN_ALGORITHM +"=" + algoritmoHash );
			
			if( algoritmoHash.equalsIgnoreCase( HashAlgorithm.SHA256.getAlgorithmName() ) ){
				signatureMethodURI = XMLSignature.ALGO_ID_SIGNATURE_RSA_SHA256;
				digestURI = "http://www.w3.org/2001/04/xmlenc#sha256";
			} else {
				signatureMethodURI = XMLSignature.ALGO_ID_SIGNATURE_RSA_SHA1;
				digestURI = "http://www.w3.org/2001/04/xmlenc#sha1";
			}
		} catch (Exception e) {
			LogWriter.writeLog("Errore nel recupero della proprietà " + PreferenceKeys.PROPERTY_SIGN_ALGORITHM );
		}
		
	}


	/* (non-Javadoc)
	 * @see it.eng.client.applet.operation.ISigner#firma(java.io.File, java.io.OutputStream, it.eng.client.applet.bean.PrivateKeyAndCert, java.security.Provider, boolean, boolean, boolean)
	 */
	@Override
	public boolean firma(FileBean bean, PrivateKeyAndCert pvc, AuthProvider provider,
			boolean timemark, boolean detached, boolean congiunta, boolean isSigned) throws Exception{

		LogWriter.writeLog("Inizio metodo firma xades - timemark = " + timemark + ", detached = " + detached +
				", congiunta = " + congiunta+", isSigned = " + isSigned );

		InputStream is =FileUtils.openInputStream( bean.getFile() );
		byte[]  data = IOUtils.toByteArray(is);
		XmlSignatureType xmlSignatureType;
//		if( congiunta )
//			xmlSignatureType = XmlSignatureType.ENVELOPING;
//		else
			xmlSignatureType = XmlSignatureType.ENVELOPED;
		
		selectAlg();
		
		byte[] digest = getDigest(data, pvc.getCertificate(), pvc.getPrivateKey(), xmlSignatureType);
		
		FileOutputStream outputStream = FileUtils.openOutputStream( bean.getOutputFile() );
		generate(digest, outputStream);
		
		SignatureResponse result = verify( new FileInputStream( bean.getOutputFile()) );
		LogWriter.writeLog("result " + result.getSignatureValidity() );
		if( result.getSignatureValidity().equals( SignatureValidity.VALID ))
			return true;
		else 
			return false;
	}

	public byte[] getDigest(byte[] data, X509Certificate certificate, PrivateKey privateKey, XmlSignatureType xmlSignatureType) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);

		String canonicalizer = Canonicalizer.ALGO_ID_C14N11_WITH_COMMENTS;      
	    		
		Element elemToSign = null;
		if (xmlSignatureType.equals(XmlSignatureType.ENVELOPED)) {
			doc = dbf.newDocumentBuilder().parse(new ByteArrayInputStream(data));

			// Apache Santuario now uses Document.getElementById; use this convention for tests.
			elemToSign = doc.getDocumentElement();
			LogWriter.writeLog("Radice doc xml " + elemToSign.getNodeName() );
			DOMHelper.useIdAsXmlId(elemToSign);
			
			LogWriter.writeLog("Constants._ATT_ID " + Constants._ATT_ID);
			if(elemToSign.hasAttributeNS(null, Constants._ATT_ID)) {
				LogWriter.writeLog( "AttributeNS " + elemToSign.getAttributeNS(null, Constants._ATT_ID) ); 
	        }
		} else {
			throw new Exception("Firma " + XmlSignatureType.ENVELOPING + " non supportata");
		}

		KeyingDataProvider keyingDataProvider = new DigestKeyingDataProviderAdapter(certificate, privateKey);
		
		XadesSigningProfile signingProfile = new XadesBesSigningProfile( keyingDataProvider );

//		signingProfile.withAlgorithmsProviderEx(new DefaultAlgorithmsProviderEx() {
//			@Override
//			public Algorithm getCanonicalizationAlgorithmForSignature() {
//				
//				return new GenericAlgorithm(Canonicalizer.ALGO_ID_C14N11_WITH_COMMENTS, Collections.EMPTY_LIST);
//			}
//		});

		signingProfile.withBasicSignatureOptionsProvider(new BasicSignatureOptionsProvider() {

			@Override
			public boolean signSigningCertificate() {
				return true;
			}

			@Override
			public boolean includeSigningCertificate() {
				return true;
			}

			@Override
			public boolean includePublicKey() {
				return false;
			}
		});

		//SignerBESTokenLess signer = (SignerBESTokenLess) signingProfile.newSigner();
		xades4j.production.XadesSigner signer = signingProfile.newSigner();
		LogWriter.writeLog("Signer " + signer.getClass() );

		//DataObjectDesc obj1 = new DataObjectReference("#xpointer(/)")
		DataObjectDesc obj1 = new DataObjectReference("")
		.withTransform(new EnvelopedSignatureTransform())
		.withTransform(new GenericAlgorithm(canonicalizer, Collections.EMPTY_LIST));

		SignedDataObjects dataObjs = new SignedDataObjects(obj1)
		//.withCommitmentType(AllDataObjsCommitmentTypeProperty.proofOfOrigin())
		;

		XadesSignatureResult digest = signer.sign(dataObjs, elemToSign, signatureAppendingStrategies);
		
//		Signature signatureAlgorithm = Signature.getInstance("SHA256withRSA");
//		signatureAlgorithm.initSign(privateKey);
//		signatureAlgorithm.update(digest.getSignature().getSignatureValue());
		//signatureAlgorithm.update(signer.getCanonicalizedOctetStream());
				
		return digest.getSignature().getSignatureValue();
		//return signer.getCanonicalizedOctetStream();


	}

	public void generate(byte[] digest, OutputStream dest) throws Exception {
		
		
		Element nscontext = createDSctx(doc, "ds", Constants.SignatureSpecNS);
		Element signatureValue = (Element) XPathAPI.selectSingleNode(doc, "//ds:Signature[1]/ds:SignatureValue", nscontext);

		signatureValue.setTextContent("\n"+Base64Utils.base64EncodeLines(digest)+"\n");

		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer trans = tf.newTransformer();
		
		trans.transform(new DOMSource(doc), new StreamResult(dest));
	}
	
	public static Element createDSctx(Document doc, String prefix, String namespace) {

		if ((prefix == null) || (prefix.trim().length() == 0)) {
			throw new IllegalArgumentException("You must supply a prefix");
		}

		Element ctx = doc.createElementNS(null, "namespaceContext");

		ctx.setAttributeNS(Constants.NamespaceSpecNS, "xmlns:" + prefix.trim(), namespace);

		return ctx;
	}
	
	@Override
	public boolean addCounterSignature(FileBean bean, PrivateKeyAndCert pvc,
			X509Certificate aCertToBeSign, AuthProvider provider)
					throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	private class DigestKeyingDataProviderAdapter implements KeyingDataProvider {
		private List<X509Certificate> certificateChain = new ArrayList<X509Certificate>();
		private PrivateKey privateKey;
		private X509Certificate certificate;

		public DigestKeyingDataProviderAdapter(X509Certificate certificate, PrivateKey privateKey) throws Exception {
			this.certificate = certificate;
			this.privateKey = privateKey;
		}

		@Override
		public List<X509Certificate> getSigningCertificateChain() throws SigningCertChainException, UnexpectedJCAException {	
			certificateChain.add(certificate);
			return certificateChain;
		}

		@Override
		public PrivateKey getSigningKey(X509Certificate arg0) throws SigningKeyException, UnexpectedJCAException {
			return privateKey;
		}
	}
	
	public SignatureResponse verify(InputStream is) throws Exception {
	    org.apache.xml.security.Init.init();    
	    
	    SignatureResponse signatureResponse = new SignatureResponse(SignerType.XAdES);
	    signatureResponse.setValid( SignatureValidity.NOT_SIGNED ); 
	    
	    javax.xml.parsers.DocumentBuilderFactory dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance();

	    dbf.setNamespaceAware(true);
	    dbf.setAttribute("http://xml.org/sax/features/namespaces", Boolean.TRUE);

	    javax.xml.parsers.DocumentBuilder db = dbf.newDocumentBuilder();

	    org.w3c.dom.Document doc = db.parse(is);
	    Element nscontext = createDSctx(doc, "ds", Constants.SignatureSpecNS);
	    Element sigElement = (Element) XPathAPI.selectSingleNode(doc,"//ds:Signature[1]", nscontext);
	    if (sigElement != null) {
	      XMLSignature signature = new XMLSignature(sigElement, "");

	      signature.addResourceResolver(new URIOfflineResolver());

	      XMLUtils.outputDOMc14nWithComments(signature.getElement(), System.out);
	      KeyInfo ki = signature.getKeyInfo();

	      if (ki != null) {
	    	  System.out.println("KI diverso da null");
	         if (!ki.containsX509Data()) {
	            LogWriter.writeLog("Could find a X509Data element in the KeyInfo");
	            signatureResponse.setValid( SignatureValidity.INVALID );
	    	    return signatureResponse;
	         }

	         X509Certificate cert = ki.getX509Certificate();
	         if (cert != null) {
	           boolean valid = signature.checkSignatureValue(cert);
	           System.out.println("valid " + valid);
	           SignatureCertificateResponse certificateResponse = new SignatureCertificateResponse(cert, null, valid,0);
	           signatureResponse.getCertificates().add(certificateResponse);
	           
	           //Setto la validita' del documento in base alla presenza o meno di altre firme
	           if (valid) {
	             if (signatureResponse.getValid().equals(SignatureValidity.NOT_SIGNED)) {
	               signatureResponse.setValid( SignatureValidity.VALID );
	             }
	           } else {
	             signatureResponse.setValid( SignatureValidity.INVALID );
	           }
	         }
	      }
	    }
	    
	    return signatureResponse;
	    
	  }
}
