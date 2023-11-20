/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

import java.util.Date;

public class IndirizzoSoggettoXmlBean {

	@NumeroColonna(numero = "1")
	private String rowId;

	@NumeroColonna(numero = "2")
	private String tipo;

	@NumeroColonna(numero = "7")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataValidoDal;

	@NumeroColonna(numero = "8")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataValidoFinoAl;

	@NumeroColonna(numero = "9")
	private String codToponimo;

	@NumeroColonna(numero = "10")
	private String toponimoIndirizzo;

	@NumeroColonna(numero = "11")
	private String civico;

	@NumeroColonna(numero = "12")
	private String interno;

	@NumeroColonna(numero = "15")
	private String cap;

	@NumeroColonna(numero = "16")
	private String frazione;

	@NumeroColonna(numero = "17")
	private String comune;

	@NumeroColonna(numero = "18")
	private String nomeComuneCitta;

	@NumeroColonna(numero = "19")
	private String stato;

	@NumeroColonna(numero = "20")
	private String nomeStato;

	@NumeroColonna(numero = "21")
	private String provincia;

	@NumeroColonna(numero = "22")
	private String zona;

	@NumeroColonna(numero = "23")
	private String complementoIndirizzo;

	@NumeroColonna(numero = "24")
	private String tipoToponimo;

	@NumeroColonna(numero = "25")
	private String appendici;

	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Date getDataValidoDal() {
		return dataValidoDal;
	}

	public void setDataValidoDal(Date dataValidoDal) {
		this.dataValidoDal = dataValidoDal;
	}

	public Date getDataValidoFinoAl() {
		return dataValidoFinoAl;
	}

	public void setDataValidoFinoAl(Date dataValidoFinoAl) {
		this.dataValidoFinoAl = dataValidoFinoAl;
	}

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

	public String getCivico() {
		return civico;
	}

	public void setCivico(String civico) {
		this.civico = civico;
	}

	public String getInterno() {
		return interno;
	}

	public void setInterno(String interno) {
		this.interno = interno;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getFrazione() {
		return frazione;
	}

	public void setFrazione(String frazione) {
		this.frazione = frazione;
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

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
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
