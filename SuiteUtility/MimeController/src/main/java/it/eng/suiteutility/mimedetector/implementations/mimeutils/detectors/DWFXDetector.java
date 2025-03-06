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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.openxml4j.opc.PackageRelationshipCollection;
import org.apache.tika.Tika;

import eu.medsea.mimeutil.MimeType;
import eu.medsea.mimeutil.detector.MimeDetector;


public class DWFXDetector extends MimeDetector {

	public static final Logger log = LogManager.getLogger(DWFXDetector.class);
	
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
		//log.debug("DWFXDetector");
		Collection coll = new ArrayList();
		//FileInputStream in = null;
		try {
			//in = new FileInputStream(file);
			/*OPCPackage pkg = OPCPackage.open(in);
			PackageRelationshipCollection dwfxSeq = 
	                pkg.getRelationshipsByType("http://schemas.autodesk.com/dwfx/2007/relationships/documentsequence");
			if (dwfxSeq.size() == 1) {
				coll.add(new MimeType("model/vnd.dwfx+xps"));
			}*/
			Tika tika = new Tika();
		    //detecting the file type using detect method
			log.debug("file: " + file);
		    String filetype = tika.detect(file);
		    log.debug("filetype: " + filetype);
		    if ( filetype!=null && filetype.equalsIgnoreCase("model/vnd.dwfx+xps") ) {
				coll.add(new MimeType("model/vnd.dwfx+xps"));
			} else if ( filetype!=null && filetype.equalsIgnoreCase("application/vnd.ms-xpsdocument") ) {
				coll.add(new MimeType("model/vnd.dwfx+xps"));
			}  
			/*FileReader fr = new FileReader(file);
			BufferedReader in = new BufferedReader(fr);
			String str;
			str = in.readLine();
			while(str!=null && coll.isEmpty()){
				if (StringUtils.contains(str, "DWF") || StringUtils.contains(str, "DWFX")) {
					coll.add(new MimeType("model/vnd.dwfx+xps"));
				}
				str = in.readLine();
			}*/
			//in.close();
			//fr.close();
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
