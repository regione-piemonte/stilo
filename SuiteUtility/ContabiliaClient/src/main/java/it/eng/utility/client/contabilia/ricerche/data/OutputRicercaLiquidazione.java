/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

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
