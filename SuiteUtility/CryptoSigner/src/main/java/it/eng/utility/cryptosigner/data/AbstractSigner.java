package it.eng.utility.cryptosigner.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CRL;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.crypto.Cipher;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.cmp.PKIStatus;
import org.bouncycastle.asn1.cmp.PKIStatusInfo;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.tsp.TimeStampResp;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSProcessable;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.tsp.TSPException;
import org.bouncycastle.tsp.TimeStampRequest;
import org.bouncycastle.tsp.TimeStampResponse;
import org.bouncycastle.tsp.TimeStampToken;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Store;

import it.eng.utility.cryptosigner.controller.bean.ValidationInfos;
import it.eng.utility.cryptosigner.controller.impl.cert.CertMessage;
import it.eng.utility.cryptosigner.data.signature.ISignature;
import it.eng.utility.cryptosigner.data.type.SignerType;
import it.eng.utility.cryptosigner.utils.MessageConstants;
import it.eng.utility.cryptosigner.utils.MessageHelper;

/**
 * Classe di base per l'esecuzione delle operazioni di estrazione, analisi e validazione di firme digitali.
 * 
 * Fornisce gi� i metodi per il controllo della corrispondenza tra marche temporali e contenuto firmato ({@link checkTimeStampTokenOverRequest}) e l�estrazione
 * del contenuto della busta su file ({@link getContentAsFile}). I metodi astratti da implementare sono i seguenti:
 * <ul>
 * <li>isSignedType: effettua l�istanziazione delle strutture di supporto e restituisce true se il file analizzato � riconosciuto</li>
 * <li>getFormat: restituisce il formato della busta nel caso sia stato riconosciuto</li>
 * <li>getTimeStampTokens: recupera le marche temporali contenute</li>
 * <li>getUnsignedContent: restituisce il contenuto sbustato</li>
 * <li>canContentBeSigned: restituisce true se il contenuto sbustato pu� a sua volta essere firmato (nel caso di firme multiple esterne)</li>
 * <li>getSignatures: recupera le firme contenute all�interno della busta. Ciascuna firma appartiene ad una classe che implementa l�interfaccia ISignature:
 * contiene al suo interno la lista delle eventuali controfirme e il metodo di validazione (sia sul contenuto embedded che su file detached - qualora previsto
 * ).</li>
 * <li>validateTimeStampTokensEmbedded / validateTimeStampTokensDetached: implementano le validazioni delle marche temporali interne o detached</li>
 * </ul>
 * Una classe estendente inoltre deve sovrascrivere uno dei metodi: {@link validateTimeStampTokensEmbedded} o {@link validateTimeStampTokensDetached} a seconda
 * se il conenuto firmato sia all'interno della busta o esterno.
 * 
 * @author Stefano Zennaro
 *
 */
public abstract class AbstractSigner {

	// limit file to 100 MB per evitare alcuni errori su imamgini mal interpretate limitiamo
	// l'apertura di file fino a 100 MB in modo che prima faccio uan verifica che i file si può aprire poi
	// lo apro
	protected int maxByteRead = 100 * 1000 * 1000;
	/*
	 * CAMPI
	 */

	static Logger log = LogManager.getLogger(AbstractSigner.class);

	/**
	 * File di firma con/senza contenuto
	 */
	//protected File file;
	//protected FileInputStream fileStream;

	/**
	 * Contenuto detached
	 */
	protected List<File> detachedFiles;

	/**
	 * File precedentemente sbustato
	 */
	// protected File alreadyExtractedFile;

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
	 * 
	 * @return
	 */
	public abstract SignerType getFormat();

	/**
	 * Controlla se il file contiene firme in formato riconosciuto
	 * 
	 * @param file
	 * @return true se il vi sono firme con formato riconosciuto
	 */
	public abstract boolean isSignedType(File file);

	/**
	 * Ritorna il timestamptoken se presente. Se il token non esiste ritorna null.
	 * 
	 * @return
	 */
	public abstract TimeStampToken[] getTimeStampTokens(File file);

	public abstract Map<byte[], TimeStampToken> getMapSignatureTimeStampTokens(File file);

	public void reset() {
		timestamptokens = null;
	}

	/**
	 * Ritorna il contenuto non firmato
	 * 
	 * @return
	 */
	public abstract InputStream getUnsignedContent(File file);

	/**
	 * Restituisce true se il contenuto sbustato può a sua volta essere firmato (nel caso di firme multiple esterne)
	 * 
	 * @return
	 */
	public abstract boolean canContentBeSigned();

	/**
	 * Recupera l'hash del contenuto utilizzando l'algorimo di digest passato in ingresso - ogni firma presente può utilizzare un proprio algoritmo di digest
	 */
	public abstract byte[] getUnsignedContentDigest(MessageDigest digestAlgorithm, File file);

	/**
	 * Recupera le firme apposte
	 * 
	 * @throws CMSException
	 */
	public abstract List<ISignature> getSignatures(File file);

	/**
	 * Recupera le CRL incluse all'interno del file firmato (solo per i formati di firma che lo prevedono)
	 */
	public abstract Collection<CRL> getEmbeddedCRLs(File file);

	/**
	 * Recupera i certificati inclusi all'interno del file firmato (solo per i formati di firma che lo prevedono)
	 */
	public abstract Collection<? extends Certificate> getEmbeddedCertificates(File file);
	/*
	 * METODI PUBBLICI
	 */

	/**
	 * Recupera il file di firma
	 */
	/*public File getFile() {
		return file;
	}

	public FileInputStream getFileStream() {
		if (fileStream == null) {
			try {
				this.fileStream = FileUtils.openInputStream(file);
			} catch (IOException e) {
				log.error("IOException getFileStream", e);
			}
		}
		return fileStream;
	}*/

	/**
	 * Definisce il file di firma
	 * 
	 * @param file
	 */
//	public void setFile(File file) {
//		this.file = file;
//		// this.alreadyExtractedFile = null;
//	}

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
	 * Metodo di utilità che esegue la validazione di un timestamptoken rispetto al messaggio di request contenente l'hashmap del contenuto da marcare.
	 * 
	 * @param validationInfos
	 *            struttura che raccoglie le informazioni riguardo all'esito della validazione del timestamp
	 * @param timestamptoken
	 *            token contenente la marca temporale
	 * @param request
	 *            classe contenente l'implementazione della richiesta di emissione di una marca temporale, secondo le specifiche descritte in RFC3161
	 */
	protected void checkTimeStampTokenOverRequest(ValidationInfos validationInfos, TimeStampToken timestamptoken, TimeStampRequest request) {

		try {

			PKIStatusInfo paramPKIStatusInfo = new PKIStatusInfo(PKIStatus.granted); // Assomiglia tanto a come era prima quando gli apssavo 0

			ASN1InputStream aIn = new ASN1InputStream(timestamptoken.getEncoded());
			ASN1Sequence seq = (ASN1Sequence) aIn.readObject();
			ContentInfo paramContentInfo = new ContentInfo(seq);
			TimeStampResp tsr = new TimeStampResp(paramPKIStatusInfo, paramContentInfo);
			TimeStampResponse response = new TimeStampResponse(tsr);

			checkTimeStampRequestOverTimeStampResponse(validationInfos, timestamptoken, request, response);

		} catch (IOException e) {
			validationInfos.addError(MessageHelper.getMessage(MessageConstants.SIGN_TOKEN_WITHOUT_VALIDMARK));
			validationInfos.addErrorWithCode(MessageConstants.SIGN_TOKEN_WITHOUT_VALIDMARK,
					MessageHelper.getMessage(MessageConstants.SIGN_TOKEN_WITHOUT_VALIDMARK));
		} catch (TSPException e) {
			validationInfos.addError(MessageHelper.getMessage(MessageConstants.GM_GENERIC_ERROR, e.getMessage()));
			validationInfos.addErrorWithCode(MessageConstants.GM_GENERIC_ERROR, MessageHelper.getMessage(MessageConstants.GM_GENERIC_ERROR, e.getMessage()));
		}
	}

	private void checkTimeStampRequestOverTimeStampResponse(ValidationInfos validationInfos, TimeStampToken timestamptoken, TimeStampRequest request,
			TimeStampResponse response) {

		String digestAlgorithmOID = null;
		/*
		 * Verifica che la marca temporale sia effettivamente associata alla request
		 */
		try {
			response.validate(request);
		} catch (TSPException e) {
			validationInfos.addError(MessageHelper.getMessage(MessageConstants.GM_GENERIC_ERROR, e.getMessage()));
			validationInfos.addErrorWithCode(MessageConstants.GM_GENERIC_ERROR, MessageHelper.getMessage(MessageConstants.GM_GENERIC_ERROR, e.getMessage()));
		}

		/*
		 * Occorre quindi verificare che il timestamp sia stato effettivamente calcolato a partire dall'impronta del file in ingresso, cioè:
		 * SignerInfo.digestAlgorithm(ContentInfo.Content / ContentInfo.signedAttributes) = SignerInfo.signaturealgorithm^-1(SignerInfo.cid.publickey,
		 * SignerInfo.Signature)
		 */
		try {

			CMSSignedData cms = timestamptoken.toCMSSignedData();
			// MODIFICA ANNA 1.53
			// CertStore certStore = timestamptoken.getCertificatesAndCRLs("Collection", "BC");
			// Collection<?> saCertificates = (Collection<?>)certStore.getCertificates(null);
			Store certStore = cms.getCertificates();
			Collection<?> saCertificates = (Collection<?>) certStore.getMatches(null);
			if (saCertificates == null)
				throw new Exception("Il certificato di TSA non risulta presente");

			Object certificate = saCertificates.iterator().next();
			if (certificate == null)
				throw new Exception("Il certificato di TSA non risulta presente");

			PublicKey publicKey = null;
			if (certificate instanceof X509CertificateHolder) {
				Certificate certificateM = new JcaX509CertificateConverter().setProvider(BouncyCastleProvider.PROVIDER_NAME)
						.getCertificate((X509CertificateHolder) certificate);
				publicKey = certificateM.getPublicKey();
			} else {
				publicKey = ((Certificate) certificate).getPublicKey();
			}

			if (publicKey == null)
				throw new Exception("La publicKey della TSA non risulta presente");

			Collection<SignerInformation> signers = (Collection<SignerInformation>) cms.getSignerInfos().getSigners();
			SignerInformation signerInfo = signers.iterator().next();
			digestAlgorithmOID = signerInfo.getDigestAlgOID();
			MessageDigest contentDigestAlgorithm = MessageDigest.getInstance(digestAlgorithmOID);

			/*
			 * I due byte array da verificare
			 */
			byte[] encodedDataToVerify = null;
			byte[] encodedSignedData = null;

			/*
			 * Verifica che il certificato sia corretto ripetto al firmatario - la public key � correttamente associata al contenuto firmato
			 */
			boolean certificateVerified = false;

			if (signerInfo.verify(new JcaSimpleSignerInfoVerifierBuilder().setProvider("BC").build(publicKey))) {
				certificateVerified = true;
			}
			CMSProcessable signedContent = cms.getSignedContent();
			byte[] originalContent = (byte[]) signedContent.getContent();

			log.debug("originalContent.length: " + originalContent.length + " originalContent: " + SignerUtil.asHex(originalContent));

			/*
			 * Controllo se occorre calcolare il digest dell'eContent oppure degli attributi firmati
			 */
			byte[] encodedSignedAttributes = signerInfo.getEncodedSignedAttributes();
			if (encodedSignedAttributes != null) {
				encodedDataToVerify = contentDigestAlgorithm.digest(encodedSignedAttributes);
			} else {
				encodedDataToVerify = contentDigestAlgorithm.digest((byte[]) cms.getSignedContent().getContent());
			}

			log.debug("encodedDataToVerify: " + SignerUtil.asHex(encodedDataToVerify));

			// Hash dell'econtent (da confrontare con l'hash del TSTInfo)
			byte[] contentDigest = signerInfo.getContentDigest();
			/*
			 * FIXME: tstInfo.getEncoded() è stato sostituito con getSignedContent().getContent() poich� nelle m7m la chiamata restituisce un errore - occorre
			 * verificare che i due metodi restituiscano lo stesso oggetto (attualmente la mancata verifica viene segnalata solo come warning)
			 */
			// TSTInfo tstInfo = timestamptoken.getTimeStampInfo().toTSTInfo();
			// byte[] tstInfoEncoded = contentDigestAlgorithm.digest(tstInfo.getEncoded());
			byte[] tstInfoEncoded = contentDigestAlgorithm.digest((byte[]) cms.getSignedContent().getContent());
			boolean contentVerified = Arrays.constantTimeAreEqual(contentDigest, tstInfoEncoded);

			digestAlgorithmOID = signerInfo.getEncryptionAlgOID();
			byte[] signature = signerInfo.getSignature();
			Cipher cipher = null;
			try {
				String algorithmName = null;
				if (PKCSObjectIdentifiers.rsaEncryption.getId().equals(digestAlgorithmOID))
					algorithmName = "RSA/ECB/PKCS1Padding";
				else if (PKCSObjectIdentifiers.sha1WithRSAEncryption.getId().equals(digestAlgorithmOID))
					algorithmName = "RSA/ECB/PKCS1Padding";
				else
					algorithmName = digestAlgorithmOID;
				cipher = Cipher.getInstance(algorithmName);
			} catch (Throwable e) {

			}
			if (cipher != null) {
				try {
					log.debug("Cipher: " + cipher.getAlgorithm());
					cipher.init(Cipher.DECRYPT_MODE, publicKey);
					byte[] decryptedSignature = cipher.doFinal(signature);

					ASN1InputStream asn1is = new ASN1InputStream(decryptedSignature);
					ASN1Sequence asn1Seq = (ASN1Sequence) asn1is.readObject();

					Enumeration<? extends ASN1Primitive> objs = asn1Seq.getObjects();
					while (objs.hasMoreElements()) {
						ASN1Primitive derObject = objs.nextElement();
						if (derObject instanceof ASN1OctetString) {
							ASN1OctetString octectString = (ASN1OctetString) derObject;
							encodedSignedData = octectString.getOctets();
							break;
						}
					}
					log.debug("encodedSignedData: " + SignerUtil.asHex(encodedSignedData));
					boolean signatureVerified = Arrays.constantTimeAreEqual(encodedSignedData, encodedDataToVerify);

					log.debug("Verifica timestampToken: certificateVerified = " + certificateVerified + ", signatureVerified=" + signatureVerified
							+ ", contentVerified=" + contentVerified);
					if (!certificateVerified) {
						validationInfos.addErrorWithCode(CertMessage.CERT_NOTVALID, MessageHelper.getMessage(CertMessage.CERT_NOTVALID));
						validationInfos.addError(MessageHelper.getMessage(CertMessage.CERT_NOTVALID));
					}
					if (!signatureVerified) {
						validationInfos.addErrorWithCode(MessageConstants.SIGN_HASH_ERROR, MessageHelper.getMessage(CertMessage.SIGN_HASH_ERROR,
								SignerUtil.asHex(encodedDataToVerify), SignerUtil.asHex(encodedSignedData)));
						validationInfos.addError(MessageHelper.getMessage(CertMessage.SIGN_HASH_ERROR, SignerUtil.asHex(encodedDataToVerify),
								SignerUtil.asHex(encodedSignedData)));
					}
					if (!contentVerified) {
						validationInfos.addWarning(MessageHelper.getMessage(MessageConstants.SIGN_SIGNEDCONTENT_WARNING, SignerUtil.asHex(tstInfoEncoded),
								SignerUtil.asHex(contentDigest)));
						validationInfos.addWarningWithCode(MessageConstants.SIGN_SIGNEDCONTENT_WARNING, MessageHelper
								.getMessage(MessageConstants.SIGN_SIGNEDCONTENT_WARNING, SignerUtil.asHex(tstInfoEncoded), SignerUtil.asHex(contentDigest)));
					}
				} catch (Exception e) {
					validationInfos.addWarning(MessageConstants.SIGN_MARK_VALIDATION_ERROR);// , MessageHelper.getMessage(
																							// MessageConstants.SIGN_MARK_VALIDATION_ERROR, e.getMessage() ) );
					validationInfos.addWarningWithCode(MessageConstants.SIGN_MARK_VALIDATION_ERROR,
							MessageHelper.getMessage(MessageConstants.SIGN_MARK_VALIDATION_ERROR));
				}
			}
		} catch (IOException e) {
			validationInfos.addErrorWithCode(MessageConstants.SIGN_TOKEN_WITHOUT_VALIDMARK,
					MessageHelper.getMessage(MessageConstants.SIGN_TOKEN_WITHOUT_VALIDMARK));
			validationInfos.addError(MessageHelper.getMessage(MessageConstants.SIGN_TOKEN_WITHOUT_VALIDMARK));
		} catch (NoSuchAlgorithmException e) {
			validationInfos.addErrorWithCode(MessageConstants.SIGN_MARK_ALGORITHM_NOTSUPPORTED,
					MessageHelper.getMessage(MessageConstants.SIGN_MARK_ALGORITHM_NOTSUPPORTED, digestAlgorithmOID));
			validationInfos.addError(MessageHelper.getMessage(MessageConstants.SIGN_MARK_ALGORITHM_NOTSUPPORTED, digestAlgorithmOID));
		} catch (Exception e) {
			validationInfos.addErrorWithCode(MessageConstants.SIGN_MARK_VALIDATION_ERROR,
					MessageHelper.getMessage(MessageConstants.SIGN_MARK_VALIDATION_ERROR, e.getMessage()));
			validationInfos.addError(MessageHelper.getMessage(MessageConstants.SIGN_MARK_VALIDATION_ERROR, e.getMessage()));
		}
	}

//	public void setFileStream(FileInputStream fileStream) {
//		this.fileStream = fileStream;
//	}

	/**
	 * Recupera il contenuto sbustato sotto forma di InputStream.
	 * 
	 * 
	 * @return restituisce il file originario se si tratta di marche detached, altrimenti salva il contenuto sbustato in un file temporaneo e ne restituisce
	 *         l'inputstream
	 * @throws IOException
	 */
	public InputStream getContentAsInputStream(File file) throws IOException {
		// Se si tratta di una firma detached restituisco
		// il file a cui si riferisce
		File detachedFile = getDetachedFile();
		if (detachedFile != null)
			return new FileInputStream(detachedFile);

		// Altrimenti occorre estrarre il contenuto..
		// Verifico se il nome del file contiene più estensioni
		// (ovvero estensione iniziale + firme: test.doc.p7m)
		// per preservare l'estensione originaria
		InputStream contentIS = getUnsignedContent(file);
		return contentIS;

	}

	/*public void closeFileStream() {
		FileInputStream stream = getFileStream();
		if (stream != null) {
			// log.info("Chiudo lo stream " + stream );
			// IOUtils.closeQuietly( stream );
			try {
				stream.close();
			} catch (IOException e) {
				log.error("IOException closeFileStream", e);
			}
		}
	}*/

	public String getEnclosedEnvelopeExtension(File file) {
		if (file == null)
			return null;
		String fileName = file.getName();//getFile().getName();
		String extension = null;
		StringTokenizer tokenizer = new StringTokenizer(fileName, ".");
		if (tokenizer.countTokens() > 2) {
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
	 * 
	 * @return
	 */
	public File getDetachedFile() {
		return detachedFiles == null ? null : detachedFiles.get(0);
	}

}