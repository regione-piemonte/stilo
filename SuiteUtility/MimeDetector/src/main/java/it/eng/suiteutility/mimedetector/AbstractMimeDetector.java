package it.eng.suiteutility.mimedetector;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import eu.medsea.mimeutil.detector.ExtensionMimeDetector;

public abstract class AbstractMimeDetector implements MimeDetector {
	
	Logger log = Logger.getLogger(this.getClass().getName());
	
	protected abstract void setDefaultConfig(MimeDetectorConfig dafaultConfig);
	
	protected abstract MimeDetectorConfig getDefaultConfig();
	
	protected Map<DetectorPrototype, Object> registeredDetectors=Collections.synchronizedMap(new HashMap<DetectorPrototype, Object>());
	
	protected ExtensionMimeDetector extensionMimeDetector = new ExtensionMimeDetector(); 
	
	//Da implementare negli adapters che sono responsabili anche 
	//del 'rewind' dell'inputstream
	public abstract Collection<MimeType> detectImplementation(InputStream stream) throws MimeDetectorException;
	public abstract MimeType detectBestImplementation(InputStream stream) throws MimeDetectorException;
	protected abstract Collection<MimeType> detectByRegisteredPrototypeImplementation(DetectorPrototype detectorPrototype, Object obj) throws MimeDetectorException;
	
	protected abstract String getDefaultConfigLocation();
	
	public AbstractMimeDetector() {
		MimeDetectorConfig defaultConfig = getDefaultConfig();
		if (defaultConfig!=null)
			try {
				this.setConfig(defaultConfig);
			} catch (MimeDetectorException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		else{
			String defaultConfigLocation = getDefaultConfigLocation();
			InputStream is = null;
			try {
				is = AbstractMimeDetector.class.getResourceAsStream(defaultConfigLocation);
				MimeDetectorConfig config = new MimeDetectorConfig(is);
				synchronized (config) {
					defaultConfig = config;	
				}
				this.setConfig(config);
			} catch (MimeDetectorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (is!=null)
					try {
						is.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
	}
	
	
	public Collection<MimeType> detect(InputStream stream) throws MimeDetectorException {
		if (!(stream.markSupported())) {
		      stream = new BufferedInputStream(stream);
	    };
	    return detectImplementation(stream);
	}

	public Collection<MimeType>  detect(File file) throws MimeDetectorException {
		Collection<MimeType> result = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);	
			result = detectImplementation(fis);
		} catch (Exception e) {
			throw new MimeDetectorException(e.getLocalizedMessage(),e);
		} finally {
			if (fis!=null)
			try {
				fis.close();
			} catch (Exception e) {}
		}
		return result;
	};
	
	public Collection<MimeType>  detect(String filePath) throws MimeDetectorException {
		try {
			return detect(new File(filePath));
		} catch (Exception e) {
			throw new MimeDetectorException(e.getLocalizedMessage(),e);
		}
	};
	
	public Collection<MimeType> detect(byte[] data) throws MimeDetectorException {
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		Collection<MimeType> result =null;
		try {
			result = detect(bis);
		} catch (Exception e) {
			throw new MimeDetectorException(e.getLocalizedMessage(),e);
		} finally {
			if (bis!=null) 
				try {
					bis.close();
				} catch (Exception e) {}
		}
		return result;
	};
	
	public Collection<MimeType> detect(URI uri) throws MimeDetectorException {
		InputStream urlis = null;
		Collection<MimeType> result = null;
		try {
			urlis = uri.toURL().openStream();
			result = detect(urlis);
		} catch (Exception e) {
			throw new MimeDetectorException(e.getLocalizedMessage(),e);
		} finally {
			if (urlis!=null)
				try {
					urlis.close();
				} catch (Exception e) {}
		}
		return result;
	}
		
	public Collection<MimeType> getRegisteredMimeTypes() {
		Collection<MimeType> mimeTypes = new HashSet<MimeType>();
		for (DetectorPrototype detector: registeredDetectors.keySet()) {
			mimeTypes.addAll(detector.getMimes());
		}
		return mimeTypes;
	}

	public Collection<MimeType> getRegisteredMimeTypesByDetectorName(
			String detectorName) {
		for (DetectorPrototype detector: registeredDetectors.keySet()) {
			if (detector.getClazz().equals(detectorName))
				return detector.getMimes();
		}
		return null;
	}

	public void registerDetector(DetectorPrototype detector)
			throws MimeDetectorException {
		try {
			//System.out.println("Registro " +detector.getClazz());
			Object object = Class.forName(detector.getClazz()).newInstance();
			registeredDetectors.put(detector, object);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MimeDetectorException(e.getLocalizedMessage(), e);
		}
	}

	public void setConfig(MimeDetectorConfig config)
			throws MimeDetectorException {
		Collection<DetectorPrototype> detectors = config.getDetectors();
		for (DetectorPrototype detector: detectors) {
			registerDetector(detector);
		}
	}

	public void unregisterDetectorByName(String detectorName) {
		registeredDetectors.remove(new DetectorPrototype(null, null, detectorName, 0));
	}

	public MimeType detectBest(InputStream stream) throws MimeDetectorException {
		MimeType result;
		result = detectBestImplementation(stream);
		return result;
	}
	
	public MimeType detectBest(File file) throws MimeDetectorException {
		FileInputStream fis = null;
		MimeType result =null;
		try {
			fis = new FileInputStream(file);
			result = detectBest(fis);
		} catch (Exception e) {
			throw new MimeDetectorException(e.getLocalizedMessage(),e);
		} finally {
			if (fis!=null)
				try {
					fis.close();
				} catch (IOException e) {}
		}
		return result;
	}
	
	public MimeType detectBest(String filePath) throws MimeDetectorException {
		return detectBest(new File(filePath));
	}
	
	public MimeType detectBest(byte[] data) throws MimeDetectorException {
		return detectBest (new ByteArrayInputStream(data));
	}
	
	/* *******************************************************************
	 * Metodi per l'analisi confidando nell'estensione del file
	 */
	
	private boolean equalMimeTypes(MimeType mime1, MimeType mime2) {
		if (mime1==null && mime2==null)
			return true;
		else if ( (mime1==null && mime2!=null) || (mime1!=null && mime2==null) )
			return false;
		else 
			return mime1.getBaseType().equals(mime2.getBaseType()) && mime1.getSubType().equals(mime2.getSubType());
	}
	
	protected Set<DetectorPrototype> getDetectorPrototypeByMimeType(MimeType mimetype) {
		if (registeredDetectors==null)
			return null;
		Set<DetectorPrototype> associatedPrototypes = new HashSet<DetectorPrototype>();
		Set<DetectorPrototype> detectorPrototypes = registeredDetectors.keySet();
		//System.out.println("Ci sono " + detectorPrototypes.size() + " detectors registrati");
		for (DetectorPrototype detectorPrototype: detectorPrototypes) {
			//System.out.println("detectorPrototype::::: " + detectorPrototype.getName());
			Collection<MimeType> detectorMimeTypes = detectorPrototype.getMimes();
			if (detectorMimeTypes!=null) {
				for (MimeType detectorMimeType: detectorMimeTypes) {
					if (equalMimeTypes(detectorMimeType, mimetype)) {
						associatedPrototypes.add(detectorPrototype);
						break;
					}
				}
			}
		}
		return associatedPrototypes;
	}

	protected MimeType detectBestUsingExtensionMimeDetector(Object obj) {
		Collection<eu.medsea.mimeutil.MimeType> extensionTypes =null;
		if (obj instanceof byte[])
			extensionTypes = extensionMimeDetector.getMimeTypes((byte[])obj);
		else if (obj instanceof File)
			extensionTypes = extensionMimeDetector.getMimeTypes((File)obj);
		else if (obj instanceof InputStream)
			extensionTypes = extensionMimeDetector.getMimeTypes((InputStream)obj);
		else if (obj instanceof String)
			extensionTypes = extensionMimeDetector.getMimeTypes((String)obj);
		else if (obj instanceof URL)
			extensionTypes = extensionMimeDetector.getMimeTypes((URL)obj);
		
		log.debug("Mime-types rilevati dall'estensione: " +  extensionTypes);
		
		if (extensionTypes!=null && extensionTypes.size()!=0) {
			eu.medsea.mimeutil.MimeType extensionType = null;
			int maxSpecificity = -1;
			for (Iterator<eu.medsea.mimeutil.MimeType> it = extensionTypes.iterator(); it.hasNext();) {
				eu.medsea.mimeutil.MimeType currentMime = it.next();
				if (currentMime.getSpecificity()>maxSpecificity) {
					maxSpecificity = currentMime.getSpecificity();
					extensionType = currentMime;
				}
			}
			try {
				return new MimeType(extensionType.getMediaType(), extensionType.getSubType());
			} catch (MimeTypeParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	protected boolean checkIfExtensionMimeIsReliable(MimeType extensionMime, Object obj) {
		if (extensionMime!=null) {
			Set<DetectorPrototype> detectorPrototypes = getDetectorPrototypeByMimeType(extensionMime);
			if (detectorPrototypes!=null) {
				// Ciascun detector deve restituire tra i mimetypes almeno 
				// quello rilevato guardando l'estensione
				for (DetectorPrototype detectorPrototype: detectorPrototypes) {
					Collection<MimeType> detectedMimeTypes;
					if (!detectorPrototype.getClazz().equals(extensionMimeDetector.getClass().getName())) {
						try {
							detectedMimeTypes = detectByRegisteredPrototypeImplementation(detectorPrototype, obj);
							if (detectedMimeTypes!=null) {
								for (MimeType detectedMimeType: detectedMimeTypes) {
									if (equalMimeTypes(detectedMimeType, extensionMime)) {
										log.debug("Il mimeType: " + extensionMime + " rilevato dall'estensione e affidabile");
										return true; 
									}
								}
							}
						} catch (MimeDetectorException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
		log.debug("Il mimeType: " + extensionMime + " rilevato dall'estensione non e affidabile");
		return false;
	}
	
	public MimeType detectBestRelyOnExtension(InputStream is) throws MimeDetectorException {
		MimeType extensionMime = detectBestUsingExtensionMimeDetector(is);
		return checkIfExtensionMimeIsReliable(extensionMime, is) ? extensionMime: detectBest(is);
	}
	
	public MimeType detectBestRelyOnExtension(File file) throws MimeDetectorException {
		MimeType extensionMime = detectBestUsingExtensionMimeDetector(file);
		return checkIfExtensionMimeIsReliable(extensionMime, file) ? extensionMime: detectBest(file);
	}
	
	public MimeType detectBestRelyOnExtension(String filePath) throws MimeDetectorException {
		MimeType extensionMime = detectBestUsingExtensionMimeDetector(filePath);
		return checkIfExtensionMimeIsReliable(extensionMime, filePath) ? extensionMime: detectBest(filePath);
	}
	
	public MimeType detectBestRelyOnExtension(byte[] data) throws MimeDetectorException {
		MimeType extensionMime = detectBestUsingExtensionMimeDetector(data);
		return checkIfExtensionMimeIsReliable(extensionMime, data) ? extensionMime: detectBest(data);
	}
	
	
}
