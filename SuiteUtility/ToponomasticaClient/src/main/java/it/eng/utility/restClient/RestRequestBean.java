package it.eng.utility.restClient;

import java.io.Serializable;

public class RestRequestBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String endpoint;
	private String metodo;
	private String token;
	private String requestParam;
	
	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getMetodo() {
		return metodo;
	}

	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRequestParam() {
		return requestParam;
	}

	public void setRequestParam(String requestParam) {
		this.requestParam = requestParam;
	}
	
}
