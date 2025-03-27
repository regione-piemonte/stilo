/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.registriNumerazione.datasource.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import java.util.Map;

public class RegistriNumerazioneBean  extends RegistriNumerazioneXmlBean{

	
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtInizioVld;
	
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtFineVld;
	
	@TipoData(tipo = Tipo.DATA)
	private Date dtUltimoCambioStato;

	private String rowid;
	private BigDecimal nroIniziale;
	private Boolean flgLocked;
	private String flgAmmEscXTipiDoc;
	private String flgModTipiDocAmmEscl;
	private Boolean flgNumerazioneSenzaContinuita;
	private Boolean flgRestrizioniVersoRegE;
	private Boolean flgRestrizioniVersoRegU;
	private Boolean flgRestrizioniVersoRegI;
	private Boolean flgCtrAbilUOMitt;
	private String gruppoRegistriApp;
	private String flgStatoRegistro;
	
	private List<TipiDocAmmEscBean> listaTipiDocAmmEsc;
	
	// Attributi dinamici
	 private Map<String, Object> valori;
	 private Map<String, String> tipiValori;
	 	
	
	
	public Date getDtInizioVld() {
		return dtInizioVld;
	}
	public void setDtInizioVld(Date dtInizioVld) {
		this.dtInizioVld = dtInizioVld;
	}
	public Date getDtFineVld() {
		return dtFineVld;
	}
	public void setDtFineVld(Date dtFineVld) {
		this.dtFineVld = dtFineVld;
	}
	public Boolean getFlgLocked() {
		return flgLocked;
	}
	public void setFlgLocked(Boolean flgLocked) {
		this.flgLocked = flgLocked;
	}
	public String getFlgAmmEscXTipiDoc() {
		return flgAmmEscXTipiDoc;
	}
	public void setFlgAmmEscXTipiDoc(String flgAmmEscXTipiDoc) {
		this.flgAmmEscXTipiDoc = flgAmmEscXTipiDoc;
	}
	public String getFlgModTipiDocAmmEscl() {
		return flgModTipiDocAmmEscl;
	}
	public void setFlgModTipiDocAmmEscl(String flgModTipiDocAmmEscl) {
		this.flgModTipiDocAmmEscl = flgModTipiDocAmmEscl;
	}
	public Boolean getFlgNumerazioneSenzaContinuita() {
		return flgNumerazioneSenzaContinuita;
	}
	public void setFlgNumerazioneSenzaContinuita(Boolean flgNumerazioneSenzaContinuita) {
		this.flgNumerazioneSenzaContinuita = flgNumerazioneSenzaContinuita;
	}
	public BigDecimal getNroIniziale() {
		return nroIniziale;
	}
	public void setNroIniziale(BigDecimal nroIniziale) {
		this.nroIniziale = nroIniziale;
	}
	public Boolean getFlgRestrizioniVersoRegE() {
		return flgRestrizioniVersoRegE;
	}
	public void setFlgRestrizioniVersoRegE(Boolean flgRestrizioniVersoRegE) {
		this.flgRestrizioniVersoRegE = flgRestrizioniVersoRegE;
	}
	public Boolean getFlgRestrizioniVersoRegU() {
		return flgRestrizioniVersoRegU;
	}
	public void setFlgRestrizioniVersoRegU(Boolean flgRestrizioniVersoRegU) {
		this.flgRestrizioniVersoRegU = flgRestrizioniVersoRegU;
	}
	public Boolean getFlgRestrizioniVersoRegI() {
		return flgRestrizioniVersoRegI;
	}
	public void setFlgRestrizioniVersoRegI(Boolean flgRestrizioniVersoRegI) {
		this.flgRestrizioniVersoRegI = flgRestrizioniVersoRegI;
	}
	public Boolean getFlgCtrAbilUOMitt() {
		return flgCtrAbilUOMitt;
	}
	public void setFlgCtrAbilUOMitt(Boolean flgCtrAbilUOMitt) {
		this.flgCtrAbilUOMitt = flgCtrAbilUOMitt;
	}
	public String getGruppoRegistriApp() {
		return gruppoRegistriApp;
	}
	public void setGruppoRegistriApp(String gruppoRegistriApp) {
		this.gruppoRegistriApp = gruppoRegistriApp;
	}
	public String getFlgStatoRegistro() {
		return flgStatoRegistro;
	}
	public void setFlgStatoRegistro(String flgStatoRegistro) {
		this.flgStatoRegistro = flgStatoRegistro;
	}
	public Date getDtUltimoCambioStato() {
		return dtUltimoCambioStato;
	}
	public void setDtUltimoCambioStato(Date dtUltimoCambioStato) {
		this.dtUltimoCambioStato = dtUltimoCambioStato;
	}
	public List<TipiDocAmmEscBean> getListaTipiDocAmmEsc() {
		return listaTipiDocAmmEsc;
	}
	public void setListaTipiDocAmmEsc(List<TipiDocAmmEscBean> listaTipiDocAmmEsc) {
		this.listaTipiDocAmmEsc = listaTipiDocAmmEsc;
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
	public String getRowid() {
		return rowid;
	}
	public void setRowid(String rowid) {
		this.rowid = rowid;
	}	
	
	
}
