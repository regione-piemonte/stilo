/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.server.common.OperazioneMassivaBean;

import java.io.Serializable;

public class OperazioneMassivaIstanzePortaleRiscossioneBean extends OperazioneMassivaBean<IstanzePortaleRiscossioneBean> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String mezziTrasmissione;

	public String getMezziTrasmissione() {
		return mezziTrasmissione;
	}

	public void setMezziTrasmissione(String mezziTrasmissione) {
		this.mezziTrasmissione = mezziTrasmissione;
	}
	
	
}
