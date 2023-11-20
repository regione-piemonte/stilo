/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.HashMap;

import it.eng.core.business.beans.AbstractBean;

public class EsitoValidazioneBean extends AbstractBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Boolean esitoValidazione;
	private HashMap<String, String> errorMessages;
	
	public Boolean getEsitoValidazione() {
		return esitoValidazione;
	}
	public void setEsitoValidazione(Boolean esitoValidazione) {
		this.esitoValidazione = esitoValidazione;
	}
	public HashMap<String, String> getErrorMessages() {
		return errorMessages;
	}
	public void setErrorMessages(HashMap<String, String> errorMessages) {
		this.errorMessages = errorMessages;
	}
	
}
