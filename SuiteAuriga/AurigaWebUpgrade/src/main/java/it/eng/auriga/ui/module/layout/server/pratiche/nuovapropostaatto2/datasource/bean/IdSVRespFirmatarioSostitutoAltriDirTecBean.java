/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class IdSVRespFirmatarioSostitutoAltriDirTecBean extends IdSVRespFirmatarioBean {
	
	@NumeroColonna(numero = "4")
	private String idSVSostituto;
	
	@NumeroColonna(numero = "5")
	private String provvedimentoSostituzione;

	public String getIdSVSostituto() {
		return idSVSostituto;
	}

	public void setIdSVSostituto(String idSVSostituto) {
		this.idSVSostituto = idSVSostituto;
	}

	public String getProvvedimentoSostituzione() {
		return provvedimentoSostituzione;
	}

	public void setProvvedimentoSostituzione(String provvedimentoSostituzione) {
		this.provvedimentoSostituzione = provvedimentoSostituzione;
	}

}
