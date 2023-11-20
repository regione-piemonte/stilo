/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

/**
 * 
 * @author dbe4235
 *
 */

public class AssInviiCCXmlBean {
	
	/**
	 * A = assegnazione / C = competenza
	 */
	@NumeroColonna(numero = "1")
	private String tipo;
	
	@NumeroColonna(numero = "2")
	private String idUo;
	
	@NumeroColonna(numero = "3")
	private String codRapido;
	
	@NumeroColonna(numero = "4")
	private String denominazione;

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getIdUo() {
		return idUo;
	}

	public void setIdUo(String idUo) {
		this.idUo = idUo;
	}

	public String getCodRapido() {
		return codRapido;
	}

	public void setCodRapido(String codRapido) {
		this.codRapido = codRapido;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

}