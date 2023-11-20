/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

public class ListaDestVantaggioBean {
	
	private List<DestVantaggioBean> listaDestVantaggioBean;
	
	private List<ErroreRigaExcelBean> listaErroreRigaExcelBean;

	public List<DestVantaggioBean> getListaDestVantaggioBean() {
		return listaDestVantaggioBean;
	}

	public void setListaDestVantaggioBean(List<DestVantaggioBean> listaDestVantaggioBean) {
		this.listaDestVantaggioBean = listaDestVantaggioBean;
	}

	public List<ErroreRigaExcelBean> getListaErroreRigaExcelBean() {
		return listaErroreRigaExcelBean;
	}

	public void setListaErroreRigaExcelBean(List<ErroreRigaExcelBean> listaErroreRigaExcelBean) {
		this.listaErroreRigaExcelBean = listaErroreRigaExcelBean;
	}
	
}