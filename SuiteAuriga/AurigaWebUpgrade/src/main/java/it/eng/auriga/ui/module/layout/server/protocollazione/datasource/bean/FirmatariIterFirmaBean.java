/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean;

import java.util.Date;

public class FirmatariIterFirmaBean {

	private String nroOrdine;
	private String idUtente;
	private String desUtente;
	private String tipoFirma;	
	private String ruolo;
	private Boolean flgApposta;
	private Date dataFirma;
	
	public String getNroOrdine() {
		return nroOrdine;
	}
	public void setNroOrdine(String nroOrdine) {
		this.nroOrdine = nroOrdine;
	}
	public String getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(String idUtente) {
		this.idUtente = idUtente;
	}
	public String getDesUtente() {
		return desUtente;
	}
	public void setDesUtente(String desUtente) {
		this.desUtente = desUtente;
	}
	public String getTipoFirma() {
		return tipoFirma;
	}
	public void setTipoFirma(String tipoFirma) {
		this.tipoFirma = tipoFirma;
	}
	public String getRuolo() {
		return ruolo;
	}
	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
	public Boolean getFlgApposta() {
		return flgApposta;
	}
	public void setFlgApposta(Boolean flgApposta) {
		this.flgApposta = flgApposta;
	}
	public Date getDataFirma() {
		return dataFirma;
	}
	public void setDataFirma(Date dataFirma) {
		this.dataFirma = dataFirma;
	}
	
}
