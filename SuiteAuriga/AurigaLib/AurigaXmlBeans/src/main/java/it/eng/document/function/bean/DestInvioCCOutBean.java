/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;

@XmlRootElement
public class DestInvioCCOutBean implements Serializable{
	
	@NumeroColonna(numero = "1")
	private String tipo;
	
	@NumeroColonna(numero = "2")
	private String id;
	
	@NumeroColonna(numero = "3")
	private String descr;
	
	@NumeroColonna(numero = "4")
	private String codRapido;
	
	@NumeroColonna(numero = "5")
	private String descrEstesa;
	
	@NumeroColonna(numero ="6")
	private String statoFirmaResponsabile;
	
	
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
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getCodRapido() {
		return codRapido;
	}
	public void setCodRapido(String codRapido) {
		this.codRapido = codRapido;
	}
	public String getDescrEstesa() {
		return descrEstesa;
	}
	public void setDescrEstesa(String descrEstesa) {
		this.descrEstesa = descrEstesa;
	}	
	public String getStatoFirmaResponsabile() {
		return statoFirmaResponsabile;
	}	
	public void setStatoFirmaResponsabile(String statoFirmaResponsabile) {
		this.statoFirmaResponsabile = statoFirmaResponsabile;
	}
	
}
