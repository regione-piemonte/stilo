package it.eng.hsm.client.config;

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

public class ProxyConfig {

	@XmlVariabile(nome = "proxyHost", tipo = TipoVariabile.SEMPLICE)
	private String proxyHost;
	@XmlVariabile(nome = "proxyPort", tipo = TipoVariabile.SEMPLICE)
	private String proxyPort;
	@XmlVariabile(nome = "proxyUser", tipo = TipoVariabile.SEMPLICE)
	private String proxyUser;
	@XmlVariabile(nome = "proxyPassword", tipo = TipoVariabile.SEMPLICE)
	private String proxyPassword;
	@XmlVariabile(nome = "useProxy", tipo = TipoVariabile.SEMPLICE)
	private boolean useProxy;

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

	public boolean isUseProxy() {
		return useProxy;
	}

	public void setUseProxy(boolean useProxy) {
		this.useProxy = useProxy;
	}

}
