package it.eng.utility.client.contabilia.ricerche.data;

import it.csi.siac.ricerche.svc._1.RicercaEstesaOrdinativiSpesaResponse;
import it.eng.utility.client.contabilia.Esito;

public class OutputRicercaEstesaOrdinativiSpesa extends Esito {
	
	RicercaEstesaOrdinativiSpesaResponse entry;
	
	public RicercaEstesaOrdinativiSpesaResponse getEntry() {
		return entry;
	}
	
	public void setEntry(RicercaEstesaOrdinativiSpesaResponse entry) {
		this.entry = entry;
	}
	
}
