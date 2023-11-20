/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OpenTextStorageConfig implements Serializable {

	private static final long serialVersionUID = -6278355161867646643L;
	
	private String username;
	private String password;
	//private String rootPath;
	//private String libraryName;
	private String wsdlLocationAuthentication;
	private String serviceNameAuthentication;
	private String wsdlLocationDocumentManager;
	private String serviceNameDocumentManager;
	private String wsdlLocationContentService;
	private String serviceNameContentService;

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

	/*public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

	public String getLibraryName() {
		return libraryName;
	}

	public void setLibraryName(String libraryName) {
		this.libraryName = libraryName;
	}*/

	public String getWsdlLocationAuthentication() {
		return wsdlLocationAuthentication;
	}

	public void setWsdlLocationAuthentication(String wsdlLocationAuthentication) {
		this.wsdlLocationAuthentication = wsdlLocationAuthentication;
	}

	public String getServiceNameAuthentication() {
		return serviceNameAuthentication;
	}

	public void setServiceNameAuthentication(String serviceNameAuthentication) {
		this.serviceNameAuthentication = serviceNameAuthentication;
	}

	public String getWsdlLocationDocumentManager() {
		return wsdlLocationDocumentManager;
	}

	public String getServiceNameDocumentManager() {
		return serviceNameDocumentManager;
	}

	public void setServiceNameDocumentManager(String serviceNameDocumentManager) {
		this.serviceNameDocumentManager = serviceNameDocumentManager;
	}

	public String getWsdlLocationContentService() {
		return wsdlLocationContentService;
	}

	public void setWsdlLocationContentService(String wsdlLocationContentService) {
		this.wsdlLocationContentService = wsdlLocationContentService;
	}

	public String getServiceNameContentService() {
		return serviceNameContentService;
	}

	public void setServiceNameContentService(String serviceNameContentService) {
		this.serviceNameContentService = serviceNameContentService;
	}

	public void setWsdlLocationDocumentManager(String wsdlLocationDocumentManager) {
		this.wsdlLocationDocumentManager = wsdlLocationDocumentManager;
	}

}
