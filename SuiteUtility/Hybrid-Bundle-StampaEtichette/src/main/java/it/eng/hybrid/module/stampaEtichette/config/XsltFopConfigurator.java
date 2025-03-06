package it.eng.hybrid.module.stampaEtichette.config;

import java.io.InputStream;

import org.apache.log4j.Logger;

public class XsltFopConfigurator {
	
	public final static Logger logger = Logger.getLogger(XsltFopConfigurator.class);
	
	public static String XSL_CONFIG = "config.xsl";
	public static String XSLT_LABEL = "label.xslt";
	
	public static InputStream getConfigFile(){
		InputStream returnIS = XsltFopConfigurator.class.getClassLoader().getResourceAsStream(XSL_CONFIG);
		logger.debug("InputStream di getConfigFile: " + returnIS);
		return returnIS;
	}

	public static InputStream getXsltLabel(){
		InputStream returnIS = XsltFopConfigurator.class.getClassLoader().getResourceAsStream(XSLT_LABEL);
		logger.debug("InputStream di getXsltLabel: " + returnIS);
		return returnIS;
	}
}
