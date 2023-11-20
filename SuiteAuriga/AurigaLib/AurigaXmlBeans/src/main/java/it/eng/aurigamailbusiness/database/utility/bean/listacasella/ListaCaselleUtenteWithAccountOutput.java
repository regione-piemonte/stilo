/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ListaCaselleUtenteWithAccountOutput implements Serializable {

	private static final long serialVersionUID = -8595056421459098647L;
	private Map<String, String> accounts;
	public void setAccounts(Map<String, String> accounts) {
		this.accounts = accounts;
	}
	public Map<String, String> getAccounts() {
		return accounts;
	}
}
