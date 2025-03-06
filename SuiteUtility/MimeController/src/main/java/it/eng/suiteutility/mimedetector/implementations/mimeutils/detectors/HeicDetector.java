package it.eng.suiteutility.mimedetector.implementations.mimeutils.detectors;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tika.Tika;

import eu.medsea.mimeutil.MimeType;
import eu.medsea.mimeutil.detector.MimeDetector;

public class HeicDetector extends MimeDetector {

	public static final Logger log = LogManager.getLogger(HeicDetector.class);
	
	public String getDescription() {
		return null;
	}

	protected Collection getMimeTypesByteArray(byte[] arg0)
			throws UnsupportedOperationException {
		return null;
	}

	protected Collection getMimeTypesFile(File file) throws UnsupportedOperationException {
		Collection coll = new ArrayList();
		try {
			Tika tika = new Tika();
		    log.debug("FILE " + file );
			String filetype = tika.detect(file);
		    log.debug("filetype: " + filetype);
		    if ( filetype!=null && filetype.startsWith("image/heic") ) {
				coll.add(new MimeType("image/heic"));
			}
		    
		}catch(Exception e) {
			log.error("",e);
		}finally {
			
		}
		log.debug("coll: " + coll);
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
