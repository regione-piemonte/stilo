package it.eng.utility.cryptosigner.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.cert.CRL;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1StreamParser;
import org.bouncycastle.asn1.cms.Attribute;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.ess.ESSCertID;
import org.bouncycastle.asn1.ess.ESSCertIDv2;
import org.bouncycastle.asn1.ess.SigningCertificate;
import org.bouncycastle.asn1.ess.SigningCertificateV2;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.CMSSignedDataParser;
import org.bouncycastle.cms.SignerId;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.tsp.TimeStampToken;
import org.bouncycastle.util.Store;
import org.bouncycastle.x509.X509CRLStoreSelector;

import it.eng.utility.FileUtil;
import it.eng.utility.cryptosigner.data.signature.CMSSignature;
import it.eng.utility.cryptosigner.data.signature.ISignature;
import it.eng.utility.cryptosigner.data.type.SignerType;
import it.eng.utility.cryptosigner.utils.InstanceCMSSignedDataParser;

/**
 * Implementa i controlli su firme di tipo P7M. Il contenuto di un file � riconosciuto se implementa le specifiche PKCS #7
 * 
 * @author Stefano Zennaro
 *
 */
public class P7SSigner extends AbstractSigner {

	/**
	 * Contenuto CMS
	 */
	// protected CMSSignedDataParser cmsSignedData = null;
	// protected File file;

	protected boolean isSignedType(CMSSignedDataParser signedData) throws CMSException {
		// Resetto il signer
		reset();
		// cmsSignedData = null;

		boolean signed = false;
		// try {
		// signedData.getSignedContent().drain();
		// }catch(Exception e) {
		// throw new CMSException("Errore firma",e);
		// }
		SignerInformationStore signersStore = signedData.getSignerInfos();
		signersStore = signedData.getSignerInfos();

		Collection<?> signers = signersStore.getSigners();
		if (signers == null || signers.isEmpty()) {
			signed = false;
		} else {
			// Controllo se l'algoritmo � di tipo SHA1
			for (Object signer : signers) {
				if (signer instanceof SignerInformation) {
					if (!CMSSignedDataGenerator.DIGEST_SHA1.equals(((SignerInformation) signer).getDigestAlgOID())) {
						signed = false;
						break;
					}
					signed = true;
				} else {
					signer = false;
				}
			}
		}

		return signed;
	}

	/**
	 * Restituisce true se il contenuto del file è di tipo CMS e l'algoritmo di digest è di tipo SHA1
	 */
	public boolean isSignedType(File file) {
		boolean signed = false;
		InputStream stream = null;
		FileInputStream fisTmp = null;
		FileInputStream fis2 = null;
		LineIterator iterator = null;
		//this.file = file;
		File tmp = null;
		try {
			stream = FileUtils.openInputStream(file);
			CMSSignedDataParser cmsSignedData = InstanceCMSSignedDataParser.getCMSSignedDataParser(stream, false);
			signed = isSignedType(cmsSignedData);

		} catch (Exception e) {
			log.error("Errore in isSignedType di P7SSigner - " + e.getMessage() + " - Il file non ha firma p7s"/*, e*/);
			try {
				// Controllo se il file comincia per -----BEGIN
				iterator = FileUtils.lineIterator(file);
				String firstline = iterator.nextLine();
				if (StringUtils.containsIgnoreCase(firstline, "-----BEGIN")) {
					tmp = File.createTempFile("tmp", ".tmp");
					BufferedWriter writer = new BufferedWriter(new FileWriter(tmp));
					while (iterator.hasNext()) {
						String line = iterator.nextLine();
						if (!StringUtils.containsIgnoreCase(line, "-----END")) {
							writer.write(line);
							writer.newLine();
							writer.flush();
						} else {
							writer.close();
						}
					}
					// tento una lettura con max bytes se va in errore lo ritengo no leggibile
					new ASN1StreamParser(new Base64InputStream(FileUtils.openInputStream(tmp)), maxByteRead).readObject();
					// Base64InputStream streambase64 = new Base64InputStream(FileUtils.openInputStream(tmp));

					CMSSignedDataParser parser = InstanceCMSSignedDataParser.getCMSSignedDataParser(FileUtils.openInputStream(tmp), true);
					signed = isSignedType(parser);
					setBase64(true);

				} else {
					// tento una lettura con max bytes se va in errore lo ritengo no leggibile
					fisTmp = FileUtils.openInputStream(file);
					new ASN1StreamParser(new Base64InputStream(fisTmp), maxByteRead).readObject();
					// Base64InputStream streambase64 = new Base64InputStream(FileUtils.openInputStream(file));

					fis2 = FileUtils.openInputStream(file);
					// CMSSignedDataParser parser = InstanceCMSSignedDataParser.getCMSSignedDataParser(FileUtils.openInputStream(file), true);
					CMSSignedDataParser parser = InstanceCMSSignedDataParser.getCMSSignedDataParser(fis2, true);
					signed = isSignedType(parser);
					setBase64(true);
				}
			} catch (Exception er) {
				//log.error("Eccezione isSignedType", er);
				signed = false;
				try {
					if (stream != null) {
						stream.close();
					}
				} catch (IOException e1) {
					log.error("Eccezione isSignedType", e);
				}
			} finally {
				if (tmp != null) {
					FileUtil.deleteFile(tmp);
				}

			}
		} finally {
			if (stream != null) {
				IOUtils.closeQuietly(stream);
			}
			if (fisTmp != null)
				IOUtils.closeQuietly(fisTmp);
			if (fis2 != null)
				IOUtils.closeQuietly(fis2);
			if (iterator != null)
				iterator.close();
		}
		return signed;
	}

	public static void main(String[] args) {
		P7SSigner signer = new P7SSigner();
		File file = new File("C:/Users/Anna Tesauro/Desktop/testPDF - Copia.pdf");
		boolean isOk = signer.isSignedType(file);
		System.out.println(isOk);

		boolean esitoCancellazione = file.delete();
		System.out.println(esitoCancellazione);
	}

	// @Deprecated
	// public boolean isSignedType(byte[] content) {
	// boolean signed = false;
	// try {
	// CMSSignedDataParser cmsSignedData= new CMSSignedDataParser(content);
	// signed = isSignedType(cmsSignedData);
	// } catch (CMSException e) {
	// signed = false;
	// }
	// return signed;
	// }

	public TimeStampToken[] getTimeStampTokens(File file) {
		// Ritorna sempre null in quanto il file p7m non ha un TimeStampToken al suo interno.
		return null;
	}

	public SignerType getFormat() {
		return SignerType.P7M;
	}

	/**
	 * Ritorna il contenuto non firmato da una struttura di tipo CMSSigned
	 */
	public static InputStream getCMSSignedDataUnsignedContent(CMSSignedDataParser sd) {
		return sd.getSignedContent().getContentStream();
	}

	protected InputStream getExtractedContent(File file) throws IOException, CMSException, OperatorCreationException {
		CMSSignedDataParser cmsSignedData = null;
		if (isBase64()) {
			File tmp = null;
			// Controllo se il file comincia per -----BEGIN
			LineIterator iterator = FileUtils.lineIterator(file);
			String firstline = iterator.nextLine();
			if (StringUtils.containsIgnoreCase(firstline, "-----BEGIN")) {
				tmp = File.createTempFile("tmp", ".tmp");
				BufferedWriter writer = new BufferedWriter(new FileWriter(tmp));
				while (iterator.hasNext()) {
					String line = iterator.nextLine();
					if (!StringUtils.containsIgnoreCase(line, "-----END")) {
						writer.write(line);
						writer.newLine();
						writer.flush();
					} else {
						writer.close();
					}
				}

				// Base64InputStream streambase64 = new Base64InputStream(FileUtils.openInputStream(tmp));

				cmsSignedData = InstanceCMSSignedDataParser.getCMSSignedDataParser(FileUtils.openInputStream(tmp), true);

				FileUtil.deleteFile(tmp);
			} else {
				// Base64InputStream streambase64 = new Base64InputStream(FileUtils.openInputStream(file));
				cmsSignedData = InstanceCMSSignedDataParser.getCMSSignedDataParser(FileUtils.openInputStream(file), true);

			}
		} else {
			cmsSignedData = InstanceCMSSignedDataParser.getCMSSignedDataParser(FileUtils.openInputStream(file), false);

		}
		return cmsSignedData.getSignedContent().getContentStream();
	}

	public InputStream getUnsignedContent(File file) {
		try {
			File detachedFile = getDetachedFile();

			// Si tratta della firma di un file detached?
			if (detachedFile != null)
				return FileUtils.openInputStream(detachedFile);

			else {
				return getExtractedContent(file);
			}
		} catch (Exception e) {
			log.error("Eccezione getUnsignedContent", e);
		}
		return null;
	}

	public byte[] getUnsignedContentDigest(MessageDigest digestAlgorithm, File file) {
		InputStream unsignedContent = this.getUnsignedContent(file);
		byte[] buff = new byte[Byte.SIZE * 512];
		int length = -1;
		digestAlgorithm.reset();
		try {
			while ((length = unsignedContent.read(buff)) != -1) {
				digestAlgorithm.update(buff, 0, length);
			}
			return digestAlgorithm.digest();
		} catch (IOException e) {
			log.error("Eccezione getUnsignedContentDigest", e);
		} finally {
			if (unsignedContent != null)
				try {
					unsignedContent.close();
				} catch (IOException e) {
					log.error("Eccezione getUnsignedContentDigest", e);
				}
		}
		return null;
	}

	/**
	 * Recupera la lista di firme da una struttura di tipo CMS settando al contempo il contenuto a cui le firme si riferiscono.
	 * 
	 * @param signedData
	 *            contenuto di tipo CMS
	 * @param detachedContent
	 *            contenuto detached
	 * @return la lista di firme
	 * @throws CMSException
	 */
	public static final List<ISignature> getISigneturesFromCMSSignedData(CMSSignedDataParser signedData, List<File> detachedContent) throws CMSException {

		List<ISignature> result = new ArrayList<ISignature>();

		Collection<?> certificates = null;
		try {
			Store storecertificate = signedData.getCertificates();
			certificates = (Collection<?>) storecertificate.getMatches(null);
		} catch (Exception e) {
			log.error("Eccezione getISigneturesFromCMSSignedData", e);
		}

		Collection<?> signers = (Collection<?>) signedData.getSignerInfos().getSigners();
		for (Object signer : signers) {
			if (signer instanceof SignerInformation) {
				// SignerId signerID = ((SignerInformation)signer).getSID();
				ISignature signature = getISignatureFromSignerInformationAndCertificates((SignerInformation) signer, certificates, detachedContent);
				if (signature != null) {
					result.add(signature);
				}
			} else {
				throw new CMSException("Tipo " + signer.getClass() + " non supportato!");
			}
		}
		return result;
	}

	protected static final List<ISignature> getISigneturesFromCMSSignedDataInternal(CMSSignedData signedData, List<File> detachedContent) throws CMSException {

		List<ISignature> result = new ArrayList<ISignature>();

		Collection<?> certificates = null;
		try {
			Store storecertificate = signedData.getCertificates();
			certificates = storecertificate.getMatches(null);
		} catch (Exception e) {
			log.error("Eccezione getISigneturesFromCMSSignedDataInternal", e);
		}

		Collection<?> signers = (Collection<?>) signedData.getSignerInfos().getSigners();
		for (Object signer : signers) {
			if (signer instanceof SignerInformation) {
				// SignerId signerID = ((SignerInformation)signer).getSID();
				ISignature signature = getISignatureFromSignerInformationAndCertificates((SignerInformation) signer, certificates, detachedContent);
				if (signature != null) {
					result.add(signature);
				}
			} else {
				throw new CMSException("Tipo " + signer.getClass() + " non supportato!");
			}
		}
		return result;
	}

	public static ISignature getISignatureFromSignerInformationAndCertificates(SignerInformation signer, Collection<?> certificates,
			List<File> detachedContent) {

		SignerId signerID = signer.getSID();

		AttributeTable signedTable = signer.getSignedAttributes();
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
					log.error("Eccezione getISignatureFromSignerInformationAndCertificates", e);
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
					log.error("Eccezione getISignatureFromSignerInformationAndCertificates", e);
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
			log.error("Eccezione getISignatureFromSignerInformationAndCertificates", e);
		}

		for (Object obj : certificates) {

			Certificate certificate = null;

			if (obj instanceof X509CertificateHolder) {
				try {
					certificate = new JcaX509CertificateConverter().setProvider(BouncyCastleProvider.PROVIDER_NAME).getCertificate((X509CertificateHolder) obj);
				} catch (CertificateException e) {
					log.error("Eccezione getISigneturesFromCMSSignedDataInternal", e);
				}
			} else {
				certificate = (Certificate) obj;
			}

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
					log.error("Eccezione getISignatureFromSignerInformationAndCertificates", e);
				}
			} else if (certificate instanceof X509Certificate && ((X509Certificate) certificate).getIssuerX500Principal().equals(signerID.getIssuer())
					&& signerID.getSerialNumber().equals(((X509Certificate) certificate).getSerialNumber())) {
				correctCertificate = true;
			}

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
									.add(getISignatureFromSignerInformationAndCertificates((SignerInformation) counterSignatureInfo, certificates, null));
						}
					}
					cmsSignature.setCounterSignatures(counterSignatures);
				}

				return cmsSignature;
			}
		}
		return null;
	}

	public List<ISignature> getSignatures(File file) {
		try {
			CMSSignedDataParser cmsSignedData = null;
			if (isBase64()) {

				File tmp = null;
				// Controllo se il file comincia per -----BEGIN
				LineIterator iterator = FileUtils.lineIterator(file);
				String firstline = iterator.nextLine();
				if (StringUtils.containsIgnoreCase(firstline, "-----BEGIN")) {
					tmp = File.createTempFile("tmp", ".tmp");
					BufferedWriter writer = new BufferedWriter(new FileWriter(tmp));
					while (iterator.hasNext()) {
						String line = iterator.nextLine();
						if (!StringUtils.containsIgnoreCase(line, "-----END")) {
							writer.write(line);
							writer.newLine();
							writer.flush();
						} else {
							writer.close();
						}
					}
					// Base64InputStream streambase64 = new Base64InputStream(FileUtils.openInputStream(tmp));
					cmsSignedData = InstanceCMSSignedDataParser.getCMSSignedDataParser(FileUtils.openInputStream(tmp), true);
					FileUtil.deleteFile(tmp);
				} else {
					// Base64InputStream streambase64 = new Base64InputStream(FileUtils.openInputStream(file));
					cmsSignedData = InstanceCMSSignedDataParser.getCMSSignedDataParser(FileUtils.openInputStream(file), true);
					// new CMSSignedDataParser(streambase64);
				}
			} else {
				cmsSignedData = InstanceCMSSignedDataParser.getCMSSignedDataParser(FileUtils.openInputStream(file), false);
				// new CMSSignedDataParser(FileUtils.openInputStream(file));
			}
			cmsSignedData.getSignedContent().drain();
			return getISigneturesFromCMSSignedData(cmsSignedData, detachedFiles);
		} catch (Exception e) {
			log.error("Eccezione getSignatures", e);
		}
		return null;
	}

	public boolean canContentBeSigned() {
		return true;
	}

	public static Collection<CRL> getCRLsFromCMSSignedData(CMSSignedDataParser cmsSignedData) {
		Store store;
		Collection<CRL> crls = null;
		try {
			store = cmsSignedData.getCRLs();
			crls = store.getMatches(new X509CRLStoreSelector());
			return crls;
		} catch (Exception e) {
			return null;
		}

	}

	public static Collection<? extends Certificate> getCertificatesFromCMSSignedData(CMSSignedDataParser cmsSignedData) {
		try {
			Store store = cmsSignedData.getCertificates();
			List<Certificate> certificateslist = new ArrayList<Certificate>();
			Collection certificates = store.getMatches(null);
			for (Object obj : certificates) {
				Certificate certificate = null;
				if (obj instanceof X509CertificateHolder) {
					try {
						certificate = new JcaX509CertificateConverter().setProvider(BouncyCastleProvider.PROVIDER_NAME)
								.getCertificate((X509CertificateHolder) obj);
					} catch (CertificateException e) {
						log.error("Eccezione getCertificatesFromCMSSignedData", e);
					}
				} else {
					certificate = (Certificate) obj;
				}
				certificateslist.add(certificate);
			}

			if (certificateslist.isEmpty()) {
				certificateslist = null;
			}
			return certificateslist;
		} catch (Exception e) {
			return null;
		}
	}

	public Collection<CRL> getEmbeddedCRLs(File file) {
		try {
			CMSSignedDataParser cmsSignedData = null;
			if (isBase64()) {
				File tmp = null;
				// Controllo se il file comincia per -----BEGIN
				LineIterator iterator = FileUtils.lineIterator(file);
				String firstline = iterator.nextLine();
				if (StringUtils.containsIgnoreCase(firstline, "-----BEGIN")) {
					tmp = File.createTempFile("tmp", ".tmp");
					BufferedWriter writer = new BufferedWriter(new FileWriter(tmp));
					while (iterator.hasNext()) {
						String line = iterator.nextLine();
						if (!StringUtils.containsIgnoreCase(line, "-----END")) {
							writer.write(line);
							writer.newLine();
							writer.flush();
						} else {
							writer.close();
						}
					}
					// Base64InputStream streambase64 = new Base64InputStream(FileUtils.openInputStream(tmp));

					cmsSignedData = InstanceCMSSignedDataParser.getCMSSignedDataParser(FileUtils.openInputStream(tmp), true);
					// new CMSSignedDataParser(streambase64);
					FileUtil.deleteFile(tmp);

				} else {
					// Base64InputStream streambase64 = new Base64InputStream(FileUtils.openInputStream(file));
					cmsSignedData = InstanceCMSSignedDataParser.getCMSSignedDataParser(FileUtils.openInputStream(file), true);
					// new CMSSignedDataParser(streambase64);
				}
			} else {
				cmsSignedData = InstanceCMSSignedDataParser.getCMSSignedDataParser(FileUtils.openInputStream(file), false);
				// new CMSSignedDataParser(FileUtils.openInputStream(file));

			}
			cmsSignedData.getSignedContent().drain();
			return getCRLsFromCMSSignedData(cmsSignedData);
		} catch (Exception e) {
			log.error("Eccezione getEmbeddedCRLs", e);
		}
		return null;
	}

	public Collection<? extends Certificate> getEmbeddedCertificates(File file) {
		FileInputStream fis;
		try {
			CMSSignedDataParser cmsSignedData = null;
			if (isBase64()) {
				File tmp = null;
				// Controllo se il file comincia per -----BEGIN
				LineIterator iterator = FileUtils.lineIterator(file);
				String firstline = iterator.nextLine();
				if (StringUtils.containsIgnoreCase(firstline, "-----BEGIN")) {
					tmp = File.createTempFile("tmp", ".tmp");
					BufferedWriter writer = new BufferedWriter(new FileWriter(tmp));
					while (iterator.hasNext()) {
						String line = iterator.nextLine();
						if (!StringUtils.containsIgnoreCase(line, "-----END")) {
							writer.write(line);
							writer.newLine();
							writer.flush();
						} else {
							writer.close();
						}
					}
					// Base64InputStream streambase64 = new Base64InputStream(FileUtils.openInputStream(tmp));
					cmsSignedData = InstanceCMSSignedDataParser.getCMSSignedDataParser(FileUtils.openInputStream(tmp), true);
					// new CMSSignedDataParser(streambase64);
					FileUtil.deleteFile(tmp);

				} else {
					// Base64InputStream streambase64 = new Base64InputStream(FileUtils.openInputStream(file));
					cmsSignedData = InstanceCMSSignedDataParser.getCMSSignedDataParser(FileUtils.openInputStream(file), true);
					// new CMSSignedDataParser(streambase64);

				}
			} else {
				cmsSignedData = InstanceCMSSignedDataParser.getCMSSignedDataParser(FileUtils.openInputStream(file), false);
				// new CMSSignedDataParser(FileUtils.openInputStream(file));
			}
			cmsSignedData.getSignedContent().drain();
			return getCertificatesFromCMSSignedData(cmsSignedData);
		} catch (Exception e) {
			log.error("Eccezione getEmbeddedCertificates", e);
		}
		return null;
	}

	@Override
	public Map<byte[], TimeStampToken> getMapSignatureTimeStampTokens(File file) {
		// TODO Auto-generated method stub
		return null;
	}

}