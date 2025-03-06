package it.eng.auriga.opentext.config;

import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class ContentServerSettings {

	private String authenticationUser;

	private String authenticationPassword;

	private String authException;

	private int cwsConfigMTOMchunkSize;

	private String enterpriseRootType;

	private String documentTypeNode;

	private String folderTypeNode;

	private int csDurataToken;

	private long idDwoCathegory;

	private long idAttachmentCathegory;

	private String authenticationWsdl;

	private String docManagementWsdl;

	private String contentServiceWsdl;

	private String memberServiceWsdl;

	private String searchServiceWsdl;

	private String folderContainerName;

	private String confidentialityLevel;
	
	private Map<String,Long> categorieOT;

	public String getAuthenticationUser() {
		return authenticationUser;
	}

	public void setAuthenticationUser(String authenticationUser) {
		this.authenticationUser = authenticationUser;
	}

	public String getAuthenticationPassword() {
		return authenticationPassword;
	}

	public void setAuthenticationPassword(String authenticationPassword) {
		this.authenticationPassword = authenticationPassword;
	}

	public String getAuthException() {
		return authException;
	}

	public void setAuthException(String authException) {
		this.authException = authException;
	}

	public int getCwsConfigMTOMchunkSize() {
		return cwsConfigMTOMchunkSize;
	}

	public void setCwsConfigMTOMchunkSize(int cwsConfigMTOMchunkSize) {
		this.cwsConfigMTOMchunkSize = cwsConfigMTOMchunkSize;
	}

	public String getEnterpriseRootType() {
		return enterpriseRootType;
	}

	public void setEnterpriseRootType(String enterpriseRootType) {
		this.enterpriseRootType = enterpriseRootType;
	}

	public String getDocumentTypeNode() {
		return documentTypeNode;
	}

	public void setDocumentTypeNode(String documentTypeNode) {
		this.documentTypeNode = documentTypeNode;
	}

	public String getFolderTypeNode() {
		return folderTypeNode;
	}

	public void setFolderTypeNode(String folderTypeNode) {
		this.folderTypeNode = folderTypeNode;
	}

	public int getCsDurataToken() {
		return csDurataToken;
	}

	public void setCsDurataToken(int csDurataToken) {
		this.csDurataToken = csDurataToken;
	}

	public long getIdDwoCathegory() {
		return idDwoCathegory;
	}

	public void setIdDwoCathegory(long idDwoCathegory) {
		this.idDwoCathegory = idDwoCathegory;
	}

	public long getIdAttachmentCathegory() {
		return idAttachmentCathegory;
	}

	public void setIdAttachmentCathegory(long idAttachmentCathegory) {
		this.idAttachmentCathegory = idAttachmentCathegory;
	}

	public String getAuthenticationWsdl() {
		return authenticationWsdl;
	}

	public void setAuthenticationWsdl(String authenticationWsdl) {
		this.authenticationWsdl = authenticationWsdl;
	}

	public String getDocManagementWsdl() {
		return docManagementWsdl;
	}

	public void setDocManagementWsdl(String docManagementWsdl) {
		this.docManagementWsdl = docManagementWsdl;
	}

	public String getContentServiceWsdl() {
		return contentServiceWsdl;
	}

	public void setContentServiceWsdl(String contentServiceWsdl) {
		this.contentServiceWsdl = contentServiceWsdl;
	}

	public String getMemberServiceWsdl() {
		return memberServiceWsdl;
	}

	public void setMemberServiceWsdl(String memberServiceWsdl) {
		this.memberServiceWsdl = memberServiceWsdl;
	}

	public String getSearchServiceWsdl() {
		return searchServiceWsdl;
	}

	public void setSearchServiceWsdl(String searchServiceWsdl) {
		this.searchServiceWsdl = searchServiceWsdl;
	}

	public String getFolderContainerName() {
		return folderContainerName;
	}

	public void setFolderContainerName(String folderContainerName) {
		this.folderContainerName = folderContainerName;
	}

	public String getConfidentialityLevel() {
		return confidentialityLevel;
	}

	public void setConfidentialityLevel(String confidentialityLevel) {
		this.confidentialityLevel = confidentialityLevel;
	}

	public Map<String, Long> getCategorieOT() {
		return categorieOT;
	}

	public void setCategorieOT(Map<String, Long> categorieOT) {
		this.categorieOT = categorieOT;
	}

}
