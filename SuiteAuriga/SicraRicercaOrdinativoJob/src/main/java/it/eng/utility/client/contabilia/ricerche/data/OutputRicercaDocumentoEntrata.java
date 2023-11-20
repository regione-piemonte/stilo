/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

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
