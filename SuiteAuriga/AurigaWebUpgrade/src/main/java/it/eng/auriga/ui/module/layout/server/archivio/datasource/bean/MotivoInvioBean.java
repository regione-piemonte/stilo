/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.archivio.datasource.bean;

import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;

public class MotivoInvioBean extends SimpleKeyValueBean {

	private String flgSpeciale;	

	public String getFlgSpeciale() {
		return flgSpeciale;
	}
	public void setFlgSpeciale(String flgSpeciale) {
		this.flgSpeciale = flgSpeciale;
	}

}
