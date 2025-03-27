/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.gestioneProcedimenti.avvioProcedimento.datasource;

import java.util.List;

public class AlberoProcessoOut {

	private List<AlberoProcessoBean> nodi;

	public List<AlberoProcessoBean> getNodi() {
		return nodi;
	}

	public void setNodi(List<AlberoProcessoBean> nodi) {
		this.nodi = nodi;
	}
}
