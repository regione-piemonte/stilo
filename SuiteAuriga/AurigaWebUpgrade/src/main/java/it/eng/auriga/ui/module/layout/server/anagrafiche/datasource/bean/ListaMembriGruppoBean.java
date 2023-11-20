/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

public class ListaMembriGruppoBean {

	private String idMailingList;	
	private List<AnagraficaRubricaEmailBean> listaMembri;
	
	public String getIdMailingList() {
		return idMailingList;
	}
	public void setIdMailingList(String idMailingList) {
		this.idMailingList = idMailingList;
	}
	public List<AnagraficaRubricaEmailBean> getListaMembri() {
		return listaMembri;
	}
	public void setListaMembri(List<AnagraficaRubricaEmailBean> listaMembri) {
		this.listaMembri = listaMembri;
	}	
		
}
