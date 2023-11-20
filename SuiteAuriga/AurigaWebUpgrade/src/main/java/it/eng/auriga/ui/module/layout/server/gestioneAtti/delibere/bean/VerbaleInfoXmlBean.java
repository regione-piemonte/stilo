/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

/**
 * 
 * @author DANCRIST
 *
 */

public class VerbaleInfoXmlBean {
	
	@XmlVariabile(nome="IdDoc", tipo=TipoVariabile.SEMPLICE)
	private String idDoc;
	
	@XmlVariabile(nome = "URI", tipo = TipoVariabile.SEMPLICE)
	private String uri;
	
	@XmlVariabile(nome = "Mimetype", tipo = TipoVariabile.SEMPLICE)
	private String mimetype;
	
	@XmlVariabile(nome = "FlgPdfizzabile", tipo = TipoVariabile.SEMPLICE)
	private Boolean flgPdfizzabile = true;
	
	@XmlVariabile(nome = "Dimensione", tipo = TipoVariabile.SEMPLICE)
	private Long dimensione;
	
	@XmlVariabile(nome="Impronta", tipo=TipoVariabile.SEMPLICE)
	private String impronta;
	
	@XmlVariabile(nome="EncodingImpronta", tipo=TipoVariabile.SEMPLICE)
	private String encoding;
	
	@XmlVariabile(nome="AlgoritmoImpronta", tipo=TipoVariabile.SEMPLICE)
	private String algoritmo;
	
	@XmlVariabile(nome="FlgFirmato", tipo=TipoVariabile.SEMPLICE)
	private Boolean flgFirmato;
	
	@XmlVariabile(nome="TipoFirma", tipo=TipoVariabile.SEMPLICE)
	private String tipoFirma;

	@XmlVariabile(nome="@Firmatari", tipo=TipoVariabile.LISTA)
	private List<FirmatarioXmlBean> listaFirmatari;
	
	@XmlVariabile(nome="FlgDaFirmare", tipo=TipoVariabile.SEMPLICE)
	private Boolean flgDaFirmare;
	
	@XmlVariabile(nome="DisplayFilename", tipo=TipoVariabile.SEMPLICE)
	private String displayFilename;
	
	@XmlVariabile(nome="NomeModello", tipo=TipoVariabile.SEMPLICE)
	private String nomeModello;
	
	@XmlVariabile(nome="IdModello", tipo=TipoVariabile.SEMPLICE)
	private String idModello;
	
	@XmlVariabile(nome="URIModello", tipo=TipoVariabile.SEMPLICE)
	private String uriModello;
	
	@XmlVariabile(nome="TipoModello", tipo=TipoVariabile.SEMPLICE)
	private String tipoModello;
	
	@XmlVariabile(nome="TestoHtml", tipo=TipoVariabile.SEMPLICE)
	private String testoHtml;
	
	/**
	 *  valori:  salvataggio - firma
	 */
	
	@XmlVariabile(nome = "AzioneDaFare", tipo = TipoVariabile.SEMPLICE)
	private String azioneDaFare;

	public String getIdDoc() {
		return idDoc;
	}

	public void setIdDoc(String idDoc) {
		this.idDoc = idDoc;
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

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getAlgoritmo() {
		return algoritmo;
	}

	public void setAlgoritmo(String algoritmo) {
		this.algoritmo = algoritmo;
	}

	public Boolean getFlgFirmato() {
		return flgFirmato;
	}

	public void setFlgFirmato(Boolean flgFirmato) {
		this.flgFirmato = flgFirmato;
	}
	
	public String getTipoFirma() {
		return tipoFirma;
	}

	public void setTipoFirma(String tipoFirma) {
		this.tipoFirma = tipoFirma;
	}

	public List<FirmatarioXmlBean> getListaFirmatari() {
		return listaFirmatari;
	}

	public void setListaFirmatari(List<FirmatarioXmlBean> listaFirmatari) {
		this.listaFirmatari = listaFirmatari;
	}

	public Boolean getFlgDaFirmare() {
		return flgDaFirmare;
	}

	public void setFlgDaFirmare(Boolean flgDaFirmare) {
		this.flgDaFirmare = flgDaFirmare;
	}

	public String getDisplayFilename() {
		return displayFilename;
	}

	public void setDisplayFilename(String displayFilename) {
		this.displayFilename = displayFilename;
	}

	public String getNomeModello() {
		return nomeModello;
	}

	public void setNomeModello(String nomeModello) {
		this.nomeModello = nomeModello;
	}

	public String getIdModello() {
		return idModello;
	}

	public void setIdModello(String idModello) {
		this.idModello = idModello;
	}

	public String getUriModello() {
		return uriModello;
	}

	public void setUriModello(String uriModello) {
		this.uriModello = uriModello;
	}

	public String getTipoModello() {
		return tipoModello;
	}

	public void setTipoModello(String tipoModello) {
		this.tipoModello = tipoModello;
	}

	public String getTestoHtml() {
		return testoHtml;
	}

	public void setTestoHtml(String testoHtml) {
		this.testoHtml = testoHtml;
	}

	public String getAzioneDaFare() {
		return azioneDaFare;
	}

	public void setAzioneDaFare(String azioneDaFare) {
		this.azioneDaFare = azioneDaFare;
	}

}