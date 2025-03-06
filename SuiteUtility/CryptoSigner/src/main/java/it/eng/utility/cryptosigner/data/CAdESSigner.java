package it.eng.utility.cryptosigner.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1StreamParser;
import org.bouncycastle.asn1.cms.Attribute;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.CMSSignedDataParser;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.tsp.TimeStampRequest;
import org.bouncycastle.tsp.TimeStampRequestGenerator;
import org.bouncycastle.tsp.TimeStampToken;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Store;

import it.eng.utility.FileUtil;
import it.eng.utility.cryptosigner.controller.bean.ValidationInfos;
import it.eng.utility.cryptosigner.data.signature.ISignature;
import it.eng.utility.cryptosigner.data.type.SignerType;
import it.eng.utility.cryptosigner.data.util.CMSSIgnerUtils;
import it.eng.utility.cryptosigner.exception.NoSignerException;
import it.eng.utility.cryptosigner.utils.InstanceCMSSignedDataParser;
import it.eng.utility.cryptosigner.utils.MessageConstants;
import it.eng.utility.cryptosigner.utils.MessageHelper;

/**
 * Implementa i controlli su firme di tipo CAdES. Il contenuto di un file � riconosciuto se implementa le specifiche RFC5126
 * 
 * @author Stefano Zennaro
 *
 */
public class CAdESSigner extends P7MSigner {

	private SignerType type = null;

	private Map<byte[], TimeStampToken> timestamptokensBySignature = null;

	private List<ISignature> signatures = null;
	protected List<Certificate> certificateslist = null;

	// private CMSSignedData signedData= null;

//	public static void main(String[] args) {
//		File file = new File("C:/Users/TESAURO/Downloads/0_ProcuraSpeciale.pdf.p7m");
//
//		AbstractSigner signer = null;
//		try {
//			signer = SignerUtil.newInstance().getSignerManager(file);
//		} catch (NoSignerException e) {
//			// Se arriva qua non e' stato trovato alcun signer per cui o il file non è firmato o
//			// è firmato in maniera ignota al cryptosigner
//			log.warn("Il file " + file + " non e' firmato");
//		}
//		 boolean isOk = signer.isSignedType(file);
//		 System.out.println("isOk " + isOk);
//	// if( isOk ){
//	try {
//		List<ISignature> signatures = signer.getSignatures(file);
//		System.out.println(signatures);
//		
//		InputStream stream = signer.getContentAsInputStream(file);
////		//
////		signer.getTimeStampTokens();
////		// signer.setFileStream(null);
////
////		signer.getMapSignatureTimeStampTokens();
////
////		signer.getUnsignedContent();
//		
////		signer.getEmbeddedCRLs();
////		signer.getEmbeddedCertificates();
////		signer.validateTimeStampTokensEmbedded();
////		signer.getFormat();
////		signer.getTimeStampTokens();
////		signer.canContentBeSigned();
////		//
//		File extracttempfile = File.createTempFile("Extract", "file", new File("C:/Users/TESAURO/Downloads/"));
//		FileOutputStream fos = new FileOutputStream(extracttempfile);
//		IOUtils.copyLarge(stream, fos);
//		fos.close();
//		 if( stream!=null)
//		 stream.close();
////		// signer.closeFileStream();
//	} catch (IOException e) {
//		log.warn("IOException", e);
//	} /*catch (CMSException e) {
//		log.warn("CMSException", e);
//	}*/
//	// }
////
////	signer.closeFileStream();
////	boolean esitoCancellazione = file.delete();
////	log.info("esito cancellazione " + esitoCancellazione);
//}
	
	
	public TimeStampToken[] getTimeStampTokens(File file) {
		 log.info("Metodo getTimeStampTokens in cades timestamptokens:" + timestamptokens);
		InputStream stream = null;
		ArrayList<TimeStampToken> timestampTokensList = new ArrayList<TimeStampToken>();

		FileInputStream fis = null;
		if (timestamptokens == null) {
			try {

				/*
				 * if (isBase64()) { Base64InputStream streambase64 = new Base64InputStream(FileUtils.openInputStream(file));
				 * 
				 * cmsSignedData= new CMSSignedDataParser(streambase64); } else { cmsSignedData= new CMSSignedDataParser(FileUtils.openInputStream(file)); }
				 */

				fis = FileUtils.openInputStream(file);
				// log.info("Apro lo stream " + fis + " sul file " + file);
				CMSSignedDataParser cmsSignedData = InstanceCMSSignedDataParser.getCMSSignedDataParser(fis, isBase64());
				// CMSSignedDataParser cmsSignedData = InstanceCMSSignedDataParser.getCMSSignedDataParser(file,isBase64());

				cmsSignedData.getSignedContent().drain();
				SignerInformationStore signersStore = cmsSignedData.getSignerInfos();
				Collection<? extends SignerInformation> signers = signersStore.getSigners();

				timestamptokensBySignature = new HashMap<byte[], TimeStampToken>();
				if (signers != null) {
					for (SignerInformation signer : signers) {
						AttributeTable table = signer.getUnsignedAttributes();
						if (table == null)
							break;
						// Attribute attribute = (Attribute)table.toHashtable().get(new DERObjectIdentifier("1.2.840.113549.1.9.16.2.47"));
						Attribute attribute = (Attribute) table.toHashtable().get(new ASN1ObjectIdentifier("1.2.840.113549.1.9.16.2.14"));
						// Attribute attribute = (Attribute)table.toHashtable().get(new ASN1ObjectIdentifier("1.2.840.113549.1.9.16.2.14"));

						if (attribute != null && attribute.getAttrValues() != null) {
							TimeStampToken timestamptoken = null;
							try {
								timestamptoken = new TimeStampToken(new CMSSignedData(attribute.getAttrValues().getObjectAt(0).toASN1Primitive().getEncoded()));
							} catch (Exception e) {
								log.warn("Exception", e);
							}
							if (timestamptoken != null) {
								timestampTokensList.add(timestamptoken);
								timestamptokensBySignature.put(signer.getSignature(), timestamptoken);
							}
						}
					}
				}
				if (timestampTokensList.size() != 0)
					timestamptokens = timestampTokensList.toArray(new TimeStampToken[timestampTokensList.size()]);
			} catch (Exception e) {
				timestamptokens = null;
			} finally {
				if (stream != null) {
					IOUtils.closeQuietly(stream);
				}
				if (fis != null) {
					// log.info("Chiudo lo stream " + fis);
					IOUtils.closeQuietly(fis);
				}
			}

		}
		return timestamptokens;
	}

	public Map<byte[], TimeStampToken> getMapSignatureTimeStampTokens() {
		// log.info("Metodo getMapSignatureTimeStampTokens in cades " );
		Map<byte[], TimeStampToken> map = new HashMap<byte[], TimeStampToken>();
		if (timestamptokensBySignature != null) {
			map = timestamptokensBySignature;
		}
		return map;
	}

	/**
	 * Restituisce true se il contenuto del file � di tipo CMS e rispetta le seguenti condizioni:
	 * <ul>
	 * <li>L'algoritmo di digest deve essere SHA256</li>
	 * <li>Il certificato di firma deve essere presente come attributo signing-certificate oppure ESS-signing-certificate-v2</li>
	 * </ul>
	 * Recupera inoltre il timestamp se presente come attributo non firmato (CAdES-T)
	 */
	protected boolean isSignedType(CMSSignedDataParser cmsSignedDataInternal) {
		// log.info("Metodo isSignedType in cades " );
		// Resetto il signer
		reset();
		type = null;
		timestamptokensBySignature = null;
		timestamptokens=null;

		ArrayList<TimeStampToken> timestampTokensList = new ArrayList<TimeStampToken>();
		timestamptokensBySignature = new HashMap<byte[], TimeStampToken>();
		signatures = new ArrayList<ISignature>();
		boolean signed = false;
		try {
			cmsSignedDataInternal.getSignedContent().drain();

			Collection<?> certificates = null;
			try {
				Store storecertificate = cmsSignedDataInternal.getCertificates();
				certificates = (Collection<?>) storecertificate.getMatches(null);
				// log.info("certificates " + certificates );
			} catch (Exception e) {
				log.warn("Eccezione getISigneturesFromCMSSignedData", e);
			}
			
			SignerInformationStore signersStore = cmsSignedDataInternal.getSignerInfos();
			Collection<SignerInformation> signers = signersStore.getSigners();
			if (signers == null || signers.isEmpty()) {
				signed = false;
			} else {
				// Controllo se l'algoritmo e' di tipo SHA256 e che sia presente l'attributo contenente il certificato
				for (SignerInformation signer : signers) {
					// log.info("signer " + signer.getDigestAlgOID() );
					AttributeTable signedTable = signer.getSignedAttributes();
					// log.info("signedTable " + signedTable);
					boolean certv2 = false;
					boolean cert = false;
					if (signedTable != null) {
						certv2 = signedTable.toHashtable().containsKey(PKCSObjectIdentifiers.id_aa_signingCertificateV2);
						cert = signedTable.toHashtable().containsKey(PKCSObjectIdentifiers.id_aa_signingCertificate);
					}
					if (certv2 && cert) {
						signed = false;
						break;
					} else {
						// L'algoritmo di digest deve essere SHA256, inoltre deve essere presente il certificato
						// come attributo signing-certificate oppure ESS-signing-certificate-v2
						if (!(CMSSignedDataGenerator.DIGEST_SHA256.equals(signer.getDigestAlgOID()) && (certv2 || !cert))) {
							signed = false;
							break;
						}
					}
					signed = true;
					
					certificateslist = new ArrayList<Certificate>();
					CMSSIgnerUtils utils = new CMSSIgnerUtils();
					ISignature signature = utils.getISignatureFromSignerInformationAndCertificates(signer, certificates, null, certificateslist);
					signatures.add(signature);
					TimeStampToken timestamptoken = utils.getTimeStamps(signer);
					if( timestamptoken!=null ){
						timestampTokensList.add(timestamptoken);
						timestamptokensBySignature.put(signer.getSignature(), timestamptoken);
					}
					
					// I formati CAdES_T e CAdES_C sono; pi� restrittivi di CAdES_BES
					if (type == null)
						type = SignerType.CAdES_BES;

					// TODO Controllo da verificare
					AttributeTable unsignedTable = signer.getUnsignedAttributes();
					if (unsignedTable != null && unsignedTable.toHashtable().containsKey(PKCSObjectIdentifiers.id_aa_signatureTimeStampToken)) {
						type = SignerType.CAdES_T;
						// Controllo se sono presenti gli atttibuti CRL negli attributi unsigned
						if (unsignedTable.toHashtable().containsKey(PKCSObjectIdentifiers.id_aa_ets_certificateRefs)
								&& unsignedTable.toHashtable().containsKey(PKCSObjectIdentifiers.id_aa_ets_revocationRefs)) {
							type = SignerType.CAdES_C;
						}
					}

				}
				if (timestampTokensList.size() != 0)
					timestamptokens = timestampTokensList.toArray(new TimeStampToken[timestampTokensList.size()]);
			}
		} catch (Exception e) {
			log.error("", e);
			signed = false;

		}
		return signed;
	}

	public boolean isSignedType(File file) {
		// log.info("Metodo isSignedType in cades per il file " + file );
		boolean signed = false;
		// InputStream stream = null;
		LineIterator iterator = null;
		FileInputStream fis = null;
		FileInputStream fis2 = null;
		FileInputStream fisTmp = null;
		//this.file = file;
		File tmp = null;
		try {
			fis = FileUtils.openInputStream(file);
			// log.info("Apro lo stream " + fis + " sul file " + file );
			CMSSignedDataParser parser = InstanceCMSSignedDataParser.getCMSSignedDataParser(fis, false);
			signed = isSignedType(parser);

			// Controolo se e' un base64
			if (!signed) {
				throw new Exception();
			}

		} catch (Exception e) {
			// log.error("", e);
			// controllo se e' in base64
			try {

				// Controllo se il file comincia per -----BEGIN
				iterator = FileUtils.lineIterator(file);
				String firstline = iterator.nextLine();
				// log.info("first line " + firstline );
				if (StringUtils.containsIgnoreCase(firstline, "-----BEGIN")) {
					// Riscrivo il file
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
					// tento una lettura con max bytes se va in errore non lo leggo
					new ASN1StreamParser(new Base64InputStream(FileUtils.openInputStream(tmp)), maxByteRead).readObject();
					// Base64InputStream streambase64 = new Base64InputStream(FileUtils.openInputStream(tmp));
					CMSSignedDataParser parser = InstanceCMSSignedDataParser.getCMSSignedDataParser(tmp, true);

					signed = isSignedType(parser);
					setBase64(true);

				} else {
					// tento una lettura con max bytes se va in errore non lo leggo
					fisTmp = FileUtils.openInputStream(file);
					// log.info("Apro lo stream " + fisTmp + " sul file " + file );
					new ASN1StreamParser(new Base64InputStream(fisTmp), maxByteRead).readObject();
					// Base64InputStream streambase64 = new Base64InputStream(FileUtils.openInputStream(file));
					// CMSSignedDataParser parser = InstanceCMSSignedDataParser.getCMSSignedDataParser(file,true);
					fis2 = FileUtils.openInputStream(file);
					// log.info("Apro lo stream " + fis2 + " sul file " + file );
					CMSSignedDataParser parser = InstanceCMSSignedDataParser.getCMSSignedDataParser(fis2, true);
					signed = isSignedType(parser);
					setBase64(true);
				}

			} catch (Exception er) {
				signed = false;
				// try {
				// if(stream!=null){
				// stream.close();
				// }
				// } catch (IOException e1) {}
			}
		} finally {
			if (tmp != null) {
				FileUtil.deleteFile(tmp);
			}
			if (fis != null) {
				// log.info("chiudo lo stream " + fis);
				IOUtils.closeQuietly(fis);
				fis = null;
			}
			if (fis2 != null) {
				// log.info("chiudo lo stream " + fis2);
				IOUtils.closeQuietly(fis2);
				fis2 = null;
			}
			if (fisTmp != null) {
				// log.info("chiudo lo stream " + fisTmp);
				IOUtils.closeQuietly(fisTmp);
				fisTmp = null;
			}
			if (iterator != null)
				iterator.close();
		}

		return signed;
	}
	
	public List<ISignature> getSignatures(File file) {
		if( signatures!=null && !signatures.isEmpty() )
			return signatures;
		else 
			return super.getSignatures(file);
	}

	public SignerType getFormat() {
		// log.info("Metodo getFormat in cades " );
		return type;
	}

	/*
	 * @see it.eng.utility.cryptosigner.data.AbstractSigner#validateTimeStampTokenEmbedded()
	 */
	public ValidationInfos validateTimeStampTokensEmbedded(File file) throws CMSException {
		// log.info("Metodo validateTimeStampTokensEmbedded in cades");
		ValidationInfos validationInfos = new ValidationInfos();

		if (type == SignerType.CAdES_BES) {
			validationInfos.addErrorWithCode(MessageConstants.SIGN_FORMAT_WITHOUT_MARK,
					MessageHelper.getMessage(MessageConstants.SIGN_FORMAT_WITHOUT_MARK, this.type));
			validationInfos.addError(MessageHelper.getMessage(MessageConstants.SIGN_FORMAT_WITHOUT_MARK, this.type));
			return validationInfos;
		}

		if (this.timestamptokens == null) {
			if (!this.isSignedType(file)) {
				validationInfos.addErrorWithCode(MessageConstants.FV_FILE_FORMAT_ERROR,
						MessageHelper.getMessage(MessageConstants.FV_FILE_FORMAT_ERROR, this.getFormat()));
				validationInfos.addError(MessageHelper.getMessage(MessageConstants.FV_FILE_FORMAT_ERROR, this.getFormat()));
				return validationInfos;
			} else
				getTimeStampTokens(file);
		}
		CMSSignedDataParser cmsSignedData = null;
		FileInputStream fis = null;
		try {
			fis = FileUtils.openInputStream(file);
			cmsSignedData = InstanceCMSSignedDataParser.getCMSSignedDataParser(fis, isBase64());
			// cmsSignedData = InstanceCMSSignedDataParser.getCMSSignedDataParser(file,isBase64());

			/*
			 * if (isBase64()) { Base64InputStream streambase64 = new Base64InputStream(FileUtils.openInputStream(file)); cmsSignedData= new
			 * CMSSignedDataParser(streambase64); } else { cmsSignedData= new CMSSignedDataParser(FileUtils.openInputStream(file)); }
			 */

			cmsSignedData.getSignedContent().drain();
			
			try {
				SignerInformationStore signersStore = cmsSignedData.getSignerInfos();
				Collection<SignerInformation> signers = signersStore.getSigners();
				if (signers != null) {
					for (SignerInformation signerInfo : signers) {
						byte[] signature = signerInfo.getSignature();
						Set<byte[]> signatures = timestamptokensBySignature.keySet();
						TimeStampToken timestamptoken = null;
						for (byte[] byteSignature : signatures) {
							if (Arrays.areEqual(byteSignature, signature)) {
								timestamptoken = timestamptokensBySignature.get(byteSignature);
								break;
							}
						}
						// MODIFICA ANNA 1.53
						// String hashAlgOID = timestamptoken.getTimeStampInfo().getMessageImprintAlgOID();
						String hashAlgOID = timestamptoken.getTimeStampInfo().getMessageImprintAlgOID().getId();
						MessageDigest digest;
						try {
							digest = MessageDigest.getInstance(hashAlgOID);
							TimeStampRequestGenerator gen = new TimeStampRequestGenerator();
							TimeStampRequest request = gen.generate(hashAlgOID, digest.digest(signature));

							checkTimeStampTokenOverRequest(validationInfos, timestamptoken, request);

						} catch (NoSuchAlgorithmException e) {
							log.error("NoSuchAlgorithmException", e);
							validationInfos.addErrorWithCode(MessageConstants.SIGN_MARK_ALGORITHM_NOTSUPPORTED,
									MessageHelper.getMessage(MessageConstants.SIGN_MARK_ALGORITHM_NOTSUPPORTED, hashAlgOID));
							validationInfos.addError(MessageHelper.getMessage(MessageConstants.SIGN_MARK_ALGORITHM_NOTSUPPORTED, hashAlgOID));
						}
					}
				}
			} catch (Exception e) {
				log.error("Errore validateTimeStampTokensEmbedded", e);
			}
			
		} catch (IOException e1) {
			throw new CMSException("Generic", e1);
		} catch (OperatorCreationException e) {
			throw new CMSException("Generic", e);
		} finally {
			if (fis != null) {
				// log.info("Chiudo lo stream");
				IOUtils.closeQuietly(fis);
			}
		}

		

		return validationInfos;
	}
	
	public Collection<? extends Certificate> getEmbeddedCertificates(File file) {
		if( certificateslist!=null)
			return certificateslist;
		else 
			return super.getEmbeddedCertificates(file);
	}

}