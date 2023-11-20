/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;


public class PuntiVenditaBean {

	@NumeroColonna(numero="1")
	private String idPuntoVendita;
		
	@NumeroColonna(numero="2")
	private String denominazionePuntoVendita;
	
	@NumeroColonna(numero="3")
	private String cittaPuntoVendita;
	
	@NumeroColonna(numero="4")
	private String targaProvinciaPuntoVendita;
	
	@NumeroColonna(numero="5")
	private String capPuntoVendita;
	
	@NumeroColonna(numero="6")
	private String indirizzoPuntoVendita;
	
	@NumeroColonna(numero="7")
	private String civicoIndirizzoPuntoVendita;

	public String getIdPuntoVendita() {
		return idPuntoVendita;
	}

	public void setIdPuntoVendita(String idPuntoVendita) {
		this.idPuntoVendita = idPuntoVendita;
	}

	
	public String getCittaPuntoVendita() {
		return cittaPuntoVendita;
	}

	public void setCittaPuntoVendita(String cittaPuntoVendita) {
		this.cittaPuntoVendita = cittaPuntoVendita;
	}

	public String getTargaProvinciaPuntoVendita() {
		return targaProvinciaPuntoVendita;
	}

	public void setTargaProvinciaPuntoVendita(String targaProvinciaPuntoVendita) {
		this.targaProvinciaPuntoVendita = targaProvinciaPuntoVendita;
	}

	public String getCapPuntoVendita() {
		return capPuntoVendita;
	}

	public void setCapPuntoVendita(String capPuntoVendita) {
		this.capPuntoVendita = capPuntoVendita;
	}

	public String getIndirizzoPuntoVendita() {
		return indirizzoPuntoVendita;
	}

	public void setIndirizzoPuntoVendita(String indirizzoPuntoVendita) {
		this.indirizzoPuntoVendita = indirizzoPuntoVendita;
	}

	public String getCivicoIndirizzoPuntoVendita() {
		return civicoIndirizzoPuntoVendita;
	}

	public void setCivicoIndirizzoPuntoVendita(String civicoIndirizzoPuntoVendita) {
		this.civicoIndirizzoPuntoVendita = civicoIndirizzoPuntoVendita;
	}

	public String getDenominazionePuntoVendita() {
		return denominazionePuntoVendita;
	}

	public void setDenominazionePuntoVendita(String denominazionePuntoVendita) {
		this.denominazionePuntoVendita = denominazionePuntoVendita;
	}
	
}
