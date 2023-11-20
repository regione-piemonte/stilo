/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SicraResidenza implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String comune;
	private String cap;
	private String siglaProvincia;
	private String specie;
	private String area;
	private String civico;
	private String email;
	private String telefono;
	private String cellulare;
	private String lettera;
	private String corte;
	private String scala;
	private String interno;
	private String piano;
	
	public String getComune() {
		return comune;
	}
	
	public void setComune(String comune) {
		this.comune = comune;
	}
	
	public String getCap() {
		return cap;
	}
	
	public void setCap(String cap) {
		this.cap = cap;
	}
	
	public String getSiglaProvincia() {
		return siglaProvincia;
	}
	
	public void setSiglaProvincia(String siglaProvincia) {
		this.siglaProvincia = siglaProvincia;
	}
	
	public String getSpecie() {
		return specie;
	}
	
	public void setSpecie(String specie) {
		this.specie = specie;
	}
	
	public String getArea() {
		return area;
	}
	
	public void setArea(String area) {
		this.area = area;
	}
	
	public String getCivico() {
		return civico;
	}
	
	public void setCivico(String civico) {
		this.civico = civico;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getTelefono() {
		return telefono;
	}
	
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public String getCellulare() {
		return cellulare;
	}
	
	public void setCellulare(String cellulare) {
		this.cellulare = cellulare;
	}
	
	public String getLettera() {
		return lettera;
	}
	
	public void setLettera(String lettera) {
		this.lettera = lettera;
	}
	
	public String getCorte() {
		return corte;
	}
	
	public void setCorte(String corte) {
		this.corte = corte;
	}
	
	public String getScala() {
		return scala;
	}
	
	public void setScala(String scala) {
		this.scala = scala;
	}
	
	public String getInterno() {
		return interno;
	}
	
	public void setInterno(String interno) {
		this.interno = interno;
	}
	
	public String getPiano() {
		return piano;
	}
	
	public void setPiano(String piano) {
		this.piano = piano;
	}
	
}
