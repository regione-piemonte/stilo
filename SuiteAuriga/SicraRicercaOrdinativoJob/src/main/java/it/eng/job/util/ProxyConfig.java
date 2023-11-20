/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

public class ProxyConfig implements Serializable {

	private static final long serialVersionUID = -5129501441034326968L;

	private String proxyHost;
	private int proxyPort;
	private String proxyUser;
	private String proxyPassword;
	
	
	public ProxyConfig() {
	}
	
	
	public String getProxyHost() {
		return proxyHost;
	}
	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}
	
	public int getProxyPort() {
		return proxyPort;
	}
	public void setProxyPort(int proxyPort) {
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


	@Override
	public String toString() {
		return String.format("ProxyConfig [proxyHost=%s, proxyPort=%s, proxyUser=%s, proxyPassword=%s]", 
				proxyHost, proxyPort, proxyUser, proxyPassword);
	}
	
	
}

