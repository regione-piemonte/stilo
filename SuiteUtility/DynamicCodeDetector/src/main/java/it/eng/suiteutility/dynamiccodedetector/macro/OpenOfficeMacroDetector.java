package it.eng.suiteutility.dynamiccodedetector.macro;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import it.eng.suiteutility.dynamiccodedetector.DynamicCodeDetector;
import it.eng.suiteutility.dynamiccodedetector.DynamicCodeDetectorException;
import it.eng.suiteutility.dynamiccodedetector.ValidationInfos;
import it.eng.suiteutility.dynamiccodedetector.utils.FormatUtils;
import it.eng.suiteutility.mimedetector.implementations.mimeutils.detectors.OfficeDetector;

public class OpenOfficeMacroDetector implements DynamicCodeDetector {

	public static final String CONTENT_FILE = "content.xml";
	public static final String SCRIPT_FILE = "Basic/script-lc.xml";
	public static final String SCRIPT_LIBRARIES_TAG = "library:library";
	public static final String SCRIPT_LIBRARY_NAME_ATTRIBUTE = "library:name";
	public static final String OBJECT_OLE = "draw:object-ole";
	public static final String OBJECT = "draw:object";
	public static final int BUFF_SIZE = 4096;

	private OfficeDetector officeDetector;

	public OpenOfficeMacroDetector() {
		this.officeDetector = new OfficeDetector();
	}

	private static Logger log = LogManager.getLogger(OpenOfficeMacroDetector.class);

	void containsMacro(String target, File openOfficeFile, ValidationInfos validationInfos) throws IOException, ParserConfigurationException, SAXException {
		if (openOfficeFile == null || !openOfficeFile.exists() || !openOfficeFile.isFile())
			throw new FileNotFoundException(openOfficeFile.getAbsolutePath());
		ZipFile zipFile = new ZipFile(openOfficeFile);
		ZipEntry contentEntry = zipFile.getEntry(CONTENT_FILE);

		InputStream is = zipFile.getInputStream(contentEntry);
		DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document document = documentBuilder.parse(is);
		checkContent(target, document, "", zipFile, validationInfos);
	}

	protected void checkContent(String target, Document document, String basePath, ZipFile zipFile, ValidationInfos validationInfos)
			throws IOException, ParserConfigurationException, SAXException {
		String absoluteScriptFolder = StringUtils.isNotEmpty(basePath) ? basePath + "/" + SCRIPT_FILE : SCRIPT_FILE;
		ZipEntry scriptEntry = zipFile.getEntry(absoluteScriptFolder);
		if (scriptEntry != null) {
			parseScriptEntry(target, scriptEntry, zipFile, validationInfos);
		}
		// Guardo se contiene dei riferimenti OLE (msoffice)
		String[] externalObjectOLERefs = null;
		if (document != null) {
			externalObjectOLERefs = parseDocumentForRefs(document, OBJECT_OLE);
			if (externalObjectOLERefs != null && externalObjectOLERefs.length != 0) {
				for (String externalObjectOLERef : externalObjectOLERefs) {
					String absoluteExternalObjectOLERef = StringUtils.isNotEmpty(basePath) ? basePath + "/" + stripRelativePath(externalObjectOLERef)
							: stripRelativePath(externalObjectOLERef);
					ZipEntry externalObjectEntry = zipFile.getEntry(absoluteExternalObjectOLERef);
					File temporaryFile = File.createTempFile("tmp", null);
					zipEntryToFile(zipFile, externalObjectEntry, temporaryFile);

					// Occorre quindi convertire il file in formato OpenOffice
					/*
					 * TODO:convertire il file in formato OpenOffice
					 */
					Collection<eu.medsea.mimeutil.MimeType> mimeTypes = (Collection<eu.medsea.mimeutil.MimeType>) officeDetector
							.getMimeTypesFile(temporaryFile);
					if (mimeTypes != null && mimeTypes.size() != 0) {
						MimeType mimeType;
						try {
							mimeType = new MimeType(mimeTypes.iterator().next().toString());
							String officeFileRealFormat = FormatUtils.officeMimeTypes2OfficeFormat.getProperty(mimeType.toString());
							if (officeFileRealFormat != null) {
								File newFilename = new File(FilenameUtils.removeExtension(temporaryFile.getAbsolutePath()) + "." + officeFileRealFormat);
								boolean resRen = temporaryFile.renameTo(newFilename);
								// System.out.println("TemporaryFile: " + newFilename + " resRen: " + resRen);
								File convertedFile = null;//OfficeMacroDetector.convertOfficeToOpenOffice(newFilename, mimeType);
								String newTarget = target + "->" + externalObjectOLERef;
								containsMacro(newTarget, convertedFile, validationInfos);
								convertedFile.delete();
							}
						} catch (MimeTypeParseException e) {
							log.error("Eccezione checkContent", e);
						}
					} else
						validationInfos.addWarning(
								target + " is linked to external reference to " + externalObjectOLERef + " which has an unknown type and could contain macros");
				}
			}
		}

		// Guardo se contiene dei riferimenti ad oggetti OpenOffice
		String[] internalObjectRefs = null;
		if (document != null) {
			InputStream is = null;
			internalObjectRefs = parseDocumentForRefs(document, OBJECT);
			try {
				if (internalObjectRefs != null && internalObjectRefs.length != 0) {
					for (String internalObjectRef : internalObjectRefs) {
						String newBaseFolder = StringUtils.isEmpty(basePath) ? stripRelativePath(internalObjectRef)
								: basePath + "/" + stripRelativePath(internalObjectRef);
						ZipEntry contentEntry = zipFile.getEntry(stripRelativePath(newBaseFolder + "/" + CONTENT_FILE));
						is = zipFile.getInputStream(contentEntry);
						DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
						Document newDocument = documentBuilder.parse(is);
						String newTarget = target + "->" + internalObjectRef;
						checkContent(newTarget, newDocument, newBaseFolder, zipFile, validationInfos);
					}
				}
			} finally {
				if (is != null)
					is.close();
			}
		}
	}

	private void parseScriptEntry(String target, ZipEntry scriptEntry, ZipFile zipFile, ValidationInfos validationInfos)
			throws ParserConfigurationException, SAXException {
		InputStream is;
		try {
			is = zipFile.getInputStream(scriptEntry);
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilderFactory.setNamespaceAware(false);
			documentBuilderFactory.setValidating(false);
			// documentBuilderFactory.setFeature("http://xml.org/sax/features/namespaces", false);
			// documentBuilderFactory.setFeature("http://xml.org/sax/features/validation", false);
			// documentBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
			// documentBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			documentBuilder.setEntityResolver(new EntityResolver() {

				public InputSource resolveEntity(java.lang.String publicId, java.lang.String systemId) throws SAXException, java.io.IOException {
					if (systemId.endsWith(".dtd"))
						// this deactivates all DTDs by giving empty XML docs
						return new InputSource(new ByteArrayInputStream("<?xml version='1.0' encoding='UTF-8'?>".getBytes()));
					else
						return null;
				}
			});

			Document document = documentBuilder.parse(is);
			Element rootElement = document.getDocumentElement();
			NodeList libraries = rootElement.getElementsByTagName(SCRIPT_LIBRARIES_TAG);
			if (libraries != null) {
				for (int i = 0; i < libraries.getLength(); ++i) {
					Element library = (Element) libraries.item(i);
					String libraryName = library.getAttribute(SCRIPT_LIBRARY_NAME_ATTRIBUTE);
					if (libraryName != null)
						validationInfos.addError(target + " contains macro library: " + libraryName);
				}
			} else {
				validationInfos.addError(target + " potentially contains macro library");
			}

		} catch (IOException e) {
			log.error("Eccezione parseScriptEntry", e);
		}

	}

	protected String stripRelativePath(String relativePath) {
		if (relativePath.startsWith("./")) {
			return relativePath.substring(2);
		}
		return relativePath;
	}

	protected void zipEntryToFile(ZipFile zipFile, ZipEntry zipEntry, File resultFile) throws IOException {
		InputStream is = zipFile.getInputStream(zipEntry);
		OutputStream out = new FileOutputStream(resultFile);
		byte buf[] = new byte[BUFF_SIZE];
		int len;
		while ((len = is.read(buf)) > 0)
			out.write(buf, 0, len);
		out.close();
		is.close();
	}

	protected String[] parseDocumentForRefs(Document document, String tagName) {
		ArrayList<String> externalObjects = new ArrayList<String>();
		Element element = document.getDocumentElement();
		NodeList objectOLENodes = element.getElementsByTagName(tagName);
		if (objectOLENodes != null && objectOLENodes.getLength() != 0) {
			for (int i = 0; i < objectOLENodes.getLength(); ++i) {
				Element objectOLEElement = (Element) objectOLENodes.item(i);
				String ref = objectOLEElement.getAttribute("xlink:href");
				externalObjects.add(ref);
			}
		}
		return externalObjects.toArray(new String[externalObjects.size()]);
	}

	public ValidationInfos detect(File file, MimeType mimeType) throws DynamicCodeDetectorException {
		ValidationInfos validationInfos = new ValidationInfos();
		try {
			containsMacro(file.getName(), file, validationInfos);
		} catch (Exception e) {
			throw new DynamicCodeDetectorException(e.getMessage(), e);
		}
		return validationInfos;
	}

}
