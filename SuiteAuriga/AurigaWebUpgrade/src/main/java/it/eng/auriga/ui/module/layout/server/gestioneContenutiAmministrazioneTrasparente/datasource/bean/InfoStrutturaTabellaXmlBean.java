/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;


public class InfoStrutturaTabellaXmlBean {
	
	@NumeroColonna(numero = "1")
	private String intestazione;
	
	@NumeroColonna(numero = "2")
	private String tipo;
	
	@NumeroColonna(numero = "3")
	private String larghezza;
	
	@NumeroColonna(numero = "4")
	private String nrPosizColonnaOrdinamento;
	
	@NumeroColonna(numero = "5")
	private String versoOrdinamento;

	@NumeroColonna(numero = "6")
	private String flgValoreObbligatorio;
	
	@NumeroColonna(numero = "7")
	private String flgDettRiga;

	@NumeroColonna(numero = "8")
	private String valoriAmmessi;

	
	public String getIntestazione() {
		return intestazione;
	}

	public String getTipo() {
		return tipo;
	}

	public String getLarghezza() {
		return larghezza;
	}

	public String getNrPosizColonnaOrdinamento() {
		return nrPosizColonnaOrdinamento;
	}

	public String getVersoOrdinamento() {
		return versoOrdinamento;
	}

	public String getFlgValoreObbligatorio() {
		return flgValoreObbligatorio;
	}

	public String getFlgDettRiga() {
		return flgDettRiga;
	}

	public void setIntestazione(String intestazione) {
		this.intestazione = intestazione;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setLarghezza(String larghezza) {
		this.larghezza = larghezza;
	}

	public void setNrPosizColonnaOrdinamento(String nrPosizColonnaOrdinamento) {
		this.nrPosizColonnaOrdinamento = nrPosizColonnaOrdinamento;
	}

	public void setVersoOrdinamento(String versoOrdinamento) {
		this.versoOrdinamento = versoOrdinamento;
	}

	public void setFlgValoreObbligatorio(String flgValoreObbligatorio) {
		this.flgValoreObbligatorio = flgValoreObbligatorio;
	}

	public void setFlgDettRiga(String flgDettRiga) {
		this.flgDettRiga = flgDettRiga;
	}

	public String getValoriAmmessi() {
		return valoriAmmessi;
	}

	public void setValoriAmmessi(String valoriAmmessi) {
		this.valoriAmmessi = valoriAmmessi;
	}

	
}