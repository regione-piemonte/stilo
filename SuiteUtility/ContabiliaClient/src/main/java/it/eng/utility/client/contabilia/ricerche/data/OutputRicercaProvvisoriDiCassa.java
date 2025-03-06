package it.eng.utility.client.contabilia.ricerche.data;

import it.csi.siac.documenti.svc._1.RicercaProvvisoriDiCassaResponse;
import it.eng.utility.client.contabilia.Esito;

public class OutputRicercaProvvisoriDiCassa extends Esito {
	
	RicercaProvvisoriDiCassaResponse entry;
	
	public RicercaProvvisoriDiCassaResponse getEntry() {
		return entry;
	}
	
	public void setEntry(RicercaProvvisoriDiCassaResponse entry) {
		this.entry = entry;
	}
	
}
