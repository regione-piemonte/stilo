/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.csi.siac.documenti.svc._1.RicercaDocumentoSpesaResponse;
import it.eng.utility.client.contabilia.Esito;

public class OutputRicercaDocumentoSpesa extends Esito {
	
	RicercaDocumentoSpesaResponse entry;
	
	public RicercaDocumentoSpesaResponse getEntry() {
		return entry;
	}
	
	public void setEntry(RicercaDocumentoSpesaResponse entry) {
		this.entry = entry;
	}
	
}
