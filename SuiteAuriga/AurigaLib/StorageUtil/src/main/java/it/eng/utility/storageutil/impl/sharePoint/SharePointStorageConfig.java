/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SharePointStorageConfig implements Serializable {

	private String username; 
	private String password; 
	private String rootPath;
	private String libraryName;
	private String wsdlLocationCopy;
	private String serviceNameCopy;
	private String serviceNamespaceCopy;
	private String wsdlLocationList;
	private String serviceNameList;
	private String serviceNamespaceList;
	private String wsdlLocationDws;
	private String serviceNameDws;
	private String serviceNamespaceDws;
	private String wsdlLocationVersion;
	private String serviceNameVersion;
	private String serviceNamespaceVersion;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRootPath() {
		return rootPath;
	}
	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}
	public String getWsdlLocationCopy() {
		return wsdlLocationCopy;
	}
	public void setWsdlLocationCopy(String wsdlLocationCopy) {
		this.wsdlLocationCopy = wsdlLocationCopy;
	}
	public String getServiceNameCopy() {
		return serviceNameCopy;
	}
	public void setServiceNameCopy(String serviceNameCopy) {
		this.serviceNameCopy = serviceNameCopy;
	}
	public String getServiceNamespaceCopy() {
		return serviceNamespaceCopy;
	}
	public void setServiceNamespaceCopy(String serviceNamespaceCopy) {
		this.serviceNamespaceCopy = serviceNamespaceCopy;
	}
	public String getLibraryName() {
		return libraryName;
	}
	public void setLibraryName(String libraryName) {
		this.libraryName = libraryName;
	}
	public String getWsdlLocationList() {
		return wsdlLocationList;
	}
	public void setWsdlLocationList(String wsdlLocationList) {
		this.wsdlLocationList = wsdlLocationList;
	}
	public String getServiceNameList() {
		return serviceNameList;
	}
	public void setServiceNameList(String serviceNameList) {
		this.serviceNameList = serviceNameList;
	}
	public String getServiceNamespaceList() {
		return serviceNamespaceList;
	}
	public void setServiceNamespaceList(String serviceNamespaceList) {
		this.serviceNamespaceList = serviceNamespaceList;
	}
	public String getWsdlLocationDws() {
		return wsdlLocationDws;
	}
	public void setWsdlLocationDws(String wsdlLocationDws) {
		this.wsdlLocationDws = wsdlLocationDws;
	}
	public String getServiceNameDws() {
		return serviceNameDws;
	}
	public void setServiceNameDws(String serviceNameDws) {
		this.serviceNameDws = serviceNameDws;
	}
	public String getServiceNamespaceDws() {
		return serviceNamespaceDws;
	}
	public void setServiceNamespaceDws(String serviceNamespaceDws) {
		this.serviceNamespaceDws = serviceNamespaceDws;
	}
	public String getWsdlLocationVersion() {
		return wsdlLocationVersion;
	}
	public void setWsdlLocationVersion(String wsdlLocationVersion) {
		this.wsdlLocationVersion = wsdlLocationVersion;
	}
	public String getServiceNameVersion() {
		return serviceNameVersion;
	}
	public void setServiceNameVersion(String serviceNameVersion) {
		this.serviceNameVersion = serviceNameVersion;
	}
	public String getServiceNamespaceVersion() {
		return serviceNamespaceVersion;
	}
	public void setServiceNamespaceVersion(String serviceNamespaceVersion) {
		this.serviceNamespaceVersion = serviceNamespaceVersion;
	}
	
	
}


