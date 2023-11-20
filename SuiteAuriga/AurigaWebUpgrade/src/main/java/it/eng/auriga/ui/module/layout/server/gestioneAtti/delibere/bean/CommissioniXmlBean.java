/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import it.eng.document.NumeroColonna;

/**
 * 
 * @author OPASSALACQUA
 *
 */

public class CommissioniXmlBean {
	
	@NumeroColonna(numero = "1")
	private String idCommissione;
	
	@NumeroColonna(numero = "2")
	private String nomeCommissione;

	public String getIdCommissione() {
		return idCommissione;
	}

	public void setIdCommissione(String idCommissione) {
		this.idCommissione = idCommissione;
	}

	public String getNomeCommissione() {
		return nomeCommissione;
	}

	public void setNomeCommissione(String nomeCommissione) {
		this.nomeCommissione = nomeCommissione;
	}
	
	
}