package net.sf.jsignpdf;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CRL;
import java.security.cert.Certificate;
import java.util.Collection;
import java.util.Map;

import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedDataParser;
import org.bouncycastle.cms.CMSTypedStream;
import org.bouncycastle.mail.smime.SMIMEUtil;
import org.bouncycastle.mail.smime.util.FileBackedMimeBodyPart;
import org.bouncycastle.mail.smime.util.SharedFileInputStream;
import org.bouncycastle.operator.OperatorCreationException;

import javax.mail.MessagingException;


/**
 * Implementa i controlli su firme di tipo M7M.
 * Il contenuto di un file e riconosciuto se implementa le specifiche S/MIME
 * @author Stefano Zennaro
 */
public class M7MSigner extends AbstractSigner {

	private MimeBodyPart p7mPart = null;
//	
//	
//	public TimeStampToken[] getTimeStampTokens() {
//		if (timestamptokens==null) {
//			TimeStampResponse resp = null;
//			TimeStampToken  tsToken = null;
//			InputStream stream = null;		
//			try {
//				stream = FileUtils.openInputStream(getFile());
//		        MimeMessage mm = new MimeMessage(null, stream);
//		        MimeMultipart mmp = (MimeMultipart)mm.getContent();
//		        int count = mmp.getCount();
//		        for (int i = 0; i < count; i++)
//		        {
//		            MimeBodyPart mbp = (MimeBodyPart)mmp.getBodyPart(i);
//		            String cType = mbp.getContentType();
//		            if (cType.startsWith("application/timestamp-reply")) {
//						byte[] buffer = null;
//						byte[] input = IOUtils.toByteArray(mbp.getInputStream());
//						try {
//							 resp = new TimeStampResponse(input);
//							 tsToken = resp.getTimeStampToken();
//						}
//						catch (Exception e1) {
//							 try {
//								 org.bouncycastle.util.encoders.Base64 dec = new org.bouncycastle.util.encoders.Base64();
//								 buffer = dec.decode(input);
//							     resp = new TimeStampResponse(buffer);
//							     tsToken = resp.getTimeStampToken();
//							 }
//						     catch(Exception e) {
//						    	 try {
//									 tsToken = new TimeStampToken(new CMSSignedData(input));  
//						    	 }catch(Exception er) {
//						    		 throw new CryptoSignerException("Formato token non riconosciuto", e);	 
//						    	 }
//						     }
//						 }
//						break;
//		            }
//		        }
//			}catch(Exception e) {
//		        e.printStackTrace();
//		        tsToken = null;
//		    } finally {
//				if (stream!=null) {
//					IOUtils.closeQuietly(stream);
//				}
//			}
//
//	    	return timestamptokens= new TimeStampToken[]{tsToken};
//		} else {
//			return timestamptokens;
//		}
//	}
//
	private MimeBodyPart getP7MPart() {
		InputStream stream = null;
		try {
			stream = FileUtils.openInputStream(getFile());
	        MimeMessage mm = new MimeMessage(null, stream);
	        MimeMultipart mmp = (MimeMultipart)mm.getContent();
	        int count = mmp.getCount();
	        for (int i = 0; i < count; i++)
	        {
	            MimeBodyPart mbp = (MimeBodyPart)mmp.getBodyPart(i);
	            String cType = mbp.getContentType();
	            if (cType.startsWith("application/pkcs7-mime")) {
	            	return mbp;
	            }
	        }
		}catch (Exception e) {
			if (stream!=null)
				try {
					stream.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
	    return null;
	}
	
	
	public boolean isSignedType(File file) {
		if (file==null)
			return false;
		SharedFileInputStream stream = null;
		try {
			//stream = FileUtils.openInputStream(file);
			 stream = new SharedFileInputStream(file.getAbsolutePath());
			return isSignedType(stream);
		} catch (IOException e) {
			return false;
		} finally {
			if (stream!=null) {
				IOUtils.closeQuietly(stream);
			}
		}
	}

	private boolean isSignedType(InputStream stream) {
		//Resetto il signer
		reset();
		p7mPart = null;
		
		boolean ism7m = false;
		boolean timestamp_reply = false;
		boolean pcks7_mime = false;
		FileBackedMimeBodyPart part  = null;
		try {
			CMSTypedStream cmstype = new CMSTypedStream(stream);
			part = SMIMEUtil.toMimeBodyPart(cmstype);
			if(part.getContentType().startsWith("multipart")){
				MimeMultipart mmp = (MimeMultipart)part.getContent();
				int count = mmp.getCount();
		        for (int i = 0; i < count; i++)
		        {
		            MimeBodyPart mbp = (MimeBodyPart)mmp.getBodyPart(i);
		            String cType = mbp.getContentType();
		            if (cType.startsWith("application/timestamp-reply")) {
		            	timestamp_reply = true;
		            } else if (cType.startsWith("application/pkcs7-mime")) {
		            	pcks7_mime = true;
		            	p7mPart = mbp;
			        }
		        }
			}
		}catch(Exception e) {
			ism7m = false;
		}finally{
			if(part!=null){
				try {
					part.dispose();
				} catch (IOException e) {
					//log.warn("Impossibile cancellare il file temporaneo", e);
				}
			}
		}
		if (timestamp_reply && pcks7_mime) {
			ism7m = true;	
		}
		return ism7m;
	}
	
	
//	/**
//	 * Restituisce true se il contenuto e di tipo S/MIME
//	 * e contiene le seguenti parti:
//	 * <ul>
//	 * 	<li>application/timestamp-reply</li>
//	 * 	<li>application/pkcs7-mime</li>
//	 * </ul>
//	 * 
//	 * @return boolean 
//	 */
//	public boolean isSignedType(byte[] content) {
//		ByteArrayInputStream bais = null;
//		try {
//			bais = new ByteArrayInputStream(content);
//			return isSignedType(bais);
//		} finally {
//			if (bais!=null)
//				try {
//					bais.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//		}
//	}
	
	
	public SignerType getFormat() {
		return SignerType.M7M;
	}
	

	
//	public ValidationInfos validateTimeStampTokensEmbedded() {
//		ValidationInfos validationInfos =new ValidationInfos();
//		if (this.file == null) {
//			validationInfos.addErrorWithCode( MessageConstants.GM_FILE_INPUT_NULL, MessageHelper.getMessage( MessageConstants.GM_FILE_INPUT_NULL ) );
//			validationInfos.addError( MessageHelper.getMessage( MessageConstants.GM_FILE_INPUT_NULL ) );
//			return validationInfos;
//		}
//		if (this.timestamptokens == null) {
//			getTimeStampTokens();
//		}
////		validationInfos.setValidatedObject(timestamptoken);
//		try {
//			if (timestamptokens==null)
//				throw new Exception();
//			for (TimeStampToken timestamptoken: timestamptokens) {
//				TimeStampRequestGenerator gen = new TimeStampRequestGenerator();
//				String hashAlgOID = timestamptoken.getTimeStampInfo().getMessageImprintAlgOID();			
//				MessageDigest digest = MessageDigest.getInstance(hashAlgOID);
//				TimeStampRequest request = gen.generate(hashAlgOID, digest.digest(IOUtils.toByteArray(p7mPart.getInputStream())) );
//				
//				this.checkTimeStampTokenOverRequest(validationInfos, timestamptoken, request);
//			}
//		}catch (MessagingException e) {
//			validationInfos.addErrorWithCode( MessageConstants.SIGN_SIGNEDFILE_NOTFOUND, MessageHelper.getMessage( MessageConstants.SIGN_SIGNEDFILE_NOTFOUND ) );
//			validationInfos.addError( MessageHelper.getMessage( MessageConstants.SIGN_SIGNEDFILE_NOTFOUND ) );
//		} catch (NoSuchAlgorithmException e) {
//			validationInfos.addErrorWithCode( MessageConstants.SIGN_ALGORITHM_NOTFOUND, MessageHelper.getMessage( MessageConstants.SIGN_ALGORITHM_NOTFOUND ) );
//			validationInfos.addError( MessageHelper.getMessage( MessageConstants.SIGN_ALGORITHM_NOTFOUND ) );
//		} catch (Exception e) {
//			validationInfos.addErrorWithCode( MessageConstants.SIGN_TOKEN_WITHOUT_VALIDMARK, MessageHelper.getMessage( MessageConstants.SIGN_TOKEN_WITHOUT_VALIDMARK ) );
//			validationInfos.addError( MessageHelper.getMessage( MessageConstants.SIGN_TOKEN_WITHOUT_VALIDMARK ) );
//		} 
//		
//		return validationInfos;
//	}
//
	public InputStream getUnsignedContent() {
		File detachedFile = getDetachedFile();
		
		//Si tratta della firma di un file detached?
		// - in teoria possono esistere m7m detached..
		if (detachedFile != null) {
			try {
				return FileUtils.openInputStream(detachedFile);
			} catch (IOException e1) {
				return null;
			}
		}
		//FIXME to checkmod cost fallisce poichè lo stream associato è già chiuso per cui ricrealo!?
		p7mPart=null;
		if (p7mPart == null)
			p7mPart = getP7MPart();
		try {
			Object content = p7mPart.getContent();
			
			if (content instanceof InputStream) 
			{
				//CMSSignedDataParser sd = new CMSSignedDataParser((InputStream)content);
				CMSSignedDataParser sd = InstanceCMSSignedDataParser.getCMSSignedDataParser((InputStream)content,false);
				
				
//				TODO: modificato, da testare
//				return P7MSigner.getCMSSignedDataUnsignedContent(sd);
				
				return sd.getSignedContent().getContentStream();
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (MessagingException e) {
			
			e.printStackTrace();
		} catch (CMSException e) {
			
			e.printStackTrace();
		} catch (OperatorCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
//
//	
//	public byte[] getUnsignedContentDigest(MessageDigest digestAlgorithm) {
//		InputStream unsignedContent = this.getUnsignedContent();
//		try {
//			return digestAlgorithm.digest(IOUtils.toByteArray(unsignedContent));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			if (unsignedContent!=null)
//				try {
//					unsignedContent.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//		}
//		return null;
//	}
//
//	
//	public List<ISignature> getSignatures() {
//		Object content;
//		try {
//			content = p7mPart.getContent();
//			if (content instanceof InputStream) {
//				CMSSignedDataParser cmsSignedData = new CMSSignedDataParser((InputStream)content);
//				cmsSignedData.getSignedContent().drain();
//				return P7MSigner.getISigneturesFromCMSSignedData(cmsSignedData, detachedFiles);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

	
	public boolean canContentBeSigned() {
		return true;
	}

	public Collection<CRL> getEmbeddedCRLs() {
		Object content;
		try {
			content = p7mPart.getContent();
			if (content instanceof InputStream) {
				//CMSSignedDataParser cmsSignedData = new CMSSignedDataParser((InputStream)content);
				CMSSignedDataParser cmsSignedData = InstanceCMSSignedDataParser.getCMSSignedDataParser((InputStream)content,false);
				
				
				return P7MSigner.getCRLsFromCMSSignedData((InputStream)content, cmsSignedData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Collection<? extends Certificate> getEmbeddedCertificates() {
		Object content;
		try {
			content = p7mPart.getContent();
			if (content instanceof InputStream) {
				//CMSSignedDataParser cmsSignedData = new CMSSignedDataParser((InputStream)content);
				CMSSignedDataParser cmsSignedData = InstanceCMSSignedDataParser.getCMSSignedDataParser((InputStream)content,false);
				
				return P7MSigner.getCertificatesFromCMSSignedData((InputStream)content, cmsSignedData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


//	@Override
//	public Map<byte[], TimeStampToken> getMapSignatureTimeStampTokens() {
//		// TODO Auto-generated method stub
//		return null;
//	}
}
