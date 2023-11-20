/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MailDocumentoIn extends CreaModDocumentoInBean {

	private String idEmail;
	private String classificazione;
	private String account;
	private Integer flgNonArchiviareEmail;

	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}

	public String getIdEmail() {
		return idEmail;
	}

	public String getClassificazione() {
		return classificazione;
	}

	public void setClassificazione(String classificazione) {
		this.classificazione = classificazione;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	
	public Integer getFlgNonArchiviareEmail() {
		return flgNonArchiviareEmail;
	}

	public void setFlgNonArchiviareEmail(Integer flgNonArchiviareEmail) {
		this.flgNonArchiviareEmail = flgNonArchiviareEmail;
	}
	
}
