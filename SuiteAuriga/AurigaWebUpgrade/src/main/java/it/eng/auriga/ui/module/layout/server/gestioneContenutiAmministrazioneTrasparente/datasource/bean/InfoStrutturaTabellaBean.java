/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class InfoStrutturaTabellaBean {
	
	private String intestazione;
	private String tipo;
	private Integer larghezza;
	private String nrPosizColonnaOrdinamento;
	private String versoOrdinamento;
	private Boolean flgValoreObbligatorio;
	private Boolean flgDettRiga;
	private String valoriAmmessi;
	
	public String getIntestazione() {
		return intestazione;
	}
	public String getTipo() {
		return tipo;
	}
	public Integer getLarghezza() {
		return larghezza;
	}
	public String getNrPosizColonnaOrdinamento() {
		return nrPosizColonnaOrdinamento;
	}
	public String getVersoOrdinamento() {
		return versoOrdinamento;
	}
	public Boolean getFlgValoreObbligatorio() {
		return flgValoreObbligatorio;
	}
	public Boolean getFlgDettRiga() {
		return flgDettRiga;
	}
	public void setIntestazione(String intestazione) {
		this.intestazione = intestazione;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public void setLarghezza(Integer larghezza) {
		this.larghezza = larghezza;
	}
	public void setNrPosizColonnaOrdinamento(String nrPosizColonnaOrdinamento) {
		this.nrPosizColonnaOrdinamento = nrPosizColonnaOrdinamento;
	}
	public void setVersoOrdinamento(String versoOrdinamento) {
		this.versoOrdinamento = versoOrdinamento;
	}
	public void setFlgValoreObbligatorio(Boolean flgValoreObbligatorio) {
		this.flgValoreObbligatorio = flgValoreObbligatorio;
	}
	public void setFlgDettRiga(Boolean flgDettRiga) {
		this.flgDettRiga = flgDettRiga;
	}
	public String getValoriAmmessi() {
		return valoriAmmessi;
	}
	public void setValoriAmmessi(String valoriAmmessi) {
		this.valoriAmmessi = valoriAmmessi;
	}
}
