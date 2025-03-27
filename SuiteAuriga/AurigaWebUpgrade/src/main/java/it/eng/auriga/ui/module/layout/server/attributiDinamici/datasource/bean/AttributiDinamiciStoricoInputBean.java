/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean;

import java.math.BigDecimal;

public class AttributiDinamiciStoricoInputBean {

	private BigDecimal idCatasto;

	public void setIdCatasto(BigDecimal idCatasto) {
		this.idCatasto = idCatasto;
	}

	public BigDecimal getIdCatasto() {
		return idCatasto;
	}	
	


}
