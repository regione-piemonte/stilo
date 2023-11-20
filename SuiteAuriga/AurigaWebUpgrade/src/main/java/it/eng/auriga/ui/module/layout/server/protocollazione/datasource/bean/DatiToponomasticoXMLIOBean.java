/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class DatiToponomasticoXMLIOBean {

	
	@NumeroColonna(numero = "1")
	private String idToponimo;

	
	@NumeroColonna(numero = "2")
	private String codRapidoToponimo;
	
	
	@NumeroColonna(numero = "3")
	private String nomeToponimo;
	
	@NumeroColonna(numero = "4")
	private String descrToponimo;
	
	
	public String getIdToponimo() {
		return idToponimo;
	}

	public void setIdToponimo(String idToponimo) {
		this.idToponimo = idToponimo;
	}

	public String getCodRapidoToponimo() {
		return codRapidoToponimo;
	}

	public void setCodRapidoToponimo(String codRapidoToponimo) {
		this.codRapidoToponimo = codRapidoToponimo;
	}

	public String getNomeToponimo() {
		return nomeToponimo;
	}

	public void setNomeToponimo(String nomeToponimo) {
		this.nomeToponimo = nomeToponimo;
	}
	
	public String getDescrToponimo() {
		return descrToponimo;
	}

	public void setDescrToponimo(String descrToponimo) {
		this.descrToponimo = descrToponimo;
	}
	
}
