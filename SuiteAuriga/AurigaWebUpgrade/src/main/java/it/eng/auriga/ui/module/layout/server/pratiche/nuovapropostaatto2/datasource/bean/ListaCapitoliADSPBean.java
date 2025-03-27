/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean;

import java.util.List;

public class ListaCapitoliADSPBean {
	
	private List<CapitoliADSPBean> listaCapitoliADSP;

	public List<CapitoliADSPBean> getListaCapitoliADSP() {
		return listaCapitoliADSP;
	}

	public void setListaCapitoliADSP(List<CapitoliADSPBean> listaCapitoliADSP) {
		this.listaCapitoliADSP = listaCapitoliADSP;
	}
}
