/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.document.function.bean;

import it.eng.document.NumeroColonna;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DistribuzioneBean implements Serializable{
	
	@NumeroColonna(numero = "1")
	private String idLista;

	@NumeroColonna(numero = "4")
	private Flag perConoscenza;
	
	@NumeroColonna(numero = "5")
   	private Flag assegna;
	
	@NumeroColonna(numero = "6")
   	private String mezzoTrasmissioneDestinatario;
	
	public String getIdLista() {
		return idLista;
	}
	public void setIdLista(String idLista) {
		this.idLista = idLista;
	}
	public Flag getPerConoscenza() {
		return perConoscenza;
	}
	public void setPerConoscenza(Flag perConoscenza) {
		this.perConoscenza = perConoscenza;
	}
	public Flag getAssegna() {
		return assegna;
	}
	public void setAssegna(Flag assegna) {
		this.assegna = assegna;
	}
	public String getMezzoTrasmissioneDestinatario() {
		return mezzoTrasmissioneDestinatario;
	}
	public void setMezzoTrasmissioneDestinatario(String mezzoTrasmissioneDestinatario) {
		this.mezzoTrasmissioneDestinatario = mezzoTrasmissioneDestinatario;
	}
	
}
