package it.eng.suiteutility.mimedetector.implementations.mimeutils.detectors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tika.Tika;

import eu.medsea.mimeutil.MimeType;
import eu.medsea.mimeutil.detector.MimeDetector;

public class DWFDetector extends MimeDetector {

	public static final Logger log = LogManager.getLogger(DWFDetector.class);
	
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	protected Collection getMimeTypesByteArray(byte[] arg0)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

	protected Collection getMimeTypesFile(File file) throws UnsupportedOperationException {
		//log.debug("DWFDetector");
		Collection coll = new ArrayList();
		//FileInputStream in = null;
		try {
			//in = new FileInputStream(file);
			/*FileReader fr = new FileReader(file);
			BufferedReader in = new BufferedReader(fr);
			String str;
			str = in.readLine();
			if (StringUtils.contains(str, "DWF")) {
				coll.add(new MimeType("model/vnd.dwf"));
			}
			in.close();
			fr.close();*/
			Tika tika = new Tika();
		    //detecting the file type using detect method
		    log.debug("FILE " + file );
			String filetype = tika.detect(file);
		    log.debug("filetype: " + filetype);
		    if ( filetype!=null && filetype.startsWith("model/vnd.dwf") ) {
				coll.add(new MimeType("model/vnd.dwf"));
			}
		    
		}catch(Exception e) {
			log.error("",e);
		}finally {
			/*if (in!=null){
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}*/
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
