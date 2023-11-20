/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SibOutputElencoDettagliContabili implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<SibRecordDettagliContabili> list;
	private boolean ok;
	private String messaggio;
	private boolean timeout;
	private boolean rispostaNonRicevuta;

	public List<SibRecordDettagliContabili> getList() {
		return list;
	}

	public void setList(List<SibRecordDettagliContabili> list) {
		this.list = list;
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
