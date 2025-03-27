/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean;

import java.util.List;

public class ListaAnagraficaSicraBean {
	
	private List<RicercaAnagraficaSicraBean> listaAnagraficaSicra;

	public List<RicercaAnagraficaSicraBean> getListaAnagraficaSicra() {
		return listaAnagraficaSicra;
	}

	public void setListaAnagraficaSicra(List<RicercaAnagraficaSicraBean> listaAnagraficaSicra) {
		this.listaAnagraficaSicra = listaAnagraficaSicra;
	}
}
