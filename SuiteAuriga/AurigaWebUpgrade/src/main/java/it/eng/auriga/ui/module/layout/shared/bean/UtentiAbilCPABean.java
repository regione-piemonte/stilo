/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.UtenteBean;

import java.util.List;

public class UtentiAbilCPABean {

	private List<UtenteBean> utentiAbilCPAList;

	public List<UtenteBean> getUtentiAbilCPAList() {
		return utentiAbilCPAList;
	}

	public void setUtentiAbilCPAList(List<UtenteBean> utentiAbilCPAList) {
		this.utentiAbilCPAList = utentiAbilCPAList;
	}

}
