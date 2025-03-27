/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.document.function.bean;

import java.io.Serializable;

public class PubblicazioneListaAllegatiAvvio implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String idDoc;
	private String nomeFileDoc;
	private String numeroAllegato;
	private String tipoDoc;
	
	public String getIdDoc() {
		return idDoc;
	}
	
	public void setIdDoc(String idDoc) {
		this.idDoc = idDoc;
	}
	
	public String getNomeFileDoc() {
		return nomeFileDoc;
	}
	
	public void setNomeFileDoc(String nomeFileDoc) {
		this.nomeFileDoc = nomeFileDoc;
	}
	
	public String getNumeroAllegato() {
		return numeroAllegato;
	}
	
	public void setNumeroAllegato(String numeroAllegato) {
		this.numeroAllegato = numeroAllegato;
	}
	
	public String getTipoDoc() {
		return tipoDoc;
	}
	
	public void setTipoDoc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}
	
	@Override
	public String toString() {
		return "PubblicazioneListaAllegatiAvvio [idDoc=" + idDoc + ", nomeFileDoc=" + nomeFileDoc + ", numeroAllegato="
				+ numeroAllegato + ", tipoDoc=" + tipoDoc + "]";
	}
	
}
