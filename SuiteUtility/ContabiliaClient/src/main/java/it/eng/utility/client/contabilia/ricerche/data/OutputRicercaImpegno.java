package it.eng.utility.client.contabilia.ricerche.data;

import it.csi.siac.ricerche.svc._1.RicercaImpegnoResponse;
import it.eng.utility.client.contabilia.Esito;

public class OutputRicercaImpegno extends Esito {
	
	private RicercaImpegnoResponse entry;
	
	public RicercaImpegnoResponse getEntry() {
		return entry;
	}
	
	public void setEntry(RicercaImpegnoResponse entry) {
		this.entry = entry;
	}
	
}
