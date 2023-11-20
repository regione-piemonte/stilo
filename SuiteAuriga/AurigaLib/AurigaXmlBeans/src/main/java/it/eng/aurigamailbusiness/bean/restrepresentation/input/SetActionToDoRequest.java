/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="richiestaImpostaAzioneDaFareSuEmail")
public class SetActionToDoRequest extends MutualInput {
	
	@XmlElement(name="idEmail", required=true)
	private String idEmail;
	
	@XmlElement(name="codiceAzione")
	private String codiceAzione;
	
	@XmlElement(name="dettaglioAzione")
	private String dettaglioAzione;
	
	@XmlElement(name="flagRilascioLock")
	private Boolean flagRilascioLock;
	
	public String getCodiceAzione() {
		return codiceAzione;
	}
	public void setCodiceAzione(String codiceAzione) {
		this.codiceAzione = codiceAzione;
	}
	
	public String getDettaglioAzione() {
		return dettaglioAzione;
	}
	public void setDettaglioAzione(String dettaglioAzione) {
		this.dettaglioAzione = dettaglioAzione;
	}
	
	public Boolean getFlagRilascioLock() {
		return flagRilascioLock;
	}
	public void setFlagRilascioLock(Boolean flagRilascioLock) {
		this.flagRilascioLock = flagRilascioLock;
	}
	
	public String getIdEmail() {
		return idEmail;
	}
	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}
	
}//SetActionToDoRequest
