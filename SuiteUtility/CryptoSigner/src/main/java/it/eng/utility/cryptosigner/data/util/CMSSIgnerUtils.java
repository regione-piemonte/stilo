package it.eng.utility.cryptosigner.data.util;

import java.io.File;
import java.security.MessageDigest;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.cms.Attribute;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.cms.CMSAttributes;
import org.bouncycastle.asn1.cms.Time;
import org.bouncycastle.asn1.ess.ESSCertID;
import org.bouncycastle.asn1.ess.ESSCertIDv2;
import org.bouncycastle.asn1.ess.SigningCertificate;
import org.bouncycastle.asn1.ess.SigningCertificateV2;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.SignerId;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.tsp.TimeStampToken;

import it.eng.utility.cryptosigner.data.AbstractSigner;
import it.eng.utility.cryptosigner.data.signature.CMSSignature;
import it.eng.utility.cryptosigner.data.signature.ISignature;

public class CMSSIgnerUtils {

	static Logger log = LogManager.getLogger(AbstractSigner.class);
	
	public ISignature getISignatureFromSignerInformationAndCertificates(SignerInformation signer, Collection<?> certificates,
			List<File> detachedContent, List<Certificate> certificateslist) {
		// log.info("Metodo getISignatureFromSignerInformationAndCertificates in p7m " );

		SignerId signerID = signer.getSID();

		AttributeTable signedTable = signer.getSignedAttributes();
		// log.info("signedTable " + signedTable);
		Attribute signingCertificateV2Attr = null;
		Attribute signingCertificateAttr = null;
		if (signedTable != null) {
			signingCertificateV2Attr = (Attribute) signedTable.toHashtable().get(PKCSObjectIdentifiers.id_aa_signingCertificateV2);
			signingCertificateAttr = (Attribute) signedTable.toHashtable().get(PKCSObjectIdentifiers.id_aa_signingCertificate);
		}

		// Hash da utilizzare per identificare il certificato di firma
		byte[] certHash = null;
		String certHashAlgorithmOid = null;

		try {
			if (signingCertificateV2Attr != null) {

				// Cerca di recuperare l'hash del certificato da una struttura 'ben fatta'
				try {
					SigningCertificateV2 signingCertificateV2 = SigningCertificateV2.getInstance(signingCertificateV2Attr.getAttrValues().getObjectAt(0));
					if (signingCertificateV2 != null) {
						ESSCertIDv2[] essCertsV2 = signingCertificateV2.getCerts();
						ESSCertIDv2 essCertV2 = essCertsV2[0];
						certHash = essCertV2.getCertHash();
						certHashAlgorithmOid = essCertV2.getHashAlgorithm().getAlgorithm().getId();
					}
				} catch (Exception e) {

					// Se c'è stato un errore, cerca di recuperare l'hash dal contenuto stesso
					ASN1Sequence signingCertificateV2Encoded = (ASN1Sequence) signingCertificateV2Attr.getAttrValues().getObjectAt(0);
					ASN1Sequence signingCertificateV2Certs = ASN1Sequence.getInstance(signingCertificateV2Encoded.getObjectAt(0));
					certHash = ASN1OctetString.getInstance(signingCertificateV2Certs.getObjectAt(0).toASN1Primitive()).getOctets();
					// Di default l'algoritmo di hash viene posto a SHA-256
					certHashAlgorithmOid = CMSSignedDataGenerator.DIGEST_SHA256;
				}
			} else if (signingCertificateAttr != null) {
				// Cerca di recuperare l'hash del certificato da una struttura 'ben fatta'
				try {
					SigningCertificate signingCertificate = SigningCertificate.getInstance(signingCertificateAttr.getAttrValues().getObjectAt(0));
					if (signingCertificateAttr != null) {
						ESSCertID[] essCertsV2 = signingCertificate.getCerts();
						ESSCertID essCert = essCertsV2[0];
						certHash = essCert.getCertHash();
						certHashAlgorithmOid = CMSSignedDataGenerator.DIGEST_SHA1;
					}
				} catch (Exception e) {

					// Se c'è stato un errore, cerca di recuperare l'hash dal contenuto stesso
					ASN1Sequence signingCertificateEncoded = (ASN1Sequence) signingCertificateAttr.getAttrValues().getObjectAt(0);
					ASN1Sequence signingCertificateCerts = ASN1Sequence.getInstance(signingCertificateEncoded.getObjectAt(0));
					certHash = ASN1OctetString.getInstance(signingCertificateCerts.getObjectAt(0).toASN1Primitive()).getOctets();
					certHashAlgorithmOid = CMSSignedDataGenerator.DIGEST_SHA1;
				}
			}
		} catch (Exception e) {
			// C'è stato un errore durante la decodifica degli attributi contenenti
			// l'hash del certificato di firma
			log.warn("Eccezione getISignatureFromSignerInformationAndCertificates", e);
		}

		for (Object obj : certificates) {

			// log.info("obj " + obj);
			Certificate certificate = null;

			if (obj instanceof X509CertificateHolder) {
				try {
					if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
						log.info("-----aggiungo il provider ");
						Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
					}
					certificate = new JcaX509CertificateConverter().setProvider(BouncyCastleProvider.PROVIDER_NAME).getCertificate((X509CertificateHolder) obj);
					// log.info("Certificate " + certificate);
				} catch (CertificateException e) {
					log.warn("Eccezione getISignatureFromSignerInformationAndCertificates", e);
				}
			} else {
				certificate = (Certificate) obj;
			}
			if( certificate!=null )
				certificateslist.add(certificate);

			boolean correctCertificate = false;
			// Identifica il certificato dal suo hash
			if (certHash != null) {
				try {
					MessageDigest digest = MessageDigest.getInstance(certHashAlgorithmOid);
					if (digest == null)
						return null;
					byte[] computedCertificateHash = digest.digest(certificate.getEncoded());
					if (org.bouncycastle.util.Arrays.areEqual(certHash, computedCertificateHash))
						correctCertificate = true;
				} catch (Exception e) {
					log.warn("Eccezione getISignatureFromSignerInformationAndCertificates", e);
				}
			} else if (certificate instanceof X509Certificate && confrontaIssuer((X509Certificate) certificate, signerID.getIssuer()) &&
			// certificate instanceof X509Certificate && ((X509Certificate)certificate).getIssuerX500Principal().equals(signerID.getIssuer()) &&
					signerID.getSerialNumber().equals(((X509Certificate) certificate).getSerialNumber())) {
				correctCertificate = true;
			}

			// log.info("correctCertificate "+ correctCertificate);
			if (correctCertificate) {
				CMSSignature cmsSignature = new CMSSignature(signer, (X509Certificate) certificate);
				cmsSignature.setDetachedFiles(detachedContent);

				// Aggiorno la lista delle controfirme
				SignerInformationStore counterSignaturesStore = signer.getCounterSignatures();
				Collection<?> counterSignaturesInfo = counterSignaturesStore.getSigners();
				if (counterSignaturesInfo != null) {
					List<ISignature> counterSignatures = new ArrayList<ISignature>();
					for (Object counterSignatureInfo : counterSignaturesInfo) {
						if (counterSignatureInfo instanceof SignerInformation) {
							counterSignatures
									.add(getISignatureFromSignerInformationAndCertificates((SignerInformation) counterSignatureInfo, certificates, null, certificateslist));
						}
					}
					cmsSignature.setCounterSignatures(counterSignatures);
				}

				SignerInformation sigInfo = cmsSignature.getSignerInformation();
				ASN1Primitive validSigningTime = null;
				try {
					validSigningTime = getSingleValuedSignedAttribute(sigInfo.getUnsignedAttributes(), sigInfo.getSignedAttributes(), CMSAttributes.signingTime,
							"signing-time");
					if (validSigningTime != null) {
						// log.info("signingTime " + Time.getInstance(validSigningTime).getDate());
						cmsSignature.setSigningTime(Time.getInstance(validSigningTime).getDate());
					}
				} catch (CMSException e1) {
					log.warn("Eccezione getISignatureFromSignerInformationAndCertificates", e1);
				}

				return cmsSignature;
			}
		}
		return null;
	}
	
	public TimeStampToken getTimeStamps(SignerInformation signer) {
		
		log.debug("Metodo getTimeStamps");
		AttributeTable table = signer.getUnsignedAttributes();
		log.debug("Metodo getTimeStamps table " + table);
		if (table == null)
			return null;
		// Attribute attribute = (Attribute)table.toHashtable().get(new DERObjectIdentifier("1.2.840.113549.1.9.16.2.47"));
		Attribute attribute = (Attribute) table.toHashtable().get(new ASN1ObjectIdentifier("1.2.840.113549.1.9.16.2.14"));
		// Attribute attribute = (Attribute)table.toHashtable().get(new ASN1ObjectIdentifier("1.2.840.113549.1.9.16.2.14"));
		log.debug("Metodo getTimeStamps attribute " + attribute);
		
		if (attribute != null && attribute.getAttrValues() != null) {
			TimeStampToken timestamptoken = null;
			try {
				timestamptoken = new TimeStampToken(new CMSSignedData(attribute.getAttrValues().getObjectAt(0).toASN1Primitive().getEncoded()));
				log.debug("Metodo getTimeStamps timestamptoken " + timestamptoken);
			} catch (Exception e) {
				log.warn("Exception", e);
			}
			if (timestamptoken != null) {
				log.debug("Metodo getTimeStamps return timestamptoken " + timestamptoken);
				return timestamptoken;
			}
		}
		
		log.debug("Metodo getTimeStamps return null ");
		return null;
	}
	
	private static ASN1Primitive getSingleValuedSignedAttribute(AttributeTable unsignedAttrTable, AttributeTable signedAttrTable, ASN1ObjectIdentifier attrOID,
			String printableName) throws CMSException {
		// log.info("Metodo getSingleValuedSignedAttribute in p7m " );

		if (unsignedAttrTable != null && unsignedAttrTable.getAll(attrOID).size() > 0) {
			throw new CMSException("The " + printableName + " attribute MUST NOT be an unsigned attribute");
		}

		if (signedAttrTable == null) {
			return null;
		}

		ASN1EncodableVector v = signedAttrTable.getAll(attrOID);
		switch (v.size()) {
		case 0:
			return null;
		case 1: {
			Attribute t = (Attribute) v.get(0);
			ASN1Set attrValues = t.getAttrValues();
			if (attrValues.size() != 1) {
				throw new CMSException("A " + printableName + " attribute MUST have a single attribute value");
			}

			return attrValues.getObjectAt(0).toASN1Primitive();
		}
		default:
			throw new CMSException("The SignedAttributes in a signerInfo MUST NOT include multiple instances of the " + printableName + " attribute");
		}
	}
	
	private static boolean confrontaIssuer(X509Certificate x509Certificato, X500Name p2) {
		// log.info("Metodo confrontaIssuer in p7m " );
		X500Name x500name;
		try {
			x500name = new JcaX509CertificateHolder(x509Certificato).getIssuer();
			return x500name.equals(p2);
		} catch (CertificateEncodingException e) {

		}
		return false;
	}
}
