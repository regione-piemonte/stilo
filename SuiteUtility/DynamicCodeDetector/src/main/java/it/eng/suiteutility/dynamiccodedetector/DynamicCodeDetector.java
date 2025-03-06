package it.eng.suiteutility.dynamiccodedetector;

import java.io.File;

import javax.activation.MimeType;

public interface DynamicCodeDetector {
	
	public ValidationInfos detect(File file, MimeType mimeType) throws DynamicCodeDetectorException;
		
}
