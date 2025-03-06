package it.eng.utility.client.contabilia.ricerche.data;

import java.io.Serializable;

import it.csi.siac.ricerche.svc._1.RicercaImpegno;

public class RicercaImpegnoRequest extends RicercaImpegno implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String idSpAoo;
	
	public String getIdSpAoo() {
		return idSpAoo;
	}
	
	public void setIdSpAoo(String idSpAoo) {
		this.idSpAoo = idSpAoo;
	}
	
}
