/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabilitaAdspResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private boolean ok;
	private String tipoMsg;
	private String msg;
	
	public boolean isOk() {
		return ok;
	}
	
	public void setOk(boolean ok) {
		this.ok = ok;
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
		return "ContabilitaAdspResponse [ok=" + ok + ", tipoMsg=" + tipoMsg + ", msg=" + msg + "]";
	}
	
}
