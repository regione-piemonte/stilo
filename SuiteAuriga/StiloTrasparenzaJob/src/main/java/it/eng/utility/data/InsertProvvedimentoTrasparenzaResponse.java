/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

public class InsertProvvedimentoTrasparenzaResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private boolean esito;
	private int status;
	private String responseMsg;
	
	public boolean isEsito() {
		return esito;
	}
	
	public void setEsito(boolean esito) {
		this.esito = esito;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getResponseMsg() {
		return responseMsg;
	}
	
	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}
	
	
	@Override
	public String toString() {
		return "InsertProvvedimentoTrasparenzaResponse [esito=" + esito + ", status=" + status + ", responseMsg="
				+ responseMsg + "]";
	}
	
}
