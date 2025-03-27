/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean;

import it.eng.document.function.bean.MovimentiContabiliSICRAXmlBean;

public class MovimentiContabiliSICRABean extends MovimentiContabiliSICRAXmlBean {

	private String id;
	private Boolean skipCtrlDisp;
	private String randomUUID;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public Boolean getSkipCtrlDisp() {
		return skipCtrlDisp;
	}

	public void setSkipCtrlDisp(Boolean skipCtrlDisp) {
		this.skipCtrlDisp = skipCtrlDisp;
	}

	public String getRandomUUID() {
		return randomUUID;
	}

	public void setRandomUUID(String randomUUID) {
		this.randomUUID = randomUUID;
	}
	
}
