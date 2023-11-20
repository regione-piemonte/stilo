/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.List;

import it.eng.document.TipoData;
import it.eng.document.XmlVariabile;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlVariabile.TipoVariabile;

public class InfoConvocazioneXmlBean {
	
	@XmlVariabile(nome="InizioLavori", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA)
	private Date dtInizioLavori;
	
	@XmlVariabile(nome="FineLavori", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA)
	private Date dtFineLavori;
	
	@XmlVariabile(nome="Verbale.IdDoc", tipo=TipoVariabile.SEMPLICE)
	private String verbaleIdDoc;
	
	@XmlVariabile(nome="Verbale.URI", tipo=TipoVariabile.SEMPLICE)
	private String verbaleURI;
	
	@XmlVariabile(nome="Verbale.Mimetype", tipo=TipoVariabile.SEMPLICE)
	private String mimetype;
	
	@XmlVariabile(nome="Verbale.Dimensione", tipo=TipoVariabile.SEMPLICE)
	private long verbaleDimensione;
	
	@XmlVariabile(nome="Verbale.FlgFirmato", tipo=TipoVariabile.SEMPLICE)
	private Integer verbaleFlgFirmato;
	
	@XmlVariabile(nome="Verbale.TipoFirma", tipo=TipoVariabile.SEMPLICE)
	private String verbaleTipoFirma;

	@XmlVariabile(nome="Verbale.@Firmatari", tipo=TipoVariabile.LISTA)
	private List<FirmatarioXmlBean> listaVerbaleFirmatari; 
	
	@XmlVariabile(nome="Verbale.FlgDaFirmare", tipo=TipoVariabile.SEMPLICE)
	private Integer verbaleFlgDaFirmare;
	
	@XmlVariabile(nome="Verbale.DisplayFilename", tipo=TipoVariabile.SEMPLICE)
	private String verbaleDisplayFilename;
	
	@XmlVariabile(nome="Verbale.Impronta", tipo=TipoVariabile.SEMPLICE)
	private String verbaleImpronta;
	
	@XmlVariabile(nome="Verbale.EncodingImpronta", tipo=TipoVariabile.SEMPLICE)
	private String verbaleEncodingImpronta;
	
	@XmlVariabile(nome="Verbale.AlgoritmoImpronta", tipo=TipoVariabile.SEMPLICE)
	private String verbaleAlgoritmoImpronta;
	
	@XmlVariabile(nome="Verbale.NomeModello", tipo=TipoVariabile.SEMPLICE)
	private String verbaleNomeModello;
	
	@XmlVariabile(nome="Verbale.IdModello", tipo=TipoVariabile.SEMPLICE)
	private String verbaleIdModello;
	
	@XmlVariabile(nome="Verbale.URIModello", tipo=TipoVariabile.SEMPLICE)
	private String verbaleUriModello;
	
	@XmlVariabile(nome="Verbale.TipoModello", tipo=TipoVariabile.SEMPLICE)
	private String verbaleTipoModello;
	
	@XmlVariabile(nome="@PresenzeAppelloIniziale", tipo=TipoVariabile.LISTA)
	private List<PresenzeOdgXmlBean> presenzeAppelloIniziale;

	@XmlVariabile(nome="Verbale.FlgPdfizzabile", tipo=TipoVariabile.SEMPLICE)
	private Integer verbaleFlgPdfizzabile;
	
	@XmlVariabile(nome="Verbale.TestoHtml", tipo=TipoVariabile.SEMPLICE)
	private String verbaleTestoHtml;
	
	@XmlVariabile(nome="DisattivatoRiordinoAutomatico", tipo=TipoVariabile.SEMPLICE)
	private Boolean disattivatoRiordinoAutomatico;
	
	public Date getDtInizioLavori() {
		return dtInizioLavori;
	}

	public void setDtInizioLavori(Date dtInizioLavori) {
		this.dtInizioLavori = dtInizioLavori;
	}

	public Date getDtFineLavori() {
		return dtFineLavori;
	}

	public void setDtFineLavori(Date dtFineLavori) {
		this.dtFineLavori = dtFineLavori;
	}

	public String getVerbaleIdDoc() {
		return verbaleIdDoc;
	}

	public void setVerbaleIdDoc(String verbaleIdDoc) {
		this.verbaleIdDoc = verbaleIdDoc;
	}

	public String getVerbaleURI() {
		return verbaleURI;
	}

	public void setVerbaleURI(String verbaleURI) {
		this.verbaleURI = verbaleURI;
	}

	public String getMimetype() {
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public long getVerbaleDimensione() {
		return verbaleDimensione;
	}

	public void setVerbaleDimensione(long verbaleDimensione) {
		this.verbaleDimensione = verbaleDimensione;
	}

	public Integer getVerbaleFlgFirmato() {
		return verbaleFlgFirmato;
	}

	public void setVerbaleFlgFirmato(Integer verbaleFlgFirmato) {
		this.verbaleFlgFirmato = verbaleFlgFirmato;
	}

	public String getVerbaleTipoFirma() {
		return verbaleTipoFirma;
	}

	public void setVerbaleTipoFirma(String verbaleTipoFirma) {
		this.verbaleTipoFirma = verbaleTipoFirma;
	}

	public List<FirmatarioXmlBean> getListaVerbaleFirmatari() {
		return listaVerbaleFirmatari;
	}

	public void setListaVerbaleFirmatari(List<FirmatarioXmlBean> listaVerbaleFirmatari) {
		this.listaVerbaleFirmatari = listaVerbaleFirmatari;
	}

	public Integer getVerbaleFlgDaFirmare() {
		return verbaleFlgDaFirmare;
	}

	public void setVerbaleFlgDaFirmare(Integer verbaleFlgDaFirmare) {
		this.verbaleFlgDaFirmare = verbaleFlgDaFirmare;
	}

	public String getVerbaleDisplayFilename() {
		return verbaleDisplayFilename;
	}

	public void setVerbaleDisplayFilename(String verbaleDisplayFilename) {
		this.verbaleDisplayFilename = verbaleDisplayFilename;
	}

	public String getVerbaleImpronta() {
		return verbaleImpronta;
	}

	public void setVerbaleImpronta(String verbaleImpronta) {
		this.verbaleImpronta = verbaleImpronta;
	}

	public String getVerbaleEncodingImpronta() {
		return verbaleEncodingImpronta;
	}

	public void setVerbaleEncodingImpronta(String verbaleEncodingImpronta) {
		this.verbaleEncodingImpronta = verbaleEncodingImpronta;
	}

	public String getVerbaleAlgoritmoImpronta() {
		return verbaleAlgoritmoImpronta;
	}

	public void setVerbaleAlgoritmoImpronta(String verbaleAlgoritmoImpronta) {
		this.verbaleAlgoritmoImpronta = verbaleAlgoritmoImpronta;
	}

	public String getVerbaleNomeModello() {
		return verbaleNomeModello;
	}

	public void setVerbaleNomeModello(String verbaleNomeModello) {
		this.verbaleNomeModello = verbaleNomeModello;
	}

	public String getVerbaleIdModello() {
		return verbaleIdModello;
	}

	public void setVerbaleIdModello(String verbaleIdModello) {
		this.verbaleIdModello = verbaleIdModello;
	}

	public String getVerbaleUriModello() {
		return verbaleUriModello;
	}

	public void setVerbaleUriModello(String verbaleUriModello) {
		this.verbaleUriModello = verbaleUriModello;
	}

	public String getVerbaleTipoModello() {
		return verbaleTipoModello;
	}

	public void setVerbaleTipoModello(String verbaleTipoModello) {
		this.verbaleTipoModello = verbaleTipoModello;
	}

	public List<PresenzeOdgXmlBean> getPresenzeAppelloIniziale() {
		return presenzeAppelloIniziale;
	}

	public void setPresenzeAppelloIniziale(List<PresenzeOdgXmlBean> presenzeAppelloIniziale) {
		this.presenzeAppelloIniziale = presenzeAppelloIniziale;
	}

	public Integer getVerbaleFlgPdfizzabile() {
		return verbaleFlgPdfizzabile;
	}

	public void setVerbaleFlgPdfizzabile(Integer verbaleFlgPdfizzabile) {
		this.verbaleFlgPdfizzabile = verbaleFlgPdfizzabile;
	}
	
	public String getVerbaleTestoHtml() {
		return verbaleTestoHtml;
	}

	public void setVerbaleTestoHtml(String verbaleTestoHtml) {
		this.verbaleTestoHtml = verbaleTestoHtml;
	}

	public Boolean getDisattivatoRiordinoAutomatico() {
		return disattivatoRiordinoAutomatico;
	}

	public void setDisattivatoRiordinoAutomatico(Boolean disattivatoRiordinoAutomatico) {
		this.disattivatoRiordinoAutomatico = disattivatoRiordinoAutomatico;
	}
	
}