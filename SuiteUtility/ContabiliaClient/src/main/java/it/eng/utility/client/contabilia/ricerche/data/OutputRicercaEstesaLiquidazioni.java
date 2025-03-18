/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

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
