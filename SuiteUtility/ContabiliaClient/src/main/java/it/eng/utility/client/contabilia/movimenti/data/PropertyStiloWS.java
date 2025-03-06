package it.eng.utility.client.contabilia.movimenti.data;

import java.io.Serializable;

import it.csi.siac.stilo.svc._1.RicercaMovimentoGestioneStilo;

public class PropertyStiloWS implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private RicercaMovimentoGestioneStilo movimento;
	private String endpoint;
	
	public RicercaMovimentoGestioneStilo getMovimento() {
		return movimento;
	}
	
	public void setMovimento(RicercaMovimentoGestioneStilo movimento) {
		this.movimento = movimento;
	}
	
	public String getEndpoint() {
		return endpoint;
	}
	
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	
	@Override
	public String toString() {
		return "PropertyStiloWS [movimento=" + movimento + ", endpoint=" + endpoint + "]";
	}
	
}
