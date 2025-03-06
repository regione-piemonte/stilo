package it.eng.suiteutility.dynamiccodedetector;

import java.io.IOException;
import java.util.Collection;

import javax.activation.MimeType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

public class DynamicCodeDetectorFactory {

	private DynamicCodeDetectorConfig config;

	private static Logger log = LogManager.getLogger(DynamicCodeDetectorConfig.class);

	public DynamicCodeDetectorFactory() {
		try {
			config = new DynamicCodeDetectorConfig();
		} catch (DynamicCodeDetectorException e) {
			log.error("Eccezione DynamicCodeDetectorFactory", e);
		} catch (SAXException e) {
			log.error("Eccezione DynamicCodeDetectorFactory", e);
		} catch (IOException e) {
			log.error("Eccezione DynamicCodeDetectorFactory", e);
		}
	}

	public DynamicCodeDetectorFactory(DynamicCodeDetectorConfig config) {
		this.config = config;
	}

	public DynamicCodeDetector getDetectorByMimeType(MimeType mime) throws DynamicCodeDetectorException {
		if (config == null)
			throw new DynamicCodeDetectorException("Configuration not set");
		Collection<DynamicCodeDetector> detectors = config.getMimeTypesDetectorAssociation().get(mime.toString());
		if (detectors == null || detectors.size() == 0)
			return null;
		else if (detectors.size() != 1)
			throw new DynamicCodeDetectorException("Troppi detector configurati per il mime type: " + mime);
		return detectors.iterator().next();
	}

	public DynamicCodeDetectorConfig getConfig() {
		return config;
	}

	public void setConfig(DynamicCodeDetectorConfig config) {
		this.config = config;
	}
}
