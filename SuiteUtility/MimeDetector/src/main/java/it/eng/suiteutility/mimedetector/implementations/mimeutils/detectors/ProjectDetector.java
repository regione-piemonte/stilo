package it.eng.suiteutility.mimedetector.implementations.mimeutils.detectors;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import net.sf.mpxj.mpp.MPPReader;
import net.sf.mpxj.reader.ProjectReader;
import eu.medsea.mimeutil.MimeType;
import eu.medsea.mimeutil.detector.MimeDetector;

public class ProjectDetector extends MimeDetector {

	ProjectReader reader = new MPPReader();
	
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	protected Collection getMimeTypesByteArray(byte[] arg0)
			throws UnsupportedOperationException {
		ByteArrayInputStream bis = new ByteArrayInputStream(arg0);
		return getMimeTypes(bis);
	}

	protected Collection getMimeTypesFile(File file)throws UnsupportedOperationException {
		Collection coll = new ArrayList();
		try {
			if (reader.read(file)!=null)
				coll.add(new MimeType("application/vnd.ms-project"));
		} catch (Exception e) {}
		return coll;
	}

	protected Collection getMimeTypesFileName(String arg0)
			throws UnsupportedOperationException {
		File file= new File(arg0);
		return getMimeTypesFile(file);
	}

	protected Collection getMimeTypesInputStream(InputStream arg0)
			throws UnsupportedOperationException {
		Collection coll = new ArrayList();
		try {
			if (reader.read(arg0)!=null)
			coll.add(new MimeType("application/vnd.ms-project"));
		} catch (Exception e) {}
		return coll;
	}

	protected Collection getMimeTypesURL(URL arg0)
			throws UnsupportedOperationException {
		return getMimeTypes(arg0.getFile());
	}

	
	
}

