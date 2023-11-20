/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

public class FascicoliCollegatiBean {
	
	private String idFolder;
	private List<FascCollegatoBean> listaFascicoliCollegati;
	public String getIdFolder() {
		return idFolder;
	}
	public void setIdFolder(String idFolder) {
		this.idFolder = idFolder;
	}
	public List<FascCollegatoBean> getListaFascicoliCollegati() {
		return listaFascicoliCollegati;
	}
	public void setListaFascicoliCollegati(List<FascCollegatoBean> listaFascicoliCollegati) {
		this.listaFascicoliCollegati = listaFascicoliCollegati;
	}
}