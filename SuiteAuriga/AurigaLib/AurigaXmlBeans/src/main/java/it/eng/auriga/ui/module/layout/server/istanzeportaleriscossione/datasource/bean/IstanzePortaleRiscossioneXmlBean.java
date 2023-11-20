/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

import java.util.Date;

public class IstanzePortaleRiscossioneXmlBean {

	//   1 - flgUdFolder
	//   2 - idUdFolder
	//   4 - N° istanza
	//  18 - Oggetto
	//  22 - Nome documento elettronico
	//  23 - N. allegati
	//  32 - Tipo di istanza
	//  93 - Nr. doc. con file 
	// 101 - Tributo
	// 102 - Anno di riferimento
	// 103 - N° doc. di riferimento
	// 103 - Classificazione
	// 201 - Data presentazione (è una data e ora)
	// 220 - Contribuente - Nome
	// 221 - Contribuente - C. F.
	// 222 - Contribuente - Cod. ACS
	// 230 - Uri documento elettronico
	// 264 - Mezzo trasmissione

	@NumeroColonna(numero = "1")
	private String flgUdFolder;
	
	@NumeroColonna(numero = "2")
	private String idUdFolder;
	
	@NumeroColonna(numero = "4")
	private String nrIstanza;
	
	@NumeroColonna(numero = "18")
	private String oggetto;

	@NumeroColonna(numero = "22")
	private String nomeDocElettronico;
		
	@NumeroColonna(numero = "23")
	private String nrAllegati;

	@NumeroColonna(numero = "32")
	private String tipoIstanza;
	
	@NumeroColonna(numero = "93")
	private String nroDocConFile;

	@NumeroColonna(numero = "101")
	private String tributo;

	@NumeroColonna(numero = "102")
	private String annoRiferimento;

	@NumeroColonna(numero = "103")
	private String numeroDocRiferimento;

	@NumeroColonna(numero = "104")
	private String classificazione;

	@NumeroColonna(numero = "201")
	@TipoData(tipo = Tipo.DATA)
	private Date tsIstanza;
	
	@NumeroColonna(numero = "220")
	private String nomeContribuente;

	@NumeroColonna(numero = "221")
	private String cfContribuente;

	@NumeroColonna(numero = "222")
	private String codACSContribuente;
	
	@NumeroColonna(numero = "230")
	private String uriDocElettronico;
	
	@NumeroColonna(numero = "264")
	private String mezzoTrasmissione;


	public String getNrIstanza() {
		return nrIstanza;
	}

	public void setNrIstanza(String nrIstanza) {
		this.nrIstanza = nrIstanza;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getNrAllegati() {
		return nrAllegati;
	}

	public void setNrAllegati(String nrAllegati) {
		this.nrAllegati = nrAllegati;
	}

	public String getTipoIstanza() {
		return tipoIstanza;
	}

	public void setTipoIstanza(String tipoIstanza) {
		this.tipoIstanza = tipoIstanza;
	}

	public String getTributo() {
		return tributo;
	}

	public void setTributo(String tributo) {
		this.tributo = tributo;
	}

	public String getAnnoRiferimento() {
		return annoRiferimento;
	}

	public void setAnnoRiferimento(String annoRiferimento) {
		this.annoRiferimento = annoRiferimento;
	}

	public String getNumeroDocRiferimento() {
		return numeroDocRiferimento;
	}

	public void setNumeroDocRiferimento(String numeroDocRiferimento) {
		this.numeroDocRiferimento = numeroDocRiferimento;
	}

	public String getClassificazione() {
		return classificazione;
	}

	public void setClassificazione(String classificazione) {
		this.classificazione = classificazione;
	}

	public Date getTsIstanza() {
		return tsIstanza;
	}

	public void setTsIstanza(Date tsIstanza) {
		this.tsIstanza = tsIstanza;
	}

	public String getNomeContribuente() {
		return nomeContribuente;
	}

	public void setNomeContribuente(String nomeContribuente) {
		this.nomeContribuente = nomeContribuente;
	}

	public String getCfContribuente() {
		return cfContribuente;
	}

	public void setCfContribuente(String cfContribuente) {
		this.cfContribuente = cfContribuente;
	}

	public String getCodACSContribuente() {
		return codACSContribuente;
	}

	public void setCodACSContribuente(String codACSContribuente) {
		this.codACSContribuente = codACSContribuente;
	}

	public String getFlgUdFolder() {
		return flgUdFolder;
	}

	public void setFlgUdFolder(String flgUdFolder) {
		this.flgUdFolder = flgUdFolder;
	}

	public String getIdUdFolder() {
		return idUdFolder;
	}

	public void setIdUdFolder(String idUdFolder) {
		this.idUdFolder = idUdFolder;
	}

	public String getUriDocElettronico() {
		return uriDocElettronico;
	}

	public void setUriDocElettronico(String uriDocElettronico) {
		this.uriDocElettronico = uriDocElettronico;
	}

	public String getNomeDocElettronico() {
		return nomeDocElettronico;
	}

	public void setNomeDocElettronico(String nomeDocElettronico) {
		this.nomeDocElettronico = nomeDocElettronico;
	}

	public String getNroDocConFile() {
		return nroDocConFile;
	}

	public void setNroDocConFile(String nroDocConFile) {
		this.nroDocConFile = nroDocConFile;
	}

	public String getMezzoTrasmissione() {
		return mezzoTrasmissione;
	}

	public void setMezzoTrasmissione(String mezzoTrasmissione) {
		this.mezzoTrasmissione = mezzoTrasmissione;
	}

}