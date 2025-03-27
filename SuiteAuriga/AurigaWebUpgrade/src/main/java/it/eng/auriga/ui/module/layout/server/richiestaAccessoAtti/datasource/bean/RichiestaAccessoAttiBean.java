/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.richiestaAccessoAtti.datasource.bean;

public class RichiestaAccessoAttiBean extends RichiestaAccessoAttiXmlBean {
	
	private Boolean prelievoEffettuato;

	public Boolean getPrelievoEffettuato() {
		return prelievoEffettuato;
	}

	public void setPrelievoEffettuato(Boolean prelievoEffettuato) {
		this.prelievoEffettuato = prelievoEffettuato;
	}
		
}
