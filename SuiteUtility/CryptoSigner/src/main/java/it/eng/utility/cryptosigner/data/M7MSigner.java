package it.eng.utility.cryptosigner.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CRL;
import java.security.cert.Certificate;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.SharedFileInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataParser;
import org.bouncycastle.cms.CMSTypedStream;
import org.bouncycastle.mail.smime.SMIMEUtil;
import org.bouncycastle.mail.smime.util.FileBackedMimeBodyPart;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.tsp.TimeStampRequest;
import org.bouncycastle.tsp.TimeStampRequestGenerator;
import org.bouncycastle.tsp.TimeStampResponse;
import org.bouncycastle.tsp.TimeStampToken;

import it.eng.utility.cryptosigner.controller.bean.ValidationInfos;
import it.eng.utility.cryptosigner.data.signature.ISignature;
import it.eng.utility.cryptosigner.data.type.SignerType;
import it.eng.utility.cryptosigner.exception.CryptoSignerException;
import it.eng.utility.cryptosigner.utils.InstanceCMSSignedDataParser;
import it.eng.utility.cryptosigner.utils.MessageConstants;
import it.eng.utility.cryptosigner.utils.MessageHelper;

/**
 * Implementa i controlli su firme di tipo M7M. Il contenuto di un file e riconosciuto se implementa le specifiche S/MIME
 * 
 * @author Stefano Zennaro
 */
public class M7MSigner extends AbstractSigner {

	private MimeBodyPart p7mPart = null;

	private static final Logger log = Logger.getLogger(M7MSigner.class);

	public TimeStampToken[] getTimeStampTokens(File file) {
		// log.info("Metodo getTimeStampTokens in M7m");
		if (timestamptokens == null) {
			TimeStampResponse resp = null;
			TimeStampToken tsToken = null;
			InputStream stream = null;
			try {

				stream = FileUtils.openInputStream(file);
				MimeMessage mm = new MimeMessage(null, stream);
				MimeMultipart mmp = (MimeMultipart) mm.getContent();
				int count = mmp.getCount();
				for (int i = 0; i < count; i++) {
					MimeBodyPart mbp = (MimeBodyPart) mmp.getBodyPart(i);
					String cType = mbp.getContentType();
					if (cType.startsWith("application/timestamp-reply")) {
						byte[] buffer = null;
						byte[] input = IOUtils.toByteArray(mbp.getInputStream());
						try {
							resp = new TimeStampResponse(input);
							tsToken = resp.getTimeStampToken();
						} catch (Exception e1) {
							try {
								org.bouncycastle.util.encoders.Base64 dec = new org.bouncycastle.util.encoders.Base64();
								buffer = dec.decode(input);
								resp = new TimeStampResponse(buffer);
								tsToken = resp.getTimeStampToken();
							} catch (Exception e) {
								try {
									tsToken = new TimeStampToken(new CMSSignedData(input));
								} catch (Exception er) {
									throw new CryptoSignerException("Formato token non riconosciuto", e);
								}
							}
						}
						break;
					}
				}
			} catch (Exception e) {
				log.warn("Eccezione getTimeStampTokens", e);
				tsToken = null;
			} finally {
				if (stream != null) {
					IOUtils.closeQuietly(stream);
				}
			}

			return timestamptokens = new TimeStampToken[] { tsToken };
		} else {
			return timestamptokens;
		}
	}

	private MimeBodyPart getP7MPart(File file) {
		InputStream stream = null;
		try {
			stream = FileUtils.openInputStream(file);
			MimeMessage mm = new MimeMessage(null, stream);
			MimeMultipart mmp = (MimeMultipart) mm.getContent();
			int count = mmp.getCount();
			for (int i = 0; i < count; i++) {
				MimeBodyPart mbp = (MimeBodyPart) mmp.getBodyPart(i);
				String cType = mbp.getContentType();
				if (cType.startsWith("application/pkcs7-mime")) {
					return mbp;
				}
			}
		} catch (Exception e) {
			if (stream != null) {
				log.warn("Eccezione getP7MPart", e);
				IOUtils.closeQuietly(stream);
			}
		}
		return null;
	}

	public boolean isSignedType(File file) {
		if (file == null)
			return false;
		SharedFileInputStream stream = null;
		try {
			// stream = FileUtils.openInputStream(file);
			stream = new SharedFileInputStream(file.getAbsolutePath());
			return isSignedType(stream);
		} catch (IOException e) {

			return false;
		} finally {
			if (stream != null) {
				IOUtils.closeQuietly(stream);
			}
		}
	}

//	public static void main(String[] args) {
//		M7MSigner signer = new M7MSigner();
//		File file = new File("C:/Users/Anna Tesauro/Desktop/domanda_dia.pdf - Copia.m7m");
//		//signer.setFile(file);
//		boolean isOk = signer.isSignedType(file);
//
//		if (isOk) {
//			try {
//				InputStream stream = signer.getContentAsInputStream();
//
//				signer.getTimeStampTokens();
//				signer.getMapSignatureTimeStampTokens();
//				// signer.getUnsignedContent();
//				signer.getSignatures();
//				signer.getEmbeddedCRLs();
//				signer.getEmbeddedCertificates();
//				// signer.validateTimeStampTokensEmbedded();
//				signer.getFormat();
//				signer.getTimeStampTokens();
//				signer.canContentBeSigned();
//
//				File extracttempfile = File.createTempFile("Extract", "file", new File("C:/Users/Anna Tesauro/Desktop/output/"));
//				FileOutputStream fos = new FileOutputStream(extracttempfile);
//				IOUtils.copyLarge(stream, fos);
//				fos.close();
//				if (stream != null)
//					stream.close();
//				signer.closeFileStream();
//			} catch (IOException e) {
//				log.warn("IOException", e);
//			}
//		} else {
//			signer.closeFileStream();
//		}
//
//		boolean esitoCancellazione = file.delete();
//		log.info("Esito cancellazione " + esitoCancellazione);
//	}

	private boolean isSignedType(InputStream stream) {
		// Resetto il signer
		reset();
		p7mPart = null;

		boolean ism7m = false;
		boolean timestamp_reply = false;
		boolean pcks7_mime = false;
		FileBackedMimeBodyPart part = null;
		try {
			CMSTypedStream cmstype = new CMSTypedStream(stream);
			part = SMIMEUtil.toMimeBodyPart(cmstype);
			if (part.getContentType().startsWith("multipart")) {
				MimeMultipart mmp = (MimeMultipart) part.getContent();
				int count = mmp.getCount();
				for (int i = 0; i < count; i++) {
					MimeBodyPart mbp = (MimeBodyPart) mmp.getBodyPart(i);
					String cType = mbp.getContentType();
					if (cType.startsWith("application/timestamp-reply")) {
						timestamp_reply = true;
					} else if (cType.startsWith("application/pkcs7-mime")) {
						pcks7_mime = true;
						p7mPart = mbp;
					}
				}
			}
		} catch (Exception e) {
			ism7m = false;
		} finally {
			if (part != null) {
				try {
					part.dispose();
				} catch (IOException e) {
					log.warn("Impossibile cancellare il file temporaneo", e);
				}
			}
		}
		if (timestamp_reply && pcks7_mime) {
			ism7m = true;
		}
		return ism7m;
	}

	// /**
	// * Restituisce true se il contenuto e di tipo S/MIME
	// * e contiene le seguenti parti:
	// * <ul>
	// * <li>application/timestamp-reply</li>
	// * <li>application/pkcs7-mime</li>
	// * </ul>
	// *
	// * @return boolean
	// */
	// public boolean isSignedType(byte[] content) {
	// ByteArrayInputStream bais = null;
	// try {
	// bais = new ByteArrayInputStream(content);
	// return isSignedType(bais);
	// } finally {
	// if (bais!=null)
	// try {
	// bais.close();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// }

	public SignerType getFormat() {
		return SignerType.M7M;
	}

	public ValidationInfos validateTimeStampTokensEmbedded(File file) {
		ValidationInfos validationInfos = new ValidationInfos();
		if (file == null) {
			validationInfos.addErrorWithCode(MessageConstants.GM_FILE_INPUT_NULL, MessageHelper.getMessage(MessageConstants.GM_FILE_INPUT_NULL));
			validationInfos.addError(MessageHelper.getMessage(MessageConstants.GM_FILE_INPUT_NULL));
			return validationInfos;
		}
		if (this.timestamptokens == null) {
			getTimeStampTokens(file);
		}
		// validationInfos.setValidatedObject(timestamptoken);
		try {
			if (timestamptokens == null)
				throw new Exception();
			for (TimeStampToken timestamptoken : timestamptokens) {
				TimeStampRequestGenerator gen = new TimeStampRequestGenerator();
				// MODIFICA ANNA 1.53
				// String hashAlgOID = timestamptoken.getTimeStampInfo().getMessageImprintAlgOID();
				String hashAlgOID = timestamptoken.getTimeStampInfo().getMessageImprintAlgOID().getId();
				log.info("hashAlgOID " + hashAlgOID);
				MessageDigest digest = MessageDigest.getInstance(hashAlgOID);
				TimeStampRequest request = gen.generate(hashAlgOID, digest.digest(IOUtils.toByteArray(p7mPart.getInputStream())));

				this.checkTimeStampTokenOverRequest(validationInfos, timestamptoken, request);
			}
		} catch (MessagingException e) {
			validationInfos.addErrorWithCode(MessageConstants.SIGN_SIGNEDFILE_NOTFOUND, MessageHelper.getMessage(MessageConstants.SIGN_SIGNEDFILE_NOTFOUND));
			validationInfos.addError(MessageHelper.getMessage(MessageConstants.SIGN_SIGNEDFILE_NOTFOUND));
		} catch (NoSuchAlgorithmException e) {
			log.error("", e);
			validationInfos.addErrorWithCode(MessageConstants.SIGN_ALGORITHM_NOTFOUND, MessageHelper.getMessage(MessageConstants.SIGN_ALGORITHM_NOTFOUND));
			validationInfos.addError(MessageHelper.getMessage(MessageConstants.SIGN_ALGORITHM_NOTFOUND));
		} catch (Exception e) {
			validationInfos.addErrorWithCode(MessageConstants.SIGN_TOKEN_WITHOUT_VALIDMARK,
					MessageHelper.getMessage(MessageConstants.SIGN_TOKEN_WITHOUT_VALIDMARK));
			validationInfos.addError(MessageHelper.getMessage(MessageConstants.SIGN_TOKEN_WITHOUT_VALIDMARK));
		}

		return validationInfos;
	}

	public InputStream getUnsignedContent(File file) {
		// log.info("Metodo getUnsignedContent in M7M");
		File detachedFile = getDetachedFile();

		// Si tratta della firma di un file detached?
		// - in teoria possono esistere m7m detached..
		if (detachedFile != null) {
			try {
				return FileUtils.openInputStream(detachedFile);
			} catch (IOException e1) {
				return null;
			}
		}
		// FIXME to checkmod cost fallisce poichè lo stream associato è già chiuso per cui ricrealo!?
		p7mPart = null;
		if (p7mPart == null)
			p7mPart = getP7MPart(file);
		try {
			Object content = p7mPart.getContent();

			if (content instanceof InputStream) {
				// CMSSignedDataParser sd = new CMSSignedDataParser((InputStream)content);
				CMSSignedDataParser sd = InstanceCMSSignedDataParser.getCMSSignedDataParser((InputStream) content, false);

				// TODO: modificato, da testare
				// return P7MSigner.getCMSSignedDataUnsignedContent(sd);

				return sd.getSignedContent().getContentStream();
			}
		} catch (IOException e) {
			log.warn("IOException", e);
		} catch (MessagingException e) {
			log.warn("MessagingException", e);
		} catch (CMSException e) {
			log.warn("CMSException", e);
		} catch (OperatorCreationException e) {
			log.warn("OperatorCreationException", e);
		}
		return null;
	}

	public byte[] getUnsignedContentDigest(MessageDigest digestAlgorithm, File file) {
		InputStream unsignedContent = this.getUnsignedContent(file);
		try {
			return digestAlgorithm.digest(IOUtils.toByteArray(unsignedContent));
		} catch (IOException e) {
			log.warn("IOException", e);
		} finally {
			if (unsignedContent != null)
				try {
					unsignedContent.close();
				} catch (IOException e) {
					log.warn("IOException", e);
				}
		}
		return null;
	}

	public List<ISignature> getSignatures(File file) {
		Object content;
		try {
			content = p7mPart.getContent();
			if (content instanceof InputStream) {
				// CMSSignedDataParser cmsSignedData = new CMSSignedDataParser((InputStream)content);

				CMSSignedDataParser cmsSignedData = InstanceCMSSignedDataParser.getCMSSignedDataParser((InputStream) content, false);

				cmsSignedData.getSignedContent().drain();
				return null;//P7MSigner.getISigneturesFromCMSSignedData((InputStream) content, cmsSignedData, detachedFiles);
			}
		} catch (Exception e) {
			log.warn("Eccezione getSignatures", e);
		}
		return null;
	}

	public boolean canContentBeSigned() {
		return true;
	}

	public Collection<CRL> getEmbeddedCRLs(File file) {
		Object content;
		try {
			content = p7mPart.getContent();
			if (content instanceof InputStream) {
				// CMSSignedDataParser cmsSignedData = new CMSSignedDataParser((InputStream)content);
				CMSSignedDataParser cmsSignedData = InstanceCMSSignedDataParser.getCMSSignedDataParser((InputStream) content, false);

				return P7MSigner.getCRLsFromCMSSignedData((InputStream) content, cmsSignedData);
			}
		} catch (Exception e) {
			log.warn("Eccezione getEmbeddedCRLs", e);
		}
		return null;
	}

	public Collection<? extends Certificate> getEmbeddedCertificates(File file) {
		Object content;
		try {
			content = p7mPart.getContent();
			if (content instanceof InputStream) {
				// CMSSignedDataParser cmsSignedData = new CMSSignedDataParser((InputStream)content);
				CMSSignedDataParser cmsSignedData = InstanceCMSSignedDataParser.getCMSSignedDataParser((InputStream) content, false);

				return P7MSigner.getCertificatesFromCMSSignedData((InputStream) content, cmsSignedData);
			}
		} catch (Exception e) {
			log.warn("Eccezione getEmbeddedCertificates", e);
		}
		return null;
	}

	@Override
	public Map<byte[], TimeStampToken> getMapSignatureTimeStampTokens(File file) {
		return null;
	}

}
