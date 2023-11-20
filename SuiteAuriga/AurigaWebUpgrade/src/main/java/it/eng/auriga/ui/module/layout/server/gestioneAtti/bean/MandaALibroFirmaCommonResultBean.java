/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;

public class MandaALibroFirmaCommonResultBean {

	private HashMap<String, String> errorMessages;
	private boolean effettuataNumerazione;

	public HashMap<String, String> getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(HashMap<String, String> errorMessages) {
		this.errorMessages = errorMessages;
	}

	public boolean isEffettuataNumerazione() {
		return effettuataNumerazione;
	}

	public void setEffettuataNumerazione(boolean effettuataNumerazione) {
		this.effettuataNumerazione = effettuataNumerazione;
	}

}