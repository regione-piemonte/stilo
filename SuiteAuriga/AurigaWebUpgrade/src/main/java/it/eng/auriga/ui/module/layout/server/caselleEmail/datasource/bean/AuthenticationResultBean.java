/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


public class AuthenticationResultBean {
	
	private boolean authenticationOK;
	private String msgError;
	private int  messageCountInBox;
	
	public int getMessageCountInBox() {
		return messageCountInBox;
	}
	public void setMessageCountInBox(int messageCountInBox) {
		this.messageCountInBox = messageCountInBox;
	}
	public String getMsgError() {
		return msgError;
	}
	public void setMsgError(String msgError) {
		this.msgError = msgError;
	}
	public boolean isAuthenticationOK() {
		return authenticationOK;
	}
	public void setAuthenticationOK(boolean authenticationOK) {
		this.authenticationOK = authenticationOK;
	}
}

