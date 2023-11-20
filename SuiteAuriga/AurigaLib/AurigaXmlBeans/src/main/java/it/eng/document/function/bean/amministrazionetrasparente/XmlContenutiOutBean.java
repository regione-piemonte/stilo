/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;

@XmlRootElement
public class XmlContenutiOutBean {

	@NumeroColonna(numero = "1")
	private String idContenuto;
	
	@NumeroColonna(numero = "2")
	private String tipoContenuto;
	
	@NumeroColonna(numero = "3")
	private String titolo;
	
	@NumeroColonna(numero = "4")
	private String contenutoHtml;
	
	@NumeroColonna(numero = "5")
	private Integer flagDatiCreazioneAggiornamento;
	
	@NumeroColonna(numero = "6")
	private String datiCreazioneAggiornamento;
	
	@NumeroColonna(numero = "7")
	private String nriRecordTabella;
	
	@NumeroColonna(numero = "8")
	private String uriFile;
	
	@NumeroColonna(numero = "9")
	private String nomeFile;
	
	@NumeroColonna(numero = "10")
	private Integer flagFileFirmato;
	
	@NumeroColonna(numero = "11")
	private Integer flagFileFirmatoCades;
	
	@NumeroColonna(numero = "12")
	private Integer flagIsOrMustBeFilePdf;
	
	@NumeroColonna(numero = "13")
	private String mimetype;
	
	@NumeroColonna(numero = "14")
	private String impronta;
	
	@NumeroColonna(numero = "15")
	private String algoritmoImpronta;
	
	@NumeroColonna(numero = "16")
	private String encodingImpronta;
	
	@NumeroColonna(numero = "17")
	private String idDocFile;;
	
	@NumeroColonna(numero = "18")
	private String nroVersione;
	
	@NumeroColonna(numero = "19")
	private String idUd;
	
	@NumeroColonna(numero = "24")
	private Integer flgPaginaDedicata;

	public String getIdContenuto() {
		return idContenuto;
	}

	public void setIdContenuto(String idContenuto) {
		this.idContenuto = idContenuto;
	}

	public String getTipoContenuto() {
		return tipoContenuto;
	}

	public void setTipoContenuto(String tipoContenuto) {
		this.tipoContenuto = tipoContenuto;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getContenutoHtml() {
		return contenutoHtml;
	}

	public void setContenutoHtml(String contenutoHtml) {
		this.contenutoHtml = contenutoHtml;
	}

	public Integer getFlagDatiCreazioneAggiornamento() {
		return flagDatiCreazioneAggiornamento;
	}

	public void setFlagDatiCreazioneAggiornamento(Integer flagDatiCreazioneAggiornamento) {
		this.flagDatiCreazioneAggiornamento = flagDatiCreazioneAggiornamento;
	}

	public String getDatiCreazioneAggiornamento() {
		return datiCreazioneAggiornamento;
	}

	public void setDatiCreazioneAggiornamento(String datiCreazioneAggiornamento) {
		this.datiCreazioneAggiornamento = datiCreazioneAggiornamento;
	}

	public String getNriRecordTabella() {
		return nriRecordTabella;
	}

	public void setNriRecordTabella(String nriRecordTabella) {
		this.nriRecordTabella = nriRecordTabella;
	}

	public String getUriFile() {
		return uriFile;
	}

	public void setUriFile(String uriFile) {
		this.uriFile = uriFile;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public Integer getFlagFileFirmato() {
		return flagFileFirmato;
	}

	public void setFlagFileFirmato(Integer flagFileFirmato) {
		this.flagFileFirmato = flagFileFirmato;
	}

	public Integer getFlagFileFirmatoCades() {
		return flagFileFirmatoCades;
	}

	public void setFlagFileFirmatoCades(Integer flagFileFirmatoCades) {
		this.flagFileFirmatoCades = flagFileFirmatoCades;
	}

	public Integer getFlagIsOrMustBeFilePdf() {
		return flagIsOrMustBeFilePdf;
	}

	public void setFlagIsOrMustBeFilePdf(Integer flagIsOrMustBeFilePdf) {
		this.flagIsOrMustBeFilePdf = flagIsOrMustBeFilePdf;
	}

	public String getMimetype() {
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public String getImpronta() {
		return impronta;
	}

	public void setImpronta(String impronta) {
		this.impronta = impronta;
	}

	public String getAlgoritmoImpronta() {
		return algoritmoImpronta;
	}

	public void setAlgoritmoImpronta(String algoritmoImpronta) {
		this.algoritmoImpronta = algoritmoImpronta;
	}

	public String getEncodingImpronta() {
		return encodingImpronta;
	}

	public void setEncodingImpronta(String encodingImpronta) {
		this.encodingImpronta = encodingImpronta;
	}

	public String getIdDocFile() {
		return idDocFile;
	}

	public void setIdDocFile(String idDocFile) {
		this.idDocFile = idDocFile;
	}

	public String getNroVersione() {
		return nroVersione;
	}

	public void setNroVersione(String nroVersione) {
		this.nroVersione = nroVersione;
	}

	public String getIdUd() {
		return idUd;
	}

	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}

	public Integer getFlgPaginaDedicata() {
		return flgPaginaDedicata;
	}

	public void setFlgPaginaDedicata(Integer flgPaginaDedicata) {
		this.flgPaginaDedicata = flgPaginaDedicata;
	}

}
