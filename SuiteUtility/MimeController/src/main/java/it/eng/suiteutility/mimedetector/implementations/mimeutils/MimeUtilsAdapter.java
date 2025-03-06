package it.eng.suiteutility.mimedetector.implementations.mimeutils;

import it.eng.core.config.ConfigUtil;
import it.eng.suiteutility.mimedetector.AbstractMimeDetector;
import it.eng.suiteutility.mimedetector.DetectorPrototype;
import it.eng.suiteutility.mimedetector.MimeDetectorConfig;
import it.eng.suiteutility.mimedetector.MimeDetectorException;
import it.eng.suiteutility.mimedetector.MimeDetectorUtils;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import eu.medsea.mimeutil.detector.ExtensionMimeDetector;
import eu.medsea.mimeutil.detector.MagicMimeMimeDetector;

public class MimeUtilsAdapter extends AbstractMimeDetector {
	
	public static final Logger log = LogManager.getLogger(MimeUtilsAdapter.class);
	
	protected static MimeDetectorConfig defaultConfig = null;
	
	public static final String DEFAULT_CONFIG_LOCATION = "/it/eng/suiteutility/mimedetector/implementations/mimeutils/config/mimeutils.xml";
	
//	private Collection<>
	
	/**
	 * Riceve in input il sorgente da analizzare e restituisce in output una mappa
	 * con i possibili mimetype e la relativa specificita
	 * @param source
	 * @return map
	 * @throws MimeDetectorException
	 */
	@SuppressWarnings("unchecked")
	private Map<MimeType, Integer> innerDetect(Object source) throws MimeDetectorException {
		Collection<DetectorPrototype> detectors = registeredDetectors.keySet();
		Map<MimeType, Integer> resultTypes = new HashMap<MimeType, Integer>();
		try {
			for (DetectorPrototype detectorProto : detectors) {
				//if( detectorProto!=null )
				//System.out.println("---detectorProto "+ detectorProto.getName());
				Object detectorImpl = registeredDetectors.get(detectorProto);
				//System.out.println("---- " + detectorImpl );
				if (detectorImpl instanceof eu.medsea.mimeutil.detector.MimeDetector) {
					eu.medsea.mimeutil.detector.MimeDetector mimeDetector = (eu.medsea.mimeutil.detector.MimeDetector) detectorImpl;
					Collection<eu.medsea.mimeutil.MimeType> returnedTypes =null;
					if (source instanceof InputStream)
						returnedTypes = mimeDetector.getMimeTypes((InputStream)source);
					else if (source instanceof String)
						returnedTypes = mimeDetector.getMimeTypes((String)source);
					else if (source instanceof URL)
						returnedTypes = mimeDetector.getMimeTypes((URL)source);
					else if (source instanceof byte[])
						returnedTypes = mimeDetector.getMimeTypes((byte[])source);
					else if (source instanceof File){
						returnedTypes = mimeDetector.getMimeTypes((File)source);
						
					}
					//log.debug("---returnedTypes "+ returnedTypes);
					
					if (returnedTypes !=null) {
						for (eu.medsea.mimeutil.MimeType mimeType : returnedTypes) {
							MimeType resultType = new MimeType(mimeType.getMediaType(), mimeType.getSubType());
							//log.debug("resultType " + resultType);
							//if( resultTypes.get(resultType)!=null)
							//	log.debug("---resultTypes.get(resultType).intValue() "+ resultTypes.get(resultType).intValue());
							//log.debug("---detectorProto.getRole() "+ detectorProto.getRole());
							if (!resultTypes.containsKey(resultType)
									|| resultTypes.get(resultType).intValue() < detectorProto.getRole()) {
								/*
								 * TODO: DA MODIFICARE!!!!
								 */
								for (MimeType mime: detectorProto.getMimes()) {
									//Se i due tipi matchano bisogna settare il campo specificity
									if (mime.match(resultType)) {
										log.debug("[MimeUtils] Detector: " + detectorProto.getName() + " [role: "+detectorProto.getRole()+"] detected type: " + mime);
										resultType.setParameter(MimeDetectorConfig.MIME_SPECIFICITY_ATTRIBUTE, 
												mime.getParameter(MimeDetectorConfig.MIME_SPECIFICITY_ATTRIBUTE));
									}
								}
								resultTypes.put(resultType,	new Integer(detectorProto.getRole()));
							}
						}
					}
				}
			}
			
//			boolean esitoCancellazione = ((File)source).delete();
//			System.out.println("-> " + esitoCancellazione);
			//log.debug("resultTypes " + resultTypes);
			return resultTypes;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new MimeDetectorException(e.getLocalizedMessage(),e);
		}
	}
	
	public boolean checkIfExtensionMimeIsReliable(MimeType extensionMime, File file) {
		//log.debug("Metodo checkIfExtensionMimeIsReliable per il file " + file );
		//log.debug("extensionMime " + extensionMime);
		//System.out.println("extensionMimeDetector.getClass().getName() " + extensionMimeDetector.getClass().getName());
		if (extensionMime!=null) {
			Set<DetectorPrototype> detectorPrototypes = getDetectorPrototypeByMimeType(extensionMime);
			//log.debug("checkIfExtensionMimeIsReliable : detectorPrototypes " + detectorPrototypes);
			if (detectorPrototypes!=null) {
				// Ciascun detector deve restituire tra i mimetypes almeno 
				// quello rilevato guardando l'estensione
				//System.out.println("Ho " + detectorPrototypes.size() + " detectors da verificare ");
				//for (DetectorPrototype detectorPrototype: detectorPrototypes) {
				//	System.out.println("detectorPrototype:: " + detectorPrototype.getName());
				//}
				for (DetectorPrototype detectorPrototype: detectorPrototypes) {
					//log.debug("detectorPrototype " + detectorPrototype.getName());
					Collection<MimeType> detectedMimeTypes;
					//log.debug("Verifico " + detectorPrototype.getClazz() + " " + extensionMimeDetector.getClass().getName());
					if (!detectorPrototype.getClazz().equals(extensionMimeDetector.getClass().getName())) {
						//log.debug("----if----");
						try {
							detectedMimeTypes = detectByRegisteredPrototypeImplementation(detectorPrototype, file);
							//log.debug("detectedMimeTypes " + detectedMimeTypes);
							if (detectedMimeTypes!=null) {
								for (MimeType detectedMimeType: detectedMimeTypes) {
									//log.debug("--detectedMimeType " + detectedMimeType);
									//log.debug("--extensionMime " + extensionMime);
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
	
	private boolean equalMimeTypes(MimeType mime1, MimeType mime2) {
		if (mime1==null && mime2==null)
			return true;
		else if ( (mime1==null && mime2!=null) || (mime1!=null && mime2==null) )
			return false;
		else 
			return mime1.getBaseType().equals(mime2.getBaseType()) && mime1.getSubType().equals(mime2.getSubType());
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
		MimeType mime = MimeDetectorUtils.getBestMimeType(innerDetect(file));
		if (mime==null) {
			try {
				mime = new MimeType("application","unknown");
			} catch (MimeTypeParseException e) {
				e.printStackTrace();
			}
		}
		return mime;
	}
	
	public static void main(String[] args) {
		
		MimeUtilsAdapter adapter = new MimeUtilsAdapter();
		//File file = new File("C:/Users/Anna Tesauro/Desktop/testPDF.pdf");
		
		//File file = new File("D:/apache-tomcat-7.0.62_FILEOP/temp/0a40b0d2214c4a3397f7d704f738c217/input6871464071721295644stemp.pdf");
		File file = new File("C:/Users/Public/Music/Sample Music/Kalimba.mp3");
	 //file = new File("C:/Users/Anna Tesauro/Desktop/allegato_3.pdf");
//		try {
//			MimeType mime = adapter.detectBestWithExtension(file, null);
//			System.out.println(mime);
//		} catch (MimeDetectorException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		try {
			MimeType mime = adapter.detectBest(file);
			System.out.println(mime);
		} catch (MimeDetectorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		boolean esitoCancellazione = file.delete();
//		System.out.println("esito cancellazione " + esitoCancellazione);
	}
	
	public MimeType detectBestWithExtension(File file, String estensione) throws MimeDetectorException {
		ExtensionMimeDetector extensionMimeDetector = new ExtensionMimeDetector();
		Collection<eu.medsea.mimeutil.MimeType> extensionTypes = null;
		if( estensione!=null ){
			file = new File(file.getAbsolutePath() + "." + estensione);
		}
		extensionTypes = extensionMimeDetector.getMimeTypes(file);
		log.debug("Mime-types rilevati dall'estensione: " +  extensionTypes);
		
		if (extensionTypes!=null && extensionTypes.size()!=0) {
			eu.medsea.mimeutil.MimeType extensionType = null;
			int maxSpecificity = -1;
			for (Iterator<eu.medsea.mimeutil.MimeType> it = extensionTypes.iterator(); it.hasNext();) {
				eu.medsea.mimeutil.MimeType currentMime = it.next();
				log.debug("currentMime " + currentMime + " specificity " + currentMime.getSpecificity() );
				if (currentMime.getSpecificity()>maxSpecificity) {
					maxSpecificity = currentMime.getSpecificity();
					extensionType = currentMime;
				}
			}
			try {
				return new MimeType(extensionType.getMediaType(), extensionType.getSubType());
			} catch (MimeTypeParseException e) {
				log.error("Errore", e);
			}
		}
		MimeType mime = null;
		try {
			mime = new MimeType("application","unknown");
		} catch (MimeTypeParseException e) {
			e.printStackTrace();
		}
		return mime;
	}
	
	public MimeType detectBest(String filePath) throws MimeDetectorException {
		MimeType mime = MimeDetectorUtils.getBestMimeType(innerDetect(filePath));
		if (mime==null) {
			try {
				mime = new MimeType("application","unknown");
			} catch (MimeTypeParseException e) {
				e.printStackTrace();
			}
		}
		return mime;
	}
	
	public MimeType detectBest(byte[] data) throws MimeDetectorException {
		MimeType mime = MimeDetectorUtils.getBestMimeType(innerDetect(data));
		if (mime==null) {
			try {
				mime = new MimeType("application","unknown");
			} catch (MimeTypeParseException e) {
				e.printStackTrace();
			}
		}
		return mime;
	}

	@Override
	public MimeType detectBestImplementation(InputStream stream)
			throws MimeDetectorException {
		return MimeDetectorUtils.getBestMimeType(innerDetect(stream));
	}

	@Override
	@SuppressWarnings("unchecked")
	protected Collection<MimeType> detectByRegisteredPrototypeImplementation(
			DetectorPrototype detectorPrototype, Object obj)
			throws MimeDetectorException {
		
		//log.debug("\n\n------> detectorPrototype " + detectorPrototype.getClazz());
		Object detectorImpl = registeredDetectors.get(detectorPrototype);
		//log.debug("------> detectorImpl "  + detectorImpl);
		eu.medsea.mimeutil.detector.MimeDetector mimeDetector = (eu.medsea.mimeutil.detector.MimeDetector) detectorImpl;
		Collection<eu.medsea.mimeutil.MimeType> returnedTypes = null;
		if (obj instanceof InputStream)
			returnedTypes = mimeDetector.getMimeTypes((InputStream)obj);
		else if (obj instanceof byte[])
			returnedTypes = mimeDetector.getMimeTypes((byte[])obj);
		else if (obj instanceof File)
			returnedTypes = mimeDetector.getMimeTypes((File)obj);
		else if (obj instanceof String)
			returnedTypes = mimeDetector.getMimeTypes((String)obj);
		if (returnedTypes==null)
			return null;
		Collection<MimeType> returnedMimes = new ArrayList<MimeType>();
		for (eu.medsea.mimeutil.MimeType returnedType: returnedTypes) {
			//log.debug("returnedType " + returnedType);
			try {
				MimeType returnedMime = new MimeType(returnedType.getMediaType(), returnedType.getSubType());
				returnedMimes.add(returnedMime);
			} catch (MimeTypeParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return returnedMimes;
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
