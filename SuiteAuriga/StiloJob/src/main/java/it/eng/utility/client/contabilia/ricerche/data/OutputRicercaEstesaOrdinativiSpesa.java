/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.csi.siac.ricerche.svc._1.RicercaEstesaOrdinativiSpesaResponse;
import it.eng.utility.client.contabilia.Esito;

public class OutputRicercaEstesaOrdinativiSpesa extends Esito {
	
	RicercaEstesaOrdinativiSpesaResponse entry;
	
	public RicercaEstesaOrdinativiSpesaResponse getEntry() {
		return entry;
	}
	
	public void setEntry(RicercaEstesaOrdinativiSpesaResponse entry) {
		this.entry = entry;
	}
	
}
