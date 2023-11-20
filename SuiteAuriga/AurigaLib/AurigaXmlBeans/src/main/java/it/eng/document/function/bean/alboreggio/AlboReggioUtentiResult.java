/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlboReggioUtentiResult implements Serializable {

	private static final long serialVersionUID = 1L;
	private boolean ok;
	private String message;
	private boolean timeout;
	private boolean rispostaNonRicevuta;
	private AlboReggioUtente[] payload;

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public AlboReggioUtente[] getPayload() {
		return payload;
	}

	public void setPayload(AlboReggioUtente[] payload) {
		this.payload = payload;
	}

	public boolean isTimeout() {
		return timeout;
	}

	public void setTimeout(boolean timeout) {
		this.timeout = timeout;
	}

	public boolean isRispostaNonRicevuta() {
		return rispostaNonRicevuta;
	}

	public void setRispostaNonRicevuta(boolean rispostaNonRicevuta) {
		this.rispostaNonRicevuta = rispostaNonRicevuta;
	}

}
