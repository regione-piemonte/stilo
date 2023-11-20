/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class PasswordScadutaBean {

	private String newPassword;
	private String oldPassword;
	private String confermaPassword;
	private Boolean changeOk = false;

	public void setNewPassword(String password) {
		this.newPassword = password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setChangeOk(Boolean changeOk) {
		this.changeOk = changeOk;
	}

	public Boolean getChangeOk() {
		return changeOk;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setConfermaPassword(String confermaPassword) {
		this.confermaPassword = confermaPassword;
	}

	public String getConfermaPassword() {
		return confermaPassword;
	}
}
