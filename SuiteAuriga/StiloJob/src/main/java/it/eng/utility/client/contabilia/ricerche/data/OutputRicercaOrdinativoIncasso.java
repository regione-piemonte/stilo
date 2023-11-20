/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.csi.siac.ricerche.svc._1.RicercaOrdinativoIncassoResponse;
import it.eng.utility.client.contabilia.Esito;

public class OutputRicercaOrdinativoIncasso extends Esito {
	
	RicercaOrdinativoIncassoResponse entry;
	
	public RicercaOrdinativoIncassoResponse getEntry() {
		return entry;
	}
	
	public void setEntry(RicercaOrdinativoIncassoResponse entry) {
		this.entry = entry;
	}
	
}
