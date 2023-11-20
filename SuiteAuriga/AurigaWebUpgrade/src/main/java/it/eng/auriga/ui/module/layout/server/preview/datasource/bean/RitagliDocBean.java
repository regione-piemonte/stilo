/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

public class RitagliDocBean {
	
	private int numeroPagina;
	
	private List<RitaglioBean> ritaglioBean;

	public int getNumeroPagina() {
		return numeroPagina;
	}

	public void setNumeroPagina(int numeroPagina) {
		this.numeroPagina = numeroPagina;
	}

	public List<RitaglioBean> getRitaglioBean() {
		return ritaglioBean;
	}

	public void setRitaglioBean(List<RitaglioBean> ritaglioBean) {
		this.ritaglioBean = ritaglioBean;
	}
	

}
