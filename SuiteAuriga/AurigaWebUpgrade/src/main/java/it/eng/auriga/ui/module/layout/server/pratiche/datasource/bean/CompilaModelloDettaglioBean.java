/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean;

public class CompilaModelloDettaglioBean<T> extends CompilaModelloBean {

	private T dettaglioBean;

	public T getDettaglioBean() {
		return dettaglioBean;
	}

	public void setDettaglioBean(T dettaglioBean) {
		this.dettaglioBean = dettaglioBean;
	}

}
