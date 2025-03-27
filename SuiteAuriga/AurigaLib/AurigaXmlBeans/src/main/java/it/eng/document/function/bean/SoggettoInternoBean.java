/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.document.function.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;

@XmlRootElement
public class SoggettoInternoBean implements Serializable {

	private static final long serialVersionUID = 5611310655086324291L;

	// 1: UT | SV | UO
	@NumeroColonna(numero = "1")
	private String tipoObj;

	// 2: ID_USER | ID_SV | ID_UO
	@NumeroColonna(numero = "2")
	private String idObj;

	// 17: F se Tipo firma = D, AV se tipo firma E
	@NumeroColonna(numero = "17")
	private String tipoFirma;

	// 19: N.ro ordine
	@NumeroColonna(numero = "19")
	private String nroOrdine;
	
	// 20: Ruolo
	@NumeroColonna(numero = "20")
	private String ruolo;

	public String getTipoObj() {
		return tipoObj;
	}

	public void setTipoObj(String tipoObj) {
		this.tipoObj = tipoObj;
	}

	public String getIdObj() {
		return idObj;
	}

	public void setIdObj(String idObj) {
		this.idObj = idObj;
	}

	public String getTipoFirma() {
		return tipoFirma;
	}

	public void setTipoFirma(String tipoFirma) {
		this.tipoFirma = tipoFirma;
	}

	public String getNroOrdine() {
		return nroOrdine;
	}

	public void setNroOrdine(String nroOrdine) {
		this.nroOrdine = nroOrdine;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
	
}
