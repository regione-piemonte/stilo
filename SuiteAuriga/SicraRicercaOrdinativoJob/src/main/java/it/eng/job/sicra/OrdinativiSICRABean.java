/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.job.sicra;


public class OrdinativiSICRABean extends OrdinativiSICRAXmlBean {

	private String id;
	private Boolean skipCtrlDisp;
	
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
	
}
