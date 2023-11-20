/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class ProxyBean {
	
	String proxySet = "";
	String proxyHost = "";
	String proxyPort = "";
	String proxyUser = "";
	String proxyPassword = "";
	String nonProxyHosts = "";
	
	public String getProxySet() {
		return proxySet;
	}
	public void setProxySet(String proxySet) {
		this.proxySet = proxySet;
	}
	public String getProxyHost() {
		return proxyHost;
	}
	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}
	public String getProxyPort() {
		return proxyPort;
	}
	public void setProxyPort(String proxyPort) {
		this.proxyPort = proxyPort;
	}
	public String getProxyUser() {
		return proxyUser;
	}
	public void setProxyUser(String proxyUser) {
		this.proxyUser = proxyUser;
	}
	public String getProxyPassword() {
		return proxyPassword;
	}
	public void setProxyPassword(String proxyPassword) {
		this.proxyPassword = proxyPassword;
	}
	public String getNonProxyHosts() {
		return nonProxyHosts;
	}
	public void setNonProxyHosts(String nonProxyHosts) {
		this.nonProxyHosts = nonProxyHosts;
	}
	
}
