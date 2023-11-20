/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import it.eng.document.NumeroColonna;

/**
 * 
 * @author DANCRIST
 *
 */

public class SoggettoAssegnazioneVisureBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@NumeroColonna(numero = "4")
	private String flgUoSvUt;
	
	@NumeroColonna(numero = "5")
	private String idUoSvUt;
	
	public String getFlgUoSvUt() {
		return flgUoSvUt;
	}
	public void setFlgUoSvUt(String flgUoSvUt) {
		this.flgUoSvUt = flgUoSvUt;
	}
	public String getIdUoSvUt() {
		return idUoSvUt;
	}
	public void setIdUoSvUt(String idUoSvUt) {
		this.idUoSvUt = idUoSvUt;
	}
		
}