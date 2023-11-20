/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.List;
import java.util.Map;

import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AllegatoProtocolloBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

public class ModelliDocBean {
		
	private String idModello;
	private String nomeModello;
	private String desModello;
	private String formatoFile;	
	private String tipoEntitaAssociata;
	private String idEntitaAssociata;
	private String nomeEntitaAssociata;
	private String nomeTabella;	
	private String idDoc;
	private String idDocHtml;
	private String note;
	private Boolean flgProfCompleta;	
	private String uriFileModello;	
	private String nomeFileModello;
	private MimeTypeFirmaBean infoFileModello;
	private Boolean flgGeneraPdf;
	private String tipoModello;
	private Date tsCreazione;
	private String creatoDa;
	private Date tsUltimoAgg;
	private String ultimoAggEffDa;
	private Boolean flgValido;
	private Boolean flgMostraDatiSensibili;
	private Boolean flgMostraOmissisBarrati;
	private List<String> elencoCampiConGestioneOmissisDaIgnorare;
	
	private List<AllegatoProtocolloBean> listaModelli;
	
	// PROFILATURA
	private List<AssociazioniAttributiCustomBean> listaAssociazioniAttributiCustom;
	private String html;
	private Map<String, Object> valori;
	private Map<String, String> tipiValori;
	private Map<String, String> colonneListe;
	
	public String getIdModello() {
		return idModello;
	}
	public void setIdModello(String idModello) {
		this.idModello = idModello;
	}
	public String getNomeModello() {
		return nomeModello;
	}
	public void setNomeModello(String nomeModello) {
		this.nomeModello = nomeModello;
	}
	public String getDesModello() {
		return desModello;
	}
	public void setDesModello(String desModello) {
		this.desModello = desModello;
	}
	public String getFormatoFile() {
		return formatoFile;
	}
	public void setFormatoFile(String formatoFile) {
		this.formatoFile = formatoFile;
	}
	public String getTipoEntitaAssociata() {
		return tipoEntitaAssociata;
	}
	public void setTipoEntitaAssociata(String tipoEntitaAssociata) {
		this.tipoEntitaAssociata = tipoEntitaAssociata;
	}
	public String getIdEntitaAssociata() {
		return idEntitaAssociata;
	}
	public void setIdEntitaAssociata(String idEntitaAssociata) {
		this.idEntitaAssociata = idEntitaAssociata;
	}
	public String getNomeEntitaAssociata() {
		return nomeEntitaAssociata;
	}
	public void setNomeEntitaAssociata(String nomeEntitaAssociata) {
		this.nomeEntitaAssociata = nomeEntitaAssociata;
	}
	public String getNomeTabella() {
		return nomeTabella;
	}
	public void setNomeTabella(String nomeTabella) {
		this.nomeTabella = nomeTabella;
	}
	public String getIdDoc() {
		return idDoc;
	}
	public void setIdDoc(String idDoc) {
		this.idDoc = idDoc;
	}
	public String getIdDocHtml() {
		return idDocHtml;
	}
	public void setIdDocHtml(String idDocHtml) {
		this.idDocHtml = idDocHtml;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Boolean getFlgProfCompleta() {
		return flgProfCompleta;
	}
	public void setFlgProfCompleta(Boolean flgProfCompleta) {
		this.flgProfCompleta = flgProfCompleta;
	}
	public String getUriFileModello() {
		return uriFileModello;
	}
	public void setUriFileModello(String uriFileModello) {
		this.uriFileModello = uriFileModello;
	}
	public String getNomeFileModello() {
		return nomeFileModello;
	}
	public void setNomeFileModello(String nomeFileModello) {
		this.nomeFileModello = nomeFileModello;
	}
	public MimeTypeFirmaBean getInfoFileModello() {
		return infoFileModello;
	}
	public void setInfoFileModello(MimeTypeFirmaBean infoFileModello) {
		this.infoFileModello = infoFileModello;
	}
	public Boolean getFlgGeneraPdf() {
		return flgGeneraPdf;
	}
	public void setFlgGeneraPdf(Boolean flgGeneraPdf) {
		this.flgGeneraPdf = flgGeneraPdf;
	}
	public String getTipoModello() {
		return tipoModello;
	}
	public void setTipoModello(String tipoModello) {
		this.tipoModello = tipoModello;
	}
	public Date getTsCreazione() {
		return tsCreazione;
	}
	public void setTsCreazione(Date tsCreazione) {
		this.tsCreazione = tsCreazione;
	}
	public String getCreatoDa() {
		return creatoDa;
	}
	public void setCreatoDa(String creatoDa) {
		this.creatoDa = creatoDa;
	}
	public Date getTsUltimoAgg() {
		return tsUltimoAgg;
	}
	public void setTsUltimoAgg(Date tsUltimoAgg) {
		this.tsUltimoAgg = tsUltimoAgg;
	}
	public String getUltimoAggEffDa() {
		return ultimoAggEffDa;
	}
	public void setUltimoAggEffDa(String ultimoAggEffDa) {
		this.ultimoAggEffDa = ultimoAggEffDa;
	}
	public Boolean getFlgValido() {
		return flgValido;
	}
	public void setFlgValido(Boolean flgValido) {
		this.flgValido = flgValido;
	}
	public Boolean getFlgMostraDatiSensibili() {
		return flgMostraDatiSensibili;
	}
	public void setFlgMostraDatiSensibili(Boolean flgMostraDatiSensibili) {
		this.flgMostraDatiSensibili = flgMostraDatiSensibili;
	}
	public Boolean getFlgMostraOmissisBarrati() {
		return flgMostraOmissisBarrati;
	}
	public void setFlgMostraOmissisBarrati(Boolean flgMostraOmissisBarrati) {
		this.flgMostraOmissisBarrati = flgMostraOmissisBarrati;
	}	
	public List<String> getElencoCampiConGestioneOmissisDaIgnorare() {
		return elencoCampiConGestioneOmissisDaIgnorare;
	}
	public void setElencoCampiConGestioneOmissisDaIgnorare(List<String> elencoCampiConGestioneOmissisDaIgnorare) {
		this.elencoCampiConGestioneOmissisDaIgnorare = elencoCampiConGestioneOmissisDaIgnorare;
	}
	public List<AllegatoProtocolloBean> getListaModelli() {
		return listaModelli;
	}
	public void setListaModelli(List<AllegatoProtocolloBean> listaModelli) {
		this.listaModelli = listaModelli;
	}
	public List<AssociazioniAttributiCustomBean> getListaAssociazioniAttributiCustom() {
		return listaAssociazioniAttributiCustom;
	}
	public void setListaAssociazioniAttributiCustom(
			List<AssociazioniAttributiCustomBean> listaAssociazioniAttributiCustom) {
		this.listaAssociazioniAttributiCustom = listaAssociazioniAttributiCustom;
	}
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	public Map<String, Object> getValori() {
		return valori;
	}
	public void setValori(Map<String, Object> valori) {
		this.valori = valori;
	}
	public Map<String, String> getTipiValori() {
		return tipiValori;
	}
	public void setTipiValori(Map<String, String> tipiValori) {
		this.tipiValori = tipiValori;
	}
	public Map<String, String> getColonneListe() {
		return colonneListe;
	}
	public void setColonneListe(Map<String, String> colonneListe) {
		this.colonneListe = colonneListe;
	}
	
}