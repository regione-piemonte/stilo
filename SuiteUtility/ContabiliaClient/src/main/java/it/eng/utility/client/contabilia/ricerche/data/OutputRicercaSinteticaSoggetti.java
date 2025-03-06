package it.eng.utility.client.contabilia.ricerche.data;

import it.csi.siac.ricerche.svc._1.RicercaSinteticaSoggettiResponse;
import it.eng.utility.client.contabilia.Esito;

public class OutputRicercaSinteticaSoggetti extends Esito {
	
	RicercaSinteticaSoggettiResponse entry;
	
	public RicercaSinteticaSoggettiResponse getEntry() {
		return entry;
	}
	
	public void setEntry(RicercaSinteticaSoggettiResponse entry) {
		this.entry = entry;
	}
	
}
