/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Properties;

public class AurigaWebServiceConfigurer {
	
	private Boolean isDebugMode = false;
	private String tempPath = null;
	private String xsdUri = null;
	private Properties xsdNames = null;
	private Properties xsdRootTags = null;
	
	public AurigaWebServiceConfigurer() {}
	
	public Boolean getIsDebugMode() {
		return isDebugMode;
	}
	public void setIsDebugMode(Boolean isDebugMode) {
		this.isDebugMode = isDebugMode;
	}
	public String getTempPath() {
		return tempPath;
	}
	public void setTempPath(String tempPath) {
		this.tempPath = tempPath;
	}
	public String getXsdUri() {
		return xsdUri;
	}
	public void setXsdUri(String xsdUri) {
		this.xsdUri = xsdUri;
	}
	public Properties getXsdNames() {
		return xsdNames;
	}
	public void setXsdNames(Properties xsdNames) {
		this.xsdNames = xsdNames;
	}
	public Properties getXsdRootTags() {
		return xsdRootTags;
	}
	public void setXsdRootTags(Properties xsdRootTags) {
		this.xsdRootTags = xsdRootTags;
	}

}
