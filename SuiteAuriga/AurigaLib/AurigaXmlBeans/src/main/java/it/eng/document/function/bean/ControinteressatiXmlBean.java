/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;

/**
 * 
 * @author dbe4235
 *
 */

@XmlRootElement
public class ControinteressatiXmlBean implements Serializable {

	@NumeroColonna(numero ="1")
	private String tipoSoggetto; // Persona fisica / giuridica
	
	@NumeroColonna(numero ="2")
	private String denominazione; // Cognome e Nome / Ragione sociale
	
	@NumeroColonna(numero ="3")
	private String codFiscale; // Cod. fiscale
	
	@NumeroColonna(numero ="4")
	private String pIva; // Partita IVA
	
	@NumeroColonna(numero ="5")
	private String note; // Note

	public String getTipoSoggetto() {
		return tipoSoggetto;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public String getCodFiscale() {
		return codFiscale;
	}

	public String getpIva() {
		return pIva;
	}

	public String getNote() {
		return note;
	}

	public void setTipoSoggetto(String tipoSoggetto) {
		this.tipoSoggetto = tipoSoggetto;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public void setCodFiscale(String codFiscale) {
		this.codFiscale = codFiscale;
	}

	public void setpIva(String pIva) {
		this.pIva = pIva;
	}

	public void setNote(String note) {
		this.note = note;
	}

}