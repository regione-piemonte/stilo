/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

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
	
}
