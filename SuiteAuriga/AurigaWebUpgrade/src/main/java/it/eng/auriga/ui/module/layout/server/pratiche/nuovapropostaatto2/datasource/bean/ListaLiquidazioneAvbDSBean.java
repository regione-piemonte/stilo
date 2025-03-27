/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean;

import java.util.List;

public class ListaLiquidazioneAvbDSBean {
	
	private List<LiquidazioneAvbDSBean> listaDati;

	public List<LiquidazioneAvbDSBean> getListaDati() {
		return listaDati;
	}

	public void setListaDati(List<LiquidazioneAvbDSBean> listaDati) {
		this.listaDati = listaDati;
	}

}
