package it.eng.utility.client.contabilia.ricerche.data;

import it.csi.siac.ricerche.svc._1.RicercaAccertamentoResponse;
import it.eng.utility.client.contabilia.Esito;

public class OutputRicercaAccertamento extends Esito {
	
	RicercaAccertamentoResponse entry;
	
	public RicercaAccertamentoResponse getEntry() {
		return entry;
	}
	
	public void setEntry(RicercaAccertamentoResponse entry) {
		this.entry = entry;
	}
	
}
