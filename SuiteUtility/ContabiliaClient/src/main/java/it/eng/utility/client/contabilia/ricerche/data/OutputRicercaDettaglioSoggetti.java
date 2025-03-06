package it.eng.utility.client.contabilia.ricerche.data;

import it.csi.siac.ricerche.svc._1.RicercaDettaglioSoggettiResponse;
import it.eng.utility.client.contabilia.Esito;

public class OutputRicercaDettaglioSoggetti extends Esito {
	
	RicercaDettaglioSoggettiResponse entry;
	
	public RicercaDettaglioSoggettiResponse getEntry() {
		return entry;
	}
	
	public void setEntry(RicercaDettaglioSoggettiResponse entry) {
		this.entry = entry;
	}
	
}
