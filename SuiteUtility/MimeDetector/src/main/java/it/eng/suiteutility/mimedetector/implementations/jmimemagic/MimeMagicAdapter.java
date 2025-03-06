package it.eng.suiteutility.mimedetector.implementations.jmimemagic;

import it.eng.suiteutility.mimedetector.AbstractMimeDetector;
import it.eng.suiteutility.mimedetector.DetectorPrototype;
import it.eng.suiteutility.mimedetector.MimeDetectorConfig;
import it.eng.suiteutility.mimedetector.MimeDetectorException;

import java.io.InputStream;
import java.util.Collection;

import javax.activation.MimeType;

public class MimeMagicAdapter extends AbstractMimeDetector {

	protected static MimeDetectorConfig defaultConfig = null;
	
	@Override
	public MimeType detectBestImplementation(InputStream stream)
			throws MimeDetectorException {
		return null;
	}

	@Override
	public Collection<MimeType> detectImplementation(InputStream stream)
			throws MimeDetectorException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Collection<MimeType> detectByRegisteredPrototypeImplementation(
			DetectorPrototype detectorPrototype, Object obj)
			throws MimeDetectorException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getDefaultConfigLocation() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected MimeDetectorConfig getDefaultConfig() {
		return defaultConfig;
	}

	@Override
	protected void setDefaultConfig(MimeDetectorConfig config) {
		synchronized (config) {
			defaultConfig  = config;	
		}
	}
}
