/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class VersionaDocumentiInTransactionInBean implements Serializable {

	private static final long serialVersionUID = -3904140374378916971L;
	
	private List<VersionaDocumentoInBean> listaDocumenti;
	
	public List<VersionaDocumentoInBean> getListaDocumenti() {
		return listaDocumenti;
	}

	public void setListaDocumenti(List<VersionaDocumentoInBean> listaDocumenti) {
		this.listaDocumenti = listaDocumenti;
	}
	
}

