/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class ProxyConfig {
	
	private String proxyUrl;
	private String proxyPort;
	private boolean proxyEnabled;
	
	public String getProxyUrl() {
		return proxyUrl;
	}
	
	public void setProxyUrl(String proxyUrl) {
		this.proxyUrl = proxyUrl;
	}
	
	public String getProxyPort() {
		return proxyPort;
	}
	
	public void setProxyPort(String proxyPort) {
		this.proxyPort = proxyPort;
	}
	
	public boolean isProxyEnabled() {
		return proxyEnabled;
	}
	
	public void setProxyEnabled(boolean proxyEnabled) {
		this.proxyEnabled = proxyEnabled;
	}
	
}
