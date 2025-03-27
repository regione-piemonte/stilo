/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean;

public class ErroreRigaExcelBean {
	
	private String numeroRiga;
	private String motivo;
	
	public String getNumeroRiga() {
		return numeroRiga;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setNumeroRiga(String numeroRiga) {
		this.numeroRiga = numeroRiga;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}	
	
}
