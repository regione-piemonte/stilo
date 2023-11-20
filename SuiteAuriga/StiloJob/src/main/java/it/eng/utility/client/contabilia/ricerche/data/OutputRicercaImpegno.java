/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.csi.siac.ricerche.svc._1.RicercaImpegnoResponse;
import it.eng.utility.client.contabilia.Esito;

public class OutputRicercaImpegno extends Esito {
	
	private RicercaImpegnoResponse entry;
	
	public RicercaImpegnoResponse getEntry() {
		return entry;
	}
	
	public void setEntry(RicercaImpegnoResponse entry) {
		this.entry = entry;
	}
	
}
