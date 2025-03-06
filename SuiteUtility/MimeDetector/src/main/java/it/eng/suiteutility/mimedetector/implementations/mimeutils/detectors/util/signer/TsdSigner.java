package it.eng.suiteutility.mimedetector.implementations.mimeutils.detectors.util.signer;


import java.io.File;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.tsp.cms.CMSTimeStampedData;

/**
 * Implementa i controlli su firme di tipo CAdES.
 * Il contenuto di un file e riconosciuto se implementa le specifiche RFC5544
 * @author Rigo Michele
 *
 */
public class TsdSigner extends CAdESSigner {
	
	
	private CMSTimeStampedData data;
	
	@Override
	public SignerType getFormat() {
		return SignerType.TSD;
	}
	
	public boolean isSignedType(File file) {
		boolean signed = false;
		InputStream stream = null;
		try {
			stream = FileUtils.openInputStream(file);
			data = new CMSTimeStampedData(stream);
			signed = true;
		}catch(Exception e) {
			signed = false;
		} finally {
			if (stream!=null) {
				IOUtils.closeQuietly(stream);
			}
		}
		return signed;
	}
	
	public boolean isSignedType(byte[] content) {
		boolean signed = false;
		try {
			data = new CMSTimeStampedData(content);
			signed = true;
		}catch(Exception e) {
			signed = false;
		}
		return signed;
	}
	



	
	
	
	
}
