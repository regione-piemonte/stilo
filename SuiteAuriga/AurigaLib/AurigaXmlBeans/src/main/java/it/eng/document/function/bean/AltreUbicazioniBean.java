/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AltreUbicazioniBean implements Serializable {

	private static final long serialVersionUID = 5611310655086324291L;

	@NumeroColonna(numero = "1")
	private String codToponimo;

	@NumeroColonna(numero = "2")
	private String toponimoIndirizzo;

	@NumeroColonna(numero = "3")
	private String frazione;

	@NumeroColonna(numero = "4")
	private String civico;

	@NumeroColonna(numero = "5")
	private String appendici;

	@NumeroColonna(numero = "6")
	private String cap;

	@NumeroColonna(numero = "7")
	private String comune;

	@NumeroColonna(numero = "8")
	private String nomeComuneCitta;

	private String provincia;

	@NumeroColonna(numero = "9")
	private String stato;

	@NumeroColonna(numero = "10")
	private String nomeStato;

	@NumeroColonna(numero = "11")
	private String zona;

	@NumeroColonna(numero = "12")
	private String complementoIndirizzo;

	@NumeroColonna(numero = "13")
	private String tipoToponimo;

	public String getCodToponimo() {
		return codToponimo;
	}

	public void setCodToponimo(String codToponimo) {
		this.codToponimo = codToponimo;
	}

	public String getToponimoIndirizzo() {
		return toponimoIndirizzo;
	}

	public void setToponimoIndirizzo(String toponimoIndirizzo) {
		this.toponimoIndirizzo = toponimoIndirizzo;
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

	public String getAppendici() {
		return appendici;
	}

	public void setAppendici(String appendici) {
		this.appendici = appendici;
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

	public String getNomeComuneCitta() {
		return nomeComuneCitta;
	}

	public void setNomeComuneCitta(String nomeComuneCitta) {
		this.nomeComuneCitta = nomeComuneCitta;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
