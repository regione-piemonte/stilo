package it.eng.suiteutility.mimedetector;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import javax.activation.MimeType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MimeDetectorConfig {

	public static final String DEFAULT_CONFIG_LOCATION = "/it/eng/sga/mimedetector/mimedetector.xml";
	
	public static final String MIME_SPECIFICITY_ATTRIBUTE = "specificity";
	public static final String MIME_REGISTRY_ATTRIBUTE = "registry";
	
	private Collection<DetectorPrototype> detectors = Collections
			.synchronizedCollection(new HashSet<DetectorPrototype>());

	public MimeDetectorConfig(Collection<DetectorPrototype> detectors)
			throws MimeDetectorException, IOException, SAXException {
		this.detectors = detectors;
	}

	public MimeDetectorConfig(String file) throws MimeDetectorException,
			IOException, SAXException {
		this(new File(file));
	}

	public MimeDetectorConfig(File file) throws MimeDetectorException,
			IOException, SAXException {
		this(getBuilder().parse(file));
	}

	public MimeDetectorConfig(InputStream stream) throws MimeDetectorException,
			IOException, SAXException {
		this(getBuilder().parse(stream));
	}

	public MimeDetectorConfig(Document document) throws MimeDetectorException,
			IOException {
		this(document.getDocumentElement());
	}

	public MimeDetectorConfig(URL url) throws MimeDetectorException,
			IOException, SAXException {
		this(getBuilder().parse(url.toString()));
	}

	public MimeDetectorConfig(Element element) throws MimeDetectorException,
			IOException {

		NodeList nodes = element.getElementsByTagName("detector");
		for (int i = 0; i < nodes.getLength(); ++i) {
			Element node = (Element) nodes.item(i);
			String name = node.getAttribute("name");
			String description = node.getAttribute("description");
			String clazz = node.getAttribute("class");
			String role = node.getAttribute("role");
			String detectorRegistry = node.getAttribute(MIME_REGISTRY_ATTRIBUTE);
			if (role==null || "".equals(role.trim()))
				role="1";
			DetectorPrototype detector = new DetectorPrototype(name,
					description, clazz, Integer.parseInt(role));
			try {
				NodeList mimes = node.getElementsByTagName("mime");
				for (int j = 0; j < mimes.getLength(); ++j) {
					Node item = mimes.item(j);
					String value = StringUtils.trim(getText(item));
					int separatorIdx = value.indexOf('/');
					MimeType mime = new MimeType();
					mime.setPrimaryType(value.substring(0, separatorIdx));
					mime.setSubType(value.substring(separatorIdx + 1));
					String specificity = ((Element)item).getAttribute(MIME_SPECIFICITY_ATTRIBUTE);
					if (specificity==null || "".equals(specificity.trim()))
						specificity="1";
					mime.setParameter(MIME_SPECIFICITY_ATTRIBUTE, specificity);
					String mimeRegistry = ((Element)item).getAttribute(MIME_REGISTRY_ATTRIBUTE);
					if (mimeRegistry!=null && !"".equals(mimeRegistry))
						mime.setParameter(MIME_REGISTRY_ATTRIBUTE, mimeRegistry);
					else if (detectorRegistry!=null && !"".equals(detectorRegistry))
						mime.setParameter(MIME_REGISTRY_ATTRIBUTE, detectorRegistry);
					detector.addMime(mime);
				}
				detectors.add(detector);
			} catch (Throwable t) {
				t.printStackTrace();
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

	private static DocumentBuilder getBuilder() throws MimeDetectorException {
		try {
			return DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new MimeDetectorException("XML parser not available", e);
		}
	}

	public Collection<DetectorPrototype> getDetectors() {
		return detectors;
	}

	public void setDetectors(Collection<DetectorPrototype> detectors) {
		this.detectors = detectors;
	}

	public static MimeDetectorConfig getDefaultConfig()
			throws MimeDetectorException {
		try {
			InputStream stream = MimeDetectorConfig.class.getResourceAsStream(DEFAULT_CONFIG_LOCATION);
			return new MimeDetectorConfig(stream);
		} catch (IOException e) {
			throw new MimeDetectorException(
					"Unable to read default configuration", e);
		} catch (SAXException e) {
			throw new MimeDetectorException(
					"Unable to parse default configuration", e);
		}
	}
}
