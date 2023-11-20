/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import it.eng.core.business.beans.AbstractBean;
import it.eng.document.NumeroColonna;

public class DettagliXlsIndirizziEmailXmlBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1309361463281287462L;

	@NumeroColonna(numero = "1")
	private String campoXls;
	
	@NumeroColonna(numero = "2")
	private String colonnaXls;

	public String getCampoXls() {
		return campoXls;
	}

	public void setCampoXls(String campoXls) {
		this.campoXls = campoXls;
	}

	public String getColonnaXls() {
		return colonnaXls;
	}

	public void setColonnaXls(String colonnaXls) {
		this.colonnaXls = colonnaXls;
	}
	
}
