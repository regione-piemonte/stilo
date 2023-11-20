/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class BodyEmailMatch {
	
	@NumeroColonna(numero = "1")
	private String bodyEmail;

	public String getBodyEmail() {
		return bodyEmail;
	}

	public void setBodyEmail(String bodyEmail) {
		this.bodyEmail = bodyEmail;
	}

}
