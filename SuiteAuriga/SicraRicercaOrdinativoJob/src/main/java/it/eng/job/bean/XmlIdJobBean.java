/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;

import it.eng.document.NumeroColonna;

public class XmlIdJobBean {
	
	@NumeroColonna(numero = "1")
	private BigDecimal idJob;

	public BigDecimal getIdJob() {
		return idJob;
	}

	public void setIdJob(BigDecimal idJob) {
		this.idJob = idJob;
	}
}
