/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AssegnatariOutBean implements Serializable{
	
	@NumeroColonna(numero = "1")
	private String internalValue;
	
	@NumeroColonna(numero = "2")
	private String codiceUo;
	
	@NumeroColonna(numero = "3")
	private String descr;
	
	@NumeroColonna(numero = "4")
	private String descrEstesa;
	
	@NumeroColonna(numero = "5")
	private String presaInCarico;
	
	@NumeroColonna(numero = "6")
	private String messAltPresaInCarico;
	
	@NumeroColonna(numero = "7")
	private Flag flgDisable;
	
	public String getInternalValue() {
		return internalValue;
	}
	public void setInternalValue(String internalValue) {
		this.internalValue = internalValue;
	}
	public String getCodiceUo() {
		return codiceUo;
	}
	public void setCodiceUo(String codiceUo) {
		this.codiceUo = codiceUo;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getDescrEstesa() {
		return descrEstesa;
	}
	public void setDescrEstesa(String descrEstesa) {
		this.descrEstesa = descrEstesa;
	}
	public String getPresaInCarico() {
		return presaInCarico;
	}
	public void setPresaInCarico(String presaInCarico) {
		this.presaInCarico = presaInCarico;
	}
	public String getMessAltPresaInCarico() {
		return messAltPresaInCarico;
	}
	public void setMessAltPresaInCarico(String messAltPresaInCarico) {
		this.messAltPresaInCarico = messAltPresaInCarico;
	}
	public Flag getFlgDisable() {
		return flgDisable;
	}
	public void setFlgDisable(Flag flgDisable) {
		this.flgDisable = flgDisable;
	}
}