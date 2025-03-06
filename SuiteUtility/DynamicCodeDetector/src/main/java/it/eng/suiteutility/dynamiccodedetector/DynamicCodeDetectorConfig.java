package it.eng.suiteutility.dynamiccodedetector;

import it.eng.suiteutility.dynamiccodedetector.pdf.PDFExecutableDetector;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DynamicCodeDetectorConfig {

	public static final  Logger log=Logger.getLogger( DynamicCodeDetectorConfig.class );
	
	public static final String DEFAULT_CONFIG_LOCATION = DynamicCodeDetectorConfig.class.getResource("/it/eng/suiteutility/dynamiccodedetector/config/config.xml").getFile();
	
	public static final String DYNAMIC_CODE_DETECTOR_TAG	= "dynamicCodeDetector";
	public static final String ACCEPTED_MIME_TYPE_TAG 		= "mime-type";
	
	public static final String DETECTOR_NAME_ATTRIBUTE 		= "name";
	public static final String DETECTOR_CLASS_ATTRIBUTE 	= "class";
	
	private Map<String, Collection<DynamicCodeDetector>> mimeTypesDetectorAssociation = Collections.synchronizedMap(new HashMap<String, Collection<DynamicCodeDetector>>()); 

	public DynamicCodeDetectorConfig(Map<String, Collection<DynamicCodeDetector>> mimeTypesDetectorAssociation){
		this.mimeTypesDetectorAssociation = mimeTypesDetectorAssociation;
	}
	
	public DynamicCodeDetectorConfig() throws DynamicCodeDetectorException, SAXException, IOException{
		this(DEFAULT_CONFIG_LOCATION);
	}

	public DynamicCodeDetectorConfig(String file) throws  DynamicCodeDetectorException, SAXException, IOException{
		this(new File(file));
	}

	public DynamicCodeDetectorConfig(File file) throws  DynamicCodeDetectorException, SAXException, IOException {
		this(getBuilder().parse(file));
	}

	public DynamicCodeDetectorConfig(InputStream stream) throws DynamicCodeDetectorException, SAXException, IOException {
		this(getBuilder().parse(stream));
	}

	public DynamicCodeDetectorConfig(Document document) throws DynamicCodeDetectorException {
		this(document.getDocumentElement());
	}

	public DynamicCodeDetectorConfig(URL url) throws  DynamicCodeDetectorException, SAXException, IOException {
		this(getBuilder().parse(url.toString()));
	}

	public DynamicCodeDetectorConfig(Element element) throws DynamicCodeDetectorException {

		NodeList nodes = element.getElementsByTagName(DYNAMIC_CODE_DETECTOR_TAG);
		//log.info("nodes " + nodes );
		for (int i = 0; i < nodes.getLength(); ++i) {
			Element node = (Element) nodes.item(i);
			String name = node.getAttribute(DETECTOR_NAME_ATTRIBUTE);
			String clazz = node.getAttribute(DETECTOR_CLASS_ATTRIBUTE);
			//log.info("name " + name + " clazz "+ clazz);
			try {
				Class<DynamicCodeDetector> detectorClazz = (Class<DynamicCodeDetector>) Class.forName(clazz);
				DynamicCodeDetector dynamicDetector = detectorClazz.newInstance();
				NodeList mimes = node.getElementsByTagName(ACCEPTED_MIME_TYPE_TAG);
				for (int j = 0; j < mimes.getLength(); ++j) {
					Node item = mimes.item(j);
					String mime = StringUtils.trim(getText(item));
					log.info("mime " + mime);
					Collection<DynamicCodeDetector> mimeTypeDetectorsCollection = mimeTypesDetectorAssociation.get(mime);
					if (mimeTypeDetectorsCollection == null){
						mimeTypeDetectorsCollection = new HashSet<DynamicCodeDetector>();
						mimeTypesDetectorAssociation.put(mime, mimeTypeDetectorsCollection);
					}
					mimeTypeDetectorsCollection.add(dynamicDetector);
				}
			} catch (Exception e) {
				log.error("", e);
				throw new DynamicCodeDetectorException(e.getMessage(), e);
			}
		}
	}

	private String getText(Node node) {
		if (node.getNodeType() == Node.TEXT_NODE)
			return node.getNodeValue();
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			StringBuilder builder = new StringBuilder();
			NodeList list = node.getChildNodes();
			for (int i = 0; i < list.getLength(); ++i) {
				builder.append(getText(list.item(i)).trim());
			}
			return builder.toString();
		}
		return "";
	}

	private static DocumentBuilder getBuilder() throws DynamicCodeDetectorException{
		try {
			return DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new DynamicCodeDetectorException("XML parser not available", e);
		}
	}

	public static DynamicCodeDetectorConfig getDefaultConfig()
			throws DynamicCodeDetectorException {
		try {
			InputStream stream = DynamicCodeDetectorConfig.class
					.getResourceAsStream(DEFAULT_CONFIG_LOCATION);
			return new DynamicCodeDetectorConfig(stream);
		} catch (Exception e) {
			throw new DynamicCodeDetectorException(
					"Unable to read default configuration", e);
		}
	}

	public Map<String, Collection<DynamicCodeDetector>> getMimeTypesDetectorAssociation() {
		return mimeTypesDetectorAssociation;
	}

	public void setMimeTypesDetectorAssociation(
			Map<String, Collection<DynamicCodeDetector>> mimeTypesDetectorAssociation) {
		this.mimeTypesDetectorAssociation = mimeTypesDetectorAssociation;
	}
}
