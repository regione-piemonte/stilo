/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;

public class FindSoggettoBean {

	private BigDecimal idSoggetto;
	private String codRapidoSoggetto;
	private String flgPersFisica;
	private String denominazioneSoggetto;
	private String cognomeSoggetto;
	private String nomeSoggetto;
	private String codfiscaleSoggetto;
	private String codTipoSoggetto;
	private String idUoSoggetto;
	private String idUserSoggetto;
	private String idScrivaniaSoggetto;
	private String flgInOrganigramma;
	private String tipologiaSoggetto;
	private boolean trovatiSoggMultipliInRicerca;
	private String usernameSoggetto;
	private String indirizzoPecSoggetto;
	private String indirizzoPeoSoggetto;
	private String indirizzoMailSoggetto;
	
	private String flgSelXAssegnazione;	

	/*
	 * Indirizzo
	 */
	private String codToponomastica;
	private String indirizzo;
	private String civico;
	private String localitaFrazione;
	private String cap;
	private String comune;
	private String nomeComune;
	private String codStato;
	private String nomeStato;
	private String zona;
	private String complementoIndirizzo;
	private String tipoToponimo;
	private String appendici;
	
	
	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	public String getCodRapidoSoggetto() {
		return codRapidoSoggetto;
	}
	public void setCodRapidoSoggetto(String codRapidoSoggetto) {
		this.codRapidoSoggetto = codRapidoSoggetto;
	}
	public String getFlgPersFisica() {
		return flgPersFisica;
	}
	public void setFlgPersFisica(String flgPersFisica) {
		this.flgPersFisica = flgPersFisica;
	}
	public String getDenominazioneSoggetto() {
		return denominazioneSoggetto;
	}
	public void setDenominazioneSoggetto(String denominazioneSoggetto) {
		this.denominazioneSoggetto = denominazioneSoggetto;
	}
	public String getCognomeSoggetto() {
		return cognomeSoggetto;
	}
	public void setCognomeSoggetto(String cognomeSoggetto) {
		this.cognomeSoggetto = cognomeSoggetto;
	}
	public String getNomeSoggetto() {
		return nomeSoggetto;
	}
	public void setNomeSoggetto(String nomeSoggetto) {
		this.nomeSoggetto = nomeSoggetto;
	}
	public String getCodfiscaleSoggetto() {
		return codfiscaleSoggetto;
	}
	public void setCodfiscaleSoggetto(String codfiscaleSoggetto) {
		this.codfiscaleSoggetto = codfiscaleSoggetto;
	}
	public String getCodTipoSoggetto() {
		return codTipoSoggetto;
	}
	public void setCodTipoSoggetto(String codTipoSoggetto) {
		this.codTipoSoggetto = codTipoSoggetto;
	}
	public String getIdUoSoggetto() {
		return idUoSoggetto;
	}
	public void setIdUoSoggetto(String idUoSoggetto) {
		this.idUoSoggetto = idUoSoggetto;
	}
	public String getIdUserSoggetto() {
		return idUserSoggetto;
	}
	public void setIdUserSoggetto(String idUserSoggetto) {
		this.idUserSoggetto = idUserSoggetto;
	}
	public String getIdScrivaniaSoggetto() {
		return idScrivaniaSoggetto;
	}
	public void setIdScrivaniaSoggetto(String idScrivaniaSoggetto) {
		this.idScrivaniaSoggetto = idScrivaniaSoggetto;
	}
	public String getFlgInOrganigramma() {
		return flgInOrganigramma;
	}
	public void setFlgInOrganigramma(String flgInOrganigramma) {
		this.flgInOrganigramma = flgInOrganigramma;
	}
	public String getTipologiaSoggetto() {
		return tipologiaSoggetto;
	}
	public void setTipologiaSoggetto(String tipologiaSoggetto) {
		this.tipologiaSoggetto = tipologiaSoggetto;
	}
	public boolean isTrovatiSoggMultipliInRicerca() {
		return trovatiSoggMultipliInRicerca;
	}
	public void setTrovatiSoggMultipliInRicerca(boolean trovatiSoggMultipliInRicerca) {
		this.trovatiSoggMultipliInRicerca = trovatiSoggMultipliInRicerca;
	}
	public String getUsernameSoggetto() {
		return usernameSoggetto;
	}
	public void setUsernameSoggetto(String usernameSoggetto) {
		this.usernameSoggetto = usernameSoggetto;
	}	
	public String getIndirizzoPecSoggetto() {
		return indirizzoPecSoggetto;
	}
	public void setIndirizzoPecSoggetto(String indirizzoPecSoggetto) {
		this.indirizzoPecSoggetto = indirizzoPecSoggetto;
	}
	public String getIndirizzoPeoSoggetto() {
		return indirizzoPeoSoggetto;
	}
	public void setIndirizzoPeoSoggetto(String indirizzoPeoSoggetto) {
		this.indirizzoPeoSoggetto = indirizzoPeoSoggetto;
	}
	public String getIndirizzoMailSoggetto() {
		return indirizzoMailSoggetto;
	}
	public void setIndirizzoMailSoggetto(String indirizzoMailSoggetto) {
		this.indirizzoMailSoggetto = indirizzoMailSoggetto;
	}
	public String getFlgSelXAssegnazione() {
		return flgSelXAssegnazione;
	}
	public void setFlgSelXAssegnazione(String flgSelXAssegnazione) {
		this.flgSelXAssegnazione = flgSelXAssegnazione;
	}	
	public String getCodToponomastica() {
		return codToponomastica;
	}
	public void setCodToponomastica(String codToponomastica) {
		this.codToponomastica = codToponomastica;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getCivico() {
		return civico;
	}
	public void setCivico(String civico) {
		this.civico = civico;
	}
	public String getLocalitaFrazione() {
		return localitaFrazione;
	}
	public void setLocalitaFrazione(String localitaFrazione) {
		this.localitaFrazione = localitaFrazione;
	}
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public String getComune() {
		return comune;
	}
	public void setComune(String comune) {
		this.comune = comune;
	}
	public String getNomeComune() {
		return nomeComune;
	}
	public void setNomeComune(String nomeComune) {
		this.nomeComune = nomeComune;
	}
	public String getCodStato() {
		return codStato;
	}
	public void setCodStato(String codStato) {
		this.codStato = codStato;
	}
	public String getNomeStato() {
		return nomeStato;
	}
	public void setNomeStato(String nomeStato) {
		this.nomeStato = nomeStato;
	}
	public String getZona() {
		return zona;
	}
	public void setZona(String zona) {
		this.zona = zona;
	}
	public String getComplementoIndirizzo() {
		return complementoIndirizzo;
	}
	public void setComplementoIndirizzo(String complementoIndirizzo) {
		this.complementoIndirizzo = complementoIndirizzo;
	}
	public String getTipoToponimo() {
		return tipoToponimo;
	}
	public void setTipoToponimo(String tipoToponimo) {
		this.tipoToponimo = tipoToponimo;
	}
	public String getAppendici() {
		return appendici;
	}
	public void setAppendici(String appendici) {
		this.appendici = appendici;
	}

}
