package it.eng.suiteutility.mimedetector.implementations.mimeutils.detectors;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;

import eu.medsea.mimeutil.MimeType;
import eu.medsea.mimeutil.detector.MimeDetector;

public class TarDetector extends MimeDetector{

	public String getDescription() {
		return "TarDetector";
	}

	protected Collection getMimeTypesByteArray(byte[] arg0)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

	protected Collection getMimeTypesFile(File file)
			throws UnsupportedOperationException {
		Collection coll = new ArrayList();
		InputStream is = null;
		ArchiveInputStream in=null;
		try {
			is = new FileInputStream(file); 
			in = new ArchiveStreamFactory().createArchiveInputStream("tar", is);
			TarArchiveEntry entry = null;
			if ((entry = (TarArchiveEntry)in.getNextEntry()) != null) {
				Calendar calendar = new GregorianCalendar();
				calendar.setTimeInMillis(0);
				if (entry.getFile()!=null || 
					(entry.getDirectoryEntries()!=null && entry.getDirectoryEntries().length!=0) || 
					entry.getMode()!=0 || 
					!entry.getModTime().equals(calendar.getTime())
					) {
					MimeType mime = new MimeType("application/x-tar");
					coll.add(mime);
				}
			}

		}catch(Exception e) {
		}
		finally {
			if (is!=null)
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
