package it.eng.suiteutility.mimedetector.implementations.mimeutils.detectors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import eu.medsea.mimeutil.MimeType;
import eu.medsea.mimeutil.detector.MimeDetector;

public class AutocadDetector extends MimeDetector{


	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	protected Collection getMimeTypesByteArray(byte[] arg0)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

	protected Collection getMimeTypesFile(File file)throws UnsupportedOperationException {
		Collection coll = new ArrayList();
		try {
			FileReader fr = new FileReader(file);
			BufferedReader in = new BufferedReader(fr);
			String str;
			str = in.readLine();
			if (str!=null && str.startsWith("AC")) {
				String tmp = str.substring(2,6);			
				try {
					Integer.parseInt(tmp);
					coll.add(new MimeType("application/acad"));
				}catch(NumberFormatException ex) {
					//Tipo non di autocad
				}
				
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
