/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

public class StampaRegistroProtocollazioniConfig implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String applicazione;
	
	private String istanzaApplicazione;

	public String getApplicazione() {
		return applicazione;
	}

	public void setApplicazione(String applicazione) {
		this.applicazione = applicazione;
	}

	public String getIstanzaApplicazione() {
		return istanzaApplicazione;
	}

	public void setIstanzaApplicazione(String istanzaApplicazione) {
		this.istanzaApplicazione = istanzaApplicazione;
	}
	
}
