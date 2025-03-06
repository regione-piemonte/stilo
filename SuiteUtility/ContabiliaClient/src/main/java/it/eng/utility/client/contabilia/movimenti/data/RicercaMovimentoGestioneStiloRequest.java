package it.eng.utility.client.contabilia.movimenti.data;

import java.io.Serializable;

import it.csi.siac.stilo.svc._1.RicercaMovimentoGestioneStilo;

public class RicercaMovimentoGestioneStiloRequest extends RicercaMovimentoGestioneStilo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String idSpAoo;
	
	public String getIdSpAoo() {
		return idSpAoo;
	}
	
	public void setIdSpAoo(String idSpAoo) {
		this.idSpAoo = idSpAoo;
	}
	
}
