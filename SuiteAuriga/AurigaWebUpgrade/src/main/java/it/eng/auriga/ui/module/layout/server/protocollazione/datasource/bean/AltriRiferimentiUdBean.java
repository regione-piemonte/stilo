/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

public class AltriRiferimentiUdBean {

	private String idUd;
	private List<AltroRifBean> listaAltriRiferimenti;

	public String getIdUd() {
		return idUd;
	}

	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}

	public List<AltroRifBean> getListaAltriRiferimenti() {
		return listaAltriRiferimenti;
	}

	public void setListaAltriRiferimenti(List<AltroRifBean> listaAltriRiferimenti) {
		this.listaAltriRiferimenti = listaAltriRiferimenti;
	}

}
