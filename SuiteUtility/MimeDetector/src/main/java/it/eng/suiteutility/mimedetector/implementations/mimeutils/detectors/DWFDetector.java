package it.eng.suiteutility.mimedetector.implementations.mimeutils.detectors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

import eu.medsea.mimeutil.MimeType;
import eu.medsea.mimeutil.detector.MimeDetector;

public class DWFDetector extends MimeDetector {

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
		Collection coll = new ArrayList();
		try {
			FileReader fr = new FileReader(file);
			BufferedReader in = new BufferedReader(fr);
			String str;
			str = in.readLine();
			if (StringUtils.contains(str, "DWF")) {
				coll.add(new MimeType("model/vnd.dwf"));
			}
			in.close();
			fr.close();
		}catch(Exception e) {
			e.printStackTrace();
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
