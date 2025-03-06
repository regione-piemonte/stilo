package it.test.timestamp;

import it.eng.client.applet.SmartCard;
import it.eng.client.applet.exception.SmartCardException;
import it.eng.client.applet.operation.AbstractSigner;
import it.eng.client.applet.operation.PasswordHandler;
import it.eng.client.applet.util.PreferenceKeys;
import it.eng.client.applet.util.PreferenceManager;
import it.eng.common.LogWriter;
import it.eng.common.bean.SignerObjectBean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;
import javax.swing.JApplet;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.io.FileUtils;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.BERSequence;
import org.bouncycastle.asn1.DERInteger;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.cms.Attribute;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.cms.CMSAttributes;
import org.bouncycastle.asn1.cms.SignedData;
import org.bouncycastle.asn1.cms.Time;
import org.bouncycastle.asn1.ess.ESSCertID;
import org.bouncycastle.asn1.ess.ESSCertIDv2;
import org.bouncycastle.asn1.ess.SigningCertificate;
import org.bouncycastle.asn1.ess.SigningCertificateV2;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.IssuerSerial;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.CMSSignedGenerator;
import org.bouncycastle.cms.DefaultSignedAttributeTableGenerator;
import org.bouncycastle.cms.SignerInfoGenerator;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.DigestCalculatorProvider;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.util.Store;

import sun.security.pkcs11.SunPKCS11;

/**
 * test classe di utilità per le marche
 * @author Russo
 *
 */
public class TestControFirma {
	private static String signeralg="SHA256WITHRSA";
	private DERObjectIdentifier digestAlgorithmId = new DERObjectIdentifier(
			CMSSignedGenerator.DIGEST_SHA256);
	
	
	public static void main(String[] args)throws Exception {
		 
		PreferenceManager.initConfig((JApplet)null);
		//File f = new File("c:/test2.txt_firmemar.p7m");
//		File f = new File("c:/testoodt.odt_ok.p7m");
//		File output= new File("c:/testoodt.odt.contro.p7m");
		File f = new File("c:/testoodt.odt.p7m");
		File output= new File("c:/testoodt.odt.contro.p7m" );
		
		//Base64InputStream streambase64 = new Base64InputStream(FileUtils.openInputStream(f));
		CMSSignedData cmsSignedData=new CMSSignedData(FileUtils.openInputStream(f));
		//CMSSignedDataParser cmsSignedData = new CMSSignedDataParser(FileUtils.openInputStream(f));
		//cmsSignedData.getSignedContent().drain();
		Security.addProvider(new BouncyCastleProvider());
		char[] pin=new String("12345").toCharArray();
		SunPKCS11 provider=getProvider(pin, 0);
		 try{
		    	provider.login(new Subject(), new PasswordHandler(pin));
			    	    
			 	KeyStore keyStore = KeyStore.getInstance("PKCS11",provider);
			 	keyStore.load(null,null);		 	        
		        //Get the first private key in the keystore:
		        PrivateKey privateKey = null;
		        Enumeration enumeration = keyStore.aliases();
		        while(enumeration.hasMoreElements()){
		        	
		        	String alias = enumeration.nextElement().toString();
		            LogWriter.writeLog("ALIAS:"+alias);
		        	privateKey = (PrivateKey)keyStore.getKey(alias, null); 
		            LogWriter.writeLog("PRIVATE KEY:"+privateKey);
		            X509Certificate certificate=(X509Certificate)keyStore.getCertificate(alias);
		            Calendar cal=null;
		            try{
		            	String dateToCheck= PreferenceManager.getString(PreferenceKeys.DATE_TO_CHECK);
		            	if(dateToCheck!=null && !dateToCheck.trim().equals(""))  
		            		cal=DatatypeConverter.parseDateTime(dateToCheck );
		            }catch(Exception ex){
		            	//ignore malformed date
		            	ex.printStackTrace();
		            }
		            
//		            if(cal!=null ){
//		            	certificate.checkValidity(cal.getTime());
//		            }else{
//		            	certificate.checkValidity();
//		            }
		            //controllo se � un certificato di firma
		            boolean[] usage = certificate.getKeyUsage();
		            for(boolean h : usage){
		            	String us = "";
		            	
		            	if(h){
		            		us+="1";
		            	}else{
		            		us+="0";
		            	}
		            	LogWriter.writeLog("KEY USAGE:"+us);
		            }
		            
		            //Come da normativa deve contenere il flag di noRepudiation a true per essere un certificato di firma valido
		            //String controlAll = System.getProperty(Constants.ALL_CERTIFICATE_USE);
		            
		            //TODO RM da togliere se si vuole controllare che il certificato � di tipo firma
		            String controlAll = SmartCard.DEBUG;
		            
		            SignerObjectBean bean = null;
		            if(usage[1] || controlAll.equals("0")){
		            	 //fai la firma !!!
		            	ASN1Object pkcs7Object=TestControFirma.detectInputStreamContent(new FileInputStream(f));
		            	FileOutputStream outputStream= new FileOutputStream(output);
		            	TestControFirma.addCounterSignature(pkcs7Object, 
		            			certificate,
		            			null, 
		            			privateKey,
		            			signeralg,
		            			provider, outputStream);
		            }
		        }  
		    }catch(Exception e){
		    	e.printStackTrace();
		    	throw new SmartCardException(e);
		    }finally{
		    	try{
		    		provider.logout();
		    	}catch(Exception e){
		    		//Errore gestito
		    	}
		    }
		//************************************
		
		
		 
	}
	
	public static SunPKCS11 getProvider(char[]pin,int slot)throws Exception{
		LogWriter.writeLog("Inizio controllo provider disponibili");	
		 
		LoginException loginex = null;
		//Carico i provider
		File tmp = new File(AbstractSigner.dir);
		File directory = new File(tmp+File.separator+"dllCrypto");
		File[] dll = directory.listFiles();
		sun.security.pkcs11.SunPKCS11 provider = null;
		for(int i=0;i<dll.length;i++){
			if(dll[i].isFile() && dll[i].getName().startsWith("libsofthsm")){	
				try{
					String os = System.getProperty("os.name");
					String osname = "windows";
					if(os.toLowerCase().startsWith("window")){
						 osname = "windows";
					 }else{
						 osname = "linux";
					}
					if(osname.equals("windows") || (osname.equals("linux") && dll[i].getAbsolutePath().toLowerCase().endsWith("so"))){
						System.out.println(dll[i].getAbsolutePath());
				    	StringBuffer cardConfig=new StringBuffer();
				        cardConfig.append ("name=SmartCards \n");
				        cardConfig.append ("slot="+slot+" \n");
				        cardConfig.append ("library="+dll[i].getAbsolutePath());
				        			        
				        ByteArrayInputStream confStream = new ByteArrayInputStream(cardConfig.toString().getBytes());
						provider = new sun.security.pkcs11.SunPKCS11(confStream);
							
						LogWriter.writeLog("File Provider:"+provider.getInfo().toLowerCase().trim());
						 
						Security.addProvider(provider); 
										
						//Tento una login per vedere se ho caricato la dll corretta
						provider.login(new Subject(), new PasswordHandler(pin));
						
						LogWriter.writeLog("Provider caricato!");
						break;
					}
				}catch(LoginException ex){
					 
					loginex = ex;
				}catch(Exception e){
					e.printStackTrace();//debug
					 
					LogWriter.writeLog("DLL non corretta tento con la successiva!");
				}
			}
		}
		return provider;
	}
	/**
	 * Il contenuto dello stream "bitStream" e, nel caso in cui sia un PKCS7, lo
	 * trasforma in un oggetto ASN1 settato nella variabile
	 * pkcs7StreamAsASN1Object.
	 * 
	 * @return ASN1ObjectIdentifier OID della busta PKCS7
	 */
	private static ASN1Object detectInputStreamContent(InputStream stream) {
		LogWriter.writeLog("detectInputStreamContent ");
		LogWriter.writeLog("Starting");
		ASN1ObjectIdentifier oid = null;
		try {
			//this.getBitStream().mark(0);
			ASN1InputStream asnInputStream = new ASN1InputStream(stream);
 
			// Se non è una sequence lancia una eccezione
			BERSequence asn1Object = (BERSequence) asnInputStream.readObject();
			int cN = asn1Object.size();
			for (int index = 0; index < cN; index++)
				LogWriter.writeLog("asn1Stream content = "
						+ asn1Object.getObjectAt(index));
			if (cN > 0) {
				oid = (ASN1ObjectIdentifier) asn1Object.getObjectAt(0);
				//this.setPKCS7StreamAsASN1Object(asn1Object);
				System.out.println("identificato oggetto "+oid);
				return asn1Object;
			}
		} catch (Throwable e) {
			LogWriter.writeLog(e.getMessage(), e);
		}
//		try {
//			this.getBitStream().reset();
//		} catch (IOException ioe) {
//			LogWriter.writeLog("Error reset InputStream", ioe);
//		}
		return null;
	} 
	
	 
	/**
	 * Aggiunge una controfirma cades ad un firma applicata all'oggetto ASN1 e
	 * individuabile dal certificato aCertToBeSign presente nella variabile
	 * pkcs7StreamAsASN1Object. Il dato firmato viene restituito in formato DER.
	 * 
	 * @param aCert
	 *            corrispondente alla chiave con cui apporre la controfirma.
	 * @param aCertToBeSign
	 *            corrispondente alla firma da controfirmare.
	 */
	private void addCounterSignatureCades(ASN1Object pkcs7Object,
			X509Certificate aCert,
			X509Certificate aCertToBeSign,
			PrivateKey signatureKey,
			String signerAlgorithm,
			Provider provider,
			OutputStream outputStream) {
		//LogWriter.writeLog("Controfirma di: " + aCertToBeSign.getIssuerX500Principal());
		LogWriter.writeLog("con:            " + aCert.getIssuerX500Principal());
		try {
			// Non è possibile rileggere lo stream di input, se serve bisogna
			// utilizzare un ResettableFileInputStream
			CMSSignedData signedData = new CMSSignedData(pkcs7Object.getEncoded());

			SignerInformation signerInfoToBeCounterSign =  findSignerInfoFor(signedData, aCertToBeSign);

			if (signerInfoToBeCounterSign == null)
				return;

			// Recupera il digest dalla firma originale
			byte[] digest = this.getDigest(
					signerInfoToBeCounterSign.getSignature(),
					 "sha256");

			List<X509Certificate> certList = new ArrayList<X509Certificate>();
			certList.add(aCert);
			Store store = new JcaCertStore(certList);

			// Settaggio attributi

			// 1. Date
			Date d = new Date();
			Attribute a1 = new Attribute(CMSAttributes.signingTime, new DERSet(
					new Time(d)));

			// 2. Hash dei dati
			Attribute a2 = new Attribute(
					org.bouncycastle.asn1.cms.CMSAttributes.messageDigest,
					new DERSet(new DEROctetString(digest)));

			Attribute a3 = null;
			IssuerSerial issSerial = null;
//			IssuerSerial issSerial = new IssuerSerial(new GeneralNames(
//					new DERSequence(new GeneralName(
//							org.bouncycastle.jce.PrincipalUtil
//									.getIssuerX509Principal(aCert)))),
//					new DERInteger(aCert.getSerialNumber()));
			if (this.digestAlgorithmId.getId().equals(
					org.bouncycastle.cms.CMSSignedDataGenerator.DIGEST_SHA1)) {
				MessageDigest msgDigest = java.security.MessageDigest
						.getInstance(CMSSignedGenerator.DIGEST_SHA1, "BC");
				ESSCertID essCertid = new ESSCertID(msgDigest.digest(aCert
						.getEncoded()), issSerial);
//				a3 = new Attribute(SignedData.id_aa_signingCertificate,
//						new DERSet(new SigningCertificate(essCertid)));
			} else if (this.digestAlgorithmId.getId().equalsIgnoreCase(
					org.bouncycastle.cms.CMSSignedDataGenerator.DIGEST_SHA256)) {
				MessageDigest msgDigest = java.security.MessageDigest
						.getInstance(CMSSignedGenerator.DIGEST_SHA256, "BC");
				ESSCertIDv2 essCertidv2 = new ESSCertIDv2(
						new AlgorithmIdentifier(
								CMSSignedDataGenerator.DIGEST_SHA256),
						msgDigest.digest(aCert.getEncoded()), issSerial);
				ESSCertIDv2[] essCertArray = new ESSCertIDv2[1];
				essCertArray[0] = essCertidv2;
				a3 = new org.bouncycastle.asn1.cms.Attribute(
						org.bouncycastle.asn1.pkcs.SignedData.id_aa_signingCertificateV2,
						new org.bouncycastle.asn1.DERSet(
								new SigningCertificateV2(essCertArray)));
			}

			ASN1EncodableVector hashTabAtt = new ASN1EncodableVector();
			hashTabAtt.add(a1);
			hashTabAtt.add(a2);
			hashTabAtt.add(a3);

			DefaultSignedAttributeTableGenerator dsat = new DefaultSignedAttributeTableGenerator(
					new AttributeTable(hashTabAtt));

			// 4. Genera la firma
			ContentSigner signer = null;
//			ContentSigner signer = new JcaContentSignerBuilder(signerAlgorithm)
//					.setProvider(p11Session.getProvider().getName()).build(
//							this.signatureKey);
			DigestCalculatorProvider dcp = new JcaDigestCalculatorProviderBuilder()
					.setProvider("BC").build();
			JcaSignerInfoGeneratorBuilder sigb = new JcaSignerInfoGeneratorBuilder(
					dcp);
			sigb.setSignedAttributeGenerator(dsat);
			SignerInfoGenerator sig = sigb.build(signer, aCert);

			CMSSignedDataGenerator generator = new CMSSignedDataGenerator();
			generator.addSignerInfoGenerator(sig);
			generator.addCertificates(store);

			SignerInformationStore newSignerInformationStore = generator
					.generateCounterSigners(signerInfoToBeCounterSign);
			SignerInformation signerInfoCounterSign = SignerInformation
					.addCounterSigners(signerInfoToBeCounterSign,
							newSignerInformationStore);

			// Nuovo elenco di firmatari....
			Collection<SignerInformation> signers = signedData.getSignerInfos()
					.getSigners();

			signers.remove(signerInfoToBeCounterSign);
			signers.add(signerInfoCounterSign);

			// Nuovo elenco certificati
			Collection newCertificates = store.getMatches(null);
			Collection certificates = signedData.getCertificates().getMatches(
					null);
			certificates.addAll(newCertificates);

			SignerInformationStore sis = new SignerInformationStore(signers);
			JcaCertStore jcs = new JcaCertStore(certificates);

			signedData = CMSSignedData.replaceSigners(signedData, sis);
			signedData = CMSSignedData.replaceCertificatesAndCRLs(signedData,
					jcs, null, null);

//			this.getPKCS7Stream().write(signedData.getEncoded());
//			this.getPKCS7Stream().flush();
//			this.getPKCS7Stream().close();
//			this.getBitStream().reset();
		} catch (Throwable e) {
			LogWriter.writeLog(e.getMessage(), e);
		}
		LogWriter.writeLog("End");
	} // End addCounterSignatureCades
	
	private static void addCounterSignature(ASN1Object pkcs7Object,
			X509Certificate aCert,
			X509Certificate aCertToBeSign,
			PrivateKey signatureKey,
			String signerAlgorithm,
			Provider provider,
			OutputStream outputStream) {
		//LogWriter.writeLog("Controfirma di: " + aCertToBeSign.getIssuerX500Principal());
		LogWriter.writeLog("con:            " + aCert.getIssuerX500Principal());
		try {
			 
			CMSSignedData signedData = new CMSSignedData(pkcs7Object.getEncoded());

			SignerInformation signerInfoToBeCounterSign = findSignerInfoFor(signedData, aCertToBeSign);

			if (signerInfoToBeCounterSign == null)
				return;

			// Recupera il digest dalla firma originale
			byte[] digest = getDigest(
					signerInfoToBeCounterSign.getSignature(),
					"sha256");

			List<X509Certificate> certList = new ArrayList<X509Certificate>();
			certList.add(aCert);
			Store store = new JcaCertStore(certList);

			// Settaggio attributi

			// 1. Date
			Date d = new Date();
			Attribute a1 = new Attribute(CMSAttributes.signingTime, new DERSet(
					new Time(d)));

			// 2. Hash dei dati
			Attribute a2 = new Attribute(
					org.bouncycastle.asn1.cms.CMSAttributes.messageDigest,
					new DERSet(new DEROctetString(digest)));

			ASN1EncodableVector hashTabAtt = new ASN1EncodableVector();
			hashTabAtt.add(a1);
			hashTabAtt.add(a2);

			DefaultSignedAttributeTableGenerator dsat = new DefaultSignedAttributeTableGenerator(
					new AttributeTable(hashTabAtt));

			// 4. Genera la firma
			ContentSigner signer = new JcaContentSignerBuilder(signerAlgorithm)
					.setProvider(provider ).build(
							 signatureKey);
			DigestCalculatorProvider dcp = new JcaDigestCalculatorProviderBuilder()
					.setProvider("BC").build();
			JcaSignerInfoGeneratorBuilder sigb = new JcaSignerInfoGeneratorBuilder(
					dcp);
			sigb.setSignedAttributeGenerator(dsat);
			SignerInfoGenerator sig = sigb.build(signer, aCert);

			CMSSignedDataGenerator generator = new CMSSignedDataGenerator();
			generator.addSignerInfoGenerator(sig);
			generator.addCertificates(store);

			SignerInformationStore newSignerInformationStore = generator
					.generateCounterSigners(signerInfoToBeCounterSign);
			SignerInformation signerInfoCounterSign = SignerInformation
					.addCounterSigners(signerInfoToBeCounterSign,
							newSignerInformationStore);

			// Nuovo elenco di firmatari....
			Collection<SignerInformation> signers = signedData.getSignerInfos()
					.getSigners();

			signers.remove(signerInfoToBeCounterSign);
			signers.add(signerInfoCounterSign);

			// Nuovo elenco certificati
			Collection newCertificates = store.getMatches(null);
			Collection certificates = signedData.getCertificates().getMatches(
					null);
			certificates.addAll(newCertificates);

			SignerInformationStore sis = new SignerInformationStore(signers);
			JcaCertStore jcs = new JcaCertStore(certificates);

			signedData = CMSSignedData.replaceSigners(signedData, sis);
			signedData = CMSSignedData.replaceCertificatesAndCRLs(signedData,
					jcs, null, null);
			
			outputStream.write(signedData.getEncoded());
			outputStream.flush();
			outputStream.close();
			//outputStream.reset();
		} catch (Throwable e) {
			LogWriter.writeLog(e.getMessage(), e);
		}
		LogWriter.writeLog("End");
	} // End addCounterSig
	
	/**
	 * Verifica se in un campo SignedData di busta PKCS7 è già presente una
	 * firma effettuta con la chiave associata al certificato aCertToBeSign
	 * 
	 * @param aSignedData
	 *            CMSSignedData
	 * @param aCertToBeSign
	 *            Certificato
	 * @return SignerInformation se trova la firma, null in caso contrario.
	 */
	private static SignerInformation findSignerInfoFor(CMSSignedData aSignedData,
			X509Certificate aCertToBeSign) {
		LogWriter.writeLog("existSignerInfoFor ");
		LogWriter.writeLog("starting");
		try {
			SignerInformationStore sInfos = aSignedData.getSignerInfos();
			Store store = aSignedData.getCertificates();
			Collection c = sInfos.getSigners();
			Iterator it = c.iterator();
			JcaX509CertificateConverter converter = new JcaX509CertificateConverter();
			while (it.hasNext()) {
				SignerInformation signer = (SignerInformation) it.next();
				Collection certCollection = store.getMatches(signer.getSID());
				Iterator certIt = certCollection.iterator();
				X509CertificateHolder bccert = (X509CertificateHolder) certIt
						.next();
				X509Certificate signCer = converter.setProvider("BC")
						.getCertificate(bccert);
				//per testare la controfirma ritorno i primo non quello che matcha
				return signer;
//				if (signCer.equals(aCertToBeSign)) {
//					LogWriter.writeLog("Signature present ["
//							+ aCertToBeSign.getIssuerX500Principal().getName()
//							+ "] [" + aCertToBeSign.getSerialNumber() + "]");
//					return signer;
//
//				}
			}
			return null;
		} catch (Throwable e) { 
			LogWriter.writeLog(e.toString(), e);
			return null;
		}

	} 
	
	/**
	 * Calcola il digest di un byte array
	 * 
	 * @param aByteArray
	 *            dati da elaborare
	 * @param digestAlgorithm
	 *            algoritmo di digest da utilizzare
	 * @return l'hash calcolato
	 */
	private static  byte[] getDigest(byte[] aByteArray, String digestAlgorithm) {
		LogWriter.writeLog("getDigest ByteArray ");
		try {
			LogWriter.writeLog("Starting");
			MessageDigest digestEngine = MessageDigest
					.getInstance(digestAlgorithm);
			ByteArrayOutputStream contentBuffer = new ByteArrayOutputStream();
			for (int index = 0; index < aByteArray.length; index++) {
				digestEngine.update(aByteArray[index]);
				contentBuffer.write(aByteArray[index]);
			}
			byte[] contentHash = digestEngine.digest();
			contentBuffer.close();
			return contentHash;
		} catch (Throwable e) {
			LogWriter.writeLog(e.getMessage(), e);
			 
		}
		return null;
	} // End getDigest

}
