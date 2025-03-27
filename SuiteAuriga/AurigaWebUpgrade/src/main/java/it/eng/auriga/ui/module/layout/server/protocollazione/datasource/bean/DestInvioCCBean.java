/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean;

public class DestInvioCCBean extends DestInvioBean {	
	
	private OpzioniInvioCCBean opzioniInvio;

	public OpzioniInvioCCBean getOpzioniInvio() {
		return opzioniInvio;
	}

	public void setOpzioniInvio(OpzioniInvioCCBean opzioniInvio) {
		this.opzioniInvio = opzioniInvio;
	}
	
}
