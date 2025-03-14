package it.eng.utility.client.contabilia.movimenti.data;

import it.csi.siac.stilo.svc._1.RicercaMovimentoGestioneStiloResponse;
import it.eng.utility.client.contabilia.Esito;

public class OutputRicercaMovimentoGestione extends Esito {
	
	RicercaMovimentoGestioneStiloResponse entry;
	Integer totaleRisultati;
	
	public RicercaMovimentoGestioneStiloResponse getEntry() {
		return entry;
	}
	
	public void setEntry(RicercaMovimentoGestioneStiloResponse entry) {
		this.entry = entry;
	}
	
	public Integer getTotaleRisultati() {
		return totaleRisultati;
	}
	
	public void setTotaleRisultati(Integer totaleRisultati) {
		this.totaleRisultati = totaleRisultati;
	}
	
}
