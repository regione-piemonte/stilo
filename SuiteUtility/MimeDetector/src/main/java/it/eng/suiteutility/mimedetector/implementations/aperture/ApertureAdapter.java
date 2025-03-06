package it.eng.suiteutility.mimedetector.implementations.aperture;

import it.eng.suiteutility.mimedetector.AbstractMimeDetector;
import it.eng.suiteutility.mimedetector.DetectorPrototype;
import it.eng.suiteutility.mimedetector.MimeDetectorConfig;
import it.eng.suiteutility.mimedetector.MimeDetectorException;
import it.eng.suiteutility.mimedetector.MimeDetectorUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;

import org.apache.log4j.Logger;
import org.semanticdesktop.aperture.mime.identifier.MimeTypeIdentifier;

/*
 * FIXME: le implementazioni
 * detectBestImplementation(InputStream stream)
 * detectImplementation(InputStream stream)
 * ritornano null
 */
public class ApertureAdapter extends AbstractMimeDetector{
	
	Logger log = Logger.getLogger(this.getClass().getName());
	
	public static final String DEFAULT_CONFIG_LOCATION ="/it/eng/suiteutility/mimedetector/implementations/aperture/config/aperture.xml";
	
	protected static MimeDetectorConfig defaultConfig = null;
	
	/*
	 * FIXME: Di default Aperture non permette di fare l'identificazione
	 * utilizzando un inputstream
	 * (non-Javadoc)
	 * @see it.eng.sga.mimedetector.AbstractMimeDetector#detectBestImplementation(java.io.InputStream)
	 */
	@Override
	public MimeType detectBestImplementation(InputStream stream)
			throws MimeDetectorException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * FIXME: Di default Aperture non permette di fare l'identificazione
	 * utilizzando un inputstream
	 * (non-Javadoc)
	 * @see it.eng.sga.mimedetector.AbstractMimeDetector#detectBestImplementation(java.io.InputStream)
	 */
	@Override
	public Collection<MimeType> detectImplementation(InputStream stream)
			throws MimeDetectorException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public Map<MimeType, Integer> innerDetect(String fileName) throws MimeDetectorException {
		Collection<DetectorPrototype> detectors = registeredDetectors.keySet();
		Map<MimeType, Integer> resultTypes = new HashMap<MimeType, Integer>();
		File file = new File(fileName);
		try {
			for (DetectorPrototype detectorProto : detectors) {
				//Class<Object> detectorWrapperClass= (Class<Object>)Class.forName(detectorProto.getClazz());
				MimeTypeIdentifier identifier = (MimeTypeIdentifier)registeredDetectors.get(detectorProto);
				FileInputStream fis = new FileInputStream(file);
				byte[] buf = new byte[identifier.getMinArrayLength()];
				fis.read(buf);
				String tmpMime = identifier.identify(buf, fileName, null);
				
				if (tmpMime==null)
					break;
				
				MimeType identifiedMime = new MimeType (tmpMime);
				
				if (!resultTypes.containsKey(identifiedMime)
						|| resultTypes.get(identifiedMime).intValue() < detectorProto.getRole()) {
					for (MimeType mime: detectorProto.getMimes()) {
						//Se i due tipi matchano bisogna settare gli attributi
						if (mime.match(identifiedMime)) {
							log.debug("[Aperture] Detector: " + detectorProto.getName() + " [role: "+detectorProto.getRole()+"] detected type: " + mime);
							MimeDetectorUtils.copyMimeParameters(identifiedMime, mime);
							//identifiedMime.setParameter(MimeDetectorConfig.MIME_SPECIFICITY_ATTRIBUTE, 
							//		mime.getParameter(MimeDetectorConfig.MIME_SPECIFICITY_ATTRIBUTE));
						}
					}
					resultTypes.put(identifiedMime, new Integer(detectorProto.getRole()));
				}
			}
			return resultTypes;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new MimeDetectorException(e.getLocalizedMessage(),e);
		}
	}
	
	public MimeType detectBest(String filePath) throws MimeDetectorException {
		return MimeDetectorUtils.getBestMimeType(innerDetect(filePath));
	}
	
	public Collection<MimeType> detect(String filePath) throws MimeDetectorException {
		return innerDetect(filePath).keySet();
	}

	@Override
	protected Collection<MimeType> detectByRegisteredPrototypeImplementation(
			DetectorPrototype detectorPrototype, Object obj)
			throws MimeDetectorException {
		Collection<MimeType> resultMimes = new ArrayList<MimeType>();
		Object detectorImpl = registeredDetectors.get(detectorPrototype);
		MimeTypeIdentifier identifier = (MimeTypeIdentifier) detectorImpl;
		byte[] buf = new byte[identifier.getMinArrayLength()];
		String fileName = null;
		try {
			if (obj instanceof InputStream)
				return null;
			else if (obj instanceof byte[])
				return null;
			else if (obj instanceof File) {
				FileInputStream fis = new FileInputStream((File)obj);
				fis.read(buf);
				fileName = ((File)obj).getAbsolutePath();
			}
			else if (obj instanceof String) {
				File file = new File((String)obj);
				FileInputStream fis = new FileInputStream(file);
				fis.read(buf);
				fileName = (String)obj;
			}
		} catch (Exception e) {
			throw new MimeDetectorException(e.getLocalizedMessage(),e);
		}
		String tmpMime = identifier.identify(buf, fileName, null);
		
		if (tmpMime==null)
			return null;
		MimeType mime;
		try {
			mime = new MimeType(tmpMime);
			resultMimes.add(mime);
		} catch (MimeTypeParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultMimes;
	}

	@Override
	protected String getDefaultConfigLocation() {
		return DEFAULT_CONFIG_LOCATION;
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
