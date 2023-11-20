/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import it.eng.auriga.ui.module.layout.server.common.OperazioneMassivaBean;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;

public class OperazioneMassivaArchivioBean extends OperazioneMassivaBean<ArchivioBean> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String mezziTrasmissione;
	private List<SimpleKeyValueBean> listaUoSelezionate;
	private String flgAnchePerUtente;
	private String idNodo;

	public String getMezziTrasmissione() {
		return mezziTrasmissione;
	}

	public void setMezziTrasmissione(String mezziTrasmissione) {
		this.mezziTrasmissione = mezziTrasmissione;
	}

	public List<SimpleKeyValueBean> getListaUoSelezionate() {
		return listaUoSelezionate;
	}

	public void setListaUoSelezionate(List<SimpleKeyValueBean> listaUoSelezionate) {
		this.listaUoSelezionate = listaUoSelezionate;
	}

	public String getFlgAnchePerUtente() {
		return flgAnchePerUtente;
	}

	public void setFlgAnchePerUtente(String flgAnchePerUtente) {
		this.flgAnchePerUtente = flgAnchePerUtente;
	}

	public String getIdNodo() {
		return idNodo;
	}

	public void setIdNodo(String idNodo) {
		this.idNodo = idNodo;
	}
	
}
