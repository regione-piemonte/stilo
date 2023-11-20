/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.csi.siac.ricerche.svc._1.RicercaOrdinativoSpesaResponse;
import it.eng.utility.client.contabilia.Esito;

public class OutputRicercaOrdinativoSpesa extends Esito {
	
	RicercaOrdinativoSpesaResponse entry;
	
	public RicercaOrdinativoSpesaResponse getEntry() {
		return entry;
	}
	
	public void setEntry(RicercaOrdinativoSpesaResponse entry) {
		this.entry = entry;
	}
	
}
