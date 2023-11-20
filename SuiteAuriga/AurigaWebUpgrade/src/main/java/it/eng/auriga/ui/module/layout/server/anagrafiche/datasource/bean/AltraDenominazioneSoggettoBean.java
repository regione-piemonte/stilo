/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;


public class AltraDenominazioneSoggettoBean {

	@NumeroColonna(numero = "1")
	private String rowId;
	@NumeroColonna(numero = "2")
	private String tipo;
	@NumeroColonna(numero = "3")
	private String denominazione;
	
	public String getRowId() {
		return rowId;
	}
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	
}
