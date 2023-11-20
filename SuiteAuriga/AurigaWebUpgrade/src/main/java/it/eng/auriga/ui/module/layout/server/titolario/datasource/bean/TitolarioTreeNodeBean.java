/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import it.eng.utility.ui.module.core.shared.bean.TreeNodeBean;

public class TitolarioTreeNodeBean extends TreeNodeBean {	
	
	private String indice;
	
	private String flgAttiva;

	public String getIndice() {
		return indice;
	}

	public void setIndice(String indice) {
		this.indice = indice;
	}

	public String getFlgAttiva() {
		return flgAttiva;
	}

	public void setFlgAttiva(String flgAttiva) {
		this.flgAttiva = flgAttiva;
	}
		
}
