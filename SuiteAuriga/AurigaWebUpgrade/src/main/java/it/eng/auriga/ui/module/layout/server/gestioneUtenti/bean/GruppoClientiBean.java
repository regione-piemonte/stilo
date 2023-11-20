/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;


public class GruppoClientiBean {

	@NumeroColonna(numero="1")
	private String idGruppoClienti;
	
	@NumeroColonna(numero="2")
	private String denominazioneGruppoClienti;

	public String getIdGruppoClienti() {
		return idGruppoClienti;
	}

	public void setIdGruppoClienti(String idGruppoClienti) {
		this.idGruppoClienti = idGruppoClienti;
	}

	public String getDenominazioneGruppoClienti() {
		return denominazioneGruppoClienti;
	}

	public void setDenominazioneGruppoClienti(String denominazioneGruppoClienti) {
		this.denominazioneGruppoClienti = denominazioneGruppoClienti;
	}

	
}
