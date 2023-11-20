/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;

import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;

import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.ui.servlet.fileExtractor.FileToExtractBean;


// Legge il file xls di IN
// File fileXslIn = new File(URLDecoder.decode(getClass().getClassLoader().getResource("fatturapa_v1.0.xsl").getFile(), "UTF-8"));

// Legge dallo storage il file xml di IN
//File fileXml = StorageImplementation.getStorage().extractFile(bean.getUriFilePrimario());

// Creo lo stream completo del file html di OUT
// pathFileHtmlOut = fileXml.getPath().substring(0, fileXml.getPath().length()-4)+".html";
//File htmlFileOut = new File(pathFileHtmlOut);


// INPUT
// fileXslIn      : E' il file xls modello
// fileXmlIn      : E' il file xml da convertire
// pathFileHtmlIn : E' il path del file html convertito

// OUTPUT
// htmlFileOut : E' lo stream del file html convertito

public class XmlToHTMLfile {	
	
	    private static Logger mLogger = Logger.getLogger(XmlToHTMLfile.class);
	
		//public static File convert(File fileXslIn, File fileXmlIn, String pathFileHtmlIn){		
	    public static FileToExtractBean convert(File fileXslIn, FileToExtractBean fileToExtractXmlIn )  throws Exception{
	    OutputStream osHtml = null;	
	    InputStream is = null;
		try {			
			TransformerFactory transformer = TransformerFactoryImpl.newInstance();
   	        Templates xslTemplate = transformer.newTemplates(new StreamSource(fileXslIn));
			Transformer transform = xslTemplate.newTransformer();
			transform.setOutputProperty(OutputKeys.METHOD, "xml"); 
			transform.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transform.setOutputProperty(OutputKeys.INDENT, "yes"); 
			
			// Estraggo dallo storage il file
			File fileXmlIn = StorageImplementation.getStorage().extractFile(fileToExtractXmlIn.getUri());
			
			// Apro lo stream di in
			Source xmlFileIn = new StreamSource(fileXmlIn);
			
			// Apro lo stream di out nella cartella temp
			File htmlFileOut = File.createTempFile("temp_file_html_", ".html");
			
			osHtml = new FileOutputStream(htmlFileOut);
			StreamResult htmlFileStreamResultOut = new StreamResult(osHtml);
			
			// Crea il file di out			
			transform.transform(xmlFileIn, htmlFileStreamResultOut);
			
			mLogger.debug("Creato il file " + htmlFileOut);
			
			// Lo copio nello storage
			FileToExtractBean lFileToExtractBean = new FileToExtractBean();
			String displayFilename  = fileToExtractXmlIn.getDisplayFilename();
			lFileToExtractBean.setDisplayFileName(displayFilename);
			is = new FileInputStream(htmlFileOut);
			lFileToExtractBean.setUri(StorageImplementation.getStorage().storeStream(is));
			//FileUtils.forceDelete(htmlFileOut);
			lFileToExtractBean.setRemoteUri(false);
			lFileToExtractBean.setSbustato(false);			
			return lFileToExtractBean;	
			
		} catch (Exception e) {
			mLogger.warn(e);
		} finally {
			if(is != null) {
				try {
					is.close();
				} catch (Exception e) {}
			}
			if(osHtml != null) {
				try {
					osHtml.close();
				} catch (Exception e) {}
			}
		}
		return null;
	}
}