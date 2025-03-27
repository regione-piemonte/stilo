/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.document.function.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PubblicazioneResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private boolean ok;
	private String result;
	private String status;
	private String errorDescription;
	
	public boolean isOk() {
		return ok;
	}
	
	public void setOk(boolean ok) {
		this.ok = ok;
	}
	
	public String getResult() {
		return result;
	}
	
	public void setResult(String result) {
		this.result = result;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getErrorDescription() {
		return errorDescription;
	}
	
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	@Override
	public String toString() {
		return "PubblicazioneResponse [ok=" + ok + ", result=" + result + ", status=" + status + ", errorDescription="
				+ errorDescription + "]";
	}
	
}
