/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SibOutputCreaProposta implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idPropostaDetermina;
	private boolean ok;
	private String messaggio;
	private boolean timeout;
	private boolean rispostaNonRicevuta;

	public Long getIdPropostaDetermina() {
		return idPropostaDetermina;
	}

	public void setIdPropostaDetermina(Long idPropostaDetermina) {
		this.idPropostaDetermina = idPropostaDetermina;
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public String getMessaggio() {
		return messaggio;
	}

	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
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
