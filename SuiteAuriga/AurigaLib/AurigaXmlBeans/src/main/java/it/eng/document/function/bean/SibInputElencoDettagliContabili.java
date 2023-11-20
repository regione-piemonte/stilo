/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.document.function.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SibInputElencoDettagliContabili implements Serializable {

	private static final long serialVersionUID = 1L;

	private String siglaRegistroProposta;
	private short annoProposta;
	private long numeroProposta;
	private Integer numeroBloccoInformazioni;
	private String tipoTitolo;

	public String getSiglaRegistroProposta() {
		return siglaRegistroProposta;
	}

	public void setSiglaRegistroProposta(String siglaRegistroProposta) {
		this.siglaRegistroProposta = siglaRegistroProposta;
	}

	public short getAnnoProposta() {
		return annoProposta;
	}

	public void setAnnoProposta(short annoProposta) {
		this.annoProposta = annoProposta;
	}

	public long getNumeroProposta() {
		return numeroProposta;
	}

	public void setNumeroProposta(long numeroProposta) {
		this.numeroProposta = numeroProposta;
	}

	public Integer getNumeroBloccoInformazioni() {
		return numeroBloccoInformazioni;
	}

	public void setNumeroBloccoInformazioni(Integer numeroBloccoInformazioni) {
		this.numeroBloccoInformazioni = numeroBloccoInformazioni;
	}

	public String getTipoTitolo() {
		return tipoTitolo;
	}

	public void setTipoTitolo(String tipoTitolo) {
		this.tipoTitolo = tipoTitolo;
	}

}
