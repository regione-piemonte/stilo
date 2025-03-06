package it.eng.utility.client.contabilia.ricerche.data;

import it.csi.siac.ricerche.svc._1.RicercaLiquidazioneResponse;
import it.eng.utility.client.contabilia.Esito;

public class OutputRicercaLiquidazione extends Esito {
	
	RicercaLiquidazioneResponse entry;
	
	public RicercaLiquidazioneResponse getEntry() {
		return entry;
	}
	
	public void setEntry(RicercaLiquidazioneResponse entry) {
		this.entry = entry;
	}
	
}
