/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.job.registri.bean;

import it.eng.job.registri.bean.ColToPrint;

public class StampaRegProtConfig {

	
	private ColToPrint[] colsToPrint;

	public ColToPrint[] getColsToPrint() {
		return colsToPrint;
	}

	public void setColsToPrint(ColToPrint[] colsToPrint) {
		this.colsToPrint = colsToPrint;
	}
	
}
