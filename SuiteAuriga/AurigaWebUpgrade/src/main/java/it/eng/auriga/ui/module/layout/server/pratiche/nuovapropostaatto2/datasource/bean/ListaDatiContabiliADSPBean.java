/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean;

import java.util.List;

import it.eng.document.function.bean.DatiContabiliADSPXmlBean;

public class ListaDatiContabiliADSPBean {

	private List<DatiContabiliADSPXmlBean> listaDatiContabiliADSP;

	public List<DatiContabiliADSPXmlBean> getListaDatiContabiliADSP() {
		return listaDatiContabiliADSP;
	}

	public void setListaDatiContabiliADSP(List<DatiContabiliADSPXmlBean> listaDatiContabiliADSP) {
		this.listaDatiContabiliADSP = listaDatiContabiliADSP;
	}
	
}
