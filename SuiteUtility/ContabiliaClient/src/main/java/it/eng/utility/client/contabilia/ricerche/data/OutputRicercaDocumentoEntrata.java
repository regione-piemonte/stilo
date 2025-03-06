package it.eng.utility.client.contabilia.ricerche.data;

import it.csi.siac.documenti.svc._1.RicercaDocumentoEntrataResponse;
import it.eng.utility.client.contabilia.Esito;

public class OutputRicercaDocumentoEntrata extends Esito {
	
	RicercaDocumentoEntrataResponse entry;
	
	public RicercaDocumentoEntrataResponse getEntry() {
		return entry;
	}
	
	public void setEntry(RicercaDocumentoEntrataResponse entry) {
		this.entry = entry;
	}
	
}
