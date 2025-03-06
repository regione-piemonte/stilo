package it.eng.utility.client.contabilia.ricerche.data;

import it.csi.siac.ricerche.svc._1.RicercaEstesaLiquidazioniResponse;
import it.eng.utility.client.contabilia.Esito;

public class OutputRicercaEstesaLiquidazioni extends Esito {
	
	RicercaEstesaLiquidazioniResponse entry;
	
	public RicercaEstesaLiquidazioniResponse getEntry() {
		return entry;
	}
	
	public void setEntry(RicercaEstesaLiquidazioniResponse entry) {
		this.entry = entry;
	}
	
}
