/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.deleghe.datasource.bean;

import java.util.List;

public class DefinizioneDelegheBean {

	private List<DelegaVsUtenteBean> listaDelegheVsUtenti;

	public List<DelegaVsUtenteBean> getListaDelegheVsUtenti() {
		return listaDelegheVsUtenti;
	}

	public void setListaDelegheVsUtenti(List<DelegaVsUtenteBean> listaDelegheVsUtenti) {
		this.listaDelegheVsUtenti = listaDelegheVsUtenti;
	}
	
}
