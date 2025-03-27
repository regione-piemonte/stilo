/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean;

public class DestAttoBean {

	private String prefisso;
	private String denominazione;
	private String indirizzo;
	private String corteseAttenzione;
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
