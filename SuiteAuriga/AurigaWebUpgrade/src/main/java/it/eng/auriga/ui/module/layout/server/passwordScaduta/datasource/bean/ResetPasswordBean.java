/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */



public class ResetPasswordBean {

	private String newPassword;
	private String emailUtente;
	private Boolean changeOk = false;
	
	private String errorMessages;
	
	public String getNewPassword() {
		return newPassword;
	}
	public String getEmailUtente() {
		return emailUtente;
	}
	public Boolean getChangeOk() {
		return changeOk;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public void setEmailUtente(String emailUtente) {
		this.emailUtente = emailUtente;
	}
	public void setChangeOk(Boolean changeOk) {
		this.changeOk = changeOk;
	}
	public String getErrorMessages() {
		return errorMessages;
	}
	public void setErrorMessages(String errorMessages) {
		this.errorMessages = errorMessages;
	}

	
}
