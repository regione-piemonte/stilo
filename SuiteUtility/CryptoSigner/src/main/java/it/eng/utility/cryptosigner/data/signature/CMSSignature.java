package it.eng.utility.cryptosigner.data.signature;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.crypto.Cipher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.cms.Attribute;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder;
import org.bouncycastle.util.Arrays;

import it.eng.utility.cryptosigner.bean.SignerBean;
import it.eng.utility.cryptosigner.controller.bean.ValidationInfos;
import it.eng.utility.cryptosigner.utils.MessageConstants;
import it.eng.utility.cryptosigner.utils.MessageHelper;

/**
 * Implementa una firma digitale di tipo CMS (utilizzata nei formati P7M, M7M e CAdES)
 * 
 * @author Stefano Zennaro
 */
public class CMSSignature implements ISignature {

	private SignerInformation signerInformation;
	// private X509Certificate certificate;
	private List<ISignature> counterSignatures;
	private SignerBean signerBean;
	private Date signingTime;

	static Logger log = LogManager.getLogger(CMSSignature.class);

	// TEST
	private List<File> detachedFiles;

	public CMSSignature(SignerInformation signerInformation, X509Certificate certificate) {
		this.detachedFiles = null;
		this.signerInformation = signerInformation;
		SignerBean signerBean = new SignerBean();
		signerBean.setCertificate(certificate);
		signerBean.setIusser(certificate.getIssuerX500Principal());
		signerBean.setSubject(certificate.getSubjectX500Principal());
		// signerBean.setSigningTime(signingTime);
		this.signerBean = signerBean;
		// this.certificate = certificate;
	}

	public CMSSignature(SignerInformation signerInformation, X509Certificate certificate, List<File> detachedFiles) {
		this(signerInformation, certificate);
		this.detachedFiles = detachedFiles;
	}

	public byte[] getSignatureBytes() {
		return signerInformation.getSignature();
	}

	public SignerBean getSignerBean() {
		return signerBean;
	}

	public Date getSigningTime() {
		return signingTime;
	}

	public void setSigningTime(Date signingTime) {
		this.signingTime = signingTime;
		this.signerBean.setSigningTime(signingTime);
	}

	public ValidationInfos verify() {
		ValidationInfos validationInfos = new ValidationInfos();
		// Verifica del contentuto incluso nel file di firma
		// log.info(detachedFiles);
		if (detachedFiles == null) {
			try {
				AttributeTable sigTab = signerInformation.getSignedAttributes();
				if (sigTab != null) {
					// log.info( "sigTab.size() " + sigTab.size());
					Hashtable sigHashTab = sigTab.toHashtable();
					Iterator sigHashTabKs = sigHashTab.keySet().iterator();
					while (sigHashTabKs.hasNext()) {
						Object o = sigHashTabKs.next();
						// log.info( o + " " + sigHashTab.get(o) );
					}
				}
				AttributeTable unsigTab = signerInformation.getUnsignedAttributes();
				if (unsigTab != null) {
					// log.info( "unsigTab.size() " + unsigTab.size());
					Hashtable unsigHashTab = unsigTab.toHashtable();
					Iterator unsigHashTabKs = unsigHashTab.keySet().iterator();
					while (unsigHashTabKs.hasNext()) {
						Object o = unsigHashTabKs.next();
						// log.info( o + " " + unsigHashTab.get(o) );
					}
				}

				// boolean test = signerInformation.verify(new
				// JcaSimpleSignerInfoVerifierBuilder().setProvider("BC").build(signerBean.getCertificate().getPublicKey()));
				// boolean test = signerInformation.verify(new
				// JcaSimpleSignerInfoVerifierBuilder().setProvider("BC").build(signerBean.getCertificate().getPublicKey()));
				if( signerBean!=null)
					log.debug("Certificate: " + signerBean.getCertificate() );
				
				boolean test = signerInformation.verify(new JcaSimpleSignerInfoVerifierBuilder().build(signerBean.getCertificate().getPublicKey()));
				log.info("Test verifica cmsSignature " + test);
				if (!test) {
					validationInfos.addErrorWithCode(MessageConstants.SIGN_VALIDATION_ERROR,
							MessageHelper.getMessage(MessageConstants.SIGN_VALIDATION_ERROR, "Firma non valida"));
					validationInfos.addError(MessageHelper.getMessage(MessageConstants.SIGN_VALIDATION_ERROR));
				}
			} catch (CMSException e) {
				log.error("CMSException verify", e);
				validationInfos.addErrorWithCode(MessageConstants.SIGN_CERTIFICATE_NOTVALID,
						MessageHelper.getMessage(MessageConstants.SIGN_CERTIFICATE_NOTVALID));
				validationInfos.addError(MessageHelper.getMessage(MessageConstants.SIGN_CERTIFICATE_NOTVALID));
			} catch (Exception e) {
				log.error("Exception verify", e);
				validationInfos.addError(MessageHelper.getMessage(MessageConstants.GM_GENERIC_ERROR, e.getMessage()));
				validationInfos.addErrorWithCode(MessageConstants.GM_GENERIC_ERROR,
						MessageHelper.getMessage(MessageConstants.GM_GENERIC_ERROR, e.getMessage()));
			}
		}

		// Verifica del contenuto detached
		else {

			File detachedFile = detachedFiles.get(0);
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(detachedFile);
				if (validationInfos != null)
					validationInfos = verifyDetachedContent(signerInformation, fis);
			} catch (FileNotFoundException e) {
				log.error("FileNotFoundException verify", e);
			} finally {
				if (fis != null)
					try {
						fis.close();
					} catch (IOException e) {
						log.error("IOException verify", e);
					}
			}

			// ByteArrayOutputStream bos = new ByteArrayOutputStream();
			// for (File detachedFile: detachedFiles) {
			// try {
			// FileInputStream fis = new FileInputStream(detachedFile);
			// bos.write(IOUtils.toByteArray(fis));
			// fis.close();
			// } catch (IOException e) {
			// validationInfos.addError("Errore durante la codifica del file detached: " + detachedFile.getName() + " : "+ e.getMessage());
			// }
			// }
			// if (validationInfos!=null)
			// validationInfos = verifyDetachedContent(signerInformation, bos.toByteArray());
		}
		return validationInfos;
	}

	public SignerInformation getSignerInformation() {
		return signerInformation;
	}

	// public void setSignerInformation(SignerInformation signerInformation) {
	// this.signerInformation = signerInformation;
	// SignerId signerId = signerInformation.getSID();
	// this.signerBean.setCertificate(signerId.getCertificate());
	//// this.certificate = signerId.getCertificate();
	// }

	public String toString() {
		return "Signature: " + signerInformation == null ? "" : getSignerBean().toString();
	}

	public void setCounterSignatures(List<ISignature> counterSignatures) {
		this.counterSignatures = counterSignatures;
	}

	public List<ISignature> getCounterSignatures() {
		return counterSignatures;
	}

	public List<File> getDetachedFiles() {
		return detachedFiles;
	}

	public void setDetachedFiles(List<File> detachedFiles) {
		this.detachedFiles = detachedFiles;
	}

	// protected ValidationInfos verifyDetachedContent(SignerInformation signerInformation, byte[] detachedContent ) {
	protected ValidationInfos verifyDetachedContent(SignerInformation signerInformation, InputStream detachedContent) {
		ValidationInfos validationInfos = new ValidationInfos();

		String digestAlgorithmOID = signerInformation.getDigestAlgOID();
		MessageDigest contentDigestAlgorithm;
		try {
			contentDigestAlgorithm = MessageDigest.getInstance(digestAlgorithmOID);

			// Impronta del contenuto esterno
			byte[] hashedDetachedData = null;
			// Impronta degli attributi firmati
			byte[] hashedSignedAttributes = null;
			// Contenuto decifrato della firma
			byte[] decodedSignature = null;
			// Attributo firmato digest
			byte[] digestSignedAttribute = null;

			byte[] buff = new byte[Byte.SIZE * 512];
			int length = -1;
			contentDigestAlgorithm.reset();
			while ((length = detachedContent.read(buff)) != -1) {
				contentDigestAlgorithm.update(buff, 0, length);
			}
			hashedDetachedData = contentDigestAlgorithm.digest();

			// hashedDetachedData = contentDigestAlgorithm.digest(detachedContent);
			AttributeTable signedAttributeTable = signerInformation.getSignedAttributes();
			hashedSignedAttributes = contentDigestAlgorithm.digest(signerInformation.getEncodedSignedAttributes());

			digestAlgorithmOID = signerInformation.getEncryptionAlgOID();
			byte[] signature = signerInformation.getSignature();
			Cipher cipher = null;
			String algorithmName = null;
			if (PKCSObjectIdentifiers.rsaEncryption.getId().equals(digestAlgorithmOID))
				algorithmName = "RSA/ECB/PKCS1Padding";
			else if (PKCSObjectIdentifiers.sha1WithRSAEncryption.getId().equals(digestAlgorithmOID))
				algorithmName = "RSA/ECB/PKCS1Padding";
			else
				algorithmName = digestAlgorithmOID;
			cipher = Cipher.getInstance(algorithmName);
			cipher.init(Cipher.DECRYPT_MODE, signerBean.getCertificate().getPublicKey());
			byte[] decryptedSignature = cipher.doFinal(signature);

			ASN1InputStream asn1is = new ASN1InputStream(decryptedSignature);
			ASN1Sequence asn1Seq = (ASN1Sequence) asn1is.readObject();

			Enumeration<? extends ASN1Primitive> objs = asn1Seq.getObjects();
			while (objs.hasMoreElements()) {
				ASN1Primitive derObject = objs.nextElement();
				if (derObject instanceof ASN1OctetString) {
					ASN1OctetString octectString = (ASN1OctetString) derObject;
					decodedSignature = octectString.getOctets();
					break;
				}
			}

			boolean signatureVerified = Arrays.constantTimeAreEqual(decodedSignature, hashedSignedAttributes);
			if (!signatureVerified) {
				validationInfos.addErrorWithCode(MessageConstants.SIGN_HASH_ERROR,
						MessageHelper.getMessage(MessageConstants.SIGN_HASH_ERROR, hashedSignedAttributes, decodedSignature));
				validationInfos.addError(MessageHelper.getMessage(MessageConstants.SIGN_HASH_ERROR, hashedSignedAttributes, decodedSignature));
			} else {
				Attribute digestAttribute = signedAttributeTable.get(PKCSObjectIdentifiers.pkcs_9_at_messageDigest);
				ASN1Set values = digestAttribute.getAttrValues();
				ASN1Primitive derObject = values.getObjectAt(0).toASN1Primitive();
				if (derObject instanceof ASN1OctetString) {
					ASN1OctetString octectString = (ASN1OctetString) derObject;
					digestSignedAttribute = octectString.getOctets();
				}

				boolean contentDigestVerified = Arrays.constantTimeAreEqual(hashedDetachedData, digestSignedAttribute);
				if (!contentDigestVerified) {
					validationInfos.addErrorWithCode(MessageConstants.SIGN_FILE_ASSOCIATION_ERROR,
							MessageHelper.getMessage(MessageConstants.SIGN_FILE_ASSOCIATION_ERROR));
					validationInfos.addError(MessageHelper.getMessage(MessageConstants.SIGN_FILE_ASSOCIATION_ERROR));
				}
			}
		} catch (NoSuchAlgorithmException e) {
			validationInfos.addErrorWithCode(MessageConstants.SIGN_ALGORITHM_NOTSUPPORTED,
					MessageHelper.getMessage(MessageConstants.SIGN_ALGORITHM_NOTSUPPORTED, digestAlgorithmOID));
			validationInfos.addError(MessageHelper.getMessage(MessageConstants.SIGN_ALGORITHM_NOTSUPPORTED, digestAlgorithmOID));
		} catch (IOException e) {
			validationInfos.addErrorWithCode(MessageConstants.SIGN_VALIDATION_ERROR,
					MessageHelper.getMessage(MessageConstants.SIGN_VALIDATION_ERROR, e.getMessage()));
			validationInfos.addError(MessageHelper.getMessage(MessageConstants.SIGN_VALIDATION_ERROR, e.getMessage()));
		} catch (GeneralSecurityException e) {
			validationInfos.addErrorWithCode(MessageConstants.SIGN_DECIPHERING_ERROR,
					MessageHelper.getMessage(MessageConstants.SIGN_DECIPHERING_ERROR, e.getMessage()));
			validationInfos.addError(MessageHelper.getMessage(MessageConstants.SIGN_DECIPHERING_ERROR, e.getMessage()));
		}

		return validationInfos;
	}

}
