/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class CriteriIterNonSvoltBean {
	
	@NumeroColonna(numero="1")
	private String faseTask;
	@NumeroColonna(numero="2")
	private String value;
	@NumeroColonna(numero="3")
	private String tipo;
	
	public String getFaseTask() {
		return faseTask;
	}
	public void setFaseTask(String faseTask) {
		this.faseTask = faseTask;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
