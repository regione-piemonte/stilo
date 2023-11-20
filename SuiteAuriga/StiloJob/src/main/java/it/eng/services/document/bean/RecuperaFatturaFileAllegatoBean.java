/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import it.eng.document.function.bean.Flag;

import java.math.BigDecimal;

public class RecuperaFatturaFileAllegatoBean {

	@NumeroColonna(numero = "1")
	private BigDecimal idDoc;

	@NumeroColonna(numero = "2")
	private String idTipoDocumento;

	@NumeroColonna(numero = "3")
	private String NomeTipoDocumento;

	@NumeroColonna(numero = "4")
	private String descrizione;

	@NumeroColonna(numero = "5")
	private Integer NroLastVisibleVer;

	@NumeroColonna(numero = "6")
	private Integer NroLastVer;

	@NumeroColonna(numero = "7")
	private String displayFilename;

	@NumeroColonna(numero = "8")
	private String uri;

	@NumeroColonna(numero = "9")
	private BigDecimal dimensione;

	@NumeroColonna(numero = "10")
	private Flag flgFirmato;

	@NumeroColonna(numero = "11")
	private String mimetype;

	@NumeroColonna(numero = "12")
	private Flag flgConvertibilePdf;

	@NumeroColonna(numero = "13")
	private String impronta;

	@NumeroColonna(numero = "14")
	private String algoritmoImpronta;

	@NumeroColonna(numero = "15")
	private String encodingImpronta;

	
	public BigDecimal getIdDoc() {
		return idDoc;
	}

	public void setIdDoc(BigDecimal idDoc) {
		this.idDoc = idDoc;
	}

	public String getIdTipoDocumento() {
		return idTipoDocumento;
	}

	public void setIdTipoDocumento(String idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}

	public String getNomeTipoDocumento() {
		return NomeTipoDocumento;
	}

	public void setNomeTipoDocumento(String nomeTipoDocumento) {
		NomeTipoDocumento = nomeTipoDocumento;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Integer getNroLastVisibleVer() {
		return NroLastVisibleVer;
	}

	public void setNroLastVisibleVer(Integer nroLastVisibleVer) {
		NroLastVisibleVer = nroLastVisibleVer;
	}

	public Integer getNroLastVer() {
		return NroLastVer;
	}

	public void setNroLastVer(Integer nroLastVer) {
		NroLastVer = nroLastVer;
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

	public String getMimetype() {
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public Flag getFlgConvertibilePdf() {
		return flgConvertibilePdf;
	}

	public void setFlgConvertibilePdf(Flag flgConvertibilePdf) {
		this.flgConvertibilePdf = flgConvertibilePdf;
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
				.format("RecuperaFatturaFileAllegatoBean [idDoc=%s, idTipoDocumento=%s, NomeTipoDocumento=%s, descrizione=%s, NroLastVisibleVer=%s, NroLastVer=%s, displayFilename=%s, uri=%s, dimensione=%s, flgFirmato=%s, mimetype=%s, flgConvertibilePdf=%s, impronta=%s, algoritmoImpronta=%s, encodingImpronta=%s]",
						idDoc, idTipoDocumento, NomeTipoDocumento, descrizione, NroLastVisibleVer, NroLastVer, displayFilename, uri,
						dimensione, flgFirmato, mimetype, flgConvertibilePdf, impronta, algoritmoImpronta, encodingImpronta);
	}

}
