/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean;

import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

public class CompilaModelloAttivitaBean extends ModelloAttivitaBean {
	
	private String idUd;
	private String estremiProtUd;
	private String esitoRollbackNumDefAtti;
	
	private String uriFileGenerato;
	private MimeTypeFirmaBean infoFileGenerato;

	public String getIdUd() {
		return idUd;
	}
	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}
	public String getEstremiProtUd() {
		return estremiProtUd;
	}
	public void setEstremiProtUd(String estremiProtUd) {
		this.estremiProtUd = estremiProtUd;
	}
	public String getEsitoRollbackNumDefAtti() {
		return esitoRollbackNumDefAtti;
	}
	public void setEsitoRollbackNumDefAtti(String esitoRollbackNumDefAtti) {
		this.esitoRollbackNumDefAtti = esitoRollbackNumDefAtti;
	}
	public String getUriFileGenerato() {
		return uriFileGenerato;
	}
	public void setUriFileGenerato(String uriFileGenerato) {
		this.uriFileGenerato = uriFileGenerato;
	}
	public MimeTypeFirmaBean getInfoFileGenerato() {
		return infoFileGenerato;
	}
	public void setInfoFileGenerato(MimeTypeFirmaBean infoFileGenerato) {
		this.infoFileGenerato = infoFileGenerato;
	}
	@Override
	public String toString() {
		return "CompilaModelloAttivitaBean [idUd=" + idUd + ", estremiProtUd=" + estremiProtUd + ", esitoRollbackNumDefAtti=" + esitoRollbackNumDefAtti
				+ ", uriFileGenerato=" + uriFileGenerato + ", infoFileGenerato=" + infoFileGenerato + ", getActivityName()=" + getActivityName()
				+ ", getEsitiXGenModello()=" + getEsitiXGenModello() + ", getIdTipoDoc()=" + getIdTipoDoc() + ", getNomeTipoDoc()=" + getNomeTipoDoc()
				+ ", getDescrizione()=" + getDescrizione() + ", getNomeFile()=" + getNomeFile() + ", getFormato()=" + getFormato() + ", getFlgDaFirmare()="
				+ getFlgDaFirmare() + ", getFlgLocked()=" + getFlgLocked() + ", getUri()=" + getUri() + ", getTipoModello()=" + getTipoModello()
				+ ", getFlgParteDispositivo()=" + getFlgParteDispositivo() + ", getIdModello()=" + getIdModello() + ", getNomeModello()=" + getNomeModello()
				+ ", getFlgSkipAnteprima()=" + getFlgSkipAnteprima() + ", getFlgParere()=" + getFlgParere() + ", getFlgNoPubbl()=" + getFlgNoPubbl()
				+ ", getFlgPubblicaSeparato()=" + getFlgPubblicaSeparato() + ", getFlgFirmaAuto()=" + getFlgFirmaAuto() + ", getUserIdFirmaAuto()="
				+ getUserIdFirmaAuto() + ", getPasswordFirmaAuto()=" + getPasswordFirmaAuto() + ", getFlgDelegaFirmaAuto()=" + getFlgDelegaFirmaAuto()
				+ ", getFirmaInDelegaFirmaAuto()=" + getFirmaInDelegaFirmaAuto() + ", getProviderFirmaAuto()=" + getProviderFirmaAuto()
				+ ", getFlgPostAvanzamentoFlusso()=" + getFlgPostAvanzamentoFlusso() + ", getCategoriaNumDaDare()=" + getCategoriaNumDaDare()
				+ ", getSiglaNumDaDare()=" + getSiglaNumDaDare() + ", getFlgCreaNuovoDoc()=" + getFlgCreaNuovoDoc() + ", getFlgFirmaGrafica()="
				+ getFlgFirmaGrafica() + ", getNroPaginaFirmaGrafica()=" + getNroPaginaFirmaGrafica() + ", getNroRigaFirmaGrafica()=" + getNroRigaFirmaGrafica()
				+ ", getNroColonnaFirmaGrafica()=" + getNroColonnaFirmaGrafica() + ", getTestoFirmaGrafica()=" + getTestoFirmaGrafica() + ", toString()="
				+ super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}	
	
}
