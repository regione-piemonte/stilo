/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.beans.AbstractBean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class InfoNotificheBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum EsitoInvio {
		OK, ERROR_INVIO, GENERIC_PROBLEM
	}

	private EsitoInvio esito;

	public EsitoInvio getEsito() {
		return esito;
	}

	public void setEsito(EsitoInvio esito) {
		this.esito = esito;
		this.getUpdatedProperties().add("esito");
	}

}
