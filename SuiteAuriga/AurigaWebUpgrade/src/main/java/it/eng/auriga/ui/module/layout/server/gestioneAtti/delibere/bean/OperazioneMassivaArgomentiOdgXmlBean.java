/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.server.common.OperazioneMassivaBean;

import java.io.Serializable;

public class OperazioneMassivaArgomentiOdgXmlBean extends OperazioneMassivaBean<ArgomentiOdgXmlBean> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String organoCollegiale;
	
	private String uriUnioneSchedaSintesi;

	public String getOrganoCollegiale() {
		return organoCollegiale;
	}

	public void setOrganoCollegiale(String organoCollegiale) {
		this.organoCollegiale = organoCollegiale;
	}	
	
	public String getUriUnioneSchedaSintesi() {
		return uriUnioneSchedaSintesi;
	}

	public void setUriUnioneSchedaSintesi(String uriUnioneSchedaSintesi) {
		this.uriUnioneSchedaSintesi = uriUnioneSchedaSintesi;
	}
	
}