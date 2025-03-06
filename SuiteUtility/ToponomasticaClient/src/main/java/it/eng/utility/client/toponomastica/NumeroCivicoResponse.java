package it.eng.utility.client.toponomastica;

import java.io.Serializable;

public class NumeroCivicoResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer status;
	private String response;
	private boolean numeroCivicoEsiste;
	
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getResponse() {
		return response;
	}
	
	public void setResponse(String response) {
		this.response = response;
	}
	
	public boolean isNumeroCivicoEsiste() {
		return numeroCivicoEsiste;
	}
	
	public void setNumeroCivicoEsiste(boolean numeroCivicoEsiste) {
		this.numeroCivicoEsiste = numeroCivicoEsiste;
	}
	
}
