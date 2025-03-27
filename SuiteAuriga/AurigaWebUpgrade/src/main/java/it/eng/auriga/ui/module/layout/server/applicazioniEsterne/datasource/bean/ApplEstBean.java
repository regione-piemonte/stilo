/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.applicazioniEsterne.datasource.bean;

import java.util.Date;
import java.util.Map;

/**
 * 
 * @author ottavio passalacqua
 *
 */

public class ApplEstBean {

	private String idApplEsterna;
    private String codApplicazione;
	private String codIstanza;
	private String nome;
	private Boolean flgUsaCredenzialiDiverse;
	private Boolean valido;
	private Date dtCensimento;
	private String utenteCensimento;
	private Date dtUltimoAggiornamento;
	private String utenteUltimoAggiornamento;
	private Boolean flgSistema;
	private String rowid;
	
	// Attributi dinamici
	private Map<String, Object> valori;
	private Map<String, String> tipiValori;

	 
	public String getCodApplicazione() {
		return codApplicazione;
	}
	public void setCodApplicazione(String codApplicazione) {
		this.codApplicazione = codApplicazione;
	}
	public String getCodIstanza() {
		return codIstanza;
	}
	public void setCodIstanza(String codIstanza) {
		this.codIstanza = codIstanza;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Date getDtCensimento() {
		return dtCensimento;
	}
	public void setDtCensimento(Date dtCensimento) {
		this.dtCensimento = dtCensimento;
	}
	public String getUtenteCensimento() {
		return utenteCensimento;
	}
	public void setUtenteCensimento(String utenteCensimento) {
		this.utenteCensimento = utenteCensimento;
	}
	public Date getDtUltimoAggiornamento() {
		return dtUltimoAggiornamento;
	}
	public void setDtUltimoAggiornamento(Date dtUltimoAggiornamento) {
		this.dtUltimoAggiornamento = dtUltimoAggiornamento;
	}
	public String getUtenteUltimoAggiornamento() {
		return utenteUltimoAggiornamento;
	}
	public void setUtenteUltimoAggiornamento(String utenteUltimoAggiornamento) {
		this.utenteUltimoAggiornamento = utenteUltimoAggiornamento;
	}
	public Boolean getFlgUsaCredenzialiDiverse() {
		return flgUsaCredenzialiDiverse;
	}
	public void setFlgUsaCredenzialiDiverse(Boolean flgUsaCredenzialiDiverse) {
		this.flgUsaCredenzialiDiverse = flgUsaCredenzialiDiverse;
	}
	public Boolean getValido() {
		return valido;
	}
	public void setValido(Boolean valido) {
		this.valido = valido;
	}
	public Boolean getFlgSistema() {
		return flgSistema;
	}
	public void setFlgSistema(Boolean flgSistema) {
		this.flgSistema = flgSistema;
	}
	public String getIdApplEsterna() {
		return idApplEsterna;
	}
	public void setIdApplEsterna(String idApplEsterna) {
		this.idApplEsterna = idApplEsterna;
	}
	public String getRowid() {
		return rowid;
	}
	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	public Map<String, Object> getValori() {
		return valori;
	}
	public void setValori(Map<String, Object> valori) {
		this.valori = valori;
	}
	public Map<String, String> getTipiValori() {
		return tipiValori;
	}
	public void setTipiValori(Map<String, String> tipiValori) {
		this.tipiValori = tipiValori;
	}
}
