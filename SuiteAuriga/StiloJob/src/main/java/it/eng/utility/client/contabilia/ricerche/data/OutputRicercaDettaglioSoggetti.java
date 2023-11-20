/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

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
