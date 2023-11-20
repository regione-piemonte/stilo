/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

public class JobDebugConfig implements Serializable {

	private static final long serialVersionUID = 80941552362789589L;

	private Boolean debugAttivo = false;
	private Boolean elabora = true;

	public Boolean getDebugAttivo() {
		return debugAttivo;
	}

	public void setDebugAttivo(Boolean debugAttivo) {
		this.debugAttivo = debugAttivo;
	}

	public Boolean getElabora() {
		return elabora;
	}

	public void setElabora(Boolean elabora) {
		this.elabora = elabora;
	}

	

}
