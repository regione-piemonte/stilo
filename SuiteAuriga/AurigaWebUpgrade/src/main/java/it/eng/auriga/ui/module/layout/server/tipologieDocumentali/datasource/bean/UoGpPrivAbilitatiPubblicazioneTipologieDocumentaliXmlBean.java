/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class UoGpPrivAbilitatiPubblicazioneTipologieDocumentaliXmlBean {

    @NumeroColonna(numero = "1")
	private String tipo;

	@NumeroColonna(numero = "2")
	private String idUoGpPriv;
	
	@NumeroColonna(numero = "3")
	private String denominazioneUoGpPriv;

	@NumeroColonna(numero = "4")
	private String nroLivelliUo;

	private String descTipo;
	
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getIdUoGpPriv() {
		return idUoGpPriv;
	}

	public void setIdUoGpPriv(String idUoGpPriv) {
		this.idUoGpPriv = idUoGpPriv;
	}

	public String getDenominazioneUoGpPriv() {
		return denominazioneUoGpPriv;
	}

	public void setDenominazioneUoGpPriv(String denominazioneUoGpPriv) {
		this.denominazioneUoGpPriv = denominazioneUoGpPriv;
	}

	public String getNroLivelliUo() {
		return nroLivelliUo;
	}

	public void setNroLivelliUo(String nroLivelliUo) {
		this.nroLivelliUo = nroLivelliUo;
	}

	public String getDescTipo() {
		return descTipo;
	}

	public void setDescTipo(String descTipo) {
		this.descTipo = descTipo;
	}


	
}