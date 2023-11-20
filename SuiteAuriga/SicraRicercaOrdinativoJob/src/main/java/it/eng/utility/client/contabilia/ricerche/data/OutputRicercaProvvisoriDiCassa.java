/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.csi.siac.documenti.svc._1.RicercaProvvisoriDiCassaResponse;
import it.eng.utility.client.contabilia.Esito;

public class OutputRicercaProvvisoriDiCassa extends Esito {
	
	RicercaProvvisoriDiCassaResponse entry;
	
	public RicercaProvvisoriDiCassaResponse getEntry() {
		return entry;
	}
	
	public void setEntry(RicercaProvvisoriDiCassaResponse entry) {
		this.entry = entry;
	}
	
}
