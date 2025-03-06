package it.eng.utility.client.contabilia.ricerche.data;

import it.csi.siac.ricerche.svc._1.RicercaOrdinativoSpesaResponse;
import it.eng.utility.client.contabilia.Esito;

public class OutputRicercaOrdinativoSpesa extends Esito {
	
	RicercaOrdinativoSpesaResponse entry;
	
	public RicercaOrdinativoSpesaResponse getEntry() {
		return entry;
	}
	
	public void setEntry(RicercaOrdinativoSpesaResponse entry) {
		this.entry = entry;
	}
	
}
