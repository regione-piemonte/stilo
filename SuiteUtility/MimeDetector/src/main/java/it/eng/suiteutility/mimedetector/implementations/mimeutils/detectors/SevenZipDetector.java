package it.eng.suiteutility.mimedetector.implementations.mimeutils.detectors;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.compress.archivers.sevenz.SevenZFile;

//import net.sf.sevenzipjbinding.ArchiveFormat;
//import net.sf.sevenzipjbinding.IInArchive;
//import net.sf.sevenzipjbinding.ISevenZipInArchive;
//import net.sf.sevenzipjbinding.SevenZip;
//import net.sf.sevenzipjbinding.impl.RandomAccessFileInStream;
import eu.medsea.mimeutil.MimeType;
import eu.medsea.mimeutil.detector.MimeDetector;

public class SevenZipDetector extends MimeDetector {

	public String getDescription() {
		return "SevenZipDetector";
	}

	protected Collection getMimeTypesByteArray(byte[] arg0) throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

	protected Collection getMimeTypesFile(File file) throws UnsupportedOperationException {
		Collection coll = new ArrayList();
		/*ISevenZipInArchive/*/ SevenZFile archive = null;
		RandomAccessFile randomAccessFile = null;
		try {

			randomAccessFile = new RandomAccessFile(file, "r");

			//archive = SevenZip.openInArchive(ArchiveFormat.SEVEN_ZIP, // null -
			//													// autodetect
			//		new RandomAccessFileInStream(randomAccessFile));
			archive = new SevenZFile (file);
			
			//int numberOfItems = archive.getNumberOfItems();

			MimeType mime = new MimeType("application/x-7z");
			coll.add(mime);

		} catch (Exception e) {
			//System.out.println("no 7z mime detected");
		} finally {
			if (archive != null) {
				try {
					archive.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (randomAccessFile != null) {
				try {
					randomAccessFile.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return coll;
	}

	protected Collection getMimeTypesFileName(String arg0) throws UnsupportedOperationException {
		File file = new File(arg0);
		return getMimeTypesFile(file);
	}

	protected Collection getMimeTypesInputStream(InputStream arg0) throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

	protected Collection getMimeTypesURL(URL arg0) throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

}
