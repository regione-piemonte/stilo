/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class DestInvioMailXmlBean  {
	
	@NumeroColonna(numero = "4")
	private String idRubrica; 
	@NumeroColonna(numero = "5")
	private String denominazione; 
	@NumeroColonna(numero = "8")
	private String indirizzoMail;
	
	public String getIdRubrica() {
		return idRubrica;
	}
	public void setIdRubrica(String idRubrica) {
		this.idRubrica = idRubrica;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public String getIndirizzoMail() {
		return indirizzoMail;
	}
	public void setIndirizzoMail(String indirizzoMail) {
		this.indirizzoMail = indirizzoMail;
	}	
	
}