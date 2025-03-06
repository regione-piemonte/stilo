package it.eng.utility.client.toponomastica;

import java.io.Serializable;

public class StradaRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String nomeVia;
	private String formatoRisposta;
	private String endpoint;
	private String metodo;
	private String token;
	
	public String getNomeVia() {
		return nomeVia;
	}
	
	public void setNomeVia(String nomeVia) {
		this.nomeVia = nomeVia;
	}
	
	public String getFormatoRisposta() {
		return formatoRisposta;
	}
	
	public void setFormatoRisposta(String formatoRisposta) {
		this.formatoRisposta = formatoRisposta;
	}

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
