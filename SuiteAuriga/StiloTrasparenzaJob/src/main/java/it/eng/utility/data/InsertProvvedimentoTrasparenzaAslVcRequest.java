/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.utility.data;

import java.io.Serializable;

public class InsertProvvedimentoTrasparenzaAslVcRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String annoProvvedimento;
	private String meseProvvedimento;
	private String oggettoProvvedimeno;
	private String numeroProvvedimento;
	private String dataProvvedimento;
	private String tipoProvvedimento;
	private String dataPubblicazioneDal;
	private String dataPubblicazioneAl;
	
	public String getAnnoProvvedimento() {
		return annoProvvedimento;
	}
	
	public void setAnnoProvvedimento(String annoProvvedimento) {
		this.annoProvvedimento = annoProvvedimento;
	}
	
	public String getMeseProvvedimento() {
		return meseProvvedimento;
	}

	public void setMeseProvvedimento(String meseProvvedimento) {
		this.meseProvvedimento = meseProvvedimento;
	}

	public String getOggettoProvvedimeno() {
		return oggettoProvvedimeno;
	}
	
	public void setOggettoProvvedimeno(String oggettoProvvedimeno) {
		this.oggettoProvvedimeno = oggettoProvvedimeno;
	}
	
	public String getNumeroProvvedimento() {
		return numeroProvvedimento;
	}
	
	public void setNumeroProvvedimento(String numeroProvvedimento) {
		this.numeroProvvedimento = numeroProvvedimento;
	}
	
	public String getDataProvvedimento() {
		return dataProvvedimento;
	}
	
	public void setDataProvvedimento(String dataProvvedimento) {
		this.dataProvvedimento = dataProvvedimento;
	}
	
	public String getTipoProvvedimento() {
		return tipoProvvedimento;
	}
	
	public void setTipoProvvedimento(String tipoProvvedimento) {
		this.tipoProvvedimento = tipoProvvedimento;
	}

	public String getDataPubblicazioneDal() {
		return dataPubblicazioneDal;
	}

	public void setDataPubblicazioneDal(String dataPubblicazioneDal) {
		this.dataPubblicazioneDal = dataPubblicazioneDal;
	}

	public String getDataPubblicazioneAl() {
		return dataPubblicazioneAl;
	}

	public void setDataPubblicazioneAl(String dataPubblicazioneAl) {
		this.dataPubblicazioneAl = dataPubblicazioneAl;
	}

	@Override
	public String toString() {
		return "InsertProvvedimentoTrasparenzaAslVcRequest [annoProvvedimento=" + annoProvvedimento
				+ ", meseProvvedimento=" + meseProvvedimento + ", oggettoProvvedimeno=" + oggettoProvvedimeno
				+ ", numeroProvvedimento=" + numeroProvvedimento + ", dataProvvedimento=" + dataProvvedimento
				+ ", tipoProvvedimento=" + tipoProvvedimento + ", dataPubblicazioneDal=" + dataPubblicazioneDal
				+ ", dataPubblicazioneAl=" + dataPubblicazioneAl + "]";
	}
	
}
