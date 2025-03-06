package it.eng.utility.oomanager.xslt;

import java.io.File;
import java.util.List;

import net.sf.saxon.TransformerFactoryImpl;

import org.apache.commons.io.FileUtils;
import org.odftoolkit.odfdom.pkg.OdfPackage;
import org.odftoolkit.odfxsltrunner.ODFXSLTRunner;
import org.odftoolkit.odfxsltrunner.XSLTParameter;

public class OdtToHtml {

	public static synchronized void convertOdtToHtml(File odt,File html,String filterBasePath)throws Exception{
		
		String aTransformerFactoryClass = TransformerFactoryImpl.class.getName();
        int aInputMode = ODFXSLTRunner.INPUT_MODE_PACKAGE;
        int aOutputMode = ODFXSLTRunner.OUTPUT_MODE_FILE;
        List<XSLTParameter> aParams = null;
        List<String> aExtractFileNames = null;
        ODFXSLTRunner runner = new ODFXSLTRunner();
		      
        File dirxsl = new File(filterBasePath);
        OdfPackage packageodf =  OdfPackage.loadPackage(odt);
               
        File style = new File(dirxsl+"/export/common/document_style/","styles.xml");
        File meta = new File(dirxsl+"/export/common/document_style/","meta.xml");
       
        FileUtils.writeByteArrayToFile(style, packageodf.getBytes("styles.xml"));
        FileUtils.writeByteArrayToFile(meta, packageodf.getBytes("meta.xml"));
              
        packageodf.close();
        
		runner.runXSLT(new File(dirxsl+"/export/xhtml/","opendoc2xhtml.xsl"), aParams, odt, aInputMode, html, aOutputMode, "content.xml", aTransformerFactoryClass, aExtractFileNames, new XsltLogger() );
				
		style.delete();
		meta.delete();
	
	}
	
	
}
