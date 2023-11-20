/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class AttrCustomToShowOutBean {

	@NumeroColonna(numero = "1")
	private String attrName;
	@NumeroColonna(numero = "2")
	private String idFolder;
	@NumeroColonna(numero = "3")
	private String campoCodIstatComune;
	@NumeroColonna(numero = "4")
	private String campoNomeComune;
	@NumeroColonna(numero = "5")
	private String campoFrazione;
	@NumeroColonna(numero = "6")
	private String campoIndirizzo;
	@NumeroColonna(numero = "7")
	private String campoCivico;
	@NumeroColonna(numero = "8")
	private String campoCap;
	@NumeroColonna(numero = "9")
	private String campoUbicazione;
	@NumeroColonna(numero = "10")
	private String campoIdLayer;
	
	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public String getIdFolder() {
		return idFolder;
	}

	public void setIdFolder(String idFolder) {
		this.idFolder = idFolder;
	}

	public String getCampoCodIstatComune() {
		return campoCodIstatComune;
	}

	public void setCampoCodIstatComune(String campoCodIstatComune) {
		this.campoCodIstatComune = campoCodIstatComune;
	}

	public String getCampoNomeComune() {
		return campoNomeComune;
	}

	public void setCampoNomeComune(String campoNomeComune) {
		this.campoNomeComune = campoNomeComune;
	}

	public String getCampoFrazione() {
		return campoFrazione;
	}

	public void setCampoFrazione(String campoFrazione) {
		this.campoFrazione = campoFrazione;
	}

	public String getCampoIndirizzo() {
		return campoIndirizzo;
	}

	public void setCampoIndirizzo(String campoIndirizzo) {
		this.campoIndirizzo = campoIndirizzo;
	}

	public String getCampoCivico() {
		return campoCivico;
	}

	public void setCampoCivico(String campoCivico) {
		this.campoCivico = campoCivico;
	}

	public String getCampoCap() {
		return campoCap;
	}

	public void setCampoCap(String campoCap) {
		this.campoCap = campoCap;
	}

	public String getCampoUbicazione() {
		return campoUbicazione;
	}

	public void setCampoUbicazione(String campoUbicazione) {
		this.campoUbicazione = campoUbicazione;
	}

	public String getCampoIdLayer() {
		return campoIdLayer;
	}

	public void setCampoIdLayer(String campoIdLayer) {
		this.campoIdLayer = campoIdLayer;
	}	
	
}