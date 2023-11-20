/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

/**
 * 
 * @author dbe4235
 *
 */

public class ContenutiDaRiordinareXmlBean {
	
	@NumeroColonna(numero = "1")
	private String idContenuto;
	
	@NumeroColonna(numero = "2")
	private Integer nrOrdine;

	public String getIdContenuto() {
		return idContenuto;
	}

	public void setIdContenuto(String idContenuto) {
		this.idContenuto = idContenuto;
	}

	public Integer getNrOrdine() {
		return nrOrdine;
	}

	public void setNrOrdine(Integer nrOrdine) {
		this.nrOrdine = nrOrdine;
	}

}