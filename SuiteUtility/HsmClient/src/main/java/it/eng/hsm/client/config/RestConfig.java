package it.eng.hsm.client.config;

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

public class RestConfig {

	@XmlVariabile(nome = "urlEndpoint", tipo = TipoVariabile.SEMPLICE)
	private String urlEndpoint;
	
	public String getUrlEndpoint() {
		return urlEndpoint;
	}

	public void setUrlEndpoint(String urlEndpoint) {
		this.urlEndpoint = urlEndpoint;
	}

	
	
}
