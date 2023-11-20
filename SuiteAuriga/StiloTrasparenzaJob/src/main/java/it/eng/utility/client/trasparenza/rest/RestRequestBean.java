/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

public class RestRequestBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String endpoint;
	private String token;
	private String paramJson;
	private ProxyConfig proxyConfig;
	private TimeoutConfig timeoutConfig;
	private String nomeServizio;
	
	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}

	public String getParamJson() {
		return paramJson;
	}

	public void setParamJson(String paramJson) {
		this.paramJson = paramJson;
	}

	public ProxyConfig getProxyConfig() {
		return proxyConfig;
	}

	public void setProxyConfig(ProxyConfig proxyConfig) {
		this.proxyConfig = proxyConfig;
	}

	public TimeoutConfig getTimeoutConfig() {
		return timeoutConfig;
	}

	public void setTimeoutConfig(TimeoutConfig timeoutConfig) {
		this.timeoutConfig = timeoutConfig;
	}

	public String getNomeServizio() {
		return nomeServizio;
	}

	public void setNomeServizio(String nomeServizio) {
		this.nomeServizio = nomeServizio;
	}

	@Override
	public String toString() {
		return "RestRequestBean [endpoint=" + endpoint + ", token=" + token + ", paramJson=" + paramJson
				+ ", proxyConfig=" + proxyConfig + ", timeoutConfig=" + timeoutConfig + ", nomeServizio=" + nomeServizio
				+ "]";
	}
	
}
