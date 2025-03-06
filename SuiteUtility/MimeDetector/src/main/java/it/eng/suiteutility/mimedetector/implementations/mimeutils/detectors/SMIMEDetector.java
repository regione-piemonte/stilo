package it.eng.suiteutility.mimedetector.implementations.mimeutils.detectors;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import javax.mail.internet.MimeMessage;

import org.apache.commons.io.IOUtils;

import eu.medsea.mimeutil.MimeType;
import eu.medsea.mimeutil.detector.MimeDetector;

public class SMIMEDetector extends MimeDetector {
		
	public String getDescription() {
		return "SMIMEDetector";
	}

	protected Collection getMimeTypesByteArray(byte[] arg0)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

	protected Collection getMimeTypesFile(File file)throws UnsupportedOperationException {
		Collection coll = new ArrayList();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			MimeMessage mimeMessage = new MimeMessage(null, fis);
			if (mimeMessage.isMimeType("multipart/signed")) {
				coll.add(new MimeType("multipart/signed"));
			}
			if (mimeMessage.isMimeType("multipart/mixed")) {
				coll.add(new MimeType("multipart/mixed"));
			}
			if (mimeMessage.isMimeType("application/pkcs7-mime")) {
				coll.add(new MimeType("application/pkcs7-mime"));
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			if( fis!=null)
				IOUtils.closeQuietly( fis );
		}
		return coll;
	}

	protected Collection getMimeTypesFileName(String arg0)
			throws UnsupportedOperationException {
		File file= new File(arg0);
		return getMimeTypesFile(file);
	}

	protected Collection getMimeTypesInputStream(InputStream arg0)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

	protected Collection getMimeTypesURL(URL arg0)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

}
