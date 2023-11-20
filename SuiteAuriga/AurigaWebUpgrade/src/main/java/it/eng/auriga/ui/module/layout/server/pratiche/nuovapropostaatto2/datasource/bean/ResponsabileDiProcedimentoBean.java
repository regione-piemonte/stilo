/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.server.organigramma.datasource.bean.SelezionaScrivaniaBean;

public class ResponsabileDiProcedimentoBean extends SelezionaScrivaniaBean {
	
	private Boolean flgRdPAncheRUP;

	public Boolean getFlgRdPAncheRUP() {
		return flgRdPAncheRUP;
	}

	public void setFlgRdPAncheRUP(Boolean flgRdPAncheRUP) {
		this.flgRdPAncheRUP = flgRdPAncheRUP;
	}
	
}
