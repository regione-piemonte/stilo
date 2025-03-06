package it.eng.suiteutility.mimedetector.implementations.tika;

import it.eng.suiteutility.mimedetector.AbstractMimeDetector;
import it.eng.suiteutility.mimedetector.DetectorPrototype;
import it.eng.suiteutility.mimedetector.MimeDetectorConfig;
import it.eng.suiteutility.mimedetector.MimeDetectorException;
import it.eng.suiteutility.mimedetector.MimeDetectorUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.activation.MimeType;

import org.apache.log4j.Logger;
import org.apache.tika.detect.Detector;
import org.apache.tika.metadata.Metadata;

public class TikaAdapter extends AbstractMimeDetector {
	
	protected static MimeDetectorConfig defaultConfig = null;
	
	Logger log = Logger.getLogger(this.getClass().getName());
	
	public static final String DEFAULT_CONFIG_LOCATION ="/it/eng/sga/mimedetector/implementations/tika/config/tika.xml";
	
	Collection<Detector> detectors = Collections.synchronizedCollection(new HashSet<Detector>());
	
	/*
	 * WARN: di default l'utilizzo dei Rereadableinputstream di Tika effettuano
	 * una copia temporanea del file. Per evitare questo occorre creare un Bufferedinputstream
	 * per ogni detector
	 */
	@SuppressWarnings("unchecked")
	public Map<MimeType, Integer> innerDetect(Object source) throws MimeDetectorException {
		Collection<DetectorPrototype> detectors = registeredDetectors.keySet();
		Map<MimeType, Integer> resultTypes = new HashMap<MimeType, Integer>();
		InputStream is = null;
		try {
			for (DetectorPrototype detectorProto : detectors) {
				Class<Object> detectorWrapperClass= (Class<Object>)Class.forName(detectorProto.getClazz());
				Object detectorImpl = registeredDetectors.get(detectorProto);
				Method mthd = detectorWrapperClass.getMethod("getDetector",(Class[])null);
				Detector tmpDetector = (Detector)mthd.invoke(detectorImpl, (Object[])null);
				
				if (source instanceof InputStream)
					is = (InputStream)source;
				else if (source instanceof String)
					is = new FileInputStream((String)source);
				else if (source instanceof URL)
					is = new FileInputStream(((URL)source).getFile());
				else if (source instanceof byte[])
					is = new ByteArrayInputStream((byte[])source);
				else if (source instanceof File)
					is = new FileInputStream((File)source);
				
				org.apache.tika.mime.MediaType mediaType = tmpDetector.detect(new BufferedInputStream(is), new Metadata());
				if (mediaType!=null) {
					MimeType resultType = new MimeType(mediaType.getType(), mediaType.getSubtype());
					if (!resultTypes.containsKey(resultType)
							|| resultTypes.get(resultType).intValue() < detectorProto.getRole()) {
						/*
						 * TODO: DA MODIFICARE!!!!
						 */
						for (MimeType mime: detectorProto.getMimes()) {
							//Se i due tipi matchano bisogna settare il campo specificity
							if (mime.match(resultType)) {
								log.debug("[Tika] Detector: " + detectorProto.getName() + " [role: "+detectorProto.getRole()+"] detected type: " + mime);
								resultType.setParameter(MimeDetectorConfig.MIME_SPECIFICITY_ATTRIBUTE, 
										mime.getParameter(MimeDetectorConfig.MIME_SPECIFICITY_ATTRIBUTE));
							}
						}
						resultTypes.put(resultType, new Integer(detectorProto.getRole()));
					}
				}
				if (!(source instanceof InputStream))
					is.close();
			}
			return resultTypes;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new MimeDetectorException(e.getLocalizedMessage(),e);
		}
	}

	public Collection<MimeType> detectImplementation(InputStream stream) throws MimeDetectorException {
		return innerDetect(stream).keySet();
	}
	
	public Collection<MimeType> detect(File file) throws MimeDetectorException {
		return innerDetect(file).keySet();
	}
	
	public Collection<MimeType> detect(String filePath) throws MimeDetectorException {
		return innerDetect(filePath).keySet();
	}
	
	public Collection<MimeType> detect(byte[] data) throws MimeDetectorException {
		return innerDetect(data).keySet();
	}
	
	@Override
	public Collection<MimeType> detect(URI uri) throws MimeDetectorException {
		
		try {
			return innerDetect(uri.toURL()).keySet();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			throw new MimeDetectorException(e.getLocalizedMessage(),e);
		}
	}
	
	public MimeType detectBest(File file) throws MimeDetectorException {
		return MimeDetectorUtils.getBestMimeType(innerDetect(file));
	}
	
	public MimeType detectBest(String filePath) throws MimeDetectorException {
		return MimeDetectorUtils.getBestMimeType(innerDetect(filePath));
	}
	
	public MimeType detectBest(byte[] data) throws MimeDetectorException {
		return MimeDetectorUtils.getBestMimeType(innerDetect(data));
	}

	@Override
	public MimeType detectBestImplementation(InputStream stream)
			throws MimeDetectorException {
		return MimeDetectorUtils.getBestMimeType(innerDetect(stream));
	}

	@Override
	protected Collection<MimeType> detectByRegisteredPrototypeImplementation(
			DetectorPrototype detectorPrototype, Object obj)
			throws MimeDetectorException {
		Collection<MimeType> result = new ArrayList<MimeType>();
		InputStream is = null;
		try {
			Class<? extends Object> detectorWrapperClass= (Class<? extends Object>)Class.forName(detectorPrototype.getClazz());
			Object detectorImpl = registeredDetectors.get(detectorPrototype);
			Method mthd = detectorWrapperClass.getMethod("getDetector",(Class[])null);
			Detector tmpDetector = (Detector)mthd.invoke(detectorImpl, (Object[])null);
			if (obj instanceof InputStream)
				is = (InputStream)obj;
			else if (obj instanceof String)
				is = new FileInputStream((String)obj);
			else if (obj instanceof URL)
				is = new FileInputStream(((URL)obj).getFile());
			else if (obj instanceof byte[])
				is = new ByteArrayInputStream((byte[])obj);
			else if (obj instanceof File)
				is = new FileInputStream((File)obj);
			org.apache.tika.mime.MediaType mediaType = tmpDetector.detect(new BufferedInputStream(is), new Metadata());
			if (mediaType!=null) {
				MimeType resultType = new MimeType(mediaType.getType(), mediaType.getSubtype());
				result.add(resultType);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new MimeDetectorException(e.getLocalizedMessage(),e);
		}
		return result;
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
