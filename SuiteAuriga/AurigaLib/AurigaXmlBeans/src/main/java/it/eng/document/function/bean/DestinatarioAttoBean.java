/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class DestinatarioAttoBean {

	@NumeroColonna(numero = "1")
	private String prefisso;
	
	@NumeroColonna(numero = "2")
	private String denominazione;
	
	@NumeroColonna(numero = "3")
	private String indirizzo;
	
	@NumeroColonna(numero = "4")
	private String corteseAttenzione;
	
	@NumeroColonna(numero = "5")
	private String idSoggRubrica;

	public String getPrefisso() {
		return prefisso;
	}

	public void setPrefisso(String prefisso) {
		this.prefisso = prefisso;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getCorteseAttenzione() {
		return corteseAttenzione;
	}

	public void setCorteseAttenzione(String corteseAttenzione) {
		this.corteseAttenzione = corteseAttenzione;
	}

	public String getIdSoggRubrica() {
		return idSoggRubrica;
	}

	public void setIdSoggRubrica(String idSoggRubrica) {
		this.idSoggRubrica = idSoggRubrica;
	}
	
}
