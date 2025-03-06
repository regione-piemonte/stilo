package it.eng.suiteutility.dynamiccodedetector.macro;

import it.eng.suiteutility.dynamiccodedetector.DynamicCodeDetector;
import it.eng.suiteutility.dynamiccodedetector.DynamicCodeDetectorException;
import it.eng.suiteutility.dynamiccodedetector.ValidationInfos;
import it.eng.suiteutility.dynamiccodedetector.utils.FormatUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import javax.activation.MimeType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Office2007MacroDetector implements DynamicCodeDetector{

	public static final String 	 CONTENT_FILE			= "[Content_Types].xml";
	public static final String	 CONTENT_ATTRIBUTE		= "ContentType";
	public static final String[] PREVENTED_CONTENT_TYPES= {"application/vnd.ms-office.vbaProject", "application/vnd.ms-office.activeX", "application/vnd.ms-office.activeX+xml"};
	
	private static final Collection<String> preventedContentTypesSet= new HashSet<String>(Arrays.asList(PREVENTED_CONTENT_TYPES));
	
	OfficeMacroDetector officeMacroDetector;
	
	public Office2007MacroDetector(){
		officeMacroDetector= new OfficeMacroDetector();
	}
	

	void containsMacro(String target, File openOfficeFile, ValidationInfos validationInfos) throws ZipException, IOException, ParserConfigurationException, SAXException{
		if (openOfficeFile==null || !openOfficeFile.exists() || !openOfficeFile.isFile())
			throw new FileNotFoundException(openOfficeFile.getAbsolutePath());;
		ZipFile zipFile = new ZipFile(openOfficeFile);
		ZipEntry contentEntry = zipFile.getEntry(CONTENT_FILE);
		
		InputStream is = zipFile.getInputStream(contentEntry);
		DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document document = documentBuilder.parse(is);
		checkContent(target, document, zipFile, validationInfos);
	}
	
	protected void checkContent(String target, Document document, ZipFile zipFile, ValidationInfos validationInfos) throws IOException, ParserConfigurationException, SAXException{
		Element root = document.getDocumentElement();
		parseBranch(target, root, validationInfos);
	}
	
	protected void parseBranch(String target, Element branchElement, ValidationInfos validationInfos){
		String contentType = branchElement.getAttribute(CONTENT_ATTRIBUTE);
		if (preventedContentTypesSet.contains(contentType)){
			validationInfos.addError(target + " contains untrusted " + contentType + "content");
		}
		NodeList children = branchElement.getChildNodes();
		if (children!=null && children.getLength()!=0){
			for (int i=0; i<children.getLength(); ++i){
				Element child = (Element)children.item(i);
				parseBranch(target, child, validationInfos);
			}
		}
	}
	
	
	public ValidationInfos detect(File file, MimeType mimeType)
			throws DynamicCodeDetectorException {
		ValidationInfos validationInfos = new ValidationInfos();
		try {
			containsMacro(file.getName(), file, validationInfos);
			if (validationInfos.isValid()){
				String officeFormat = FormatUtils.officeMimeTypes2OfficeFormat.getProperty(mimeType.toString());
				
				// Guardo se si tratta di un formato convertibile con openoffice
				if (officeFormat!=null){
					File convertedFile = new File(FilenameUtils.removeExtension(file.getAbsolutePath()) + "." + officeFormat);
					FileUtils.copyFile(file, convertedFile);
					//officeMacroDetector.containsMacro(convertedFile, mimeType, validationInfos);
				}
				
			}
		} catch (Exception e) {
			throw new DynamicCodeDetectorException(e.getMessage(), e);
		}
		return validationInfos;
	}

}
