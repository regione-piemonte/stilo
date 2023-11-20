/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

public class DocumentiCollegatiUdBean {
	
	private String idUd;
	private List<DocCollegatoBean> listaDocumentiCollegati;
	
	public String getIdUd() {
		return idUd;
	}
	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}
	public List<DocCollegatoBean> getListaDocumentiCollegati() {
		return listaDocumentiCollegati;
	}
	public void setListaDocumentiCollegati(List<DocCollegatoBean> listaDocumentiCollegati) {
		this.listaDocumentiCollegati = listaDocumentiCollegati;
	}
	
}
