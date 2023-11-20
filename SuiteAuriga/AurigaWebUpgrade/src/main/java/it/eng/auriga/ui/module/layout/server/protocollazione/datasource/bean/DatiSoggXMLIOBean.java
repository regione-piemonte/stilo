/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class DatiSoggXMLIOBean {

	@NumeroColonna(numero = "1")
	private String denominazioneSoggetto;

	@NumeroColonna(numero = "1")
	private String cognomeSoggetto;

	@NumeroColonna(numero = "2")
	private String nomeSoggetto;

	@NumeroColonna(numero = "3")
	private String flgPersFisica;

	@NumeroColonna(numero = "4")
	private String codfiscaleSoggetto;

	@NumeroColonna(numero = "15")
	private String codToponomastica;// Codice via (se indirizzo censito nella toponomastica)

	@NumeroColonna(numero = "16")
	private String indirizzo;// Nome via/toponimo

	@NumeroColonna(numero = "17")
	private String civico;// Civico (solo il N. la prima parte)

	@NumeroColonna(numero = "18")
	private String localitaFrazione;// Località/frazione
	
	@NumeroColonna(numero = "19")
	private String codComune;// Codice ISTAT del comune

	@NumeroColonna(numero = "20")
	private String nomeComune;// Nome del comune italiano o della città estera

	@NumeroColonna(numero = "21")
	private String codStato;// Codice ISTAT dello stato

	@NumeroColonna(numero = "22")
	private String nomeStato;// Nome dello stato

	@NumeroColonna(numero = "23")
	private String zona;// Zona della residenza/sede legale

	@NumeroColonna(numero = "24")
	private String complementoIndirizzo;// Info aggiuntive indirizzo

	@NumeroColonna(numero = "25")
	private String tipoToponimo;// Tipo di toponimo (i.e. via vicolo ecc)

	@NumeroColonna(numero = "26")
	private String appendici;// 2a parte del civico

	@NumeroColonna(numero = "27")
	private String codRapidoSoggetto;

	@NumeroColonna(numero = "28")
	private String codTipoSoggetto;

	@NumeroColonna(numero = "31")
	private String idUoSoggetto;

	@NumeroColonna(numero = "32")
	private String idUserSoggetto;

	@NumeroColonna(numero = "33")
	private String idScrivaniaSoggetto;

	@NumeroColonna(numero = "34")
	private String tipologiaSoggetto;
	
	@NumeroColonna(numero = "45")
	private String cap;// CAP

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

	public String getFlgPersFisica() {
		return flgPersFisica;
	}

	public void setFlgPersFisica(String flgPersFisica) {
		this.flgPersFisica = flgPersFisica;
	}

	public String getCodfiscaleSoggetto() {
		return codfiscaleSoggetto;
	}

	public void setCodfiscaleSoggetto(String codfiscaleSoggetto) {
		this.codfiscaleSoggetto = codfiscaleSoggetto;
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

	public String getCodComune() {
		return codComune;
	}

	public void setCodComune(String codComune) {
		this.codComune = codComune;
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

	public String getCodRapidoSoggetto() {
		return codRapidoSoggetto;
	}

	public void setCodRapidoSoggetto(String codRapidoSoggetto) {
		this.codRapidoSoggetto = codRapidoSoggetto;
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

	public String getTipologiaSoggetto() {
		return tipologiaSoggetto;
	}

	public void setTipologiaSoggetto(String tipologiaSoggetto) {
		this.tipologiaSoggetto = tipologiaSoggetto;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

}
