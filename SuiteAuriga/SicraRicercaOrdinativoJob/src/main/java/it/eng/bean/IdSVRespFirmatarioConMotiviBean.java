/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class IdSVRespFirmatarioConMotiviBean {

	@NumeroColonna(numero = "1")
	private String idSV;

	@NumeroColonna(numero = "2")
	private Boolean flgFirmatario;

	@NumeroColonna(numero = "3")
	private String motivi;

	public String getIdSV() {
		return idSV;
	}

	public void setIdSV(String idSV) {
		this.idSV = idSV;
	}

	public Boolean getFlgFirmatario() {
		return flgFirmatario;
	}

	public void setFlgFirmatario(Boolean flgFirmatario) {
		this.flgFirmatario = flgFirmatario;
	}

	public String getMotivi() {
		return motivi;
	}

	public void setMotivi(String motivi) {
		this.motivi = motivi;
	}
		
}
