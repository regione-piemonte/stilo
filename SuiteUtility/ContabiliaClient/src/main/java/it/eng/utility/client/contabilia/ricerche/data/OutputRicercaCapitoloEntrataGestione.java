package it.eng.utility.client.contabilia.ricerche.data;

import it.csi.siac.ricerche.svc._1.RicercaCapitoloEntrataGestione;
import it.csi.siac.ricerche.svc._1.RicercaCapitoloEntrataGestioneResponse;
import it.eng.utility.client.contabilia.Esito;

public class OutputRicercaCapitoloEntrataGestione extends Esito {
	
	RicercaCapitoloEntrataGestioneResponse entry;
	
	public RicercaCapitoloEntrataGestioneResponse getEntry() {
		return entry;
	}
	
	public void setEntry(RicercaCapitoloEntrataGestioneResponse entry) {
		this.entry = entry;
	}
	
}
