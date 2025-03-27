/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.document.function.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;

@XmlRootElement
public class CoredattoriBean implements Serializable{
	
	@NumeroColonna(numero = "1")
	private String tipo;
	
	@NumeroColonna(numero = "2")
	private String id;
	
	@NumeroColonna(numero = "3")
	private String codRapido;
	
	@NumeroColonna(numero = "4")
	private String descr;
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCodRapido() {
		return codRapido;
	}
	public void setCodRapido(String codRapido) {
		this.codRapido = codRapido;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	
}
