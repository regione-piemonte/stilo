/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import it.eng.document.function.bean.Flag;

import java.math.BigDecimal;
import java.util.Date;

public class RecuperaFatturaRicevutaSdIBean {

	@NumeroColonna(numero = "1")
	private BigDecimal idDoc;

	@NumeroColonna(numero = "2")
	private String tipoRicevuta;

	@NumeroColonna(numero = "3")
	private Date dataOraArrivo;

	@NumeroColonna(numero = "4")
	private String descrizione;

	@NumeroColonna(numero = "5")
	private String displayFilename;

	@NumeroColonna(numero = "6")
	private String uri;

	@NumeroColonna(numero = "7")
	private BigDecimal dimensione;

	@NumeroColonna(numero = "8")
	private Flag flgFirmato;

	@NumeroColonna(numero = "9")
	private Flag flgConvertibilePdf;

	@NumeroColonna(numero = "10")
	private String mimetype;

	@NumeroColonna(numero = "11")
	private BigDecimal idFormato;

	@NumeroColonna(numero = "12")
	private String impronta;

	@NumeroColonna(numero = "13")
	private String algoritmoImpronta;

	@NumeroColonna(numero = "14")
	private String encodingImpronta;

	
	public BigDecimal getIdDoc() {
		return idDoc;
	}

	public void setIdDoc(BigDecimal idDoc) {
		this.idDoc = idDoc;
	}

	public String getTipoRicevuta() {
		return tipoRicevuta;
	}

	public void setTipoRicevuta(String tipoRicevuta) {
		this.tipoRicevuta = tipoRicevuta;
	}

	public Date getDataOraArrivo() {
		return dataOraArrivo;
	}

	public void setDataOraArrivo(Date dataOraArrivo) {
		this.dataOraArrivo = dataOraArrivo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getDisplayFilename() {
		return displayFilename;
	}

	public void setDisplayFilename(String displayFilename) {
		this.displayFilename = displayFilename;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public BigDecimal getDimensione() {
		return dimensione;
	}

	public void setDimensione(BigDecimal dimensione) {
		this.dimensione = dimensione;
	}

	public Flag getFlgFirmato() {
		return flgFirmato;
	}

	public void setFlgFirmato(Flag flgFirmato) {
		this.flgFirmato = flgFirmato;
	}

	public Flag getFlgConvertibilePdf() {
		return flgConvertibilePdf;
	}

	public void setFlgConvertibilePdf(Flag flgConvertibilePdf) {
		this.flgConvertibilePdf = flgConvertibilePdf;
	}

	public String getMimetype() {
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public BigDecimal getIdFormato() {
		return idFormato;
	}

	public void setIdFormato(BigDecimal idFormato) {
		this.idFormato = idFormato;
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

	@Override
	public String toString() {
		return String
				.format("RecuperaFatturaRicevutaSdIBean [idDoc=%s, tipoRicevuta=%s, dataOraArrivo=%s, descrizione=%s, displayFilename=%s, uri=%s, dimensione=%s, flgFirmato=%s, flgConvertibilePdf=%s, mimetype=%s, idFormato=%s, impronta=%s, algoritmoImpronta=%s, encodingImpronta=%s]",
						idDoc, tipoRicevuta, dataOraArrivo, descrizione, displayFilename, uri, dimensione, flgFirmato, flgConvertibilePdf,
						mimetype, idFormato, impronta, algoritmoImpronta, encodingImpronta);
	}

}
