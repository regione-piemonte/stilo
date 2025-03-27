/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.gestioneContenutiAmministrazioneTrasparente.datasource.bean;

import java.util.List;

/**
 * 
 * @author dbe4235
 *
 */

public class RiordinaContenutiSezioneBean {
	
	private String idSezione;
	
	private List<ContenutiDaRiordinareXmlBean> listContenuti;

	public String getIdSezione() {
		return idSezione;
	}

	public void setIdSezione(String idSezione) {
		this.idSezione = idSezione;
	}

	public List<ContenutiDaRiordinareXmlBean> getListContenuti() {
		return listContenuti;
	}

	public void setListContenuti(List<ContenutiDaRiordinareXmlBean> listContenuti) {
		this.listContenuti = listContenuti;
	}

}
