/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

public class ListaTipologieDocBean 
{
	private String idUo;
	private List<TipologieDocumentaliBean> listaTipologieDoc;
	
	public String getIdUo() {
		return idUo;
	}
	public void setIdUo(String idUo) {
		this.idUo = idUo;
	}
	public List<TipologieDocumentaliBean> getListaTipologieDoc() {
		return listaTipologieDoc;
	}
	public void setListaTipologieDoc(List<TipologieDocumentaliBean> listaTipologieDoc) {
		this.listaTipologieDoc = listaTipologieDoc;
	}
	

	
}
