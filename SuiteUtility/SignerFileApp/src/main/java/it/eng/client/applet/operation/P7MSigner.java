package it.eng.client.applet.operation;



import it.eng.client.applet.bean.PrivateKeyAndCert;
import it.eng.client.applet.operation.detector.P7MDetector;
import it.eng.client.applet.operation.jsignpdf.HashAlgorithm;
import it.eng.client.applet.operation.ts.TimeStamperUtility;
import it.eng.client.applet.util.PreferenceKeys;
import it.eng.client.applet.util.PreferenceManager;
import it.eng.client.applet.util.ProxyUtil;
import it.eng.client.data.SignerTypeUtil;
import it.eng.common.CMSUtils;
import it.eng.common.LogWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.AuthProvider;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.CMSSignedDataParser;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.util.Store;

public class P7MSigner implements ISigner  {
	 
	//dati di default
	private String digestAlg=CMSSignedDataGenerator.DIGEST_SHA1;
//	private String signerAlg="SHA256WITHRSA";

	public P7MSigner(){

	}

	public P7MSigner(String digestAlg, String signerAlg) {

//		this.digestAlg=digestAlg;
//		this.signerAlg=signerAlg;
	}

	public P7MSigner(String digestAlg) {
		//this.digestAlg=digestAlg;
		this.digestAlg = selectAlg().getAlgorithmName();
	}

	//	public void firmaDetached(File inputFile,File outputFile,PrivateKeyAndCert pvc,Provider provider,boolean timemark) throws Exception{
	//		
	//		FileInputStream is = new FileInputStream(inputFile);
	//		//String fileName=FilenameUtils.getBaseName(inputFile)+".p7m";
	//        OutputStream bOut=FileUtils.openOutputStream(outputFile);
	//		java.security.cert.X509Certificate cert = pvc.getCertificate();
	//        PrivateKey key = pvc.getPrivateKey();
	//        //Certificate[] chain = keyStore.getCertificateChain(alias);
	//        Certificate[] list= new Certificate[1];
	//        list[0]=cert;
	//        long timeStart=System.currentTimeMillis();
	//        CertStore certStore = CertStore.getInstance("Collection",new CollectionCertStoreParameters(Arrays.asList(list)));
	//        CMSSignedDataStreamGenerator gen = new CMSSignedDataStreamGenerator();
	//        gen.addSigner(key, (X509Certificate) cert, CMSSignedDataGenerator.DIGEST_SHA1, provider);
	//        gen.addCertificatesAndCRLs(certStore);
	//        OutputStream sigOut = gen.open(bOut,false);
	//        IOUtils.copyLarge(is, sigOut);
	//        IOUtils.closeQuietly(sigOut);
	//        IOUtils.closeQuietly(bOut);
	//        IOUtils.closeQuietly(is);
	//        //FileUtils.writeByteArrayToFile(File.createTempFile("second", ""), detached.getEncoded());  
	//	}

	/**
	 * calcola la firma detached del file pasato in ingresso restituendo una struttura CMSSigneData
	 * @param inputFile
	 * @param pvc chiave e certificati 
	 * @param provider provider 
	 * @param timemark se effettuare la marcatura temporale
	 * @return
	 * @throws Exception
	 */
	public CMSSignedData firmaDetached(File inputFile,PrivateKeyAndCert pvc,Provider provider,boolean timemark) throws Exception{
		byte[] hash = calcolaHash(FileUtils.openInputStream(inputFile));
		CMSSignedData res = firmaDetached(hash, pvc, provider, timemark);
		return res;
	}
	/**
	 * analogo a
	 *  {@link P7MSigner#firmaDetached(File, PrivateKeyAndCert, Provider, boolean)} ma per i bytes
	 * @param hash
	 * @param pvc
	 * @param provider
	 * @param timemark
	 * @return
	 * @throws Exception
	 */
	public CMSSignedData firmaDetached(byte[] hash,PrivateKeyAndCert pvc,Provider provider,boolean timemark) throws Exception{
		Certificate[] list= new Certificate[1];
		list[0]=pvc.getCertificate();
		CertStore certStore = CertStore.getInstance("Collection",new CollectionCertStoreParameters(Arrays.asList(list)));
		CMSSignedHashGenerator gen2 = new CMSSignedHashGenerator();
		
		LogWriter.writeLog("hash " + digestAlg );
		gen2.addSigner(pvc.getPrivateKey(), (X509Certificate) pvc.getCertificate(), digestAlg, null,null);
		gen2.addCertificatesAndCRLs(certStore);

		CMSSignedData signedData= gen2.generateDetached(hash, provider);
		if(timemark){
			ProxyUtil.initProxyConnection();
			//aggiungo la marca
			//TODO  non devi chedere una marca per ogni signerinfo devi usare la stessa calcolata
			//magari sulle signature totali o su una specifica
			Collection signers=signedData.getSignerInfos().getSigners();
			List<SignerInformation> newSigners=new ArrayList<SignerInformation>();
			for (Object signer: signers) {
				if(signer instanceof SignerInformation){
					newSigners.add(TimeStamperUtility.addMarca((SignerInformation)signer));
				}
			}
			signedData=CMSSignedData.replaceSigners(signedData, new SignerInformationStore(newSigners));
		}
		return signedData;
	}


	/**
	 * Poiche l'rfc 5126 indica che: "The value of the messageImprint field within TimeStampToken shall be
   a hash of the value of the signature field within SignerInfo for the
   signedData being time-stamped."
   per firmare e marcare dovresti prima generare la firma e poi marcare i signerinfo contenuti
   se il file è di grandi dimensioni conviene generare la firma detached modificarla aggiungendo la marca 
   ed infine riattaccarla al contenuto.
	 * @param inputFile
	 * @param outputStream
	 * @param pvc
	 * @param provider
	 * @param timemark
	 * @throws Exception
	 */
	public boolean firma(File inputFile,OutputStream outputStream,PrivateKeyAndCert pvc,AuthProvider provider,boolean timemark,boolean detached,boolean congiunta,boolean isSigned)throws Exception{
		
		LogWriter.writeLog("Inizio metodo firma P7M - timemark = " + timemark + ", detached = " + detached +
				", congiunta = " + congiunta+", isSigned = " + isSigned );
		
	//	java.security.cert.X509Certificate cert = pvc.getCertificate();
//		PrivateKey key = pvc.getPrivateKey(); 
//		
//
//		Certificate[] list= new Certificate[1];
//		list[0]=cert;
//		CertStore certStore = CertStore.getInstance("Collection",new CollectionCertStoreParameters(Arrays.asList(list)));
		//se vuoi farla congiunta devi fare l'hash dello sbustato
		CMSSignedData signedData;
		//la firma congiunta vuol dire che sostituiamo i signer attuali con gli stessi più il nuovo
		if( congiunta ){
			//si suppone che il file sia già firmato 
			//FIXME make a check file signed
			P7MDetector detc= new P7MDetector();
			//        	creo l'hash dello sbustato
			 
			byte[] hash=calcolaHash( detc.getContent(inputFile) );
			//creo la busta detached dello sbustato con la nuova firma
			signedData=firmaDetached(hash, pvc, provider, false);
			//aggiungo la nuova firma a quelle pre-esistenti
			CMSUtils.addSignerInfo(inputFile, signedData, outputStream);
			return true;
			
		} else{
			signedData = firmaDetached(inputFile, pvc, provider, timemark);
		}
		//attacco il file alla busta
		if( detached ) {
			//suppongo che la firma detached puoi sempre gestirla inmemoria
			IOUtils.copyLarge(new ByteArrayInputStream(signedData.getEncoded()), outputStream);
			//FileUtils.writeByteArrayToFile(File.createTempFile("second", ""), signedData.getEncoded());  
		} else{
			SignerTypeUtil.attach(FileUtils.openInputStream(inputFile), signedData, outputStream);
		}
		return true;

		//        CMSSignedDataStreamGenerator gen = new CMSSignedDataStreamGenerator();
		//        //attributi non firmati per fare buste di formati diversi CADES-T etc.
		//        ASN1EncodableVector unsignetAttrV = new ASN1EncodableVector();
		//        Configuration config=PreferenceManager.getConfiguration();
		//        
		//	    AttributeTable unsignetAttrTable = new AttributeTable(unsignetAttrV);
		//        gen.addSigner(key, (X509Certificate) cert, digestAlg, null,unsignetAttrTable,provider);
		//        gen.addCertificatesAndCRLs(certStore);
		//        OutputStream sigOut = gen.open(outputStream,true);
		//        
		//        InputStream inputStream=FileUtils.openInputStream(inputFile);
		//        IOUtils.copyLarge(inputStream, sigOut);
		//      
		//        IOUtils.closeQuietly(sigOut);
		//        IOUtils.closeQuietly(outputStream);
		//        IOUtils.closeQuietly(inputStream);

	}
	/**
	 * aggiunge una controfirma ad un file firmato 
	 * @param input file in ingresso
	 * @param outputStream dove scrivere il risultato 
	 * @param pvc
	 * @param aCertToBeSign
	 * @param provider
	 */
	public  void addCounterSignature(File input,
			OutputStream outputStream,
			PrivateKeyAndCert pvc,
			X509Certificate aCertToBeSign, 
			AuthProvider provider
			) {
		LogWriter.writeLog("start controfirma");
		try {
			 
			//CMSSignedData signedData = new CMSSignedData(pkcs7Object.getEncoded());
			InputStream is= FileUtils.openInputStream(input);
			CMSSignedDataParser signedData = new CMSSignedDataParser(is);
			signedData.getSignedContent().drain();
			SignerInformation signerInfoToBeCounterSign=null;
			boolean countersignall=aCertToBeSign==null;//se nonindichi il cert da controfirmare controfirma tutti
			byte[] hashCtr=null;//byte di cui fare l'hash da controfirmare
			if(aCertToBeSign==null){
				//faccio l'hash di tutti le signature
				//TODO fare solo quello delle valide!?
				Collection coll=signedData.getSignerInfos().getSigners();
				Iterator it = coll.iterator();
				ByteArrayOutputStream baos = new ByteArrayOutputStream( );
				 
				while (it.hasNext()) {
					SignerInformation signer = (SignerInformation) it.next();
					baos.write(signer.getSignature());
				}
				hashCtr=baos.toByteArray();
			}else{
				signerInfoToBeCounterSign = findSignerInfoFor(signedData, aCertToBeSign);
				hashCtr=signerInfoToBeCounterSign.getSignature();
			}
			 

			// Recupera il digest dei bytes  da controfirmare
			byte[] digest =  calcolaHash(hashCtr);
			//genero la busta detached per la controfirma
			CMSSignedData ctrFirm=firmaDetached(digest, pvc, provider, false);
			List<X509Certificate> certList = new ArrayList<X509Certificate>();
			certList.add(pvc.getCertificate());
			Store store = new JcaCertStore(certList);
			 
			//elenco attuale da sostituire
			Collection<SignerInformation> signers = signedData.getSignerInfos().getSigners();
			
			if(signerInfoToBeCounterSign==null){
				//controfirma tutti
				ArrayList<SignerInformation> temp=new ArrayList<SignerInformation>();
				//itero gli attuali 
				Iterator it = signers.iterator();
				while (it.hasNext()) {
					SignerInformation signerTOCounterSign = (SignerInformation) it.next();
					SignerInformationStore newSignerInformationStore = ctrFirm.getSignerInfos();
					SignerInformation signerInfoCounterSign = SignerInformation.addCounterSigners(signerTOCounterSign,
							newSignerInformationStore);
					//memorizzo il nuovo
					temp.add(signerInfoCounterSign);
				}
				//cancello i vecchi per i nuovi
				signers.clear();
				signers.addAll(temp);
				
			}else{
				SignerInformationStore newSignerInformationStore = ctrFirm.getSignerInfos();
				SignerInformation signerInfoCounterSign = SignerInformation
						.addCounterSigners(signerInfoToBeCounterSign,
								newSignerInformationStore);
	
				// Nuovo elenco di firmatari....
				
	
				signers.remove(signerInfoToBeCounterSign);
				signers.add(signerInfoCounterSign);
			}
			// Nuovo elenco certificati
			Collection newCertificates = store.getMatches(null);
			Collection certificates = signedData.getCertificates().getMatches(null);
			certificates.addAll(newCertificates);

			SignerInformationStore sis = new SignerInformationStore(signers);
			JcaCertStore jcs = new JcaCertStore(certificates);
			
//			OutputStream temp= CMSSignedDataParser.replaceSigners(FileUtils.openInputStream(input), sis,outputStream);
//			 
//			output = CMSSignedDataParser.replaceCertificatesAndCRLs(temp, jcs, output);
			InputStream is2=FileUtils.openInputStream(input);
			CMSUtils.replaceSignerAndCertificatesAndCRLs(is2, sis, jcs,null,null, outputStream) ;
			outputStream.flush();
			IOUtils.closeQuietly(is);IOUtils.closeQuietly(is2);
			//close or open ?
			IOUtils.closeQuietly(outputStream);
			 
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
	private   SignerInformation findSignerInfoFor(CMSSignedDataParser aSignedData,
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
				
				if (signCer.equals(aCertToBeSign)) {
					LogWriter.writeLog("Signature present ["
							+ aCertToBeSign.getIssuerX500Principal().getName()
							+ "] [" + aCertToBeSign.getSerialNumber() + "]");
					return signer;

				}
			}
			return null;
		} catch (Throwable e) { 
			LogWriter.writeLog("findSignerInfoFor err:"+e.toString(), e);
			return null;
		}

	} 
	
	private byte[] calcolaHash(byte[] bytes){
		if(getDigestAlg().equals(CMSSignedDataGenerator.DIGEST_SHA256)){
			return DigestUtils.sha256(bytes);
		}else  {
			return DigestUtils.sha256(bytes);
		}
	}
	
	private byte[] calcolaHash(InputStream is)throws Exception{
		LogWriter.writeLog("digestAlg " + digestAlg);
		if(digestAlg.equals(CMSSignedDataGenerator.DIGEST_SHA256)){
			return DigestUtils.sha256(is);
		}else  {
			return DigestUtils.sha(is);
		}
	}
	public String getDigestAlg() {
		return digestAlg;
	}

	public void setDigestAlg(String digestAlg) {
		this.digestAlg = digestAlg;
	}

//	public String getSignerAlg() {
//		return signerAlg;
//	}
//
//	public void setSignerAlg(String signerAlg) {
//		this.signerAlg = signerAlg;
//	}	
	
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



}
