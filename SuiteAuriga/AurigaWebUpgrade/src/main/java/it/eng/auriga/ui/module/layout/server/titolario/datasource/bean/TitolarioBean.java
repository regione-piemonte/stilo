/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.Date;

public class TitolarioBean {

	private String idPianoClassif;
	private String idClassificazione;
	private String tipo;
	private String descrizione;
	private String descrizioneEstesa;
	private String paroleChiave;
	private String indice;
	private String indiceXOrd;

	private String nroLivello;
	private String livelloCorrente;
	private String livello;

	private Date tsValidaDal;
	private Date tsValidaFinoAl;
	
	private String flgAttiva;
	
	private Boolean flgNumContinua;

	/**
	 * Periodo conservazione
	 */
	// -- (valori 1/0/NULL) Se 1 significa che in generale la documentazione della data classifica deve essere conservata per tempo illimitato
	private Boolean flgConservPermIn;
	// -- Periodo - espresso in anni - per cui conservare la documentazione della data classifica (salvo eccezioni)
	private String periodoConservAnni;

	private Boolean flgNoRichAbil;

	private String idClassificaSup;
	private String desClassificaSup;

	private Boolean flgStoricizzaDati;
	private Boolean flgDatiDaStoricizzare;

	private Boolean flgSelXFinalita;
	private BigDecimal score;
	
	private Integer flgIgnoreWarning;
	
	
	

	public String getIdPianoClassif() {
		return idPianoClassif;
	}

	public void setIdPianoClassif(String idPianoClassif) {
		this.idPianoClassif = idPianoClassif;
	}

	public String getIdClassificazione() {
		return idClassificazione;
	}

	public void setIdClassificazione(String idClassificazione) {
		this.idClassificazione = idClassificazione;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Boolean getFlgSelXFinalita() {
		return flgSelXFinalita;
	}

	public void setFlgSelXFinalita(Boolean flgSelXFinalita) {
		this.flgSelXFinalita = flgSelXFinalita;
	}

	public String getIdClassificaSup() {
		return idClassificaSup;
	}

	public void setIdClassificaSup(String idClassificaSup) {
		this.idClassificaSup = idClassificaSup;
	}

	public String getDesClassificaSup() {
		return desClassificaSup;
	}

	public void setDesClassificaSup(String desClassificaSup) {
		this.desClassificaSup = desClassificaSup;
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public String getDescrizioneEstesa() {
		return descrizioneEstesa;
	}

	public void setDescrizioneEstesa(String descrizioneEstesa) {
		this.descrizioneEstesa = descrizioneEstesa;
	}

	public String getParoleChiave() {
		return paroleChiave;
	}

	public void setParoleChiave(String paroleChiave) {
		this.paroleChiave = paroleChiave;
	}

	public String getIndice() {
		return indice;
	}

	public void setIndice(String indice) {
		this.indice = indice;
	}

	public Date getTsValidaDal() {
		return tsValidaDal;
	}

	public void setTsValidaDal(Date tsValidaDal) {
		this.tsValidaDal = tsValidaDal;
	}

	public Date getTsValidaFinoAl() {
		return tsValidaFinoAl;
	}

	public void setTsValidaFinoAl(Date tsValidaFinoAl) {
		this.tsValidaFinoAl = tsValidaFinoAl;
	}
	
	public String getFlgAttiva() {
		return flgAttiva;
	}

	public void setFlgAttiva(String flgAttiva) {
		this.flgAttiva = flgAttiva;
	}
	
	public Boolean getFlgNoRichAbil() {
		return flgNoRichAbil;
	}

	public void setFlgNoRichAbil(Boolean flgNoRichAbil) {
		this.flgNoRichAbil = flgNoRichAbil;
	}

	public String getIndiceXOrd() {
		return indiceXOrd;
	}

	public Integer getFlgIgnoreWarning() {
		return flgIgnoreWarning;
	}

	public void setFlgIgnoreWarning(Integer flgIgnoreWarning) {
		this.flgIgnoreWarning = flgIgnoreWarning;
	}

	public void setIndiceXOrd(String indiceXOrd) {
		this.indiceXOrd = indiceXOrd;
	}

	public String getNroLivello() {
		return nroLivello;
	}

	public void setNroLivello(String nroLivello) {
		this.nroLivello = nroLivello;
	}

	public String getLivelloCorrente() {
		return livelloCorrente;
	}

	public void setLivelloCorrente(String livelloCorrente) {
		this.livelloCorrente = livelloCorrente;
	}

	public String getLivello() {
		return livello;
	}

	public void setLivello(String livello) {
		this.livello = livello;
	}

	public Boolean getFlgStoricizzaDati() {
		return flgStoricizzaDati;
	}

	public void setFlgStoricizzaDati(Boolean flgStoricizzaDati) {
		this.flgStoricizzaDati = flgStoricizzaDati;
	}

	public Boolean getFlgDatiDaStoricizzare() {
		return flgDatiDaStoricizzare;
	}

	public void setFlgDatiDaStoricizzare(Boolean flgDatiDaStoricizzare) {
		this.flgDatiDaStoricizzare = flgDatiDaStoricizzare;
	}

	/**
	 * @return the flgConservPermIn
	 */
	public Boolean getFlgConservPermIn() {
		return flgConservPermIn;
	}

	/**
	 * @param flgConservPermIn
	 *            the flgConservPermIn to set
	 */
	public void setFlgConservPermIn(Boolean flgConservPermIn) {
		this.flgConservPermIn = flgConservPermIn;
	}

	/**
	 * @return the periodoConserv
	 */
	public String getPeriodoConservAnni() {
		return periodoConservAnni;
	}

	/**
	 * @param periodoConserv
	 *            the periodoConserv to set
	 */
	public void setPeriodoConservAnni(String periodoConservAnni) {
		this.periodoConservAnni = periodoConservAnni;
	}

	public Boolean getFlgNumContinua() {
		return flgNumContinua;
	}

	public void setFlgNumContinua(Boolean flgNumContinua) {
		this.flgNumContinua = flgNumContinua;
	}

	

	

}
