/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabilitaAdspTestataResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private boolean ok;
	private Integer esitoOperazione;
	private String tipoMsg;
	private String msg;
	
	public boolean isOk() {
		return ok;
	}
	
	public void setOk(boolean ok) {
		this.ok = ok;
	}
	
	public Integer getEsitoOperazione() {
		return esitoOperazione;
	}

	public void setEsitoOperazione(Integer esitoOperazione) {
		this.esitoOperazione = esitoOperazione;
	}

	public String getTipoMsg() {
		return tipoMsg;
	}

	public void setTipoMsg(String tipoMsg) {
		this.tipoMsg = tipoMsg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "ContabilitaAdspTestataResponse [ok=" + ok + ", esitoOperazione=" + esitoOperazione + ", tipoMsg="
				+ tipoMsg + ", msg=" + msg + "]";
	}
	
}
