package it.eng.suiteutility.mimedetector.implementations.mimeutils.detectors;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import jxl.Workbook;

import org.apache.poi.hdgf.HDGFDiagram;
import org.apache.poi.hpbf.HPBFDocument;
import org.apache.poi.hslf.HSLFSlideShow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import eu.medsea.mimeutil.MimeType;
import eu.medsea.mimeutil.detector.MimeDetector;

public class OfficeDetector extends MimeDetector {

	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	protected Collection< ? > getMimeTypesByteArray(byte[] arg0)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection< ? > getMimeTypesFile(File file)throws UnsupportedOperationException {
		Collection<MimeType> coll = new ArrayList();
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			POIFSFileSystem fs = new POIFSFileSystem(is); 
			if (fs==null) throw new Exception("Errore nella apertura del file PowerPoint");
			Object document=null;
			try {
				if ((document=new HSLFSlideShow( fs ))!=null)
					coll.add(new MimeType("application/powerpoint"));
			}catch(Exception e) {}
			if (document==null)
				try {
					if ( ( document = new HSSFWorkbook(fs))!=null)
						coll.add(new MimeType("application/excel"));	
				}catch(Exception e) {}
			if (document==null)
				try {
					if ( ( document = new HDGFDiagram(fs))!=null)
						coll.add(new MimeType("application/visio"));
				}catch(Exception e) {}
			if (document==null)
				try {
					if ( ( document = new HWPFDocument(fs))!=null)
						coll.add(new MimeType("application/msword"));
				}catch(Exception e) {}
			if (document==null)
				try {
					if ( ( document = new HPBFDocument(fs))!=null)
						coll.add(new MimeType("application/publisher"));
				}catch(Exception e) {}
			if (document==null)
					try {
						Workbook work = Workbook.getWorkbook(file);
						if (work!=null)
							coll.add(new MimeType("application/excel"));
					}catch(Exception e) {}	
				
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

