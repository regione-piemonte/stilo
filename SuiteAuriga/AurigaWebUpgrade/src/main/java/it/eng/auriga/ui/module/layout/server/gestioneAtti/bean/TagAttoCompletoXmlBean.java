/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

/**
 * 
 * @author DANCRIST
 *
 */

public class TagAttoCompletoXmlBean {

	@NumeroColonna(numero ="1")
	private String idUd;
	
	@NumeroColonna(numero ="2")
	private String idProcess;

	public String getIdUd() {
		return idUd;
	}

	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}

	public String getIdProcess() {
		return idProcess;
	}

	public void setIdProcess(String idProcess) {
		this.idProcess = idProcess;
	}
	
}