package it.eng.suiteutility.mimedetector.implementations.tika.detectors;

import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.detect.Detector;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.microsoft.OfficeParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
public class OfficeDetector extends OfficeParser implements Detector{

	public Detector getDetector() {
		return this;
	}
	
	public MediaType detect(InputStream paramInputStream, Metadata paramMetadata)
			throws IOException {
		try {
			// TODO Auto-generated method stub
			Metadata meta = new Metadata();
			ContentHandler contenthandler = new BodyContentHandler();  
			
				super.parse(paramInputStream, contenthandler, meta);
			String content = meta.get(Metadata.CONTENT_TYPE);
			if (content==null)
				return null;
			int col = content.indexOf('/');
			String maintype = content.substring(0, col);
			String subtype = content.substring(col+1);
			
			return new MediaType(maintype, subtype);
		} catch (Exception e) {;
		} 
		return null;
	}
	
	
	
	 
	
}
