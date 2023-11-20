/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class DatiTopograficoXMLIOBean {

	
	@NumeroColonna(numero = "1")
	private String idTopografico;

	
	@NumeroColonna(numero = "2")
	private String codRapidoTopografico;
	
	
	@NumeroColonna(numero = "3")
	private String nomeTopografico;
	
	@NumeroColonna(numero = "4")
	private String descrTopografico;
	
	
	public String getIdTopografico() {
		return idTopografico;
	}

	public void setIdTopografico(String idTopografico) {
		this.idTopografico = idTopografico;
	}

	public String getCodRapidoTopografico() {
		return codRapidoTopografico;
	}

	public void setCodRapidoTopografico(String codRapidoTopografico) {
		this.codRapidoTopografico = codRapidoTopografico;
	}

	public String getNomeTopografico() {
		return nomeTopografico;
	}

	public void setNomeTopografico(String nomeTopografico) {
		this.nomeTopografico = nomeTopografico;
	}
	
	public String getDescrTopografico() {
		return descrTopografico;
	}

	public void setDescrTopografico(String descrTopografico) {
		this.descrTopografico = descrTopografico;
	}
	
}
