/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

public class ProvvedimentoOutput implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private boolean result;
	private String errorMsg;
	
	public boolean isResult() {
		return result;
	}
	
	public void setResult(boolean result) {
		this.result = result;
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}
	
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	@Override
	public String toString() {
		return "ProvvedimentoOutput [result=" + result + ", errorMsg=" + errorMsg + "]";
	}
	
}
