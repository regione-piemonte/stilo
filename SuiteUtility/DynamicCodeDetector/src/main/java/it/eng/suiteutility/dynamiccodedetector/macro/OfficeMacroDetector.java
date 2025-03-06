package it.eng.suiteutility.dynamiccodedetector.macro;


import it.eng.suiteutility.dynamiccodedetector.DynamicCodeDetector;
import it.eng.suiteutility.dynamiccodedetector.DynamicCodeDetectorException;
import it.eng.suiteutility.dynamiccodedetector.ValidationInfos;
import it.eng.suiteutility.dynamiccodedetector.utils.FormatUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.activation.MimeType;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;


public class OfficeMacroDetector implements DynamicCodeDetector{ 

	private OpenOfficeMacroDetector openOfficeMacroDetector;

	//private static DocumentFormatRegistry registry = new DefaultDocumentFormatRegistry();
	
	public OfficeMacroDetector(){
		this.openOfficeMacroDetector = new OpenOfficeMacroDetector();
	}
	
	/*static File convertOfficeToOpenOffice(File officeFile, MimeType mimeType) throws IOException{
		String openOfficeMimeType = FormatUtils.officeMimeTypes2OOMimeTypes.getProperty(mimeType.toString());
		String openOfficeFormat = FormatUtils.ooMimeTypes2OoFormat.getProperty(openOfficeMimeType);
		OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
		connection.connect();
		// convert
		DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
		File outputFile = File.createTempFile("tmp", "." + openOfficeFormat);
		String officeMimeType = mimeType.getPrimaryType() + "/" + mimeType.getSubType();
		DocumentFormat documentFormatSource = registry.getFormatByFileExtension(FormatUtils.officeMimeTypes2OfficeFormat.getProperty(officeMimeType));
		DocumentFormat documentFormatDest = registry.getFormatByFileExtension(openOfficeFormat);
		converter.convert(officeFile, documentFormatSource, outputFile, documentFormatDest);
		connection.disconnect();
		return outputFile;
	}*/
	
	/*void containsMacro(File officeFile, MimeType mimeType, ValidationInfos validationInfos)throws DynamicCodeDetectorException, IOException, ParserConfigurationException, SAXException{
		if (officeFile==null || !officeFile.exists() || !officeFile.isFile())
			throw new FileNotFoundException(officeFile.getAbsolutePath());
		String openOfficeMimeType = FormatUtils.officeMimeTypes2OOMimeTypes.getProperty(mimeType.toString());
		if (openOfficeMimeType!=null){
			File covertedFile = convertOfficeToOpenOffice(officeFile, mimeType);
			openOfficeMacroDetector.containsMacro(officeFile.getName(), covertedFile, validationInfos);
		}else{
			validationInfos.addWarning(officeFile + " has not correspondent OpenOffice format, thus it cannot be analyzed");
		}
	}*/

	public ValidationInfos detect(File file, MimeType mimeType)
			throws DynamicCodeDetectorException {
		ValidationInfos validationInfos = new ValidationInfos();
		/*try {
			containsMacro(file, mimeType, validationInfos);
		} catch (Exception e) {
			throw new DynamicCodeDetectorException(e.getMessage(), e);
		}*/
		return validationInfos;
	}
	
}
