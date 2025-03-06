package it.eng.suiteutility.mimedetector.implementations.mimeutils.detectors.util.signer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.tsp.TimeStampResponse;
import org.bouncycastle.tsp.TimeStampToken;


/**
 * Implementa i controlli su firme di tipo CAdES.
 * Il contenuto di un file e riconosciuto se implementa le specifiche RFC3161
 * @author Stefano Zennaro
 *
 */
public class TsrSigner extends AbstractSigner{

	CMSSignedData cmsSignedData = null;

	public boolean isSignedType(File file) {
		if (file==null)
			return false;
		byte[] buffer = null;
		 try {
			buffer = FileUtils.readFileToByteArray(file);
			return isSignedType(buffer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		return false;
	}	
	
	/**
	 * Restituisce true se il contenuto corrisponte alla codifica di un 
	 * TimeStampResponse
	 * @param buffer array
	 * @return boolean 
	 */
	public boolean isSignedType(byte[] buffer) {
		TimeStampToken timestamptoken= null;
		boolean isTsr = false;
		 InputStream stream = null;
		 try {
//			 buffer = FileUtils.readFileToByteArray(file);			 
			 timestamptoken = new TimeStampResponse(buffer).getTimeStampToken();
			 isTsr = true;	 
		 }
		 catch ( Exception e1 ) {
			 try {
				 byte[] buffer64 = org.bouncycastle.util.encoders.Base64.decode(buffer);
				 timestamptoken = new TimeStampResponse(buffer64).getTimeStampToken();
		    	 isTsr = true;
		     }
		     catch ( Exception e ) {
		    	try {
		    		timestamptoken = new TimeStampToken(cmsSignedData= new CMSSignedData(buffer));
		    		isTsr = true;
				} catch (Exception e2) {
					isTsr = false;
				}
		    	 
		     }
		 } finally {
			if (stream!=null) {
				IOUtils.closeQuietly(stream);
			}
		 }
		 if (timestamptoken!=null) {
			 TimeStampToken[] tok = new TimeStampToken[]{timestamptoken};
		 }
		 return isTsr;
	}
	
	
	public SignerType getFormat() {
		return SignerType.TSR;
	}
}