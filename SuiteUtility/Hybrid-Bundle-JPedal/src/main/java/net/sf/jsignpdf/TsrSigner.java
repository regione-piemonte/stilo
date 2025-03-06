package net.sf.jsignpdf;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataParser;
import org.bouncycastle.tsp.TimeStampResponse;
import org.bouncycastle.tsp.TimeStampToken;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.tsp.TimeStampResp;


/**
 * Implementa i controlli su firme di tipo CAdES.
 * Il contenuto di un file � riconosciuto se implementa le specifiche RFC3161
 * @author Stefano Zennaro
 *
 */
public class TsrSigner extends AbstractSigner{

	CMSSignedDataParser cmsSignedData = null;
	
	public TimeStampToken[] getTimeStampTokens() {
		if (timestamptokens==null)
			isSignedType(file);
		return timestamptokens;
	}


	
	/**
	 * Restituisce true se il contenuto del file contiene la codifica di un 
	 * TimeStampResponse
	 */
	public boolean isSignedType(File file) {
		if (file==null)
			return false;
		if (file.length()>FileUtils.ONE_MB) {
			//TODO Se il file e' maggiore di 1 MB allora ritorno false per evitare degli out.of.Memory
			return false;
		}
		
		
		 TimeStampToken timestamptoken= null;
		boolean isTsr = false;
		 InputStream stream = null;
		 InputStream stream1 = null;
		 try {
			 stream = FileUtils.openInputStream(file);
			 timestamptoken = new TimeStampResponse(stream).getTimeStampToken();
			 isTsr = true;	 
		 }
		 catch ( Exception e1 ) {
			 try {
				 try {
					 stream1 = FileUtils.openInputStream(file);
					 Base64InputStream streambase64 = new Base64InputStream(stream1);
					//timestamptoken = new TimeStampResponse(streambase64).getTimeStampToken();
					 TimeStampResp  tsr=TimeStampResp.getInstance(new ASN1InputStream(streambase64,maxByteRead).readObject());
					 timestamptoken = new TimeStampResponse(tsr).getTimeStampToken();
					 isTsr = true;
					 stream.close();
				 } catch(Exception er) {
						isTsr = false;
						try {
							stream.close();
						} catch (IOException e2) {}
					}	
			 }
		     catch ( Exception e ) {
		    	try {
		    		cmsSignedData= InstanceCMSSignedDataParser.getCMSSignedDataParser(stream, false);
		    		//new CMSSignedDataParser(stream);
		    		
		    		timestamptoken = new TimeStampToken(new CMSSignedData(cmsSignedData.getSignedContent().getContentStream()));
		    		isTsr = true;
				} catch (Exception e2) {
					isTsr = false;
				}
		    	 
		     }
		 } finally {
			if (stream!=null) {
				IOUtils.closeQuietly(stream);
			}
			if (stream1!=null) {
				IOUtils.closeQuietly(stream1);
			}
		 }
		 if (timestamptoken!=null) {
			 timestamptokens = new TimeStampToken[]{timestamptoken};
		 }
		 return isTsr;			
		
	}	
	
	public SignerType getFormat() {
		return SignerType.TSR;
	}
	
//	public ValidationInfos validateTimeStampTokensDetached(File attachedFile ) {
//		ValidationInfos validationInfos = new ValidationInfos();
//		if (this.file == null) {
//			validationInfos.addErrorWithCode( MessageConstants.GM_FILE_INPUT_NULL, MessageHelper.getMessage( MessageConstants.GM_FILE_INPUT_NULL ) );
//			validationInfos.addError( MessageHelper.getMessage( MessageConstants.GM_FILE_INPUT_NULL ) );
//			return validationInfos;
//		}
//		if (this.timestamptokens == null || this.timestamptokens.length == 0 ) {
//			if (!this.isSignedType(file)) {
//				validationInfos.addErrorWithCode( MessageConstants.GM_FILE_INPUT_NULL, MessageHelper.getMessage( MessageConstants.FV_FILE_FORMAT_ERROR, this.getFormat() ) );
//				validationInfos.addError( MessageHelper.getMessage( MessageConstants.FV_FILE_FORMAT_ERROR, this.getFormat() ) );
//				return validationInfos;
//			}
//			else getTimeStampTokens();
//		}
//		
////		validationInfos.setValidatedObject(timestamptoken);
//		String hashAlgOID =null;
//		try {
//			if (timestamptokens==null)
//				throw new Exception("Il token non contiene una marca temporale");
//			for (TimeStampToken timestamptoken: timestamptokens) {
//				TimeStampRequestGenerator gen = new TimeStampRequestGenerator();
//				hashAlgOID = timestamptoken.getTimeStampInfo().getMessageImprintAlgOID();			
//				MessageDigest digest = MessageDigest.getInstance(hashAlgOID);
//				byte[] hash = generateHash(digest, validationInfos, attachedFile);
//				TimeStampRequest request = gen.generate(hashAlgOID, hash);
//				checkTimeStampTokenOverRequest(validationInfos, timestamptoken, request);
//			}
//		} catch (Exception e) {
//			validationInfos.addErrorWithCode( MessageConstants.SIGN_MARK_VALIDATION_ERROR, MessageHelper.getMessage( MessageConstants.SIGN_MARK_VALIDATION_ERROR, e.getMessage() ) );
//			validationInfos.addError( MessageHelper.getMessage( MessageConstants.SIGN_MARK_VALIDATION_ERROR, e.getMessage() ) );
//		}
//		return validationInfos;
//	}
	
//	private byte[] generateHash(MessageDigest digest, ValidationInfos validationInfos, File...files) {
//		if (files==null)
//			return null;
//		for (File file: files) {
//			InputStream fis=null;
//			try {
//				fis = new FileInputStream(file);
//				digest.update(IOUtils.toByteArray(fis));
//			} catch (FileNotFoundException e) {
//				validationInfos.addErrorWithCode( MessageConstants.SIGN_MARKEDEDFILE_NOTFOUND, MessageHelper.getMessage( MessageConstants.SIGN_MARKEDEDFILE_NOTFOUND ) );
//				validationInfos.addError( MessageHelper.getMessage( MessageConstants.SIGN_MARKEDEDFILE_NOTFOUND ) );
//			}catch (IOException e) {
//				validationInfos.addErrorWithCode( MessageConstants.SIGN_TOKEN_WITHOUT_VALIDMARK, MessageHelper.getMessage( MessageConstants.SIGN_TOKEN_WITHOUT_VALIDMARK ) );
//				validationInfos.addError( MessageHelper.getMessage( MessageConstants.SIGN_TOKEN_WITHOUT_VALIDMARK ) );
//			} finally {
//				if (fis!=null)
//					try {
//						fis.close();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//			}
//		}
//		return digest.digest();
//	}
//		
//	public byte[] getUnsignedContentDigest(MessageDigest digestAlgorithm) {
//		// TODO Auto-generated method stub
//		return null;
//	}
    
//	
//	public List<ISignature> getSignatures() {
//		if (timestamptokens==null || timestamptokens.length==0)
//			return null;
//		TimeStampToken timestamptoken = timestamptokens[0];
//		try {
//			return P7MSigner.getISigneturesFromCMSSignedDataInternal(timestamptoken.toCMSSignedData(), null);
//		} catch (CMSException e) {
//			log.error("Error", e);
//			return null;
//		}
//	}
//	
//	


//
//	
	public InputStream getUnsignedContent() {
		// TODO Auto-generated method stub
		return null;
	}
//
//
//	
//	public boolean canContentBeSigned() {
//		return false;
//	}
//
//
//	public Collection<CRL> getEmbeddedCRLs() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//
//	public Collection<? extends Certificate> getEmbeddedCertificates() {
//		// TODO Auto-generated method stub
//		return null;
//	}



//	@Override
//	public Map<byte[], TimeStampToken> getMapSignatureTimeStampTokens() {
//		if (timestamptokens==null || timestamptokens.length==0)
//			return null;
//		Map<byte[],TimeStampToken> map = new HashMap<byte[], TimeStampToken>(); 
//		TimeStampToken timestamptoken = timestamptokens[0];
//		List<ISignature> firme = getSignatures();
//		for(ISignature firma : firme ){
//			System.out.println(firma);
//			map.put( firma.getSignatureBytes(), timestamptoken);
//		}
//		return map;
//		
//	}

}