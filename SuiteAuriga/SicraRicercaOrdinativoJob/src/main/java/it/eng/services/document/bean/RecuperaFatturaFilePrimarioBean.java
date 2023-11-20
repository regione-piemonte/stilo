/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;
import it.eng.document.function.bean.Flag;

import java.math.BigDecimal;

public class RecuperaFatturaFilePrimarioBean {

	@XmlVariabile(nome = "NroLastVisibleVer", tipo = TipoVariabile.SEMPLICE)
	private Integer nroLastVisibleVer;

	@XmlVariabile(nome = "NroLastVer", tipo = TipoVariabile.SEMPLICE)
	private Integer nroLastVer;

	@XmlVariabile(nome = "DisplayFilename", tipo = TipoVariabile.SEMPLICE)
	private String displayFilename;

	@XmlVariabile(nome = "URI", tipo = TipoVariabile.SEMPLICE)
	private String uri;

	@XmlVariabile(nome = "URI_VerNonFirmata", tipo = TipoVariabile.SEMPLICE)
	private String uri_VerNonFirmata;

	@XmlVariabile(nome = "Dimensione", tipo = TipoVariabile.SEMPLICE)
	private BigDecimal dimensione;

	@XmlVariabile(nome = "FlgFirmato", tipo = TipoVariabile.SEMPLICE)
	private Flag flgFirmato;

	@XmlVariabile(nome = "Impronta", tipo = TipoVariabile.SEMPLICE)
	private String impronta;

	@XmlVariabile(nome = "AlgoritmoImpronta", tipo = TipoVariabile.SEMPLICE)
	private String algoritmoImpronta;

	@XmlVariabile(nome = "EncodingImpronta", tipo = TipoVariabile.SEMPLICE)
	private String encodingImpronta;

	@XmlVariabile(nome = "Mimetype", tipo = TipoVariabile.SEMPLICE)
	private String mimetype;

	@XmlVariabile(nome = "FlgConvertibilePdf", tipo = TipoVariabile.SEMPLICE)
	private Flag flgConvertibilePdf;

	public Integer getNroLastVisibleVer() {
		return nroLastVisibleVer;
	}

	public void setNroLastVisibleVer(Integer nroLastVisibleVer) {
		this.nroLastVisibleVer = nroLastVisibleVer;
	}

	public Integer getNroLastVer() {
		return nroLastVer;
	}

	public void setNroLastVer(Integer nroLastVer) {
		this.nroLastVer = nroLastVer;
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

	public String getUri_VerNonFirmata() {
		return uri_VerNonFirmata;
	}

	public void setUri_VerNonFirmata(String uri_VerNonFirmata) {
		this.uri_VerNonFirmata = uri_VerNonFirmata;
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

	@Override
	public String toString() {
		return String
				.format("RecuperaFatturaFilePrimarioBean [nroLastVisibleVer=%s, nroLastVer=%s, displayFilename=%s, uri=%s, uri_VerNonFirmata=%s, dimensione=%s, flgFirmato=%s, impronta=%s, algoritmoImpronta=%s, encodingImpronta=%s, mimetype=%s, flgConvertibilePdf=%s]",
						nroLastVisibleVer, nroLastVer, displayFilename, uri, uri_VerNonFirmata, dimensione, flgFirmato, impronta,
						algoritmoImpronta, encodingImpronta, mimetype, flgConvertibilePdf);
	}

}
