/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("restriction")
@XmlRootElement
public class OracleWccStorageConfig implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5961222266866766088L;
	
	private String sslKeystoreAlias;
	private String port;
	private String sslKeystoreAliasPassword;
	private String sslEnable;
	private String sslKeystoreFile;
	private String sslKeystorePassword;
	private String type;
	private String host;
	private String connectionTimeout;
	private String contentUser;
	private String useSSL;
	private String password;
	
	private String tempRepositoryBasePath;
	
	public String getSslKeystoreAlias() {
		return sslKeystoreAlias;
	}
	public void setSslKeystoreAlias(String sslKeystoreAlias) {
		this.sslKeystoreAlias = sslKeystoreAlias;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getSslKeystoreAliasPassword() {
		return sslKeystoreAliasPassword;
	}
	public void setSslKeystoreAliasPassword(String sslKeystoreAliasPassword) {
		this.sslKeystoreAliasPassword = sslKeystoreAliasPassword;
	}
	public String getSslEnable() {
		return sslEnable;
	}
	public void setSslEnable(String sslEnable) {
		this.sslEnable = sslEnable;
	}
	public String getSslKeystoreFile() {
		return sslKeystoreFile;
	}
	public void setSslKeystoreFile(String sslKeystoreFile) {
		this.sslKeystoreFile = sslKeystoreFile;
	}
	public String getSslKeystorePassword() {
		return sslKeystorePassword;
	}
	public void setSslKeystorePassword(String sslKeystorePassword) {
		this.sslKeystorePassword = sslKeystorePassword;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getConnectionTimeout() {
		return connectionTimeout;
	}
	public void setConnectionTimeout(String connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}
	public String getContentUser() {
		return contentUser;
	}
	public void setContentUser(String contentUser) {
		this.contentUser = contentUser;
	}
	public String getUseSSL() {
		return useSSL;
	}
	public void setUseSSL(String useSSL) {
		this.useSSL = useSSL;
	}
	public String getTempRepositoryBasePath() {
		return tempRepositoryBasePath;
	}
	public void setTempRepositoryBasePath(String tempRepositoryBasePath) {
		this.tempRepositoryBasePath = tempRepositoryBasePath;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}