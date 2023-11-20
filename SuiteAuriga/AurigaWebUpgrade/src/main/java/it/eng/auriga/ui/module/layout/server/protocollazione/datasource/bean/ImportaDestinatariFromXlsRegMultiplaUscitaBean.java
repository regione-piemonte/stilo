/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

public class ImportaDestinatariFromXlsRegMultiplaUscitaBean {
	
	private List<FileXlsDestinatariRegMultiplaUscitaBean> listaFileXls;
	private List<DestinatariXFileXlsRegMultiplaUscitaBean> listaDestinatariXFileXls = new ArrayList<DestinatariXFileXlsRegMultiplaUscitaBean>();

	public List<FileXlsDestinatariRegMultiplaUscitaBean> getListaFileXls() {
		return listaFileXls;
	}

	public void setListaFileXls(List<FileXlsDestinatariRegMultiplaUscitaBean> listaFileXls) {
		this.listaFileXls = listaFileXls;
	}

	public List<DestinatariXFileXlsRegMultiplaUscitaBean> getListaDestinatariXFileXls() {
		return listaDestinatariXFileXls;
	}

	public void setListaDestinatariXFileXls(List<DestinatariXFileXlsRegMultiplaUscitaBean> listaDestinatariXFileXls) {
		this.listaDestinatariXFileXls = listaDestinatariXFileXls;
	}

}
