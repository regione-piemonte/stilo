/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.richiesteAutotutelaCed.datasource.bean;

import java.util.List;

public class GetListaRichiesteAutotutelaCedBean {

	private List<RichiestaAutotutelaCedBean> listaRichiesteAutotutelaCed;

	public List<RichiestaAutotutelaCedBean> getListaRichiesteAutotutelaCed() {
		return listaRichiesteAutotutelaCed;
	}

	public void setListaRichiesteAutotutelaCed(List<RichiestaAutotutelaCedBean> listaRichiesteAutotutelaCed) {
		this.listaRichiesteAutotutelaCed = listaRichiesteAutotutelaCed;
	}

}
