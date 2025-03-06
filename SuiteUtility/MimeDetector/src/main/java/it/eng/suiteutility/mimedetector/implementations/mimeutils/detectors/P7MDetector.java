package it.eng.suiteutility.mimedetector.implementations.mimeutils.detectors;

import it.eng.suiteutility.mimedetector.implementations.mimeutils.detectors.util.signer.AbstractSigner;
import it.eng.suiteutility.mimedetector.implementations.mimeutils.detectors.util.signer.SignerType;
import it.eng.suiteutility.mimedetector.implementations.mimeutils.detectors.util.signer.SignerUtil;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import eu.medsea.mimeutil.MimeType;
import eu.medsea.mimeutil.detector.MimeDetector;

public class P7MDetector  extends MimeDetector {

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Collection getMimeTypesByteArray(byte[] arg0)	throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Collection getMimeTypesFile(File file)throws UnsupportedOperationException {
		Collection coll = new ArrayList();
		try {
			AbstractSigner signer = SignerUtil.newInstance().getSignerManager(file);
			
			if (signer.getFormat().equals(SignerType.CAdES_BES) || 
					signer.getFormat().equals(SignerType.CAdES_C) ||
					signer.getFormat().equals(SignerType.CAdES_T) ||
					signer.getFormat().equals(SignerType.CAdES_X_Long) ||
					signer.getFormat().equals(SignerType.P7M)) {
				coll.add(new MimeType("application/pkcs7-mime"));				
			} else if (signer.getFormat().equals(SignerType.TSR)) {
				coll.add(new MimeType("application/timestamp-reply"));
			} else if (signer.getFormat().equals(SignerType.TSD)) {
				coll.add(new MimeType("application/timestamped-data"));
			}
		}catch(Exception e) {
			
		}
		
		return coll;
	}

	@Override
	protected Collection getMimeTypesFileName(String path)throws UnsupportedOperationException {
		File file= new File(path);
		return getMimeTypesFile(file);
	}

	@Override
	protected Collection getMimeTypesInputStream(InputStream stream)throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Collection getMimeTypesURL(URL url)throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
