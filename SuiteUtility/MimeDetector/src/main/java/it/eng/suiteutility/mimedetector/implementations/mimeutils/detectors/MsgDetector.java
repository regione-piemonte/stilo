package it.eng.suiteutility.mimedetector.implementations.mimeutils.detectors;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hsmf.MAPIMessage;
import org.apache.tika.detect.Detector;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AutoDetectParser;

import eu.medsea.mimeutil.MimeType;
import eu.medsea.mimeutil.detector.MimeDetector;

public class MsgDetector extends MimeDetector {

	@Override
	public String getDescription() {
		return "MsgDetector";
	}

	@Override
	protected Collection getMimeTypesByteArray(byte[] arg0)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Collection getMimeTypesFile(File file)
			throws UnsupportedOperationException {
		Collection coll = new ArrayList();
		InputStream is = null;
		FileInputStream fis = null;
        try {
			fis = new FileInputStream(file);
        	is = new BufferedInputStream( fis);
			new MAPIMessage(is);
			coll.add(new MimeType("application/vnd.ms-outlook"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}finally{
			if( fis!=null)
				IOUtils.closeQuietly( fis );
			if( is!=null)
				IOUtils.closeQuietly( is );
		}
        
		return coll;
	}

	@Override
	protected Collection getMimeTypesFileName(String arg0)
			throws UnsupportedOperationException {
		File file= new File(arg0);
		return getMimeTypesFile(file);
	}

	@Override
	protected Collection getMimeTypesInputStream(InputStream arg0)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Collection getMimeTypesURL(URL arg0)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

}
