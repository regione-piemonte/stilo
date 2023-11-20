/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.IndirizzoSoggettoProtBean;

/**
 * 
 * @author cristiano
 *
 */

public class CtrlDatiIndirizzoBean extends IndirizzoSoggettoProtBean {

	private Boolean esito;

	/**
	 * @return the esito
	 */
	public Boolean getEsito() {
		return esito;
	}

	/**
	 * @param esito
	 *            the esito to set
	 */
	public void setEsito(Boolean esito) {
		this.esito = esito;
	}
}
