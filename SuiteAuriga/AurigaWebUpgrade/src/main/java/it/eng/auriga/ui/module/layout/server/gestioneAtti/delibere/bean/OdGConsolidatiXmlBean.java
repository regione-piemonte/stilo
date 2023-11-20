/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

/**
 * 
 * @author DANCRIST
 *
 */

public class OdGConsolidatiXmlBean {
	
	@NumeroColonna(numero = "1")
	private String idDoc;
	
	@NumeroColonna(numero = "2")
	@TipoData(tipo = Tipo.DATA)
	private Date dtConsolidamento;
	
	@NumeroColonna(numero = "3")
	private String tipoOdGConsolidato;
	
	@NumeroColonna(numero = "4")
	private String uri;
	
	@NumeroColonna(numero = "5")
	private String mimetype;
	
	@NumeroColonna(numero = "6")
	private Boolean flgPdfizzabile;
	
	@NumeroColonna(numero = "7")
	private Long dimensione;
	
	@NumeroColonna(numero = "8")
	private String impronta;
	
	@NumeroColonna(numero = "9")
	private String encodingImpronta;
	
	@NumeroColonna(numero = "10")
	private String algoritmoImpronta;
	
	@NumeroColonna(numero = "11")
	private Boolean flgFirmato;
	
	@NumeroColonna(numero = "12")
	private String firmatario;
	
	@NumeroColonna(numero = "13")
	private String displayFilename;

	@NumeroColonna(numero = "14")
	private String tipoFirma;
	
	@NumeroColonna(numero = "15")
	private Boolean flgRichPubblicazione;
	
	public String getIdDoc() {
		return idDoc;
	}

	public void setIdDoc(String idDoc) {
		this.idDoc = idDoc;
	}

	public Date getDtConsolidamento() {
		return dtConsolidamento;
	}

	public void setDtConsolidamento(Date dtConsolidamento) {
		this.dtConsolidamento = dtConsolidamento;
	}

	public String getTipoOdGConsolidato() {
		return tipoOdGConsolidato;
	}

	public void setTipoOdGConsolidato(String tipoOdGConsolidato) {
		this.tipoOdGConsolidato = tipoOdGConsolidato;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getMimetype() {
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public Boolean getFlgPdfizzabile() {
		return flgPdfizzabile;
	}

	public void setFlgPdfizzabile(Boolean flgPdfizzabile) {
		this.flgPdfizzabile = flgPdfizzabile;
	}

	public Long getDimensione() {
		return dimensione;
	}

	public void setDimensione(Long dimensione) {
		this.dimensione = dimensione;
	}

	public String getImpronta() {
		return impronta;
	}

	public void setImpronta(String impronta) {
		this.impronta = impronta;
	}

	public String getEncodingImpronta() {
		return encodingImpronta;
	}

	public void setEncodingImpronta(String encodingImpronta) {
		this.encodingImpronta = encodingImpronta;
	}

	public String getAlgoritmoImpronta() {
		return algoritmoImpronta;
	}

	public void setAlgoritmoImpronta(String algoritmoImpronta) {
		this.algoritmoImpronta = algoritmoImpronta;
	}

	public Boolean getFlgFirmato() {
		return flgFirmato;
	}

	public void setFlgFirmato(Boolean flgFirmato) {
		this.flgFirmato = flgFirmato;
	}

	public String getFirmatario() {
		return firmatario;
	}

	public void setFirmatario(String firmatario) {
		this.firmatario = firmatario;
	}

	public String getDisplayFilename() {
		return displayFilename;
	}

	public void setDisplayFilename(String displayFilename) {
		this.displayFilename = displayFilename;
	}

	public String getTipoFirma() {
		return tipoFirma;
	}

	public void setTipoFirma(String tipoFirma) {
		this.tipoFirma = tipoFirma;
	}

	public Boolean getFlgRichPubblicazione() {
		return flgRichPubblicazione;
	}

	public void setFlgRichPubblicazione(Boolean flgRichPubblicazione) {
		this.flgRichPubblicazione = flgRichPubblicazione;
	}

}