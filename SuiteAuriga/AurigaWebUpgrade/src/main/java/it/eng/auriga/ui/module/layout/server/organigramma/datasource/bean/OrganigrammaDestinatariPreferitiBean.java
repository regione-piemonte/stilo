/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.organigramma.datasource.bean;

public class OrganigrammaDestinatariPreferitiBean {

	private String tipo;
	private String idDestPref;
	private String flgAddDel;
	
	
	public String getIdDestPref() {
		return idDestPref;
	}
	public void setIdDestPref(String idDestPref) {
		this.idDestPref = idDestPref;
	}
	public String getFlgAddDel() {
		return flgAddDel;
	}
	public void setFlgAddDel(String flgAddDel) {
		this.flgAddDel = flgAddDel;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
}
