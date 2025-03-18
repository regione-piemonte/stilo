/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.InputStream;

public class XsltFopConfigurator {
	
	public static String XSL_CONFIG = "config.xsl";
	public static String XSLT_LABEL = "label.xslt";
	
	public static InputStream getConfigFile(){
		return XsltFopConfigurator.class.getClassLoader().getResourceAsStream(XSL_CONFIG);
	}

	public static InputStream getXsltLabel(){
		return XsltFopConfigurator.class.getClassLoader().getResourceAsStream(XSLT_LABEL);
	}
}
