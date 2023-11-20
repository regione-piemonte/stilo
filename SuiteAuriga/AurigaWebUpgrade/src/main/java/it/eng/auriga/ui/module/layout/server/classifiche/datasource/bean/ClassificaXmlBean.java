/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class ClassificaXmlBean 
{
	
	@NumeroColonna(numero = "2")
	private String idClassificazione;
	
	@NumeroColonna(numero = "4")
	private String tipo;
	
	@NumeroColonna(numero = "3")
	private String descrizione;
	
	@NumeroColonna(numero = "5")
	private String descrizioneEstesa;
	
	@NumeroColonna(numero = "6")
	private String paroleChiave;
	@NumeroColonna(numero = "7")
	private String indice;
	@NumeroColonna(numero = "25")
	private String indiceXOrd;	
	
	@NumeroColonna(numero = "1")
	private String nroLivello;
	
	private String livelloCorrente;
	private String livello;
	
	@NumeroColonna(numero = "8")
	private String tsValidaDal;
	@NumeroColonna(numero = "9")
	private String tsValidaFinoAl;
	
	private String flgNoRichAbil;
	
	@NumeroColonna(numero = "20")
	private String idClassificaSup;
	@NumeroColonna(numero = "21")
	private String desClassificaSup;
	
	@NumeroColonna(numero = "19")
	private String flgSelXFinalita;
	@NumeroColonna(numero = "24")
	private String score;
	
	@NumeroColonna(numero = "26")
	private String flgAbilATutti;
	
	private String idUo;
	private String classificheAbilitate;
	private String  denominazioneEstesaUO;
	
	public String getIdUo() {
		return idUo;
	}
	public void setIdUo(String idUo) {
		this.idUo = idUo;
	}
	public String getClassificheAbilitate() {
		return classificheAbilitate;
	}
	public void setClassificheAbilitate(String classificheAbilitate) {
		this.classificheAbilitate = classificheAbilitate;
	}
	public String getDenominazioneEstesaUO() {
		return denominazioneEstesaUO;
	}
	public void setDenominazioneEstesaUO(String denominazioneEstesaUO) {
		this.denominazioneEstesaUO = denominazioneEstesaUO;
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
	
	
	public String getIndiceXOrd() {
		return indiceXOrd;
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
	public String getTsValidaDal() {
		return tsValidaDal;
	}
	public void setTsValidaDal(String tsValidaDal) {
		this.tsValidaDal = tsValidaDal;
	}
	public String getTsValidaFinoAl() {
		return tsValidaFinoAl;
	}
	public void setTsValidaFinoAl(String tsValidaFinoAl) {
		this.tsValidaFinoAl = tsValidaFinoAl;
	}
	public String getFlgNoRichAbil() {
		return flgNoRichAbil;
	}
	public void setFlgNoRichAbil(String flgNoRichAbil) {
		this.flgNoRichAbil = flgNoRichAbil;
	}
	public String getFlgSelXFinalita() {
		return flgSelXFinalita;
	}
	public void setFlgSelXFinalita(String flgSelXFinalita) {
		this.flgSelXFinalita = flgSelXFinalita;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getFlgAbilATutti() {
		return flgAbilATutti;
	}
	public void setFlgAbilATutti(String flgAbilATutti) {
		this.flgAbilATutti = flgAbilATutti;
	}
	
}
