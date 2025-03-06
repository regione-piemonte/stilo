package it.eng.suiteutility.mimedetector;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.util.Collection;

import javax.activation.MimeType;

public interface MimeDetector {
		
	public Collection<MimeType> detect(File file) throws MimeDetectorException;
	
	public Collection<MimeType> detect(String filePath) throws MimeDetectorException;
	
	public Collection<MimeType> detect(byte[] data) throws MimeDetectorException;
	
	public Collection<MimeType> detect(URI uri) throws MimeDetectorException;
	
	public Collection<MimeType> detect(InputStream stream) throws MimeDetectorException;
	
	public MimeType detectBest(InputStream stream) throws MimeDetectorException;
	
	public MimeType detectBest(File file) throws MimeDetectorException;
	
	public MimeType detectBest(String filePath) throws MimeDetectorException;
	
	public MimeType detectBest(byte[] data) throws MimeDetectorException;
	
	public Collection<MimeType> getRegisteredMimeTypes();
	
	public Collection<MimeType> getRegisteredMimeTypesByDetectorName(String detectorName);
	
	public void registerDetector(DetectorPrototype detector) throws MimeDetectorException;
	
	public void unregisterDetectorByName(String detectorName);
	
	public void setConfig(MimeDetectorConfig config) throws MimeDetectorException;
	
	public MimeType detectBestRelyOnExtension(InputStream is) throws MimeDetectorException;
	
	public MimeType detectBestRelyOnExtension(File file) throws MimeDetectorException;
	
	public MimeType detectBestRelyOnExtension(String filePath) throws MimeDetectorException;
	
	public MimeType detectBestRelyOnExtension(byte[] data) throws MimeDetectorException;
	
}
