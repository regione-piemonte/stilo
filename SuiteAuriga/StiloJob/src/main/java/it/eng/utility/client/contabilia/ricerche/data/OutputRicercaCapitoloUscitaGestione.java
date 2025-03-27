/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.utility.client.contabilia.ricerche.data;

import it.csi.siac.ricerche.svc._1.RicercaCapitoloUscitaGestioneResponse;
import it.eng.utility.client.contabilia.Esito;

public class OutputRicercaCapitoloUscitaGestione extends Esito {
	
	RicercaCapitoloUscitaGestioneResponse entry;
	
	public RicercaCapitoloUscitaGestioneResponse getEntry() {
		return entry;
	}
	
	public void setEntry(RicercaCapitoloUscitaGestioneResponse entry) {
		this.entry = entry;
	}
	
}
