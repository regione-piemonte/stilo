/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import it.eng.utility.ui.module.core.shared.bean.VisualBean;

public class SelezionaProcOrganigrammaBean extends VisualBean {

	@NumeroColonna(numero = "1")
	private String idProcessType;
	@NumeroColonna(numero = "2")
	private String nomeProcessType;
	@NumeroColonna(numero = "3")
	private String idDocType;
	@NumeroColonna(numero = "4")
	private String nomeDocType;
	
	public String getIdProcessType() {
		return idProcessType;
	}
	public void setIdProcessType(String idProcessType) {
		this.idProcessType = idProcessType;
	}
	public String getNomeProcessType() {
		return nomeProcessType;
	}
	public void setNomeProcessType(String nomeProcessType) {
		this.nomeProcessType = nomeProcessType;
	}
	public String getIdDocType() {
		return idDocType;
	}
	public void setIdDocType(String idDocType) {
		this.idDocType = idDocType;
	}
	public String getNomeDocType() {
		return nomeDocType;
	}
	public void setNomeDocType(String nomeDocType) {
		this.nomeDocType = nomeDocType;
	}
	
}
