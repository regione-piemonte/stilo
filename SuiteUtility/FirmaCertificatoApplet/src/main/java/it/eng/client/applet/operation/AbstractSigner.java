package it.eng.client.applet.operation;


import it.eng.client.applet.bean.PrivateKeyAndCert;
import it.eng.client.applet.exception.SmartCardException;
import it.eng.client.applet.i18N.MessageKeys;
import it.eng.client.applet.i18N.Messages;
import it.eng.client.applet.operation.jsignpdf.HashAlgorithm;
import it.eng.client.applet.operation.ts.TimeStamperUtility;
import it.eng.client.applet.util.PreferenceKeys;
import it.eng.client.applet.util.PreferenceManager;
import it.eng.client.applet.util.ProxyUtil;
import it.eng.common.LogWriter;
import it.eng.common.bean.HashFileBean;
import it.eng.common.bean.SignerObjectBean;
import it.eng.common.type.SignerType;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.security.AuthProvider;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

import javax.security.auth.Subject;

import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.cms.Attribute;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.ess.ESSCertIDv2;
import org.bouncycastle.asn1.ess.SigningCertificateV2;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.tsp.TimeStampToken;

import com.itextpdf.text.pdf.security.DigestAlgorithms;
import com.itextpdf.text.pdf.security.ExternalDigest;
import com.itextpdf.text.pdf.security.MakeSignature.CryptoStandard;
import com.itextpdf.text.pdf.security.PdfPKCS7;

public abstract class AbstractSigner {

	 
	public static String dir = System.getProperty("user.home");
	public static String cryptodll = "dllCrypto";
	public static String cryptoConfig = cryptodll+File.separator+"config";
	public static String cryptoConfigFile = cryptoConfig+File.separator+"crypto.config";
	
	public static String externalDllPath = "dllPath";
	
    protected HashAlgorithm digest = HashAlgorithm.SHA256;//algoritmo da usare per l'hash
	protected SignerType signerType = SignerType.P7M;//come vuoi fare al busta default P7M
	protected AuthProvider provider = null;
	protected boolean timemark=false;//indica se apporre la marca temporale alla firma
	
	public abstract String signerfile(HashFileBean file, PrivateKeyAndCert pvc, char[] pin, boolean timemark ) throws SmartCardException;
	
	public HashAlgorithm getDigest() {
		return digest;
	}

	public SignerType getSignerType() {
		return signerType;
	}
	
	/**
	 * Definisce se la busta è una base 64
	 */
	private boolean base64 = false;
	
	public boolean isBase64() {
		return base64;
	}

	public void setBase64(boolean base64) {
		this.base64 = base64;
	}
	
	public AbstractSigner(HashAlgorithm digest,SignerType signerType,AuthProvider provider) {
		this.digest = digest;
		this.signerType = signerType;
		this.provider = provider;
	}
	
	private CertStore getCertStore(X509Certificate certificate) throws Exception {
		  ArrayList<Certificate> list = new ArrayList<Certificate>();
		  list.add(certificate);
		  return CertStore.getInstance("Collection", new CollectionCertStoreParameters(list), "BC");
	}
	 
	public abstract X509Certificate[] getSigningCertificates(char[] pin) throws SmartCardException;
	
	public abstract PrivateKeyAndCert[] getPrivateKeyAndCert(char[] pin) throws SmartCardException;
	
	public abstract PrivateKeyAndCert getPrivateKeyAndCertByAlias(char[] pin, String alias) throws SmartCardException;
	
	public abstract X509Certificate[] getSigningCertificates(char[] pin, char[] cardPin) throws SmartCardException;
	
	public abstract PrivateKeyAndCert[] getPrivateKeyAndCert(char[] pin, char[] cardPin) throws SmartCardException;
	
	
	protected SignerObjectBean signerP7M(PrivateKey key, X509Certificate certificate, byte[] hash, char[] pin, boolean timemark) throws Exception {
			SignerObjectBean bean = null;
			try{
				Certificate[] list= new Certificate[1];
				list[0]=certificate;
				CertStore certStore = CertStore.getInstance("Collection",new CollectionCertStoreParameters( Arrays.asList(list)));
				CMSSignedHashGenerator generator = new CMSSignedHashGenerator();
				ASN1EncodableVector unsignetAttrV = new ASN1EncodableVector();
		        
			    //nonserve la marca si mette dopo la firma
			    AttributeTable unsignetAttrTable = new AttributeTable(unsignetAttrV);
//	            LogWriter.writeLog("timemark " + timemark );
//			    if(timemark){
//	            	LogWriter.writeLog("aggiungo marca alla firma");
//	            	addMarca(unsignetAttrV, hash);
//	            }
	  	       
	  	        LogWriter.writeLog("digest " + digest);
	  	        String alg = CMSSignedDataGenerator.DIGEST_SHA1;
				if(digest.equals(HashAlgorithm.SHA256)){
	            	alg = CMSSignedDataGenerator.DIGEST_SHA256;
		  	        
	            	//Calcolo l'hash del certificato
	            	byte[] certhash = DigestUtils.sha256(certificate.getEncoded());
	            	AlgorithmIdentifier algcert256 = new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256);
	            	ESSCertIDv2 cert = new ESSCertIDv2(algcert256, certhash);
	            	ESSCertIDv2[] arraycert = new ESSCertIDv2[]{cert};
	            	SigningCertificateV2 signcert = new SigningCertificateV2(arraycert);
	            	Attribute attr = new Attribute(PKCSObjectIdentifiers.id_aa_signingCertificateV2,new DERSet(signcert.toASN1Primitive())); 
	            	ASN1EncodableVector v = new ASN1EncodableVector();
	            	v.add(attr);
	      	        AttributeTable signedAttr = new AttributeTable(v);
	      	        generator.addSigner(key, certificate, alg,signedAttr, null);
		  	       
	            } else {
	            	generator.addSigner(key, certificate, alg);
	            }
	  	        
				generator.addCertificatesAndCRLs(certStore);
           
//	    		provider.logout();
//	  	        provider.login(new Subject(), new PasswordHandler( pin ) );
	    		
	  	        //Creo l'oggetto di ritorno
	  	        bean = generator.generate(hash, provider, certificate);
	    		bean.setMarca( timemark );
	  	        
			}catch(Exception e){
				e.printStackTrace();
				throw new Exception(e); 
			}
			return bean;
		}
		
	 
	
		/**
		 * aggiunge la marca agli attributi pasati
		 * @return
		 */
		private void  addMarca(ASN1EncodableVector unsignetAttrV,byte[] hash)throws Exception{
			TimeStampToken tst=getMarca(hash);
  	        ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(tst.getEncoded());
            ASN1InputStream asn1inputstream = new ASN1InputStream(bytearrayinputstream);
  	        Attribute marca= new Attribute(new DERObjectIdentifier("1.2.840.113549.1.9.16.2.14"),new DERSet( asn1inputstream.readObject()    ));
  	        unsignetAttrV.add(marca);
		}
		
		
 
	
		//prendi la marca dal servizio di marcatura
		private TimeStampToken getMarca(byte[] digest)throws Exception{
			 //test
			 String url=PreferenceManager.getString( PreferenceKeys.PROPERTY_SIGN_TSASERVER );
			TsrGenerator tsrgen = new TsrGenerator(url);
	    	TimeStampToken tst;
			try {
				tst = tsrgen.generateTsrData(digest );
			} catch (Exception e) {
				throw new Exception( Messages.getMessage( MessageKeys.MSG_MARCA_ERROR ),e );
			}
			return tst;
		}
	 
		protected SignerObjectBean signerPDF(PrivateKey key, X509Certificate certificate, byte[] hash) throws Exception {
			try{
				SignerObjectBean bean = new SignerObjectBean();

				ExternalDigest externalDigest = new ExternalDigest() {
					public MessageDigest getMessageDigest(String hashAlgorithm) 	throws GeneralSecurityException {
						return DigestAlgorithms.getMessageDigest(hashAlgorithm, null);
					}
				};
				
				LogWriter.writeLog("digest " + digest);
				LogWriter.writeLog("hash " + new String(hash));
				
				PdfPKCS7 pk7 = new PdfPKCS7(key, new Certificate[]{certificate},digest.getAlgorithmName(), null, externalDigest, false);
				//PdfPKCS7 pk7 = new PdfPKCS7(key, new Certificate[]{certificate}, null, digest.name(),provider.getName(), false);
				Calendar cal = GregorianCalendar.getInstance();
				byte sh[] = pk7.getAuthenticatedAttributeBytes(hash, cal, null, null, CryptoStandard.CADES ); 
				//byte sh[] = pk7.getAuthenticatedAttributeBytes(hash,cal,null);
				pk7.update(sh, 0, sh.length);
							
				byte sg[] = pk7.getEncodedPKCS7(hash, cal, null, null, null, CryptoStandard.CADES);
				//byte sg[] = pk7.getEncodedPKCS7(hash, cal,null);
				bean.setSignerInfo(sg);
				bean.getCertificates().add(certificate);
				
				bean.setDigestAlgs(hash);
				
				return bean;
	
			} catch(Exception e){
				throw new Exception(e); 
			}
			
		}
		public boolean isTimemark() {
			return timemark;
		}
		public void setTimemark(boolean timemark) {
			this.timemark = timemark;
		}
		public AuthProvider getProvider() {
			return provider;
		}
		
		
}