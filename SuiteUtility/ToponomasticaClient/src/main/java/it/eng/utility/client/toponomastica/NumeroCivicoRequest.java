package it.eng.utility.client.toponomastica;

import java.io.Serializable;

public class NumeroCivicoRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String codiceStrada;
	private String numeroCivico;
	private String letteraCivico;
	private String coloreCivico;
	private String formatoRisposta;
	private String endpoint;
	private String metodo;
	private String token;
	
	public String getCodiceStrada() {
		return codiceStrada;
	}
	
	public void setCodiceStrada(String codiceStrada) {
		this.codiceStrada = codiceStrada;
	}
	
	public String getNumeroCivico() {
		return numeroCivico;
	}
	
	public void setNumeroCivico(String numeroCivico) {
		this.numeroCivico = numeroCivico;
	}
	
	public String getLetteraCivico() {
		return letteraCivico;
	}
	
	public void setLetteraCivico(String letteraCivico) {
		this.letteraCivico = letteraCivico;
	}
	
	public String getColoreCivico() {
		return coloreCivico;
	}
	
	public void setColoreCivico(String coloreCivico) {
		this.coloreCivico = coloreCivico;
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
