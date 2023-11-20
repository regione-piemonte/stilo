/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

public class ToponomasticaRicercaRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String endpoint;
	private String metodo;
	private String token;
	
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
	
}
