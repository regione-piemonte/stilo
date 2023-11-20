/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class RigaDatiDaACSOR {

	private String codiceACSOR;

	@NumeroColonna(numero="1")
    private String cognome;
	@NumeroColonna(numero="2")
    private String nome;
	@NumeroColonna(numero="3")
    private String cf;
	@NumeroColonna(numero="4")
    private String piva;
	@NumeroColonna(numero="5")
    private String codiceToponimo;
	@NumeroColonna(numero="6")
    private String descrizioneToponimo;
	@NumeroColonna(numero="7")
    private String frazione;
	@NumeroColonna(numero="8")
    private String civico;
	@NumeroColonna(numero="9")
    private String barrato;
	@NumeroColonna(numero="10")
    private String cap;
	@NumeroColonna(numero="11")
    private String codiceIstatComune;
	@NumeroColonna(numero="12")
    private String descrizioneComune;
	@NumeroColonna(numero="13")
    private String codiceIstatStato;
	@NumeroColonna(numero="14")
    private String descrizioneStato;
	@NumeroColonna(numero="15")
    private String tipoToponimo;
	
	
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCf() {
		return cf;
	}
	public void setCf(String cf) {
		this.cf = cf;
	}
	public String getPiva() {
		return piva;
	}
	public void setPiva(String piva) {
		this.piva = piva;
	}
	public String getCodiceToponimo() {
		return codiceToponimo;
	}
	public void setCodiceToponimo(String codiceToponimo) {
		this.codiceToponimo = codiceToponimo;
	}
	public String getDescrizioneToponimo() {
		return descrizioneToponimo;
	}
	public void setDescrizioneToponimo(String descrizioneToponimo) {
		this.descrizioneToponimo = descrizioneToponimo;
	}
	public String getFrazione() {
		return frazione;
	}
	public void setFrazione(String frazione) {
		this.frazione = frazione;
	}
	public String getCivico() {
		return civico;
	}
	public void setCivico(String civico) {
		this.civico = civico;
	}
	public String getBarrato() {
		return barrato;
	}
	public void setBarrato(String barrato) {
		this.barrato = barrato;
	}
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public String getCodiceIstatComune() {
		return codiceIstatComune;
	}
	public void setCodiceIstatComune(String codiceIstatComune) {
		this.codiceIstatComune = codiceIstatComune;
	}
	public String getDescrizioneComune() {
		return descrizioneComune;
	}
	public void setDescrizioneComune(String descrizioneComune) {
		this.descrizioneComune = descrizioneComune;
	}
	public String getCodiceIstatStato() {
		return codiceIstatStato;
	}
	public void setCodiceIstatStato(String codiceIstatStato) {
		this.codiceIstatStato = codiceIstatStato;
	}
	public String getDescrizioneStato() {
		return descrizioneStato;
	}
	public void setDescrizioneStato(String descrizioneStato) {
		this.descrizioneStato = descrizioneStato;
	}
	public String getTipoToponimo() {
		return tipoToponimo;
	}
	public void setTipoToponimo(String tipoToponimo) {
		this.tipoToponimo = tipoToponimo;
	}
	
	public String getCodiceACSOR() {
		return codiceACSOR;
	}
	public void setCodiceACSOR(String codiceACSOR) {
		this.codiceACSOR = codiceACSOR;
	}
}
