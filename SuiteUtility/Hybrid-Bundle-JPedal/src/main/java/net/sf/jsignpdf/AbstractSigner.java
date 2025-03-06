package net.sf.jsignpdf;

import it.eng.hybrid.module.jpedal.util.ValidationInfos;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.bouncycastle.cms.CMSException;
import org.bouncycastle.tsp.TimeStampToken;


/**
 * Classe di base per l'esecuzione delle operazioni di estrazione, analisi 
 * e validazione di firme digitali.
 *  
 * Fornisce gi� i metodi per il controllo della corrispondenza tra marche 
 * temporali e contenuto firmato ({@link checkTimeStampTokenOverRequest})
 * e l�estrazione del contenuto della busta su file ({@link getContentAsFile}). 
 * I metodi astratti da implementare sono i seguenti:
 * <ul>
 * 	<li>isSignedType: effettua l�istanziazione delle strutture di supporto e restituisce true se il file analizzato � riconosciuto</li>
 * 	<li>getFormat: restituisce il formato della busta nel caso sia stato riconosciuto</li>
 * 	<li>getTimeStampTokens: recupera le marche temporali contenute</li>
 * 	<li>getUnsignedContent: restituisce il contenuto sbustato</li>
 * 	<li>canContentBeSigned: restituisce true se il contenuto sbustato pu� a sua volta essere firmato (nel caso di firme multiple esterne)</li>
 *  <li>getSignatures: recupera le firme contenute all�interno della busta. Ciascuna firma appartiene ad una classe che implementa l�interfaccia ISignature: contiene al suo interno la lista delle eventuali controfirme e il metodo di validazione (sia sul contenuto embedded che su file detached - qualora previsto ).</li>
 *  <li>validateTimeStampTokensEmbedded / validateTimeStampTokensDetached: implementano le validazioni delle marche temporali interne o detached</li>
 * </ul>
 * Una classe estendente inoltre deve sovrascrivere uno dei metodi: {@link validateTimeStampTokensEmbedded} o {@link validateTimeStampTokensDetached}
 * a seconda se il conenuto firmato sia all'interno della busta o esterno.
 * @author Stefano Zennaro
 *
 */
public abstract class AbstractSigner{
	
	protected int maxByteRead=100*1000*1000;
	/*
	 * CAMPI
	 */

		/**
		 * File di firma con/senza contenuto
		 */
		protected File file;
		
		/**
		 * Contenuto detached
		 */
		protected List<File> detachedFiles;
		
		/**
		 * File precedentemente sbustato
		 */
		//protected File alreadyExtractedFile;
		
		/**
		 * Marche temporali contenute nella busta
		 */
		protected TimeStampToken[] timestamptokens = null;
		
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

	/*
	 * METODI ASTRATTI (da implementare nelle sottoclassi)
	 */

		/**
		 * Recupera il formato della busta nel caso sia stato riconosciuto
		 * @return
		 */
		public abstract SignerType getFormat();
	
		/**
		 * Controlla se il file contiene firme in formato riconosciuto
		 * @param file
		 * @return true se il vi sono firme con formato riconosciuto
		 */
		public abstract boolean isSignedType(File file);
		
		/**
		 * Ritorna il timestamptoken se presente. 
		 * Se il token non esiste ritorna null.
		 * @return
		 */
		//public abstract TimeStampToken[] getTimeStampTokens();

		
		public void reset() {
			timestamptokens = null;
		}
		
		/**
		 * Ritorna il contenuto non firmato
		 * @return
		 */
		public abstract InputStream getUnsignedContent();
		
		/**
		 * Restituisce true se il contenuto sbustato può a sua volta essere firmato (nel caso di firme multiple esterne)
		 * @return
		 */
		//public abstract boolean canContentBeSigned();
		
		/**
		 *  Recupera l'hash del contenuto utilizzando
		 *  l'algorimo di digest passato in ingresso
		 *  - ogni firma presente può utilizzare un proprio algoritmo di digest
		 */
		//public abstract byte[] getUnsignedContentDigest(MessageDigest digestAlgorithm);
		
		/**
		 * Recupera le firme apposte
		 * @throws CMSException 
		 */
		//public abstract List<ISignature> getSignatures();
	
		/**
		 * Recupera le CRL incluse all'interno del
		 * file firmato (solo per i formati di firma
		 * che lo prevedono)
		 */
		//public abstract Collection<CRL> getEmbeddedCRLs();
		
		/**
		 * Recupera i certificati inclusi all'interno del
		 * file firmato (solo per i formati di firma
		 * che lo prevedono)
		 */
		//public abstract Collection<? extends Certificate> getEmbeddedCertificates();
	/*
	 * METODI PUBBLICI
	 */
		
	/**
	 * Recupera il file di firma
	 */
	public File getFile() {
		return file;
	}

	/**
	 * Definisce il file di firma
	 * @param file
	 */
	public void setFile(File file) {
		this.file = file;
		//this.alreadyExtractedFile = null;
	}
	
	/**
	 * Esegue la validazione di marche temporali embedded
	 * 
	 * @return le informazioni sull'esito della validazione 
	 * @throws CMSException 
	 */
	public ValidationInfos validateTimeStampTokensEmbedded() throws CMSException {
		return null;
	}
	
	/**
	 * Esegue la validazione di marche temporali detached
	 * 
	 * @return le informazioni sull'esito della validazione 
	 */
	public ValidationInfos validateTimeStampTokensDetached(File attachedFile) {
		return null;
	}
	
	/**
	 * Metodo di utilità che esegue la validazione di un timestamptoken rispetto al 
	 * messaggio di request contenente l'hashmap del contenuto da marcare. 
	 * @param validationInfos struttura che raccoglie le informazioni riguardo all'esito
	 * della validazione del timestamp
	 * @param timestamptoken token contenente la marca temporale 
	 * @param request classe contenente l'implementazione della richiesta di emissione di 
	 * una marca temporale, secondo le specifiche descritte in RFC3161
	 */
//	protected void checkTimeStampTokenOverRequest(ValidationInfos validationInfos, TimeStampToken timestamptoken, TimeStampRequest request) {
//		
//		try {
//			
//			PKIStatusInfo paramPKIStatusInfo  = new PKIStatusInfo(0);
//			
//			ASN1InputStream  aIn = new ASN1InputStream(timestamptoken.getEncoded());
//            ASN1Sequence      seq = (ASN1Sequence)aIn.readObject();
//			ContentInfo paramContentInfo = new ContentInfo(seq);
//			TimeStampResp tsr = new TimeStampResp(paramPKIStatusInfo, paramContentInfo);
//			TimeStampResponse response = new TimeStampResponse(tsr);
//			
//			checkTimeStampRequestOverTimeStampResponse(validationInfos, timestamptoken, request, response);
//			
//		} catch (IOException e) {
//			validationInfos.addError("Il token non contiene una marca temporale valida");
//		} catch (TSPException e) {
//			validationInfos.addError(e.getMessage());
//		} 
//	}
	
//	private void checkTimeStampRequestOverTimeStampResponse(ValidationInfos validationInfos, TimeStampToken timestamptoken, TimeStampRequest request, TimeStampResponse response) {
//		
//		String digestAlgorithmOID  = null;
//		/*
//		 * Verifica che la marca temporale sia effettivamente associata alla request
//		 */
//		try {
//			response.validate(request);
//		}catch (TSPException e) {
//					validationInfos.addError(e.getMessage());
//		}
//		
//		/*
//		 * Occorre quindi verificare che il timestamp sia stato effettivamente
//		 * calcolato a partire dall'impronta del file in ingresso, cioè:
//		 *   SignerInfo.digestAlgorithm(ContentInfo.Content / ContentInfo.signedAttributes) = SignerInfo.signaturealgorithm^-1(SignerInfo.cid.publickey, SignerInfo.Signature)
//		 */
//		try {
//			
//			CMSSignedData 	cms = timestamptoken.toCMSSignedData();
//			CertStore 	certStore = timestamptoken.getCertificatesAndCRLs("Collection", "BC");
//						
//			Collection<?> saCertificates =  (Collection<?>)certStore.getCertificates(null);
//			if (saCertificates==null)
//				throw new Exception("Il certificato di TSA non risulta presente");
//			
//			Object certificate = saCertificates.iterator().next();
//			if (certificate==null)
//				throw new Exception("Il certificato di TSA non risulta presente");
//			
//			PublicKey 	publicKey = null;
//			if(certificate instanceof X509CertificateHolder){
//				Certificate certificateM = new JcaX509CertificateConverter().setProvider(BouncyCastleProvider.PROVIDER_NAME).getCertificate((X509CertificateHolder)certificate);
//				publicKey = certificateM.getPublicKey();
//			}else{
//				publicKey = ((Certificate)certificate).getPublicKey();
//			}
//						
//			if (publicKey==null)
//				throw new Exception("La publicKey della TSA non risulta presente");
//	
//			Collection<SignerInformation> signers = (Collection<SignerInformation>)cms.getSignerInfos().getSigners();
//			SignerInformation signerInfo = signers.iterator().next();
//			digestAlgorithmOID = signerInfo.getDigestAlgOID();
//			MessageDigest contentDigestAlgorithm = MessageDigest.getInstance(digestAlgorithmOID);
//	
//			/*
//			 * I due byte array da verificare
//			 */
//			byte[] encodedDataToVerify = null;
//			byte[] encodedSignedData = null;
//			
//			/*
//			 * Verifica che il certificato sia corretto ripetto al firmatario
//			 *  - la public key � correttamente associata al contenuto firmato
//			 */
//			boolean certificateVerified = false;
//					
//			if (signerInfo.verify(new JcaSimpleSignerInfoVerifierBuilder().setProvider("BC").build(publicKey))) {
//				certificateVerified = true;
//			}
//			CMSProcessable signedContent = cms.getSignedContent();
//			byte[] originalContent  = (byte[]) signedContent.getContent();
//			
//			//log.debug("originalContent.length: "+ originalContent.length + " originalContent: " + SignerUtil.asHex(originalContent));
//			
//			/*
//			 * Controllo se occorre calcolare il digest dell'eContent oppure degli attributi firmati
//			 */
//			byte[] encodedSignedAttributes = signerInfo.getEncodedSignedAttributes();
//			if (encodedSignedAttributes!=null) {
//				encodedDataToVerify  = contentDigestAlgorithm.digest(encodedSignedAttributes);
//			} else {
//				encodedDataToVerify = contentDigestAlgorithm.digest((byte[])cms.getSignedContent().getContent());
//			}
//			
//			//log.debug("encodedDataToVerify: " + SignerUtil.asHex(encodedDataToVerify));
//			
//			//	Hash dell'econtent (da confrontare con l'hash del TSTInfo)
//			byte[] contentDigest  = signerInfo.getContentDigest();
//			/*
//			 * FIXME: tstInfo.getEncoded() è stato sostituito con getSignedContent().getContent() 
//			 * poich� nelle m7m la chiamata restituisce un errore 
//			 * - occorre verificare che i due metodi restituiscano lo stesso oggetto 
//			 * (attualmente la mancata verifica viene segnalata solo come warning)
//			 */
////			TSTInfo tstInfo = timestamptoken.getTimeStampInfo().toTSTInfo();
////			byte[] tstInfoEncoded = contentDigestAlgorithm.digest(tstInfo.getEncoded());
//			byte[] tstInfoEncoded = contentDigestAlgorithm.digest((byte[])cms.getSignedContent().getContent());
//			boolean contentVerified = Arrays.constantTimeAreEqual(contentDigest, tstInfoEncoded);
//			
//			digestAlgorithmOID = signerInfo.getEncryptionAlgOID();
//			byte[] signature = signerInfo.getSignature();
//			Cipher cipher = null;
//			try {
//				String algorithmName = null;
//				if (PKCSObjectIdentifiers.rsaEncryption.getId().equals(digestAlgorithmOID))
//					algorithmName = "RSA/ECB/PKCS1Padding";
//				else if (PKCSObjectIdentifiers.sha1WithRSAEncryption.getId().equals(digestAlgorithmOID))
//					algorithmName = "RSA/ECB/PKCS1Padding";
//				else
//					algorithmName = digestAlgorithmOID;
//				cipher = Cipher.getInstance(algorithmName);			
//			} catch (Throwable e) {
//				
//			}
//			if(cipher!=null){
//				try {
//					//log.debug("Cipher: " + cipher.getAlgorithm());
//					cipher.init(Cipher.DECRYPT_MODE, publicKey);
//					byte[] decryptedSignature = cipher.doFinal(signature);
//				
//					ASN1InputStream  asn1is = new ASN1InputStream(decryptedSignature);
//		            ASN1Sequence     asn1Seq = (ASN1Sequence)asn1is.readObject();
//	            	            	
//		            Enumeration<? extends DERObject> objs = asn1Seq.getObjects();
//		            while (objs.hasMoreElements()) {
//		            	DERObject derObject = objs.nextElement();
//	      			    if (derObject instanceof ASN1OctetString) {
//	      			    	ASN1OctetString octectString = (ASN1OctetString)derObject;
//	      			    	encodedSignedData = octectString.getOctets();
//	      			    	break;
//	      			    }
//		            }
//		            //log.debug("encodedSignedData: " + SignerUtil.asHex(encodedSignedData));
//		            boolean signatureVerified = Arrays.constantTimeAreEqual(encodedSignedData, encodedDataToVerify);
//		            
//		            
//		           // log.debug("Verifica timestampToken: certificateVerified = " + certificateVerified  +", signatureVerified="+signatureVerified+", contentVerified="+contentVerified);
//		            if (!certificateVerified)
//		            	validationInfos.addError("Il certificato non è valido");
//		            if (!signatureVerified)
//		            	validationInfos.addError("La firma non è valida: l'hash di contenuto + attributi è " + SignerUtil.asHex(encodedDataToVerify) + ", mentre la firma è stata apposta su contenuto + attributi con hash: " + SignerUtil.asHex(encodedSignedData));
//		            if (!contentVerified)
//		            	validationInfos.addWarning("Il contenuto non corrisponde a quanto firmato: previsto " + SignerUtil.asHex(tstInfoEncoded) + ", trovato " + SignerUtil.asHex(contentDigest));
//		            
//				} catch (Exception e) {
//					validationInfos.addWarning("Errore durante la verifica del timestamp: " + e.getMessage());
//				} 
//			}
//		} catch (IOException e) {
//			validationInfos.addError("Il token non contiene una marca temporale valida");
//		}catch (NoSuchAlgorithmException e) {
//			validationInfos.addError("Impossibile validare la marca poichè l'algoritmo di hashing non è supportato: " + digestAlgorithmOID );
//		} catch (Exception e) {
//			validationInfos.addError("Errore durante la validazione della marca temporale: " + e.getMessage() );
//		} 
//	}
	
	/**
	 * Recupera il contenuto sbustato sotto forma di InputStream.
	 * 
	 * 
	 * @return restituisce il file originario se si tratta di marche detached,
	 * altrimenti salva il contenuto sbustato in un file temporaneo e ne restituisce
	 * l'inputstream
	 * @throws IOException
	 */
	public InputStream getContentAsInputStream() throws IOException {
		// Se si tratta di una firma detached restituisco
		// il file a cui si riferisce
		File detachedFile = getDetachedFile();
		if (detachedFile!=null)
			return new FileInputStream(detachedFile);

		// Altrimenti occorre estrarre il contenuto..
		// Verifico se il nome del file contiene più estensioni
		// (ovvero estensione iniziale + firme: test.doc.p7m)
		// per preservare l'estensione originaria
		InputStream contentIS = getUnsignedContent();
		return contentIS;
		
	}

	public String getEnclosedEnvelopeExtension() {
		if (file == null)
			return null;
		String fileName = getFile().getName();
		String extension = null;
		StringTokenizer tokenizer = new StringTokenizer(fileName, ".");
		if (tokenizer.countTokens()>2) {
			tokenizer.nextToken();
			extension = "." + tokenizer.nextToken();
		}
		return extension;
	}

	/**
	 * Definisce il file detached a cui si riferiscono le marche temporali 
	 */
	public void setDetachedFile(File detachedFile) {
		if (detachedFiles == null)
			detachedFiles = new ArrayList<File>();
		detachedFiles.add(0, detachedFile);
	}
	
	/**
	 * Recupera il file detached a cui si riferiscono le marche temporali
	 * @return
	 */
	public File getDetachedFile() {
		return detachedFiles==null ? null: detachedFiles.get(0);
	}
	
}