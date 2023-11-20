/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;


public class UnlockEmailXmlBean {
	
	@NumeroColonna(numero = "1")
	private String idEmail;
	
	@NumeroColonna(numero = "2")
	private String progressivoMessaggio;

	public String getIdEmail() {
		return idEmail;
	}

	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}

	public String getProgressivoMessaggio() {
		return progressivoMessaggio;
	}

	public void setProgressivoMessaggio(String progressivoMessaggio) {
		this.progressivoMessaggio = progressivoMessaggio;
	}
	
}
